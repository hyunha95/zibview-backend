package com.view.zib.domain.transaction.hash;


import jakarta.persistence.Id;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

import java.math.BigDecimal;
import java.math.RoundingMode;

@ToString
@Getter
@RedisHash("transactionApartment")
public class TransactionApartmentHash {

    @Id
    private Long id;
    private Long jibunId;
    private BigDecimal exclusiveUseArea;
    private Integer dealYear;
    private Integer dealMonth;
    private String dealAmount;

    /**
     * 거래금액을 억 단위로 변환
     * @return
     */
    public BigDecimal getDealAmountInOneHundredMillion() {
        String dealAmountInNumber = this.dealAmount.replace(",", "");
        return new BigDecimal(dealAmountInNumber).divide(BigDecimal.valueOf(10_000));
    }

    /**
     * 전용면적을 평 단위로 변환
     * @return
     */
    public BigDecimal getExclusiveUseAreaInPyung() {
        return this.exclusiveUseArea.multiply(BigDecimal.valueOf(0.3025)).setScale(0, RoundingMode.FLOOR);
    }
}
