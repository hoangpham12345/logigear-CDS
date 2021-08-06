package com.example.demo.service.Impl;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.Exception.WrongBodyException;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javassist.NotFoundException;
import javassist.bytecode.stackmap.BasicBlock.Catch;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleService roleService;

	private String userRole = "employee";

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAllUsers();
	}

	@Override
	public void addNewUser(User user) {
		try {
			Role defaultRole = roleService.getRoleByName(userRole);
			userRepository.save(user);
			user.getRoles().add(defaultRole);
			userRepository.save(user);
		} catch (Exception e) {
			throw new WrongBodyException("wrong body");
		}
	}

	@Override
	public void updateUser(User user, Long id) {
		Optional<User> userOptional = userRepository.findById(id);

		if (!userOptional.isPresent()) {
			throw new ResourceNotFoundException("user not found with id = " + id);
		}

		try {
			if (user.getEmail().length() == 0) {
				throw new EntityNotFoundException();
			} else if (user.getUsername().length() == 0) {
				throw new EntityNotFoundException();
			}

			User updatedUser = userOptional.get();
			updatedUser.setEmail(user.getEmail());
			updatedUser.setUsername(user.getUsername());
			userRepository.save(updatedUser);
		} catch (EntityNotFoundException e) {
			throw new WrongBodyException("username or email are not nullable");
		} catch (Exception e) {
			throw new WrongBodyException("wrong body");
		}

	}
	


	@Override
	public User getUserById(Long id) {

		Optional<User> userOptional = userRepository.findById(id);
		if (!userOptional.isPresent()) {
			throw new ResourceNotFoundException("user not found with id = " + id);
		}
		return userOptional.get();
	}

	@Override
	public List<User> getUsersWithUsernameLike(String username) {
		if (userRepository.findByUsernameContaining(username).isEmpty()) {
			throw new ResourceNotFoundException("user not found with username = " + username);
		}
		return userRepository.findByUsernameContaining(username);
	}

	@Override
	public void deleteUser(Long id) {
		Optional<User> userOptional = userRepository.findById(id);
		if (!userOptional.isPresent()) {
			throw new ResourceNotFoundException("user not found with id = " + id);
		}
		userRepository.deleteById(id);
	}

	@Override
	public List<User> getUsersByRoles(String roleName) {
		Role role = roleService.getRoleByName(roleName);
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		List<User> users = userRepository.findUSerByRole(roles);
		return users;
	}

}
