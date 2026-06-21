package kr.ktb.finn_week6.domain.post.dto.command;


public record CreatePostCommand(
        Long userId,
        String title,
        String content,
        String contentImg
) {
}
