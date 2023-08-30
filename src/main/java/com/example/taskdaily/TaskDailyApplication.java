package com.example.taskdaily;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

public class TaskDailyApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskDailyApplication.class, args);
    }

}
