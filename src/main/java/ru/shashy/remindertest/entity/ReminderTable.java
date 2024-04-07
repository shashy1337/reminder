package ru.shashy.remindertest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "reminder_table", schema = "reminder_schema")
public class ReminderTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description", length = 4096)
    private String description;

    @Column(name = "remind")
    private LocalDateTime remind;

    @Column(name = "send")
    private Boolean send = false;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public ReminderTable(String title, String description, LocalDateTime remind, User user) {
        this.title = title;
        this.description = description;
        this.remind = remind;
        this.user = user;
    }
}