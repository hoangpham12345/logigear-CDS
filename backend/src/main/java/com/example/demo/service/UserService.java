package com.example.demo.service;

import java.util.List;

import com.example.demo.model.User;

import org.springframework.data.jpa.repository.Modifying;

public interface UserService {
    public List<User> getAllUsers();

    public void addNewUser(User user);

	public void updateUser(User user, Long id);

	public User getUserById(Long id);

	public List<User> getUsersWithUsernameLike(String username);

    @Modifying
    public void deleteUser(Long id);
}
