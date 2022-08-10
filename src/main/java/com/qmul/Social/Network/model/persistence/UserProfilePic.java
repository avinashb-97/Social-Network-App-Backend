package com.qmul.Social.Network.model.persistence;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class UserProfilePic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String filename;

    private String contentType;

    private Long fileSize;

    @OneToOne(mappedBy = "image")
    private UserProfile profile;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] data;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time")
    private Date createdTime;
}
