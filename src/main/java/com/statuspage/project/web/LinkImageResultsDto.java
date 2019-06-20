package com.statuspage.project.web;

public class LinkImageResultsDto {
    private String jobId;
    private String levelZeroUrl;
    private int level;
    private String imageUrl;

    public LinkImageResultsDto(String jobId, String levelZeroUrl, int level, String imageUrl) {
        this.jobId = jobId;
        this.levelZeroUrl = levelZeroUrl;
        this.level = level;
        this.imageUrl = imageUrl;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getLevelZeroUrl() {
        return levelZeroUrl;
    }

    public void setLevelZeroUrl(String levelZeroUrl) {
        this.levelZeroUrl = levelZeroUrl;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
