package hepl.DACSC.protocol;

import hepl.DACSC.MyCrypto.CryptoUtils;
import hepl.DACSC.model.DAO.DBConnexion;
import hepl.DACSC.model.DAO.DoctorDAO;
import hepl.DACSC.protocol.Reponse.*;
import hepl.DACSC.protocol.Requetes.IRequete;
import hepl.DACSC.protocol.Requetes.RequeteLoginAuth;
import hepl.DACSC.protocol.Requetes.RequeteLoginFirst;
import hepl.DACSC.protocol.Requetes.RequeteLoginSession;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.Socket;
import java.security.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MRPS implements Protocole {
    private Map<String, byte[]> sessionSalts = new HashMap<>();
    private Map<String, SecretKey> sessionKeys = new HashMap<>();
    private Map<Socket, String> socketToLogin = new HashMap<>();

    private PrivateKey serverPrivateKey;
    private PublicKey serverPublicKey;

    private DBConnexion conn;

    private DoctorDAO doctorDAO;

    public MRPS(DBConnexion dbConnection) throws Exception {
        this.conn = dbConnection;
        this.doctorDAO = new DoctorDAO(conn);
        loadServerKeys();
    }

    private void loadServerKeys() throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream("CryptoCles/clePriveeServeur.ser");
        if (is == null) {
            throw new FileNotFoundException("clePriveeServeur.ser not found in resources");
        }
        ObjectInputStream ois = new ObjectInputStream(is);
        serverPrivateKey = (PrivateKey) ois.readObject();
        ois.close();

        is = getClass().getClassLoader().getResourceAsStream("CryptoCles/clePubliqueServeur.ser");
        if (is == null) {
            throw new FileNotFoundException("clePubliqueServeur.ser not found in resources");
        }
        ois = new ObjectInputStream(is);
        serverPublicKey = (PublicKey) ois.readObject();
        ois.close();
    }

    @Override
    public String getNom() {
        return "MRPS Protocol";
    }

    @Override
    public IReponse processRequest(IRequete requete, Socket socket) throws IOException, SQLException {

        if(requete instanceof RequeteLoginFirst)
        {
            ReponseLoginFirst rep =  handleLoginFirst((RequeteLoginFirst) requete);
            if(rep.isSucces())
            {
                socketToLogin.put(socket, ((RequeteLoginFirst) requete).getLogin());
                sessionSalts.put(((RequeteLoginFirst) requete).getLogin(), rep.getSalt());
            }
            return rep;
        }
        else if (requete instanceof RequeteLoginAuth) {
            try {
                return handleLoginAuth((RequeteLoginAuth) requete, socket);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else if (requete instanceof RequeteLoginSession) {
            try {
                return handleLoginSession((RequeteLoginSession) requete, socket);
            } catch (Exception e) {
                return new ReponseLoginSession(false, "Erreur: " + e.getMessage());
            }
        }


        return new ReponseErreur();
    }


    public ReponseLoginFirst handleLoginFirst(RequeteLoginFirst requete) throws SQLException {
        if(doctorDAO.isDoctorPresent(requete.getLogin())){
            byte[] salt = CryptoUtils.generateSalt();

            return new ReponseLoginFirst(true, salt, "OK");
        }
        return new ReponseLoginFirst(false, null, "ERROR");
    }

    public ReponseLoginAuth handleLoginAuth(RequeteLoginAuth requete, Socket socket) throws Exception {
        try{
            PublicKey clientKey = getClientPublicKey();
            if(!CryptoUtils.verifySignature(requete.getDigest(), requete.getSignature(), clientKey))
            {
                return new ReponseLoginAuth(false, "ERROR => Signature changed");
            }

            String login = socketToLogin.get(socket);

            if (login == null) {
                return new ReponseLoginAuth(false, "Session expirée ou login introuvable");
            }

            String password = doctorDAO.getPasswordByLogin(login);
            if (password == null) {
                sessionSalts.remove(login);
                socketToLogin.remove(socket);
                return new ReponseLoginAuth(false, "Erreur d'authentification");
            }

            byte[] salt = sessionSalts.get(login);
            if (salt == null) {
                socketToLogin.remove(socket);
                return new ReponseLoginAuth(false, "Session expirée");
            }

            byte[] expectedDigest = CryptoUtils.generateDigest(login, password, salt);

            if(!Arrays.equals(expectedDigest, requete.getDigest()))
            {
                socketToLogin.remove(socket);
                sessionSalts.remove(login);
                return new ReponseLoginAuth(false,"Mot de passe Incorrect");
            }

            return new ReponseLoginAuth(true, "OK");
        }
        catch (Exception e){
            throw new IOException(e);
        }
    }

    private IReponse handleLoginSession(RequeteLoginSession requete, Socket socket) throws IOException, ClassNotFoundException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, NoSuchProviderException {
        String login = socketToLogin.get(socket);
        if (login == null) {
            return new ReponseLoginSession(false, "Session expirée - Aucun login associé");
        }

        byte[] clientSessionKey = CryptoUtils.DecryptAsymRSA(serverPrivateKey, requete.getCryptedSessionKey());
        SecretKey sessionKey = new SecretKeySpec(clientSessionKey, "DES");

        return new ReponseLoginSession(true, "OK");
    }

    private byte[] calculateDigest(String login, String password, byte[] sel)
            throws NoSuchAlgorithmException, NoSuchProviderException, IOException {

        // Concaténer login + password + sel
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeUTF(login);
        dos.writeUTF(password);
        dos.write(sel);

        byte[] dataToHash = baos.toByteArray();

        // Calculer le digest avec SHA-256
        MessageDigest md = MessageDigest.getInstance("SHA-256", "BC");
        byte[] digest = md.digest(dataToHash);

        return digest;
    }

    public PublicKey getClientPublicKey() throws IOException, ClassNotFoundException {
        InputStream is = getClass().getClassLoader().getResourceAsStream("CryptoCles/clePubliqueClient.ser");
        if (is == null) {
            throw new FileNotFoundException("clePubliqueServeur.ser not found in resources");
        }
        ObjectInputStream ois = new ObjectInputStream(is);
        PublicKey cle = (PublicKey) ois.readObject();
        ois.close();
        return cle;
    }
}
