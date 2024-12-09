package com.hcc;

import com.hcc.entities.Assignment;
import com.hcc.entities.User;
import com.hcc.repositories.AssignmentRepository;
import com.hcc.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class BackendApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AssignmentRepository assignmentRepository;

	@Test
	void contextLoads() {
		String uniqueUsername = "testuser" + System.currentTimeMillis(); // Generate a unique username
		User user = new User(LocalDate.now(), uniqueUsername, "password123");
		userRepository.save(user);

		Assignment assignment1 = new Assignment("In Progress", 1, "https://github.com/test/repo1", "main", null, user);
		Assignment assignment2 = new Assignment("Submitted", 2, "https://github.com/test/repo2", "dev", "https://video.com/review1", user);

		assignmentRepository.saveAll(List.of(assignment1, assignment2));

		User retrievedUser = userRepository.findById(user.getId()).orElse(null);
		assert retrievedUser != null;
		assert retrievedUser.getAssignments().size() == 2;
	}
}

