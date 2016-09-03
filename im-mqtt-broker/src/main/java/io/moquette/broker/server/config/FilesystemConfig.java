/*
 */
package io.moquette.broker.server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.ParseException;
import java.util.Properties;

/**
 * Configuration that loads file from the file system
 *
 * @author andrea
 */
public class FilesystemConfig extends IConfig {
    private static final Logger LOG = LoggerFactory.getLogger(FilesystemConfig.class);

    private Properties m_properties = new Properties();

    public FilesystemConfig(File file) {
        ConfigurationParser confParser = new ConfigurationParser();
        assignDefaults();
        try {
            confParser.parse(file);
        } catch (ParseException pex) {
            LOG.warn("An error occurred in parsing configuration, fallback on default configuration", pex);
        }
        m_properties = confParser.getProperties();
    }

    public FilesystemConfig() {
        this(defaultConfigFile());
    }

    private static File defaultConfigFile() {
        String configPath = System.getProperty("moquette.path", null);
        return new File(configPath, "config/moquette.conf");
    }

    @Override
    public void setProperty(String name, String value) {
        m_properties.setProperty(name, value);
    }

    @Override
    public String getProperty(String name) {
        return m_properties.getProperty(name);
    }

    @Override
    public String getProperty(String name, String defaultValue) {
        return m_properties.getProperty(name, defaultValue);
    }
}
