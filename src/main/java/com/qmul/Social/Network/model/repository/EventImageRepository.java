package com.qmul.Social.Network.model.repository;

import com.qmul.Social.Network.model.persistence.EventImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventImageRepository extends JpaRepository<EventImage, Long> {
}
