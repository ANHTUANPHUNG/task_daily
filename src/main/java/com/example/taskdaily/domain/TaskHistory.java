package com.example.taskdaily.domain;

import com.example.taskdaily.domain.Enums.ETaskStatus;
import com.example.taskdaily.domain.Enums.ETaskType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "task_histories")
public class TaskHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    private LocalDateTime start;

    private LocalDateTime end;
    private LocalDate created;

    @Enumerated(value = EnumType.STRING)
    private ETaskStatus status;

    @Enumerated(value = EnumType.STRING)
    private ETaskType type;
    private LocalDate renewalDate = LocalDate.now().plusDays(1);


    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @PrePersist
    public void setupBeforeInsert(){

        created = LocalDate.now();
        status = ETaskStatus.TODO;

    }
//    @PrePersist
//    public void setupBeforeInsert() {
//    }
}
