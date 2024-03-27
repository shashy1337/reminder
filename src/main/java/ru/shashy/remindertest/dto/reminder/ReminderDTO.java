package ru.shashy.remindertest.dto.reminder;

import lombok.Data;

import java.time.LocalDateTime;

public @Data class ReminderDTO {
    private int id;
    private String title;
    private String description;
    private LocalDateTime remind;
}
