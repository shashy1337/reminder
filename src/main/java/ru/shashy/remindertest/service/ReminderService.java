package ru.shashy.remindertest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.shashy.remindertest.dto.reminder.ReminderDTO;
import ru.shashy.remindertest.entity.ReminderTable;
import ru.shashy.remindertest.entity.User;
import ru.shashy.remindertest.exception.ForbiddenException;
import ru.shashy.remindertest.repository.ReminderRepository;
import ru.shashy.remindertest.util.AuthenticationUtil;
import ru.shashy.remindertest.util.PaginationUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReminderService extends AuthenticationUtil
        implements CRUDReminderOperations<ReminderDTO, Integer> {

    private final ReminderRepository reminderRepository;
    private final PaginationUtil paginationUtil;

    @Override
    public ResponseEntity<?> createEntityDb(ReminderDTO dto) {
        User user = getUserAuthEntity();
        var reminderTable = new ReminderTable(
                dto.getTitle(),
                dto.getDescription(),
                dto.getRemind(),
                user);
        reminderRepository.save(reminderTable);
        return ResponseEntity.ok("OK!");
    }

    @Override
    public ResponseEntity<?> readEntityDb(int page, int size) {
        /*Authentication user = SecurityContextHolder.getContext().getAuthentication();
        User user1 = userService.findWithReminderTables((String) user.getPrincipal());
        List<ReminderTable> allTables =
                paginationUtil.getPage(user1.getReminderTables(), page, size);*/
        User userEntity = getUserAuthEntity();
        List<ReminderTable> allTables =
                paginationUtil.getPage(userEntity.getReminderTables(), page, size);
        return ResponseEntity.ok(allTables);
    }

    @Override
    public ResponseEntity<?> updateEntityDb(ReminderDTO dto) {
        ReminderTable reminderTable =
                reminderRepository.findById(dto.getId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reminder NOT_FOUND"));
        User user = getUserAuthEntity();
        if (!reminderTable.getUser().equals(user)) {
            throw new ForbiddenException("You do not have permission to update this reminder");
        }
        modelMapper.map(dto, reminderTable);
        reminderRepository.save(reminderTable);
        return ResponseEntity.ok("UPDATED!");
    }

    @Override
    public ResponseEntity<?> deleteEntityDb(Integer id) {
        ReminderTable reminderTable =
                reminderRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reminder NOT_FOUND"));
        User user = getUserAuthEntity();
        if (!reminderTable.getUser().equals(user)) {
            throw new ForbiddenException("You do not have permission to delete this reminder");
        }
        reminderRepository.delete(reminderTable);
        return ResponseEntity.ok("DELETED");
    }

    public ResponseEntity<?> sortedList(String sortType) {
        User user = getUserAuthEntity();
        List<ReminderTable> allReminders = user.getReminderTables();
        switch (sortType) {
            case "name" -> {
                allReminders.sort(Comparator.comparing(ReminderTable::getTitle));
                return ResponseEntity.ok(allReminders);
            }
            case "date" -> {
                allReminders.sort(Comparator.comparing(r -> r.getRemind().toLocalDate()));
                return ResponseEntity.ok(allReminders);
            }
            case "time" -> {
                allReminders.sort(Comparator.comparing(r -> r.getRemind().toLocalTime()));
                return ResponseEntity.ok(allReminders);
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }


    public ResponseEntity<?> filterReminders(LocalDate date) {
        User user = getUserAuthEntity();
        List<ReminderTable> allFilterRemindersByDate = user.getReminderTables()
                .stream()
                .filter(c -> c.getRemind() != null && c.getRemind().toLocalDate().equals(date))
                .toList();
        return ResponseEntity.ok(allFilterRemindersByDate);
    }

    public ResponseEntity<?> filterReminders(LocalTime time) {
        User user = getUserAuthEntity();
        List<ReminderTable> allFilterRemindersByTime = getUserAuthEntity().getReminderTables()
                .stream()
                .filter(c -> c.getRemind() != null && c.getRemind().toLocalTime().equals(time))
                .toList();
        return ResponseEntity.ok(allFilterRemindersByTime);
    }
}
