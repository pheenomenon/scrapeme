package com.statuspage.project.service;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class JobResultsDto {
    @JsonProperty
    UUID id;

    @JsonProperty
    List<Map<String, List<String>>> results;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<Map<String, List<String>>> getResults() {
        return results;
    }

    public void setResults(List<Map<String, List<String>>> results) {
        this.results = results;
    }
}
