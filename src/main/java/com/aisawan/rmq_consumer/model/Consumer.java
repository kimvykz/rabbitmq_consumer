package com.aisawan.rmq_consumer.model;

import com.aisawan.rmq_consumer.model.process.Receiver;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;


public class Consumer {

    private final ConnectionFactory connectionFactory;

    private String routingKey;
    private String queueName;
    private String name;




    public Consumer (String consumerName, String routingKey, String queueName, ConnectionFactory connectionFactory, Receiver receiver) {
        this.name = consumerName;
        this.routingKey = routingKey;
        this.queueName = queueName;
        this.connectionFactory = connectionFactory;

        initContainer(receiver);
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public String getQueueName() {
        return queueName;
    }

    public String getName() {
        return name;
    }

    private void initContainer(Receiver receiver) {
        // set up the queue, to the broker, bind it to exchange with routing key
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        Queue queue = new Queue(queueName);
        admin.declareQueue(queue);
        TopicExchange exchange = new TopicExchange("data-distribution");
        admin.declareExchange(exchange);
        admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(routingKey));

        // set up the listener and container
        SimpleMessageListenerContainer container =
                new SimpleMessageListenerContainer(connectionFactory);

        MessageListenerAdapter adapter = new MessageListenerAdapter(receiver, "receiveMessage");
        container.setMessageListener(adapter);
        container.setQueueNames(queueName);
        container.start();
    }
}
