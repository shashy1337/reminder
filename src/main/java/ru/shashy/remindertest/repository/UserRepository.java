package ru.shashy.remindertest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.shashy.remindertest.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
    boolean existsByLogin(String login);
    @Query("SELECT u FROM User u JOIN FETCH u.reminderTables WHERE u.login = :login")
    User findUserWithReminders(@Param("login") String login);
}
