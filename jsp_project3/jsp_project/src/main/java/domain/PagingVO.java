package domain;

public class PagingVO {

	private int pageNo; //현재 화면에 출력되는 페이지네이션 번호
	private int qty;  // 한 페이지에 보여줄 게시물 수
	
	//+++++++++검색 멤버변수 추가
	private String type;
	private String keyword;
	
	public PagingVO(){
		this(1,10);
	}
	
	public PagingVO(int pageNo, int qty) {
		this.pageNo = pageNo;
		this.qty = qty;
	}
	
	//**********추가
	public int getPageStart() {
		return (this.pageNo-1)*10; //시작 limit 번지 구하려고
	}
	
	//++++++타입을 배열처리
	// type 변수에 저장된 검색 조건값을 분열하여 배열로 반환함
	// ex) tc라는 값이 저장되어 있다면 t, c 와 같은 문자열 배열을 반환하기 위해
	// null일경우 빈 배열을 반환함
	public String[] getTypeToArray() {
			return this.type == null ? new String[]{} : this.type.split("");
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	
	//++++검색 시에 필요한 getter,setter, toSting 추가
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getKeyword() {
		return keyword;
	}
	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public String toString() {
		return "PagingVO [pageNo=" + pageNo + ", qty=" + qty + ", type=" + type + ", keyword=" + keyword + "]";
	}
	
	
	
	
}
