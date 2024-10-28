package com.example.real_time.RecoveryCode;

import java.util.Optional;

public interface RecoveryCodeRepository {
    Optional<RecoveryCode> findByRecoveryCode(String recoveryCode);
}
