package com.qmul.Social.Network.model.repository;

import com.qmul.Social.Network.model.persistence.UserProfilePic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfilePicRepository extends JpaRepository<UserProfilePic, Long> {
}
