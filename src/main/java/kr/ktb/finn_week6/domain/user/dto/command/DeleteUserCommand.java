package kr.ktb.finn_week6.domain.user.dto.command;

public record DeleteUserCommand(
        Long id,
        Long sessionUserId
) {
}
