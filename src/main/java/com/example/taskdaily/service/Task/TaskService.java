package com.example.taskdaily.service.Task;

import com.example.taskdaily.domain.Enums.ETaskStatus;
import com.example.taskdaily.domain.Enums.ETaskType;
import com.example.taskdaily.domain.Task;
import com.example.taskdaily.domain.TaskHistory;
import com.example.taskdaily.repositories.TaskHistoryRepository;
import com.example.taskdaily.repositories.TaskRepository;
import com.example.taskdaily.service.Task.request.EditRequest;
import com.example.taskdaily.service.Task.request.SaveRequest;
import com.example.taskdaily.service.Task.response.ListResponse;
import com.example.taskdaily.util.AppMessage;
import com.example.taskdaily.util.AppUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskHistoryRepository taskHistoryRepository;
    public List<ListResponse> findAll(){
        return taskRepository.findAll().stream().map(task -> {
            var result = new ListResponse();
            result.setId(task.getId());
            result.setTitle(task.getTitle());
            result.setDescription(task.getDescription());
            result.setStart(task.getStart());
            result.setEnd(task.getEnd());
            return result;
        }).toList();
    }
    public void createTask(SaveRequest request) {
        var task = AppUtil.mapper.map(request, Task.class);
        taskRepository.save(task);
        var taskHistory = AppUtil.mapper.map(request, TaskHistory.class);
        LocalDate now = LocalDateTime.now().toLocalDate();
        taskHistory.setStatus(ETaskStatus.TODO);
        taskHistory.setType(ETaskType.DAILY);
        taskHistory.setTask(task);
        taskHistory.setStart(LocalDateTime.of(now, task.getStart()));
        taskHistory.setEnd(LocalDateTime.of(now, task.getEnd()));
        taskHistoryRepository.save(taskHistory);
    }
    public void editDaily(EditRequest request, Long id) {
//        LocalDateTime now = LocalDateTime.now();
        var userInDb = findById(id);
//        TaskHistory taskHistory = taskHistoryRepository.getTaskHistoriesByTaskId(id);
//        if (taskHistory != null) {
//            taskHistory.setTitle(request.getTitle());
//            taskHistory.setDescription(request.getDescription());
//            taskHistory.setStart(LocalDateTime.of(now.toLocalDate(), LocalTime.parse(request.getStart())));
//            taskHistory.setEnd(LocalDateTime.of(now.toLocalDate(), LocalTime.parse(request.getEnd())));
//            taskHistoryRepository.save(taskHistory);
//        }
        userInDb.setTitle(request.getTitle());
        userInDb.setDescription((request.getDescription()));
        userInDb.setStart(LocalTime.parse(request.getStart()));
        userInDb.setEnd(LocalTime.parse(request.getEnd()));
        request.setId(id.toString());
        taskRepository.save(userInDb);
    }
    public Task findById(Long id){
        //de tai su dung
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format(AppMessage.ID_NOT_FOUND, "Daily", id)));
    }
    public TaskHistory findById2(Long id){
        return taskHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        String.format(AppMessage.ID_NOT_FOUND, "Task", id)));
    }



    public void deleteById(Long id) {
        TaskHistory taskHistory = taskHistoryRepository.getTaskHistoriesByTaskId(id);
        taskHistory.setTask(null);
        taskRepository.deleteById(id);
    }
    public EditRequest showEditTitle(Long id){
        Task task = findById(id);
        return  update(task);

    }
    private EditRequest update(Task task ){
        var result = new EditRequest();
        result.setTitle(String.valueOf(task.getTitle()));
        result.setDescription(String.valueOf(task.getDescription()));
        result.setStart(String.valueOf(task.getStart()));
        result.setEnd(String.valueOf(task.getEnd()));
        result.setId(String.valueOf(task.getId()));
        return result;
    }

}
