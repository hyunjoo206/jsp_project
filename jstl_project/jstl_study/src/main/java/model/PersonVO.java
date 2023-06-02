package model;

public class PersonVO {
	private String name;
	private int age;
	
	//생성자
	public PersonVO() {}
	public PersonVO(String name, int age) {
		this.name = name;
		this.age = age;
	}

	//getter&setter
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	
}
