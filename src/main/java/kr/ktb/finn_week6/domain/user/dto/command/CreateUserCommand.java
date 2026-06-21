package kr.ktb.finn_week6.domain.user.dto.command;

import kr.ktb.finn_week6.domain.user.User;
import kr.ktb.finn_week6.domain.user.dto.response.CreateUserResponse;

public record CreateUserCommand(
        String nickname,
        String email,
        String password,
        String profileImg
) {
    public User createUser(){
        return new User(nickname, email, password, profileImg);
    }
}
