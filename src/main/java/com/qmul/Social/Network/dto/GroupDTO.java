package com.qmul.Social.Network.dto;

import com.qmul.Social.Network.conf.constants.AppConstants;
import com.qmul.Social.Network.model.persistence.Group;
import com.qmul.Social.Network.model.persistence.User;
import com.qmul.Social.Network.model.persistence.enums.GroupType;
import com.qmul.Social.Network.model.persistence.enums.Visibility;
import com.qmul.Social.Network.utils.HelperUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTO {

    private long id;

    private String name;

    private String description;

    private Visibility visibility;

    private Date createdTime;

    private GroupType type;

    private String imageUrl;

    private boolean isJoined;

    private boolean isPending;

    private UserDTO createdUser;

    public static GroupDTO convertEntityToGroupDTO(Group group, User user)
    {
        GroupDTO groupDTO = new GroupDTO();
        BeanUtils.copyProperties(group, groupDTO);
        if(group.getImage() != null)
        {
            String imageUrl = HelperUtil.getImageUrl(group.getImage().getFileSize(), group.getImage().getId(), AppConstants.groupImageUrl);
            groupDTO.setImageUrl(imageUrl);
        }
        groupDTO.setJoined(group.getJoinedUsers().contains(user));
        groupDTO.setPending(group.getPendingUsers().contains(user));
        groupDTO.setCreatedUser(UserDTO.convertEntityToUserDTO(group.getCreatedUser()));
        return groupDTO;
    }

    public static List<GroupDTO> convertEntityListToGroupDTOList(Set<Group> groups, User user)
    {
        List<GroupDTO> groupDTOList = new ArrayList<>();
        for(Group group : groups){
            groupDTOList.add(convertEntityToGroupDTO(group, user));
        }
        groupDTOList.sort((a,b) -> b.getCreatedTime().compareTo(a.getCreatedTime()));
        return groupDTOList;
    }
}
