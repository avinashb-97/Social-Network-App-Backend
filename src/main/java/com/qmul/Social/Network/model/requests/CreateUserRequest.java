package com.qmul.Social.Network.model.requests;

import com.qmul.Social.Network.model.persistence.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

    private String name;

    private String mail;

    private String password;

    private String confirmPassword;

    private Long instituteId;

    private Long departmentId;

    private Long courseId;

    private String code;

    private Role role;

}
