/*
 */
package io.moquette.broker.interception;

import io.moquette.broker.interception.messages.InterceptAcknowledgedMessage;
import io.moquette.broker.interception.messages.InterceptConnectMessage;
import io.moquette.broker.interception.messages.InterceptDisconnectMessage;
import io.moquette.broker.interception.messages.InterceptPublishMessage;
import io.moquette.broker.interception.messages.InterceptSubscribeMessage;
import io.moquette.broker.interception.messages.InterceptUnsubscribeMessage;

/**
 * Basic abstract class usefull to avoid empty methods creation in subclasses.
 *
 * Created by andrea on 08/12/15.
 */
public abstract class AbstractInterceptHandler implements InterceptHandler {

    @Override
    public void onConnect(InterceptConnectMessage msg) {}

    @Override
    public void onDisconnect(InterceptDisconnectMessage msg) {}

    @Override
    public void onPublish(InterceptPublishMessage msg) {}

    @Override
    public void onSubscribe(InterceptSubscribeMessage msg) {}

    @Override
    public void onUnsubscribe(InterceptUnsubscribeMessage msg) {}
    
    @Override
	public void onMessageAcknowledged(InterceptAcknowledgedMessage msg) {}
}
