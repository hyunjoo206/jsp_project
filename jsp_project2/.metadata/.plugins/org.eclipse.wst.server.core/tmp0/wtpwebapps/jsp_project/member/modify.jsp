<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>Modify Page</h1>

	<!-- 6개의 정보가 ses에 다 저장되어 있음
		근데 우리는 4개만 쓰고싶음 2개는 어떻게 처리?
		1번째 방법 - 
		2번째 방법 - reg, lastloin을 숨겨서(hidden) 가져가던지
		2번째 방법으로 함 
	 -->
	<form action="/mem/edit" method="post">		
		id: <input type="text"  name="id" value="${ses.id}" readonly="readonly"><br>
		pw: <input type="text" name="password" value="${ses.password}"><br>
		email: <input type="text" name="email" value="${ses.email}"><br>
		age: <input type="text" name="age" value="${ses.age}"><br><br>
		<!-- hidden으로 숨겨서 가져감 -->
		<input type="hidden" name="reg_date" value="${ses.reg_date}"><br>
		<input type="hidden" name="last_login" value="${ses.last_login}"><br>
		
		<button type="submit">modify</button>
		<button type="submit">회원탈퇴</button>		
	</form>
	
</body>
</html>