package com.hivex.campusconnect.dto.News;

import lombok.Data;
import java.util.List;


@Data
public class NewsResponse {


    private int totalArticles;


    private List<NewsArticle> articles;


}