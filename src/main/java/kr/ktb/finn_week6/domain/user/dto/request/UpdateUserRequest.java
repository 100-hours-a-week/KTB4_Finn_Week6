package kr.ktb.finn_week6.domain.user.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kr.ktb.finn_week6.domain.user.dto.command.UpdateUserCommand;
import org.hibernate.validator.constraints.URL;

public record UpdateUserRequest(
        @NotNull(message = "Nickname is required")
        @Size(max = 10, message = "Nickname must be less than 10 characters")
        String nickname,
        @URL(message = "Profile image must be a valid URL")
        String profileImg
) {
    public UpdateUserCommand updateUserCommand(Long userId, Long sessionUserId){
        return new UpdateUserCommand(userId, sessionUserId, nickname(), profileImg());
    }
}
