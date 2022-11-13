package com.project.coalba.domain.profile.repository;

import com.project.coalba.domain.profile.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffProfileRepository extends JpaRepository<Staff, Long> {
}
