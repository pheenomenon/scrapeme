package com.statuspage.project.web;

import java.util.List;

public class ResultJsonWrapper {
    private List<LinkImageResultsDto> results;

    public List<LinkImageResultsDto> getResults() {
        return results;
    }

    public void setResults(List<LinkImageResultsDto> results) {
        this.results = results;
    }
}
