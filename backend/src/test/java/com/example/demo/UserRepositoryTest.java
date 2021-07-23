package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)

public class UserRepositoryTest {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	public void testCreateUser() {
		User user = new User();
		user.setUsername("admin");
		user.setPassword("123456789");
		user.setEmail("admin@mail.com");

		User saveUser = userRepository.save(user);
		
		User existUser = testEntityManager.find(User.class, saveUser.getId());
		
		assertThat(user.getUsername()).isEqualTo(existUser.getUsername());
	}
}
