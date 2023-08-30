package com.example.taskdaily.domain.Enums;

public enum EGender {
    MALE("Nam"),FEMALE("Nữ"),OTHER("Giới tính khác");
     final String name;
    EGender(String name) {
        this.name=name;
    }

    public String getName() {
        return name;
    }
}
