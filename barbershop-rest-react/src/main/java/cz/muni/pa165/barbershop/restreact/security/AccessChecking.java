package cz.muni.pa165.barbershop.restreact.security;


import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;

public class AccessChecking {

    public static boolean isAdmin() {
        var principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getRole().equals("ROLE_ADMIN");
    }

    public static boolean hasUserId(Long id) {
        var principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getId().equals(id);
    }

    public static boolean hasUserLogin(String login) {
        var principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getLogin().equals(login);
    }
}
