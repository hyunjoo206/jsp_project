<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>제품 상세 페이지</title>
</head>
<body>
	<table border="1">
			<tr>
				<th>번호</th>
				<td>${pvo.pno}</td>		
			</tr>
			<tr>
				<th>제목</th>
				<td>${pvo.pname}</td>		
			</tr>
			<tr>
				<th>가격</th>
				<td>${pvo.price}</td>		
			</tr>
			<tr>
				<th>등록일자</th>
				<td>${pvo.regdate}</td>		
			</tr>
			<tr>
				<th>세부내용</th>
				<td>${pvo.madeby}</td>		
			</tr>

	</table>
	<a href="modify.pd?pno=${pvo.pno}"><button type="button">modify</button></a>
	<a href="remove.pd?pno=${pvo.pno}"><button type="button">delete</button></a>
</body>
</html>