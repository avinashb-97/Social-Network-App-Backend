package com.qmul.Social.Network.dto;

import com.qmul.Social.Network.model.persistence.Institution;
import com.qmul.Social.Network.model.persistence.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String name;

    private String email;

    public static UserDTO convertEntityToUserDTO(User user)
    {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    public static List<UserDTO> convertEntityListToUserDTOList(Set<User> userSet)
    {
        List<UserDTO> userDTOS = new ArrayList<>();
        for(User user : userSet)
        {
            userDTOS.add(convertEntityToUserDTO(user));
        }
        return userDTOS;
    }

}
