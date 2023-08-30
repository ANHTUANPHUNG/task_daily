package com.example.taskdaily.service.Task.request;

import com.example.taskdaily.domain.Enums.ETaskStatus;
import com.example.taskdaily.domain.Enums.ETaskType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
@Data
@NoArgsConstructor
public class EditRequest {
    private String id;

    private String title;

    private String description;

    private String start;

    private String end;

//    private ETaskStatus status;
//
//    private ETaskType type;

    public String getTime(){
        return start.toString() + " - " + end;
    }
}
