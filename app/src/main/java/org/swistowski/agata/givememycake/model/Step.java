package org.swistowski.agata.givememycake.model;

import java.net.URL;

public class Step implements java.io.Serializable{

    private final int id;

    private final String shortDescription;
    private final String description;
    private final URL video;
    private final URL thumbnail;

    public Step(int id, String shortDescription, String description, URL video, URL thumbnail) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.video = video;
        this.thumbnail = thumbnail;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public URL getVideo() {
        return video;
    }

    public URL getThumbnail() {
        return thumbnail;
    }
}
