<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>JOIN Page</h1>	
	<form action="/mem/register" method="post">
		id: <input type="text" name="id" placeholder="ID"><br>
		pw: <input type="password" name="password"><br>
		email: <input type="email" name="email" placeholder="abc@example.com"><br>
		age: <input type="text" name="age"><br><br>
		<button type="submit">가입완료</button>	
	</form>
	
</body>
</html>