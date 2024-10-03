package com.view.zib.domain.address.repository.custom.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.view.zib.domain.address.repository.custom.JibunCustomRepository;
import com.view.zib.domain.address.repository.dto.JibunSearchResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

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
    public List<JibunSearchResultDTO> findAddressesInUtmkAndNotInJibunIds(BigDecimal minX, BigDecimal minY, BigDecimal maxX, BigDecimal maxY, List<Long> jibunIds) {
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
                        locationBuilding.xCoordinate.goe(minX),
                        locationBuilding.xCoordinate.loe(maxX),
                        locationBuilding.yCoordinate.goe(minY),
                        locationBuilding.yCoordinate.loe(maxY),
                        jibunIdsNotIn(jibunIds)
                )
                .fetch();
    }

    private BooleanExpression jibunIdsNotIn(List<Long> jibunIds) {
        if (CollectionUtils.isEmpty(jibunIds)) return null;

        return jibun.id.notIn(jibunIds);
    }
}
