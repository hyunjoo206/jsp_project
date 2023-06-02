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

import com.mysql.cj.xdevapi.JsonParser;

import domain.CommentVO;
import service.CommentService;
import service.CommentServiceImpl;

@WebServlet("/cmt/*")
public class CommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(CommentController.class);
	//목적지 주소로 보내는 destPage를 쓰지 않음.
	//비동기 통신으로 요청이 온 곳으로 돌려보냄
	private int isOk;
	private CommentService csv;
	
	
    public CommentController() {
    	csv = new CommentServiceImpl();
    }


	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//contentType은 사용하지 않음.
		
		//brd/list, brd/register brd/modify?bno=1 => 이전엔 이런 식으로 but,,여기선
		//cmt/post/1 => 이런 식으로 가져올거임 /cmt/modify/1 => rest API방식임
		// modify만 가져오려면
		String uri = request.getRequestURI();
		log.info(uri);
		
		// modify/1, post
		String pathUri =  uri.substring("/cmt/".length());
		String path = pathUri; //post만 남음
		String pathVar = "";
		if(pathUri.contains("/")) {
			path = pathUri.substring(0, pathUri.lastIndexOf("/")); // => /cmt/modify
			pathVar = pathUri.substring(pathUri.lastIndexOf("/")+1); // => 1만 뽑아낸거
		}
		
		log.info(pathUri);
		log.info(path);
		log.info(pathVar);
		
		
		//1을 가져오기 위한 구문 (/뒤에 숫자)
			
		switch(path) {
		case "post":
			try {
				//js에서 보낸 데이터를 StringBuffer로 읽어들이는 작업
				//StringBuffer는 가변적.. 바꿀 수 있는 String
				StringBuffer sb = new StringBuffer();
				String line="";
				//BufferedReader => int, String같이 file형태만 받는 타입임
				//request에 cmtData가 들어있음
				//getReader 는 body 부분을 읽어들여서 br에 넣음
				BufferedReader br = request.getReader(); 
				//request 안에는 프로미스 형태로 들어있고, 
				//readLine이 한 줄씩 읽겠다
				while((line = br.readLine()) != null) { //값이 남아 있다면
					//sb에 한 줄씩 추가한다 => StringBuffer이 바꿀 수 있는 거라서 가능..(?)
					sb.append(line);
				}
				log.info(">>>sb :" +sb.toString());
				
				//객체로 생성 
				// JSON <-> text 변경하는 게 parser (라이브러리 필요)
				//JSONParser가 하나의 타입인거
				JSONParser parser = new JSONParser();
				//JSON 담으려고 객체 만드는거... JSON객체로 변경하는
				//sb.toString 이 여러줄 들어있는 걸 String으로 바꾸는 거
				//JSONObject 형태로 바꿔줌
				JSONObject jsonObj = (JSONObject)parser.parse(sb.toString());
				
				//객체를 toString으로 바꾸고, integer parseInt해서 int에 넣는거임.
				int bno = Integer.parseInt(jsonObj.get("bno").toString());
				String writer = jsonObj.get("writer").toString();
				String content = jsonObj.get("content").toString();
				
				//DB에 넣는 과정
				CommentVO cvo = new CommentVO(bno, writer, content);
				
				isOk = csv.post(cvo);
				log.info(">>>>post"+(isOk>0?"ok":"fail"));
				//결과 전송
				
				//response 하기 위해서 
				PrintWriter out = response.getWriter(); //writer객체를 가져와서 응답객체로 보낼거임
				out.print(isOk);
				
				
			}catch(Exception e) {
				log.info(">>>> comment post > error");
				e.printStackTrace();
			}
			break;
			
		case "list" :
			int bno = Integer.parseInt(pathVar); 
			List<CommentVO> list = csv.getList(bno);
			log.info(">>>> comment list > DB ok");
			try {
				//JSON 형태로 변환해서 printWriter 
				//json object를 담을 수 있는 배열을 만든것
				JSONObject[] jsonObjArr = new JSONObject[list.size()];
				//배열을 담는 리스트를 만들기
				JSONArray jsonObjList = new JSONArray();
				for(int i=0; i<list.size(); i++) {
					jsonObjArr[i] = new JSONObject(); // key : value
					jsonObjArr[i].put("cno", list.get(i).getCno()); //list에 get으로 i번째 배열을 가져와서 cno값을 가져와서 배열에 넣음 
					jsonObjArr[i].put("bno", list.get(i).getBno());
					jsonObjArr[i].put("writer", list.get(i).getWriter());
					jsonObjArr[i].put("content", list.get(i).getContent());
					jsonObjArr[i].put("reg_date", list.get(i).getReg_date());
					//하나씩 뽑은 데이터를 JsonObjList에 넣기
					jsonObjList.add(jsonObjArr[i]);
				}
				
				//스트링 형태로 만들어서 보내기
				String jsonData = jsonObjList.toJSONString();
				log.info(">>> jsonObjList"+jsonObjList); 
				
				//보낼때 쓰는것      js의 resp
				PrintWriter out = response.getWriter();
				log.info(">>> jsonObjList"+jsonData); 

				out.print(jsonData);
				
			} catch (Exception e) {
				log.info(">>>> comment list > error");
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
		}
		
	}
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

}