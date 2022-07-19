package com.qmul.Social.Network.service;

import com.qmul.Social.Network.model.persistence.Institution;
import com.qmul.Social.Network.model.persistence.User;
import com.qmul.Social.Network.model.repository.InstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
        User user = userService.createInstitutionAdmin(adminMail, password);
        Institution institution = new Institution();
        institution.setName(instituionName);
//        instituion.setPassword(bCryptPasswordEncoder.encode(password));
        institution = institutionRepository.save(institution);
        return institution;
    }
}
