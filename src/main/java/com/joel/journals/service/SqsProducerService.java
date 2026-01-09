package com.joel.journals.service.sqs;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Service
@RequiredArgsConstructor
public class SqsProducerService {

    private final SqsClient sqsClient;

    @Value("${aws.sqs.input-queue-url}")
    private String inputQueueUrl;

    public void sendMessage(String message) {
        SendMessageRequest request = SendMessageRequest.builder()
                .queueUrl(inputQueueUrl)
                .messageBody(message)
                .build();

        sqsClient.sendMessage(request);
    }
}
