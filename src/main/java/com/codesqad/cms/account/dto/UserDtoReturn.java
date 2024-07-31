package com.codesqad.cms.account.dto;

import com.codesqad.cms.account.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoReturn {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    @JsonIgnore
    private String password;
    private Role role;
}
