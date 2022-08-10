package com.qmul.Social.Network.model.requests;


import com.qmul.Social.Network.model.persistence.enums.EventVisibility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventRequest {

    private String name;

    private String place;

    private Date time;

    private String description;

    private MultipartFile image;

    private EventVisibility visibility;

}
