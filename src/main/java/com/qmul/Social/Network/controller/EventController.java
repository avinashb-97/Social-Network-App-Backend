package com.qmul.Social.Network.controller;

import com.qmul.Social.Network.dto.EventDTO;
import com.qmul.Social.Network.model.persistence.Event;
import com.qmul.Social.Network.model.persistence.EventImage;
import com.qmul.Social.Network.model.persistence.enums.Visibility;
import com.qmul.Social.Network.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
                                                @RequestParam Visibility visibility) throws IOException {

        Event event = eventService.createEvent(name, place, desc, eventTime, visibility, image);
        return ResponseEntity.ok(EventDTO.convertEntityToEventDTO(event));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> createEvent(@PathVariable("id") long eventId,
                                                @RequestParam String name,
                                                @RequestParam String place,
                                                @RequestParam String desc,
                                                @RequestParam Date eventTime,
                                                @RequestParam(required = false) MultipartFile image,
                                                @RequestParam Visibility visibility) throws IOException {

        Event event = eventService.editEvent(eventId, name, place, desc, eventTime, visibility, image);
        return ResponseEntity.ok(EventDTO.convertEntityToEventDTO(event));
    }

    @GetMapping
    public ResponseEntity<List<EventDTO>> getEvents()
    {
        Set<Event> events = eventService.getEventsForCurrentUser();
        return ResponseEntity.ok(EventDTO.convertEntityListToEventDTOList(events));
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable("id") long imageId)
    {
        EventImage image = eventService.getEventImageById(imageId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"inline; filename= "+image.getFilename())
                .body(new ByteArrayResource(image.getData()));
    }


}
