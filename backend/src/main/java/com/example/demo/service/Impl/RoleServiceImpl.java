package com.example.demo.service.Impl;

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
}
