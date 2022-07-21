package com.qmul.Social.Network.model.repository;

import com.qmul.Social.Network.model.persistence.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
