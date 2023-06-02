<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>EL param 연습4</title>
</head>
<body>
<!-- EL 방식은 null 값은 출력되지 않음. 그냥 비어있음 -->
	메뉴1 : ${paramValues.food[0] }<br>
	메뉴2 : ${paramValues.food[1] }<br>
</body>
</html>