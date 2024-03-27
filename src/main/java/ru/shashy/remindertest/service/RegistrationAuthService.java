package ru.shashy.remindertest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.shashy.remindertest.dto.error.AppError;
import ru.shashy.remindertest.dto.request.AuthDTO;
import ru.shashy.remindertest.dto.request.RegistrationDTO;
import ru.shashy.remindertest.dto.response.JwtResponseDTO;
import ru.shashy.remindertest.entity.User;
import ru.shashy.remindertest.enums.RoleEnum;
import ru.shashy.remindertest.util.JWTutil;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationAuthService implements AuthAndRegister{

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTutil jwTutil;

    @Override
    public ResponseEntity<?> registerUser(RegistrationDTO registrationDTO) {
        var user = new User();

        if (userService.isExists(registrationDTO.getLogin())) {
            return ResponseEntity.badRequest()
                    .body(new AppError(HttpStatus.BAD_REQUEST.value(), "USER EXISTS"));
        }
        user.setLogin(registrationDTO.getLogin());
        user.setRole(RoleEnum.ROLE_USER);
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        userService.save(user);
        return ResponseEntity.ok().body("OK!");
    }

    @Override
    public ResponseEntity<?> authUser(AuthDTO authDTO) throws BadCredentialsException {
        var usernameAuthPasswordToken =
                new UsernamePasswordAuthenticationToken(authDTO.getLogin(), authDTO.getPassword());
        var authentication = authenticationManager.authenticate(usernameAuthPasswordToken);
        var user = (User) authentication.getPrincipal();
        String token = jwTutil.generateToken(user);
        return ResponseEntity
                .ok(new JwtResponseDTO(token));
    }
}
