package com.qmul.Social.Network.controller;


import com.qmul.Social.Network.dto.UserDTO;
import com.qmul.Social.Network.model.persistence.*;
import com.qmul.Social.Network.model.requests.CreateUserRequest;
import com.qmul.Social.Network.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long id)
    {
        User user = userService.getUserById(id);
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

    @PutMapping("/{id}/profile")
    public ResponseEntity<UserDTO> createEvent(@RequestParam(required = false) String headline,
                                                @RequestParam(required = false) String bio,
                                                @RequestParam(required = false) String facebook,
                                                @RequestParam(required = false) String instagram,
                                                @RequestParam(required = false) String youtube,
                                                @RequestParam(required = false) String linkedin,
                                                @RequestParam(required = false) String twitter,
                                                @RequestParam(required = false) MultipartFile image) throws IOException {


        User user = userService.updateUserProfile(headline, bio, facebook, instagram, youtube, linkedin, twitter, image);
        return ResponseEntity.ok(UserDTO.convertEntityToUserDTO(user));
    }

    private boolean isPasswordStrong(String password, String confirmPassword)
    {
        return (password.length() >= 8 && confirmPassword.equals(password));
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable("id") long imageId)
    {
        UserProfilePic image = userService.getProfileImageByImageId(imageId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"inline; filename= "+image.getFilename())
                .body(new ByteArrayResource(image.getData()));
    }

    @PostMapping("/changepassword")
    public ResponseEntity changepassword(@RequestParam String oldpass,
                                                  @RequestParam String newpass,
                                                  @RequestParam String confirmpass)
    {

        if(!newpass.equals(confirmpass))
        {
            return ResponseEntity.badRequest().build();
        }
        userService.changePasswordForCurrentUser(oldpass, newpass);
        return ResponseEntity.ok().build();
    }

}
