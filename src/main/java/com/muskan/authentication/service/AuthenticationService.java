package com.muskan.authentication.service;

import com.muskan.authentication.dto.request.CreateJwtRequest;
import com.muskan.authentication.dto.request.LoginRequest;
import com.muskan.authentication.dto.response.JwtPayloadResponse;
import com.muskan.authentication.dto.response.UserDetailResponse;
import com.muskan.authentication.entities.User;
import com.muskan.authentication.repository.AuthenticationRepo;
import com.muskan.authentication.dto.request.SignupRequest;
import com.muskan.authentication.dto.response.SignupResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Value("${jwt.secret}")
    private String jwtSecret;
    private final AuthenticationRepo authenticationRepo;
    private final String COOKIE_NAME = "count";
    private final JwtService jwtService;
    private final CookieService cookieService;

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

    public String login(LoginRequest loginRequest) {
        User user = authenticationRepo.findByUsername(loginRequest.getUsername());
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "no user");
        }
        if (!Objects.equals(loginRequest.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "password does not match");
        }

        CreateJwtRequest createJwtRequest = new CreateJwtRequest(
                user.getId(),
                jwtSecret
        );

        return jwtService.createJwt(createJwtRequest).getToken();
    }

    public Long getPayload(String token){
        String payload = jwtService.getJwtPayload(jwtSecret, token);
        return Long.parseLong(payload);
    }

    public UserDetailResponse getUser(Long id){
        try {
            User user = authenticationRepo.findById(id).orElse(null);
            if(user == null){
                throw new EntityNotFoundException("user not found for this id: "+id);
            }
            return new UserDetailResponse(user.getUsername());

        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}

