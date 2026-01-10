package hepl.DACSC.protocol;

import hepl.DACSC.protocol.Reponse.IReponse;
import hepl.DACSC.protocol.Requetes.IRequete;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public interface Protocole {
    String getNom();
    IReponse processRequest(IRequete requete, Socket socket) throws IOException, SQLException;
}