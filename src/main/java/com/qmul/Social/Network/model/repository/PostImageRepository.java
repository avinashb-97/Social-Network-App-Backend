package com.qmul.Social.Network.model.repository;

import com.qmul.Social.Network.model.persistence.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
}
