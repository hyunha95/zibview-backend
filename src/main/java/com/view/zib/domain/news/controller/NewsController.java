package com.view.zib.domain.news.controller;

import com.view.zib.domain.client.naver.client.NaverSearchClient;
import com.view.zib.domain.client.naver.dto.NaverNewsResponse;
import com.view.zib.domain.client.naver.enums.Sort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/news")
@RestController
public class NewsController {

    private final NaverSearchClient naverSearchClient;

    @GetMapping("/naver")
    public NaverNewsResponse searchNews(String query, int start, int display) {
        return naverSearchClient.searchNews(query, display, start, Sort.SIM);
    }

}
