package kr.ktb.finn_week6.domain.post.dto.response;

import kr.ktb.finn_week6.domain.post.Post;

public record UpdatePostResponse(
        Long id,
        String title,
        String content,
        String contentImg
) {
    public static UpdatePostResponse createResponse(Post post){
        return new UpdatePostResponse(post.getId(), post.getTitle(), post.getContent(), post.getContentImg());
    }
}
