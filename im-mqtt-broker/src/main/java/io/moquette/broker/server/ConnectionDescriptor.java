/*
 */
package io.moquette.broker.server;

import io.netty.channel.Channel;

/**
 * Value object to maintain the information of single connection, like ClientID, IoSession,
 * and other clean session fla.
 * 
 * 
 * @author andrea
 */
public class ConnectionDescriptor {
    
    public final String clientID;
    public final Channel channel;
    public final boolean cleanSession;
    
    public ConnectionDescriptor(String clientID, Channel session, boolean cleanSession) {
        this.clientID = clientID;
        this.channel = session;
        this.cleanSession = cleanSession;
    }

    @Override
    public String toString() {
        return "ConnectionDescriptor{" + "clientID=" + clientID + ", cleanSession=" + cleanSession + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConnectionDescriptor that = (ConnectionDescriptor) o;

        if (clientID != null ? !clientID.equals(that.clientID) : that.clientID != null) return false;
        return !(channel != null ? !channel.equals(that.channel) : that.channel != null);

    }

    @Override
    public int hashCode() {
        int result = clientID != null ? clientID.hashCode() : 0;
        result = 31 * result + (channel != null ? channel.hashCode() : 0);
        return result;
    }
}
