/*
 */
package com.jim.im.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.moquette.broker.BrokerConstants;
import io.moquette.parser.netty.MQTTDecoder;
import io.moquette.parser.netty.MQTTEncoder;
import io.moquette.parser.proto.messages.AbstractMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Class used just to send and receive MQTT messages without any protocol login in action, just use
 * the encoder/decoder part.
 * 
 * @author andrea
 */
public class Client {
    private static final Logger LOG = LoggerFactory.getLogger(Client.class);

    public static interface ICallback {
        void call(AbstractMessage msg);

    }

    final ClientNettyMQTTHandler handler = new ClientNettyMQTTHandler();
    EventLoopGroup workerGroup;
    Channel m_channel;
    private boolean m_connectionLost = false;

    public Client(String host) {
        this(host, BrokerConstants.PORT);
    }

    public Client(String host, int port) {
        handler.setClient(this);
        workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast("decoder", new MQTTDecoder());
                    pipeline.addLast("encoder", new MQTTEncoder());
                    pipeline.addLast("handler", handler);
                }
            });

            // Start the client.
            m_channel = b.connect(host, port).sync().channel();
        } catch (Exception ex) {
            LOG.error("Error received in client setup", ex);
            workerGroup.shutdownGracefully();
        }
    }

    ICallback callback;

    public void setCallback(ICallback callback) {
        this.callback = callback;
    }

    public void sendMessage(AbstractMessage msg) {
        m_channel.writeAndFlush(msg);
    }

    void messageReceived(AbstractMessage msg) {
        LOG.info("Received message " + msg);
        if (this.callback != null) {
            this.callback.call(msg);
        }
    }

    void setConnectionLost(boolean status) {
        m_connectionLost = status;
    }

    public boolean isConnectionLost() {
        return m_connectionLost;
    }

    public void close() throws InterruptedException {
        // Wait until the connection is closed.
        m_channel.closeFuture().sync();
        if (workerGroup == null) {
            throw new IllegalStateException("Invoked close on an Acceptor that wasn't initialized");
        }
        workerGroup.shutdownGracefully();
    }

    public static void main(String[] args) {
        Client client = new Client("127.0.0.1");
    }
}
