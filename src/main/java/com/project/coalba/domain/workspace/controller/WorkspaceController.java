package com.project.coalba.domain.workspace.controller;

import com.project.coalba.domain.workspace.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class WorkspaceController {
    private final WorkspaceService workspaceService;

    @PostMapping("boss/workspaces/{workspaceId}/staffs")
    public void inviteStaff(@PathVariable Long workspaceId, @RequestBody String email){
        workspaceService.inviteStaff(workspaceId, email);
    }
}
