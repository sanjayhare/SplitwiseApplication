package com.splitwise.filter;

import com.splitwise.service.UserServiceImpl;
import com.splitwise.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class UserAuthFilter extends OncePerRequestFilter {

    private final UserServiceImpl userService;

    private final JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {



        String requestHeader = request.getHeader("Authorization");
        String token = null;
        if(requestHeader != null && requestHeader.startsWith("Bearer_")){
            token = requestHeader.substring(7);
            UserDetails securityUser = userService.loadUserByUsername(jwtUtil.extractUsername(token));

            if(SecurityContextHolder.getContext().getAuthentication() == null && jwtUtil.validateToken(token, securityUser)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(securityUser.getUsername(), null, securityUser.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
