package com.example.taskdaily.service.TaskHistory;

import com.example.taskdaily.domain.Enums.ETaskStatus;
import com.example.taskdaily.domain.Enums.ETaskType;
import com.example.taskdaily.domain.Task;
import com.example.taskdaily.domain.TaskHistory;
import com.example.taskdaily.repositories.TaskHistoryRepository;
import com.example.taskdaily.repositories.TaskRepository;
import com.example.taskdaily.service.Task.request.EditRequest;
import com.example.taskdaily.service.TaskHistory.request.TaskEditRequest;
import com.example.taskdaily.service.TaskHistory.request.TaskSaveRequest;
import com.example.taskdaily.service.TaskHistory.response.TaskListResponse;
import com.example.taskdaily.util.AppMessage;
import com.example.taskdaily.util.AppUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskHistoryService {
    private final TaskRepository taskRepository;
    private final TaskHistoryRepository taskHistoryRepository;
    public List<TaskListResponse> getTasks() {
        return taskHistoryRepository.findAllTaskToDay()
                .stream()
                .map(e -> AppUtil.mapper.map(e, TaskListResponse.class))
                .collect(Collectors.toList());
    }
    public List<TaskHistory> getListView(){
        return taskHistoryRepository.findAll();
    }
    public List<TaskHistory> getListDate(LocalDate localDate){
        return taskHistoryRepository.findByStartDateToday(localDate);
    }
    public Map<String, Long> calculateStatusTotals(List<Object[]> statusCounts) {
        Map<String, Long> statusTotals = new HashMap<>();
        for (Object[] statusCount : statusCounts) {
            ETaskStatus status = (ETaskStatus) statusCount[0]; // Sử dụng kiểu ETaskStatus thay vì String
            Long count = (Long) statusCount[1];

            String statusString = status.toString(); // Chuyển đổi ETaskStatus thành String
            statusTotals.put(statusString, statusTotals.getOrDefault(statusString, 0L) + count);
        }
        return statusTotals;
    }


    public List<Object[]> getStatusCounts(LocalDate localDate) {
        return taskHistoryRepository.getStatusCountsByStartDate(localDate);
    }


    public void create(TaskSaveRequest request) {
        var taskHistory = AppUtil.mapper.map(request, TaskHistory.class);
        if (Objects.equals(request.getType(), ETaskType.DAILY.toString())) {
//            var task = AppUtil.mapper.map(request, Task.class);
//            task = taskRepository.save(task);
            LocalDate now = LocalDateTime.now().toLocalDate();
//            taskHistory.setTask(task);
            taskHistory.setStart(LocalDateTime.of(now, LocalTime.parse(request.getStart())));
            taskHistory.setEnd(LocalDateTime.of(now, LocalTime.parse(request.getEnd())));
        }
        taskHistoryRepository.save(taskHistory);
    }

    public void changeStatus(Long id, ETaskStatus status){
        var task = findById(id);
        task.setStatus(status);
        taskHistoryRepository.save(task);
    }
    public List<Object[]> getStatusCountsByWeek(LocalDate startDate, LocalDate endDate) {
        return taskHistoryRepository.getStatusCountsByWeek(startDate, endDate);
    }

    public TaskHistory findById(Long id){
        return taskHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        String.format(AppMessage.ID_NOT_FOUND, "Task", id)));
    }
    public void deleteById(Long id){
        taskHistoryRepository.deleteById(id);
    }
    public TaskEditRequest showEditTitle(Long id){
        TaskHistory taskHistory = findById(id);
        return edit(taskHistory);
    }
    private TaskEditRequest edit(TaskHistory taskHistory){
        var result = new TaskEditRequest();
        result.setTitle(String.valueOf(taskHistory.getTitle()));
        result.setDescription(String.valueOf(taskHistory.getDescription()));
        result.setStart(String.valueOf(taskHistory.getStart()));
        result.setEnd(String.valueOf(taskHistory.getEnd()));
        result.setStatus(String.valueOf(taskHistory.getStatus()));
        result.setType(String.valueOf(taskHistory.getType()));
        result.setId(String.valueOf(taskHistory.getId()));
        return result;
    }
    @Transactional
    public void edit(TaskEditRequest request, Long id) {
        //lấy task history bằng id.
        // nếu task có value và isEditAll = true.
        // cập nhập task cũ và taskhistory cho hôm nay
        // còn không chỉ cập nhập task history cho Hôm nay

        var taskHistoryExistDb = findById(id);

        AppUtil.mapper.map(request, taskHistoryExistDb);
        boolean isEditAll = Boolean.parseBoolean(request.getIsEditAll());

        var taskExistDb = taskHistoryExistDb.getTask();
        if(isEditAll && taskExistDb != null){
            AppUtil.mapper.map(request, taskExistDb);
            taskRepository.save(taskExistDb);
        }
        if(taskExistDb != null){
            LocalDate now = LocalDate.now();
            taskHistoryExistDb.setTask(taskExistDb);
            taskHistoryExistDb.setStart(LocalDateTime.of(now,LocalTime.parse(request.getStart(), DateTimeFormatter.ofPattern("HH:mm"))));
            taskHistoryExistDb.setEnd(LocalDateTime.of(now, LocalTime.parse(request.getEnd(), DateTimeFormatter.ofPattern("HH:mm"))));
        }
        taskHistoryRepository.save(taskHistoryExistDb);
    }

}

