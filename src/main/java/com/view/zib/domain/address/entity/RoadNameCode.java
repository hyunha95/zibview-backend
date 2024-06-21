package com.view.zib.domain.address.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "road_name_code")
public class RoadNameCode {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "road_name_code_id")
    private Long id;

    // 도로명코드
    @Column(name = "road_name_code", length = 12)
    private String roadCode;

    // 도로명
    @Column(name = "road_name", length = 80)
    private String roadName;

    // 도로명 로마자
    @Column(name = "road_name_eng", length = 80)
    private String roadNameEng;

    // 읍면동일련번호
    @Column(name = "emd_num", length = 2)
    private String emdNum;

    // 시도명
    @Column(name = "sido_name", length = 20)
    private String sidoName;

    // 시도명 로마자
    @Column(name = "sido_name_eng", length = 40)
    private String sidoNameEng;

    // 시군구명
    @Column(name = "sigungu_name", length = 20)
    private String sigunguName;

    // 시군구명 로마자
    @Column(name = "sigungu_name_eng", length = 40)
    private String sigunguNameEng;

    // 읍면동명
    @Column(name = "emd_name", length = 20)
    private String emdName;

    // 읍면동명 로마자
    @Column(name = "emd_name_eng", length = 40)
    private String emdNameEng;

    // 읍면동 구분 (0:읍면동, 1:동, 2:미부여)
    @Column(name = "emd_type", length = 1)
    private String emdType;

    // 읍면동 코드
    @Column(name = "emd_code", length = 3)
    private String emdCode;

    // 사용여부 (0:사용, 1:미사용)
    @Column(name = "inactive", length = 1)
    private String inactive;

    // 변경사유 (0:도로명변경, 1:도로명폐지, 2:시도시군구변경, 3:읍면동변경, 4:영문도로명변경, 9:기타)
    @Column(name = "change_reason", length = 1)
    private String changeReason;

    // 변경이력정보 (도로명코드(12)+ 읍면동일련번호(2) ※ 신규정보일경우“신규”로표시)
    @Column(name = "change_history_info", length = 14)
    private String changeHistoryInfo;

    // 공시일자 (YYYYMMDDD)
    @Column(name = "notification_date", length = 8)
    private String notificationDate;

    // 말소일자 (YYYYMMDDD)
    @Column(name = "cancellation_date", length = 8)
    private String cancellationDate;
}
