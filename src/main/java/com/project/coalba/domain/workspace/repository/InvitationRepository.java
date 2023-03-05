package com.project.coalba.domain.workspace.repository;

import com.project.coalba.domain.workspace.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface InvitationRepository extends JpaRepository<Invitation, String> {

    @Query("select i from Invitation i where i.workspace.id = :workspaceId and i.receiverEmail = :receiverEmail and i.expired = false and i.expirationDate >= :now")
    List<Invitation> findValidAllByWorkspaceIdAndReceiverEmail(@Param("workspaceId") Long workspaceId, @Param("receiverEmail") String receiverEmail, @Param("now") LocalDateTime now);
}
