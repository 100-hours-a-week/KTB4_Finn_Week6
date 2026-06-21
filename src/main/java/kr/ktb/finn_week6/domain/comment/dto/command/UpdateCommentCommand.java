package kr.ktb.finn_week6.domain.comment.dto.command;

public record UpdateCommentCommand(
        Long loginUserId,
        Long commentId,
        String content
) {
}
