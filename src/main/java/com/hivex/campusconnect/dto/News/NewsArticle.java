package com.hivex.campusconnect.dto.News;

import lombok.Data;

@Data
public class NewsArticle {

    private String title;

    private String description;

    private String content;

    private String url;

    private String image;

    private String publishedAt;

    private Source source;


    @Data
    public static class Source{

        private String name;

        private String url;

    }

}