package hepl.DACSC;

import hepl.DACSC.model.DAO.DBConnexion;
import hepl.DACSC.protocol.MRPS;
import hepl.DACSC.server.ConfigServer;
import hepl.DACSC.server.Logger;
import hepl.DACSC.server.ThreadSeveurDemande;

import java.io.IOException;
import java.security.Security;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        DBConnexion con;
        ConfigServer configServer = new ConfigServer();
        new DBConnexion(
                configServer.getDbLink(),
                configServer.getDbUser(),
                configServer.getDbPassword(),
                configServer.getDbDriver()
        );
        MRPS protocol = new MRPS(null);

        Logger logger = new Logger() {
            @Override
            public void Trace(String message) {
                System.out.println(message);
            }
        };

        ThreadSeveurDemande server = new ThreadSeveurDemande(
                configServer.getPortConsultation(),
                protocol,
                logger
        );

        server.run();
        DBConnexion.closeConnection();
    }
}