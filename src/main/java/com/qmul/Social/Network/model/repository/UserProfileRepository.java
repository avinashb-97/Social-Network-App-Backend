package com.qmul.Social.Network.model.repository;

import com.qmul.Social.Network.model.persistence.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}
