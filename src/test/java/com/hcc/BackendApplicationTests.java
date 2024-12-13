package com.hcc;

import com.hcc.entities.Assignment;
import com.hcc.entities.User;
import com.hcc.enums.AssignmentStatusEnum;
import com.hcc.repositories.AssignmentRepository;
import com.hcc.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional // Ensures data is rolled back after each test
class BackendApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @BeforeEach
    void cleanDatabase() {
        // Clear all existing data before each test
        assignmentRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void contextLoads() {
        // Create a new user
        User user = new User();
        user.setUsername("unique_testuser");
        user.setPassword("password123");
        user = userRepository.save(user); // Save and retrieve the persisted user

        // Create two assignments linked to the user
        Assignment assignment1 = new Assignment(AssignmentStatusEnum.PENDING_SUBMISSION, 1, "https://github.com/test/repo1", "main", null, user);
        Assignment assignment2 = new Assignment(AssignmentStatusEnum.SUBMITTED, 2, "https://github.com/test/repo2", "dev", "https://video.com/review1", user);

        // Save the assignments
        assignmentRepository.save(assignment1);
        assignmentRepository.save(assignment2);

        // Retrieve all assignments from the repository
        List<Assignment> assignments = assignmentRepository.findAll();

        // Assertions to verify the database state
        assertNotNull(assignments, "Assignments should not be null");
        assertEquals(2, assignments.size(), "There should be 2 assignments in the database");
    }

}
