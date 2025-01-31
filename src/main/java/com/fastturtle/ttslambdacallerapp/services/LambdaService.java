package com.fastturtle.ttslambdacallerapp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastturtle.ttslambdacallerapp.models.Request;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;

@Service
public class LambdaService {

    private final LambdaClient lambdaClient;

    public LambdaService() {
        this.lambdaClient = LambdaClient.builder().build();
    }

    public String invokeLambdaForTTSUsingPolly(Request request) {
        String payload;

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            payload = objectMapper.writeValueAsString(request);

            InvokeRequest invokeRequest = InvokeRequest.builder()
                    .functionName("LambdaForTTSUsingPolly")
                    .payload(SdkBytes.fromUtf8String(payload))
                    .build();

            InvokeResponse response = lambdaClient.invoke(invokeRequest);

            return response.payload().asUtf8String();

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serialising request", e);
        }
    }
}
