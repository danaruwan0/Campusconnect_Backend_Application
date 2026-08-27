package com.hivex.campusconnect.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class NewsService {

    private final RestTemplate restTemplate;

    @Value("${mediastack.api.key}")
    private String apiKey;

    @Value("${mediastack.base.url}")
    private String baseUrl;

    public NewsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Map<String, Object>> getNews(String category) {

        String url =
                baseUrl +
                        "/news?access_key=" +
                        apiKey +
                        "&categories=" +
                        category +
                        "&languages=en";

        try {

            Map<String, Object> response =
                    restTemplate.getForObject(
                            url,
                            Map.class
                    );

            if (response == null) {
                return Collections.emptyList();
            }

            return (List<Map<String, Object>>) response.get("data");

        } catch (HttpClientErrorException.TooManyRequests e) {

            System.out.println("MediaStack API limit reached.");

            return Collections.emptyList();

        } catch (Exception e) {

            System.out.println("News API error: " + e.getMessage());

            return Collections.emptyList();
        }
    }


    public List<Map<String, Object>> searchNews(String keyword) {

        String url =
                baseUrl +
                        "/news?access_key=" +
                        apiKey +
                        "&keywords=" +
                        keyword +
                        "&languages=en";

        try {

            Map<String, Object> response =
                    restTemplate.getForObject(
                            url,
                            Map.class
                    );

            if (response == null) {
                return Collections.emptyList();
            }

            return (List<Map<String, Object>>) response.get("data");

        } catch (Exception e) {

            System.out.println("News search error: " + e.getMessage());

            return Collections.emptyList();
        }
    }
}