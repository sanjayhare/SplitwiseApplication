package com.splitwise.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class CsrfCookieFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        System.out.println("In CsrfCookieFilter start");
        if (null != csrfToken.getHeaderName()) {
            System.out.println("In CsrfCookieFilter in if loop");
            response.setHeader(csrfToken.getHeaderName(), csrfToken.getToken());
             response.setHeader("Access-Control-Expose-Headers", "X-Requested-With, XSRF-TOKEN, *");
              response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, X-XSRF-TOKEN, Authorization,*");
        }
        filterChain.doFilter(request, response);
    }

}
