package ru.shashy.remindertest.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

public interface CRUDReminderOperations<D, T> {
    @Transactional
    ResponseEntity<?> createEntityDb(D dto);
    @Transactional
    ResponseEntity<?> readEntityDb(int page, int size);
    @Transactional
    ResponseEntity<?> updateEntityDb(D dto);
    @Transactional
    ResponseEntity<?> deleteEntityDb(T id);

}
