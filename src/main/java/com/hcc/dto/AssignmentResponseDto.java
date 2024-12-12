package com.hcc.dto;

import com.hcc.entities.Assignment;
import com.hcc.enums.AssignmentStatusEnum;

public class AssignmentResponseDto {

    private Long id;
    private String status;
    private Integer number;
    private String githubUrl;
    private String branch;
    private String reviewVideoUrl;
    private String username;
    private AssignmentStatusEnum[] assignmentStatusEnums = AssignmentStatusEnum.values();

    // Constructor to initialize fields from Assignment entity
    public AssignmentResponseDto(Assignment assignment) {
        this.id = assignment.getId();
        this.status = assignment.getStatus().name(); // Convert enum to string
        this.number = assignment.getNumber();
        this.githubUrl = assignment.getGithubUrl();
        this.branch = assignment.getBranch();
        this.reviewVideoUrl = assignment.getReviewVideoUrl();
        this.username = assignment.getUser().getUsername(); // Extract username
    }

    // Constructor for error messages
    public AssignmentResponseDto(String message) {
        this.status = message;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getReviewVideoUrl() {
        return reviewVideoUrl;
    }

    public void setReviewVideoUrl(String reviewVideoUrl) {
        this.reviewVideoUrl = reviewVideoUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AssignmentStatusEnum[] getAssignmentStatusEnums() {
        return assignmentStatusEnums;
    }
}
