package controller;

public class Person {
	private String phonenumber;
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	
	public Person(String pn, String n) {
		phonenumber = pn;
		name = n;
	}
}
