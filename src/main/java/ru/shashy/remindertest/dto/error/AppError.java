package ru.shashy.remindertest.dto.error;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public @Data class AppError {
    private int status;
    private String message;
    private LocalDateTime timestamp;

    public AppError(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
