package ru.shashy.remindertest.dto.request;

import lombok.Data;

public @Data class AuthDTO {
    private String login;
    private String password;
}
