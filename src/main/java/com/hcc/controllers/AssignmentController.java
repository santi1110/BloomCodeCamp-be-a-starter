package com.hcc.controllers;

import com.hcc.dto.AssignmentResponseDto;
import com.hcc.entities.Assignment;
import com.hcc.entities.User;
import com.hcc.enums.AssignmentStatusEnum;
import com.hcc.exceptions.ResourceNotFoundException;
import com.hcc.repositories.AssignmentRepository;
import com.hcc.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/assignments")
public class AssignmentController {

    private static final Logger logger = LoggerFactory.getLogger(AssignmentController.class);

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private UserRepository userRepository;

    // Get all assignments - Restricted to learners
    @GetMapping
    @PreAuthorize("hasRole('LEARNER')") // Only learners can access
    public List<AssignmentResponseDto> getAllAssignments() {
        List<Assignment> assignments = assignmentRepository.findAll();
        return assignments.stream()
                .map(AssignmentResponseDto::new)
                .collect(Collectors.toList());
    }

    // Get specific assignment by ID - Restricted to learners
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('LEARNER')") // Only learners can access
    public ResponseEntity<AssignmentResponseDto> getAssignmentById(@PathVariable Long id) {
        return assignmentRepository.findById(id)
                .map(assignment -> ResponseEntity.ok(new AssignmentResponseDto(assignment)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new AssignmentResponseDto("Assignment not found")));
    }

    // Create a new assignment - Restricted to learners
    @PostMapping(consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasRole('LEARNER')") // Only learners can create assignments
    public ResponseEntity<AssignmentResponseDto> createAssignment(@Valid @RequestBody Assignment assignment) {
        logger.info("Received assignment: " + assignment);  // Log incoming assignment

        try {
            // Ensure the status is correctly mapped from the string to the enum
            AssignmentStatusEnum statusEnum = AssignmentStatusEnum.fromString(assignment.getStatus().toString());
            assignment.setStatus(statusEnum);

            // Retrieve the user by username
            User user = userRepository.findByUsername(assignment.getUsername())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + assignment.getUsername()));

            // Set user and save the assignment
            assignment.setUser(user);
            Assignment savedAssignment = assignmentRepository.save(assignment);

            return ResponseEntity.ok(new AssignmentResponseDto(savedAssignment));
        } catch (Exception e) {
            logger.error("Error creating assignment: ", e);  // Log error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AssignmentResponseDto("Error: " + e.getMessage()));
        }
    }

    // Update an existing assignment - Restricted to learners
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('LEARNER')") // Only learners can update assignments
    public ResponseEntity<Assignment> updateAssignment(@PathVariable Long id, @RequestBody Assignment updatedAssignment) {
        return assignmentRepository.findById(id)
                .map(existingAssignment -> {
                    existingAssignment.setStatus(updatedAssignment.getStatus());
                    existingAssignment.setNumber(updatedAssignment.getNumber());
                    existingAssignment.setGithubUrl(updatedAssignment.getGithubUrl());
                    existingAssignment.setBranch(updatedAssignment.getBranch());
                    existingAssignment.setReviewVideoUrl(updatedAssignment.getReviewVideoUrl());
                    assignmentRepository.save(existingAssignment);
                    return ResponseEntity.ok(existingAssignment);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete an assignment - Restricted to admins
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // Only admins can delete assignments
    public ResponseEntity<String> deleteAssignment(@PathVariable Long id) {
        try {
            // Check if the assignment exists
            if (assignmentRepository.existsById(id)) {
                // Delete the assignment
                assignmentRepository.deleteById(id);
                return ResponseEntity.ok("Assignment deleted successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Assignment not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting assignment: " + e.getMessage());
        }
    }
}
