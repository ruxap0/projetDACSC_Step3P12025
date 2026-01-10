package hepl.DACSC.client;

import hepl.DACSC.MyCrypto.CryptoUtils;
import hepl.DACSC.client.views.AddReportInterface;
import hepl.DACSC.client.views.ClientInterface;
import hepl.DACSC.client.views.LoginInterface;
import hepl.DACSC.protocol.Reponse.ReponseLoginAuth;
import hepl.DACSC.protocol.Reponse.ReponseLoginFirst;
import hepl.DACSC.protocol.Reponse.ReponseLoginSession;
import hepl.DACSC.protocol.ReponseMRPS;
import hepl.DACSC.protocol.Requetes.RequeteLoginAuth;
import hepl.DACSC.protocol.Requetes.RequeteLoginFirst;
import hepl.DACSC.protocol.Requetes.RequeteLoginSession;

import javax.crypto.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.security.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class ClientController implements ActionListener {
    private Socket socket;
    private ClientInterface clientView;
    private LoginInterface loginView;
    private AddReportInterface addView;
    private boolean isLoggedIn = false;

    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    private SecretKey _sessionKey;
    private PrivateKey _privateKey;
    private PublicKey _publicKey;

    public ClientController(LoginInterface loginView) throws IOException, ClassNotFoundException {
        this.loginView = loginView;
        this.loginView.addActionListeners(this);

        this._privateKey = loadPrivateKey();
        this._publicKey = loadPublicKey();
    }

    private PublicKey loadPublicKey() throws IOException, ClassNotFoundException {
        try {
            // Charger depuis les resources (fonctionne en JAR et en développement)
            InputStream is = getClass().getResourceAsStream("/CryptoCles/clePubliqueClient.ser");

            if (is == null) {
                throw new FileNotFoundException("Fichier de clé introuvable dans les resources");
            }

            ObjectInputStream ois = new ObjectInputStream(is);
            PublicKey key = (PublicKey) ois.readObject();
            ois.close();

            return key;
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de la clé privée: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private PrivateKey loadPrivateKey() throws IOException, ClassNotFoundException {
        try {
            InputStream is = getClass().getResourceAsStream("/CryptoCles/clePriveeClient.ser");

            if (is == null) {
                throw new FileNotFoundException("Fichier de clé introuvable dans les resources");
            }

            ObjectInputStream ois = new ObjectInputStream(is);
            PrivateKey key = (PrivateKey) ois.readObject();
            ois.close();

            return key;
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de la clé privée: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        switch (e.getActionCommand())
        {
            // Boutons Gestion interfaces
            case "Add Report":
                if (addView == null)
                {
                    addView = new AddReportInterface();
                    addView.addActionListeners(this);
                }
                addView.setVisible(true);
                break;
            case "Cancel":
                if (loginView != null)
                {
                    loginView.dispose();
                    loginView = null;
                }
                if (addView != null)
                {
                    addView.setVisible(false);
                }
                break;
            case "Logout":
                if(isLoggedIn)
                {
                    isLoggedIn = false;
                    clientView.dispose();
                    clientView = null;

                    if (loginView == null)
                    {
                        loginView = new LoginInterface();
                        loginView.setVisible(true);
                        loginView.addActionListeners(this);
                    }
                }
                break;

            // Boutons Messages à envoyer
            case "Se connecter":
                gestionLogin();
                break;
            case "Submit Report":
                break;
            case "Update Report":
                break;
            case "Rafraichir":
                reloadScreen();
                break;
        }
    }

    private void reloadScreen() {
        String patientName = clientView.getFilterPatient();
        LocalDate date = null;

        boolean hasPatientFilter = patientName != null && !patientName.isEmpty();
        boolean hasDateFilter = false;
        String dateString = clientView.getFilterDate();

        if (dateString != null && !dateString.isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                formatter = formatter.withLocale(Locale.FRANCE);
                date = LocalDate.parse(dateString, formatter);
                hasDateFilter = true;
            } catch (DateTimeParseException e) {
                // Date invalide
                clientView.showMessage("Erreur", "Format de date invalide. Utilisez le format YYYY-MM-DD");
                return; // ou gérer autrement selon ton besoin
            }
        }

    }

    private void gestionLogin()
    {
        try
        {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

            String loginDoctor = loginView.getUsername();
            String passwordDoctor = loginView.getPassword();

            socket = new Socket("192.168.253.128", 50009);

            RequeteLoginFirst requete1 = new RequeteLoginFirst(loginDoctor);
            oos.writeObject(requete1);
            oos.flush();

            ReponseLoginFirst reponse1 = (ReponseLoginFirst) ois.readObject();
            if(!reponse1.isSucces())
            {
                loginView.showMessage("Erreur", reponse1.getMessage());
                return;
            }
            byte[] digest = CryptoUtils.generateDigest(loginDoctor, passwordDoctor, reponse1.getSalt());
            byte[] signature = CryptoUtils.signData(digest, _privateKey);

            RequeteLoginAuth requete2 = new RequeteLoginAuth(digest, signature);
            oos.writeObject(requete2);
            oos.flush();

            ReponseLoginAuth reponse2 = (ReponseLoginAuth) ois.readObject();
            if(!reponse2.isSucces())
            {
                loginView.showMessage("Erreur", reponse1.getMessage());
                return;
            }

            SecretKey sessionKey = CryptoUtils.generateSessionKey();
            PublicKey serverKey = getServerPublicKey();
            byte[] cryptedKey = CryptoUtils.CryptAsymRSA(serverKey, sessionKey.getEncoded());

            RequeteLoginSession requete3 = new RequeteLoginSession(cryptedKey);
            oos.writeObject(requete3);
            oos.flush();

            ReponseLoginSession reponse3 = (ReponseLoginSession) ois.readObject();
            if(reponse3.isSucces())
            {
                isLoggedIn = true;
                _sessionKey = sessionKey;

                loginView.showMessage("Succès", "Connexion réussie !");

                clientView = new ClientInterface(loginDoctor.replace(".", " "));
                clientView.setVisible(true);
                clientView.addActionListener(this);
            }
            else{
                loginView.showMessage("Erreur", reponse3.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
            loginView.showMessage("Erreur", "Erreur de connexion: " + e.getMessage());
        }
    }

    private PublicKey getServerPublicKey() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("./DACSC/MyCrypto/clePubliqueServeur")
        );
        PublicKey cle = (PublicKey) ois.readObject();
        ois.close();
        return cle;
    }
}