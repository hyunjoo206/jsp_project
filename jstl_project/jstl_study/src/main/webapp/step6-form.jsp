<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- get방식 : 쿼리스트링을 달고 URL 상에서 이동하는 방식 (기본) -->
<!-- post방식 : 데이터를 숨겨서 가는 방식 - 보안상, 내용이 많을경우(url짧게)-->
	<form action="step6-action.jsp" method="post">
		주문자 <input type = "text" name="name"> <br>

		<input type="checkbox" name="fruits" value="딸기">딸기<br>
		<input type="checkbox" name="fruits" value="배">배<br>
		<input type="checkbox" name="fruits" value="사과">사과<br>
		<input type="checkbox" name="fruits" value="귤">귤<br>
		<input type="checkbox" name="fruits" value="포도">포도<br>
		<!-- name 값이 똑같아서 배열로 들어감 -->
		<input type="submit" value="주문">	
	</form>
</body>
</html>