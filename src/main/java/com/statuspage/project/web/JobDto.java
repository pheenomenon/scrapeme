package com.statuspage.project.web;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.UUID;

public class JobDto {
    private UUID id;

    @JsonGetter
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
