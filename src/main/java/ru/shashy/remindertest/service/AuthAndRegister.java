package ru.shashy.remindertest.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.annotation.Transactional;
import ru.shashy.remindertest.dto.request.AuthDTO;
import ru.shashy.remindertest.dto.request.RegistrationDTO;

public interface AuthAndRegister {
    @Transactional
    ResponseEntity<?> registerUser(RegistrationDTO registrationDTO);
    ResponseEntity<?> authUser(AuthDTO authDTO) throws BadCredentialsException;
}
