package ru.shashy.remindertest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.shashy.remindertest.dto.request.AuthDTO;
import ru.shashy.remindertest.dto.request.RegistrationDTO;
import ru.shashy.remindertest.service.RegistrationAuthService;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationAuthService registrationService;


    @PostMapping("/registration")
    public ResponseEntity<?> registrationPost(
            @RequestBody RegistrationDTO registrationDTO) {
        return registrationService.registerUser(registrationDTO);
    }

    @PostMapping("/auth")
    public ResponseEntity<?> authPost(
            @RequestBody AuthDTO authDTO) {
        return registrationService.authUser(authDTO);
    }
}
