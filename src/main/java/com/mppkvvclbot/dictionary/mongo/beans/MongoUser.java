package com.mppkvvclbot.dictionary.mongo.beans;

import javax.persistence.*;

public class MongoUser {
	@Id
	private long id;

	private String username;

	private String password;

	private String name;

	private String mobileNo;

	private String role;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public MongoUser() {
	}

	@Override
	public String toString() {
		return "MongoUser{" +
				"id=" + id +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", name='" + name + '\'' +
				", mobileNo='" + mobileNo + '\'' +
				", role='" + role + '\'' +
				'}';
	}
}
