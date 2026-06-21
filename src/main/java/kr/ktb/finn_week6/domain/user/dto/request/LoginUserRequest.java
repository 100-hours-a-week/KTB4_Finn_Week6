package kr.ktb.finn_week6.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import kr.ktb.finn_week6.domain.user.dto.command.LoginUserCommand;

public record LoginUserRequest(
        @NotBlank(message = "Email is required")
        @Email
        String email,

        @NotBlank(message = "Password is required")
        String password
) {
    public LoginUserCommand loginUserCommand(){
        return new LoginUserCommand(email(), password());
    }
}
