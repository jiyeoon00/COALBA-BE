package com.project.coalba.domain.profile.repository;

import com.project.coalba.domain.profile.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StaffProfileRepository extends JpaRepository<Staff, Long> {

    @Query("select s from Staff s JOIN s.user u on u.email = :email")
    Staff getStaffByUserEmail(@Param("email") String email);
}
