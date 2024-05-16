package com.view.zib.domain.api.building.service;

import com.view.zib.domain.api.building.controller.request.BuildingRequest;
import com.view.zib.domain.api.building.controller.response.BuildingUseResponse;
import com.view.zib.domain.api.building.domain.BuildingUseApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class VWorldServiceImpl implements VWorldService {

    @Value("${api.vworld.key}")
    private String apiKey;

    /**
     * https://www.data.go.kr/data/15044713/openapi.do
     * @param request
     * @return
     */
    @Override
    public BuildingUseResponse getBuildingUse(BuildingRequest request) {
        RestClient restClient = RestClient.create("http://apis.data.go.kr/1613000/BldRgstService_v2/getBrTitleInfo");
        String response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam(encode("serviceKey"),     "szdoyoxGdJJOLdZTgYr3rr%2F28p3bFNs28qegKTSqrChoYOLUACRpcgc3RmjV%2Bwgxf6qrZmTkCvb%2F2%2F7O6jjhQw%3D%3D")
                        .queryParam(encode("sigunguCd"),  encode(request.sigunguCd()))
                        .queryParam(encode("bjdongCd"),   encode(request.bjdongCd()).substring(0, 5))
                        .queryParam(encode("platGbCd"),   encode("0")) /*0:대지 1:산 2:블록*/
                        .queryParam(encode("bun"),        encode(""))
                        .queryParam(encode("ji"),         encode(""))
                        .queryParam(encode("startDate"),  encode("")) /*YYYYMMDD*/
                        .queryParam(encode("endDate"),    encode("")) /*YYYYMMDD*/
                        .queryParam(encode("numOfRows"),  encode("10")) /*페이지당 목록 수*/
                        .queryParam(encode("pageNo"),     encode("1")) /*페이지번호*/
                        .queryParam(encode("_type"),       encode("json"))
                        .build())
                .retrieve()
                .body(String.class);

        System.out.println(response);
        return BuildingUseResponse.from(null);
    }

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
