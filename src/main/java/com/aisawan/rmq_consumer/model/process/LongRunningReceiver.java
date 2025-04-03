package com.aisawan.rmq_consumer.model.process;

public class LongRunningReceiver implements Receiver{

    private MessageLogger messageLogger = MessageLogger.getInstance();

    private String consumerName;
    private int runtime;

    public LongRunningReceiver(String consumerName, int runtime) {
        this.consumerName = consumerName;
        this.runtime = runtime;
    }

    @Override
    public void receiveMessage(String message) throws Exception {
        messageLogger.log(String.format("[%s] running for %d seconds...", consumerName, runtime));
        Thread.sleep(runtime * 1000);
        messageLogger.log(String.format("[%s] - %s", consumerName, message));
    }
}
