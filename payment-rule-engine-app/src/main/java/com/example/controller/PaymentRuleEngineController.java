package com.example.controller;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.example.handlers.PaymentRuleEngineHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/evaluatePaymentRules")
public class PaymentRuleEngineController {

    private final PaymentRuleEngineHandler paymentRuleEngineHandler;

    public PaymentRuleEngineController(@Value("${dynamo.db.url}") String dynamoDbUrl) {
        // Local dynamo DB instance creation
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(dynamoDbUrl, "us-west-2"))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("dummyAccessKey", "dummySecretKey")))
                .build();
        this.paymentRuleEngineHandler = new PaymentRuleEngineHandler(new DynamoDB(client));

    }

    @PostMapping
    public Map<String, Object> evaluatePaymentRules(@RequestBody Map<String, Object> input) {
        return (paymentRuleEngineHandler.handleRequest(input, null));
    }
}
