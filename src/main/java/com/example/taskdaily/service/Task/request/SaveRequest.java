package com.example.taskdaily.service.Task.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaveRequest {
    private String title;

    private String description;

    private String start;

    private String end;

    private String type;
}
