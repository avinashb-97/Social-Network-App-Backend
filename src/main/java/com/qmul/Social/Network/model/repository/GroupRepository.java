package com.qmul.Social.Network.model.repository;

import com.qmul.Social.Network.model.persistence.Event;
import com.qmul.Social.Network.model.persistence.Group;
import com.qmul.Social.Network.model.persistence.enums.Visibility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface GroupRepository extends JpaRepository<Group, Long> {

    public Set<Group> findGroupsByVisibility(Visibility visibility);
}
