package com.hivex.campusconnect.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin
public class AIController {


    @Value("${huggingface.api.key}")
    private String apiKey;


    private final WebClient client = WebClient.builder()
            .baseUrl("https://router.huggingface.co")
            .build();


    @PostMapping("/chat")
    public String chat(@RequestBody Map<String,String> body){

        String message = body.get("message");


        Map<String,Object> request = Map.of(
                "model","meta-llama/Llama-3.1-8B-Instruct",
                "messages",
                List.of(
                        Map.of(
                                "role","user",
                                "content",message
                        )
                ),
                "max_tokens",500
        );


        return client.post()
                .uri("/v1/chat/completions")
                .header(
                        "Authorization",
                        "Bearer "+apiKey
                )
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .block();

    }
}