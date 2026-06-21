package kr.ktb.finn_week6.domain.comment.dto.response;


import java.time.LocalDateTime;

public record CommentDetailResponse(
        Long id,
        Long userId,
        String nickname,
        LocalDateTime createdAt,
        String content,
        boolean isMine
) {
}
