package com.aisawan.rmq_consumer.model.process;

import com.sun.nio.sctp.IllegalReceiveException;

public class FaultyReceiver implements Receiver {

    private MessageLogger messageLogger = MessageLogger.getInstance();

    private String consumeName;


    public FaultyReceiver(String consumeName){
        this.consumeName = consumeName;
    }


    @Override
    public void receiveMessage(String message) {
        messageLogger.log(String.format("Error occurred!!!: [%s] - %s", consumeName, message ));
        throw new IllegalReceiveException(String.format("Receiver exception: cannot process the message - %s", message));
    }
}
