package com.qmul.Social.Network.model.repository;

import com.qmul.Social.Network.model.persistence.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
