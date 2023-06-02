<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <!-- c:if를 쓰고 싶으면 taglib 달아줘야 함 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Index Page</h1>
	
	<!-- ne는 비교 표현식 not equal의 줄임말 
		id가 null이 아니라면 (id에 값이 있다면)
		로그인 안 하면 보이는 부분
	 -->	 	 
	 <c:if test ="${ses.id eq null}">
	 <a href="/mem/login"><button>로그인</button></a>
	 <a href="/mem/join"><button>회원가입</button></a>
	 </c:if>
	 
	 <!-- not equal 의 줄임말 ne 
	 	결국 ses.id가 null이 아니라면 => id에 값이 있다면
	 	로그인 하면 보이는 부분
	  -->
	 <c:if test="${ses.id ne null}">
	 <a href="/mem/logout"><button>로그아웃</button></a>
	 <a href="/mem/modify"><button>회원정보수정</button></a>
	 <a href="/mem/remove"><button>회원탈퇴</button></a><br>
	 <a href="/mem/list"><button>회원리스트</button></a>
	 <a href="/brd/register"><button>게시글작성</button></a>
	 </c:if>
	
	<!-- 로그인 하든 안 하든 보이는 부분 -->
	 <a href="/brd/page"><button>게시글목록</button></a>
	 
	 <br>
	 <script type="text/javascript">
	 const msg_join =`<c:out value="${msg_join}"/>`
	 if(msg_join === '1'){
		 alert('가입이 완료되었습니다.')
	 }
	 
	 const msg_login = `<c:out value="${msg_login}" />`;
	 console.log(msg_login);
	 if(msg_login === '0'){
		 alert('로그인 정보가 올바르지 않습니다.')
	 }
	 
	 </script>
	 
</body>
</html>