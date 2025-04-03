package com.aisawan.rmq_consumer.controller;

import com.aisawan.rmq_consumer.service.ConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consumer")
@Slf4j
@RequiredArgsConstructor
public class ConsumerController {
    @Autowired
    private ConsumerService consumerService;

    @GetMapping()
    public List<String> getLoggedMessages() {
        return consumerService.getLoggedMessages();
    }

    @PostMapping("/register/{consumerName}/{queueName}/{routingKey}")
    public String registerListener(
            @PathVariable String consumerName,
            @PathVariable String queueName,
            @PathVariable String routingKey,
            @RequestParam(value = "faultyConsumer", required = false, defaultValue = "false") boolean faultyConsumer,
            @RequestParam(value = "runtime", required = false, defaultValue = "0") int runtime
            ) {

        return consumerService.register(
                consumerName,
                queueName,
                routingKey,
                faultyConsumer,
                runtime
        );
    }


}
