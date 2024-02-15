package com.project.messageapp.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.messageapp.responses.UniversalResponse;
import com.project.messageapp.utils.SessionAlert;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final SessionAlert msg = new SessionAlert();
    @Autowired
    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        if (request.getServletPath().equals("/api/msgApp/register")) {
            filterChain.doFilter(request, response);
        }
        else {
            String authorizationHeader = request.getHeader("Authorization");
            String token = null;
            String username = null;
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
                try {
                    token = authorizationHeader.substring(8);
                    username = jwtTokenUtil.extractUsername(token);

                    if(username != null && SecurityContextHolder.getContext().getAuthentication()==null){
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                        if (jwtTokenUtil.validateToken(token, userDetails)) {
                            UsernamePasswordAuthenticationToken authenticationToken =
                                    new UsernamePasswordAuthenticationToken(username, null,userDetails.getAuthorities());
                            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                            filterChain.doFilter(request, response);
                        }
                    }
                }
                catch (Exception e) {
                    log.info("exceptions " + e.getMessage());
                    UniversalResponse errorResponse = msg.getSessionErrorMsg();
                    String jsonResponse = new ObjectMapper().writeValueAsString(errorResponse); // Convert UniversalResponse to JSON
                    response.setContentType("application/json;charset=UTF-8"); // Set response content type to JSON
                    response.setStatus(HttpServletResponse.SC_OK); // Set response status to 200 OK
                    response.getWriter().write(jsonResponse); // Write JSON response to the response writer
                }
            }
            else {
                filterChain.doFilter(request, response);
            }

        }
    }
}