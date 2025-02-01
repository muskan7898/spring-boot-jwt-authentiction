package com.muskan.authentication.controllers;

import com.muskan.authentication.dto.request.CreateJwtRequest;
import com.muskan.authentication.dto.response.CreateJwtResponse;
import com.muskan.authentication.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/jwt")
@RequiredArgsConstructor
public class JwtController {
    private final JwtService jwtService;

    @PostMapping()
    ResponseEntity<CreateJwtResponse> createJwt(@RequestBody CreateJwtRequest createJwtRequest) {
        return ResponseEntity.ok(jwtService.createJwt(createJwtRequest));
    }

    @ExceptionHandler(ResponseStatusException.class)
    ResponseEntity<String> handleException(ResponseStatusException exception) {
        return ResponseEntity.status(exception.getStatusCode()).body(exception.getMessage());
    }
}
