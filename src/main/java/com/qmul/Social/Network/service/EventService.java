package com.qmul.Social.Network.service;

import com.qmul.Social.Network.model.persistence.*;
import com.qmul.Social.Network.model.persistence.enums.Visibility;
import com.qmul.Social.Network.model.repository.EventImageRepository;
import com.qmul.Social.Network.model.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventImageRepository eventImageRepository;

    @Autowired
    private UserService userService;

    public Event createEvent(String name, String place, String desc, Date dateTime, Visibility visibility, MultipartFile image) throws IOException {

        Event event = new Event();
        User user = userService.getCurrentUser();
        event.setName(name);
        event.setDescription(desc);
        event.setPlace(place);
        event.setEventDateTime(dateTime);
        event.setVisibility(visibility);
        event.setUser(user);
        event.setInstitution(user.getInstitution());
        event.setDepartment(user.getDepartment());
        if(image != null)
        {
            EventImage eventImage = new EventImage();
            eventImage.setContentType(image.getContentType());
            eventImage.setData(Base64.getEncoder().encode(image.getBytes()));
            eventImage.setFilename(image.getOriginalFilename());
            eventImage.setFileSize(image.getSize());
            event.setImage(eventImage);
        }
        event = eventRepository.save(event);
        return event;
    }

    public EventImage getEventImageById(long imageId)
    {
        EventImage eventImage = null;
        try
        {
            eventImage = eventImageRepository.getReferenceById(imageId);
            eventImage.setData(Base64.getDecoder().decode(eventImage.getData()));
        }
        catch (Exception e)
        {
            throw new RuntimeException("Image Not Found " +imageId);
        }
        return eventImage;
    }

    public Set<Event> getEventsForCurrentUser()
    {
        User user = userService.getCurrentUser();
        Department department = user.getDepartment();
        Set<Event> events = department.getEvents();
        events.addAll(getAllowedInstitutionEvents(user.getInstitution().getEvents()));
        Set<Event> openEvent = eventRepository.findEventByVisibility(Visibility.EVERYONE);
        events.addAll(openEvent);
        return events;
    }

    private Set<Event> getAllowedInstitutionEvents(Set<Event> events)
    {
        Set<Event> eventSet = new HashSet<>();
        for(Event event : events)
        {
            if(event.getVisibility() == Visibility.UNIVERSITY)
            {
                eventSet.add(event);
            }
        }
        return eventSet;
    }

}
