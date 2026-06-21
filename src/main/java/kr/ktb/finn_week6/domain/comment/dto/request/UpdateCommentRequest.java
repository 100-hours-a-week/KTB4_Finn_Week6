package kr.ktb.finn_week6.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import kr.ktb.finn_week6.domain.comment.dto.command.UpdateCommentCommand;

public record UpdateCommentRequest(
        @NotBlank(message = "Content is required")
        String content
) {
    public UpdateCommentCommand createCommentCommand(Long loginUserId, Long commentId, String content){
        return new UpdateCommentCommand(loginUserId,commentId,content);
    }
}
