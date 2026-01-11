package hepl.DACSC.client;

import hepl.DACSC.client.views.LoginInterface;

import javax.swing.*;
import java.security.Security;

public class MainClient {
    public static void main(String[] args) {
        // Lancer l'interface graphique sur le thread EDT (Event Dispatch Thread)
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        SwingUtilities.invokeLater(() -> {
            try {
                // Créer la fenêtre de login
                LoginInterface loginView = new LoginInterface();

                // Créer le controller avec la vue login
                // Le controller gérera la création de ClientInterface après login réussi
                ClientController controller = new ClientController(loginView);

                // Afficher la fenêtre de login
                loginView.setVisible(true);

            } catch (Exception e) {
                System.err.println("Erreur lors du lancement de l'application : " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}