package ru.shashy.remindertest.service;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

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
