package com.muskan.authentication.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class CookieService {

    private final String COUNTER_COOKIE_NAME = "counter";

    public int getCount(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (COUNTER_COOKIE_NAME.equals(cookie.getName())) {
                    return Integer.parseInt(cookie.getValue());
                }
            }
        }

        return 0;
    }

    public void setCount(HttpServletResponse response, int count){
        Cookie cookie = new Cookie(COUNTER_COOKIE_NAME, String.valueOf(count));
        cookie.setMaxAge(900);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
