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
	<h1>회원목록</h1>
	<!-- 리스트를 화면에 나타내려면 표가 필요 -->
	<table border="1">
		<thead>
			<tr>
				<th>아이디</th>
				<th>email</th>
				<th>핸드폰번호</th>
				<th>나이</th>
			</tr>
		</thead>
		<tbody>
		<!-- list에 있는걸 하나하나 꺼내서 mvo객체에 담는다 -->
			<c:forEach items="${list}" var="mvo">
			<tr>
				<td>${mvo.id}</td>
				<td>${mvo.email}</td>
				<td>${mvo.phone}</td>
				<td>${mvo.age}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<a href="/"><button>뒤로</button></a>
</body>
</html>