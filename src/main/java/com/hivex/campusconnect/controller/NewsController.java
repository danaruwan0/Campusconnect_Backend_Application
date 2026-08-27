package com.hivex.campusconnect.controller;


import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import com.hivex.campusconnect.service.NewsService;


@RestController
@RequestMapping("/api/news")
@CrossOrigin
public class NewsController {


    private final NewsService newsService;


    public NewsController(NewsService newsService){

        this.newsService = newsService;

    }



    @GetMapping("/{category}")
    public List<Map<String,Object>> getNews(
            @PathVariable String category
    ){

        return newsService.getNews(category);

    }



    @GetMapping("/search")
    public List<Map<String,Object>> search(
            @RequestParam String keyword
    ){

        return newsService.searchNews(keyword);

    }


}