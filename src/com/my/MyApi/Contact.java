package com.my.MyApi;

public class Contact {
	
	//private variables
	String user;
	String password;
	String currency;
	
	// Empty constructor
	public Contact(){
		
	}
	// constructor
	public Contact(String user, String password){
		this.user = user;
		this.password = password;
		this.currency="Rs.";
	}
	public Contact(String user, String password,String curr){
		this.user = user;
		this.password = password;
		this.currency=curr;
	}
	// getting user
	public String getUser(){
		return this.user;
	}
	
	// setting user
	public void setUser(String user){
		this.user = user;
	}
	
	public String getPass() {
		// TODO Auto-generated method stub
		return password;
	}
	public void profileActivate(){
		
	}
}