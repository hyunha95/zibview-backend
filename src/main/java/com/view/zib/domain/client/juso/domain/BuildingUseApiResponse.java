package com.view.zib.domain.client.juso.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * see below link for more information:
 * https://www.vworld.kr/dtna/dtna_apiSvcFc_s001.do?apiNum=6
 */
@Data
public class BuildingUseApiResponse {

    private BuildingUses buildingUses;

    @Data
    public static class BuildingUses {
        private List<Field> field;
        private String resultMsg;
        private String totalCount;
        private String pageNo;
        private String resultCode;
        private String numOfRows;
    }

    @Data
    public static class Field {
        @JsonProperty("mainPurpsCdNm")
        private String mainPrposCodeNm;         // 주요용도명 ex)제2종근린생활시설

        private int hhldCnt; // 세대수(세대)
        private int fmlyCnt; // 가구수(가구)
        private int grndFlrCnt; // 지상층수
        private int ugrndFlrCnt; // 지하층수
        private int rideUseElvtCnt; // 승용승강기수
        private int emgenUseElvtCnt; // 비상용승강기수
        private String bldNm; // 건물명
        private String dongNm; // 동명칭
    }
}
