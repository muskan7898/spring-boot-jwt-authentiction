package com.muskan.authentication.service;

import com.muskan.authentication.entities.User;
import com.muskan.authentication.repository.AuthenticationRepo;
import com.muskan.authentication.dto.request.SignupRequest;
import com.muskan.authentication.dto.response.SignupResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationRepo authenticationRepo;
    private final String COOKIE_NAME = "count";

    public SignupResponse signup(SignupRequest signupRequest) {
        // create the user with the signup request
        try {
            if(authenticationRepo.existsByUsername(signupRequest.getUsername())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user name is already taken");
            }
            User user = User.builder()
                    .username(signupRequest.getUsername())
                    .password(signupRequest.getPassword())
                            .build();


            User savedUser = authenticationRepo.save(user);
            System.out.println(savedUser.getId().toString());

            return new SignupResponse(
                    savedUser.getId(),
                    savedUser.getUsername(),
                    savedUser.getPassword()
            );
        }
        catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public int getCount(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (COOKIE_NAME.equals(cookie.getName())) {
                    return Integer.parseInt(cookie.getValue());
                }
            }
        }
        return 0; // If no cookie found, return 0
    }

    public void setCount(HttpServletResponse response, int count){
        Cookie cookie = new Cookie(COOKIE_NAME, String.valueOf(count));
        cookie.setMaxAge(900);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
