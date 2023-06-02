package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.CommentVO;
import service.CommentService;
import service.CommentServiceImpl;

@WebServlet("/cmt/*")
public class CommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(CommentController.class);
	private int isOk;
	private CommentService csv;
	
	//컨트롤러랑 서비스 연결
    public CommentController() {
    	csv = new CommentServiceImpl();
    }
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//contentType은 사용x
		
		//cmt/post/1 => 이런 식으로 가져올 거임. rest API방식
		//post만 가져오려면 아래와 같이 잘라주기
		
		//request 객체에 담아서 getRequestURI => uri를 받아옴
		String uri = request.getRequestURI();
		log.info(uri);
		
		String pathUri = uri.substring("/cmt/".length());
		String path = pathUri; //post만 남음
		String pathVar =""; //1만 남음
		if(pathUri.contains("/")) {
			path = pathUri.substring(0, pathUri.lastIndexOf("/")); // /cmt/post 뽑아냄
			pathVar = pathUri.substring(pathUri.lastIndexOf("/")+1);// 1만 뽑아냄
		}
		
		log.info(pathUri);
		log.info(path);
		log.info(pathVar);
		
		
		switch(path) { //switch 시작
		
		case "post" :
			try {
				//js에서 보낸 데이터를 받기위해 StringBuffer 타입의 sb를 미리 선언
				//String은 변경불가인데 StringBuffer는 변경이나 추가 가능
				StringBuffer sb =new StringBuffer();
				
				//비어있는 String 타입 line 선언
				String line =""; 
				
				//request객체에서 getReader로 body부분을 읽어들여서 BufferedReader 타입의 br에 넣어줌
				//BufferedReader는 file 형태만 받는 타입
				BufferedReader br = request.getReader();
				
				//readLine으로 br(파일형태)을 한 줄씩 읽어들여서 line에 넣는데, 
				//읽어들일 값이 있으면 (null이 아니면) 
				while((line = br.readLine()) != null) {
					//sb(스트링버퍼형태)에 append를 이용해서 line 값을 추가
					sb.append(line);
				}
				
				//그럼 sb(스트링버퍼타입)에 line 한 줄 한 줄이 다 들어가있는 거임
				
				log.info(">>>sb:"+sb.toString());
				
				//JSONParser라는 형태의 parser를 선언
				//JSONParser 객체는 JSON 데이터를 파싱하는 java 클래스임
				//JSONParser에서 parse라는 메서드를 사용할 수 있음
				JSONParser parser = new JSONParser();
				//**************************************사실 잘 이해안감
				//JSONObject 형태의 jsonObj에
				//sb(StringBuffer형태)를 String형태로 바꿔서 parsing해서 JSONObject형태로 바꿔줌
				// JsonParser 객체를 사용하여 JSON 데이터를 파싱하면, 
				// Java에서 JSON 데이터를 쉽게 가공하거나 활용할 수 있어서 하는거 같음
				
				//parser.parse로 sb.toString해서 만든 고정된 String 내용을 한 줄씩 parse해서
				//jsonObj에 넣어주는 것. (객체로. JSON형태로)
				JSONObject jsonObj = (JSONObject)parser.parse(sb.toString());
				
				//객체를 toString으로 바꾸고, 형변환 필요하면 해줌
				int bno = Integer.parseInt(jsonObj.get("bno").toString());
				String writer = jsonObj.get("writer").toString();
				String content = jsonObj.get("content").toString();
				
				//DB에 넣는 과정
				CommentVO cvo = new CommentVO(bno, writer,content);
				isOk=csv.post(cvo);
				log.info(">>>>post"+(isOk>0?"ok":"fail"));
				
				//response 하기 위한 과정
				PrintWriter out = response.getWriter();
				out.print(isOk);
				
				
			} catch (Exception e) {
				log.info(">>>>comment post > error");
				e.printStackTrace();
			}
			break;
		
		case "list" :
			//pathVar에 뭐가 담겨있는데? 숫자...?!!!bno!!
			int bno = Integer.parseInt(pathVar);
			//list 담을 객체 생성하고 가져온 값을 바로 담아줌
			List<CommentVO> list = csv.getList(bno);
			log.info(">>> comment list > DB ok");
			try {
				//json objext를 담을 수 있는 배열을 생성
				JSONObject[] jsonObjArr = new JSONObject[list.size()];
				//배열 담는 리스트 생성
				JSONArray jsonObjList = new JSONArray();
				for(int i=0; i<list.size(); i++) {
					jsonObjArr[i] = new JSONObject();
					jsonObjArr[i].put("cno", list.get(i).getCno()); //list에 get으로 i번째 배열을 가져와서 cno값을 가져와서 배열에 넣음 
					jsonObjArr[i].put("bno", list.get(i).getBno());
					jsonObjArr[i].put("writer", list.get(i).getWriter());
					jsonObjArr[i].put("content", list.get(i).getContent());
					jsonObjArr[i].put("reg_date", list.get(i).getReg_date());
					//하나씩 뽑은 데이터를 JsonObjList에 넣기
					jsonObjList.add(jsonObjArr[i]);
				}
				
				//jsonObjList를 string으로 만들어서 보냄. 이게 가능? toJSONString이라는 게 있는 거?
				//toJSONString은 메서드 - 만들어둔 JSON객체를 문자열 데이터로 변경
				String jsonData = jsonObjList.toJSONString();
				log.info(">>> jsonObjList" + jsonObjList);
				
				//보낼때 쓰는 것 js의 resp
				PrintWriter out = response.getWriter();
				log.info(">>> jsonObjList" + jsonData);
				
				out.print(jsonData);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "remove":
			try {
				// 쿼리스트링 ?로 보냈기 때문에 바로 int 값으로 받아도 됨
				int cno = Integer.parseInt(request.getParameter("cnoVal"));
				log.info(">>> cno >"+cno);
				//서비스에 remove 생성
				isOk =csv.remove(cno); 
				log.info(">>> remove >"+(isOk>0?"성공":"실패"));
				PrintWriter out = response.getWriter();
				out.print(isOk);
			} catch (Exception e) {
				log.info("remove error");
				e.printStackTrace();
			}
			break;
			
		case "modify":
			try {
				//가져온 json은 파일로 값이 오니까 StringBuffer로 받아야 함
				StringBuffer sb = new StringBuffer();
				//line은 널값으로
				String line="";
				//해당하는 객체를 getReader가 읽어서 br에 담음
				BufferedReader br = request.getReader();
				//br에서 값을 읽어서 line에 넣기. => 그 값이 null이 아니면(값이 있다면)
				while((line=br.readLine()) != null) {
					//sb에 line 값을 담기(append하기)
					sb.append(line);
				}
				log.info(">>>sb :" +sb.toString());
				
				//객체로 생성
				JSONParser parser = new JSONParser();
				//parser를 이용해서 parse 메서드 호출해서 sb의 toString 값을 받아서 object로 형변환
				JSONObject jsonObj = (JSONObject)parser.parse(sb.toString());
				
				//객체를 toString으로 바꾸고 integer parseInt로 int에 넣기
				int cno = Integer.parseInt(jsonObj.get("cno").toString());
				//나머지는 그냥 string이니까 obj에서 get으로 writer 값을 toString으로 가져오기
				// 사실 writer은 굳이 안 해줘도 됨. 어차피 등록되어 있기 때문.
				String writer = jsonObj.get("writer").toString();
				String content = jsonObj.get("content").toString();
				
				//DB에 넣는 과정
				//앞에 commentVO.java에서 생성자 만든 순서로!!!  
				CommentVO cvo = new CommentVO(writer, content, cno);
				log.info(">>>>>>>>cvo확인 "+cvo);
				//csv한테 cvo 주고 edit 만들어주기
				isOk = csv.edit(cvo);
				log.info(">>>edit"+(isOk>0?"성공":"실패"));
				
				//결과전송
				//printWriter가 뭐지>..?? 찾아보기
				PrintWriter out = response.getWriter();
				out.print(isOk);
				
			} catch (Exception e) {
				log.info("modify error");
				e.printStackTrace();
			}
			break;
		
			
			
		}//switch 끝
		
		
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

}
