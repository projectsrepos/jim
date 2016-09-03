/*
 */
package io.moquette.broker.spi.security;

import javax.net.ssl.SSLContext;

/**
 * SSL certificate loader used to open SSL connections (websocket and MQTT-S).
 *
 * Created by andrea on 13/12/15.
 */
public interface ISslContextCreator {

    SSLContext initSSLContext();
}
