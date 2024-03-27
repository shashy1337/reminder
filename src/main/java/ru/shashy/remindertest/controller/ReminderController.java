package ru.shashy.remindertest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shashy.remindertest.dto.reminder.ReminderDTO;
import ru.shashy.remindertest.service.ReminderService;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequiredArgsConstructor
public class ReminderController {

    private final ReminderService reminderService;

    @PostMapping("/reminder/create")
    public ResponseEntity<?> createRemind(
            @RequestBody ReminderDTO reminderDTO) {
        return reminderService.createEntityDb(reminderDTO);
    }

    @GetMapping("/reminder/list")
    public ResponseEntity<?> readRemind(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return reminderService.readEntityDb(page, size);
    }


    @PutMapping("/reminder/update")
    public ResponseEntity<?> updateRemind(
            @RequestBody ReminderDTO reminderDTO
    ) {
        return reminderService.updateEntityDb(reminderDTO);
    }

    @DeleteMapping("/reminder/delete/{id}")
    public ResponseEntity<?> deleteRemind(
            @PathVariable int id
    ) {
        return reminderService.deleteEntityDb(id);
    }

    @GetMapping("/reminder/sort")
    public ResponseEntity<?> sortTypeRemind(
            @RequestParam String sortType
    ) {
        return reminderService.sortedList(sortType);
    }

    @GetMapping("/reminder/filter/date")
    public ResponseEntity<?> filterBy(
            @RequestParam LocalDate localDate
    ){
        return reminderService.filterReminders(localDate);
    }


    @GetMapping("/reminder/filter/time")
    public ResponseEntity<?> filterBy(
            @RequestParam LocalTime localTime
    ){
        return reminderService.filterReminders(localTime);
    }
}
