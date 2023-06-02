<%@ page import="model.CarVO" %> <%-- ctrl+space 하면 import 선택하는 란뜸 --%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>EL연습</title>
</head>
<body>
	<h3>EL(Expression Language)</h3>
	<%
		CarVO car = new CarVO("1234","소나타",2000);
		//request.setAttribute : request 객체에 값을 저장(set)하는 역할
		// request.getAttribute : request 객체의 값을 가져오는(get) 역할
		// request.setAttribute("변수명", 값); 
		request.setAttribute("cvo", car);		
	%>
	<!-- 변수 출력 -->
	<strong>1. CarVO의 객체에서 변수 출력</strong> <br>
	<!-- 방법1 : requestScope를 사용 -->
	${requestScope.cvo.name} <br>
	
	<!-- 방법2 : requestScope (생략가능) -->
	${cvo.name}, ${cvo.num}, ${cvo.price}<br>

	<hr>
	
	<!-- 리스트로 변수 출력 -->
	<%
		ArrayList<CarVO> list = new ArrayList<CarVO>();
		//ArrayList 빨간줄 -> 위에 java.util.ArrayList 를 직접 import 해주어야 함
		list.add(new CarVO("4567","벤츠",5000)); // 0번지 객체
		list.add(new CarVO("1111","마티즈",500)); // 1번지 객체
		session.setAttribute("carList", list);		
	%>
		<strong>2. CarVO의 객체를 session 영역으로 설정 list로 출력</strong> <br>
		${sessionScope.carList[0].name }<br>
		${sessionScope.carList[1].name }<br>
		<hr>
		
	<!-- 해쉬맵으로 변수를 출력 -->
	<%
		HashMap<String, CarVO> map = new HashMap<String, CarVO>(); 
		//HashMap<String, CarVO> map = new HashMap<여기 비워도 됨>(); 
		map.put("car1", new CarVO("4567","벤츠",5000));
		request.setAttribute("carMap", map);	
	%>
		<strong>3. CarVO 객체에서 map 출력</strong> <br>
		${requestScope.carMap.car1.name}<br>
		${requestScope.carMap.car1.num}<br>
		${requestScope.carMap.car1.price}<br>
		
		<hr>
		<a href = "step2-1.jsp">step2-1로 이동</a>
</body>
</html>