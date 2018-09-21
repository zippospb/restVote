package ru.zippospb.restvote.web.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.zippospb.restvote.AuthorizedUser;

import static java.util.Objects.requireNonNull;

public class SecurityUtil {
    public static AuthorizedUser saveGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null){
            return null;
        }
        Object principal = auth.getPrincipal();
        return (principal instanceof AuthorizedUser) ? (AuthorizedUser) principal : null;
    }

    public static AuthorizedUser get(){
        AuthorizedUser user = saveGet();
        requireNonNull(user, "No authorized user found");
        return user;
    }

    public static int authUserId(){
        return get().getId();
    }
}
