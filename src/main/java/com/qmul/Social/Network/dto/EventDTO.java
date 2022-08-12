package com.qmul.Social.Network.dto;

import com.qmul.Social.Network.conf.constants.AppConstants;
import com.qmul.Social.Network.model.persistence.Event;
import com.qmul.Social.Network.model.persistence.enums.Visibility;
import com.qmul.Social.Network.utils.HelperUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {

    private Long id;

    private String imageUrl;

    private UserDTO createdUser;

    private String name;

    private String place;

    private String description;

    private Date eventDateTime;

    private Visibility visibility;

    public static EventDTO convertEntityToEventDTO(Event event)
    {
        EventDTO eventDTO = new EventDTO();
        BeanUtils.copyProperties(event, eventDTO);
        eventDTO.setCreatedUser(UserDTO.convertEntityToUserDTO(event.getUser()));
        if(event.getImage() != null)
        {
            String imageUrl = HelperUtil.getImageUrl(event.getImage().getFileSize(), event.getImage().getId(), AppConstants.eventImageUrl);
            eventDTO.setImageUrl(imageUrl);
        }
        return eventDTO;
    }

    public static List<EventDTO> convertEntityListToEventDTOList(Set<Event> eventSet)
    {
        List<EventDTO> eventDTOS = new ArrayList<>();
        for(Event event : eventSet)
        {
            eventDTOS.add(EventDTO.convertEntityToEventDTO(event));
        }
        Collections.sort(eventDTOS, (a, b) -> a.getEventDateTime().compareTo(b.getEventDateTime()));
        return eventDTOS;
    }


}
