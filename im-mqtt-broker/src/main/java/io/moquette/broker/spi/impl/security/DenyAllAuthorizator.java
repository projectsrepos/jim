/*
 */
package io.moquette.broker.spi.impl.security;

import io.moquette.broker.spi.security.IAuthorizator;

/**
 * @author andrea
 */
public class DenyAllAuthorizator implements IAuthorizator {
    @Override
    public boolean canWrite(String topic, String user, String client) {
        return true;
    }

    @Override
    public boolean canRead(String topic, String user, String client) {
        return true;
    }
}
