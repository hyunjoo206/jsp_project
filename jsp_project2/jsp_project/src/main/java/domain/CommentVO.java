package domain;

public class CommentVO {
/*create table comment(
cno int not null auto_increment,
bno int default 0,
writer varchar(100) default “unknown”,
content varchar(1000) not null,
reg_date datetime default now(), 
primary key(cno)
);*/
	private int cno;
	private int bno;
	private String writer;
	private String content;
	private String reg_date;
	private String datetime;
	
	public CommentVO() {}
	
	//등록할때 필요 - cno는 자동 increment되니까 필요없음/ bno,writer,content
	public CommentVO(int bno, String writer, String content) {
		this.bno=bno;
		this.writer=writer;
		this.content=content;
	}
	//writer,content,cno (위에랑 같으면 오버라이딩 안되니까 int 마지막으로 보내줌)
	public CommentVO(String writer, String content, int cno) {
		this.writer=writer;
		this.content=content;
		this.cno=cno;
	}
	
	//수정(modify)할때 필요 - cno, content
	public CommentVO(int cno, String content) {
		this.cno=cno;
		this.writer=writer;
	}
	
	//댓글 표시(list)할때 필요한거 - 전부 cno,bno,writer,content,reg_date
	public CommentVO(int cno, int bno, String writer, String content, String reg_date) {
		this.cno = cno;
		this.bno = bno;
		this.writer = writer;
		this.content = content;
		this.reg_date = reg_date;
	}

	//getter& setter
	public int getCno() {
		return cno;
	}

	public void setCno(int cno) {
		this.cno = cno;
	}

	public int getBno() {
		return bno;
	}

	public void setBno(int bno) {
		this.bno = bno;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	//toString
	@Override
	public String toString() {
		return "CommentVO [cno=" + cno + ", bno=" + bno + ", writer=" + writer + ", content=" + content + ", reg_date="
				+ reg_date + ", datetime=" + datetime + "]";
	}

	
	
	
	
}
