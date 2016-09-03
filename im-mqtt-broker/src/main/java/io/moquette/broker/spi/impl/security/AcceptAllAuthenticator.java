/*
 */

package io.moquette.broker.spi.impl.security;

import io.moquette.broker.spi.security.IAuthenticator;

/**
 * Created by andrea on 8/23/14.
 */
public class AcceptAllAuthenticator implements IAuthenticator {
    public boolean checkValid(String username, byte[] password) {
        return true;
    }
}
