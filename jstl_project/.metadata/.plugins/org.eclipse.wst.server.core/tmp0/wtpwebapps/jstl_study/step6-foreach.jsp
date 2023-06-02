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
	<% //자바코드 표시
		String food[] = {"사과","배","귤","감","바나나"};
		request.setAttribute("f",food);
		//담을때 setAttribute
	%>
	<!-- forEach : 반복문 
		items : 대상배열, collection(list, set, map)
		var : 요소를 저장할 변수
		varStatus : 변수  => 변수.count(개수), 변수.index(주소)
	-->
	<c:forEach items="${requestScope.f}" var="fname" varStatus="order">
		count : ${order.count } <br>
		index : ${order.index } <br>
		${fname} <br>
	</c:forEach>
	
	<!-- step6-form.jsp, step6-action.jsp
		form 태그 안에 check box 생성
		주문자 : 이름
		체크박스의 내용물을 선택하여 action으로 전송
		
		action에서 c:forEach로 체크박스의 값을 배열로 받아 화면에 반복찍기 
	 -->
	
</body>
</html>