package com.qmul.Social.Network.model.persistence;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class PostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String filename;

    private String contentType;

    private String fileSize;

    @OneToOne(mappedBy = "image")
    private Post post;

    private byte[] data;
}
