package ru.shashy.remindertest.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.shashy.remindertest.entity.ReminderTable;
import ru.shashy.remindertest.repository.ReminderRepository;
import ru.shashy.remindertest.service.ReminderEmailSenderService;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
@EnableScheduling
@RequiredArgsConstructor
public class ReminderScheduler {

    private final ReminderRepository reminderRepository;
    private final ReminderEmailSenderService emailService;

    @Transactional
    @Scheduled(fixedRate = 60000)
    public void sendReminder() {
        LocalDateTime now = LocalDateTime.now();
        List<ReminderTable> reminderTables = reminderRepository.findAllByRemindBeforeAndSendIsFalse(now);
        //log.info(Arrays.toString(reminderTables.toArray()));

        if (!reminderTables.isEmpty()) {
            reminderTables.forEach(
                    rt -> {
                        try {
                            emailService.sendReminderToEmail(rt.getUser(), rt);
                            return;
                        } catch (RuntimeException e) {
                            log.error("Письмо не отправлено");
                        }
                        rt.setSend(true);
                        reminderRepository.save(rt);
                    });
        }

    }
}
