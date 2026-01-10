package hepl.DACSC.server;

import hepl.DACSC.protocol.MRPS;
import hepl.DACSC.protocol.Protocole;

import java.io.IOException;
import java.net.Socket;

public class ThreadSeveurDemande extends ThreadServer {
    public ThreadSeveurDemande(int port, Protocole protocole, Logger logger) throws IOException {
        super(port, protocole, logger);
    }

    @Override
    public void run() {
        logger.Trace("Démarrage du serveur de demande sur le port " + port + " avec le protocole " + protocole.getNom());
        while (!this.isInterrupted()) {
            Socket csocket;
            try {
                serverSocket.setSoTimeout(2000);
                csocket = serverSocket.accept();
                logger.Trace("Connexion acceptée de " + csocket.getInetAddress().getHostAddress());

                // Traiter la connexion dans un nouveau thread ou via un pool de threads
                ThreadClientDemande clientHandler = new ThreadClientDemande(csocket, protocole, logger);
                clientHandler.start();
            } catch (java.io.IOException e) {
                logger.Trace("Erreur lors de l'acceptation de la connexion: " + e.getMessage());
            }
        }
    }
}
