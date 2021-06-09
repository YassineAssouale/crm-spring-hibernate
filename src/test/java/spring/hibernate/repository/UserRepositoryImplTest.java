package spring.hibernate.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import crm.hibernate.config.AppConfig;
import crm.hibernate.model.User;
import crm.hibernate.repository.impl.UserRepositoryImpl;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AppConfig.class }, loader = AnnotationConfigContextLoader.class)
@Transactional
@DirtiesContext
@TestMethodOrder(OrderAnnotation.class)
class UserRepositoryImplTest {

	Logger logger = LoggerFactory.getLogger(UserRepositoryImplTest.class);

	@Autowired
	private UserRepositoryImpl userRepository;

	@Test
	@Order(1)
	void givenUsernameAndPassword_whenCallingTypedQueryMethod_thenReturnUser() {
		User user = userRepository.getByUsernameAndPassword("mgilbert", "1234");
		Assertions.assertNotNull(user, "No user found for username and password");
	}

	@Test
	@Order(2)
	void givenId_whenCallingTypedQueryMethod_thenReturnUser() {
		User user = userRepository.getById(1);
		Assertions.assertNotNull(user, "No user found for id 1");
	}

	@Test
	@Order(3)
	void whenCallingTypedQueryMethod_thenReturnListOfUsers() {
		List<User> users = userRepository.getAll();
		Assertions.assertEquals(1, users.size(), "Wrong number of users");
	}

	@Test
	@Order(4)
	void givenUser_whenCallingTypedQueryMethodForCreate_thenReturnOK() {
		User newUser = new User();
		newUser.setUsername("mtest");
		newUser.setPassword("mtest");
		newUser.setMail("mtest@test.fr");

		List<User> users = userRepository.getAll();
		int numberOfUsersBeforeCreation = users.size();

		userRepository.createUser(newUser);

		List<User> usersAfterCreation = userRepository.getAll();
		int numberOfUsersAfterCreation = usersAfterCreation.size();
		Assertions.assertEquals(numberOfUsersBeforeCreation + 1, numberOfUsersAfterCreation);
	}

	@Test
	@Order(5)
	void givenUser_whenCallingTypedQueryMethodForUpdate_thenReturnOK() {

		User user = userRepository.getById(1);
		user.setMail("nouveauMail@test.fr");

		userRepository.updateUser(user);

		User updatedUser = userRepository.getById(1);
		Assertions.assertEquals("nouveauMail@test.fr", updatedUser.getMail());
	}

	@Test
	@Order(6)
	void givenUser_whenCallingTypedQueryMethodForDelete_thenReturnOK() {
		User user = userRepository.getById(1);

		userRepository.deleteUser(user);

		User deletedUser = userRepository.getById(1);
		logger.debug("Deleted User = {}", deletedUser);
		Assertions.assertNull(deletedUser, "Deleted order must be null");
	}

}
