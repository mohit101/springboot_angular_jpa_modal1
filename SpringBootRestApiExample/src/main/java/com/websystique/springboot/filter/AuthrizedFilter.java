/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.websystique.springboot.filter;

import javax.servlet.Filter;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author mohitm@bdcvit.com
 */
public class AuthrizedFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        System.out.println("Reached once");
        HttpServletRequest req1 = (HttpServletRequest) req;
        boolean accessAllowed = false;
        String valueFromCookie = getValueFromCookie(req1);
        if (valueFromCookie != null) {
            accessAllowed = true;
        } else {
            accessAllowed = false;
        }
        if (accessAllowed) {
            chain.doFilter(req, res);
        } else {
            req.getRequestDispatcher("/htmlpages/error.html").forward(req, res);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

    private String getValueFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
