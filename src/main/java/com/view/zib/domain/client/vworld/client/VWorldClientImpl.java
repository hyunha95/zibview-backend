package com.view.zib.domain.client.vworld.client;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.view.zib.domain.client.vworld.dto.OfficeTelTransactionClientDTO;
import com.view.zib.domain.client.vworld.dto.VWorldResponseDto;
import com.view.zib.global.properties.ApiProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
@Service
public class VWorldClientImpl implements VWorldClient {

    private final RestClient vworldRestClient;
    private final RestClient officetelTransactionRestClient;
    private final ApiProperties apiProperties;

    public VWorldClientImpl(RestClient vworldRestClient, RestClient officetelTransactionRestClient, ApiProperties apiProperties) {
        this.vworldRestClient = vworldRestClient;
        this.apiProperties = apiProperties;
        this.officetelTransactionRestClient = officetelTransactionRestClient;
    }

    @Override
    public Optional<VWorldResponseDto> searchJibunDetail(String ssgCd, String bjdCd, int jibun, int subJibun) {
        return vworldRestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam(encode("serviceKey"), apiProperties.getVWorld().getKey())
                        .queryParam(encode("sigunguCd"), encode(ssgCd))
                        .queryParam(encode("bjdongCd"), encode(bjdCd))
                        .queryParam(encode("platGbCd"), encode("0"))                                /*0:대지 1:산 2:블록*/
                        .queryParam(encode("bun"), encode(String.format("%04d", jibun)))
                        .queryParam(encode("ji"), encode(String.format("%04d", subJibun)))
                        .queryParam(encode("startDate"), encode(""))                                /*YYYYMMDD*/
                        .queryParam(encode("endDate"), encode(""))                                  /*YYYYMMDD*/
                        .queryParam(encode("numOfRows"), encode("1"))                               /*페이지당 목록 수*/
                        .queryParam(encode("pageNo"), encode("1"))                                  /*페이지번호*/
                        .queryParam("_type", "json")
                        .build())
                .exchange((request, response) -> {
                    validateResponse(response);
                    return getvWorldResponseDto(response);
                });
    }

    private static void validateResponse(RestClient.RequestHeadersSpec.ConvertibleClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is4xxClientError()) {
            log.error("[VWORLD] 잘못된 요청입니다. ERROR CODE: [{}]", response.getStatusCode().value());
            log.error("[VWORLD] ERROR MESSAGE: [{}]", response.bodyTo(String.class));
            throw new BadRequestException("[VWORLD] 잘못된 요청입니다.");
        } else if (response.getStatusCode().is5xxServerError()) {
            throw new BadRequestException("[VWORLD] 서버 오류입니다.");
        } else if (response.getStatusCode().isError()) {
            throw new BadRequestException("[VWORLD] 요청에 실패했습니다.");
        }
    }

    private Optional<VWorldResponseDto> getvWorldResponseDto(RestClient.RequestHeadersSpec.ConvertibleClientHttpResponse response) {
        try {
            VWorldResponseDto vWorldResponseDto = response.bodyTo(VWorldResponseDto.class);
            return Optional.ofNullable(vWorldResponseDto);
        } catch (RestClientException e) {
            log.error("[VWORLD] 데이터 조회에 실패했습니다", e);
            return Optional.empty();
        }
    }

    /**
     * 국토교통부_오피스텔 전월세 실거래가 자료
     * https://www.data.go.kr/data/15126475/openapi.do#/API%20%EB%AA%A9%EB%A1%9D/getRTMSDataSvcOffiRent
     */
    @Override
    public Optional<OfficeTelTransactionClientDTO> getRTMSDataSvcOffiRent(String legalDongCode, String dealYmd) {
        log.info("[VWORLD] 국토교통부_오피스텔 전월세 실거래가 자료 조회 시작 legalDongCode: [{}], dealYmd: [{}]", legalDongCode, dealYmd);
        String lawdCd = legalDongCode.substring(0, 5);

        return officetelTransactionRestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("LAWD_CD", lawdCd)
                        .queryParam("DEAL_YMD", dealYmd)
                        .queryParam("serviceKey", apiProperties.getVWorld().getKey())
                        .queryParam("pageNo", "1")
                        .queryParam("numOfRows", "1000")
                        .build())
                .exchange((request, response) -> {
                    validateResponse(response);
                    return getOfficeTelTransactionClientDTO(response);
                });
    }

    private Optional<OfficeTelTransactionClientDTO> getOfficeTelTransactionClientDTO(RestClient.RequestHeadersSpec.ConvertibleClientHttpResponse response) throws IOException {
        try {
            String body = new String(response.getBody().readAllBytes());
            OfficeTelTransactionClientDTO officeTelTransactionClientDTO = new XmlMapper().readValue(body, OfficeTelTransactionClientDTO.class);
            if (CollectionUtils.isEmpty(officeTelTransactionClientDTO.body().items().item())) {
                return Optional.empty();
            }

            return Optional.of(officeTelTransactionClientDTO);
        } catch (RestClientException e) {
            log.error("[VWORLD] 데이터 조회에 실패했습니다", e);
            return Optional.empty();
        }
    }

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
