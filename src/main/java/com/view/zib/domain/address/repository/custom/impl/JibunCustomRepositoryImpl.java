package com.view.zib.domain.address.repository.custom.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.view.zib.domain.address.entity.Jibun;
import com.view.zib.domain.address.repository.custom.JibunCustomRepository;
import com.view.zib.domain.address.repository.dto.JibunMultipleConditionDTO;
import com.view.zib.domain.address.repository.dto.JibunSearchResultDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static com.view.zib.domain.address.entity.QAddressAdditionalInfo.addressAdditionalInfo;
import static com.view.zib.domain.address.entity.QJibun.jibun;
import static com.view.zib.domain.address.entity.QRoadNameAddress.roadNameAddress;
import static com.view.zib.domain.address.entity.QRoadNameCode.roadNameCode;
import static com.view.zib.domain.location.entity.QLocationBuilding.locationBuilding;

@Slf4j
@RequiredArgsConstructor
@Repository
public class JibunCustomRepositoryImpl implements JibunCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<JibunSearchResultDTO> findAddressesInUtmkAndNotInJibunIds(BigDecimal minX, BigDecimal minY, BigDecimal maxX, BigDecimal maxY, Set<Long> jibunIds) {
        List<JibunSearchResultDTO> result = jpaQueryFactory.select(Projections.fields(
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
                        jibun.representative,
                        addressAdditionalInfo.apartment,
                        locationBuilding.buildingName.isNotEmpty(),
                        locationBuilding.xCoordinate.goe(minX),
                        locationBuilding.xCoordinate.loe(maxX),
                        locationBuilding.yCoordinate.goe(minY),
                        locationBuilding.yCoordinate.loe(maxY)
                )
                .fetch();

        long start = System.currentTimeMillis();
        List<JibunSearchResultDTO> list = result.stream().filter(dto -> !jibunIds.contains(dto.getJibunId())).toList();
        log.info("filtering time: {}", System.currentTimeMillis() - start);
        return list;
    }

    @Override
    public List<Jibun> findByMultipleLegalDongCodeAndJibunNumber(List<JibunMultipleConditionDTO> conditions) {
        if (conditions.isEmpty()) {
            return List.of();
        }

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        for (JibunMultipleConditionDTO condition : conditions) {
            booleanBuilder.or(
                    jibun.legalDongCode.eq(condition.legalDongCode())
                            .and(jibun.jibunMain.eq(condition.jibunMain()))
                            .and(jibun.jibunSub.eq(condition.jibunSub()))
            );
        }

        return jpaQueryFactory.selectFrom(jibun)
                .leftJoin(jibun.jibunDetail).fetchJoin()
                .where(booleanBuilder)
                .fetch();
    }

    @Override
    public List<JibunSearchResultDTO> findAddressInUtmk(BigDecimal minX, BigDecimal minY, BigDecimal maxX, BigDecimal maxY) {
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
                        addressAdditionalInfo.apartment,
                        locationBuilding.buildingName.isNotEmpty(),
                        locationBuilding.xCoordinate.goe(minX),
                        locationBuilding.xCoordinate.loe(maxX),
                        locationBuilding.yCoordinate.goe(minY),
                        locationBuilding.yCoordinate.loe(maxY)
                )
                .fetch();
    }
}
