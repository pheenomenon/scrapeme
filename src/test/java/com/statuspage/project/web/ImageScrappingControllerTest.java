package com.statuspage.project.web;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ImageScrappingControllerTest {

    @Test
    public void scrapeForImagesTest() {
        List<String> urlList = new ArrayList<String>();

        urlList.add("https://google.com");
        urlList.add("https://www.statuspage.io");
        urlList.add("https://travis-ci.org/");

        ScrapingJobDto job = new ScrapingJobDto();
        job.setUrls(urlList);

        ImageScrappingController ctrlr = new ImageScrappingController();
        ctrlr.scrapeForImages(job);


    }

}