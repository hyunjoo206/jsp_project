<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>제품 수정 페이지</title>
</head>
<body>
<h1>Product Modify Page</h1> 
	<br>
	<form action="edit.pd" method="post">
		<input type="hidden" name="pno" value="${pvo.pno}">
		pno : <input type="text" name="pno" value="${pvo.pno}" disabled="disabled"><br>
		name : <input type="text" name="pname" value="${pvo.pname}"><br>
		price : <input type="text" name="price" value="${pvo.price}"><br>
		regdate : <input type="text" name="regdate" value="${pvo.regdate}" disabled="disabled"><br>
		madeby : <input type="text" name="madeby" value="${pvo.madeby}"><br>
		
		<button type="submit">modify</button>
	</form>
</body>
</html>