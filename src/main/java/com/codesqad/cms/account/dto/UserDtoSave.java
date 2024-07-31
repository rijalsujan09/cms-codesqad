package com.codesqad.cms.account.dto;

import com.codesqad.cms.account.entity.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoSave {
    @NotNull
    private String firstName;
    @NotNull
    @Size(min = 3, max = 10 , message = "Last name must be between 3 and 20 characters")
    private String lastName;
    @Email(message = "Invalid email")
    private String email;
    @NotNull
    @Size(min = 3, max = 16 , message = "User name must be between 3 and 12 characters")
    private String username;
    @NotNull
    @Size(min = 6, max = 24 , message = "password must be between 6 and 18 characters")
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;


}
