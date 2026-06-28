package kr.ktb.finn_week6.domain.comment.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kr.ktb.finn_week6.domain.comment.dto.command.DeleteCommentCommand;
import kr.ktb.finn_week6.domain.comment.dto.command.UpdateCommentCommand;
import kr.ktb.finn_week6.domain.comment.dto.request.UpdateCommentRequest;
import kr.ktb.finn_week6.domain.comment.dto.response.UpdateCommentResponse;
import kr.ktb.finn_week6.domain.comment.service.CommentService;
import kr.ktb.finn_week6.global.RequestMessage;
import kr.ktb.finn_week6.global.SessionManager;
import kr.ktb.finn_week6.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final SessionManager sessionManager;

    @PatchMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<UpdateCommentResponse> updateComment(@PathVariable Long commentId, @Valid @RequestBody UpdateCommentRequest request, HttpSession session){
        Long sessionUserId = sessionManager.getSessionUserId(session);
        UpdateCommentCommand commentCommand = request.createCommentCommand(sessionUserId, commentId, request.comment());
        UpdateCommentResponse updateCommentResponse = commentService.updateComment(commentCommand);

        return new ApiResponse<>(RequestMessage.SUCCESS.getDescription(), updateCommentResponse);
    }
    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> deleteComment(@PathVariable Long commentId, HttpSession session){
        Long sessionUserId = sessionManager.getSessionUserId(session);
        DeleteCommentCommand deleteCommentCommand = DeleteCommentCommand.createDeleteCommentCommand(commentId, sessionUserId);
        commentService.deleteComment(deleteCommentCommand);
        return new ApiResponse<>(RequestMessage.SUCCESS.getDescription(), null);
    }
}
