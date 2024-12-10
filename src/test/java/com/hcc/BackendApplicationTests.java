/*package com.hcc;

import com.hcc.entities.Assignment;
import com.hcc.entities.User;
import com.hcc.enums.AssignmentStatusEnum;
import com.hcc.repositories.AssignmentRepository;
import com.hcc.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BackendApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Test
    void contextLoads() {
        // Create a unique username for the test user
        String uniqueUsername = "testuser" + System.currentTimeMillis();

        // Create the User without using LocalDate or constructors
        User user = new User();  // Use default constructor
        user.setUsername(uniqueUsername);  // Set the username
        user.setPassword("password123");  // Set the password

        // Save the user to the database
        userRepository.save(user);

        // Create the assignments with the correct constructor using the enum
        Assignment assignment1 = new Assignment(AssignmentStatusEnum.PENDING_SUBMISSION, 1, "https://github.com/test/repo1", "main", null, user);
        Assignment assignment2 = new Assignment(AssignmentStatusEnum.SUBMITTED, 2, "https://github.com/test/repo2", "dev", "https://video.com/review1", user);

        // Save assignments to the database
        assignmentRepository.saveAll(List.of(assignment1, assignment2));

        // Use a query to fetch the user and eagerly load the assignments
        User retrievedUser = userRepository.findByIdWithAssignments(user.getId());

        // Assertions to verify correctness
        assertNotNull(retrievedUser, "User should not be null after saving to the database");
        assertEquals(2, retrievedUser.getAssignments().size(), "User should have 2 assignments");
    }
}*/



