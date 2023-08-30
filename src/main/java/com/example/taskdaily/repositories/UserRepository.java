package com.example.taskdaily.repositories;

import com.example.taskdaily.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
