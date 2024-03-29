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

public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final Logger log = LoggerFactory.getLogger(MemberController.class);
	private RequestDispatcher rdp;
	private MemberService msv;
	private int isOk;
	private String destPage;
	
    public MemberController() {
    	msv = new MemberServiceImpl();
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		String uri = request.getRequestURI();
		log.info(">>> uri : "+uri);
		// /mem/join : 요청에 대한 path만 남기기
		String path = uri.substring(uri.lastIndexOf("/")+1);
		log.info(">>> path : "+path);
		
		switch(path) {
		
		case "join":
			destPage="/member/join.jsp";
			break;
			
		case "register":
			//jsp 에서 가져온 파라미터 저장
			try {
				String id = request.getParameter("id");
				String password = request.getParameter("password");
				String email = request.getParameter("email");
				int age = Integer.parseInt(request.getParameter("age"));
				
				// 파라미터로 mvo 객체 생성
				MemberVO mvo = new MemberVO(id, password, email, age);
				
				isOk = msv.register(mvo);
				log.info(">>> JOIN > "+ (isOk>0 ? "success" :"fail"));
				destPage = "/";
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		
		case "login" :
			destPage="/member/login.jsp";
			break;
			
		case "login_auth" :
			try {
				//login.jsp에서 사용자가 입력한 값을 request로 받아오는 것
				String id = request.getParameter("id");
				String password = request.getParameter("password");
				//mvo2에는 사용자가 입력한 id,pw가 들어있음
				MemberVO mvo = new MemberVO(id, password);
				//mvo2 객체를 DB에 전달(login)해서 가져온 값을 loginMvo에 담음
				MemberVO loginMvo = msv.login(mvo);
				
				if(loginMvo != null) { //DB에 일치하는 정보가 있어서 값이 loginMvo에 담기면 로그인됨
					//session은 어디서든 쓸 수 있음
					HttpSession ses = request.getSession();
					ses.setAttribute("ses", loginMvo);
				}else {
					request.setAttribute("msg_login", 0);
				}
				destPage="/"; //로그인 다 되면 다시 index페이지로 => jsp에서 c:if로 로그인 된 상태 보이게 
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		
		case "logout" :
			try {
				HttpSession ses = request.getSession();
				// 이전 로그인 시 세션에서 ses 라는 이름으로 저장된 속성을 가져와서 mvo2에 담음
				MemberVO mvo = (MemberVO)ses.getAttribute("ses");
				String id = mvo.getId();
				log.info(">>> login id : " + id);
				//로그인 정보를(id)를 주고 마지막 로그인 시간 기록
				isOk = msv.lastLogin(id);
				log.info(">>> logout > "+ (isOk>0 ? "success":"fail"));
				//권한 끊기
				ses.invalidate();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			destPage="/";
			break;
		
		case "modify" :
			//jsp로 보낼때
			log.info(">>> modify page 접근");
			destPage="/member/modify.jsp";
			break;
			
		case "edit" :
			try {				
				MemberVO mvo = new MemberVO(
					request.getParameter("id"),
					request.getParameter("password"),
					request.getParameter("email"),
					Integer.parseInt(request.getParameter("age")),
					request.getParameter("reg_date"),
					request.getParameter("last_login")
					);
				isOk = msv.modify(mvo);
				
				log.info(">>> modify :" + (isOk>0 ? "성공":"실패"));
				if(isOk > 0) {
					request.setAttribute("mvo", mvo);
					log.info("session changed");
				}
//				destPage="/"; //case1의 경우
				destPage="login_auth"; //case2의 경우
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "remove":
			try {
				//기존에 연결된 세션을 가져옴.(로그인 한 객체를 가져옴)
				HttpSession ses = request.getSession();
				//session에서 값을 가져올 때는 리턴값이 object이기 때문에 형변환
				MemberVO reMvo = (MemberVO)ses.getAttribute("ses");
				String delid = reMvo.getId();
				//서비스한테 지워달라고 부탁
				isOk = msv.remove(delid);
				log.info(">>> remove >" + (isOk>0? "success":"fail"));
				ses.invalidate();
				log.info(">>> logout(세션삭제) 완료");
				destPage = "/";
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "list":
			//list는 가져다줄 게 없음!
			log.info(">>> list page 이동");
			//객체를 담을 수 있는 list를 만들기 
			List<MemberVO> list = new ArrayList<MemberVO>();			
			list = msv.list();
			request.setAttribute("list", list);
			log.info(">>> list(리스트페이지) 완료");
			destPage = "/member/list.jsp";
			break;
			
		}//switch 끝
		
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
