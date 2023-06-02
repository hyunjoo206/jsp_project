package domain;

public class PagingVO {
	
	private int pageNo; //현재 화면에 출력되는 페이지네이션 번호
	private int qty; //한 페이지에 보여줄 게시물 수
	
	//++++++++++++++++++++++++검색관련 멤버변수 추가
	private String type;
	private String keyword;
	
	
	
	//기본값 먼저 올 수 있도록 앞에 위치함
	public PagingVO() { //페이지네이션을 클릭하기 전 기본 값으로 지정
		this(1,10);
	}

	public PagingVO(int pageNo, int qty) {
		this.pageNo = pageNo;
		this.qty=qty;
	}
	
	//추가
	public int getPageStart() {
		return (this.pageNo-1)*10; //시작 limit 번지 구하기 : 미완성
	}
	
	//+++++++++++++++++++++++++++++++++타입을 배열처리
	public String[] getTypeToArray() {
		//this.type이 null이면 new Sting[]{} 
		// null이 아닐 경우 잘라서 넣어줄거임~ 
		//잘라준다는 게 잘 이해안감 왜..?첫글자만 왜..?
		return this.type == null ? new String[] {} : this.type.split("");
	}
	
	
	//getter&setter

	public int getpageNo() {
		return pageNo;
	}

	public void setpageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	//+++++++++검색시 필요한 게터,세터 추가

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
