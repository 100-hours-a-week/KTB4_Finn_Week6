package kr.ktb.finn_week6.domain.user.dto.response;

public record UserDetailResponse(
        Long id,
        String nickname,
        String email,
        String profileImg
) {
}
