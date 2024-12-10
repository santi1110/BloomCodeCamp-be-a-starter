package com.hcc;

import com.hcc.controllers.AssignmentController;
import com.hcc.entities.Assignment;
import com.hcc.entities.User;
import com.hcc.repositories.AssignmentRepository;
import com.hcc.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AssignmentController.class)
public class AssignmentControllerTest {

    @Autowired
    private MockMvc mockMvc;  // Inject MockMvc

    @MockBean
    private AssignmentRepository assignmentRepository;  // Mock the repository hello

    @MockBean
    private UserRepository userRepository;  // Mock the user repository

    @InjectMocks
    private AssignmentController assignmentController;  // Inject the controller

    public AssignmentControllerTest() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
    }

    @Test
    public void testCreateAssignment() throws Exception {
        // Create a mock user and assignment
        User user = new User();
        user.setUsername("testuser");

        Assignment assignment = new Assignment("IN_REVIEW", 3, "https://github.com/test/repo3", "main", null, user);
        assignment.setUsername("testuser");

        // Mock the userRepository to return a valid user
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(assignmentRepository.save(any(Assignment.class))).thenReturn(assignment);

        mockMvc.perform(post("/assignments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\":\"IN_REVIEW\",\"number\":3,\"githubUrl\":\"https://github.com/test/repo3\",\"branch\":\"main\",\"reviewVideoUrl\":null,\"username\":\"testuser\"}"))
                .andExpect(status().isOk())  // Expect HTTP 200
                .andExpect(jsonPath("$.status").value("IN_REVIEW"))
                .andExpect(jsonPath("$.number").value(3));
    }





    @Test
    public void testGetAssignmentById() throws Exception {
        // Create mock user and assignment
        User user = new User();
        user.setUsername("testuser");

        Assignment assignment = new Assignment("In Progress", 3, "https://github.com/test/repo3", "main", null, user);
        assignment.setId(1L);

        // Mock repository calls
        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(assignment));

        // Perform the GET request
        mockMvc.perform(get("/assignments/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("In Progress"));
    }

    @Test
    public void testGetAssignmentByInvalidId() throws Exception {
        // Mock repository to return empty for a non-existing assignment
        when(assignmentRepository.findById(100L)).thenReturn(Optional.empty());

        // Perform the GET request for invalid ID
        mockMvc.perform(get("/assignments/{id}", 100))
                .andExpect(status().isNotFound());
    }
}




