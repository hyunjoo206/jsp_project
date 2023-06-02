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
    private RequestDispatcher rdp; //경로를 줌 
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
	
		String uri = request.getRequestURI();
		log.info(">>>uri : " +uri);
		String path = uri.substring(uri.lastIndexOf("/")+1);
		
		log.info(">>>path : " +path);
		
		
		switch(path) {
		case "join" :		
			destPage="/member/join.jsp";
			break;
		case "register" :
			//join에서 입력한 정보를 받아서 db로 넘겨주는 역할만
			//객체로 전달해줘야 하니까 객체 생성
			//그러려면 변수에 각각 담는게 먼저
			//받아오려면 request.getParameter 사용
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			String phone = request.getParameter("phone");
			//age는 int로 db에 생성했기에 int로 형변환 필요
			int age = Integer.parseInt(request.getParameter("age"));
			
			//객체를 생성
			MemberVO mvo = new MemberVO(id, password, email, phone, age);
			
			//db로 보내서 등록만 해주는 거니까 db 구문으로는 insert만 해주는 것
			//insert, update, delete는 isOk로 받기
			
			//memberservice(msv)에 register라는 동작(포스버튼)을 만들어주기
			//근데 그 register는 mvo 객체로 가져가야 하니 ()안에 매개변수로 달고가줌
			isOk = msv.register(mvo);
			
			//isOk를 하는 이유는 사실상 여기서 값이 잘 전달되는지 확인하기 위해
			log.info(">>>register" + (isOk>0 ? "성공":"실패"));
			
			//destPage는 위에 구문이 다 끝나고 가야할 페이지
			//등록했으니까 다시 인덱스 화면으로 돌아가면 됨.
			
			//중간에 가입이 완료되었습니다 창을 띄우면 더 좋을듯.=> 어떻게? 물어보기	
			if(isOk==1) {
				//msg_join에 1이라는 값을 넣는다.
				request.setAttribute("msg_join", 1);
			}
			
			destPage ="/";		
			break;
		
		case "login" :
			//로그인 화면으로 이동
			destPage="/member/login.jsp";
			break;
			
		case "login_auth" :
			//실제 로그인이 일어나는 케이스. 
			//이미 register케이스로 등록은 해줬기때문에 db엔 회원가입 정보가 저장되어 있음
			//내가 login.jsp에서 받아온 id와 pw값을 가져와서 db에 주고, 
			//db에 id랑 pw 값이 같은 정보가 있는지 체크하기.
			
			try {
				//login 페이지에서 입력한 값을 가져와서 변수(id2)에 담기
				String id2 = request.getParameter("id");
				String password2 = request.getParameter("password");
				//객체로 전달해줘야 하니까 객체 생성
				MemberVO mvo2= new MemberVO(id2, password2);
				//log로 확인
				log.info("login 요청 >>>" +mvo2);
				//msv에 객체로 전달하고 객체로 리턴받을 거니까 "객체"에 저장하기
				MemberVO loginMvo =msv.login_auth(mvo2);
				//만약에 받아오는 loginMvo 객체가 null이 아니면(!= null)
				//그러니까 DB에 저장된 객체가 있으면, 
				if(loginMvo != null) {
					//session을 가져와서 "ses"라는 이름으로 
					//DB에서 가져온 loginMvo객체를 담는다
					//HttpSession은 타입인거..? 잘 모르겠음...흑
					HttpSession ses = request.getSession();
					ses.setAttribute("ses", loginMvo);
				}else {
					//저장된 객체가 없으면, 그니까 일치하는 id,pw값이 없으면
					//msg_login에 0이란 값을 넣는다.
					request.setAttribute("msg_login", 0);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//로그인이 완료됐으니까 인덱스화면으로 가면됨
			//인덱스 화면에서는 로그인이 되면 나타낼 화면을 따로 c:if로 표시해줄 거임
			destPage="/";
			break;
			
		case "logout" :
			//연결된 세션을 가져와서 권한을 끊어야함 => 사실상 가져와서 끊는것만 해도됨
			HttpSession ses = request.getSession();
			//근데 로그인했을 때 마지막 로그인 시간을 찍어주기 위해서 아래 구문을 하는거임
			//-------------------마지막 로그인 시간 찍기 ----------------
			//getAttribute 하면 object 형태로 리턴되기 때문에 형변환 해줘야 함
			MemberVO mvo2 = (MemberVO)ses.getAttribute("ses");
			//mvo2에서 getId로 id를 가져와서 id2에 담기
			String id2 = mvo2.getId();
			//id2를 가져왔으니 이걸 매개변수로 lastlogin이라는 기능(?) 연결체를 만들어서
			//거기에 id2를 담아서 갖고 가고, 다시 갖고 오는거. isOk는 확인하기 위함
			isOk = msv.lastlogin(id2);
			log.info(">>>logout"+(isOk>0?"성공":"실패"));
			//-------------------마지막 로그인 시간 찍기 ----------------
			//session 안에 있는 내용을 지워줌 
			//session의 모든 속성값을 제거해줌
			ses.invalidate();
			
			//로그아웃버튼 누르면 여기로 와서 다시 인덱스 화면으로 가게끔
			destPage="/";
			break;
		
		case "modify" :
			//그 다음 페이지는 수정하는 페이지
			destPage="/member/modify.jsp";
			break;
			
		case "edit" :
			//modify.jsp에서 수정한 값을 가져와서 객체로 보내고
			//입력받은(수정한) 값을 가져오려면 request로 변수에 담아야 하니
			//getParameter 안의 값은 꼭 ""안에 넣어주기..잊지말자
			//위에서 id2를 썼었기 때문에 id22로.. 3하면 아래에서 또 틀어지니까...
			try {
				String id22 = request.getParameter("id");
				String password2 = request.getParameter("password");
				String email2 = request.getParameter("email");
				String phone2 = request.getParameter("phone");
				//age는 int인거 잊지말자...제발...형변환 해줘...
				int age2 = Integer.parseInt(request.getParameter("age")) ;
				String reg_date2 = request.getParameter("reg_date");
				String last_login2 = request.getParameter("last_login");
				//객체 생성하기
				MemberVO mvo3 = new MemberVO(id22, password2, email2, phone2, age2, reg_date2, last_login2);
				//수정은 DB에 값 바꿔주고 다시 돌려줄 건 아니니까 isOk
				//DB에서 수정하는거니까 update 구문을 쓸거임
				isOk = msv.edit(mvo3);
				log.info(">>> edit 성공" + (isOk>0?"성공":"실패"));
				
				if(isOk>0) {
					//isOk가 0보다 크면, 그니까 db등록이 성공하면
					//login_auth 케이스로 보내서 session 값 덮어씌우기(login_auth에서하는거)
					//request로 "mvo"라는 이름을 지어서 거기에 mvo3 객체를 넣어줌
					request.setAttribute("mvo", mvo3);
					log.info(">>> session 변경완료");
					//완료되면 수정되었습니다 alert창 띄운다음에
					destPage="login_auth"; 					
				}else {
					request.setAttribute("msg_edit", 0);
					destPage="/member/modify.jsp";
				}
				//isOk가 성공하든 안 하든 login_auth로 가는것?
			} catch (Exception e) {
				e.printStackTrace();
			}			
			
			//다시 인덱스페이지로 가기
			destPage="/";
			break;
			
		case "remove" :
			//회원탈퇴 케이스 - 이미 로그인된 상태에서만 보이는 버튼 
			//지금 연결된 세션을 가져옴(로그인한 객체 가져오기)
			try {
				//세션 불러오기
				HttpSession ses2 = request.getSession();
				//ses라는 이름인거를 session에서 가져와서(object로 반환됨 형변환필)
				//형변환해서 mvo4라고 해주기 
				MemberVO reMvo = (MemberVO)ses2.getAttribute("ses");
				//세션에 있던걸 형변환한 객체인 reMvo에서 id 가져오기
				String delid = reMvo.getId();
				//db에 가서 지우기(delete)만 할거니까 isOk로 하는거고
				//msv에 remove라는 거 만들어주기(delid를 매개변수로 주는)
		
				//delid를 매개변수로 갖고 가서 remove라는 통로(?)를 만들어주기
				isOk = msv.remove(delid);
				
				//세션에 있는 객체를 끊어달라고 하기 -> 로그아웃
				ses2.invalidate();
				
				//회원삭제가 완료되면, 세션에 저장된 내역도 사라지고,로그아웃되면서 인덱스페이지로 감
				destPage = "/";
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "list" :
			try {
				//리스트는 회원목록 보여주는거
				//DB에 저장된 객체 각각을 모아서 전체를 뿌려줘야함
				//받아올 게 있음 그게. list형태.
				//그러려면 list를 써야함. list라는 이름의 list 객체 생성
				List <MemberVO> list = new ArrayList<MemberVO>();
				//list라는 이름의 보내주고 받는 통로 생성
				list = msv.showlist();
				//DB에서 꺼내와서 request객체에 넣어줘야 하니까
				//받아온 "list"라는 이름에 list객체를 넣어주기(등록해주기)
				request.setAttribute("list",list);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			destPage = "/member/list.jsp";
			break;
		}
		
		
		//외워야함
		rdp = request.getRequestDispatcher(destPage);
		rdp.forward(request, response);
		
	} //service끝나는 부분
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

}
