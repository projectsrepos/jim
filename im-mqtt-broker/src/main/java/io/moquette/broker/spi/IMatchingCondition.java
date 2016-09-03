/*
 */
package io.moquette.broker.spi;

/**
 */
public interface IMatchingCondition {
    boolean match(String key);
}

