<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
<meta charset="UTF-8">
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>회원정보 수정</h1><br>
	
	<form action="/mem/edit" method="post">		
		아이디 : <input type="text"  name="id" value="${ses.id}" readonly="readonly" class="form-control"><br>
		비밀번호 : <input type="text" name="password" value="${ses.password}" class="form-control"><br>
		email: <input type="text" name="email" value="${ses.email}" class="form-control"><br>
		나이 : <input type="text" name="age" value="${ses.age}" class="form-control"><br><br>
		
		<input type="hidden" name="reg_date" value="${ses.reg_date}">
		<input type="hidden" name="last_login" value="${ses.last_login}">
		
		<button type="submit" class="btn btn-outline-primary">수정완료</button>
		<button type="submit" class="btn btn-outline-primary">회원탈퇴</button>		
	</form>

<!-- 부트스트랩 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"
   integrity="sha384-U1DAWAznBHeqEIlVSCgzq+c9gqGAJn5c/t99JyeKa9xxaYpSvHU5awsuZVVFIhvj" crossorigin="anonymous">
</script>
</body>
</html>