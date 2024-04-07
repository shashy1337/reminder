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
import ru.shashy.remindertest.service.filterAndSort.FilteringAndSortingResponse;
import ru.shashy.remindertest.util.AuthenticationHelper;
import ru.shashy.remindertest.util.PaginationUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReminderService extends AuthenticationHelper
        implements CRUDReminderOperations<ReminderDTO, Long>, FilteringAndSortingResponse {

    private final ReminderRepository reminderRepository;
    private final PaginationUtil paginationUtil;

    @Override
    public ResponseEntity<?> createEntityDb(ReminderDTO dto) {
        var reminderTable = new ReminderTable(
                dto.getTitle(),
                dto.getDescription(),
                dto.getRemind(),
                getUserAuthEntity());
        reminderRepository.save(reminderTable);
        return ResponseEntity.ok("OK!");
    }

    @Override
    public ResponseEntity<?> readEntityDb(int page, int size) {
        List<ReminderTable> allTables =
                paginationUtil.getPage(getUserAuthEntityWithReminders().getReminderTables(), page, size);
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
    public ResponseEntity<?> deleteEntityDb(Long id) {
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


    @Override
    public ResponseEntity<?> sortEntity(String sortType, int page, int size) {

        List<ReminderTable> allReminders = getUserAuthEntityWithReminders().getReminderTables();
        switch (sortType) {
            case "name" -> {
                allReminders.sort(Comparator.comparing(ReminderTable::getTitle));
                return ResponseEntity.ok(paginationUtil.getPage(allReminders, page, size));
            }
            case "date" -> {
                allReminders.sort(Comparator.comparing(r -> r.getRemind().toLocalDate()));
                return ResponseEntity.ok(paginationUtil.getPage(allReminders, page, size));
            }
            case "time" -> {
                allReminders.sort(Comparator.comparing(r -> r.getRemind().toLocalTime()));
                return ResponseEntity.ok(paginationUtil.getPage(allReminders, page, size));
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> filterByDate(LocalDate localDate, int page, int size) {
        List<ReminderTable> allFilterRemindersByDate = getUserAuthEntityWithReminders().getReminderTables()
                .stream()
                .filter(c -> c.getRemind() != null && c.getRemind().toLocalDate().equals(localDate))
                .toList();
        return ResponseEntity.ok(paginationUtil.getPage(allFilterRemindersByDate, page, size));
    }

    @Override
    public ResponseEntity<?> filterByTime(LocalTime localTime, int page, int size) {
        List<ReminderTable> allFilterRemindersByTime = getUserAuthEntityWithReminders().getReminderTables()
                .stream()
                .filter(c -> c.getRemind() != null && c.getRemind().toLocalTime().equals(localTime))
                .toList();
        paginationUtil.getPage(allFilterRemindersByTime, page, size);
        return ResponseEntity.ok(allFilterRemindersByTime);
    }
}
