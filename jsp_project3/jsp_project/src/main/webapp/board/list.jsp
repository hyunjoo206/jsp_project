<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- taglibrary 달아주기 -->
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>List Page</h1>

<!-- 검색부분 -->
<form action="">
	<div>
	<!-- c:set은 typed라는 변수에 할당된 값을 가져옴 -->
	<!-- pgh 객체의 pgvo 속성의 type 값을 가져와서 typed 변수에 넣는다. 
	PagingHandler의 pgh에서 참조하는 것 -->
	<c:set value="${pgh.pgvo.type}" var="typed"></c:set>
		<select name="type">
		<!--  현재 선택된 값이 없는 경우(기본값) selected 속성 갖도록-->
			<option ${typed == null?'selected':''}>선택</option>
			<option value="t" ${typed eq 't' ? 'selected':'' }>제목</option>
			<option value="c" ${typed eq 'c' ? 'selected':'' }>내용</option>
			<option value="w" ${typed eq 'w' ? 'selected':'' }>글쓴이</option>
			<option value="tw" ${typed eq 'tw' ? 'selected':'' }>제목 또는 글쓴이</option>
			<option value="cw" ${typed eq 'cw' ? 'selected':'' }>내용 또는 글쓴이</option>
		</select>
		<input type="text" name="keyword" plcaeholder="Search">
		<input type="hidden" name="pageNo" value="${pgh.pgvo.pageNo}">
		<input type="hidden" name="qty" value="${pgh.pgvo.qty}">
		<button type="submit">검색</button>
	</div>
	<br>
</form>


	<table border="1">
		<thead>
			<tr>
				<th>bno</th>
				<th>title</th>
				<th>writer</th>
				<th>content</th>
			</tr>
		</thead>
		<tbody>
			<!-- list4로 받아온거 확인하기 -->
			<c:forEach items="${list}" var="bvo">
				<tr>
					<!-- input에만 hidden 기능이 있음, bno가 필요하지만 화면에 보일 필요는 없으므로 -->
					<!-- 현재페이지에서 넘어갈 다음 페이지의 주소(uri)를 적어주기 -->
					<td><a href="/brd/detail?bno=${bvo.bno}">${bvo.bno}</a></td>					
					<td><a href="/brd/detail?bno=${bvo.bno}">${bvo.title}</a></td>					
					<td>${bvo.writer}</td>
					<td>${bvo.content}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br>
	<a href="/"><button>index로</button></a> <br>
	
	<!-- ------------------------페이지네이션 위치------------------------- -->
	<!-- 컨트롤러에서 page 정보 싣고 와야함 -->
	<!-- startPage ~ endPage까지 숫자 반복 => forEach 이용 -->
	
	<!-- 이전페이지 -->
	<c:if test="${pgh.prev }">
		<!-- get방식으로 하는 이유? pageNo, qty만 ??필요???왜??? -->				
									<!-- qty는 db에서 가져와야 하니까 pgvo거쳐서 가져와야함-->
		<a href="/brd/page?pageNo=${pgh.startPage-1}&qty=${pgh.pgvo.qty}">이전</a>
	</c:if>
	
	<!-- 1,2,3 ... 10 페이지네이션 숫자 뽑기 foreach로 반복 -->
	<c:forEach begin="${pgh.startPage}" end="${pgh.endPage}" var="i">
		<a href="/brd/page?pageNo=${i}&qty=${pgh.pgvo.qty}">${i }</a>
	</c:forEach>
	
	<!-- 다음페이지 -->
	<c:if test="${pgh.next }">
		<a href="/brd/page?pageNo=${pgh.endPage+1 }&qty=${pgh.pgvo.qty}">다음</a>
	</c:if>
	
	
	
</body>
</html>