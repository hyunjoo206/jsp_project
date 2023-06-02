<%@ page import="model.PersonVO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSTL choose 연습</title>
</head>
<body>
	<!-- model 안에 PersonVO class 생성 멤버변수는 name, age 만 추가
	생성자, getter&setter만 추가-->
	
	<!-- EL 방식으로 name, age를 출력 -->
	<%
		PersonVO pvo = new PersonVO("홍길동",18);
	//객체생성 후 
		request.setAttribute("pvo", pvo);
	//request로 setAttribute로 이름지정, 객체이름
	%>
	
	<!-- requestScope.지정한이름.거기서name -->
	${requestScope.pvo.name} <br>
	${requestScope.pvo.age} <br>
	
	<hr>
	
	<!-- 나이가 20살 이상이면 성인입니다 -->
	<c:if test ="${pvo.age>=20}">
		성인입니다.	
	</c:if>
	
	<hr>
	
	<!--  다중조건을 사용할 경우 choose, when, otherwise -->
	<c:choose>
		<c:when test="${pvo.age >= 20}">
		성인입니다. <br>		
		</c:when>
		
		<c:when test="${pvo.age >= 10}">
		청소년입니다. <br>		
		</c:when>
		
		<c:otherwise>
		유아입니다. <br>		
		</c:otherwise>
	</c:choose>
	
	<!-- step5-form.jsp : 이름과 나이 입력받기 step5-action.jsp로 전송 -->
	<!-- step5-action.jsp : 값을 받아 이름 출력하고, 나이 출력 후 c:choose -->
	<!-- 나이가 20 이상이면 성인, 15세 이상이면 청소년, 5세 이상이면 어린이, 1살 이상이면 유아,
		나머지는 아직 태어나지 않았습니다. 
	-->
	
</body>
</html>