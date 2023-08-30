package com.example.taskdaily.domain;

import com.example.taskdaily.domain.Enums.ETaskStatus;
import com.example.taskdaily.domain.Enums.ETaskType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tasks")
public class Task  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    private LocalTime start;

    private LocalTime end;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "task")
    private Set<TaskHistory> taskHistories;


    private LocalDate renewalDate = LocalDate.now().plusDays(1);


//    @Override
//    public boolean supports(Class<?> clazz) {
//        return false;
//    }
//
//    @Override
//    public void validate(Object target, Errors errors) {
//        Task task = (Task) target;
//        String title = task.getTitle();
//        if (title == null || title.length() < 8) {
//            errors.rejectValue("title", "error.title.invalid", "Title must be at least 8 characters long");
//        }
//        String description = task.getDescription();
//        if (description == null || description.length() < 8) {
//            errors.rejectValue("description", "error.title.description", "description must be at least 8 characters long");
//        }
//    }
}
