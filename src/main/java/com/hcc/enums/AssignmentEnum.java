package com.hcc.enums;

public enum AssignmentEnum {
    ASSIGNMENT_1(1, "Spring Boot Service"),
    ASSIGNMENT_2(2, "Spring Boot Data JPA"),
    ASSIGNMENT_3(3, "Spring Boot Postgresql"),
    ASSIGNMENT_4(4, "Docker Compose Setup"),
    ASSIGNMENT_5(5, "React Frontend Hooks");

    private final int assignmentNumber;
    private final String assignmentName;

    AssignmentEnum(int assignmentNumber, String assignmentName) {
        this.assignmentNumber = assignmentNumber;
        this.assignmentName = assignmentName;
    }

    public int getAssignmentNumber() {
        return assignmentNumber;
    }

    public String getAssignmentName() {
        return assignmentName;
    }
}

