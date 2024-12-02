package com.view.zib.domain.client.naver.client;

import com.view.zib.domain.client.naver.dto.NaverNewsResponse;
import com.view.zib.domain.client.naver.enums.Sort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class NaverSearchClientImplTest {

    @Autowired
    private NaverSearchClient naverSearchClient;

    @Test
    void searchNews() {
        NaverNewsResponse naverNewsResponse = naverSearchClient.searchNews("경기도 성남시 부동산", 10, 1, Sort.SIM);
        assertThat(naverNewsResponse.items().size()).isEqualTo(10);
    }
}