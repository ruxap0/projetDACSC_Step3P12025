package hepl.DACSC.server;

import hepl.DACSC.protocol.Protocole;

import java.io.IOException;
import java.net.Socket;

public class ThreadClientDemande extends ThreadClient {
    public ThreadClientDemande(Socket socket, Protocole protocole, Logger logger) throws IOException {
        super(socket, protocole, logger);
    }

    @Override
    public void run() {
        logger.Trace("Démarrage du thread client demande " + this.getName() + " pour le client " + socket.getInetAddress().getHostAddress());
        super.run();
        logger.Trace("Fin du thread client demande " + this.getName());
    }
}
