package kr.ktb.finn_week6.domain.comment.dto.command;

public record DeleteCommentCommand(
        Long id,
        Long sessionUserId
) {
    public static DeleteCommentCommand createDeleteCommentCommand(Long id, Long sessionUserId) {
        return new DeleteCommentCommand(id, sessionUserId);
    }
}
