package ru.shashy.remindertest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public @Data class JwtResponseDTO {
    private String token;
}
