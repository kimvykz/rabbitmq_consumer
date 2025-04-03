package com.aisawan.rmq_consumer.model.process;

public interface Receiver {
    public void receiveMessage(String message) throws Exception;
}
