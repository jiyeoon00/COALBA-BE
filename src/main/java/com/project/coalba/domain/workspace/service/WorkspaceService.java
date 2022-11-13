package com.project.coalba.domain.workspace.service;

import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.profile.repository.StaffProfileRepository;
import com.project.coalba.domain.workspace.entity.Workspace;
import com.project.coalba.domain.workspace.entity.WorkspaceMember;
import com.project.coalba.domain.workspace.repository.WorkspaceMemberRepository;
import com.project.coalba.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkspaceService {
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final StaffProfileRepository staffProfileRepository;

    @Transactional
    public void inviteStaff(Long workSpaceId, String email){
        Staff staff = staffProfileRepository.getStaffByUserEmail(email);
        Optional<Workspace> workspace = workspaceRepository.findById(workSpaceId);
        if(staff != null && workspace.isPresent()){
            WorkspaceMember workspaceMember = WorkspaceMember.builder()
                    .staff(staff)
                    .workspace(workspace.get())
                    .build();

            workspaceMemberRepository.save(workspaceMember);
        }
    }

}
