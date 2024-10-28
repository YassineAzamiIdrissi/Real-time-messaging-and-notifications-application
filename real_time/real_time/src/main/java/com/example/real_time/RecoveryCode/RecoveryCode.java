package com.example.real_time.RecoveryCode;

import com.example.real_time.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EntityListeners(AuditingEntityListener.class)
public class RecoveryCode {
    @Id
    private Integer id;
    private String recoveryCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean used;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
