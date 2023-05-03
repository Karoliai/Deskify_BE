package org.deskify.model.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor
public class LoginRequest {
    @NonNull
    @Schema(description = "User username", example = "TestAccount")
    private String username;

    @NonNull
    @Schema(description = "User Password", example = "123")
    private String password;
}
