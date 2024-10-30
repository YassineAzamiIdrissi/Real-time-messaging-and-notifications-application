package com.example.real_time.RecoveryCode;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecoveryCodeRepository extends JpaRepository<RecoveryCode, Integer> {
    Optional<RecoveryCode> findByRecoveryCode(String recoveryCode);
}
