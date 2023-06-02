<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
<meta charset="UTF-8">
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="rounded-3">
	<h1>Modify Page</h1>
	<br>
	
	<!-- form의 action은 컨트롤러로 이동하는거 -->
										
	<form action="/brd/edit" method="post" enctype = "multipart/form-data">
		bno:<input type="text" name="bno" value="${bvo.bno}" readonly="readonly"  class="form-control"><br>
		title:<input type="text" name="title" value="${bvo.title}"  class="form-control"><br>
		writer:<input type="text" name="writer" value="${bvo.writer}" readonly="readonly"  class="form-control"><br>
		content:<input type="text" name="content" value="${bvo.content}"  class="form-control"><br>
		<!-- 추가 -->
		image_file : <img alt="없음" src="/_fileUpload/th_${bvo.image_file }"  class="form-control">
		<input type="hidden" name="reg_date" value="${bvo.reg_date}" readonly="readonly"><br>
		<input type="hidden" name="image_file" value="${bvo.image_file }"> <!-- 기존의 파일 -->
		<input type="file" name="new_file">	<!-- 바뀐 파일 -->
		
		<!-- 숨겨서 가져가기 -->
		
		<button type="submit" class="btn btn-outline-primary">수정확인</button>
	</form>
	</div>
	
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"
   integrity="sha384-U1DAWAznBHeqEIlVSCgzq+c9gqGAJn5c/t99JyeKa9xxaYpSvHU5awsuZVVFIhvj" crossorigin="anonymous">
</script>
</body>
</html>