package com.view.zib.domain.address.repository.custom.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.view.zib.domain.address.repository.custom.JibunCustomRepository;
import com.view.zib.domain.address.repository.dto.JibunSearchResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

import static com.view.zib.domain.address.entity.QAddressAdditionalInfo.addressAdditionalInfo;
import static com.view.zib.domain.address.entity.QJibun.jibun;
import static com.view.zib.domain.address.entity.QRoadNameAddress.roadNameAddress;
import static com.view.zib.domain.address.entity.QRoadNameCode.roadNameCode;
import static com.view.zib.domain.location.entity.QLocationBuilding.locationBuilding;

@RequiredArgsConstructor
@Repository
public class JibunCustomRepositoryImpl implements JibunCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<JibunSearchResultDTO> findAddressesInUtmk(BigDecimal utmkX, BigDecimal utmkY, BigDecimal utmkXSpan, BigDecimal utmkYSpan) {
        return jpaQueryFactory.select(Projections.fields(
                        JibunSearchResultDTO.class,
                        jibun.id.as("jibunId"),
                        jibun.sidoName,
                        jibun.sggName,
                        jibun.emdName,
                        jibun.riName,
                        jibun.mountainYn,
                        jibun.jibunMain,
                        jibun.jibunSub,
                        locationBuilding.buildingName,
                        jibun.legalDongCode,
                        locationBuilding.xCoordinate,
                        locationBuilding.yCoordinate)
                )
                .from(jibun)
                .join(jibun.roadNameAddress, roadNameAddress)
                .join(roadNameAddress.addressAdditionalInfo, addressAdditionalInfo)
                .join(roadNameAddress.roadNameCode, roadNameCode)
                .join(roadNameAddress.locationBuilding, locationBuilding)
                .where(
                        addressAdditionalInfo.apartmentYn.eq("1"),
                        locationBuilding.buildingName.isNotEmpty(),
                        locationBuilding.xCoordinate.goe(utmkX),
                        locationBuilding.xCoordinate.loe(utmkX.add(utmkXSpan)),
                        locationBuilding.yCoordinate.goe(utmkY),
                        locationBuilding.yCoordinate.loe(utmkY.add(utmkYSpan))
                )
                .fetch();
    }
}
