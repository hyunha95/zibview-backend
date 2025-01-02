package com.view.zib.domain.transaction.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.view.zib.domain.transaction.entity.TransactionApartment;
import com.view.zib.domain.transaction.hash.TransactionApartmentHash;
import com.view.zib.domain.transaction.repository.dto.DuplicateTransactionBuildingDTO;
import com.view.zib.domain.transaction.repository.jdbc.TransactionApartmentJdbcTemplate;
import com.view.zib.domain.transaction.repository.jpa.TransactionApartmentJpaRepository;
import com.view.zib.domain.transaction.repository.redis.TransactionApartmentRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Repository
public class TransactionApartmentRepository {

    private final TransactionApartmentJpaRepository transactionApartmentJpaRepository;
    private final TransactionApartmentJdbcTemplate transactionApartmentJdbcTemplate;
    private final TransactionApartmentRedisRepository transactionApartmentRedisRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;



    public List<DuplicateTransactionBuildingDTO> findBySggCodesInAndDealYearAndDealMonthGroupBy(Set<String> sggCodes, int searchYear, int searchMonth) {
        return transactionApartmentJpaRepository.findBySggCodesInAndDealYearAndDealMonthGroupBy(sggCodes, searchYear, searchMonth);
    }

    public List<TransactionApartment> findBySggCodesInAndDealYearAndDealMonth(Set<String> sggCodes, int searchYear, int searchMonth) {
        return transactionApartmentJpaRepository.findBySggCodesInAndDealYearAndDealMonth(sggCodes, searchYear, searchMonth);
    }

    public List<TransactionApartment> findByJibunIdIn(List<Long> jibunIds) {
        return transactionApartmentJpaRepository.findByJibunIdIn(jibunIds);
    }

    public void bulkInsert(List<TransactionApartment> newTransactionApartments) {
        transactionApartmentJdbcTemplate.bulkInsert(newTransactionApartments);
    }

    public List<TransactionApartmentHash> findByJibunIdInAndDealYearAndDealMonth(Set<Long> jibunIds, List<java.time.YearMonth> yearMonths) {

        List<TransactionApartmentHash> transactionApartmentHashes = new ArrayList<>();
        for (YearMonth yearMonth : yearMonths) {
            List<Object> objects = redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
                for (Long jibunId : jibunIds) {
                    String key = "transactionApartment:" + jibunId + ":" + yearMonth.getYear() + ":" + yearMonth.getMonthValue();
                    connection.hashCommands().hGetAll(key.getBytes());
                }
                return null;
            });

            List<TransactionApartmentHash> results = objects.stream()
                    .filter(o -> !((LinkedHashMap<?, ?>) o).isEmpty())
                    .map(o -> objectMapper.convertValue(o, TransactionApartmentHash.class))
                    .toList();
            transactionApartmentHashes.addAll(results);
        }

        return transactionApartmentHashes;
    }

    private TransactionApartmentHash deserialize(Object member) {
        try {
            return objectMapper.readValue((String)member, TransactionApartmentHash.class);
        } catch (Exception e) {
            log.error("Failed to deserialize transactionApartment: {}", member);
            throw new RuntimeException(e);
        }
    }

    public List<TransactionApartment> findByJibunIdGroupByExclusiveUseAreaOrderByYMD(Long jibunId) {
        return transactionApartmentJpaRepository.findByJibunIdGroupByExclusiveUseAreaOrderByYMD(jibunId);
    }

    public Optional<TransactionApartment> findOneByJibunId(Long jibunId) {
        Page<TransactionApartment> oneByJibunId = transactionApartmentJpaRepository.findOneByJibunId(jibunId, PageRequest.of(0, 1));
        return oneByJibunId.getContent().stream().findFirst();
    }

    public List<TransactionApartment> findByJibunIdAndDealYearAfterAndExclusiveUseArea(Long jibunId, Integer fromYear, BigDecimal exclusiveUseArea) {
        return transactionApartmentJpaRepository.findByJibunIdAndDealYearAfterAndExclusiveUseArea(jibunId, fromYear, exclusiveUseArea);
    }

    public void saveAll(List<TransactionApartment> transactionApartments) {
        transactionApartmentJpaRepository.saveAll(transactionApartments);
    }
}
