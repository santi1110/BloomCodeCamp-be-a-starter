package com.hcc.enums;

public enum AssignmentStatusEnum {
    PENDING_SUBMISSION("Pending Submission", 1),
    SUBMITTED("Submitted", 2),
    IN_REVIEW("In Review", 3),
    NEEDS_UPDATE("Needs Update", 4),
    COMPLETED("Completed", 5);

    private final String status;
    private final int step;

    AssignmentStatusEnum(String status, int step) {
        this.status = status;
        this.step = step;
    }

    public String getStatus() {
        return status;
    }

    public int getStep() {
        return step;
    }

    // Method to map a string to an enum
    public static AssignmentStatusEnum fromString(String text) {
        for (AssignmentStatusEnum status : AssignmentStatusEnum.values()) {
            if (status.name().equalsIgnoreCase(text)) { // Use name() to match enum names
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status: " + text);
    }
}
