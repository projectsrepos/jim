/*
 */
package io.moquette.broker.spi.security;

/**
 * ACL checker.
 *
 * Create an authorizator that matches topic names with same grammar of subscriptions.
 * The # is always a terminator and its the multilevel matcher.
 * The + sign is the single level matcher.
 *
 * @author andrea
 */
public interface IAuthorizator {

    /**
     * Ask the implementation of the authorizator if the topic can be used in a publish.
     * */
    boolean canWrite(String topic, String user, String client);

    boolean canRead(String topic, String user, String client);
}
