/*
 */
package io.moquette.broker.spi.security;

/**
 * username and password checker
 * 
 * @author andrea
 */
public interface IAuthenticator {

    boolean checkValid(String username, byte[] password);
}
