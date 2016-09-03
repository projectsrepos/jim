/*
 */
package io.moquette.broker.server;

import com.jim.im.broker.handler.LoginInterceptHandler;
import com.jim.im.broker.handler.PublishInterceptHandler;
import com.jim.im.broker.repo.BrokerMessageRepo;
import io.moquette.broker.BrokerConstants;
import io.moquette.broker.interception.InterceptHandler;
import io.moquette.broker.server.config.ClasspathConfig;
import io.moquette.broker.server.config.FilesystemConfig;
import io.moquette.broker.server.config.IConfig;
import io.moquette.broker.server.config.MemoryConfig;
import io.moquette.broker.server.netty.NettyAcceptor;
import io.moquette.broker.spi.impl.ProtocolProcessor;
import io.moquette.broker.spi.impl.SimpleMessaging;
import io.moquette.broker.spi.security.IAuthenticator;
import io.moquette.broker.spi.security.IAuthorizator;
import io.moquette.broker.spi.security.ISslContextCreator;
import io.moquette.parser.proto.messages.PublishMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

/**
 * Launch a  configured version of the server.
 * @author andrea
 */
public class Server {
    
    private static final Logger LOG = LoggerFactory.getLogger(Server.class);
    
    private ServerAcceptor m_acceptor;

    private volatile boolean m_initialized;

    private ProtocolProcessor m_processor;
    
    private LoginInterceptHandler loginInterceptHandler;
    private PublishInterceptHandler publishInterceptHandler;
    private BrokerMessageRepo brokerMessageRepo;
    private String brokerName;

    public Server setLoginInterceptHandler(LoginInterceptHandler loginInterceptHandler) {
        this.loginInterceptHandler = loginInterceptHandler;
        return this;
    }
    public Server setPublishInterceptHandler(PublishInterceptHandler publishInterceptHandler) {
        this.publishInterceptHandler = publishInterceptHandler;
        return this;
    }
    public Server setBrokerMessageRepo(BrokerMessageRepo brokerMessageRepo) {
        this.brokerMessageRepo = brokerMessageRepo;
        return this;
    }

    public String getBrokerName() {
        return this.brokerName;
    }

    public void init() throws IOException {
        brokerName = UUID.randomUUID().toString();
        publishInterceptHandler.setBrokerName(brokerName);
        startServer();
        //Bind  a shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
            	stopServer();
            }
        });
    }
    
    /**
     * Starts Moquette bringing the configuration from the file 
     * located at m_config/moquette.conf
     */
    public void startServer() throws IOException {
        //final IConfig config = new FilesystemConfig();
        final IConfig config = new ClasspathConfig();
        startServer(config);
    }

    /**
     * Starts Moquette bringing the configuration from the given file
     */
    public void startServer(File configFile) throws IOException {
        LOG.info("Using m_config file: " + configFile.getAbsolutePath());
        final IConfig config = new FilesystemConfig(configFile);
        startServer(config);
    }
    
    /**
     * Starts the server with the given properties.
     * 
     * Its suggested to at least have the following properties:
     * <ul>
     *  <li>port</li>
     *  <li>password_file</li>
     * </ul>
     */
    public void startServer(Properties configProps) throws IOException {
        final IConfig config = new MemoryConfig(configProps);
        startServer(config);
    }

    /**
     * Starts Moquette bringing the configuration files from the given Config implementation.
     */
    public void startServer(IConfig config) throws IOException {
    	List<InterceptHandler> handlers = new ArrayList<InterceptHandler>();
    	handlers.add(loginInterceptHandler);
        handlers.add(publishInterceptHandler);
        startServer(config, handlers);
    }

    /**
     * Starts Moquette with config provided by an implementation of IConfig class and with the
     * set of InterceptHandler.
     * */
    public void startServer(IConfig config, List<? extends InterceptHandler> handlers) throws IOException {
        startServer(config, handlers, null, null, null);
    }

    public void startServer(IConfig config, List<? extends InterceptHandler> handlers,
                            ISslContextCreator sslCtxCreator, IAuthenticator authenticator,
                            IAuthorizator authorizator) throws IOException {
        if (handlers == null) {
            handlers = Collections.emptyList();
        }

        final String handlerProp = System.getProperty("intercept.handler");
        if (handlerProp != null) {
            config.setProperty("intercept.handler", handlerProp);
        }
        LOG.info("Persistent store file: " + config.getProperty(BrokerConstants.PERSISTENT_STORE_PROPERTY_NAME));
        final ProtocolProcessor processor = SimpleMessaging.getInstance().init(config, handlers, authenticator, authorizator, brokerMessageRepo);

        if (sslCtxCreator == null) {
            sslCtxCreator = new DefaultMoquetteSslContextCreator(config);
        }

        m_acceptor = new NettyAcceptor();
        m_acceptor.initialize(processor, config, sslCtxCreator);
        m_processor = processor;
        m_initialized = true;
    }

    /**
     * Use the broker to publish a message. It's intended for embedding applications.
     * It can be used only after the server is correctly started with startServer.
     *
     * @param msg the message to forward.
     * @throws IllegalStateException if the server is not yet started
     * */
    public void internalPublish(PublishMessage msg) {
        if (!m_initialized) {
            throw new IllegalStateException("Can't publish on a server is not yet started");
        }
        m_processor.internalPublish(msg);
    }
    
    public void stopServer() {
    	LOG.info("Server stopping...");
        m_acceptor.close();
        SimpleMessaging.getInstance().shutdown();
        m_initialized = false;
        LOG.info("Server stopped");
    }
}
