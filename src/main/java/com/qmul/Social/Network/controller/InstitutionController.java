package com.qmul.Social.Network.controller;

import com.qmul.Social.Network.dto.InstitutionDTO;
import com.qmul.Social.Network.model.persistence.Institution;
import com.qmul.Social.Network.model.requests.CreateInstitutionRequest;
import com.qmul.Social.Network.service.InstitutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/institute")
@RestController
public class InstitutionController {

    @Autowired
    private InstitutionService institutionService;

    private static Logger logger = LoggerFactory.getLogger(InstitutionController.class);

    @PostMapping("/create")
    public ResponseEntity<InstitutionDTO> createUser(@RequestBody CreateInstitutionRequest createInstitutionRequest)
    {
        String password = createInstitutionRequest.getPassword();
        if(!isPasswordStrong(password, createInstitutionRequest.getConfirmPassword()))
        {
            logger.info("[Create Institution] bad password given, Institution -> "+ createInstitutionRequest.getAdminMail());
            return ResponseEntity.badRequest().build();
        }
        Institution institution = institutionService.createInstitution(createInstitutionRequest.getName(),
                createInstitutionRequest.getAdminMail(), password);
        logger.info("[Create Institution] Institution Creation successful, Institution -> "+ institution.getName());
        return ResponseEntity.ok(InstitutionDTO.convertEntityToInstitutionDTO(institution));
    }

    @GetMapping("/current")
    public ResponseEntity<InstitutionDTO> getCurrentInstitution()
    {
        Institution institution = institutionService.getCurrentInstitution();
        return ResponseEntity.ok(InstitutionDTO.convertEntityToInstitutionDTO(institution));
    }

    @GetMapping
    public ResponseEntity<List<InstitutionDTO>> getInstitutions()
    {
        List<Institution> institutions = institutionService.getAllInstitutions();
        return ResponseEntity.ok(InstitutionDTO.convertEntityListToInstitutionDTOList(institutions));
    }

    private boolean isPasswordStrong(String password, String confirmPassword)
    {
        return (password.length() >= 8 && confirmPassword.equals(password));
    }
}
