package com.example.demo.controller;

import java.net.URI;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserController {
  @Autowired
  private UserService userService;

  @Autowired
  private RoleService roleService;

  @GetMapping("/users")
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("/users/{id}")
	public ResponseEntity<Object> getUserById(@PathVariable("id") Long id) {
		// if (userService.getUserById( id) == null) {        
	  //       return ResponseEntity.status(404).body("id not found");
	  //   }
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
		} 
    catch (EntityNotFoundException e) {
      return ResponseEntity.status(404).build();
    } 
    catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping("/users/search")
	public ResponseEntity<Object> getUsersWithUsernameLike(@RequestParam("username") String username) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userService.getUsersWithUsernameLike(username));
		} 
    catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

  @PostMapping("/users")
  public ResponseEntity<Object> addNewUsers(@RequestBody User user) {
    try {
      Role defaultRole = roleService.getRoleById(2L);
      user.getRoles().add(defaultRole);
      userService.addNewUser(user);
      URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId())
          .toUri();
      return ResponseEntity.created(location).build();
    } 
    catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

	@PutMapping("/users/{id}")
	public ResponseEntity<Object> updateUser(@RequestBody User user, @PathVariable("id") Long id) {
		try {
		 	userService.updateUser(user, id);
		 	return ResponseEntity.accepted().build();
		} 
    catch (EntityNotFoundException e) {
		 	return ResponseEntity.status(404).build();
		} 
    catch (Exception e) {
		 	return ResponseEntity.badRequest().build();
		}
		
	}

  @DeleteMapping("/users/{id}")
  public ResponseEntity<Object> deleteUser(@PathVariable Long id){
    try {
      userService.deleteUser(id);
      return ResponseEntity.status(200).build();
    }
    catch (EntityNotFoundException e) {
      return ResponseEntity.status(404).build();
   } 
    catch(Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
