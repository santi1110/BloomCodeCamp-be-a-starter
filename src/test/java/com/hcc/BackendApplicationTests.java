/*
package com.hcc;

import com.hcc.entities.Assignment;
import com.hcc.entities.User;
import com.hcc.enums.AssignmentStatusEnum;
import com.hcc.repositories.AssignmentRepository;
import com.hcc.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BackendApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Test
    void contextLoads() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        user = userRepository.save(user);

        Assignment assignment1 = new Assignment(AssignmentStatusEnum.PENDING_SUBMISSION, 1, "https://github.com/test/repo1", "main", null, user);
        Assignment assignment2 = new Assignment(AssignmentStatusEnum.SUBMITTED, 2, "https://github.com/test/repo2", "dev", "https://video.com/review1", user);

        assignmentRepository.save(assignment1);
        assignmentRepository.save(assignment2);

        List<Assignment> assignments = assignmentRepository.findAll();
        assertNotNull(assignments, "Assignments should not be null");
        assertEquals(2, assignments.size(), "There should be 2 assignments in the database");
    }
}
*/
