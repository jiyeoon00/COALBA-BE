package com.project.coalba.domain.workspace.dto.request;

import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Getter
public class WorkspaceUpdateRequest {

    @Valid
    private Workspace workspace;
    private String imageUrl;

    @Getter
    public static class Workspace {

        @NotBlank @Size(max = 100)
        private String name;

        @NotBlank @Pattern(regexp = "010[0-9]{8}|0(2|51|53|32|62|42|52|44|31|33|43|41|63|61|54|55|64)[0-9]{7}")
        private String phoneNumber;

        @NotBlank
        private String address;
    }
}
