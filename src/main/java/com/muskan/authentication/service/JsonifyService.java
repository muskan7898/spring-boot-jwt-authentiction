package com.muskan.authentication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.muskan.authentication.dto.request.JsonifyRequest;
import com.muskan.authentication.dto.response.JsonifyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class JsonifyService {
    private final ObjectMapper objectMapper;

    public JsonifyResponse convertToJson(JsonifyRequest jsonifyRequest){
        try{
            Object json = objectMapper.readTree(jsonifyRequest.getPayload().toString());
            String jsonstring = objectMapper.writeValueAsString(json);
            return  new JsonifyResponse(jsonstring);
        }
        catch (JsonProcessingException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid json");
        }
    }
}
