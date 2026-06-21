package kr.ktb.finn_week6.global;

import jakarta.servlet.http.HttpSession;
import kr.ktb.finn_week6.global.customException.UnauthenticatedException;
import org.springframework.stereotype.Component;

@Component
public class SessionManager {

    public Long getSessionUserId(HttpSession session) {
        if (session.getAttribute("userId") == null) {
            throw new UnauthenticatedException(RequestMessage.UNAUTHORIZED.getDescription());
        }

        return (Long) session.getAttribute("userId");
    }
}
