package kr.ktb.finn_week6.domain.post.dto.response;

import kr.ktb.finn_week6.domain.post.Post;

public record CreatePostResponse(
        Long id,
        String title,
        String content,
        String contentImg
) {

    public static CreatePostResponse createPostResponse(Post post){
        return new CreatePostResponse(post.getId(), post.getTitle(), post.getContent(), post.getContentImg());
    }
}
