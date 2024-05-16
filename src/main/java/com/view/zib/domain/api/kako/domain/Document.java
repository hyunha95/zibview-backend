package com.view.zib.domain.api.kako.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Document {

        @JsonProperty("address_name")
        private String addressName;

        /**
         * address_name의 값의 타입(Type)
         * 다음 중 하나:
         * REGION(지명)
         * ROAD(도로명)
         * REGION_ADDR(지번 주소)
         * ROAD_ADDR(도로명 주소)
         */
        @JsonProperty("address_type")
        private String addressType;

        private String x; // X 좌표값, 경위도인 경우 경도(longitude)
        private String y; // Y 좌표값, 경위도인 경우 위도(latitude)


}
