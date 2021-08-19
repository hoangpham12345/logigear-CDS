package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.User;
import com.example.demo.model.UserDetails;

public interface UserDetailRepository extends JpaRepository<UserDetails, Long> {
	
	@Query(value = "SELECT * FROM user_details u WHERE u.address = ?1", nativeQuery = true)
	UserDetails findByAddress(String address);
	
}
