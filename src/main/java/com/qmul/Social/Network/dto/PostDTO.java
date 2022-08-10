package com.qmul.Social.Network.dto;

import com.qmul.Social.Network.conf.constants.AppConstants;
import com.qmul.Social.Network.model.persistence.Post;
import com.qmul.Social.Network.model.persistence.PostComment;
import com.qmul.Social.Network.model.persistence.User;
import com.qmul.Social.Network.utils.AuthUtil;
import com.qmul.Social.Network.utils.HelperUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    private List<CommentDTO> comments;

    public static PostDTO convertEntityToPostDTO(Post post)
    {
        PostDTO postDTO = new PostDTO();
        BeanUtils.copyProperties(post, postDTO);
        postDTO.setCreatedUser(UserDTO.convertEntityToUserDTO(post.getUser()));
        if(post.getImage() != null)
        {
            String imageUrl = HelperUtil.getImageUrl(post.getImage().getFileSize(), post.getImage().getId(), AppConstants.postImageUrl);
            postDTO.setImageUrl(imageUrl);
        }
        if(post.getLikedUsers() != null)
        {
            postDTO.setLikesCount(post.getLikedUsers().size());
            for(User user : post.getLikedUsers())
            {
                if(user.getEmail().equals(AuthUtil.getLoggedInUserName()))
                {
                    postDTO.setLikedByUser(true);
                    break;
                }
            }
        }
        if(post.getPostComments() != null)
        {
            postDTO.setComments(CommentDTO.convertEntityListToCommentDTOList(post.getPostComments()));
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
