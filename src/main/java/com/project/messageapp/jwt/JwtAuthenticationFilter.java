package com.project.messageapp.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/api/msgApp/register")) {
            filterChain.doFilter(request, response);
        }
        else if (request.getServletPath().equals("/api/msgApp/login") ){
            filterChain.doFilter(request, response);
        }
//        else if (request.getServletPath().equals("/api/msgApp/sendFileMessage")) {
//            filterChain.doFilter(request, response);
//        }
        else {

            // If the servlet path matches, continue with the filter chain
            String authorizationHeader = request.getHeader("Authorization");
            String token;
            String username;
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
                try {
                    token = authorizationHeader.substring(7);
                    username = jwtTokenUtil.extractUsername(token);
                    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                        if (jwtTokenUtil.isTokenValid(token, userDetails)) {
                            System.out.println("Het: "+token);
                            System.out.println(userDetails);
                            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
                            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                            filterChain.doFilter(request, response);
                        }
                    }
                }
                catch (Exception e) {
                    log.error("Exception has1 occurred: " + e.getMessage());
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Set HTTP 401 Unauthorized status
                    response.getWriter().write("Unauthorized: " + e.getMessage());
                }
            }
        }


        }
//        else {
//            HttpServletResponse httpResponse;
//            httpResponse = response;
//            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN); // Set HTTP 403 Forbidden status
//            httpResponse.getWriter().write("Access Denied: Invalid Servlet Path");
//            log.info("HTTP Response status set to: {}", HttpServletResponse.SC_FORBIDDEN);
//            return; // Stop further processing
//        }

}