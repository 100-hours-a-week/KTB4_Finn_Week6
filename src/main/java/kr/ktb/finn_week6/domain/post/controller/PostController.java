package kr.ktb.finn_week6.domain.post.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kr.ktb.finn_week6.domain.comment.dto.command.CreateCommentCommand;
import kr.ktb.finn_week6.domain.comment.dto.request.CreateCommentRequest;
import kr.ktb.finn_week6.domain.comment.dto.response.CommentDetailResponse;
import kr.ktb.finn_week6.domain.comment.dto.response.CommentListResponse;
import kr.ktb.finn_week6.domain.comment.dto.response.CreateCommentResponse;
import kr.ktb.finn_week6.domain.comment.service.CommentService;
import kr.ktb.finn_week6.domain.like.dto.command.LikePostCommand;
import kr.ktb.finn_week6.domain.like.dto.response.LikeResponse;
import kr.ktb.finn_week6.domain.like.service.LikeService;
import kr.ktb.finn_week6.domain.post.dto.command.CreatePostCommand;
import kr.ktb.finn_week6.domain.post.dto.command.UpdatePostCommand;
import kr.ktb.finn_week6.domain.post.dto.request.CreatePostRequest;
import kr.ktb.finn_week6.domain.post.dto.request.UpdatePostRequest;
import kr.ktb.finn_week6.domain.post.dto.response.*;
import kr.ktb.finn_week6.domain.post.service.PostService;
import kr.ktb.finn_week6.global.RequestMessage;
import kr.ktb.finn_week6.global.SessionManager;
import kr.ktb.finn_week6.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final SessionManager sessionManager;
    private final CommentService commentService;
    private final LikeService likeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreatePostResponse> registerPost(@Valid @RequestBody CreatePostRequest request, HttpSession session){
        Long sessionUserId = sessionManager.getSessionUserId(session);
        CreatePostCommand postCommand = request.createPostCommand(sessionUserId);
        CreatePostResponse response = postService.register(postCommand);
        return new ApiResponse<>(RequestMessage.SUCCESS.getDescription(), response);
    }

    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<PostDetailResponse> getPost(@PathVariable Long postId, HttpSession session){
        Long sessionUserId = sessionManager.getSessionUserId(session);

        PostDetailResponse postDetail = postService.getPostDetail(postId, sessionUserId);
        return new ApiResponse<>(RequestMessage.SUCCESS.getDescription(), postDetail);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<PostListResponse> getPostList(HttpSession session){
        sessionManager.getSessionUserId(session);
        List<PostResponse> postList = postService.getPostList();
        PostListResponse postListResponse = PostListResponse.createPostListResponse(postList);
        return new ApiResponse<>(RequestMessage.SUCCESS.getDescription(), postListResponse);
    }

    @PatchMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<UpdatePostResponse> updatePost(@PathVariable Long postId, @Valid @RequestBody UpdatePostRequest request, HttpSession session){
        Long sessionUserId = sessionManager.getSessionUserId(session);
        UpdatePostCommand command = request.toCommand(sessionUserId, postId);
        UpdatePostResponse updatePostResponse = postService.updatePost(command);
        return new ApiResponse<>(RequestMessage.SUCCESS.getDescription(), updatePostResponse);
    }


    @PostMapping("/{postId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateCommentResponse> registerComment(@PathVariable Long postId, @Valid @RequestBody CreateCommentRequest request, HttpSession session){
        Long sessionUserId = sessionManager.getSessionUserId(session);
        CreateCommentCommand commentCommand = request.createCommentCommand(postId, sessionUserId, request);
        CreateCommentResponse register = commentService.register(commentCommand);
        return new ApiResponse<>(RequestMessage.SUCCESS.getDescription(), register);
    }

    @GetMapping("/{postId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<CommentListResponse> getCommentsByPostId(@PathVariable Long postId, HttpSession session){
        Long sessionUserId = sessionManager.getSessionUserId(session);
        List<CommentDetailResponse> comments = commentService.getCommentsByPostId(postId, sessionUserId);
        CommentListResponse commentListResponse = CommentListResponse.createCommentListResponse(comments);
        return new ApiResponse<>(RequestMessage.SUCCESS.getDescription(), commentListResponse);
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable Long postId, HttpSession session){
        Long sessionUserId = sessionManager.getSessionUserId(session);
        postService.deletePost(postId, sessionUserId);
    }

    @PostMapping("/{postId}/like")
    public ApiResponse<LikeResponse> likePost(@PathVariable Long postId, HttpSession session){
        Long sessionUserId = sessionManager.getSessionUserId(session);
        LikePostCommand likePostCommand = LikePostCommand.createLikePostCommand(sessionUserId, postId);
        LikeResponse likeResponseDto = likeService.likePost(likePostCommand);
        return new ApiResponse<>(RequestMessage.SUCCESS.getDescription(), likeResponseDto);
    }

}
