<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <!-- taglib 써야 c:forEach 적용됨  -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>List Page</h1>
	<table border="1">
		<thead>
			<tr>
				<th>id</th>
				<th>email</th>
				<th>age</th>
			</tr>
		</thead>
		<tbody>
			<!-- c:forEach 구문 => list에 있는 걸 하나하나 꺼내서 mvo 객체에 담는다 -->
			<c:forEach items="${list}" var="mvo">		
				<tr>		
					<td>${mvo.id}</td>
					<td>${mvo.email}</td>
					<td>${mvo.age}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<a href="/"><button>index</button></a>
</body>
</html>