package com.qmul.Social.Network.dto;

import com.qmul.Social.Network.model.persistence.Post;
import com.qmul.Social.Network.model.persistence.User;
import com.qmul.Social.Network.utils.AuthUtil;
import com.qmul.Social.Network.utils.HelperUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    private Long id;

    private String content;

    private String imageUrl;

    private UserDTO createdUser;

    private Integer likesCount;

    private boolean likedByUser;

    private Date createdTime;

    public static PostDTO convertEntityToPostDTO(Post post)
    {
        PostDTO postDTO = new PostDTO();
        BeanUtils.copyProperties(post, postDTO);
        postDTO.setCreatedUser(UserDTO.convertEntityToUserDTO(post.getUser()));
        if(post.getImage() != null)
        {
            String imageUrl = HelperUtil.getImageUrl(post.getImage(), post.getImage().getId());
            postDTO.setImageUrl(imageUrl);
        }
        postDTO.setLikesCount(post.getLikedUsers().size());
        for(User user : post.getLikedUsers())
        {
            if(user.getEmail().equals(AuthUtil.getLoggedInUserName()))
            {
                postDTO.setLikedByUser(true);
                break;
            }
        }
        return postDTO;
    }

    public static List<PostDTO> convertEntityListToPostDTOList(Set<Post> postSet)
    {
        List<PostDTO> postDTOS = new ArrayList<>();
        for(Post post : postSet)
        {
            postDTOS.add(convertEntityToPostDTO(post));
        }
        postDTOS.sort((a,b) -> b.getCreatedTime().compareTo(a.getCreatedTime()));
        return postDTOS;
    }



}
