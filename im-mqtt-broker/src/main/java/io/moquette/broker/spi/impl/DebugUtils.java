/*
 */
package io.moquette.broker.spi.impl;

import java.nio.ByteBuffer;

/**
 *
 * @author andrea
 */
class DebugUtils {
    static String  payload2Str(ByteBuffer content) {
        byte[] b = new byte[content.remaining()];
        content.mark();
        content.get(b);
        content.reset();
        return new String(b);
    } 
}
