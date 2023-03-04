package com.project.coalba.domain.workspace.repository;

import com.project.coalba.domain.workspace.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation, String> {
}
