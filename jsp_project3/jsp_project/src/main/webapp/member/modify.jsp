<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>회원정보 수정</h1>
	<form action="/mem/edit" method="post">
	<!-- value는 화면에 이미 있는 값을 나타내고 싶어서 
	(보통 수정페이지에 원래 있는거 있으니까) -->
	아이디 : <input type="text" name="id" value=${ses.id} readonly="readonly"><br>
	비밀번호 : <input type="password" name="password" value=${ses.password}><br>
	email : <input type="email" name="email" value=${ses.email}><br>
	핸드폰번호 : <input type="phone" name="phone" value=${ses.phone}><br>
	나이: <input type="age" name="age" value=${ses.age}><br>
	<!-- hidden이면 안 보이니까 굳이 value를 안 줘도 되지 않을까..? -->
	<input type="hidden" name="reg_date"><br> 
	<input type="hidden" name="last_login"><br>
	
	<button type="submit">수정하기</button>
	<a href="/mem/remove"><button type="submit">회원탈퇴</button></a>
	</form>
	

</body>
</html>