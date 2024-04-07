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
            @PathVariable Long id
    ) {
        return reminderService.deleteEntityDb(id);
    }

    @GetMapping("/reminder/sort/{sortType}")
    public ResponseEntity<?> sortTypeRemind(
            @PathVariable String sortType,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return reminderService.sortEntity(sortType, page, size);
    }

    @GetMapping("/reminder/filter/date/{dateVal}")
    public ResponseEntity<?> filterBy(
            @PathVariable LocalDate dateVal,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return reminderService.filterByDate(dateVal, page, size);
    }


    @GetMapping("/reminder/filter/time/{timeVal}")
    public ResponseEntity<?> filterBy(
            @PathVariable LocalTime timeVal,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return reminderService.filterByTime(timeVal, page, size);
    }
}
