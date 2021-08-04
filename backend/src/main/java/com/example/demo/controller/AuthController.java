package com.example.demo.controller;


import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.Email;

import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.Exception.WrongBodyException;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.EmailService;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import com.example.demo.service.Impl.EmailServiceImpl;
import com.example.demo.model.AuthRequest;
import com.example.demo.model.AuthResponse;
import com.example.demo.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

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
    private EmailServiceImpl emailService;
    
	private ResponseEntity<?> createAuthenticationToken(User user) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Incorrect username or password"
                    , HttpStatus.FORBIDDEN);
        }

        final String jwt = jwtUtil.generateToken(user.getUsername());

        return new ResponseEntity<>(new AuthResponse(jwt), HttpStatus.OK);
    }
    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody Map<String, Object> payload){
        String username = null;
        String password = null;
        try {
            username = payload.get("username").toString();
            password = payload.get("password").toString();
        }catch(Exception e){
            return new ResponseEntity<>(new Exception("Missing value field !")
                    , HttpStatus.FORBIDDEN);
        }
        User user = new User(username, password);
        return createAuthenticationToken(user);
    }
    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody Map<String, Object> payload) throws Exception {
        String email = null;
        String username = null;
        String password = null;
        try {
            email = payload.get("email").toString();
            password = payload.get("password").toString();
            username = payload.get("username").toString();
        } catch(Exception e){
            return new ResponseEntity<>(new Exception("Missing value field !")
                    , HttpStatus.FORBIDDEN);
        }
        if(!userRepo.findByEmail(email).isEmpty()) return new ResponseEntity<>(new Exception(
            "There is already an account with the email address: " + email), HttpStatus.FORBIDDEN); 
        User user = new User(username, password, email);
        try{
            userRepo.save(user);
        }catch(Exception e){
            return new ResponseEntity<>(new Exception("Couldn't save to database"),HttpStatus.FORBIDDEN);
        }
            ObjectNode objectNode = mapper.createObjectNode();
            objectNode.put("status", true);
            objectNode.put("message", "Account creation successful");

            return new ResponseEntity<>(objectNode, HttpStatus.OK);
    }

    @RequestMapping(value = "/confirm", method= RequestMethod.POST)
    @Transactional
    public ResponseEntity<?> confirmToken(@RequestBody Map<String, Object> payload) {
        String email = null;
        try {
            email = payload.get("email").toString();
        } catch (Exception e) {
            return new ResponseEntity<>(new Exception("Missing email !")
                    , HttpStatus.FORBIDDEN);
        }
        User user = userRepo.findByEmail(email).get(0);
        ObjectNode objectNode = mapper.createObjectNode();
        if (generateToken(user)) {
            String link = "http://localhost:8080/api/auth/confirm?token=" + user.getToken();
            emailService.send(
                    user.getEmail(),
                    emailService.buildEmail(user.getUsername(), link));
            objectNode.put("status", true);
            objectNode.put("message", "Email sent");
            return new ResponseEntity<>(objectNode, HttpStatus.OK);
        }
        objectNode.put("status", false);
        objectNode.put("message", "Token have not expired or user already verified");
        return new ResponseEntity<>(objectNode, HttpStatus.OK);
    }
    @RequestMapping(value = "/confirm", method= RequestMethod.GET)
    @Transactional
    public ResponseEntity<?> confirmToken(@RequestParam(name= "token", required=true) String token){
        User userByToken = null;
        ObjectNode objectNode = mapper.createObjectNode();
        try {
            UUID newToken = UUID.fromString(token);
            userByToken = userRepo.findByToken(newToken).get(0);
        }catch(Exception e){
            return new ResponseEntity<>(new Exception("Token not found !")
                    , HttpStatus.FORBIDDEN);
        }
        // UUID userToken = userByToken.getToken();
        // LocalDateTime userExpired = userByToken.getUpdatedAt();
        // if(!userToken.equals(UUID.fromString(token)) || !userExpired.isBefore(LocalDateTime.now())){
        //     objectNode.put("status", false);
        //     objectNode.put("message", "Token doesn't match or have not expired yet !");
        //     return new ResponseEntity<>(objectNode, HttpStatus.CONFLICT);
        // }
        objectNode.put("status", true);
        objectNode.put("message", "Email verified");
        return new ResponseEntity<>(objectNode, HttpStatus.OK);
    }
    

    private boolean generateToken(User user){
        if((user.getToken() == null)){
            user.setToken(UUID.randomUUID());
            return true;
        }
        return false;
    }
}
