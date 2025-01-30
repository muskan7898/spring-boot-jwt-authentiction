package com.muskan.authentication.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.muskan.authentication.dto.request.JsonifyRequest;
import com.muskan.authentication.service.JsonifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class JsonifyController {
    private final ObjectMapper objectMapper;
    private final JsonifyService jsonifyService;

    @PostMapping("/jsonify")
    public ResponseEntity<?> convertToJson(@RequestBody JsonifyRequest jsonifyRequest){
        try{
            return ResponseEntity.ok(jsonifyService.convertToJson(jsonifyRequest));
        }
        catch (ResponseStatusException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
