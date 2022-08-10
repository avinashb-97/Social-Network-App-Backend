package com.qmul.Social.Network.controller;

import com.qmul.Social.Network.dto.EventDTO;
import com.qmul.Social.Network.dto.PostDTO;
import com.qmul.Social.Network.model.persistence.Event;
import com.qmul.Social.Network.model.persistence.enums.EventVisibility;
import com.qmul.Social.Network.model.requests.CreateEventRequest;
import com.qmul.Social.Network.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RequestMapping("/api/event")
@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@RequestParam String name,
                                                @RequestParam String place,
                                                @RequestParam String desc,
                                                @RequestParam Date eventTime,
                                                @RequestParam(required = false) MultipartFile image,
                                                @RequestParam EventVisibility visibility) throws IOException {

//        String name = createEventRequest.getName();
//        String place = createEventRequest.getPlace();
//        String desc = createEventRequest.getDescription();
//        Date eventTime = createEventRequest.getTime();
//        MultipartFile image = createEventRequest.getImage();
//        EventVisibility visibility = createEventRequest.getVisibility();

        Event event = eventService.createEvent(name, place, desc, eventTime, visibility, image);
        return ResponseEntity.ok(EventDTO.convertEntityToEventDTO(event));
    }

    @GetMapping
    public ResponseEntity<List<EventDTO>> getEvents()
    {
        Set<Event> events = eventService.getEventsForCurrentUser();
        return ResponseEntity.ok(EventDTO.convertEntityListToEventDTOList(events));
    }


}
