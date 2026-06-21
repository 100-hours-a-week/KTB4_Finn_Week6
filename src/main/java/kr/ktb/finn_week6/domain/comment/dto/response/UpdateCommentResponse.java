package kr.ktb.finn_week6.domain.comment.dto.response;

import kr.ktb.finn_week6.domain.comment.Comment;

import java.time.LocalDateTime;

public record UpdateCommentResponse(
        Long id,
        Long postId,
        Long userId,
        String username,
        String content,
        LocalDateTime updatedAt
) {
    public static UpdateCommentResponse createResponse(Comment comment){
        return new UpdateCommentResponse(
                comment.getId(),
                comment.getPost().getId(),
                comment.getUser().getId(),
                comment.getUser().getNickname(),
                comment.getContent(),
                comment.getUpdatedAt());
    }
}
