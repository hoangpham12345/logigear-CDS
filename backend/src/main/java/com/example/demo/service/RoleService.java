package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Role;

public interface RoleService {
  public List<Role> getAllRoles();

  public Role getRoleById(Long id);

  public Role getRolesByName(String name);

  public void addNewRole(Role role);
}