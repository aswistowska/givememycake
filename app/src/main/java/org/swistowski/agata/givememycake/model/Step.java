package org.swistowski.agata.givememycake.model;

import com.google.gson.annotations.SerializedName;

import java.net.URL;

public class Step implements java.io.Serializable{

    @SerializedName("id")
    private Integer id;
    @SerializedName("shortDescription")
    private String shortDescription;
    @SerializedName("description")
    private String description;
    @SerializedName("videoURL")
    private String video;
    @SerializedName("thumbnailURL")
    private String thumbnail;

    public Step(int id, String shortDescription, String description, String video, String thumbnail) {
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

    public String getVideo() {
        return video;
    }

    public String getThumbnail() {
        return thumbnail;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
