package com.qmul.Social.Network.dto;

import com.qmul.Social.Network.model.persistence.Institution;
import com.qmul.Social.Network.model.persistence.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String name;

    private String email;

    private UserProfileDTO userProfile;

    private Date createdTime;

    private String course;

    private String departmentName;

    private boolean isEnabled;

    public static UserDTO convertEntityToUserDTO(User user)
    {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        if(user.getProfile() != null)
        {
            userDTO.setUserProfile(UserProfileDTO.convertEntityToUserProfileDTO(user.getProfile()));
        }
        if(user.getCourse() != null)
        {
            userDTO.setCourse(user.getCourse().getName());
        }
        if(user.getDepartment() != null)
        {
            userDTO.setDepartmentName(user.getDepartment().getName());
        }
        userDTO.setEnabled(user.isEnabled());
        return userDTO;
    }

    public static List<UserDTO> convertEntityListToUserDTOList(Set<User> userSet)
    {
        List<UserDTO> userDTOS = new ArrayList<>();
        for(User user : userSet)
        {
            userDTOS.add(convertEntityToUserDTO(user));
        }
        Collections.sort(userDTOS, (a, b) -> a.getCreatedTime().compareTo(b.getCreatedTime()));
        return userDTOS;
    }

}
