package domain;

public class MemberVO {
//	create table member(
//			id varchar(100) not null,
//			password varchar(100) not null,
//			email varchar(100) not null,
//			age int,
//			reg_date datetime default now(),
//			last_login datetime default null,
//			primary key(id));
	
	//멤버변수 선언
	private String id;
	private String password;
	private String email;
	private String phone;
	private int age;
	private String reg_date;
	private String last_login;
	
	//생성자
	public MemberVO() {}
	
	//login할 때 쓸거 id, pw
	public MemberVO(String id, String password) {
		this.id = id;
		this.password = password;
	}
	
	//join할 때 쓸거 id, pw, email, age
	public MemberVO(String id, String password, String email, String phone, int age) {
		this.id = id;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.age = age;
	}
	
	//list 볼 때 쓸거 id, email, age, reg_date, last_login
	public MemberVO(String id, String email, String phone, int age, String reg_date, String last_login){
		this.id = id;
		this.email = email;
		this.phone = phone;
		this.age = age;
		this.reg_date = reg_date;
		this.last_login = last_login;
	}
	
	//modify할 때 쓸거 password, email, phone, age
	public MemberVO(String password,String phone, String email, int age) {
		this.password = password;
		this.phone = phone;
		this.email = email;
		this.age = age;
	}
	
	//전체
	public MemberVO(String id, String password, String email, String phone, int age, String reg_date, String last_login){
		this.id = id;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.age = age;
		this.reg_date = reg_date;
		this.last_login = last_login;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	
	

	
	//toString 안해도 된다...? => 또 까먹음... 왜..? 값 안 찍어보려면 굳이 없어도 되는거?
	
	
	
}
