package com.qmul.Social.Network.service;

import com.qmul.Social.Network.exception.InstitutionNotFoundException;
import com.qmul.Social.Network.model.persistence.*;
import com.qmul.Social.Network.model.repository.PostImageRepository;
import com.qmul.Social.Network.model.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostImageRepository postImageRepository;

    @Autowired
    private UserService userService;

    public Post addCommentForPost(long postId, String content)
    {
        Post post = getPostById(postId);
        User currUser = userService.getCurrentUser();
        PostComment comment = new PostComment();
        comment.setContent(content);
        comment.setUser(currUser);
        comment.setPost(post);
        post.getPostComments().add(comment);
        post = postRepository.save(post);
        return post;
    }

    public Post likePostById(long postId)
    {
        Post post = getPostById(postId);
        User currUser = userService.getCurrentUser();
        if(!post.getLikedUsers().contains(currUser))
        {
            post.getLikedUsers().add(currUser);
            post = postRepository.save(post);
        }
        return post;
    }

    public Post unlikePostById(long postId)
    {
        Post post = getPostById(postId);
        User currUser = userService.getCurrentUser();
        if(post.getLikedUsers().contains(currUser))
        {
            post.getLikedUsers().remove(currUser);
            post = postRepository.save(post);
        }
        return post;
    }

    public void deletePostById(long postId)
    {
        Post post = getPostById(postId);
        if(post.getUser().getId() != userService.getCurrentUser().getId())
        {
            throw new SecurityException("User not allowed to delete this post !");
        }
        postRepository.deleteById(postId);
    }

    public Set<Post> getAllPostsForCurrentUser()
    {
        User user = userService.getCurrentUser();
        Department department = user.getDepartment();
        Set<Post> posts = new HashSet<>();

        for(User curr : department.getUsers())
        {
            posts.addAll(curr.getPosts());
        }
        return posts;
    }

    public Post createPostForCurrentUser(String content, MultipartFile image) throws IOException {
        Post post = new Post();
        post.setContent(content);
        post.setUser(userService.getCurrentUser());
        post.setPostComments(new HashSet());
        post.setLikedUsers(new HashSet());
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
        return post;
    }

    public PostImage getImageByImageId(long id)
    {
        PostImage postImage = null;
        try
        {
            postImage = postImageRepository.getReferenceById(id);
            postImage.setData(Base64.getDecoder().decode(postImage.getData()));
        }
        catch (Exception e)
        {
            throw new RuntimeException("Image Not Found " +id);
        }
        return postImage;
    }

    public Post getPostById(long id)
    {
        Post post = null;
        try
        {
            post = postRepository.getReferenceById(id);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Image Not Found " +id);
        }
        return post;
    }

    public static byte[] compress(byte[] in) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            DeflaterOutputStream defl = new DeflaterOutputStream(out);
            defl.write(in);
            defl.flush();
            defl.close();

            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] decompress(byte[] in) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InflaterOutputStream infl = new InflaterOutputStream(out);
            infl.write(in);
            infl.flush();
            infl.close();

            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
