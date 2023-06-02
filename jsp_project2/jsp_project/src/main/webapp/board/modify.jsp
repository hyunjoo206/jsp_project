<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Modify Page</h1>
	<br>
	<!-- form의 action은 컨트롤러로 이동하는거 -->
										
	<form action="/brd/edit" method="post" enctype = "multipart/form-data">
		bno:<input type="text" name="bno" value="${bvo.bno}" readonly="readonly"><br>
		title:<input type="text" name="title" value="${bvo.title}"><br>
		writer:<input type="text" name="writer" value="${bvo.writer}" readonly="readonly"><br>
		content:<input type="text" name="content" value="${bvo.content}"><br>
		<!-- 추가 -->
		image_file : <img alt="없음" src="/_fileUpload/th_${bvo.image_file }">
		<input type="hidden" name="reg_date" value="${bvo.reg_date}" readonly="readonly"><br>
		<input type="hidden" name="image_file" value="${bvo.image_file }"> <!-- 기존의 파일 -->
		<input type="file" name="new_file">	<!-- 바뀐 파일 -->
		
		<!-- 숨겨서 가져가기 -->
		
		<button type="submit">수정확인</button>
	</form>
</body>
</html>