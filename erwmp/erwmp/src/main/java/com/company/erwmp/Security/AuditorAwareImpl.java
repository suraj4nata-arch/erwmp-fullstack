package com.company.erwmp.Security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {   //Spring, whenever you need to know who is doing this action, call me.x`

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();   //who is currently logged-in

        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication.getPrincipal().equals("anonymousUser")) {
            return Optional.of("SYSTEM");
        }
        //If no user is logged in, mark the action as done by SYSTEM.


        return Optional.of(authentication.getName());  //It gets the username stored in JWT
    }
}

/*
* This class automatically tells Spring “who is the currently logged-in user” so the system can auto-fill audit fields like
* Every document has:
  Created By
  Last Updated By

* Whenever someone creates or edits a document, tell me who is doing it.”
  ->That receptionist = AuditorAwareImpl
 */