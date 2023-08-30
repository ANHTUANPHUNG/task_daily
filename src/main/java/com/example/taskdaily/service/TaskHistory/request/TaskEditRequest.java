package com.example.taskdaily.service.TaskHistory.request;

import com.example.taskdaily.domain.Enums.ETaskStatus;
import com.example.taskdaily.domain.Enums.ETaskType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
public class TaskEditRequest {
    private String id;

    private String title;

    private String description;

    private String start;

    private String end;

    private String status;

    private String type;
    private String isEditAll;


    public String getTime(){
        return start.toString() + " - " + end;
    }
}
