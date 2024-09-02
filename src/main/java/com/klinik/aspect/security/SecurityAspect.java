package com.klinik.aspect.security;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.klinik.aspect.security.annotation.SecuredControl;
/**
 * АОП для ограничения доступа по роли
 */
@Aspect
@Component
public class SecurityAspect {

    @Before("@annotation(securedControl)")
    public void checkSecurity(JoinPoint joinPoint, SecuredControl securedControl) throws SecurityException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("User is not authenticated");
        }
        String[] requiredRoles = securedControl.roles(); 
        boolean hasRole = false;
        for (String role : requiredRoles) {
            if ( authentication.getAuthorities()
                               .stream()
                               .anyMatch( grantedAuthority -> grantedAuthority.getAuthority()
                                                                              .equals( role ))) {
                hasRole = true;
                break;
            }
        }
        if ( !hasRole ) {
            throw new SecurityException("The user does not have access to this resource");
        }
    }

}