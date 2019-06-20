package com.statuspage.project.web;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SingleScrapeJob {

    @JsonProperty
    String levelZeroUrl;

    @JsonProperty
    List<String> imageUri;

    @JsonProperty
    String status="p";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLevelZeroUrl() {
        return levelZeroUrl;
    }

    public void setLevelZeroUrl(String levelZeroUrl) {
        this.levelZeroUrl = levelZeroUrl;
    }

    public List<String> getImageUri() {
        return imageUri;
    }

    public void setImageUri(List<String> imageUri) {
        this.imageUri = imageUri;
    }
}
