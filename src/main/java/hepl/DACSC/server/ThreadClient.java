package hepl.DACSC.server;

import hepl.DACSC.protocol.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

import hepl.DACSC.protocol.Reponse.IReponse;
import hepl.DACSC.protocol.Requetes.IRequete;
import hepl.DACSC.server.Logger;

public abstract class ThreadClient extends Thread {
    protected Socket socket;
    protected Protocole protocole;
    protected Logger logger;
    private int numero;

    private static int numCourant = 1;

    public ThreadClient(Socket socket, Protocole protocole, Logger logger) throws IOException {
        super("Client-" + numCourant);
        this.socket = socket;
        this.logger = logger;
        this.protocole = protocole;
        this.numero = numCourant++;
    }

    public ThreadClient(Protocole protocole, ThreadGroup groupe, Logger logger) throws IOException
    {
        super(groupe, "Client-" + numCourant + "NumCourant=" + numCourant);
        this.protocole = protocole;
        this.logger = logger;
        this.numero = numCourant++;
    }

    @Override
    public void run() {
        try
        {
            ObjectInputStream ois = null;
            ObjectOutputStream oos = null;

            try
            {
                ois = new ObjectInputStream(socket.getInputStream());
                oos = new ObjectOutputStream(socket.getOutputStream());

                while(true)
                {
                    IRequete requete = (IRequete) ois.readObject();
                    IReponse reponse = protocole.processRequest(requete, socket);
                    oos.writeObject(reponse);
                }
            }
            catch (IOException ex)
            {
                logger.Trace("Fin de connexion demandée par le client " + this.getName());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        catch (ClassNotFoundException ex) { logger.Trace("Erreur de classe");}
        finally {
            try {
                socket.close();
            } catch (IOException ex) {
                logger.Trace("Erreur de fermeture de socket");
            }
        }
    }


}