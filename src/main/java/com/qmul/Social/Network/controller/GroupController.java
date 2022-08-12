package com.qmul.Social.Network.controller;

import com.qmul.Social.Network.dto.GroupDTO;
import com.qmul.Social.Network.dto.PostDTO;
import com.qmul.Social.Network.model.persistence.Group;
import com.qmul.Social.Network.model.persistence.GroupImage;
import com.qmul.Social.Network.model.persistence.Post;
import com.qmul.Social.Network.model.persistence.enums.GroupType;
import com.qmul.Social.Network.model.persistence.enums.Visibility;
import com.qmul.Social.Network.service.GroupService;
import com.qmul.Social.Network.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RequestMapping("/api/group")
@RestController
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<GroupDTO> createGroup(@RequestParam String name,
                                                @RequestParam String desc,
                                                @RequestParam GroupType type,
                                                @RequestParam(required = false) MultipartFile image,
                                                @RequestParam Visibility visibility) throws IOException {

        Group group = groupService.createGroup(name, desc, type, visibility, image);
        return ResponseEntity.ok(GroupDTO.convertEntityToGroupDTO(group, userService.getCurrentUser()));
    }

    @GetMapping
    public ResponseEntity<List<GroupDTO>> getAllGroupsForUser()
    {
        Set<Group> groups = groupService.getGroupsForCurrentUser();
        return ResponseEntity.ok(GroupDTO.convertEntityListToGroupDTOList(groups, userService.getCurrentUser()));
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable("id") long imageId)
    {
        GroupImage image = groupService.getGroupImageById(imageId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"inline; filename= "+image.getFilename())
                .body(new ByteArrayResource(image.getData()));
    }

    @PostMapping("/{id}/post")
    public ResponseEntity<PostDTO> createPostForCurrentUser(@PathVariable("id") long groupId, @RequestParam String content, @RequestParam(required = false) MultipartFile image) throws IOException {

        Post post = groupService.createPostForCurrentGroup(groupId,content, image);
        return ResponseEntity.ok(PostDTO.convertEntityToPostDTO(post));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDTO> getGroupById(@PathVariable("id") long groupId)
    {
        Group group = groupService.getGroupById(groupId);
        return ResponseEntity.ok(GroupDTO.convertEntityToGroupDTO(group, userService.getCurrentUser()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupDTO> createGroup(@PathVariable("id") long groupId,
                                                @RequestParam String name,
                                                @RequestParam String desc,
                                                @RequestParam(required = false) MultipartFile image) throws IOException {

        Group group = groupService.editGroup(groupId, name, desc, image);
        return ResponseEntity.ok(GroupDTO.convertEntityToGroupDTO(group, userService.getCurrentUser()));
    }

    @GetMapping("/{id}/post")
    public ResponseEntity<List<PostDTO>> getAllCurrentGroupPosts(@PathVariable("id") long groupId)
    {
        Set<Post> posts = groupService.getAllCurrentGroupPosts(groupId);
        return ResponseEntity.ok(PostDTO.convertEntityListToPostDTOList(posts));
    }

    @PostMapping("/{id}/join")
    public ResponseEntity<GroupDTO> joinGroup(@PathVariable("id") long groupId)
    {
        Group group = groupService.joinGroupByGroupId(groupId);
        return ResponseEntity.ok(GroupDTO.convertEntityToGroupDTO(group, userService.getCurrentUser()));
    }

    @PostMapping("/{id}/leave")
    public ResponseEntity<GroupDTO> leaveGroup(@PathVariable("id") long groupId)
    {
        Group group = groupService.leaveGroupByGroupId(groupId);
        return ResponseEntity.ok(GroupDTO.convertEntityToGroupDTO(group, userService.getCurrentUser()));
    }



}
