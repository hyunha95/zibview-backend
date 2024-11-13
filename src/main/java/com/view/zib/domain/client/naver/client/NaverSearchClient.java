package com.view.zib.domain.client.naver.client;

import com.view.zib.domain.client.naver.dto.NaverNewsResponse;
import com.view.zib.domain.client.naver.enums.Sort;

public interface NaverSearchClient {

    NaverNewsResponse searchNews(String query, int display, int start, Sort sort);
}
