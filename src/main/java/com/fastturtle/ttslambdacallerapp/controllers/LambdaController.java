package com.fastturtle.ttslambdacallerapp.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastturtle.ttslambdacallerapp.models.Request;
import com.fastturtle.ttslambdacallerapp.models.Response;
import com.fastturtle.ttslambdacallerapp.services.LambdaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LambdaController {

    private final LambdaService lambdaService;

    public LambdaController(LambdaService lambdaService) {
        this.lambdaService = lambdaService;
    }

    @PostMapping("/callTextToAudioLambda")
    public Response callLambda(@RequestBody Request request) {  // Added @RequestBody, which resolved error of empty request body
        String lambdaResponse = lambdaService.invokeLambdaForTTSUsingPolly(request);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Response response = objectMapper.readValue(lambdaResponse, Response.class);
            return response;
        } catch(JsonProcessingException jex) {
            throw new RuntimeException("Error processing JSON ", jex);
        }

    }
}
