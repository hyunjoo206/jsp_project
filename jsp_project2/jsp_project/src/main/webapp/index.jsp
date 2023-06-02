<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- c:if 쓰려면 아래 구문 첨부해야함! -->
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Index</title>
</head>
<body>	
	<h1>Index Page</h1>
			<!-- ne는 비교 표현식 not equal의 줄임말 -->
			<!-- id가 null이 아니라면( = id에 값이 있다면) -->	
			<!-- session이 무엇인가...어디서 정의를 해주었나..컨트롤러에 login_auth에서 정의해줌 -->	
			<!-- session은 객체를 저장해놓는 공간...(request, session) 객체를 담는 그릇이라 생각하자
				request : 목적지 주소에서만 사용 가능
				session : 모든 jsp에서 사용가능
			 -->			
	<c:if test="${ses.id ne null}">
		${ses.id }님이 login 하였습니다. <br>
		${ses.id}님은 ${ses.age}살입니다.<br><br>
		계정생성일 : ${ses.reg_date }<br>
		마지막접속 : ${ses.last_login }<br><br>
		<br>
		<a href="/mem/modify"><button>회원정보수정</button></a>
		<a href="/mem/remove"><button>회원탈퇴</button></a>
		<a href="/mem/logout"><button>로그아웃</button></a>
		<a href="/mem/list"><button>회원list</button></a>	
		<br>
		<a href="/brd/register"><button>게시글 작성</button></a>
	</c:if>
	<!-- brd /page로 바꿔주기 -->
	<a href="/brd/page"><button>게시글 보기</button></a>
		
	
	<c:if test="${ses.id eq null}"> 
	<a href="/mem/join"><button>join</button></a>
	<a href="/mem/login"><button>login</button></a>
	</c:if>
	
	
	
	<br><br>
	
	<script type="text/javascript">
	const msg_login = `<c:out value="${msg_login}" />`;
	console.log(msg_login);
	if(msg_login === '0'){
		alert('로그인 정보가 올바르지 않습니다.')
	}
	
	</script>
	
</body>
</html>