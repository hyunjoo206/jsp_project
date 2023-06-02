<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <!-- taglib 써야 c:forEach 적용됨  -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board List Page</title>


</head>
<body>
	<h1>List Page</h1>
	
<!-- 검색라인 -->
<form action="brd/page" method="post">
	<div>
	<c:set value="${pgh.pgvo.type}" var="typed"></c:set>
		<!-- 보내야하니 name 설정 -->
		<select name="type">
		<!-- selected : 현재 내가 선택한 값 -->
			<option ${typed == null?'selected':''}>Choose...</option> 
			<option value="t" ${typed eq 't' ? 'selected':'' }>title</option>
			<option value="c" ${typed eq 'c' ? 'selected':'' }>Content</option>
			<option value="w" ${typed eq 'w' ? 'selected':'' }>Writer</option>
			<option value="tc" ${typed eq 'tc' ? 'selected':'' }>title or content</option>
			<option value="tw" ${typed eq 'tw' ? 'selected':'' }>title or writer</option>
			<option value="cw" ${typed eq 'cw' ? 'selected':'' }>content or writer</option>
		</select>		
		<input type="text" name="keyword" placeholder="Search">
		<input type="hidden" name="pageNo" value="${pgh.pgvo.pageNo}">
		<input type="hidden" name="qty" value="${pgh.pgvo.qty}">
		<button type="submit">Search</button>
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
				<th>readcount</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="bvo">
				<tr>
					<!-- input에만 hidden 기능이 있음, bno가 필요하지만 화면에 보일 필요는 없으므로 -->
					<!-- 현재페이지에서 넘어갈 다음 페이지의 주소(uri)를 적어주기 -->
					<td><a href="/brd/detail?bno=${bvo.bno}">${bvo.bno}</a></td>					
					<td>
					
					<!-- -------------------------- -파일 사진 추가 ------------------------------>
					<!-- 비어있지도, null도 아니어야 함 -->
					<c:if test="${bvo.image_file ne '' && bvo.image_file ne null }">
										<!-- 썸네일 src -->
						<img alt="없음" src="/_fileUpload/th_${bvo.image_file }">
					</c:if>
					
					<a href="/brd/detail?bno=${bvo.bno}">${bvo.title}</a>
					</td>					
					<td>${bvo.writer}</td>
					<td>${bvo.content}</td>
					<td>${bvo.readcount}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br>
	<a href="/"><button>index로</button></a> <br>
	
	<!-- 페이지네이션 위치 -->
	<!-- 컨트롤러에서 page 정보를 싣고 와야함 -->
	<!-- startPage~endPage까지 숫자 반복 // forEach 이용해서 반복예정-->
	
	<!-- 이전페이지  -->
	<c:if test="${pgh.prev }">
		<!-- sub를 버립시다,, sub까지 하려면 복잡해짐 -->
		<a href="/brd/page?pageNo=${pgh.startPage-1 }&qty=${pgh.pgvo.qty}&type=${pgh.pgvo.type}&keyword=${pgh.pgvo.keyword}">◀</a>
	</c:if>
	<c:forEach begin="${pgh.startPage }" end="${pgh.endPage }" var="i">
		<a href="/brd/page?pageNo=${i }&qty=${pgh.pgvo.qty}&type=${pgh.pgvo.type}&keyword=${pgh.pgvo.keyword}">${i } | </a>
	</c:forEach>
	
	<!-- 다음페이지  -->
	<c:if test="${pgh.next }">
		<a href="/brd/page?pageNo=${pgh.endPage+1 }&qty=${pgh.pgvo.qty}&type=${pgh.pgvo.type}&keyword=${pgh.pgvo.keyword}">▶</a>
	</c:if>
	
</body>
</html>