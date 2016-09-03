/*
 */
package io.moquette.broker.spi.impl.security;

import static io.moquette.broker.spi.impl.security.Authorization.Permission.READWRITE;

/**
 * Carries the read/write authorization to topics for the users.
 *
 * @author andrea
 */
public class Authorization {
    protected final String topic;
    protected final Permission permission;

    /**
     * Access rights
     * */
    enum Permission {
        READ, WRITE, READWRITE
    }

    Authorization(String topic) {
        this(topic, Permission.READWRITE);
    }

    Authorization(String topic, Permission permission) {
        this.topic = topic;
        this.permission = permission;
    }

    public boolean grant(Permission desiredPermission) {
        return permission == desiredPermission || permission == READWRITE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Authorization that = (Authorization) o;

        if (permission != that.permission) return false;
        if (!topic.equals(that.topic)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = topic.hashCode();
        result = 31 * result + permission.hashCode();
        return result;
    }
}
