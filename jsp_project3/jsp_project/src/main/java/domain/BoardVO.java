package domain;

public class BoardVO {
/*create table board(
bno int not null auto_increment,
title varchar(100) not null,
writer varchar(100) not null,
content text,
reg_date datetime default now(),
primary key(bno));*/
	private int bno;
	private String title;
	private String writer;
	private String content;
	private String reg_date;

//기본 생성자
public BoardVO() {}

//register할 때 필요한거 - 제목, 글쓴이, 내용 (title, writer, content)
public BoardVO(String title, String writer, String content) {
	this.title = title;
	this.writer = writer;
	this.content = content;
}


//list 볼 때 필요한거 - bno,title,writer,reg_date 
//내용보기 - 게시글 번호, 제목, 글쓴이, 등록날짜
public BoardVO(int bno, String title, String writer, String reg_date) {
	this.bno=bno;
	this.title=title;
	this.writer=writer;
	this.reg_date=reg_date;
}

//detail 볼 때 필요한거 - 전부다 
public BoardVO(int bno, String title, String writer, String content, String reg_date) {
	this.bno=bno;
	this.title=title;
	this.writer=writer;
	this.content= content;
	this.reg_date=reg_date;
}

//update 수정할 때 필요한거 (bno, title, content)
public BoardVO(int bno, String title, String content){
	this.bno = bno;
	this.title=title;
	this.content=content;
}

//getter& setter

public int getBno() {
	return bno;
}

public void setBno(int bno) {
	this.bno = bno;
}

public String getTitle() {
	return title;
}

public void setTitle(String title) {
	this.title = title;
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

//toString
@Override
public String toString() {
	return "BoardVO [bno=" + bno + ", title=" + title + ", writer=" + writer + ", content=" + content + ", reg_date="
			+ reg_date + "]";
}








}