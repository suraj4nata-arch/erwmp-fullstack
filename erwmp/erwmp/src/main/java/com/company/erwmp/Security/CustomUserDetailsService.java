package com.company.erwmp.Security;

import com.company.erwmp.user.entity.UserEntity;
import com.company.erwmp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)    //Spring Security calls this automatically, During login and During JWT authentication
            throws UsernameNotFoundException {

        UserEntity user = userRepository.findByUsername(username)  //looks user in DB, if not found auth fails
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new User(    //It is spring security user not entity
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream()
                        .flatMap(role -> role.getPermissions().stream())  //flatMap → collects permissions from all roles
                        .map(permission -> new SimpleGrantedAuthority(permission.getName()))  //Convert role names into Security-understandable format
                        .collect(Collectors.toSet())   //Collect all roles into a set
        );
    }
}

/*
* CustomUserDetailsService is the translator between your database and Spring Security.
* CustomUserDetailsService tells Spring who a user is
👉 Spring Security does NOT know about UserEntity
👉 It only understands UserDetails
*
* */