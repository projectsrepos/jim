/*
 */
package io.moquette.parser.proto.messages;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andrea
 */
public class SubscribeMessage extends MessageIDMessage {

    public static class Couple {

        public final byte qos;
        public final String topicFilter;

        public Couple(byte qos, String topic) {
            this.qos = qos;
            this.topicFilter = topic;
        }
        
    }
    private List<Couple> m_subscriptions = new ArrayList<>();

    public SubscribeMessage() {
        //Subscribe has always QoS 1
        m_messageType = AbstractMessage.SUBSCRIBE;
        m_qos = QOSType.LEAST_ONE;
    }
    
    public List<Couple> subscriptions() {
        return m_subscriptions;
    }

    public void addSubscription(Couple subscription) {
        m_subscriptions.add(subscription);
    }
}
