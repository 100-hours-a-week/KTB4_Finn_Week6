package kr.ktb.finn_week6.domain.user.dto.command;

import kr.ktb.finn_week6.domain.user.User;

public record LoginUserCommand(
        String email,
        String password
) {
}
