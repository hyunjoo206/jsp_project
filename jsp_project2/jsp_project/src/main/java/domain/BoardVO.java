package domain;

import java.util.List;

public class BoardVO {

	private int bno;
	private String title;
	private String writer;
	private String content;
	private String reg_date;
	private int readcount;
	private String image_file;
	
	public BoardVO() {}
	
	//register(title,writer, content)
	public BoardVO(String title, String writer, String content) {
		this.title = title;
		this.writer = writer;
		this.content = content;
	}
	
	//list(bno, title, writer, reg_date)내용보기
	public BoardVO(int bno, String title,String writer, String reg_date, int readcount) {
		this.bno=bno;
		this.title = title;
		this.writer=writer;
		this.reg_date=reg_date;
		this.readcount=readcount;
	}
	
	//detail(all)
	public BoardVO(int bno, String title, String writer, String content, String reg_date, int readcount, String image_file){
		this.bno=bno;
		this.title = title;
		this.writer=writer;
		this.content = content;
		this.reg_date=reg_date;
		this.readcount=readcount;
		this.image_file = image_file;
	}
	
	//update(bno, title, content)
	public BoardVO(int bno, String title, String content) {
		this.bno=bno;
		this.title=title;
		this.content=content;
	}

	//getter&setter
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
	
	public List<BoardVO> list() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	public int getReadcount() {
		return readcount;
	}

	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}

	
	
	public String getImage_file() {
		return image_file;
	}

	public void setImage_file(String image_file) {
		this.image_file = image_file;
	}

	@Override
	public String toString() {
		return "BoardVO [bno=" + bno + ", title=" + title + ", writer=" + writer + ", content=" + content
				+ ", reg_date=" + reg_date + ", readcount=" + readcount + ", image_file=" + image_file + "]";
	}



	
	
	
}
