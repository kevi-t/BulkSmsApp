package com.project.messageapp.jwt;

import com.project.messageapp.utils.RequestContext;
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
        if (request.getServletPath().equals("/api/msgApp/register") ||
            request.getServletPath().equals("/api/msgApp/login") ||
            request.getServletPath().equals("/api/msgApp/test" )) {

            filterChain.doFilter(request, response);
        }

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
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                        // Set username in RequestContext
                        RequestContext.setUsername(username);
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