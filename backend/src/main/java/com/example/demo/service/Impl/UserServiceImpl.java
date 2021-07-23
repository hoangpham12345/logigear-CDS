package com.example.demo.service.Impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public void addNewUser(User user) {
    userRepository.save(user);
  }

  @Override
	public void updateUser(User user, Long id) {
//		return userRepo.save(user);
		// if (userRepository.findById(id).isPresent()) {
		// 	User userTemp = userRepository.findById(id).get();
		// 	userTemp.setUsername(user.getUsername());
		// 	userTemp.setPassword(user.getPassword());
		// 	userTemp.setEmail(user.getEmail());
		// 	return userRepository.save(userTemp);
		// }
		// return null;
    Optional<User> userOptional = userRepository.findById(id);
    if (!userOptional.isPresent()){
      throw new EntityNotFoundException("User with" + id + "is not existed.");
    }
    User updatedUser = userOptional.get();
    updatedUser.setEmail(user.getEmail());
    updatedUser.setUsername(user.getUsername());
    updatedUser.setPassword(user.getPassword());
    userRepository.save(updatedUser);
	}

	@Override
	public User getUserById(Long id) {
		// return userRepo.findById(id).get();
		// if (userRepository.findById(id).isPresent()) {
		// 	return userRepository.findById(id).get();
		// }
		// return null;
    Optional<User> userOptional = userRepository.findById(id);
    if (!userOptional.isPresent()){
      throw new EntityNotFoundException("User with" + id + "is not existed.");
    }
    return userOptional.get();
	}

	@Override
	public List<User> getUsersWithUsernameLike(String username) {
		return userRepository.findByUsernameContaining(username);
		// if (userRepository.findByUsernameContaining(username).isEmpty()) {
		// 	return null;
		// }

		// return userRepository.findByUsernameContaining(username);
	}

  @Override
  public void deleteUser(Long id) {
  //   Optional<User> userOptional = userRepository.findById(id);
  //   if (!userOptional.isPresent()){
  //     throw new EntityNotFoundException("User with" + id + "is not existed.");
  //   }
  //   User deletedUser = userOptional.get();
  //   deletedUser.getRoles().clear();
  //   userRepository.save(deletedUser);
  //   userRepository.deleteUserById(id);
    userRepository.deleteById(id);
  }

}
