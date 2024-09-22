package com.view.zib.domain.transaction.repository.jpa;

import com.view.zib.domain.transaction.entity.BuildingTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingTransactionJpaRepository extends JpaRepository<BuildingTransaction, Long> {
}
