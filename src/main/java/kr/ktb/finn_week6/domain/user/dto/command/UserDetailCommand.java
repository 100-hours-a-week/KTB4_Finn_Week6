package kr.ktb.finn_week6.domain.user.dto.command;


public record UserDetailCommand(
        Long id,
        Long sessionUserId
) {
}
