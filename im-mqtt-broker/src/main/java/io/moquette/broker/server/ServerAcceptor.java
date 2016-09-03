/*
 */
package io.moquette.broker.server;

import java.io.IOException;

import io.moquette.broker.server.config.IConfig;
import io.moquette.broker.spi.impl.ProtocolProcessor;
import io.moquette.broker.spi.security.ISslContextCreator;

/**
 *
 * @author andrea
 */
public interface ServerAcceptor {
    
    void initialize(ProtocolProcessor processor, IConfig props, ISslContextCreator sslCtxCreator) throws IOException;
    
    void close();
}
