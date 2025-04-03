package com.aisawan.rmq_consumer.model.process;

public class ReliableReceiver implements Receiver{

    private MessageLogger messageLogger = MessageLogger.getInstance();

    private String consumerName;

    public ReliableReceiver(String consumerName) { this.consumerName = consumerName; }

    @Override
    public void receiveMessage(String message) {
        messageLogger.log(String.format("[%s] - %s", consumerName, message));
    }
}
