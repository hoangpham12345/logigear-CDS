package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.UpdateDetails;
import com.example.demo.model.User;
import com.example.demo.model.UserDetails;

import org.springframework.data.jpa.repository.Modifying;

public interface UserService {
    public List<User> getAllUsers();

    public void addNewUser(User user);

	public void updateUser(UpdateDetails details, Long id);

	public User getUserById(Long id);

	public List<User> getUsersWithUsernameLike(String username);

    public User getUserWithNameAndPass(String username, String password);

    @Modifying
    public void deleteUser(Long id);
}
