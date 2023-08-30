package com.example.taskdaily.service.Task.response;

import com.example.taskdaily.domain.Enums.ETaskStatus;
import com.example.taskdaily.domain.Enums.ETaskType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
@Data
@NoArgsConstructor
public class ListResponse {
    private Long id;

    private String title;

    private String description;

    private LocalTime start;

    private LocalTime end;

    public String getTime(){
        return start.toString() + " - " + end;
    }
}
