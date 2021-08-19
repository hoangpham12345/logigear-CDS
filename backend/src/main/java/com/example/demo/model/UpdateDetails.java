package com.example.demo.model;

import java.time.LocalDate;

public class UpdateDetails {
	
	private Long id;
	private String username;
	private String password;
	private String email;
	
	private String address;
	private LocalDate birthday;
	private String fullname;
	private String phone;
	
	
	public UpdateDetails() {
		super();
	}
	
	public UpdateDetails(Long id, String username, String password, String email, String address, LocalDate birthday,
			String fullname, String phone) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.address = address;
		this.birthday = birthday;
		this.fullname = fullname;
		this.phone = phone;
	}
	
	public UpdateDetails(String username, String password, String email) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public LocalDate getBirthday() {
		return birthday;
	}
	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
