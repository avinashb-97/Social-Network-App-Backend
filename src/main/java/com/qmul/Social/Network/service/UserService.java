package com.qmul.Social.Network.service;


import com.qmul.Social.Network.exception.UserNotFoundException;
import com.qmul.Social.Network.model.persistence.*;
import com.qmul.Social.Network.model.persistence.enums.Role;
import com.qmul.Social.Network.model.repository.UserProfilePicRepository;
import com.qmul.Social.Network.model.repository.UserRepository;
import com.qmul.Social.Network.utils.AuthUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InstitutionService institutionService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserProfilePicRepository userProfilePicRepository;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    public User getCurrentUser()
    {
        User user = getUserByEmail(AuthUtil.getLoggedInUserName());
        logger.info("Getting current user, userid -> "+user.getId());
        return user;
    }

    public User getUserById(long id)
    {
        User user = null;
        try
        {
            user = userRepository.getReferenceById(id);
        }
        catch (Exception e)
        {
            throw new RuntimeException("User Not Found " +id);
        }
        return user;
    }

//    public void generateOTPForPasswordReset(String email) {
//        boolean isUserExists = userRepository.existsUserByEmailIgnoreCase(email);
//        if(!isUserExists)
//        {
//            logger.info("[Password Reset] Trying to generate otp for unregistered user, email -> "+email);
//        }
//        else
//        {
//            Integer otp = otpService.generateOTP(email);
//            emailSenderService.sendOTPForUser(email,  otp, "OTP - Password Reset");
//        }
//    }

    public User enableAccount(long id)
    {
        User user = getUserById(id);
        user.setEnabled(true);
        user = userRepository.save(user);
        return user;
    }


    public User disableAccount(long id)
    {
        User user = getUserById(id);
        user.setEnabled(false);
        user = userRepository.save(user);
        return user;
    }

    public User createUser(User user, String password)
    {
        return createUser(user, password, false);
    }

    public User createUser(User user, String password, boolean isAdmin) {
        if(userRepository.existsUserByEmailIgnoreCase(user.getEmail()))
        {
            user = getUserByEmail(user.getEmail());
            if(!user.isDummyUser())
            {
                return user;
            }
        }
        if(isAdmin)
        {
            user = addAdminRoleToUser(user);
        }
        user.setDummyUser(false);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setEnabled(true);
        user = userRepository.save(user);
        return user;
    }

    public User createUser(String name, String password, String mail, Role role, Long instituteId, Long departmentId, Long courseid, String code)
    {
        Institution institution = institutionService.getInstitutionById(instituteId);
        if(!institution.getCode().equals(code))
        {
            throw new RuntimeException("Invalid Share Code");
        }
        User user = new User();
        user.setName(name);
        user.setEmail(mail);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        if(role == Role.STAFF )
        {
            user = addStaffRoleToUser(user);
        }
        else
        {
            user = addUserRoleToUser(user);
            user.setCourse(departmentService.getCourseById(courseid));
        }
        user.setInstitution(institution);
        user.setDepartment(departmentService.getDepartmentByID(departmentId));
        user.setEnabled(true);
        user.setProfile(new UserProfile());
        user = userRepository.save(user);
        return user;
    }

    public User updateUserProfile(String headline,
                                  String bio,
                                  String facebook,
                                  String instagram,
                                  String youtube,
                                  String linkedin,
                                  String twitter,
                                  MultipartFile profilepic) throws IOException {

        User user = getCurrentUser();
        UserProfile userProfile = user.getProfile();
        if(userProfile == null)
        {
            userProfile = new UserProfile();
        }
        userProfile.setHeadline(headline);
        userProfile.setBio(bio);
        userProfile.setFacebook(facebook);
        userProfile.setInstagram(instagram);
        userProfile.setYoutube(youtube);
        userProfile.setLinkedin(linkedin);
        userProfile.setTwitter(twitter);
        if(profilepic != null)
        {
            UserProfilePic profilePic = new UserProfilePic();
            profilePic.setContentType(profilepic.getContentType());
            profilePic.setData(Base64.getEncoder().encode(profilepic.getBytes()));
            profilePic.setFilename(profilepic.getOriginalFilename());
            profilePic.setFileSize(profilepic.getSize());
            userProfile.setImage(profilePic);
        }
        user.setProfile(userProfile);
        user = userRepository.save(user);
        return user;
    }

    public UserProfilePic getProfileImageByImageId(long imageId)
    {
        UserProfilePic profilePic = null;
        try
        {
            profilePic = userProfilePicRepository.getReferenceById(imageId);
            profilePic.setData(Base64.getDecoder().decode(profilePic.getData()));
        }
        catch (Exception e)
        {
            throw new RuntimeException("Image Not Found " +imageId);
        }
        return profilePic;
    }

    public User createInstitutionAdmin(String adminMail, String password, Institution institution)
    {
        User user = new User();
        user.setEmail(adminMail);
        user.setInstitution(institution);
        return createUser(user, password, true);
    }

    private User addAdminRoleToUser(User user)
    {
        return setRoleForUser(user, true, false, false, false);
    }

    private User addUserRoleToUser(User user)
    {
        return setRoleForUser(user, false, false, true, false);
    }

    private User addStaffRoleToUser(User user)
    {
        return setRoleForUser(user, false, true, true, false);
    }

    private User setRoleForUser(User user, boolean isAdmin, boolean isStaff, boolean isUser, boolean isRecruiter)
    {
        Set<Role> roleSet = new HashSet<>();
        if(isUser) roleSet.add(Role.USER);
        if(isStaff) roleSet.add(Role.STAFF);
        if(isAdmin) roleSet.add(Role.ADMIN);
        if(isRecruiter) roleSet.add(Role.RECRUITER);
        user.setRoles(roleSet);
        return user;
    }

    public User getUserByEmail(String email) {
        return userRepository.findUserByEmailIgnoreCase(email)
                .orElseThrow(() -> new UserNotFoundException("User not found, email -> "+email));
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found, userid -> "+id));
    }

    public void resetPassword(String email, String password) {
        User user = getUserByEmail(email);
        logger.info("[Reset Password] User found successfully, user: "+user.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);
        logger.info("[Reset Password] Password Reset Success, user: "+user.getEmail());
    }

    public void changePasswordForCurrentUser(String oldPassword, String password) {
        User user = userRepository.findUserByEmailIgnoreCase(AuthUtil.getLoggedInUserName())
                .orElseThrow(()-> new UserNotFoundException("User not found"));
        if(!BCrypt.checkpw(oldPassword, user.getPassword()))
        {
            throw new SecurityException("Invalid Password given");
        }
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);
    }

//    public boolean verifyOTP(String email, int userOTP) {
//        Integer otp = otpService.getOtp(email);
//        return userOTP == otp && otp != null && otp != 0;
//    }

    public void deleteUser(String email) {
        User user = getUserByEmail(email);
        userRepository.delete(user);
    }

}