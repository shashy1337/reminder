package ru.shashy.remindertest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shashy.remindertest.entity.ReminderTable;
import ru.shashy.remindertest.entity.User;

@Service
@RequiredArgsConstructor
public class ReminderEmailSenderService {

    private final EmailService emailService;

    public void sendReminderToEmail(User user, ReminderTable reminderTable){
        emailService.send(user.getEmail(),
                reminderTable.getTitle(),
                reminderTable.getDescription());
    }
}
