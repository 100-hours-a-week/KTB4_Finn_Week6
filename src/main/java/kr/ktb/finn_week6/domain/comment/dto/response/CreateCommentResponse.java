package kr.ktb.finn_week6.domain.comment.dto.response;

import kr.ktb.finn_week6.domain.comment.Comment;

import java.time.LocalDateTime;

public record CreateCommentResponse(
        Long id,
        Long postId,
        Long userId,
        String username,
        String content,
        LocalDateTime createdAt
) {
    public static CreateCommentResponse createCommentResponse(Comment comment){
        return new CreateCommentResponse(
                comment.getId(),
                comment.getPost().getId(),
                comment.getUser().getId(),
                comment.getUser().getNickname(),
                comment.getContent(),
                comment.getCreatedAt());

    }
}
