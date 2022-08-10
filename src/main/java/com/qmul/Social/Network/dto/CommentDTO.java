package com.qmul.Social.Network.dto;

import com.qmul.Social.Network.model.persistence.PostComment;
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
public class CommentDTO {

    private Long id;

    private String content;

    private UserDTO user;

    private Date createdTime;

    public static CommentDTO convertEntityToCommentDTO(PostComment comment)
    {
        CommentDTO commentDTO = new CommentDTO();
        BeanUtils.copyProperties(comment, commentDTO);
        commentDTO.setUser(UserDTO.convertEntityToUserDTO(comment.getUser()));
        return commentDTO;
    }

    public static List<CommentDTO> convertEntityListToCommentDTOList(Set<PostComment> comments)
    {
        List<CommentDTO> commentDTOS = new ArrayList<>();
        for(PostComment comment : comments)
        {
            commentDTOS.add(convertEntityToCommentDTO(comment));
        }
        Collections.sort(commentDTOS, (a, b) -> a.getCreatedTime().compareTo(b.getCreatedTime()));
        return commentDTOS;
    }

}
