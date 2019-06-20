package com.statuspage.project.web;

public class UrlLinkDto {
    private String url;
    private int level = 0;

    public UrlLinkDto(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
