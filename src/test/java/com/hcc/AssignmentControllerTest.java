package com.hcc;

import com.hcc.controllers.AssignmentController;
import com.hcc.entities.Assignment;
import com.hcc.entities.User;
import com.hcc.enums.AssignmentStatusEnum;
import com.hcc.repositories.AssignmentRepository;
import com.hcc.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AssignmentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AssignmentRepository assignmentRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AssignmentController assignmentController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(assignmentController).build();
    }

    @Test
    public void testCreateAssignment() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password123");

        Assignment assignment = new Assignment();
        assignment.setId(1L);
        assignment.setStatus(AssignmentStatusEnum.PENDING_SUBMISSION);
        assignment.setNumber(3);
        assignment.setGithubUrl("https://github.com/test/repo3");
        assignment.setBranch("main");
        assignment.setUser(user);

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(assignmentRepository.save(any(Assignment.class))).thenReturn(assignment);

        String jsonPayload = "{"
                + "\"status\":\"PENDING_SUBMISSION\","
                + "\"number\":3,"
                + "\"githubUrl\":\"https://github.com/test/repo3\","
                + "\"branch\":\"main\","
                + "\"username\":\"testuser\"}";

        mockMvc.perform(post("/api/assignments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PENDING_SUBMISSION"))
                .andExpect(jsonPath("$.number").value(3));
    }

    @Test
    public void testGetAssignmentById() throws Exception {
        User user = new User();
        user.setUsername("testuser");

        Assignment assignment = new Assignment();
        assignment.setId(1L);
        assignment.setStatus(AssignmentStatusEnum.PENDING_SUBMISSION);
        assignment.setNumber(3);
        assignment.setGithubUrl("https://github.com/test/repo3");
        assignment.setBranch("main");
        assignment.setUser(user);

        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(assignment));

        mockMvc.perform(get("/api/assignments/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PENDING_SUBMISSION"))
                .andExpect(jsonPath("$.number").value(3));
    }

    @Test
    public void testGetAssignmentByInvalidId() throws Exception {
        when(assignmentRepository.findById(100L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/assignments/{id}", 100))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateAssignment() throws Exception {
        // Mock user
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password123");

        // Mock assignment
        Assignment existingAssignment = new Assignment();
        existingAssignment.setId(1L);
        existingAssignment.setStatus(AssignmentStatusEnum.PENDING_SUBMISSION);
        existingAssignment.setNumber(1);
        existingAssignment.setGithubUrl("https://github.com/test/repo");
        existingAssignment.setBranch("main");
        existingAssignment.setUser(user); // Associate user with the assignment

        // Mock repository behavior
        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(existingAssignment));
        when(assignmentRepository.save(any(Assignment.class))).thenAnswer(invocation -> {
            Assignment updatedAssignment = invocation.getArgument(0);
            existingAssignment.setStatus(updatedAssignment.getStatus());
            existingAssignment.setNumber(updatedAssignment.getNumber());
            existingAssignment.setGithubUrl(updatedAssignment.getGithubUrl());
            existingAssignment.setBranch(updatedAssignment.getBranch());
            return existingAssignment;
        });

        // JSON payload for the update request
        String jsonPayload = "{"
                + "\"status\":\"IN_REVIEW\","
                + "\"number\":3,"
                + "\"githubUrl\":\"https://github.com/test/repo3\","
                + "\"branch\":\"main\","
                + "\"reviewVideoUrl\":\"https://youtube.com/review-video\"}";

        // Perform the PUT request
        mockMvc.perform(put("/api/assignments/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk()) // Expect HTTP 200 OK
                .andExpect(jsonPath("$.status").value("IN_REVIEW"))
                .andExpect(jsonPath("$.number").value(3))
                .andExpect(jsonPath("$.githubUrl").value("https://github.com/test/repo3"))
                .andExpect(jsonPath("$.branch").value("main"))
                .andExpect(jsonPath("$.reviewVideoUrl").value("https://youtube.com/review-video"));
    }







    @Test
    public void testDeleteAssignment() throws Exception {
        when(assignmentRepository.existsById(1L)).thenReturn(true);
        doNothing().when(assignmentRepository).deleteById(1L);

        mockMvc.perform(delete("/api/assignments/{id}", 1))
                .andExpect(status().isOk());
    }
}
