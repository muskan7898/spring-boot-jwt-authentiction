package com.muskan.authentication.controllers;

import com.muskan.authentication.dto.request.SignupRequest;
import com.muskan.authentication.dto.response.SignupResponse;
import com.muskan.authentication.service.AuthenticationService;
import jakarta.servlet.http.Cookie;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    // create signup api
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> register(@RequestBody SignupRequest signupRequest){
        try {
            return ResponseEntity.ok(authenticationService.signup(signupRequest));
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }

    @GetMapping("/increment-me")
    public String incrementCount(HttpServletRequest request, HttpServletResponse response){
        int count = authenticationService.getCount(request);
        count++; // Increase the count
        authenticationService.setCount(response, count);

        return "You have visited this API " + count + " times!";
    }
}
