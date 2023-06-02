async function postCommentToServer(cmtData){
	try{
		//이 정보를 다 가지고 controller로 가는거
		const url = "/cmt/post";
		const config = {
			method:'post', //post 방식이고
			header:{ //타입이 json, utf-8이고
				'Content-Type' : 'application/json; charset=utf-8'
			},
			body:
			//json을 문자열로 바꿔주는 내장함수
			//cmtData가 json의 형태인데 stringify를 사용해서 string형태로 바꿔줌
			JSON.stringify(cmtData)
		};
	
		const resp = await fetch(url, config);
		//받는게 왜 text인가..? => isOk라서 
		const result = await resp.text();
		return result;
		
	}catch(error){
		console.log(error);
	}
}

//댓글등록버튼 (deatil.jsp에 있음)을 누르면 이벤트 발생
document.getElementById("cmtAddBtn").addEventListener('click',()=>{
	//코멘트에 입력한 value값을 cmtText라고 detail.jsp에서 정해줌
	const cmtText = document.getElementById('cmtText').value;
	console.log(cmtText);
	//댓글창이 비어있으면 등록 안함
	if(cmtText == null || cmtText ==""){
		//입력창이 비어있으면 alert 띄우기
		alert('댓글을 입력해주세요');
		return false;
	}else{
		//여기서 cmtData가 나오는거임~~ 위에 postCommentToServer에서 쓸거임
		let cmtData = {
			//JSON 형태 : key, value 값으로 받음
			//detail.jsp 에서 가져온 값임 
			bno : bnoVal,
			writer : document.getElementById('cmtWriter').value,
			content : cmtText
		};
		postCommentToServer(cmtData).then(result=>{
			if(result>0){//프로미스 객체 형태에 0,1이 들어있는 상태임 그래서 0보다크면
				alert('댓글 등록 성공!!');
				//댓글등록 성공 후 댓글창 비워주려고 "" 넣어줌
				document.getElementById('cmtText').value="";		
			}
			
			printCommentList(cmtData.bno);
		})
	}
});

//댓글 가져오기기능 - bno에 해당하는 댓글 리스트 달라고 요청하는 기능
async function getCommentListFromServer(bno){
	try{
		//ex) 1번 게시글의 댓글을 주세요
		//위에랑 다르게 url을 직접 담음
		const resp = await fetch('/cmt/list/'+bno);
		//db에서 json객체로 가져와서 k,v 값으로 뽑은 후 화면에 뿌려줘야 함
		const cmtList = await resp.json();
		return cmtList;	
	}catch{
		console.log(error);
	}	
}

//댓글리스트출력하는 메서드
function printCommentList(bno){
getCommentListFromServer(bno).then(result=>{
	console.log(result);
	if(result.length>0){//result는 isOk로 받으니까 0보다 크면(댓글이 있으면)
		spreadCommentList(result);//화면에 뿌려주는 메서드 호출
	}else{
		//detail.jsp에 부트스트랩으로 가져온 부분
		let div = document.getElementById('accordionExample');
		div.innerText = "등록된댓글이 없습니다"
	}
})
}

//화면에 출력하는 메서드
function spreadCommentList(result){
	console.log(result);
	let div = document.getElementById('accordionExample');
	div.innerHTML="";
	for(let i=0; i<result.length; i++){
		let html =`<div class="accordion-item">`;
		html += `<h2 class="accordion-header" id="heading${i}">`;
		html += `<button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapse${i}" aria-expanded="true" aria-controls="collapse${i}">`
		html += `${result[i].cno}, ${result[i].writer} </button></h2>`;
		html += `<div id="collapse${i}" class="accordion-collapse collapse show" aria-labelledby="heading${i}" data-bs-parent="#accordionExample">`;
		html += `<div class="accordion-body">`;
		
		//*********************** 모르는부분
		//여기서부터는 어디서 나온거? 내가... 부트스트랩을 덜 가져왔나? 아님...수정 삭제 버튼 만들어준거??
		html += `<button type="button" data-cno="${result[i].cno}" data-writer="${result[i].writer}" class="btn btn-sm btn-outline-warning cmtModBtn">%</button>`;
		html += `<button type="button" data-cno="${result[i].cno}" class="btn btn-sm btn-outline-danger cmtDelBtn">X</button>`;
		html += `<input type="text" class="form-control" id="cmtText1" value="${result[i].content}">`;
		html += `${result[i].reg_date}`;
		html += `</div></div></div>`;
		div.innerHTML += html; //누적해서 담기
	}
}	
	
	
	//댓글 삭제하는 기능
	async function removeCommentFromServer(cnoVal){
		try{
			const url = '/cmt/remove?cnoVal='+cnoVal;
			const config ={
				method : 'post'
			}
		const resp = await fetch(url, config);
		const result = await resp.text(); //isOk
		return result;		
		
		}catch(error){
			console.log(error);
		}
	}
	
	
	//댓글 수정하는 기능
	async function updateCommentFromServer(cnoVal, cmtText1, writer){
	try{
		const url ="/cmt/modify";
		const config ={
			method : 'post',
			//값 여러개일 경우 어떤 형태로 보낼지 headers 필요함
			headers :{
				'Content-Type' : 'application/json; charset=utf-8'
			},
			//매개변수 받아서 바로 객체 생성한거임 key:value로
			body : JSON.stringify({cno:cnoVal, content:cmtText1, writer:writer})
		}
		const resp = await fetch(url, config);
		//update 하는 거니까 text가 온다. => isOk하려고 
		const result = await resp.text();
		return result;
	}catch(error){
		console.log(error);
		
	}
}


									//e의 의미는 내가 클릭한 값(객체)
document.addEventListener('click',(e)=>{
				//cmtModBtn버튼이 있으면 괄호 안을 처리해줘. equals 안됨 포함된지 봐야함
				//위에 html에서 cmtModBtn 버튼 생성해줌 %(수정)X(삭제)
	if(e.target.classList.contains('cmtModBtn')){
	//수정작업	
		let cnoVal = e.target.dataset.cno; //dataset으로 값을 빼기
		console.log(cnoVal);
		//기존 위치에서 값을 읽어들여 내용을 DB에 저장한 후 변경
		//현재 수정하고자 하는 값 input box의 value값을 찾기위한 작업
		let div = e.target.closest('div'); //closet => 현재 타겟을 기준으로 가장 가까운 (div)값 찾기 
		//쿼리셀렉터로 가져오는 이유는? *********************
		let cmtText1 = div.querySelector('#cmtText1').value;
		let writer = e.target.dataset.writer;
		console.log(cmtText1);
		console.log(writer);
		
		
		//비동기 통신  
		updateCommentFromServer(cnoVal, cmtText1, writer).then(result=>{
			if(result>0){
				alert('댓글 수정 완료!');
				console.log(result);
				//출력
				printCommentList(bnoVal);
			}else{
				//writer가 아닌 case일 경우 보통 수정이 불가함
				alert("수정이 불가합니다.");
			}
		})
		
	}

	if(e.target.classList.contains('cmtDelBtn')){
	//삭제작업
		let cnoVal = e.target.dataset.cno; //dataset으로 값을 빼기
		console.log(cnoVal);
		removeCommentFromServer(cnoVal).then(result=>{
			if(result > 0){
				//값이 있으면 댓글삭제완료 알람
				alert('댓글 삭제 완료!');
				printCommentList(bnoVal);
				console.log(bnoVal);
			}
		})
	}

});
