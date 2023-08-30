package com.example.taskdaily.service.user.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserEditRequest {

    private String id;

    private String password;

    private String oldPassword;

    private String dob;

    private String gender;

    private String fullName;
}
