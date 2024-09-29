package com.view.zib.domain.transaction.repository.custom.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.view.zib.domain.transaction.repository.custom.TransactionApartmentCustomRepository;
import com.view.zib.domain.transaction.repository.dto.DuplicateTransactionBuildingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

import static com.view.zib.domain.transaction.entity.QTransactionApartment.transactionApartment;

@RequiredArgsConstructor
@Repository
public class TransactionApartmentCustomRepositoryImpl implements TransactionApartmentCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<DuplicateTransactionBuildingDTO> findBySggCodesInAndDealYearAndDealMonthGroupBy(Set<String> sggCodes, String searchYear, String searchMonth) {
        return queryFactory.select(Projections.fields(DuplicateTransactionBuildingDTO.class,
                        transactionApartment.sggCode,
                        transactionApartment.dealYear,
                        transactionApartment.dealMonth
                ))
                .from(transactionApartment)
                .where(transactionApartment.sggCode.in(sggCodes)
                        .and(transactionApartment.dealYear.eq(searchYear))
                        .and(transactionApartment.dealMonth.eq(searchMonth)))
                .groupBy(transactionApartment.sggCode, transactionApartment.dealYear, transactionApartment.dealMonth)
                .fetch();
    }
}
