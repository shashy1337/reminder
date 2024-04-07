package ru.shashy.remindertest.service.filterAndSort;

import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;

public interface FilteringAndSortingResponse {
    ResponseEntity<?> sortEntity(String sortType, int page, int size);
    ResponseEntity<?> filterByDate(LocalDate localDate, int page, int size);
    ResponseEntity<?> filterByTime(LocalTime localTime, int page, int size);
}
