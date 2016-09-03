/*
 */
package io.moquette.broker;

import java.io.File;

/**
 * Created by andrea on 08/12/15.
 */
public class BrokerConstants {
    public static final String PERSISTENT_STORE_PROPERTY_NAME = "persistent_store";
    public static final String AUTOSAVE_INTERVAL_PROPERTY_NAME = "autosave_interval";
    public static final String PASSWORD_FILE_PROPERTY_NAME = "password_file";
    public static final String PORT_PROPERTY_NAME = "port";
    public static final String HOST_PROPERTY_NAME = "host";
    public static final String DEFAULT_MOQUETTE_STORE_MAP_DB_FILENAME = "moquette_store.mapdb";
    public static final String DEFAULT_PERSISTENT_PATH = System.getProperty("user.dir") + File.separator + DEFAULT_MOQUETTE_STORE_MAP_DB_FILENAME;
    public static final String WEB_SOCKET_PORT_PROPERTY_NAME = "websocket_port";
    public static final String WSS_PORT_PROPERTY_NAME = "secure_websocket_port";
    public static final String SSL_PORT_PROPERTY_NAME = "ssl_port";
    public static final String JKS_PATH_PROPERTY_NAME = "jks_path";
    public static final String KEY_STORE_PASSWORD_PROPERTY_NAME = "key_store_password";
    public static final String KEY_MANAGER_PASSWORD_PROPERTY_NAME = "key_manager_password";
    public static final String ALLOW_ANONYMOUS_PROPERTY_NAME = "allow_anonymous";
    public static final String ACL_FILE_PROPERTY_NAME = "acl_file";
    public static final String AUTHORIZATOR_CLASS_NAME = "authorizator_class";
    public static final String AUTHENTICATOR_CLASS_NAME = "authenticator_class";
    public static final int PORT = 1883;
    public static final int WEBSOCKET_PORT = 8080;
    public static final String HOST = "0.0.0.0";
    public static final String NEED_CLIENT_AUTH = "need_client_auth";
}
