package com.joel.journals.service.sqs;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SqsConsumerService {

    private final SqsClient sqsClient;

    @Value("${aws.sqs.input-queue-url}")
    private String inputQueueUrl;

    @Value("${aws.sqs.output-queue-url}")
    private String outputQueueUrl;

    @PostConstruct
    public void startPolling() {
        log.info("Starting SqsConsumerService");
        new Thread(this::pollMessages).start();
    }

    private void pollMessages() {
        while (true) {
            ReceiveMessageRequest receiveRequest = ReceiveMessageRequest.builder()
                    .queueUrl(inputQueueUrl)
                    .maxNumberOfMessages(5)
                    .waitTimeSeconds(20) // long polling
                    .build();

            List<Message> messages = sqsClient.receiveMessage(receiveRequest).messages();

            for (Message message : messages) {
                log.info("Received message: {}", message.body());

                // Push to Queue B
                sqsClient.sendMessage(SendMessageRequest.builder()
                        .queueUrl(outputQueueUrl)
                        .messageBody("PROCESSED: " + message.body())
                        .build());

                // Delete from Queue A
                sqsClient.deleteMessage(DeleteMessageRequest.builder()
                        .queueUrl(inputQueueUrl)
                        .receiptHandle(message.receiptHandle())
                        .build());
            }
        }
    }
}
