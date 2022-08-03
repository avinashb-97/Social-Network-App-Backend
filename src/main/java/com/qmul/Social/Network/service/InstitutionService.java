package com.qmul.Social.Network.service;

import com.qmul.Social.Network.exception.InstitutionNotFoundException;
import com.qmul.Social.Network.model.persistence.Institution;
import com.qmul.Social.Network.model.persistence.User;
import com.qmul.Social.Network.model.repository.InstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

@Service
public class InstitutionService {

    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    @Lazy
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
        institution.setCode(generateShareCode());
        institution = institutionRepository.save(institution);
        User user = userService.createInstitutionAdmin(adminMail, password, institution);
        institution.setUsers(new HashSet(){{add(user);}});
        return institution;
    }

    private String generateShareCode()
    {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.valueOf(number);
    }

    public Institution getCurrentInstitution()
    {
        return userService.getCurrentUser().getInstitution();
    }

    public List<Institution> getAllInstitutions()
    {
        return institutionRepository.findAll();
    }

    public Institution getInstitutionById(Long id) {
        Institution institution = null;
        try
        {
            institution = institutionRepository.getReferenceById(id);
        }
        catch (Exception e)
        {
            throw new InstitutionNotFoundException("Institution Not Found " +id);
        }
        return institution;
    }
}
