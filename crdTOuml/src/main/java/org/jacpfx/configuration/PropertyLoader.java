package org.jacpfx.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Logger;

public class PropertyLoader {
    private static final Properties properties;
    private final static Logger LOGGER = Logger.getLogger(PropertyLoader.class.getName());

    static {
        properties = new Properties();
        try (InputStream input = PropertyLoader.class.getClassLoader().getResourceAsStream(Optional.ofNullable(System.getProperty("config")).orElse("config.properties"))) {
            if (input == null) {
                LOGGER.info("Sorry, unable to find properties");

            } else {
                //load a properties file from class path, inside static method
                properties.load(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.info("failed to load properties" + e.getMessage());
        }
    }

    public static Properties getProperties() {
        return properties;
    }

    public static String getProperty(String name) {
        return Optional.ofNullable(System.getProperty(name)).orElse(getProperties().getProperty(name));
    }
}
