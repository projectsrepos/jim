/*
 */
package io.moquette.broker.spi.impl;

import io.moquette.broker.interception.InterceptHandler;
import io.moquette.broker.interception.Interceptor;
import io.moquette.broker.interception.messages.InterceptAcknowledgedMessage;
import io.moquette.broker.interception.messages.InterceptConnectMessage;
import io.moquette.broker.interception.messages.InterceptDisconnectMessage;
import io.moquette.broker.interception.messages.InterceptPublishMessage;
import io.moquette.broker.interception.messages.InterceptSubscribeMessage;
import io.moquette.broker.interception.messages.InterceptUnsubscribeMessage;
import io.moquette.parser.proto.messages.ConnectMessage;
import io.moquette.broker.spi.impl.subscriptions.Subscription;
import io.moquette.parser.proto.messages.PublishMessage;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * An interceptor that execute the interception tasks asynchronously.
 *
 * @author Wagner Macedo
 */
final class BrokerInterceptor implements Interceptor {
    private final List<InterceptHandler> handlers;
    private final ExecutorService executor;

    BrokerInterceptor(List<InterceptHandler> handlers) {
        this.handlers = handlers;
        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    /**
     * Shutdown graciously the executor service
     */
    void stop() {
        executor.shutdown();
    }

    @Override
    public void notifyClientConnected(final ConnectMessage msg) {
        for (final InterceptHandler handler : this.handlers) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    handler.onConnect(new InterceptConnectMessage(msg));
                }
            });
        }
    }

    @Override
    public void notifyClientDisconnected(final String clientID, final String username ) {
        for (final InterceptHandler handler : this.handlers) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    handler.onDisconnect(new InterceptDisconnectMessage(clientID, username));
                }
            });
        }
    }

    @Override
    public void notifyTopicPublished(final PublishMessage msg, final String clientID, final String username) {
        for (final InterceptHandler handler : this.handlers) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    handler.onPublish(new InterceptPublishMessage(msg, clientID, username));
                }
            });
        }
    }

    @Override
    public void notifyTopicSubscribed(final Subscription sub, final String username) {
        for (final InterceptHandler handler : this.handlers) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    handler.onSubscribe(new InterceptSubscribeMessage(sub, username));
                }
            });
        }
    }

    @Override
    public void notifyTopicUnsubscribed(final String topic, final String clientID, final String username) {
        for (final InterceptHandler handler : this.handlers) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    handler.onUnsubscribe(new InterceptUnsubscribeMessage(topic, clientID, username));
                }
            });
        }
    }

	@Override
	public void notifyMessageAcknowledged( final InterceptAcknowledgedMessage msg ) {
        for (final InterceptHandler handler : this.handlers) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    handler.onMessageAcknowledged(msg);
                }
            });
        }
    }
}
