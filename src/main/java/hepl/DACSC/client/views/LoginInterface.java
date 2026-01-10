package hepl.DACSC.client.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginInterface extends JFrame {

    // Composants pour l'onglet connexion
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JCheckBox isNewUserCheckBox;

    // Bouton commun
    private JButton cancelButton;

    private WindowCloseListener closeListener;

    public void showMessage(String erreur, String loginInexistant) {
        // Dialog
    }

    public interface WindowCloseListener {
        void onWindowClosed();
    }

    public LoginInterface() {
        setTitle("Connexion - Application");
        setSize(420, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (closeListener != null) {
                    closeListener.onWindowClosed();
                }
            }
        });

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Onglet connexion
        JPanel loginPanel = createLoginPanel();
        tabbedPane.addTab("Se connecter", loginPanel);

        // Footer avec bouton intégré
        JPanel footerPanel = createFooterPanel();
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        // Ajout du conteneur principal à la fenêtre
        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(new Color(30, 144, 255)); // DodgerBlue
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Connexion - Application");
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);

        headerPanel.add(titleLabel);
        return headerPanel;
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panneau pour les champs de saisie avec GridBagLayout pour un meilleur contrôle
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);

        // Ajout des composants de connexion
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        fieldsPanel.add(new JLabel("Nom d'utilisateur:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        usernameField = new JTextField(15);
        usernameField.setPreferredSize(new Dimension(200, 25));
        fieldsPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        fieldsPanel.add(new JLabel("Mot de passe:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        passwordField = new JPasswordField(15);
        passwordField.setPreferredSize(new Dimension(200, 25));
        fieldsPanel.add(passwordField, gbc);

        // Case à cocher pour nouvel utilisateur
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        isNewUserCheckBox = new JCheckBox("Nouvel utilisateur");
        fieldsPanel.add(isNewUserCheckBox, gbc);

        // Panneau pour le bouton de connexion
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginButton = new JButton("Se connecter");
        buttonPanel.add(loginButton);

        panel.add(fieldsPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(211, 211, 211)); // LightGray
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panneau pour le bouton à droite
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(211, 211, 211));

        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.GRAY);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setPreferredSize(new Dimension(100, 30));
        cancelButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);

        buttonPanel.add(cancelButton);
        footerPanel.add(buttonPanel, BorderLayout.CENTER);

        return footerPanel;
    }

    public void addActionListeners(ActionListener listener) {
        cancelButton.addActionListener(listener);
        loginButton.addActionListener(listener);
    }

    // Getters pour récupérer les valeurs des champs (connexion)
    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public boolean isNewUser() {
        return isNewUserCheckBox.isSelected();
    }

    // Méthodes utilitaires pour vider les champs
    public void clearLoginFields() {
        usernameField.setText("");
        passwordField.setText("");
    }
}