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

  public Role getRoleByName(String name){
    Optional<Role> roleOptional = roleRepository.findByName(name);
    if (!roleOptional.isPresent()){
      throw new IllegalStateException("Name does not exist.");
    }
    return roleOptional.get();
  }

  @Override
  public Role addRole(String name) {
    Role role = new Role(name);
    roleRepository.save(role);
    return role;
  }
  
}
