package com.view.zib.domain.address.service.impl;

import com.view.zib.domain.address.entity.Jibun;
import com.view.zib.domain.address.repository.JibunRepository;
import com.view.zib.domain.address.repository.dto.JibunMultipleConditionDTO;
import com.view.zib.domain.address.repository.dto.JibunSearchResultDTO;
import com.view.zib.domain.address.service.JibunQueryService;
import com.view.zib.domain.client.vworld.dto.ApartmentTransactionResponse;
import com.view.zib.global.utils.NumberUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class JibunQueryServiceImpl implements JibunQueryService {

    private final JibunRepository jibunRepository;

    @Override
    public Jibun getById(Long jibunId) {
        return jibunRepository.getById(jibunId);
    }

    @Override
    public Jibun getJibunByManagementNo(String managementNo) {
        return jibunRepository.getJibunByManagementNo(managementNo);
    }

    @Override
    public List<JibunSearchResultDTO> findAddressesInUtmkAndNotInJibunIds(BigDecimal minX, BigDecimal minY, BigDecimal maxX, BigDecimal maxY, Set<Long> jibunIds) {
        return jibunRepository.findAddressesInUtmkAndNotInJibunIds(minX, minY, maxX, maxY, jibunIds);
    }

    @Override
    public List<Jibun> findByLegalDongCodeAndJibunNumber(String legalDongCode, String jibunNumber) {
        String jibunMain = jibunNumber;
        String jibunSub = "0";
        if (jibunNumber.contains("-")) {
            String[] jibunNumbers = jibunNumber.split("-");
            if (jibunNumbers.length != 2) {
                return List.of();
            }

            jibunMain = jibunNumbers[0];
            jibunSub = jibunNumbers[1];
        }

        if (!NumberUtils.isDigit(jibunMain) || !NumberUtils.isDigit(jibunSub)) {
            return List.of();
        }

        return jibunRepository.findByLegalDongCodeAndJibunMainAndJibunSub(legalDongCode, jibunMain, jibunSub);
    }

    @Override
    public List<Jibun> findByMultipleLegalDongCodeAndJibunNumber(List<ApartmentTransactionResponse.Item> items) {
        List<JibunMultipleConditionDTO> conditions = new ArrayList<>();
        for (ApartmentTransactionResponse.Item item : items) {
            String jibunNumber = item.jibun();
            String jibunMain = jibunNumber;
            String jibunSub = "0";
            if (jibunNumber.contains("-")) {
                String[] jibunNumbers = jibunNumber.split("-");
                if (jibunNumbers.length != 2) {
                    continue;
                }
                jibunMain = jibunNumbers[0];
                jibunSub = jibunNumbers[1];
            }
            if (!NumberUtils.isDigit(jibunMain) || !NumberUtils.isDigit(jibunSub)) {
                continue;
            }

            conditions.add(JibunMultipleConditionDTO.builder()
                    .legalDongCode(item.legalDongCode())
                    .jibunMain(Integer.parseInt(jibunMain))
                    .jibunSub(Integer.parseInt(jibunSub))
                    .build());
        }


        return jibunRepository.findByMultipleLegalDongCodeAndJibunNumber(conditions);
    }

    @Override
    public List<Jibun> findByIdIn(List<Long> jibunIds) {
        return jibunRepository.findByJibunIdIn(jibunIds);
    }

    @Override
    public List<JibunSearchResultDTO> findAddressInUtmk(BigDecimal minX, BigDecimal minY, BigDecimal maxX, BigDecimal maxY) {
        return jibunRepository.findAddressInUtmk(minX, minY, maxX, maxY);
    }
}
