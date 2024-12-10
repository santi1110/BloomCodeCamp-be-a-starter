/*package com.hcc;

import com.hcc.controllers.AssignmentController;
import com.hcc.entities.Assignment;
import com.hcc.entities.User;
import com.hcc.repositories.AssignmentRepository;
import com.hcc.repositories.UserRepository;
import com.hcc.enums.AssignmentStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest  // Load the full Spring context
public class AssignmentControllerTest {

    @Autowired
    private MockMvc mockMvc;  // Inject MockMvc

    @MockBean
    private AssignmentRepository assignmentRepository;  // Mock the repository

    @MockBean
    private UserRepository userRepository;  // Mock the user repository

    @InjectMocks
    private AssignmentController assignmentController;  // Inject the controller

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
    }

    @Test
    public void testCreateAssignment() throws Exception {
        // Create a mock user
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");

        // Create the assignment and associate it with the user
        Assignment assignment = new Assignment();
        assignment.setStatus(AssignmentStatusEnum.IN_REVIEW);  // Set enum status directly
        assignment.setNumber(3);
        assignment.setGithubUrl("https://github.com/test/repo3");
        assignment.setBranch("main");
        assignment.setReviewVideoUrl(null);
        assignment.setUsername("testuser");
        assignment.setUser(user);  // Associate user with assignment

        // Mock the userRepository to return a valid user
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(assignmentRepository.save(any(Assignment.class))).thenReturn(assignment);

        // Perform the POST request
        mockMvc.perform(post("/assignments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\":\"IN_REVIEW\",\"number\":3,\"githubUrl\":\"https://github.com/test/repo3\",\"branch\":\"main\",\"reviewVideoUrl\":null,\"username\":\"testuser\"}"))
                .andExpect(status().isOk())  // Expect HTTP 200
                .andExpect(jsonPath("$.status").value("IN_REVIEW"))
                .andExpect(jsonPath("$.number").value(3));
    }

    @Test
    public void testGetAssignmentById() throws Exception {
        // Create a mock user and assignment
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");

        Assignment assignment = new Assignment();
        assignment.setId(1L);
        assignment.setStatus(AssignmentStatusEnum.IN_REVIEW);  // Set enum status directly
        assignment.setNumber(3);
        assignment.setGithubUrl("https://github.com/test/repo3");
        assignment.setBranch("main");
        assignment.setReviewVideoUrl("https://video.url/review");
        assignment.setUser(user);  // Set user for the assignment

        // Mock repository calls
        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(assignment));

        // Perform the GET request
        mockMvc.perform(get("/assignments/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("IN_REVIEW"))
                .andExpect(jsonPath("$.number").value(3))
                .andExpect(jsonPath("$.githubUrl").value("https://github.com/test/repo3"));
    }

    @Test
    public void testGetAssignmentByInvalidId() throws Exception {
        // Mock repository to return empty for a non-existing assignment
        when(assignmentRepository.findById(100L)).thenReturn(Optional.empty());

        // Perform the GET request for invalid ID
        mockMvc.perform(get("/assignments/{id}", 100))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateAssignment() throws Exception {
        // Create a mock user and existing assignment
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");

        Assignment assignment = new Assignment();
        assignment.setId(1L);
        assignment.setStatus(AssignmentStatusEnum.PENDING_SUBMISSION);  // Set enum status directly
        assignment.setNumber(1);
        assignment.setGithubUrl("https://github.com/test/repo");
        assignment.setBranch("main");
        assignment.setReviewVideoUrl(null);
        assignment.setUser(user);

        // Mock repository calls
        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(assignment));
        when(assignmentRepository.save(any(Assignment.class))).thenReturn(assignment);

        // Perform the PUT request
        mockMvc.perform(put("/assignments/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\":\"IN_REVIEW\",\"number\":3,\"githubUrl\":\"https://github.com/test/repo3\",\"branch\":\"main\",\"reviewVideoUrl\":null,\"username\":\"testuser\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("IN_REVIEW"))
                .andExpect(jsonPath("$.number").value(3));
    }

    @Test
    public void testDeleteAssignment() throws Exception {
        // Create a mock user and assignment
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");

        Assignment assignment = new Assignment();
        assignment.setId(1L);
        assignment.setStatus(AssignmentStatusEnum.PENDING_SUBMISSION);  // Set enum status directly
        assignment.setNumber(1);
        assignment.setGithubUrl("https://github.com/test/repo");
        assignment.setBranch("main");
        assignment.setReviewVideoUrl(null);
        assignment.setUser(user);

        // Mock repository calls
        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(assignment));
        doNothing().when(assignmentRepository).deleteById(1L);

        // Perform the DELETE request
        mockMvc.perform(delete("/assignments/{id}", 1))
                .andExpect(status().isOk());
    }
}*/
