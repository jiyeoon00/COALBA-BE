package com.project.coalba.domain.profile.repository;

import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.profile.repository.custom.StaffProfileRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StaffProfileRepository extends JpaRepository<Staff, Long>, StaffProfileRepositoryCustom {

    @Query("select s from Staff s where s.user.id = :userId")
    Optional<Staff> findByUserId(@Param("userId") Long userId);

    @Query("select s from Staff s join s.user u on u.email = :email")
    Optional<Staff> findByUserEmail(@Param("email") String email);

    @Query("select s from Staff s inner join WorkspaceMember wm on s.id = wm.staff.id where wm.workspace.id = :workspaceId")
    List<Staff> findAllByWorkspaceId(@Param("workspaceId") Long workspaceId);
}
