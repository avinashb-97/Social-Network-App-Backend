package com.qmul.Social.Network.dto;

import com.qmul.Social.Network.conf.constants.AppConstants;
import com.qmul.Social.Network.model.persistence.User;
import com.qmul.Social.Network.model.persistence.UserProfile;
import com.qmul.Social.Network.utils.HelperUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class UserProfileDTO {

    private String headline;

    private String bio;

    private String facebook;

    private String instagram;

    private String youtube;

    private String linkedin;

    private String twitter;

    private String imageUrl;

    public static UserProfileDTO convertEntityToUserProfileDTO(UserProfile userProfile)
    {
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        BeanUtils.copyProperties(userProfile, userProfileDTO);
        if(userProfile.getImage() != null)
        {
            String imageUrl = HelperUtil.getImageUrl(userProfile.getImage().getFileSize(), userProfile.getImage().getId(), AppConstants.profileImageUrl);
            userProfileDTO.setImageUrl(imageUrl);
        }
        return userProfileDTO;
    }

}
