<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	//post 방식의 한글처리 (인코딩 설정)
	request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	
	<%--
	과일1 : ${paramValues.fruits[0] }<br>
	과일2 : ${paramValues.fruits[1] }<br>
	과일3 : ${paramValues.fruits[2] }<br>
	과일4 : ${paramValues.fruits[3] }<br>
	과일5 : ${paramValues.fruits[4] }<br>
	
		String fruits[] = {};
		request.setAttribute("f", fruits);
	--%>
	
	주문자명 : ${param.name }<br>
	<hr>
	<c:forEach items="${paramValues.fruits}" var="fname" varStatus="i">
			<!-- 배열을 받으면 됨 / var는 임시변수- 값 나타내려고 / varStatus는  --> 
		${i.count }. ${fname} <br>
	</c:forEach>
	주문하신 메뉴 나왔습니다.
	


	
	
</body>
</html>