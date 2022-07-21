package com.qmul.Social.Network.service;


import com.qmul.Social.Network.conf.constants.SecurityConstants;
import com.qmul.Social.Network.exception.UserNotFoundException;
import com.qmul.Social.Network.model.persistence.Institution;
import com.qmul.Social.Network.model.persistence.User;
import com.qmul.Social.Network.model.persistence.enums.Role;
import com.qmul.Social.Network.model.repository.UserRepository;
import com.qmul.Social.Network.utils.AuthUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    public User getCurrentUser()
    {
        User user = getUserByEmail(AuthUtil.getLoggedInUserName());
        logger.info("Getting current user, userid -> "+user.getId());
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

    public User createInstitutionAdmin(String adminMail, String password, Institution institution)
    {
        User user = new User();
        user.setEmail(adminMail);
        user.setInstitution(institution);
        return createUser(user, password, true);
    }

    private User addAdminRoleToUser(User user)
    {
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(Role.USER);
        roleSet.add(Role.MODERATOR);
        roleSet.add(Role.ADMIN);
        user.setRoles(roleSet);
        return user;
    }

    public User getUserByEmail(String email) {
        return userRepository.findUserByEmailIgnoreCase(email)
                .orElseThrow(() -> new UserNotFoundException("User not found, email -> "+email));
    }

    public User findUserById(Long id) {
        return userRepository.findById(id.toString())
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