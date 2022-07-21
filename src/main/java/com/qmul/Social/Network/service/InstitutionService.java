package com.qmul.Social.Network.service;

import com.qmul.Social.Network.model.persistence.Institution;
import com.qmul.Social.Network.model.persistence.User;
import com.qmul.Social.Network.model.repository.InstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

@Service
public class InstitutionService {

    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Institution createInstitution(String instituionName, String adminMail, String password)
    {
        if(institutionRepository.existsInstituionByName(instituionName))
        {
            throw new RuntimeException("Institution already exists");
        }

        Institution institution = new Institution();
        institution.setName(instituionName);
        institution = institutionRepository.save(institution);
        User user = userService.createInstitutionAdmin(adminMail, password, institution);
        institution.setUsers(new HashSet(){{add(user);}});
        return institution;
    }
}
