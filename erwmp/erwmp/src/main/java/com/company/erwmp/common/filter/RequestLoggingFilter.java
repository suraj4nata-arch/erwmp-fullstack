package com.company.erwmp.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {   //extends OncePerRequestFilter Guarantees the filter runs only once per request

    private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        long start = System.currentTimeMillis();  //records current time

        filterChain.doFilter(request, response);   //send requests to next filter, then controller, service and repository

        long duration = System.currentTimeMillis() - start;   //Calculates total request processing time

        log.info("{} {} -> {} ({} ms)",
                request.getMethod(),  //GET /api/users/11 -> 200 (32 ms)
                request.getRequestURI(),  //POST /api/files/upload -> 201 (145 ms)
                response.getStatus(),  //GET /api/users/999 -> 500 (12 ms)
                duration);
    }
}


/*
* This class:

-->Runs for every HTTP request

* Measures how long the request took
* Logs:
    HTTP method
    URL
    Response status
    Time taken

It does this without touching controllers or services.
*
*
* protected void doFilterInternal(...)
📌 This is the heart of the filter
*
 -->Spring calls this method:
 -->Before the request reaches the controller

And again after response is ready
* */