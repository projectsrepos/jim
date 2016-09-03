/*
 */
package io.moquette.broker.interception.messages;

import io.moquette.parser.proto.messages.AbstractMessage;

/**
 * @author Wagner Macedo
 */
public abstract class InterceptAbstractMessage {
    private final AbstractMessage msg;

    InterceptAbstractMessage(AbstractMessage msg) {
        this.msg = msg;
    }

    public boolean isRetainFlag() {
        return msg.isRetainFlag();
    }

    public boolean isDupFlag() {
        return msg.isDupFlag();
    }

    public AbstractMessage.QOSType getQos() {
        return msg.getQos();
    }
}
