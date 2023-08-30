package com.example.taskdaily.domain;

import com.example.taskdaily.domain.Enums.EGender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "profiles")
@Where(clause = "deleted = 0")
@SQLDelete(sql= "UPDATE profiles SET `deleted` = 1 WHERE (`id` = ?); ")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;

    private LocalDate dob;

    @Enumerated(EnumType.STRING)
    private EGender gender;

    private boolean deleted = false;
}
