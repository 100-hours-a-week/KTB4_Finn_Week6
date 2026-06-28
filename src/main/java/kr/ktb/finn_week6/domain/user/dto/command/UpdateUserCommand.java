package kr.ktb.finn_week6.domain.user.dto.command;

import kr.ktb.finn_week6.domain.user.dto.response.UserDetailResponse;

public record UpdateUserCommand(
        Long sessionUserId,
        String nickname,
        String profileImg
) {
}
