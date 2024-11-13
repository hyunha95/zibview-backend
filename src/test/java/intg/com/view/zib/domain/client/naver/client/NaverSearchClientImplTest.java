package com.view.zib.domain.client.naver.client;

import com.view.zib.domain.client.naver.Sort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NaverSearchClientImplTest {

    @Autowired
    private NaverSearchClient naverSearchClient;

    @Test
    void searchNews() {
        naverSearchClient.searchNews("test", 10, 1, Sort.SIM);
    }


}