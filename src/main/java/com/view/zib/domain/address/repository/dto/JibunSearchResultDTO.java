package com.view.zib.domain.address.repository.dto;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class JibunSearchResultDTO {
    private Long jibunId;
    private String managementNo;
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

    public String getJibunAddress() {
        StringBuilder sb = new StringBuilder();
        sb.append(sidoName).append(" ");
        sb.append(sggName).append(" ");
        if (StringUtils.isNotBlank(emdName)) {
            sb.append(emdName).append(" ");
        }
        if (StringUtils.isNotBlank(riName)) {
            sb.append(riName).append(" ");
        }
        sb.append(getJibunNo());

        return sb.toString();
    }
}
