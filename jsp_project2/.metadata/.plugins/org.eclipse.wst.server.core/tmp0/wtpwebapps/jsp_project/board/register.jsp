<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board</title>
</head>
<body>
	<form action="/brd/insert" method="post" enctype="multipart/form-data">
		title : <input type="text" name="title"><br>
		writer : <input type="text" name="writer" placeholder="${ses.id}" value="${ses.id}" readonly="readonly"><br>
		content : <textarea rows="3" cols="30" name="content"></textarea><br>
		imageFile : <input type="file" id="file" name="image_file" accept="image/png, image/jpg, image/jpeg, image/bmt, imgae/gif">
						<!-- <input> 태그의 accept 속성은 서버로 업로드할 수 있는 파일의 타입을 명시 -->
		<br><br>
		<button type="submit">등록하기</button>
		<a href="/brd/list"><button type="bvo">목록보기</button></a>
	</form>
</body>
</html>