package com.qmul.Social.Network.model.repository;

import com.qmul.Social.Network.model.persistence.Event;
import com.qmul.Social.Network.model.persistence.enums.EventVisibility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface EventRepository extends JpaRepository<Event, Long> {

    public Set<Event> findEventByVisibility(EventVisibility visibility);

}
