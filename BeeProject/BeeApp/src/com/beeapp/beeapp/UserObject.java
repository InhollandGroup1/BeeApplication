package com.beeapp.beeapp;

public class UserObject {
	public int id;
	public String email;
	public String firstname;
	public String lastname;
	UserObject() {
		this.id = -1;
		this.email = "unknown";
		this.firstname = "unknown";
		this.lastname = "unknown";
	}
	UserObject(int id, String email, String firstname, String lastname) {
		this.id = id;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
	}
}
