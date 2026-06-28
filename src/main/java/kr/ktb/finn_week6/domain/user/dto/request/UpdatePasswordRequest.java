package kr.ktb.finn_week6.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import kr.ktb.finn_week6.domain.user.dto.command.UpdatePasswordCommand;

public record UpdatePasswordRequest(
        @NotBlank(message = "password is required")
        String password
) {
    public UpdatePasswordCommand toCommand(Long sessionUserId){
        return new UpdatePasswordCommand(sessionUserId,password());
    }

}
