/*
 */

package io.moquette.broker.spi.persistence;

import static io.moquette.broker.BrokerConstants.AUTOSAVE_INTERVAL_PROPERTY_NAME;
import static io.moquette.broker.BrokerConstants.PERSISTENT_STORE_PROPERTY_NAME;
import io.moquette.broker.server.config.IConfig;
import io.moquette.broker.spi.IMessagesStore;
import io.moquette.broker.spi.ISessionsStore;
import io.moquette.parser.proto.MQTTException;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * MapDB main persistence implementation
 */
public class MapDBPersistentStore {

    /**
     * This is a DTO used to persist minimal status (clean session and activation status) of
     * a session.
     * */
    public static class PersistentSession implements Serializable {
        public final boolean cleanSession;

        public PersistentSession(boolean cleanSession) {
            this.cleanSession = cleanSession;
        }
    }

    private static final Logger LOG = LoggerFactory.getLogger(MapDBPersistentStore.class);

    private DB m_db;
    private final String m_storePath;
    private final int m_autosaveInterval; // in seconds

    protected final ScheduledExecutorService m_scheduler = Executors.newScheduledThreadPool(1);

    public MapDBPersistentStore(IConfig props) {
        this.m_storePath = props.getProperty(PERSISTENT_STORE_PROPERTY_NAME, "");
        this.m_autosaveInterval = Integer.parseInt(props.getProperty(AUTOSAVE_INTERVAL_PROPERTY_NAME, "30"));
    }

    /**
     * Factory method to create message store backed by MapDB
     * */
    public IMessagesStore messagesStore() {
        //TODO check m_db is valid and
        IMessagesStore msgStore = new MapDBMessagesStore(m_db);
        msgStore.initStore();
        return msgStore;
    }

    public ISessionsStore sessionsStore(IMessagesStore msgStore) {
        ISessionsStore sessionsStore = new MapDBSessionsStore(m_db, msgStore);
        sessionsStore.initStore();
        return sessionsStore;
    }
    
    public void initStore() {
        if (m_storePath == null || m_storePath.isEmpty()) {
            m_db = DBMaker.newMemoryDB().make();
        } else {
            File tmpFile;
            try {
                tmpFile = new File(m_storePath);
                boolean fileNewlyCreated = tmpFile.createNewFile();
                LOG.info("Starting with {} [{}] db file", fileNewlyCreated ? "fresh" : "existing", m_storePath);
            } catch (IOException ex) {
                LOG.error(null, ex);
                throw new MQTTException("Can't create temp file for subscriptions storage [" + m_storePath + "]", ex);
            }
            m_db = DBMaker.newFileDB(tmpFile).make();
        }
        m_scheduler.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                m_db.commit();
            }
        }, this.m_autosaveInterval, this.m_autosaveInterval, TimeUnit.SECONDS);
    }

    public void close() {
        if (this.m_db.isClosed()) {
            LOG.debug("already closed");
            return;
        }
        this.m_db.commit();
        //LOG.debug("persisted subscriptions {}", m_persistentSubscriptions);
        this.m_db.close();
        LOG.debug("closed disk storage");
        this.m_scheduler.shutdown();
        LOG.debug("Persistence commit scheduler is shutdown");
    }
}
