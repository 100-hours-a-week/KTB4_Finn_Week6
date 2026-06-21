package kr.ktb.finn_week6.domain.user.service;

import kr.ktb.finn_week6.domain.post.Post;
import kr.ktb.finn_week6.domain.post.repository.PostRepository;
import kr.ktb.finn_week6.domain.user.User;
import kr.ktb.finn_week6.domain.user.dto.command.*;
import kr.ktb.finn_week6.domain.user.dto.response.CreateUserResponse;
import kr.ktb.finn_week6.domain.user.dto.response.LoginUserResponse;
import kr.ktb.finn_week6.domain.user.dto.response.UserDetailResponse;
import kr.ktb.finn_week6.domain.user.repository.UserRepository;
import kr.ktb.finn_week6.global.PermissionValidator;
import kr.ktb.finn_week6.global.RequestMessage;
import kr.ktb.finn_week6.global.customException.DuplicateEmailException;
import kr.ktb.finn_week6.global.customException.IncorrectPasswordException;
import kr.ktb.finn_week6.global.customException.NoSuchEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PermissionValidator permissionValidator;


    @Transactional
    public CreateUserResponse register(CreateUserCommand command) {
        if (userRepository.existsByEmail(command.email())) {
            throw new DuplicateEmailException(RequestMessage.DUPLICATE_EMAIL.getDescription());
        }
        User user = command.createUser();
        userRepository.save(user);
        return new CreateUserResponse(user.getId(), user.getNickname());
    }

    public LoginUserResponse login(LoginUserCommand command){
        User user = userRepository.findByEmail(command.email()).orElseThrow(
                () -> new NoSuchEmailException(RequestMessage.NOT_FOUND_EMAIL.getDescription())
        );
        if(!command.password().equals(user.getPassword())){
            throw new IncorrectPasswordException(RequestMessage.INVALID_PASSWORD.getDescription());
        }
        return new LoginUserResponse(user.getId(), user.getNickname(), user.getEmail(), user.getProfileImg());
    }

    public UserDetailResponse getUserDetail(UserDetailCommand command){
        permissionValidator.validatePermission(command.id(), command.sessionUserId());
        User targetUser = userRepository.findById(command.id()).orElseThrow(
                () -> new NoSuchElementException(RequestMessage.NOT_FOUND_USER.getDescription())
        );

        return new UserDetailResponse(targetUser.getId(), targetUser.getNickname(), targetUser.getEmail(), targetUser.getProfileImg());
    }

    @Transactional
    public UserDetailResponse updateUserDetail(UpdateUserCommand command){
        permissionValidator.validatePermission(command.id(), command.sessionUserId());
        User targetUser = userRepository.findById(command.id()).orElseThrow(
                () -> new NoSuchElementException(RequestMessage.NOT_FOUND_USER.getDescription())
        );
        targetUser.updateUser(command.nickname(), command.profileImg());
        return new UserDetailResponse(targetUser.getId(), targetUser.getNickname(), targetUser.getEmail(), targetUser.getProfileImg());
    }

    @Transactional
    public void deleteUser(DeleteUserCommand command){
        permissionValidator.validatePermission(command.id(), command.sessionUserId());
        User targetUser = userRepository.findById(command.id()).orElseThrow(
                () -> new NoSuchElementException(RequestMessage.NOT_FOUND_USER.getDescription())
        );
        targetUser.setDeleted();
        List<Post> posts = postRepository.findByUserId(targetUser.getId());
        for (Post post : posts) {
            post.setDeleted();
        }
    }


}
