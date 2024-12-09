package com.hcc.controllers;

import com.hcc.dto.AssignmentResponseDto;
import com.hcc.entities.Assignment;
import com.hcc.entities.User;
import com.hcc.enums.AssignmentStatusEnum;  // Import your enum
import com.hcc.exceptions.ResourceNotFoundException;
import com.hcc.repositories.AssignmentRepository;
import com.hcc.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private UserRepository userRepository;

    // Get all assignments
    @GetMapping
    public List<AssignmentResponseDto> getAllAssignments() {
        List<Assignment> assignments = assignmentRepository.findAll();
        return assignments.stream()
                .map(AssignmentResponseDto::new)
                .collect(Collectors.toList());
    }

    // Create a new assignment
    @PostMapping
    public ResponseEntity<AssignmentResponseDto> createAssignment(@RequestBody Assignment assignment) {
        try {
            // Ensure that status is valid
            AssignmentStatusEnum statusEnum = AssignmentStatusEnum.fromString(assignment.getStatus());

            if (statusEnum == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AssignmentResponseDto("Invalid status: " + assignment.getStatus()));
            }

            assignment.setStatus(statusEnum.getStatus()); // Set the status as the valid enum

            // Find user by username
            User user = userRepository.findByUsername(assignment.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found with username: " + assignment.getUsername()));

            assignment.setUser(user);

            // Save the assignment
            Assignment savedAssignment = assignmentRepository.save(assignment);

            return ResponseEntity.ok(new AssignmentResponseDto(savedAssignment));

        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AssignmentResponseDto("Error: " + e.getMessage()));
        }
    }




    // Get a specific assignment by ID
    @GetMapping("/{id}")
    public ResponseEntity<AssignmentResponseDto> getAssignmentById(@PathVariable Long id) {
        try {
            Assignment assignment = assignmentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Assignment not found with ID: " + id));
            return ResponseEntity.ok(new AssignmentResponseDto(assignment));
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AssignmentResponseDto("Assignment not found"));
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Assignment> updateAssignment(@PathVariable Long id, @RequestBody Assignment updatedAssignment) {
        Optional<Assignment> existingAssignment = assignmentRepository.findById(id);

        if (existingAssignment.isPresent()) {
            Assignment assignment = existingAssignment.get();
            assignment.setStatus(updatedAssignment.getStatus());
            assignment.setNumber(updatedAssignment.getNumber());
            assignment.setGithubUrl(updatedAssignment.getGithubUrl());
            assignment.setBranch(updatedAssignment.getBranch());
            assignment.setReviewVideoUrl(updatedAssignment.getReviewVideoUrl());

            User user = userRepository.findByUsername(updatedAssignment.getUsername())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            assignment.setUser(user);

            Assignment savedAssignment = assignmentRepository.save(assignment);
            return ResponseEntity.ok(savedAssignment);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Not found if assignment doesn't exist
        }
    }


}





