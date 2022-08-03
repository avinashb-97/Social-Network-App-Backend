package com.qmul.Social.Network.controller;


import com.qmul.Social.Network.dto.InstitutionDTO;
import com.qmul.Social.Network.dto.UserDTO;
import com.qmul.Social.Network.model.persistence.Institution;
import com.qmul.Social.Network.model.persistence.User;
import com.qmul.Social.Network.model.requests.CreateInstitutionRequest;
import com.qmul.Social.Network.model.requests.CreateUserRequest;
import com.qmul.Social.Network.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    public ResponseEntity<UserDTO> getCurrentUser()
    {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(UserDTO.convertEntityToUserDTO(user));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody CreateUserRequest createUserRequest)
    {
        String password = createUserRequest.getPassword();
        if(!isPasswordStrong(password, createUserRequest.getConfirmPassword()))
        {
            logger.info("[Create User] bad password given, User -> "+ createUserRequest.getMail());
            return ResponseEntity.badRequest().build();
        }

        String name = createUserRequest.getName();
        String mail = createUserRequest.getMail();
        Long institutionId = createUserRequest.getInstituteId();
        Long departmentId = createUserRequest.getDepartmentId();
        Long courseId = createUserRequest.getCourseId();
        String code = createUserRequest.getCode();
        User user = userService.createUser(name, password, mail, institutionId, departmentId, courseId, code);
        logger.info("[Create User] User Creation successful, Institution -> "+ name);
        return ResponseEntity.ok(UserDTO.convertEntityToUserDTO(user));
    }

    private boolean isPasswordStrong(String password, String confirmPassword)
    {
        return (password.length() >= 8 && confirmPassword.equals(password));
    }

}
