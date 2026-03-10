package com.company.erwmp.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component  //Spring automatically creates this filter
public class RateLimitingFilter extends OncePerRequestFilter {   //It runs for every HTTP request

    private final Map<String, Integer> requestCounts = new ConcurrentHashMap<>();   //Why ConcurrentHashMap
                                                                                    // Many users hit your app at the same time
                                                                                    // Normal HashMap would break
                                                                                    //This one is safe for multi-threading
    @Override
    protected void doFilterInternal(   //This method runs for every incoming request before your controller.
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws IOException, ServletException {

        String ip = request.getRemoteAddr();  //Identifies who is making the request
        requestCounts.putIfAbsent(ip, 0);  //If this IP is visiting for the first time: Start counting from 0

        if (requestCounts.get(ip) > 100) {   //If requests > 100: stop processing return HTTP 429 Too Many Requests
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            return;
        }

        requestCounts.put(ip, requestCounts.get(ip) + 1);  //Count this request
        filterChain.doFilter(request, response);  //Request is allowed Goes to:Controller, Service and Repository
    }
}

/**
 *
 * This filter limits how many requests one IP can send to your application, so no one can overload or abuse your API.
 *
 * Why Rate Limiting is needed (before code)
    - Problem without rate limiting
 A user (or bot) can send 1000s of requests per second
    - Your server:
    - becomes slow
    - crashes
    - database gets overloaded
    - Attack example:
    - Brute force login
    - DDoS-like abuse
    - API scraping
 * */