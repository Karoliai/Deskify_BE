package org.deskify.model.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    @Schema(description = "User id", example = "1")
    private Long id;

    @Schema(description = "User username", example = "user123")
    private String username;
}
