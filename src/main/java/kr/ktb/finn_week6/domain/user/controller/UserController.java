package kr.ktb.finn_week6.domain.user.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kr.ktb.finn_week6.domain.user.dto.command.*;
import kr.ktb.finn_week6.domain.user.dto.request.CreateUserRequest;
import kr.ktb.finn_week6.domain.user.dto.request.LoginUserRequest;
import kr.ktb.finn_week6.domain.user.dto.request.UpdatePasswordRequest;
import kr.ktb.finn_week6.domain.user.dto.request.UpdateUserRequest;
import kr.ktb.finn_week6.domain.user.dto.response.CreateUserResponse;
import kr.ktb.finn_week6.domain.user.dto.response.LoginUserResponse;
import kr.ktb.finn_week6.domain.user.dto.response.UserDetailResponse;
import kr.ktb.finn_week6.domain.user.service.UserService;
import kr.ktb.finn_week6.global.SessionManager;
import kr.ktb.finn_week6.global.RequestMessage;
import kr.ktb.finn_week6.global.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final SessionManager sessionManager;
    public UserController(UserService userService, SessionManager sessionManager) {
        this.userService = userService;
        this.sessionManager = sessionManager;
    }


    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        CreateUserCommand command = request.createUserCommand();
        CreateUserResponse createUserResponse = userService.register(command);
        return new ApiResponse<>(RequestMessage.SUCCESS.getDescription(), createUserResponse);
    }


    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<UserDetailResponse> getUserDetail(HttpSession session) {
        Long sessionUserId = sessionManager.getSessionUserId(session);

        UserDetailCommand userDetailCommand = new UserDetailCommand(sessionUserId);
        UserDetailResponse userDetailResponse = userService.getUserDetail(userDetailCommand);

        return new ApiResponse<>(RequestMessage.SUCCESS.getDescription(), userDetailResponse);
    }

    @PatchMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<UserDetailResponse> updateUserDetail(@Valid @RequestBody UpdateUserRequest request, HttpSession session) {
        Long sessionUserId = sessionManager.getSessionUserId(session);

        UpdateUserCommand updateUserCommand = request.updateUserCommand(sessionUserId);
        UserDetailResponse userDetailResponse = userService.updateUserDetail(updateUserCommand);

        return new ApiResponse<>(RequestMessage.SUCCESS.getDescription(), userDetailResponse);
    }

    @PatchMapping("/me/password")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> updateUserPassword(@Valid @RequestBody UpdatePasswordRequest request, HttpSession session){
        Long sessionUserId = sessionManager.getSessionUserId(session);

        UpdatePasswordCommand command = request.toCommand(sessionUserId);
        userService.updatePassword(command);
        return new ApiResponse<>(RequestMessage.SUCCESS.getDescription(), null);
    }


    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<LoginUserResponse> login(@Valid @RequestBody LoginUserRequest request, HttpSession session) {
        LoginUserCommand loginUserCommand = request.loginUserCommand();
        LoginUserResponse loginUserResponse = userService.login(loginUserCommand);

        session.setAttribute("userId", loginUserResponse.id());

        return new ApiResponse<>(RequestMessage.SUCCESS.getDescription(), loginUserResponse);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> logout(HttpSession session) {
        session.invalidate();
        return new ApiResponse<>(RequestMessage.SUCCESS.getDescription(), null);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(HttpSession session) {
        Long sessionUserId = sessionManager.getSessionUserId(session);
        DeleteUserCommand deleteUserCommand = new DeleteUserCommand(sessionUserId);
        userService.deleteUser(deleteUserCommand);
        session.invalidate();
    }

}
