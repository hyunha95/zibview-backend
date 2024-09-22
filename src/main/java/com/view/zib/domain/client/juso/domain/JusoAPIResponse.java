package com.view.zib.domain.client.juso.domain;

import java.util.List;

public record JusoAPIResponse(List<Result> results) {


    public record Result(Common common, Juso juso) {
    }


    public record Common(String errorMessage, String currentPage, String countPerPage, String totalCount,
                         String errorCode) {
    }

    public record Juso(
            String roadAddr,         //	Y	전체 도로명주소
            String roadAddrPart1,         //	Y	도로명주소(참고항목 제외)
            String roadAddrPart2,         //	N	도로명주소 참고항목
            String jibunAddr,         //	Y	지번주소
            String engAddr,         //	Y	도로명주소(영문)
            String zipNo,         //	Y	우편번호
            String admCd,         //	Y	행정구역코드
            String rnMgtSn,         //	Y	도로명코드
            String bdMgtSn,         //	Y	건물관리번호
            String detBdNmList,         //	N	상세건물명
            String bdNm,         //	N	건물명
            String bdKdcd,         //	Y	공동주택여부(1 : 공동주택, 0 : 비공동주택)
            String siNm,         //	Y	시도명
            String sggNm,         //	Y	시군구명
            String emdNm,         //	Y	읍면동명
            String liNm,         //	N	법정리명
            String rn,         //	Y	도로명
            String udrtYn,         //	Y	지하여부(0 : 지상, 1 : 지하)
            String buldMnnm,         //	Y	건물본번
            String buldSlno,         //	Y	건물부번
            String mtYn,         //	Y	산여부(0 : 대지, 1 : 산)
            String lnbrMnnm,         //	Y	지번본번(번지)
            String lnbrSlno,         //	Y	지번부번(호)
            String emdNo,         //	Y	읍면동일련번호
            String hstryYn, //	Y	* 2020년12월8일 추가된 항목 변동이력여부(0: 현행 주소정보, 1: 요청변수의 keyword(검색어)가 변동된 주소정보에서 검색된 정보)
            String relJibun,         //	N	* 2020년12월8일 추가된 항목 관련지번
            String hemdNm         //	N	* 2020년12월8일 추가된 항목 관할주민센터 ※ 참고정보이며, 실제와 다를 수 있습니다.
    ) {
    }
}
