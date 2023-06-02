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
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.MemberVO;

import service.MemberService;
import service.MemberServiceImpl;


@WebServlet("/mem/*")
// /mem/* controller라고 보내겠다는 의미 //일부러 member과 다르게 함
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    //log설정 
	//private static final Logger log = LoggerFactory.getLogger(클래스명);
	private static final Logger log = LoggerFactory.getLogger(MemberController.class);
	private RequestDispatcher rdp; //외워야함 (내장함수)
	private MemberService msv;
	private int isOk; 
	private String destPage;

    public MemberController() {
    	//msv라는 서비스(포스기)에 기능(버튼)을 추가해준 것 
    	msv = new MemberServiceImpl();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//characterEncoding 설정 / contentType / uri
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		//uri에 request로 URI값(/mem/join같은거)을 가져와서 담음
		String uri= request.getRequestURI();
		log.info(">>> uri : "+uri); // /mem/join
		// /mem/join : 요청에 대한 path만 남기기
		//path에 uri에서 / 뒤에 있는 글자만 떼서 담음
		String path = uri.substring(uri.lastIndexOf("/")+1);
		
		log.info(">>> path: "+path); 
		
		switch(path) {
		case "join":
			destPage = "/member/join.jsp"; //uri
			break;
		case "register":
			//join.jsp에서 입력한 값들을 저장해야 하니까 
			//객체를 담는 그릇(request)에 각각 입력한 정보를 담는 것!!
			String id = request.getParameter("id"); //괄호 안의 id는 input의 name과 같은거
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			//input으로 받는 값은 String인데 age는 int니까 형변환을 해줌
			int age = Integer.parseInt(request.getParameter("age"));
			
			
			//join에서 전송된 값을 가지고 위에서 각각 저장해줬으니까 
			// 만들어놓은 생성자를 가지고 mvo 객체를 생성함
			// 객체로 값을 db에 전달해줘야 하니까...? 여기서는 db에 insert할거임
			MemberVO mvo = new MemberVO(id, password, email, age);
			// 서비스에 register라는 걸 새로 만들어주고, 이게 객체를 주고받는 역할을 해줌.
			// 대신 우리는 지금 받아온 mvo값을 가지고 갈꺼니까 
			isOk = msv.register(mvo); //포스기에 버튼 등록?
			log.info(">>> JOIN > "+ (isOk>0 ? "success":"fail"));
			destPage = "/"; //index.jsp 페이지를 서버에서 설정해준 것(톰캣)
			break;
			
		case "login": //로그인 페이지로 이동
			log.info(">>> login page 이동");
			destPage = "/member/login.jsp";
			break;
			
		case "login_auth": //실제 로그인이 일어나는 케이스
			try {
				String id2 = request.getParameter("id");
				String password2 = request.getParameter("password");
				//jsp에서 보내는 name= password 값과 같아야 함
				MemberVO mvo2 = new MemberVO(id2, password2);
				//해당 id, password가 db상에 있는지 체크
				//해당 객체(사용자)를 가져와서 
				//해당 객체(사용자)를 세션에 담기
				log.info(">>> login 요청" + mvo2);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
				MemberVO loginMvo = msv.login(mvo2);
				//같은 정보가 있으면 객체를 리턴, 없으면 null이 리턴
				//객체가 있다면 세션에 저장
				if(loginMvo != null) {
					//세션을 가져오기. 연결된 세션이 없다면 새로 생성
					//String이나 int같은 데이터타입 
					HttpSession ses = request.getSession();
					ses.setAttribute("ses", loginMvo);
					//로그인 유지 시간 설정(초단위)(10분*60초)
					ses.setMaxInactiveInterval(10*60);
				}else {
					request.setAttribute("msg_login", 0);
				}
				destPage = "/";				
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "logout" :
			try {
				//세션 가져오기 (실제 연결된 세션)
				HttpSession ses = request.getSession();
				//마지막 로그인 시간 기록
				//현재 접속된 객체를 갖고오기 -> 일반 object 형태로 리턴되기때문에 형변환 해야 함
				MemberVO mvo2 = (MemberVO)ses.getAttribute("ses");
				//mvo2를 가져와서 id2에 담기
				String id2 = mvo2.getId();
				//현재 로그인된 id가 맞는지 확인해보기
				log.info(">>> login id : " +id2);
				//login정보(id)를 주고 마지막 로그인 시간 기록(update해야함)
				isOk = msv.lastLogin(id2);
				log.info(">>> logout > "+ (isOk>0 ? "success":"fail"));
				//권한 끊기
				ses.invalidate();
			} catch (Exception e) {
			
			}
			destPage ="/";
			break;
			
		case "modify" :
			//jsp로 보낼때
			log.info(">>> modify page 접근");
			destPage="/member/modify.jsp";
			break;
			
		case "edit" :
			try {				
				//객체 가져오기
				MemberVO mvo3 = new MemberVO(
						request.getParameter("id"),
						request.getParameter("password"),
						request.getParameter("email"),
						Integer.parseInt(request.getParameter("age")),
						request.getParameter("reg_date"),
						request.getParameter("last_login")
						);
				isOk = msv.modify(mvo3);
				
				log.info(">>> modify 성공, session 변경 : " + (isOk>0 ? "success":"fail"));
				if(isOk > 0) {
					
					//case1 - session을 이용해서 객체에 값을 담기
					//mvo = msv.login(mvo3); //case1.5 - modify에서 모든 정보를 가져오지 않았을 경우
//					HttpSession ses = request.getSession();
//					ses.setAttribute("ses", mvo3);
					
//					case2 login_auth case로 mvo3 객체 가지고 보내기
					request.setAttribute("mvo", mvo3);
					log.info("session 변경완료");
				}
//				destPage="/"; //case1의 경우
				destPage="login_auth"; //case2의 경우
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "remove":
			//ses 객체에서 login 정보 가져오기
			try {
				//기존에 연결된 세션을 가져옴.(로그인 한 객체를 가져옴)
				HttpSession ses = request.getSession();
				//session에서 값을 가져올 때는 리턴값이 object이기 때문에 형변환을 해야 MemberVO에 담길 수 있음!
				MemberVO reMvo = (MemberVO)ses.getAttribute("ses");
				String delid = reMvo.getId();
				//서비스한테 지워달라고 부탁
				isOk = msv.remove(delid);
				log.info(">>> remove >" + (isOk>0? "success":"fail"));
				//세션에 있는 객체를 끊어주셈 - 로그아웃
				ses.invalidate();
				log.info(">>> logout(세션삭제) 완료");
				destPage = "/";
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "list":
			//list는 가져다줄 게 없음! 파라미터 없어 걍 ㅑ없어
			log.info(">>> list page 이동");
			//객체를 담을 수 있는 list를 만들기 
			List<MemberVO> list = new ArrayList<MemberVO>();			
			list = msv.list(); //서비스한테 list 달라고 해서 넣기.
			//받아온 리스트를 보내주기
			request.setAttribute("list", list);
			log.info(">>> list(리스트페이지) 완료");
			//받아오면 리퀘스트 객체에 담아서 list 페이지로 전달해주셈.
			destPage = "/member/list.jsp";
			break;
		
		
		}
		
		//외워야 함 destPage로 갈 수 있도록 (String 으로 받은 주소로 갈 수 있도록 하는 구문)
		rdp = request.getRequestDispatcher(destPage);
		rdp.forward(request, response);
		
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		service(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
