package com.example.taskdaily.service.user.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSaveRequest {

    private String username;

    private String email;

    private String password;

    private String dob;

    private String gender;

    private String fullName;
}
