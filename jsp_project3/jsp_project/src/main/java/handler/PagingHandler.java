package handler;

import domain.PagingVO;

// MVC의 컨트롤러 안에서 실제 요청을 처리하는 메소드를 핸들러라고 함
// 서버로 요청이 가면, 해당 요청에 맞는 컨트롤러의 핸들러로 정보가 전해지고, 
// 핸들러는 이를 처리하여 적절한 값을 반환한다. 


public class PagingHandler {
	private int startPage; //현재 화면에서 보여줄 시작페이지네이션 번호
	private int endPage; //현재 화면에서 보여줄 끝페이지네이션 번호
	private boolean prev, next; //이전, 다음버튼 존재여부를 (t,f로 나타내려고)
	
	private int totalCount; //총 게시물 수
	private PagingVO pgvo;
	
	public PagingHandler(PagingVO pgvo, int totalCount) {
		this.pgvo = pgvo;
		this.totalCount = totalCount;
		// ex)127개의 게시글 => 13페이지까지 필요
		// startpage = 1, endpage = 10 다음(버튼)
		// startpage = 11, endpage = 13 이전(버튼)
		// (21/10)*10 => 2.1에서 올려져서 2.1(3)*10 => 30
		// 하단 마지막 페이지 10 숫자 => endPage
		this.endPage = (int)Math.ceil(pgvo.getPageNo() / (pgvo.getQty()*1.0))*pgvo.getQty();
		// (int)(올림){(출력되는 페이지네이션 번호)/(한 페이지에 보여줄 게시물 수 *1.0)} * 한 페이지에 보여줄 게시물 수
		// 올림(1 /10.0) * 10
		this.startPage = this.endPage-9;
		
		// 진짜 마지막 페이지 => realEndPage
		int realEndPage = (int)(Math.ceil((totalCount*1.0)/pgvo.getQty()));
		//(int)올림{(전체 게시글*1.0)/한 페이지에 보여줄 게시물 수}
		// 127*1.0 => 127.0 
		//127.0 / 10 => 12.7
		// 13
		
		//찐마지막페이지 < 마지막페이지 이라면 마지막페이지에 찐마지막페이지 넣어주기
		// 13<20 이라면 마지막 페이지가 13여야 함
		if(realEndPage < this.endPage) {
			this.endPage = realEndPage;
		}
		
		// 현재 화면에서 보여지는 거 startPage = 1, 11, 21 ...
		// startPage가 1보다 커야 11이면 prev에 True값
		// endPage가 realEndPage보다 작으면 10<13 next에 True값
		this.prev = this.startPage > 1; 
		this.next = this.endPage < realEndPage;
			
	
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public PagingVO getPgvo() {
		return pgvo;
	}

	public void setPgvo(PagingVO pgvo) {
		this.pgvo = pgvo;
	}
	
}
