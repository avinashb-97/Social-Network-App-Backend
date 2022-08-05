package com.qmul.Social.Network.model.repository;

import com.qmul.Social.Network.model.persistence.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
