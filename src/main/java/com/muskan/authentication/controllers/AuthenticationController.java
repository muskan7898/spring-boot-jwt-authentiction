package com.muskan.authentication.controllers;

import com.muskan.authentication.dto.request.LoginRequest;
import com.muskan.authentication.dto.request.SignupRequest;
import com.muskan.authentication.service.AuthenticationService;

import com.muskan.authentication.service.CookieService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final CookieService cookieService;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody SignupRequest signupRequest){
        try {
            return ResponseEntity.ok(authenticationService.signup(signupRequest));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getBody());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response){
        try{
            String token = authenticationService.login(loginRequest);
            cookieService.setAuthCookie(response, token);
            return ResponseEntity.ok("login success");
        }
        catch (ResponseStatusException e){
            return ResponseEntity.status(e.getStatusCode()).body(e.getBody());
        }
        catch (Exception e){
            return ResponseEntity.status(500).body("internal server error");
        }
    }

    @GetMapping("/get-user")
    public ResponseEntity<?> getUserDetail(HttpServletResponse response, HttpServletRequest request){
        try{
            Cookie cookie = cookieService.getAuthCookie(request);
            if(cookie == null){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "login please");
            }
            String token = cookie.getValue();
            Long userId = authenticationService.getPayload(token);
            return ResponseEntity.ok(authenticationService.getUser(userId));
        }
        catch (ResponseStatusException e){
            return ResponseEntity.status(e.getStatusCode()).body(e.getBody());
        }
        catch (Exception e){
            return ResponseEntity.status(500).body("internal server error");
        }
    }

}
