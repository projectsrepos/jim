/*
 */
package com.jim.im.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.moquette.broker.server.netty.NettyMQTTHandler;
import io.moquette.parser.proto.Utils;
import io.moquette.parser.proto.messages.AbstractMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 *
 * @author andrea
 */
@ChannelHandler.Sharable
class ClientNettyMQTTHandler extends ChannelInboundHandlerAdapter {
    
    private static final Logger LOG = LoggerFactory.getLogger(NettyMQTTHandler.class);
    private Client m_client;
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object message) {
        AbstractMessage msg = (AbstractMessage) message;
        LOG.info("Received a message of type {}", Utils.msgType2String(msg.getMessageType()));
        m_client.messageReceived(msg);
    }
    
    @Override
    public void channelInactive(ChannelHandlerContext ctx)throws Exception {
        m_client.setConnectionLost(true);
        ctx.close(/*false*/);
    }

    void setClient(Client client) {
        m_client = client;
    }
    
}
