package com.qmul.Social.Network.controller;


import com.qmul.Social.Network.dto.PostDTO;
import com.qmul.Social.Network.model.persistence.Post;
import com.qmul.Social.Network.model.persistence.PostImage;
import com.qmul.Social.Network.model.requests.CreatePostRequest;
import com.qmul.Social.Network.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RequestMapping("/api/post")
@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<PostDTO> createPostForCurrentUser(@RequestParam String content, @RequestParam(required = false) MultipartFile image) throws IOException {

        Post post = postService.createPostForCurrentUser(content, image);
        return ResponseEntity.ok(PostDTO.convertEntityToPostDTO(post));
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPostForCurrentUser()
    {
        Set<Post> posts = postService.getAllPostsForCurrentUser();
        return ResponseEntity.ok(PostDTO.convertEntityListToPostDTOList(posts));
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable("id") long imageId)
    {
        PostImage image = postService.getImageByImageId(imageId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"inline; filename= "+image.getFilename())
                .body(new ByteArrayResource(image.getData()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePost(@PathVariable("id") long postId)
    {
        postService.deletePostById(postId);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<PostDTO> likePost(@PathVariable("id") long postId)
    {
        Post post = postService.likePostById(postId);
        return ResponseEntity.ok(PostDTO.convertEntityToPostDTO(post));
    }

    @PostMapping("/{id}/unlike")
    public ResponseEntity<PostDTO> unlikePost(@PathVariable("id") long postId)
    {
        Post post = postService.unlikePostById(postId);
        return ResponseEntity.ok(PostDTO.convertEntityToPostDTO(post));
    }

}
