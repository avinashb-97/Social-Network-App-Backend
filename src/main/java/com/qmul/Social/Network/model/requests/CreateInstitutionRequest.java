package com.qmul.Social.Network.model.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateInstitutionRequest {

    private String name;

    private String adminMail;

    private String password;

    private String confirmPassword;

}
