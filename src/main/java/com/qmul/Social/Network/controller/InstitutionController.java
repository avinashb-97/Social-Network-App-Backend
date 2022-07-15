package com.qmul.Social.Network.controller;

import com.qmul.Social.Network.dto.InstitutionDTO;
import com.qmul.Social.Network.model.Instituion;
import com.qmul.Social.Network.model.requests.CreateInstitutionRequest;
import com.qmul.Social.Network.service.InstitutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/institute")
@RestController
public class InstitutionController {

    @Autowired
    private InstitutionService institutionService;

    private static Logger logger = LoggerFactory.getLogger(InstitutionController.class);

    @PostMapping("/create")
    public ResponseEntity<InstitutionDTO> createUser(@ModelAttribute CreateInstitutionRequest createInstitutionRequest)
    {

        Instituion instituion = convertCreateInstitutionRequestToInstitution(createInstitutionRequest);
        String password = createInstitutionRequest.getPassword();
        if(!isPasswordStrong(password, createInstitutionRequest.getConfirmPassword()))
        {
            logger.info("[Create Institution] bad password given, Institution -> "+ createInstitutionRequest.getAdminMail());
            return ResponseEntity.badRequest().build();
        }
        instituion = institutionService.createInstitution(instituion, password);
        logger.info("[Create Institution] Institution Creation successful, Institution -> "+instituion.getName());
        return ResponseEntity.ok(InstitutionDTO.convertEntityToInstitutionDTO(instituion));
    }

    private Instituion convertCreateInstitutionRequestToInstitution(CreateInstitutionRequest createInstitutionRequest)
    {
        Instituion instituion = new Instituion();
        instituion.setName(createInstitutionRequest.getName());
        instituion.setAdminMail(createInstitutionRequest.getAdminMail());
        return instituion;
    }

    private boolean isPasswordStrong(String password, String confirmPassword)
    {
        return (password.length() >= 8 && confirmPassword.equals(password));
    }
}
