package com.muskan.authentication.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.muskan.authentication.dto.request.CreateJwtRequest;
import com.muskan.authentication.dto.response.CreateJwtResponse;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final ObjectMapper objectMapper;

    public CreateJwtResponse createJwt(CreateJwtRequest request) {
        try {
            String jsonPayload = objectMapper.writeValueAsString(request.getPayload());

            // JWT payload aka claim set, because jwt secures claim between parties
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .claim("data", jsonPayload)
                    .issueTime(new Date())
                    .expirationTime(new Date(System.currentTimeMillis() + 3600 * 1000)) // 1 hour expiry
                    .build();

            // Creating the headers for signing the claim
            SignedJWT signedJWT = new SignedJWT(
                    new JWSHeader(JWSAlgorithm.HS256),
                    claimsSet
            );

            // Signing
            signedJWT.sign(new MACSigner(request.getSecret().getBytes()));

            // Creating the response
            return new CreateJwtResponse(signedJWT.serialize());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to create jwt");
        }
    }
}
