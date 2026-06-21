package kr.ktb.finn_week6.domain.comment.dto.command;

public record CreateCommentCommand(
        Long postId,
        Long userId,
        String content
) {
}
