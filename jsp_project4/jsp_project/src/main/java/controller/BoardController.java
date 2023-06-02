package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.BoardVO;
import domain.PagingVO;
import handler.FileHandler;
import handler.PagingHandler;
import net.coobird.thumbnailator.Thumbnails;
import service.BoardService;
import service.BoardServiceImpl;

@WebServlet("/brd/*")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(BoardController.class);
    private RequestDispatcher rdp; // 웹의 목적지 주소로 객체를 전달해주는 객체 
	private String destPage; //목적지 주소를 저장해주는 변수
	private int isOk; //db의 결과를 받는 변수
    private BoardService bsv; // 서비스 인터페이스
    //파일 경로를 저장할 변수
    private String savePath;
    private final String UTF8 ="utf-8"; //인코딩 설정시
	
    public BoardController() {
    	bsv = new BoardServiceImpl();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		String uri = request.getRequestURI();
		log.info(">>>uri > "+uri);
		String path = uri.substring(uri.lastIndexOf("/")+1);
		log.info(">>>path > "+path);
		
		switch(path) {
		case "register" : 
			destPage = "/board/register.jsp";
			break;

		case "insert":
			try {
				// _fileUpload 폴더의 물리적 경로 반환
				savePath = getServletContext().getRealPath("_fileUpload");
				log.info(">>>파일경로 "+savePath);
				
				//fileDir 변수에는 실제 파일이 저장될 폴더인 File 객체가 생성됨
				File fileDir = new File(savePath);
				
				DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
				fileItemFactory.setRepository(fileDir); //파일의 저장 위치를 저장
				fileItemFactory.setSizeThreshold(2*1024*1024); //파일을 저장하기 위한 임시 메모리 용량 설정 : byte 단위
				
				//객체 생성
				BoardVO bvo = new BoardVO();
				//image 넣어야 하므로 set으로 가져올 예정 => 중복 값 허용 안하기 위해
				//ServletFileUpload: multipart/form-data 형식으로 넘어온 request 객체를 다루기 쉽게 변환해주는 역할
				//request 객체 안에 있는 값(분리되어있는)을 다시 싹 조립해줌
				ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
				//fileupload가 변환해서 list화해서 itemList에 저장 
				
				//fileUpload.parseRequest(request)를 통해 FileItem 객체 리스트인 itemList를 가져옴
				//itemList에 title, writer, contents가 key:value 로 들어가있음
				//request 객체에서 FileItem을 파싱해서 itemList에 저장함
				List<FileItem> itemList = fileUpload.parseRequest(request);
				for(FileItem item : itemList) { //itemList에 있는 FileItem에 대해 반복
					switch(item.getFieldName()) { //필드이름 가져와서 => key의 값을 가져오는 작업 수행
					case "title" :
						bvo.setTitle(item.getString(UTF8)); 
						// getName하면 안됨 .. 지금 갈아져있음 ,, 그래서 utf8형식으로 가져와달라 해야함
						//그냥 빼면 깨지기 때문에 인코딩 형식 담아서 변환
						break;					
				
					case "writer":
						bvo.setWriter(item.getString(UTF8));
						break;
						
					case "content":
						bvo.setContent(item.getString(UTF8));
						break;
						
					case "image_file":
						//이미지가 있는지 없는지 체크부터 
						if(item.getSize() > 0) { //데이터의 크기를 확인해서 업로드 됐는지 확인
							//경로를 포함한 파일이름 ex) ~~~/dog.jpg
							//파일 이름 가져와서 /부터 마지막 파일이름 추출
							String fileName = item.getName().substring(item.getName().lastIndexOf("/")+1);
							//파일이름이 중복되는 경우 구분히 확실X => 유일성을 주기 위해 파일명 앞에 시간 적음
							fileName = System.currentTimeMillis()+"_"+fileName;
							
							log.info(">>>파일이름 : "+fileName);
							//파일 저장될 경로 포함 File 객체인 uploadFilePath를 생성
							File uploadFilePath = new File(fileDir + File.separator+fileName);
							log.info(">>>실제 파일경로 :" + uploadFilePath);
							
							//저장
							try {
								item.write(uploadFilePath); // uploadFilePath(실제경로)에 자바 객체(파일)를 디스크에 쓰기(파일을 저장한다는 의미)
								bvo.setImage_file(fileName); //bvo의 image)file 속성에 파일 이름 설정해줌
								
								//썸네일 작업 : 리스트 페이지에서 트래픽 과다사용 방지
								//th_는 접두어 붙여서 파일로 저장됨
								Thumbnails.of(uploadFilePath).size(75,75)
								.toFile(new File(fileDir+File.separator+"th_"+fileName));
								
							} catch (Exception e) {
								log.info(">>> file writer on disk fail");
								e.printStackTrace();
							
							}
						}
						break;
					}
				}
				
				isOk = bsv.register(bvo);
				log.info(">>>register > "+(isOk>0?"success":"fail"));
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			destPage = "page";
			break;
			
		case "list":
			//DB 값 가져오기
			// -> setAt~~~
			log.info(">>> list page 이동");
			//객체를 담을 수 있는 list 생성
			List<BoardVO> list = new ArrayList<BoardVO>();
			//service에 list(내맘대로 이름 정해도됨) 버튼 추가해주는 것 => 빨간줄 타고 간다.   
			//빨간줄이 사라지면 db에서 정보를 받아온 상태가 되는 것
			list = bsv.list();
			log.info(">>> list" + list);
			
			//""안의 list는 임의로 이름 정해준 것. 아무거나 해도됨. jsp페이지에서 쓰려고
			//jsp에 뿌려주기 위한 구문 외워.
			request.setAttribute("list", list);
			log.info(">>> list(리스트페이지) 완료");
			
			destPage = "/board/list.jsp";
			break;
			
		case "pagesub":
			try {
				//request parameter로 받아서 넣기
				int pageNo =1;
				int qty = 10;
				//request객체로 받아오는 pageNo에 값이 있으면 
				if(request.getParameter("pageNo")!= null) {
					//pageNo과 qty 값을 각각 가져와서 int로 형변환하기
					pageNo = Integer.parseInt(request.getParameter("pageNo"));
					qty = Integer.parseInt(request.getParameter("qty"));
				}
				
				//pageNo,qty를 갖고 가는 pgvo 객체를 생성
				PagingVO pgvo = new PagingVO(pageNo,qty);
				//전체 페이지 개수 요청 
				//db에 가서 전체 페이지 개수 확인 select count(bno) from board where bno>0; 
				int totCount = bsv.getTotal(pgvo);
				log.info(">>> 전체페이지 개수 : "+totCount);
				//limit을 이용한 select List를 호출 (startPage,qty)
				//한 페이지에 나와야 하는 리스트
				List<BoardVO> list1 = bsv.getPageList(pgvo);
				log.info(">>> list : "+ list1.size());

				PagingHandler ph = new PagingHandler(pgvo, totCount);
				request.setAttribute("pgh", ph);
				request.setAttribute("list", list1);
				log.info("pageList 성공");
				destPage = "/board/list.jsp";
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		//--------------------------------------------------------페이징 여기에 추가	
		case "page":
			try {
				int pageNo =1;
				int qty = 10;
				
				//---------------------------검색 추가 
				String type="";
				String keyword="";
				
				if(request.getParameter("type")!= null) {
					type = request.getParameter("type");
					keyword = request.getParameter("keyword");
					log.info(">>>type >"+ type + " keyword >" +keyword);
				}
				if(request.getParameter("pageNo")!= null) {
					pageNo = Integer.parseInt(request.getParameter("pageNo"));
					qty = Integer.parseInt(request.getParameter("qty"));
				}
				
				PagingVO pgvo = new PagingVO(pageNo,qty);
				
				//-------검색 생성자 추가
				pgvo.setType(type);
				pgvo.setKeyword(keyword);
				log.info(">>>pgvo > " + pgvo);
				
				//전체 페이지 개수 요청 
				//db에 가서 전체 페이지 개수 확인 select count(bno) from board where bno>0; 
				int totCount = bsv.getTotal(pgvo);
				log.info(">>> 전체페이지 개수 : "+totCount);
				//limit을 이용한 select List를 호출 (startPage,qty)
				//한 페이지에 나와야 하는 리스트
				List<BoardVO> list1 = bsv.getPageList(pgvo);
				log.info(">>> list : "+ list1.size());
				
				PagingHandler ph = new PagingHandler(pgvo, totCount);
				request.setAttribute("pgh", ph);
				request.setAttribute("list", list1);
				log.info("pageList 성공~");
				destPage = "/board/list.jsp";
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
			
			
		case "detail":
			try {
				//고유키(기본키)이기 때문에 bno만 있어도 다른 값을 가져올 수 있음
				// 받아오는 값은 다 String이라서 형변환 해줘야 함
				int bno = Integer.parseInt(request.getParameter("bno"));
				log.info(">>> bno > " + bno);
				//DB에서 객체로 받아올 거니까 미리 객체를 생성함.
				BoardVO b = new BoardVO();
				// 그 생성한 변수에 DB에서 받아온 bno를 매개변수로 가져오는 객체를 담아준 것
				b = bsv.detail(bno);
				log.info("bvo >>>>"+b);			
				request.setAttribute("bvo2", b);
				log.info("bvo >>>>"+b);			
				
				
				//이건 나중에 추가함 조회수 세려고
				isOk = bsv.readcount(bno);	
				
				log.info(">>>readcount > "+(isOk>0?"success":"fail"));
				
				log.info(">>> detail 성공");
				
				destPage = "/board/detail.jsp";
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		//DB로 수정하는 정보를 보내기만 함
		case "modify":
			log.info(">>> modify 접근");		
			int bno2 = Integer.parseInt(request.getParameter("bno"));
//			//
			BoardVO b2 = new BoardVO();
			b2 = bsv.detail(bno2);
			request.setAttribute("bvo", b2);
			destPage="/board/modify.jsp";
			break;
			
		case "edit" :
			try {
				savePath = getServletContext().getRealPath("/_fileUpload");
				File fileDir = new File(savePath);
				
				DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
				fileItemFactory.setRepository(fileDir);
				fileItemFactory.setSizeThreshold(2*1024*1024); //2메가 저장공간 확보
				
				BoardVO bvo = new BoardVO();
				
				ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
				log.info(">>> update 준비");
				
				

				
				//key value 형태로 가지고 오려고
				List<FileItem> itemList = fileUpload.parseRequest(request);
				
				String old_file = null;
				for(FileItem item : itemList) {
					switch(item.getFieldName()) {
					case "bno" :
						bvo.setBno(Integer.parseInt(item.getString(UTF8)));
						break;

					case "title" :
						bvo.setTitle(item.getString(UTF8));
						break;
				
					case "content" :
						bvo.setContent(item.getString(UTF8));
						break;
					
					case "image_file" :
						//기존 파일의 이름 가져와서 담기
						old_file = item.getString(UTF8);
						break;
					
					case "new_file" :
						if(item.getSize()>0) { //새로운 파일이 등록됨
							if(old_file != null) {
								//파일 핸들러 호출(기존 파일 삭제)
								FileHandler fileHandler = new FileHandler();
								isOk = fileHandler.deleteFile(old_file, savePath);
							}
							//경로 전체의 이름 설정 : 경로 + 이름 / ex) ~~~/dog.jpg
							String fileName= item.getName().substring(item.getName().lastIndexOf("/")+1);
							log.info("new_fileName : "+fileName);
							//실제 저장이름
							fileName = System.currentTimeMillis()+"_"+fileName;
							File uploadFilePath = new File(fileDir + File.separator+fileName);
							try {
								item.write(uploadFilePath);
								bvo.setImage_file(fileName);
								log.info(">>> bvo.image_file > "+bvo.getImage_file());
								
								//썸네일 작업
								Thumbnails.of(uploadFilePath).size(75, 75).toFile(new File(fileDir+File.separator+"th_"+fileName));
								
							} catch (Exception e2) {
								log.info(">>> file update on disk fail");
								e2.printStackTrace();
							}
						}else {//새로운 파일을 넣지 않았다면,,, (파일 변경을 안 했다면)
							//기존파일을 다시 bvo객체에 저장
							bvo.setImage_file(old_file);
							
						}
						break;
					}
				}
				
				//modify 페이지에서 수정한 값을 받기 위해서 request로 불러와서 변수에 넣음
//				String title3 = request.getParameter("title");
//				String content3 = request.getParameter("content");
//				int bno3 = Integer.parseInt(request.getParameter("bno"));
					//객체로 보내야 하니까 생성
//				BoardVO bvo3 = new BoardVO(bno3, title3, content3);
					//update가 DB에 가져다주고, 가져오는 걸 전부 다 하는 거임
//				isOk = bsv.update(bvo3);			
					log.info(">>>update > "+(isOk>0?"success":"fail"));
					//수정하고나서 컨트롤러의 list로 가야함!!! 
					//list.jsp로 가면 빈 페이지만 띄워줌 (주의!!)

				isOk = bsv.update(bvo);
				log.info(">>>update > "+(isOk>0?"success":"fail"));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
//			destPage="list";
			destPage="page";
			break;
			
		case "delete":
			try {
				int bno = Integer.parseInt(request.getParameter("bno"));
				//파일 삭제 추가
				//image_file 호출, savePath 
				String imageFileName = bsv.getFileName(bno); //삭제할 파일이름 호출
				log.info(">>> removeFileName : "+imageFileName);
				savePath = getServletContext().getRealPath("/_fileUpload");
				FileHandler fh = new FileHandler();
				isOk = fh.deleteFile(imageFileName, savePath);
				log.info(">>> removeFile >> " + (isOk >0 ? "ok":"fail"));
				
				isOk = bsv.delete(bno);
				log.info(">>> allremove >> " + (isOk >0 ? "ok":"fail"));
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			destPage="page";
			break;
			
			
			
		}//switch 끝	
		
		rdp=request.getRequestDispatcher(destPage);
		rdp.forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

}
