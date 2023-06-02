<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품리스트 보기</title>
</head>
<body>
<h1>Product LIst Page</h1>
<table border="1">
	<thead>
		<tr>
			<th>번호(pno)</th>
			<th>상품명(pname)</th>
			<th>등록일(regDate)</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${list}" var="pvo">
			<tr>
				<td>${pvo.pno }</td>
				<td><a href="detail.pd?pno=${pvo.pno }">${pvo.pname }</a></td>
				<td>${pvo.regdate }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<br>
<a href="register.pd"><button type="button">상품등록</button></a>
<a href="index.jsp"><button type="button">index</button></a>
</body>
</html>