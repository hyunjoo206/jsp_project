<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <!-- js할 때 c쓰니까 넣어줌 -->
 <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<!-- 부트스트랩 링크임-->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<h1>상세페이지</h1>
	<table border="1" class="table table-hover">
		<tr>
			<th>번호</th>
			<td>${bvo2.bno}</td>
		</tr>
		<tr>
			<th>제목</th>
			<td>${bvo2.title}</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td>${bvo2.writer}</td>
		</tr>
		<tr>
			<th>내용</th>
			<td>${bvo2.content}</td>
		</tr>
		<tr> 
			<th>작성일</th>
			<td>${bvo2.reg_date}</td>
		</tr>
		
	</table>
	
	
	<!-- 가져가야될 정보가 여러개면 &로 묶어주면 됨, 띄어쓰기는 하면 절대 안됨 -->
	<a href="/brd/modify?bno=${bvo2.bno}"><button type="button">수정하기</button></a>
	<a href="/brd/delete?bno=${bvo2.bno}"><button type="button">삭제하기</button></a>
	<a href="/brd/list"><button type="button">목록보기</button></a>
	
	
	<!-- ---------------------Comment부분 ----------------- -->
	<br> 
	<div> 
	<h3>Comment line</h3>
	
	<!-- 댓글작성라인 -->
	<!-- name으로 안 쓰고 id로 쓰는 이유는 js에서 id로 받기 때문임 -->
		<input type="text" id="cmtWriter" value=${ses.id} readonly="readonly"><br>
		<input type="text" id="cmtText" placeholder="코멘트를 입력해주세요"><br>
		<button type="button" id="cmtAddBtn">댓글등록</button>	
	</div><br>
	
	<!-- 댓글표시라인, 부트스트랩에서 가져옴 -->
		<div class="accordion" id="accordionExample">
	 	 <div class="accordion-item">
	    	<h2 class="accordion-header" id="headingOne">
	     	 <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
	       		 cno, writer
	      	</button>
	   		 </h2>
	   	 	<div id="collapseOne" class="accordion-collapse collapse show" aria-labelledby="headingOne" data-bs-parent="#accordionExample">
	   		 <div class="accordion-body">
	   		  -
			content, reg_date
	     	 </div>
	   		 </div>
	 	 </div>
		</div>
	
		
		<script type="text/javascript">
			//c: 로 가져온 bvo2.bno값을 가져와서 bnoVal에 넣는다
			const bnoVal = `<c:out value="${bvo2.bno}" />`;
			//bnoVal을 매개변수로 가져가는 printCommentList 함수를 불러옴??
			printCommentList(bnoVal);
		</script>
		
		<!-- board_detail.js에서 bnoVal을 사용할 수 있도록 연결해주는 구문 -->
		<script src="/resources/board_detail.js"></script>
		
		
		<!-- 부트스트랩 -->
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
	

</body>
</html>