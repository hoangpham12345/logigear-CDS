package com.example.demo.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import com.example.demo.model.AuthResponse;
import com.example.demo.util.JwtUtil;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
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

	@Autowired
	private ObjectMapper mapper;

	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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
	@JsonView(Views.External.class)
	public User getUserById(@PathVariable("id") Long id) {
		return userService.getUserById(id);
	}

	@GetMapping("/users/search")
	@JsonView(Views.External.class)
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

	@PostMapping("/auth/login")
	public ResponseEntity<?> login(@RequestBody Map<String, Object> payload){
        String username = null;
        String password = null;
		User user = null;
        try {
            username = payload.get("username").toString();
            password = payload.get("password").toString();
        }catch(Exception e){
            return new ResponseEntity<>(new Exception("Missing value field !")
                    , HttpStatus.FORBIDDEN);
        }
		user = new User(username, password);
		// System.out.println(user.getUsername());
        return createAuthenticationToken(user);
    }

	private ResponseEntity<?> createAuthenticationToken(User user) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Incorrect username or password"
                    , HttpStatus.FORBIDDEN);
        }
		try {
			user = userService.getUserWithNameAndPass(user.getUsername(), user.getPassword());
		}catch(Exception e){
			return new ResponseEntity<>(new Exception("Unable to find user account in the database !")
                    , HttpStatus.FORBIDDEN);
		}
        final String jwt = jwtUtil.generateToken(user.getUsername());

        return new ResponseEntity<>(new AuthResponse(user, jwt), HttpStatus.OK);
    }

	@PostMapping("/auth/signup")
    public ResponseEntity<?> register(@RequestBody Map<String, Object> payload) throws Exception {
        String email = null;
        String username = null;
        String password = null;
        try {
            email = payload.get("email").toString();
            password = payload.get("password").toString();
			password = bCryptPasswordEncoder.encode(password);
            username = payload.get("username").toString();
        } catch(Exception e){
            return new ResponseEntity<>(new Exception("Missing value field !")
                    , HttpStatus.FORBIDDEN);
        }
        if(!userRepo.findByEmail(email).isEmpty()) return new ResponseEntity<>(new Exception(
            "There is already an account with the email address: " + email), HttpStatus.FORBIDDEN); 
        User user = new User(username, password, email);
        try{
            userService.addNewUser(user);
        }catch(Exception e){
            return new ResponseEntity<>(new Exception("Couldn't save to database"),HttpStatus.FORBIDDEN);
        }
            ObjectNode objectNode = mapper.createObjectNode();
            objectNode.put("status", true);
            objectNode.put("message", "Account creation successful");

            return new ResponseEntity<>(objectNode, HttpStatus.OK);
    }
}
