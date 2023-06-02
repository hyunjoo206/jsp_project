package domain;

import java.util.List;

public class MemberVO {
	/*member
		id varchar(100)
		password varchar(100)
		email varchar(100)
		age int
		reg_date datetime default now()
		last_login datetime default null*/
	
	private String id;
	private String password;
	private String email;
	private int age;
	private String reg_date;
	private String last_login;
	
	public MemberVO() {}
	//login : id, pw
	public MemberVO(String id, String password) {
		this.id = id;
		this.password = password;
	}
	//join : id, pw, email, age
	public MemberVO(String id, String password, String email, int age) {
		this.id = id;
		this.password = password;
		this.email = email;
		this.age = age;
	}
	//list : id, email, age, reg_date, last_login
	public MemberVO(String id,  String email, int age, String reg_date, String last_login) {
		this.id= id;
		this.email = email;
		this.age = age;
		this.reg_date = reg_date;
		this.last_login = last_login;
	}
	
	public MemberVO(String id, String password, String email, int age, String reg_date, String last_login) {
		this.id= id;
		this.age =age;
		this.email = email;
		this.age = age;
		this.reg_date = reg_date;
		this.last_login = last_login;
	}
	
	//modify : password, email, age
	public MemberVO(String password, String email, int age) {
		this.password= password;
		this.email= email;
		this.age= age;	
	}
	//getter&setter
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getLast_login() {
		return last_login;
	}
	public void setLast_login(String last_login) {
		this.last_login = last_login;
	}
	//toString 안해도 됨
	public List<MemberVO> list() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
