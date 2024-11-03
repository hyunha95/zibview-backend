package com.view.zib.domain.transaction.repository.redis;

import com.view.zib.domain.transaction.entity.TransactionApartment;
import com.view.zib.domain.transaction.hash.TransactionApartmentHash;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface TransactionApartmentRedisRepository extends CrudRepository<TransactionApartmentHash, Long> {

    List<TransactionApartmentHash> findByJibunIdInAndDealYearAndDealMonth(Set<Long> jibunIds, int year, int month);
}
