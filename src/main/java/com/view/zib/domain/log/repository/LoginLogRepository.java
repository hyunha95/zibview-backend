package com.view.zib.domain.log.repository;

import com.view.zib.domain.log.entity.LoginLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginLogRepository extends JpaRepository<LoginLog, Long> {
}
