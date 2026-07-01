package kr.ktb.finn_week6.security.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class RefreshToken {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    public RefreshToken(String token, Long userId, LocalDateTime expiredAt) {
        this.token = token;
        this.userId = userId;
        this.expiredAt = expiredAt;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiredAt);
    }
}
