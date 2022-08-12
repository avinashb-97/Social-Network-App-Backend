package com.qmul.Social.Network.service;

import com.qmul.Social.Network.model.persistence.*;
import com.qmul.Social.Network.model.persistence.enums.GroupType;
import com.qmul.Social.Network.model.persistence.enums.Visibility;
import com.qmul.Social.Network.model.repository.GroupImageRepository;
import com.qmul.Social.Network.model.repository.GroupRepository;
import com.qmul.Social.Network.model.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

@Service
public class GroupService {


    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupImageRepository groupImageRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    public Group createGroup(String name, String desc, GroupType type, Visibility visibility, MultipartFile image) throws IOException {
        User user = userService.getCurrentUser();
        Group group = new Group();
        group.setName(name);
        group.setDescription(desc);
        group.setType(type);
        group.setVisibility(visibility);
        group.setCreatedUser(user);
        group.setDepartment(user.getDepartment());
        group.setInstitution(user.getInstitution());
        if(image != null)
        {
            GroupImage groupImage = new GroupImage();
            groupImage.setContentType(image.getContentType());
            groupImage.setData(Base64.getEncoder().encode(image.getBytes()));
            groupImage.setFilename(image.getOriginalFilename());
            groupImage.setFileSize(image.getSize());
            group.setImage(groupImage);
        }
        Set<User> joinedUsers = new HashSet<>();
        joinedUsers.add(user);
        group.setJoinedUsers(joinedUsers);
        group.setPendingUsers(new HashSet<>());
        group = groupRepository.save(group);
        return group;
    }

    public Group editGroup(Long groupId, String name, String desc, MultipartFile image) throws IOException {
        User user = userService.getCurrentUser();
        Group group = getGroupById(groupId);
        group.setName(name);
        group.setDescription(desc);
        if(image != null)
        {
            GroupImage groupImage = new GroupImage();
            groupImage.setContentType(image.getContentType());
            groupImage.setData(Base64.getEncoder().encode(image.getBytes()));
            groupImage.setFilename(image.getOriginalFilename());
            groupImage.setFileSize(image.getSize());
            group.setImage(groupImage);
        }
        group = groupRepository.save(group);
        return  group;
    }

    public Set<Group> getGroupsForCurrentUser()
    {
        User user = userService.getCurrentUser();
        Set<Group> groups = user.getDepartment().getGroups();
        groups.addAll(getAllowedInstitutionGroups(user.getInstitution().getGroups()));
        Set<Group> everyoneGroups = groupRepository.findGroupsByVisibility(Visibility.EVERYONE);
        groups.addAll(everyoneGroups);
        return groups;
    }

    private Set<Group> getAllowedInstitutionGroups(Set<Group> groups)
    {
        Set<Group> groupSet = new HashSet<>();
        for(Group group : groups)
        {
            if(group.getVisibility() == Visibility.UNIVERSITY)
            {
                groupSet.add(group);
            }
        }
        return groupSet;
    }

    public GroupImage getGroupImageById(long imageId)
    {
        GroupImage groupImage = null;
        try
        {
            groupImage = groupImageRepository.getReferenceById(imageId);
            groupImage.setData(Base64.getDecoder().decode(groupImage.getData()));
        }
        catch (Exception e)
        {
            throw new RuntimeException("Image Not Found " +imageId);
        }
        return groupImage;
    }

    public Group getGroupById(long id)
    {
        Group group = null;
        try
        {
            group = groupRepository.getReferenceById(id);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Group Not Found " +id);
        }
        return group;
    }

    public  Post createPostForCurrentGroup(long groupId, String content, MultipartFile image) throws IOException {
        Group group = getGroupById(groupId);
        Post post = new Post();
        post.setContent(content);
        post.setUser(userService.getCurrentUser());
        post.setPostComments(new HashSet());
        post.setLikedUsers(new HashSet());
        post.setUserGroup(group);
        if(image != null && image.getSize() > 0)
        {
            PostImage postImage = new PostImage();
            postImage.setContentType(image.getContentType());
            postImage.setData(Base64.getEncoder().encode(image.getBytes()));
            postImage.setFilename(image.getOriginalFilename());
            postImage.setFileSize(image.getSize());
            post.setImage(postImage);
        }
        post = postRepository.save(post);
        group.getPosts().add(post);
        groupRepository.save(group);
        return post;
    }

    public Set<Post> getAllCurrentGroupPosts(long groupId)
    {
        Group group = getGroupById(groupId);
        Set<Post> posts = group.getPosts();
        return posts;
    }

    public Group joinGroupByGroupId(long groupId)
    {
        Group group = getGroupById(groupId);
        User user = userService.getCurrentUser();
        group.getJoinedUsers().add(user);
        group = groupRepository.save(group);
        return group;
    }

    public Group leaveGroupByGroupId(long groupId)
    {
        Group group = getGroupById(groupId);
        User user = userService.getCurrentUser();
        group.getJoinedUsers().remove(user);
        group = groupRepository.save(group);
        return group;
    }

}
