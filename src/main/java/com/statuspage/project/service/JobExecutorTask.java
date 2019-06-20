package com.statuspage.project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.statuspage.project.web.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class JobExecutorTask implements Runnable {

    ScrapingJobDto urls;
    String jobId;
    ConcurrentHashMap<String,JobExecutionDetailsDto> jobExecDtls;
    JobExecutionDetailsDto jobDtls = new JobExecutionDetailsDto();

    public final int MAX_LEVEL = 1;
    public JobExecutorTask(ScrapingJobDto urls, String jobId,
                           ConcurrentHashMap<String, JobExecutionDetailsDto> jobExecDtls) {
        this.urls = urls;
        this.jobId = jobId;
        this.jobExecDtls = jobExecDtls;
        jobExecDtls.put(jobId,jobDtls);
    }

    FileWriter file;

    {
        try {
            file = new FileWriter("imageResults.json",true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ObjectMapper mapper = new ObjectMapper();
    SequenceWriter sequenceWriter = null;
    JobResultsDto resultDto = new JobResultsDto();


    @Override
    public void run() {
        ResultJsonWrapper result = new ResultJsonWrapper();
        for (int i = 0; i < urls.getUrls().size(); i++) {
            UrlLinkDto eachLevelZeroUrl = new UrlLinkDto(urls.getUrls().get(i));

            int jobId = i;


            List<LinkImageResultsDto> imgResDto = processJob(result,file, mapper, sequenceWriter, eachLevelZeroUrl, jobId);
            SingleScrapeJob thisurl = new SingleScrapeJob();
            thisurl.setLevelZeroUrl(urls.getUrls().get(i));
            for(LinkImageResultsDto each : imgResDto) {
                thisurl.getImageUri().add(each.getImageUrl());
            }
            thisurl.setStatus("c");

            jobDtls.getInputUrlList().add(thisurl);

        }

        try {
            sequenceWriter = mapper.writer().writeValuesAsArray(file);

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            sequenceWriter.write(jobDtls);

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            sequenceWriter.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<LinkImageResultsDto> processJob(ResultJsonWrapper result,FileWriter file, ObjectMapper mapper, SequenceWriter sequenceWriter
            , UrlLinkDto eachLevelZeroUrl, int jobId) {
        Queue<UrlLinkDto> urlQue = new LinkedList<UrlLinkDto>();
        Set<String> nonDupLinkUrls = new LinkedHashSet<String>();


        List<LinkImageResultsDto> resultList = new ArrayList<LinkImageResultsDto>();
        urlQue.offer(eachLevelZeroUrl);

        while (!urlQue.isEmpty()) {
            UrlLinkDto eachUrlDto = urlQue.poll();

            // get imgs
            Document webDoc = null;
            try {
                webDoc = Jsoup.connect(eachUrlDto.getUrl()).get();
//                Elements images = webDoc.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
                Elements mediaContent = webDoc.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
                //webDoc.select("src");

                if (mediaContent != null) {
                    processImagePaths(jobId,eachLevelZeroUrl, eachUrlDto, mediaContent,resultList);
                }

                if(eachUrlDto.getLevel() >= MAX_LEVEL) {
                    continue;
                }
                Elements links = null;
                links = webDoc.select("a[href");

                if (links != null) {
                    addLinksToQue(urlQue, eachUrlDto, links, nonDupLinkUrls);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


            result.getResults().addAll(resultList);
        return resultList;
    }

    private void processImagePaths(int jobid,UrlLinkDto eachLevelZeroUrl, UrlLinkDto eachUrlDto,
                                   Elements mediaContent,List<LinkImageResultsDto> resultList) throws IOException {
        for (int j = 0; j < mediaContent.size(); j++) {
            Element eachMedia = mediaContent.get(j);
            // TODO make sure its fully qualified

            String imgSrc = eachMedia.attr("abs:src");
            System.out.println("------" + imgSrc);
            saveImagePath(jobid,eachLevelZeroUrl, eachUrlDto, imgSrc,resultList);
            /*if(ImageValidator.validate(imgSrc)) {
                saveImagePath(eachLevelZeroUrl, eachUrlDto, imgSrc,resultList);
            }*/

        }
    }

    private void addLinksToQue(Queue<UrlLinkDto> urlQue, UrlLinkDto eachUrlDto
            , Elements links,Set<String> nonDupLinkUrls) {
        for (int j = 0; j < links.size(); j++) {
            Element eachLink = links.get(j);
            String linkUrl = eachLink.attr("abs:href");

            if(nonDupLinkUrls.add(linkUrl)) {
                UrlLinkDto eachSubLinkDto = new UrlLinkDto(linkUrl);
                eachSubLinkDto.setLevel(eachUrlDto.getLevel()+1);
                urlQue.offer(eachSubLinkDto);
            }

        }
    }

    private void saveImagePath(int jobId,UrlLinkDto eachLevelZeroUrl, UrlLinkDto eachUrlDto
            , String imgSrc,List<LinkImageResultsDto> resultList) throws IOException {
        LinkImageResultsDto imgRes = new LinkImageResultsDto("jobId_",eachLevelZeroUrl.getUrl(),
                eachUrlDto.getLevel(),imgSrc);

        resultList.add(imgRes);
    }
}
