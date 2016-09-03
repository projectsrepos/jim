/*
 */
package io.moquette.broker.interception;

import io.moquette.broker.interception.messages.InterceptAcknowledgedMessage;
import io.moquette.parser.proto.messages.AbstractMessage;
import io.moquette.broker.spi.impl.subscriptions.Subscription;
import io.moquette.broker.interception.messages.InterceptConnectMessage;
import io.moquette.broker.interception.messages.InterceptDisconnectMessage;
import io.moquette.broker.interception.messages.InterceptPublishMessage;
import io.moquette.broker.interception.messages.InterceptSubscribeMessage;
import io.moquette.broker.interception.messages.InterceptUnsubscribeMessage;

/**
 * This interface is used to inject code for intercepting broker events.
 * <p>
 * The events can act only as observers.
 * <p>
 * Almost every method receives a subclass of {@link AbstractMessage}, except
 * <code>onDisconnect</code> that receives the client id string and
 * <code>onSubscribe</code> and <code>onUnsubscribe</code> that receive a
 * {@link Subscription} object.
 *
 * @author Wagner Macedo
 */
public interface InterceptHandler {

    void onConnect(InterceptConnectMessage msg);

    void onDisconnect(InterceptDisconnectMessage msg);

    void onPublish(InterceptPublishMessage msg);

    void onSubscribe(InterceptSubscribeMessage msg);

    void onUnsubscribe(InterceptUnsubscribeMessage msg);
    
    void onMessageAcknowledged(InterceptAcknowledgedMessage msg);
}
