package com.example.taskdaily.repositories;

import com.example.taskdaily.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Modifying
    @Transactional
    void deleteTaskByTitleOrderByTitle(String title);

    List<Task> findByRenewalDate(LocalDate currentDate);

    Task findTopByOrderByRenewalDateAsc();
}
