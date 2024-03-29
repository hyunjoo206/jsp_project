/**
 * 
 */

async function postCommentToServer(cmtData){
	try{
		//요청할 주소(적어놓은 경로로 가는 것)
		const url = "/cmt/post";
		//데이터의 정보
		const config = { 
			method:'post', 
			header:{
				'Content-Type' : 'application/json; charset=utf-8'
			},
			body:
				//json을 문자열로 바꿔주는 내장함수
				//cmtData가 json의 형태인데 그걸 stringify를 사용해서 string형태로 바꿔줌
				JSON.stringify(cmtData)
		};
		//response객체가 resp임...  
		//비동기 통신이라서 await이 가능/ 뜻:fetch(url,config)가 끝날 때까지 기다려라
		//fetch 함수 역시 promise 객체를 반환함
		//isOk
		
		//url로 config 가져갈게.
		const resp = await fetch(url, config);
		//객체를 text로 바꿔서 담아주세요
		
		//이해안감
		const result = await resp.text(); // isOk가 오는거 controller에서 보내는 결과
		return result; //프로미스 객체로 리턴 (result = 프로미스객체)
		 //프로미스로 구현된 비동기 함수는 프로미스 객체를 반환하고,
		// 후속처리 메서드 then, catch를 통해 결과를 전달받아 처리함.
	}catch(error){
		console.log(error);
	}
}

//미리 보낼 데이터를 만들어서 함수로 전달 cmtData
document.getElementById("cmtAddBtn").addEventListener('click',()=>{
	const cmtText = document.getElementById('cmtText').value;
	console.log(cmtText);
	//비어있는 값이 있으면 등록 안하도록
	if(cmtText == null || cmtText == ""){
		//입력창이 빈칸이면 alert 띄우기
		alert('댓글을 입력해주세요');
		return false; //안해도됨
	}else{
		//cmtData 자체가 JSON형태
		let cmtData = {
			//key,value 로 받아야
			//detail.jsp에서 c:로 해서 가져온거
			bno : bnoVal,
			writer : document.getElementById('cmtWriter').value,
			content : cmtText
		};
		//프로미스 객체에 체인 방식으로 받아오는 거. 외워야함 찾아보자..then...
		// then : 두 개의 콜백함수를 인자로 받고, 프로미스를 반환함
		//postCommentToServer(cmtData)가 리턴한 값을 result라는 이름의 변수에 담는다
		postCommentToServer(cmtData).then(result =>{
			if(result >0){ //프로미스객체 형태에 0,1이 들어가 있는 상태 ... 무튼 변환없이 비교가능
				alert('댓글 등록 성공!!!');
				document.getElementById('cmtText').value=""; //댓글 등록 후 input창 비우기
			}
			//댓글 표시(밑에서 함수 만들고 불러옴)
			printCommentList(cmtData.bno);
		})
	}
	
});



//댓글 가져오기
//컨트롤러에 가서 bno에 해당하는 댓글 리스트 달라고 요청하기.
async function getCommentListFromServer(bno){
	try{
	// ex)1번 게시글의 댓글을 주세요
	// 데이터가 컨트롤러로 이동한 후 컨트롤러 처리를 하고 
	// 응답 데이터를 가져와서 저장
	// 프로미스라는 포패키지로 가져오는 거임
	const resp = await fetch('/cmt/list/'+bno); // cmt/list/1
	const cmtList = await resp.json(); //댓글 객체가 리턴되야함
	return cmtList;
	
	}catch{
		console.log(error);
	}
}


function printCommentList(bno){
									//result=cmtList
									//result는 cmtList가 리턴해주는 걸 받는 것
//호출하는 애가 bno를 담으면 bno로 값을 뿌린다
getCommentListFromServer(bno).then(result=>{
	console.log(result);
	if(result.length >0){//등록된 댓글이 있으면
		spreadCommentList(result); //호출
	}else{
		let div = document.getElementById('accordionExample');
		div.innerText = "comment가 없습니다."
	}
})	
}


function spreadCommentList(result){ //댓글 list
console.log(result);
let div = document.getElementById('accordionExample');
div.innerHTML="";
for(let i=0; i<result.length; i++){
	let html = `<div class="accordion-item">`;
	//one있는 부분 지우고 ${i}로 교체 
	html += `<h2 class="accordion-header" id="heading${i}">`;
	html += `<button class="accordion-button" type="button" data-bs-toggle="collapse" `; 
	html += `data-bs-target="#collapse${i}" aria-expanded="true" aria-controls="collapse${i}">`;
	html += `${result[i].cno}, ${result[i].writer} </button></h2>`; //1번댓글 작성자
	html += `<div id="collapse${i}" class="accordion-collapse collapse show" `;
	html += `aria-labelledby="heading${i}" data-bs-parent="#accordionExample">`;
	html += `<div class="accordion-body">`;
	html += `<button type="button" data-cno="${result[i].cno}" data-writer="${result[i].writer}" class="btn btn-sm btn-outline-warning cmtModBtn">%</button>`;
	html += `<button type="button" data-cno="${result[i].cno}" class="btn btn-sm btn-outline-danger cmtDelBtn">X</button>`;
	html += `<input type="text" class="form-control" id="cmtText1" value="${result[i].content}">`;
	html += `${result[i].reg_date}`;
	html += `</div></div></div>`;
	div.innerHTML += html; //누적해서 담기
	}

}
async function removeCommentFromServer(cnoVal){
	try{
		//일반적으로 가는 쿼리스트링 방식
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
				//cmtMdoBtn버튼이 있으면 괄호 안을 처리해줘. equals 안됨 포함된지 봐야함
	if(e.target.classList.contains('cmtModBtn')){
	//수정작업	
		let cnoVal = e.target.dataset.cno; //dataset으로 값을 빼기
		console.log(cnoVal);
		//기존 위치에서 값을 읽어들여 내용을 DB에 저장한 후 변경
		//현재 수정하고자 하는 값 input box의 value값을 찾기위한 작업
		let div = e.target.closest('div'); //closet => 현재 타겟을 기준으로 가장 가까운 (div)값 찾기 
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
})


