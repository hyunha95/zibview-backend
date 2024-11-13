package com.view.zib.domain.client.naver.client;

import com.view.zib.domain.client.naver.Sort;

public interface NaverSearchClient {

    void searchNews(String query, int display, int start, Sort sort);
}
