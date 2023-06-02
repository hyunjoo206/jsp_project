<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>회원가입 페이지</h1>
	<!-- action이 도착지, method가 보내는 방식 
		도착하는 곳은 controller, 근데 이 정보를 가지고 어디로? db로 보내기만 할거.
		그러니까 register라는 case를 만들어주고, 컨트롤러에서 register로 받자.
	-->
	
	<form action="/mem/register" method="post">
		아이디 : <input type="text" name="id"><br>
		비밀번호 : <input type="password" name="password"><br>
		e-mail : <input type="text" name="email" 
			placeholder="abc@naver.com"><br>
		전화번호 : <input type="text" name="phone"><br>
		나이 : <input type="text" name="age">	<br>
		<br>
		<button type="submit">가입하기</button>
	</form>
	
	
</body>
</html>