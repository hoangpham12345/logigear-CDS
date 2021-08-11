package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
  
	@Query(value = "SELECT * FROM users u WHERE u.username = ?1", nativeQuery = true)
    User findByUsername(String username);
    
    @Query(value = "SELECT * FROM users ", nativeQuery = true)
    List <User> findAllUsers();

    @Query(value = "DELETE FROM users WHERE id = ?1", nativeQuery = true)
    User deleteUserById(Long id);

    List<User> findByUsernameContaining(String username);

    List<User> findByEmail(String email);
    
    @Query(value = "SELECT * FROM users u WHERE u.username = ?1 AND u.password = ?2", nativeQuery = true)
    User findByUsernameAndPassword(String name, String password);
}
