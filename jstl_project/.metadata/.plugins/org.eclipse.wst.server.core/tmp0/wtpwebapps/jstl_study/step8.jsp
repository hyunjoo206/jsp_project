<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.PersonVO" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</head>
<body>
	<%
		// PersonVO 객체를 이용하여 list에 여러명을 추가(5명)
		// list를 request 객체에 담아 jsp로 전송
		
		ArrayList<PersonVO> list = new ArrayList<PersonVO>();
		// ArrayList로 PersonVO객체를 담을 list 생성
		
		//5명의 PersonVO 객체 생성하여 리스트에 추가
		list.add(new PersonVO("김마리", 15));
		list.add(new PersonVO("이마리", 35));
		list.add(new PersonVO("김말이", 5));
		list.add(new PersonVO("이말이", 20));
		list.add(new PersonVO("이마뤼", 49));
		
		//리스트를 request 객체에 담음 -> jsp로 전송
		request.setAttribute("list", list);

	%>
	
	<!-- 테이블을 생성하여 표로 출력 -->
	
	<table border="1"  class="table table-hover">
		<!-- 첫 번째 행 -->
		<tr>
			<th>번호</th>
			<th>이름</th>
			<th>나이</th>
			<th>연령대</th>		
		</tr>
		
		<!-- c:forEach가 향상된 for문 같은거 tr 한 행을 채우는걸 반복-->
		<!-- varStatus를 이용해서 객체의 count나 index값 가져와서 번호 출력 -->
		<c:forEach var="person" items="${list}" varStatus="status">
			<tr>
				<td>${status.count}</td>
				<td>${person.name }</td>
				<td>${person.age }</td>
				<td>
					<!-- 삼항연산자로 간단한건 가능 ${person.age>=20 ? "성인" : "미성년"} -->
					<c:choose>
						<c:when test="${person.age<9}">유아</c:when>
						<c:when test="${person.age<20}">청소년</c:when>					
						<c:when test="${person.age<40}">청년</c:when>					
						<c:when test="${person.age>=40}">어른</c:when>					
						<c:otherwise>나머지</c:otherwise>					
					</c:choose>
				</td>
			</tr>
		</c:forEach>
		
		
	
	</table>
	
</body>
</html>