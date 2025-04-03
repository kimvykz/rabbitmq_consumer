package com.aisawan.rmq_consumer.service;

import com.aisawan.rmq_consumer.component.ConsumerBuilder;
import com.aisawan.rmq_consumer.model.Consumer;
import com.aisawan.rmq_consumer.model.process.MessageLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConsumerService {

    @Autowired
    private ConsumerBuilder consumerBuilder;

    private MessageLogger messageLogger = MessageLogger.getInstance();
    private Map<String, Consumer> consumerPool = new HashMap<>();

    public List<String> getLoggedMessages(){
        return messageLogger.getLoggedMessages();
    }

    public String register(String consumerName,
                           String queueName,
                           String routingKey,
                           boolean faultyConsumer,
                           int runtime) {

        Consumer consumer =
                consumerBuilder
                    .withFaultyReceiver(faultyConsumer)
                    .withRuntime(runtime)
                    .build(consumerName, routingKey, queueName);

        consumerPool.put(consumerName, consumer);
        messageLogger.log(String.format("Registered new consumer: %s", consumerName));

        return String.format("Registered new consumer: %s", consumerName);
    }


}
