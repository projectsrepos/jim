/*
 */
package io.moquette.broker.interception;

import io.moquette.broker.interception.messages.InterceptAcknowledgedMessage;
import io.moquette.parser.proto.messages.ConnectMessage;
import io.moquette.parser.proto.messages.PublishMessage;
import io.moquette.broker.spi.impl.subscriptions.Subscription;

/**
 * This interface is to be used internally by the broker components.
 * <p>
 * An interface is used instead of a class to allow more flexibility in changing
 * an implementation.
 * <p>
 * Interceptor implementations forward notifications to a <code>InterceptHandler</code>,
 * that is normally a field. So, the implementations should act as a proxy to a custom
 * intercept handler.
 *
 * @see InterceptHandler
 * @author Wagner Macedo
 */
public interface Interceptor {

    void notifyClientConnected(ConnectMessage msg);

    void notifyClientDisconnected(String clientID, String username);

    void notifyTopicPublished(PublishMessage msg, String clientID, final String username);

    void notifyTopicSubscribed(Subscription sub, final String username);

    void notifyTopicUnsubscribed(String topic, String clientID, final String username);

    void notifyMessageAcknowledged(InterceptAcknowledgedMessage msg);
}
