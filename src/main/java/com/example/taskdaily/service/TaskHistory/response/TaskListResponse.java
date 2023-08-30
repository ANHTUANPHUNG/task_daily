package com.example.taskdaily.service.TaskHistory.response;

import com.example.taskdaily.domain.Enums.ETaskStatus;
import com.example.taskdaily.domain.Enums.ETaskType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
public class TaskListResponse {
    private Long id;

    private String title;

    private String description;

    private LocalTime start;

    private LocalTime end;

    private ETaskStatus status;

    private ETaskType type;

    public String getTime(){
        return start.toString() + " - " + end;
    }
}
