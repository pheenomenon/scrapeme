package com.statuspage.project.web;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.statuspage.project.service.JobExecutorTask;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

/*
 Controller class to scrape images for given
 list of urls
*/
@RestController
@RequestMapping(path = "/jobs")
public class ImageScrappingController {
    public final int MAX_LEVEL = 1;
    private int masterJobId = 1;
    private ConcurrentHashMap<String,JobExecutionDetailsDto> jobExecDtls = new ConcurrentHashMap<String, JobExecutionDetailsDto>();

    public ImageScrappingController() {

    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json",path = "/{jobId}/status")
    public String getJobStatus(@PathVariable("jobId") String jobId) {

        return jobId;
    }


    @RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public String scrapeForImages(@RequestBody ScrapingJobDto urls){

        String resp = null;
        try {
            resp = saveJobDetails(urls);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new JobExecutorTask(urls,resp,jobExecDtls));
        return resp ;
    }


    private String saveJobDetails(ScrapingJobDto urls) throws JsonProcessingException {
        JobDto job = new JobDto();
        UUID jobId = UUID.randomUUID();
        job.setId(jobId);
        return jobId.toString();
        /*
        FileWriter file = null;
        try {
            file = new FileWriter("jobs.json",true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer();
        try {
            writer.writeValue(file,job);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapper.writeValueAsString(job);
        */
    }



}
