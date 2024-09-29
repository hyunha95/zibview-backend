package com.view.zib.domain.address.repository.dto;

import com.view.zib.domain.client.vworld.dto.ApartmentTransactionResponse;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class JibunSearchResultDTO {
    private Long jibunId;
    private String sidoName;
    private String sggName;
    private String emdName;
    private String riName;
    private String mountainYn;
    private Integer jibunMain;
    private Integer jibunSub;
    private String buildingName;
    private String legalDongCode;
    private BigDecimal xCoordinate;
    private BigDecimal yCoordinate;

    public String getJibunNo() {
        if (jibunSub == 0) {
            return String.valueOf(jibunMain);
        }
        return jibunMain + "-" + jibunSub;
    }

    public boolean isSameJibun(ApartmentTransactionResponse.Item item) {
        return this.legalDongCode.equals(item.sggCd() + item.umdCd()) && this.getJibunNo().equals(item.jibun());


    }
}
