package com.view.zib.domain.api.building.service;

import com.view.zib.domain.api.building.controller.request.BuildingRequest;
import com.view.zib.domain.api.building.controller.response.BuildingUseResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class VWorldServiceImpl implements VWorldService {

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
                        .queryParam(encode("serviceKey"),     "서비스키")
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

    public static void main(String[] args) {
        VWorldServiceImpl vWorldService = new VWorldServiceImpl();
        vWorldService.getBuildingUse(new BuildingRequest("11110", "10100"));
    }
}
