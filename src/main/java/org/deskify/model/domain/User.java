package org.deskify.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;
    private String username;
    private String password;
    private String firstName;
    private AccountType accountType;
    private String lastName;
    private String email;
}
