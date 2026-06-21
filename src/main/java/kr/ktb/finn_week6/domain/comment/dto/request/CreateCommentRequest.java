package kr.ktb.finn_week6.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import kr.ktb.finn_week6.domain.comment.dto.command.CreateCommentCommand;

public record CreateCommentRequest(
        @NotBlank(message = "Content is required")
        String content
) {
    public CreateCommentCommand createCommentCommand(Long postId, Long userId, CreateCommentRequest rerequest){
        return new CreateCommentCommand(postId, userId, rerequest.content());
    }
}
