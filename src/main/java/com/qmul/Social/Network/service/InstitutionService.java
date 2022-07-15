package com.qmul.Social.Network.service;

import com.qmul.Social.Network.model.Instituion;
import com.qmul.Social.Network.model.repository.InstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class InstitutionService {

    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Instituion createInstitution(Instituion instituion, String password)
    {
        if(institutionRepository.existsInstituionByName(instituion.getName()))
        {
            return instituion;
        }
        instituion.setPassword(bCryptPasswordEncoder.encode(password));
        instituion = institutionRepository.save(instituion);
        return instituion;
    }
}
