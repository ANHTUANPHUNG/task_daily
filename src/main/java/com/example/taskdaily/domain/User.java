package com.example.taskdaily.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Set;

@Entity
@Data
@Table(name = "users")
@Where(clause = "deleted=0")
@SQLDelete(sql = "UPDATE users SET `deleted` = 1 WHERE (`id` = ?); ")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String username;

    private String email;

    private String password;

    private boolean deleted = false;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToMany(mappedBy = "user")
    private Set<Task> tasks;

}
