package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.BoardVO;
import domain.PagingVO;
import handler.PagingHandler;
import service.BoardService;
import service.BoardServiceImpl;


@WebServlet("/brd/*")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(BoardController.class);
     private RequestDispatcher rdp;
     private String destPage;
     private int isOk;
     private BoardService bsv;

    public BoardController() {
    	bsv = new BoardServiceImpl();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//utf=8로 setting
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		//request로 uri 값 가져와서 string에 담기 
		String uri = request.getRequestURI();
		log.info(">>>uri"+uri);
		String path = uri.substring(uri.lastIndexOf("/")+1);
		log.info(">>>path"+path);
		
		//받아온 값을 path로 담아서 switch문 이용해서 case별로 코드작성
		switch(path) {
		case "register":
			destPage="/board/register.jsp";
			break;
		
		case "insert" :
			//db에 객체로 전달해서 저장하는 역할
			//jsp에서 입력한 값 가져오기 위해 파라미터 저장
			//jsp에서 가져온 파라미터 저장
			String title = request.getParameter("title");
			String writer = request.getParameter("writer");
			String content = request.getParameter("content");
		
			//객체생성
			BoardVO bvo = new BoardVO(title, writer,content);
			//insert 할거니까 isOk로 받자
			isOk = bsv.register(bvo);
			destPage = "page";
			break;
		
		case "list":
			try {
				log.info(">>> list page 이동");
				//객체를 담을 수 있는 list 생성
				List<BoardVO> list = new ArrayList<BoardVO>();
				list = bsv.list();
				log.info(">>> list" + list);
				
				//""안의 list는 임의로 이름 정해준 것. 아무거나 해도됨. jsp페이지에서 쓰려고
				//jsp에 뿌려주기 위한 구문 외워.
				request.setAttribute("list", list);
				log.info(">>> list(리스트페이지) 완료");
				destPage = "/board/list.jsp";
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			break;
		
		//-----------------------------페이지 추가
		case "page":
			try {
				//pageNo, qty 값을 정해줌
				int pageNo = 1;
				int qty = 10;
				
				//++++++++++++++++++++++검색 search 추가
				String type = "";
				String keyword="";
				
				//list.jsp에서 request로 받아온 pageNo와 qty의 값이 있으면 pageNo, qty 값 다시 셋팅되게
				if(request.getParameter("pageNo")!=null) {
					pageNo  = Integer.parseInt(request.getParameter("pageNo"));
					qty = Integer.parseInt(request.getParameter("qty"));
				}
				
				
				//값이 존재하면 type과 keyword를 PagingVO 객체에 전달함
				if(request.getParameter("type")!=null) {
					type = request.getParameter("type");
					keyword = request.getParameter("keyword");
					log.info(">>>> type : "+type + "keyword : "+keyword);
				}
				
				//객체 생성 DB에 전해줄 때 pageNo, qty 들고 가서 달라고 해야하니..?
				PagingVO pgvo = new PagingVO(pageNo,qty);
				
				//+++검색 생성자 추가 
				pgvo.setType(type);
				pgvo.setKeyword(keyword);
				log.info(">>>pgvo > " + pgvo);
				
				// 전체 페이지 개수 요청
				// db에 가서 전체 페이지 개수 확인 (몇 개가 등록되어 있는지 모르니까)
				// select count(bno) from board where bno>0;
				// 페이징 할 때는 ()빈거였다가 검색할때 pgvo추가함. 페이징은 그저 전체 데이터 확인. but
				// 검색은 검색 조건에 맞는 데이터의 개수를 가져와야 하기 대문에 검색 조건 포함한 pgvo 객체를 인자로 전달함
				int totCount = bsv.getTotal(pgvo); 
				log.info(">>> 전체 페이지 개수:"+ totCount);
				//list 객체를 만들어서 바로 연결 
				//db에서 PageList를 받아오려고 ,,, 화면에 뿌려주기 위해
				List<BoardVO> list = bsv.getPageList(pgvo);
				log.info(">>> list" + list.size());
				//페이징 핸들러 객체 생성 pgvo와 totCount(전체 페이지 개수)
				PagingHandler ph = new PagingHandler(pgvo, totCount);
				//request에 set해줌 ph 객체를 "pgh"라고
				request.setAttribute("pgh", ph);
				request.setAttribute("list", list);
				log.info("pageList 성공");
				destPage = "/board/list.jsp";
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		
			
			
		case "detail":
			//list.jsp에서 /brd/detail?bno=${bvo.bno}로 받아옴
			int bno = Integer.parseInt(request.getParameter("bno"));
			log.info(">>> bno > " + bno);
			//DB에서 객체로 받아올 거니까 미리 객체를 생성함.
			BoardVO b = new BoardVO();
			b = bsv.detail(bno);
			log.info("bvo >>>>"+b);			
			request.setAttribute("bvo2", b);
			
			log.info(">>> detail 성공");		
			destPage = "/board/detail.jsp";
			break;
			
		case "modify":
			//수정하는 정보 DB로 보내기만 함
			log.info(">>> modify 접근");		
			int bno2 = Integer.parseInt(request.getParameter("bno"));
			BoardVO b2 = new BoardVO();
			//수정하는데 왜 detail로 또 보내는가.....재활용?
			b2 = bsv.detail(bno2);
			request.setAttribute("bvo", b2);
			destPage="/board/modify.jsp";
			break;
		
		case "edit":
			//modify.jsp에서 수정한 값 받기 위함
			String title3 = request.getParameter("title");
			String content3 = request.getParameter("content");
			int bno3 = Integer.parseInt(request.getParameter("bno"));
			BoardVO bvo3 = new BoardVO(bno3, title3, content3);
			isOk = bsv.update(bvo3);			
			log.info(">>>update > "+(isOk>0?"success":"fail"));
			//수정하고나서 컨트롤러의 list로 가야함!!! 
			//list.jsp로 가면 빈 페이지만 띄워줌 (주의!!)
			destPage="page";
			break;
			
		case "delete" :
			//detail.jsp에서 get방식으로 받아옴
			//bno로 찾아서 db에서 삭제하면 되니까 bno만 가져가면 됨
			int bno4 = Integer.parseInt(request.getParameter("bno"));
			isOk = bsv.delete(bno4);
			destPage="/board/delete.jsp";
			break;
		
		}
		
		
		//destPage의 목적지로 request 객체를 보내겠다. 연결해주는 구문
		rdp = request.getRequestDispatcher(destPage);
		rdp.forward(request, response);
	
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

}
