package ru.shashy.remindertest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController  {

    @GetMapping("/ok")
    public String getOk() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "OK!" + authentication.getPrincipal();
    }

    @PostMapping("/logout")
    public String logout() {
        log.info(SecurityContextHolder.getContext().getAuthentication().toString());
        SecurityContextHolder.getContext().setAuthentication(null);
        return "ok!";
    }
}


