package com.company.erwmp.Security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;    //Runs once for every HTTP request

import java.io.IOException;

@Component    //this means spring manages this class
@RequiredArgsConstructor   //Injects dependencies automatically
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;  //Verifies ID card & reads username
    private final CustomUserDetailsService userDetailsService;   //Loads full user info from databas

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException { //This runs for every request

        String jwt = parseJwt(request);  //Reads Authorization header Extracts token after "Bearer "

        if (jwt != null && jwtUtils.validateToken(jwt)) { //check if Token exists, not expired and not tampered
            String username = jwtUtils.getUsernameFromToken(jwt);  //Username was stored inside the token at login time

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);  //loads username and other details

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()
                    ); //this tells spring who user is what roles they have

            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request) //adds IP address and other details like session info
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);  //Spring knows the user is authenticated
        }

        filterChain.doFilter(request, response);  //Lets request proceed to controller
    }

    private String parseJwt(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}

/*
* JwtAuthenticationFilter is the security guard at the gate of your application.
* JwtAuthenticationFilter tells Spring which user is making this request
*
👉 Every request (except login/signup) must pass through this guard.
👉 The guard checks the JWT ID card, verifies it, and tells Spring who the user is.
*
* 🏢 Your Application = Office Building
🛂 JWT Token = Employee ID Card
👮 JwtAuthenticationFilter = Security Guard at the entrance
* */