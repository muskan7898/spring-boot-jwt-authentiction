package com.muskan.authentication.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class ConceptController {
    private final ObjectMapper objectMapper;

    @PostMapping("/jsonify")
    public ResponseEntity<?> convertToJson(@RequestBody String body){
        try{
            Object json = objectMapper.readTree(body);
            String jsonString = objectMapper.writeValueAsString(json);
            return ResponseEntity.ok(jsonString);
        }
        catch (JsonProcessingException e){
            return ResponseEntity.badRequest().body("invalid json format");
        }
    }
}
