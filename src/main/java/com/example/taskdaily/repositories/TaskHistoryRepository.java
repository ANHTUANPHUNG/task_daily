package com.example.taskdaily.repositories;

import com.example.taskdaily.domain.TaskHistory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Long> {
    @Query(value = "SELECT t  FROM TaskHistory t WHERE  " +
            "(DATE_FORMAT(t.start, '%Y-%m-%d') <= DATE_FORMAT(CURRENT_DATE, '%Y-%m-%d') AND " +
            "DATE_FORMAT(t.end, '%Y-%m-%d') >= DATE_FORMAT(CURRENT_DATE, '%Y-%m-%d'))" +
            "ORDER BY t.start")
    List<TaskHistory> findAllTaskToDay();

    //    @Query(value = "SELECT t  FROM TaskHistory t WHERE  " +
//
//            "(DATE_FORMAT(t.start, '%Y-%m-%d') > DATE_FORMAT(CURRENT_DATE, '%Y-%m-%d'))" +
//            "ORDER BY t.start" )
//
//    List<TaskHistory> findTaskHistoriesByStartAfter();
//    void findTaskHistoriesByStartAfter(LocalDateTime start);
    @Modifying
    @Transactional
    void deleteTaskHistoriesByTitleOrderByTitle(String title);

    TaskHistory getTaskHistoriesByTaskId(Long task_id);

    @Query("SELECT e.created FROM TaskHistory e ORDER BY e.created DESC limit 1")
    List<LocalDate> findCreatedTask(PageRequest pageRequest);

    void deleteById(Long id);

    //    @Query("SELECT DATE(th.datetimeColumn) FROM TaskHistory th WHERE th.id = :id")
//    LocalDate getDateOnly(@Param("id") Long id);
    @Query("SELECT t FROM TaskHistory t WHERE FUNCTION('DATE_FORMAT', t.start, '%Y-%m-%d') = FUNCTION('DATE_FORMAT', :localDate, '%Y-%m-%d')")
    List<TaskHistory> findByStartDateToday(@Param("localDate") LocalDate localDate);



// ...

    @Query("SELECT t.status, COUNT(t.status) as status_count FROM TaskHistory t WHERE FUNCTION('DATE_FORMAT', t.start, '%Y-%m-%d') = FUNCTION('DATE_FORMAT', :startDate, '%Y-%m-%d') GROUP BY t.status")
    List<Object[]> getStatusCountsByStartDate(@Param("startDate") LocalDate startDate);
    @Query("SELECT t.status, COUNT(t.status) as status_count FROM TaskHistory t WHERE FUNCTION('DATE_FORMAT', t.start, '%Y-%m-%d') >= FUNCTION('DATE_FORMAT', :startDate, '%Y-%m-%d') AND FUNCTION('DATE_FORMAT', t.end, '%Y-%m-%d') <= FUNCTION('DATE_FORMAT', :endDate, '%Y-%m-%d') GROUP BY t.status")
    List<Object[]> getStatusCountsByWeek(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);



}
