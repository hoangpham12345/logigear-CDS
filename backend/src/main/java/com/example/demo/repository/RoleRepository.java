package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
  // @Query(value = "SELECT r.* FROM roles r INNER JOIN users_roles ur ON r.id = ur.role_id WHERE ur.user_id = ?1", nativeQuery = true)
  // public List<Role> findRolesByUserID(Long id);

  @Query(value = "SELECT * FROM roles r WHERE r.name = ?1", nativeQuery = true)
  Optional<Role> findByName(String name);

  @Query(value = "DELETE FROM roles WHERE name = ?1", nativeQuery = true)
  Role deleteRollByName(String name);

  @Query("FROM Role WHERE name = ?1")
  List<Role> findRolesByName(String name);

}