package com.filipmoszczynski.media.repository;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "media")
public class MediaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    private String fileName;

    public MediaEntity(){
    }

    public MediaEntity(String fileName) {
        this.uuid = UUID.randomUUID().toString();
        this.fileName = fileName;
    }
}
