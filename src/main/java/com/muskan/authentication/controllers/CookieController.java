package com.muskan.authentication.controllers;

import com.muskan.authentication.service.CookieService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cookie")
@RequiredArgsConstructor
public class CookieController {
    private final CookieService cookieService;

    @GetMapping("/increment-me")
    public String incrementCount(HttpServletRequest request, HttpServletResponse response){
        int count = cookieService.getCount(request);
        count++;
        cookieService.setCount(response, count);

        return "You have visited this API " + count + " times!";
    }

}
