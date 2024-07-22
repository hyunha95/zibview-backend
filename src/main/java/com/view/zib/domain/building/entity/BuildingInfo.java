package com.view.zib.domain.building.entity;

import com.view.zib.domain.api.kako.domain.Coordinate;
import com.view.zib.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Entity
public class BuildingInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "building_info_id")
    private Long id;

    private BigDecimal longitude;
    private BigDecimal latitude;

    private String gisIdntfcNo;             // 	GIS건물통합식별번호	2005197355104560323100000000
    private String pnu;             // 	고유번호	1111018300101970001

    @Column(name = "ld_code")
    private String legalDongCode;          // 	법정동코드	1111018300

    @Column(name = "ld_code_nm")
    private String legalDongCodeName;            // 	법정동명	서울특별시 종로구 평창동
    private String regstrSeCode;            // 	특수지구분코드	1
    private String regstrSeCodeNm;          // 	특수지구분명	일반

    @Column(name = "mnnm_slno")
    private String jibun;            // 	지번	197-1

    @Column(name = "buld_idntfc_no")
    private String buildingIdentificationNumber; // 	건물식별번호	14652
    private String agbldgSeCode;            // 	집합건물구분코드	1
    private String agbldgSeCodeNm;          // 	집합건물구분	일반건축물
    private String buldKndCode;             // 	대장종류코드	2
    private String buldKndCodeNm;           // 	대장종류	일반건축물대장
    private String buldNm;          // 	건물명	(주)길륭평창동사옥

    @Column(name = "buld_dong_nm")
    private String buildingDongName;          // 	건물동명	(주)길륭평창동사옥
    private String buldMainAtachSeCode;             // 	건물주부구분코드	0
    private String buldMainAtachSeCodeNm;           // 	건물주부구분명	주건축물
    private String buldPlotAr;          // 	건물대지면적(㎡)	453
    private String buldBildngAr;            // 	건물건축면적(㎡)	233.3
    private String buldTotar;           // 	건물연면적(㎡)	1154.16
    private String measrmtRt;           // 	용적율(%)	198.45
    private String btlRt;           // 	건폐율(%)	51.5
    private String strctCode;           // 	건축물구조코드	21
    private String strctCodeNm;             // 	건축물구조명	철근콘크리트구조
    private String mainPrposCode;           // 	주요용도코드	04000
    private String mainPrposCodeNm;             // 	주요용도명	제2종근린생활시설
    private String detailPrposCode;             // 	세부용도코드	04001
    private String detailPrposCodeNm;           // 	세부용도명	일반음식점


    private String buildingPurposeCode;             // 	건물용도분류코드	2
    private String buildingPurposeCodeName;           // 	건물용도분류명	상업용
    private String buldHg;          // 	건물높이(m)	17.75
    private String groundFloorCo;           // 	지상층수	5
    private String undgrndFloorCo;          // 	지하층수	1
    private String prmisnDe;            // 	허가일자	2004-11-30
    private String useConfmDe;          // 	사용승인일자	2005-07-19
    private String lastUpdtDt;          // 	데이터기준일자	2017-08-11

    public String getAddress() {
        return legalDongCodeName + " " + jibun;
    }

    public void updateCoordinate(Coordinate coordinate) {
        this.longitude = coordinate.longitude();
        this.latitude = coordinate.latitude();
    }
}
