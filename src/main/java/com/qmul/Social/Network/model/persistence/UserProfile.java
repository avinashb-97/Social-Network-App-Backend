package com.qmul.Social.Network.model.persistence;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String headline;

    private String bio;

    private String facebook;

    private String instagram;

    private String youtube;

    private String linkedin;

    private String twitter;

    @OneToOne(mappedBy = "profile")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private UserProfilePic image;
}
