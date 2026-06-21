package kr.ktb.finn_week6.domain.like.service;

import kr.ktb.finn_week6.domain.like.Like;
import kr.ktb.finn_week6.domain.like.dto.command.LikePostCommand;
import kr.ktb.finn_week6.domain.like.repository.LikeRepository;
import kr.ktb.finn_week6.domain.post.Post;
import kr.ktb.finn_week6.domain.post.repository.PostRepository;
import kr.ktb.finn_week6.domain.user.User;
import kr.ktb.finn_week6.domain.user.repository.UserRepository;
import kr.ktb.finn_week6.global.PermissionValidator;
import kr.ktb.finn_week6.global.RequestMessage;
import kr.ktb.finn_week6.global.customException.IllegalResourceStateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PermissionValidator permissionValidator;

    @Transactional
    public void likePost(LikePostCommand command){
        User user = userRepository.findById(command.sessionUserId()).orElseThrow(
                () -> new NoSuchElementException(RequestMessage.NOT_FOUND_USER.getDescription())
        );
        Post post = postRepository.findById(command.postId()).orElseThrow(
                () -> new NoSuchElementException(RequestMessage.NOT_FOUND_POST.getDescription())
        );
        likeRepository.findByPostIdAndUserId(command.postId(), command.sessionUserId()).ifPresentOrElse(
                like -> {
                    if(!like.isDeleted()){
                        throw new IllegalResourceStateException(RequestMessage.ALREADY_LIKED.getDescription());
                    }
                    like.recovered();
                    post.increaseLikeCount();
                },
                () ->{
                    Like like = new Like(user, post);
                    post.increaseLikeCount();
                    likeRepository.save(like);
                }
        );
    }

    @Transactional
    public void deleteLike(Long likeId, Long sessionUserId){
        Like like = likeRepository.findById(likeId).orElseThrow(
                () -> new NoSuchElementException(RequestMessage.NOT_FOUND.getDescription())
        );
        permissionValidator.validatePermission(like.getUser().getId(), sessionUserId);
        like.setDeleted();
        like.getPost().decreaseLikeCount();
    }

}
