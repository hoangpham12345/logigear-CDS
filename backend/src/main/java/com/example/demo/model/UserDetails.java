package com.example.demo.model;


import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonView;

@Entity(name = "UserDetails")
@Table(name = "user_details", uniqueConstraints = { @UniqueConstraint(name = "id_unique", columnNames = "id")})

public class UserDetails {

	@Id
	@Column(name = "id", updatable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@JsonView(Views.External.class)
	private Long id;
	
	@Column(length = 75, nullable = false)
	@JsonView(Views.External.class)
	private String address;
	
	@Column(length = 50, nullable = false)
	@JsonView(Views.External.class)
	private LocalDate birthday;
	
	@Column(length = 50, nullable = false)
	@JsonView(Views.External.class)
	private String fullname;
	
	@Column(nullable = false)
	@Size(min = 0, max = 10)
	@JsonView(Views.External.class)
	private String phone;
	
	@OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "id")
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public UserDetails(Long id, String address, LocalDate birthday, String fullname, String phone) {
		super();
		this.id = id;
		this.address = address;
		this.birthday = birthday;
		this.fullname = fullname;
		this.phone = phone;
	}
	
	public UserDetails(String address, LocalDate birthday, String fullname, String phone) {
		super();
		this.address = address;
		this.birthday = birthday;
		this.fullname = fullname;
		this.phone = phone;
	}
	
	
	public UserDetails() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public void setBirthday(LocalDate brithday) {
		this.birthday = brithday;
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


