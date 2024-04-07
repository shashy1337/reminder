package ru.shashy.remindertest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ReminderTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReminderTestApplication.class, args);
    }

}
