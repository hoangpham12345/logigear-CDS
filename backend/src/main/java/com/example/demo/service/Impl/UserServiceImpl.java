package com.example.demo.service.Impl;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.Email;

import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.Exception.WrongBodyException;
import com.example.demo.model.UpdateDetails;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.UserDetails;
import com.example.demo.repository.UserDetailRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.spring5.util.DetailedError;

import javassist.NotFoundException;
import javassist.bytecode.stackmap.BasicBlock.Catch;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserDetailRepository userDetailRepository;

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserService userService;

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAllUsers();
	}
	
	@Override
	public void addNewUser(User user) {
		try {
			Role defaultRole = roleService.getRoleByName("employee");
			user.getRoles().add(defaultRole);
			userRepository.save(user);
			
			UserDetails userDetails = new UserDetails("default",LocalDate.of(2000,01,01), "default", "default");
			userDetails.setUser(user);
			userDetailRepository.save(userDetails);
			
		} catch (Exception e) {
			throw new WrongBodyException("wrong body");
		}
	}
	
	@Override
	public void updateUser(UpdateDetails details, Long id) {
		Optional<User> userOptional = userRepository.findById(id);
		User updatedUser = userOptional.get();
		Optional<UserDetails> userDetailsOptional = userDetailRepository.findById(id);
		UserDetails updatedUserDetails = userDetailsOptional.get();
		
		if (!userOptional.isPresent()) {
			throw new ResourceNotFoundException("user not found with id = " + id);
		}

		try {
			if (!details.getEmail().isEmpty())
				updatedUser.setEmail(details.getEmail());
			
			if (!details.getAddress().isEmpty())
				updatedUserDetails.setAddress(details.getAddress());
			if (!details.getFullname().isEmpty())
				updatedUserDetails.setFullname(details.getFullname());
			if (!details.getPhone().isEmpty())
				updatedUserDetails.setPhone(details.getPhone());
			if (!details.getBirthday().isEqual(updatedUserDetails.getBirthday()))
				updatedUserDetails.setBirthday(details.getBirthday());
			
			userDetailRepository.save(updatedUserDetails);
			userRepository.save(updatedUser);
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
	public User getUserWithNameAndPass(String username, String password) {
		// TODO Auto-generated method stub
		User user = userRepository.findByUsername(username);
		user = userRepository.findByUsernameAndPassword(username, user.getPassword());
		return user;
	}

}
