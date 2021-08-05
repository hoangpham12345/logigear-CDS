package com.example.demo.service.Impl;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.Role;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

  @Autowired
  private RoleRepository roleRepository;

  public Role getRoleById(Long id){
    Optional<Role> roleOptional = roleRepository.findById(id);
    if (!roleOptional.isPresent()){
      throw new IllegalStateException("Id does not exist.");
    }
    return roleOptional.get();
  }

  @Override
  public Role getRolesByName(String name) {
    List<Role> roles = roleRepository.findRolesByName(name);
    if(!roles.isEmpty())
      return roles.get(0);
    return null;
  }

  @Override
  public void addNewRole(Role role) {
    // TODO Auto-generated method stub
      roleRepository.save(role);
  }

  @Override
  public List<Role> getAllRoles() {
    // TODO Auto-generated method stub
    return roleRepository.findAll();
  }
}
