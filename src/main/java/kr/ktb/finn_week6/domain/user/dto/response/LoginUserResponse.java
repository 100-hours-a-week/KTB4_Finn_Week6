package kr.ktb.finn_week6.domain.user.dto.response;

public record LoginUserResponse(
        Long id,
        String nickname,
        String email,
        String profileImg
) {
}
