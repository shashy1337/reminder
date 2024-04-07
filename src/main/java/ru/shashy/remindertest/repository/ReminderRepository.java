package ru.shashy.remindertest.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.shashy.remindertest.entity.ReminderTable;
import ru.shashy.remindertest.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReminderRepository extends JpaRepository<ReminderTable, Long>,
        PagingAndSortingRepository<ReminderTable, Long> {

    /*List<ReminderTable> findAllByUser(User user, Pageable pageable);
    List<ReminderTable> findAllByUser(User user, Sort sort);
    List<ReminderTable> findAllByUser(User user);*/
    @Query("SELECT r FROM ReminderTable r JOIN FETCH r.user WHERE r.remind <= :now AND r.send = FALSE")
    List<ReminderTable> findAllByRemindBeforeAndSendIsFalse(LocalDateTime now);

}
