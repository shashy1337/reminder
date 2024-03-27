package ru.shashy.remindertest.util;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.shashy.remindertest.entity.User;
import ru.shashy.remindertest.service.UserService;

@Component
@NoArgsConstructor
@Slf4j
public abstract class AuthenticationUtil {

    @Autowired
    protected UserService userService;
    @Autowired
    protected ModelMapper modelMapper;
    protected User getUserAuthEntity(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info((String) authentication.getPrincipal());
        return userService.findByLogin((String) authentication.getPrincipal());
    }

    protected String getUserAuthFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (String) authentication.getPrincipal();
    }
}
