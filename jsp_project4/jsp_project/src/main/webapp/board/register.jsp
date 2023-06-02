<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="container" class="rounded-3">
<h1>게시글 작성</h1><br>
	<form action="/brd/insert" method="post" enctype="multipart/form-data">
		title : <input type="text" name="title" class="form-control"><br>
		writer : <input type="text" name="writer" class="form-control" placeholder="${ses.id}" value="${ses.id}" readonly="readonly"><br>
		content : <textarea rows="3" cols="30" name="content" class="form-control"></textarea><br>
		imageFile : <input type="file" id="file" name="image_file" class="form-control" accept="image/png, image/jpg, image/jpeg, image/bmt, imgae/gif">
						<!-- <input> 태그의 accept 속성은 서버로 업로드할 수 있는 파일의 타입을 명시 -->
		<br><br>
		<button type="submit" class="btn btn-outline-primary">등록하기</button>
	</form><br>
		<a href="/brd/page"><button type="bvo" class="btn btn-outline-primary">목록보기</button></a>
	</div>
	
<!-- 부트스트랩 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"
   integrity="sha384-U1DAWAznBHeqEIlVSCgzq+c9gqGAJn5c/t99JyeKa9xxaYpSvHU5awsuZVVFIhvj" crossorigin="anonymous">
</script>
</body>
</html>