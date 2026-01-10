package hepl.DACSC.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigServer {
    private Properties properties;

    public ConfigServer() throws IOException {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("serverconfig.properties")) {
            if (input == null) {
                throw new IOException("Fichier config.properties introuvable");
            }
            properties.load(input);
        }
    }

    public int getPortConsultation() {
        return Integer.parseInt(properties.getProperty("PORT_CONSULTATION"));
    }

    public String getDbUser() {
        return properties.getProperty("DB_USR");
    }

    public String getDbPassword() {
        return properties.getProperty("DB_PWD");
    }

    public String getDbLink() {
        return properties.getProperty("LINK_DB");
    }

    public String getDbDriver() {
        return properties.getProperty("DB_DRIVER");
    }
}