<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
<meta charset="UTF-8">
<title>Index</title>
</head>
<body>
<h1>Index Page</h1><br>

<c:if test="${ses.id ne null}">
	<div class="container" >
	${ses.id}님이 login 하였습니다. <br>
	${ses.id}님은 ${ses.age}세입니다. <br>
	계정생성일 : ${ses.reg_date}<br>
	마지막 접속 : ${ses.last_login}<br><br>
	</div>

	<a href="/mem/modify"><button class="btn btn-outline-primary">회원정보 수정</button></a>
	<a href="/mem/remove"><button class="btn btn-outline-primary">회원탈퇴</button></a>
	<a href="/mem/logout"><button class="btn btn-outline-primary">로그아웃</button></a>
	<a href="/mem/list"><button class="btn btn-outline-primary">회원리스트</button></a>
	<a href="/brd/register"><button class="btn btn-outline-primary">게시글 작성</button></a>
	<a href="/brd/page"><button class="btn btn-outline-primary">게시글 보기</button></a>
</c:if>



<c:if test="${ses.id eq null }">
<a href="/mem/join"><button class="btn btn-outline-primary">회원가입</button></a>
<a href="/mem/login"><button class="btn btn-outline-primary">로그인</button></a>
</c:if>

<script type="text/javascript">
const msg_login = `<c:out value="${msg_login}" />`;
if(msg_login === '0'){
	alert('로그인 정보가 올바르지 않습니다.');
}
</script>


<!-- 부트스트랩 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"
   integrity="sha384-U1DAWAznBHeqEIlVSCgzq+c9gqGAJn5c/t99JyeKa9xxaYpSvHU5awsuZVVFIhvj" crossorigin="anonymous">
</script>

</body>
</html>