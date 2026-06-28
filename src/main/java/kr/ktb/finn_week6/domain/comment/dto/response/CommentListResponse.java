package kr.ktb.finn_week6.domain.comment.dto.response;

import java.util.List;

public record CommentListResponse(
        List<CommentDetailResponse> comments
) {
    public static CommentListResponse createCommentListResponse(List<CommentDetailResponse> commentResponses){
        return new CommentListResponse(commentResponses);
    }
}
