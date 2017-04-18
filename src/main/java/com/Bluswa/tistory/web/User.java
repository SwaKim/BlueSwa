package com.Bluswa.tistory.web;

public class User {
	private String userID;
	private String password;
	private String name;
	private String email;
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "User [userID=" + userID + ", password=" + password + ", name=" + name + ", email=" + email + "]";
	}

}
