package com.example.taskdaily.repositories;

import com.example.taskdaily.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
