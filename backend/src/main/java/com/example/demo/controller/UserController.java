package com.example.demo.controller;

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
import com.example.demo.model.Views;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import com.example.demo.model.AuthRequest;
import com.example.demo.util.JwtUtil;
import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserController {
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserRepository userRepo;

	@GetMapping("/users")
	@JsonView(Views.External.class)
	public List<User> getAllUsersWithoutPass() {
		return userService.getAllUsers();
	}
	
	@GetMapping("/usersmanager")
	@JsonView(Views.Internal.class)
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}


	@GetMapping("/users/{id}")
	public User getUserById(@PathVariable("id") Long id) {
		return userService.getUserById(id);
	}

	@GetMapping("/users/search")
	public List<User> getUsersWithUsername(@RequestParam("username") String username) {

		return userService.getUsersWithUsernameLike(username);
	}

	@PostMapping("/users")
	public ResponseEntity<Object> addNewUsers(@RequestBody User user) {
		//Special cases must use try catch
		
		userService.addNewUser(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId())
				.toUri();
		return ResponseEntity.accepted().build();
		
	}

	@PostMapping("/authenticate")
	public String generateToken(@RequestBody AuthRequest authrequest) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authrequest.getUsername(), authrequest.getPassword()));
		}
		catch (Exception e) {
			throw new Exception("Invalid username and password");
		}
		
		return jwtUtil.generateToken(authrequest.getUsername());
	}
	
	@PutMapping("/users/{id}")
	public ResponseEntity<Object> updateUser(@RequestBody User user, @PathVariable("id") Long id) {
		//Special cases must use try catch
		userService.updateUser(user, id);
		return ResponseEntity.accepted().build();
		
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return ResponseEntity.status(200).build();
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public List<User> getUsersByRole(@RequestParam(name = "role", required = false, defaultValue = "employee")String role){
		List<User> users = userService.getUsersByRoles(role);
		if(users.isEmpty())
			return null;
		return users;
	}

	@GetMapping("/roles")
	public List<Role> getAllRoles(){
		return roleService.getAllRoles();
	}

	@PostMapping("/role")
	public Role addRole(@RequestBody Role role){
		roleService.addNewRole(role);
		return role;
	}

}
