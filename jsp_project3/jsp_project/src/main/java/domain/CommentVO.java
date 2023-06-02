package domain;

public class CommentVO {

	/*create table comment(
cno int not null auto_increment,
bno int default 0,
writer varchar(100) default “unknown”,
content varchar(1000) not null,
reg_date datetime default now(), 
primary key(cno)
);
*/
private int cno; //auto_increament
private int bno;
private String writer;
private String content;
private String reg_date;

public CommentVO() {}

//등록할 때 필요 - bno, writer, content 
public CommentVO(int bno, String writer, String content) {
this.bno = bno;
this.writer = writer;
this.content=content;
}

//삭제할 때 필요 - cno, writer, content
// 위에랑 int, String, String 순서라서 바꿔서 해줘야함
// writer, content, int 순서로
public CommentVO(String writer, String content, int cno) {
	this.writer=writer;
	this.content=content;
	this.cno=cno;
}
	
//수정할 때 필요 - cno, content
public CommentVO(int cno, String content) {
	this.cno=cno;
	this.content=content;
}

//댓글표시(list) 할 때 필요 - 전부
public CommentVO(int cno, int bno, String writer, String content, String reg_date) {
	this.cno=cno;
	this.bno=bno;
	this.writer=writer;
	this.content=content;
	this.reg_date=reg_date;
}

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

@Override
public String toString() {
	return "CommentVO [cno=" + cno + ", bno=" + bno + ", writer=" + writer + ", content=" + content + ", reg_date="
			+ reg_date + "]";
}




}

	

