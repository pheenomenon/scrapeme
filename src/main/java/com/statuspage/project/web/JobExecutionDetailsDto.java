package com.statuspage.project.web;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class JobExecutionDetailsDto {

    @JsonProperty
    List<SingleScrapeJob> inputUrlList = new ArrayList<SingleScrapeJob>();

    public List<SingleScrapeJob> getInputUrlList() {
        return inputUrlList;
    }

    public void setInputUrlList(List<SingleScrapeJob> inputUrlList) {
        this.inputUrlList = inputUrlList;
    }
}
