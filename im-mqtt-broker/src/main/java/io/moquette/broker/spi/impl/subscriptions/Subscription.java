/*
 */
package io.moquette.broker.spi.impl.subscriptions;

import java.io.Serializable;
import io.moquette.broker.spi.ISessionsStore;
import io.moquette.parser.proto.messages.AbstractMessage.QOSType;

/**
 * Maintain the information about which Topic a certain ClientID is subscribed 
 * and at which QoS
 * 
 * 
 * @author andrea
 */
public class Subscription implements Serializable {
    
    QOSType requestedQos; //max QoS acceptable
    String clientId;
    String topicFilter;
    boolean active = true;
    
    public Subscription(String clientId, String topicFilter, QOSType requestedQos) {
        this.requestedQos = requestedQos;
        this.clientId = clientId;
        this.topicFilter = topicFilter;
    }

    public Subscription(Subscription orig) {
        this.requestedQos = orig.requestedQos;
        this.clientId = orig.clientId;
        this.topicFilter = orig.topicFilter;
        this.active = orig.active;
    }

    public String getClientId() {
        return clientId;
    }

    public QOSType getRequestedQos() {
        return requestedQos;
    }

    public String getTopicFilter() {
        return topicFilter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subscription that = (Subscription) o;

        if (clientId != null ? !clientId.equals(that.clientId) : that.clientId != null) return false;
        return !(topicFilter != null ? !topicFilter.equals(that.topicFilter) : that.topicFilter != null);

    }

    @Override
    public int hashCode() {
        int result = clientId != null ? clientId.hashCode() : 0;
        result = 31 * result + (topicFilter != null ? topicFilter.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("[filter:%s, cliID: %s, qos: %s, active: %s]", this.topicFilter, this.clientId, this.requestedQos, this.active);
    }

    @Override
    public Subscription clone() {
        try {
            return (Subscription) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public ISessionsStore.ClientTopicCouple asClientTopicCouple() {
        return new ISessionsStore.ClientTopicCouple(this.clientId, this.topicFilter);
    }
}
