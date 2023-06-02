<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="/brd/insert" method="post">
		
		title : <input type="text" name="title"><br>
		writer : <input type="text" name="writer" placeholder="${ses.id}"
				value="${ses.id}" readonly="readonly"><br>
		content : <textarea rows="3" col="30" name="content"></textarea><br>
		<button type="submit">등록하기</button>
		<a href="/brd/list"><button type="bvo">목록보기</button></a>
		
	</form>

</body>
</html>