package com.hcc.entities;

import com.hcc.enums.AssignmentStatusEnum;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "assignments")
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Integer number;

    @Column(name = "github_url", nullable = false)
    private String githubUrl;

    @Column(nullable = false)
    private String branch;

    @Column(name = "review_video_url")
    private String reviewVideoUrl;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Transient // This ensures that this field is not mapped to the database
    private String username;  // Temporary field to handle the username during the POST request

    public Assignment() {
        // No-args constructor
    }

    public Assignment(String status, Integer number, String githubUrl, String branch, String reviewVideoUrl, User user) {
        this.status = status;
        this.number = number;
        this.githubUrl = githubUrl;
        this.branch = branch;
        this.reviewVideoUrl = reviewVideoUrl;
        this.user = user;
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
        if (status != null && Arrays.stream(AssignmentStatusEnum.values())
                .anyMatch(enumStatus -> enumStatus.getStatus().equals(status))) {
            this.status = status;
        } else {
            this.status = "Pending Submission";  // Set default value if invalid
        }
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Temporary getter and setter for 'username' field (not stored in the database)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}


