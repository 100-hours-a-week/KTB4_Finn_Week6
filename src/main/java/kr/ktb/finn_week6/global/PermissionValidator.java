package kr.ktb.finn_week6.global;

import kr.ktb.finn_week6.global.customException.ForbiddenException;
import org.springframework.stereotype.Component;

@Component
public class PermissionValidator {
    public void validatePermission(Long userId, Long sessionUserId) {
        if(!userId.equals(sessionUserId)){
            throw new ForbiddenException(RequestMessage.FORBIDDEN.getDescription());
        }
    }
}
