package kr.ktb.finn_week6.domain.post.dto.request;

import kr.ktb.finn_week6.domain.post.dto.command.UpdatePostCommand;

public record UpdatePostRequest(
        String title,
        String content,
        String contentImg
) {
    public UpdatePostCommand toCommand(Long userId, Long postId){
        return new UpdatePostCommand(userId,postId,title(),content(),contentImg());
    }
}
