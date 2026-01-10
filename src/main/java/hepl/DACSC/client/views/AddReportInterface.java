package hepl.DACSC.client.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddReportInterface extends JFrame {

    private JTextField patientIdField;
    private JTextField dateField;
    private JTextArea rapportTextArea;
    private JButton ajouterRapportButton;
    private JButton cancelButton;

    // Interface pour notifier la fermeture
    public interface WindowCloseListener {
        void onWindowClosed();
    }

    private WindowCloseListener closeListener;

    public AddReportInterface() {
        setTitle("Ajout d'un Rapport Médical");
        setSize(600, 500);
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
        JPanel contentPanel = createContentPanel();
        JPanel footerPanel = createFooterPanel();

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    public void setWindowCloseListener(WindowCloseListener listener) {
        this.closeListener = listener;
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(new Color(30, 144, 255));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Ajout d'un Rapport Médical");
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);

        headerPanel.add(titleLabel);
        return headerPanel;
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ID du patient
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel patientIdLabel = new JLabel("ID du Patient :");
        patientIdLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        panel.add(patientIdLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        patientIdField = new JTextField(20);
        patientIdField.setPreferredSize(new Dimension(250, 30));
        panel.add(patientIdField, gbc);

        // Date du rapport
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD) :");
        dateLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        panel.add(dateLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        dateField = new JTextField(20);
        dateField.setPreferredSize(new Dimension(250, 30));
        panel.add(dateField, gbc);

        // Texte du rapport
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.insets = new Insets(10, 10, 10, 10);
        JLabel rapportLabel = new JLabel("Rapport :");
        rapportLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        panel.add(rapportLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        rapportTextArea = new JTextArea(10, 30);
        rapportTextArea.setLineWrap(true);
        rapportTextArea.setWrapStyleWord(true);
        rapportTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        rapportTextArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JScrollPane scrollPane = new JScrollPane(rapportTextArea);
        scrollPane.setPreferredSize(new Dimension(350, 200));
        panel.add(scrollPane, gbc);

        // Bouton ajouter
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.insets = new Insets(20, 10, 10, 10);

        ajouterRapportButton = new JButton("Ajouter le Rapport");
        ajouterRapportButton.setBackground(new Color(0, 128, 0));
        ajouterRapportButton.setForeground(Color.WHITE);
        ajouterRapportButton.setPreferredSize(new Dimension(180, 40));
        ajouterRapportButton.setFocusPainted(false);
        ajouterRapportButton.setBorderPainted(false);
        ajouterRapportButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        panel.add(ajouterRapportButton, gbc);

        return panel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(new Color(211, 211, 211));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        cancelButton = new JButton("Annuler");
        cancelButton.setBackground(Color.GRAY);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setPreferredSize(new Dimension(100, 35));
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);
        cancelButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

        footerPanel.add(cancelButton);
        return footerPanel;
    }

    // Méthodes pour ajouter les listeners
    public void addActionListeners(ActionListener listener) {
        cancelButton.addActionListener(listener);
        ajouterRapportButton.addActionListener(listener);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    // Getters pour récupérer les données
    public int getPatientId() throws NumberFormatException {
        return Integer.parseInt(patientIdField.getText().trim());
    }

    public String getDate() {
        return dateField.getText().trim();
    }

    public String getRapportText() {
        return rapportTextArea.getText().trim();
    }

    public JButton getAjouterRapportButton() {
        return ajouterRapportButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    // Méthode pour vider les champs
    public void clearFields() {
        patientIdField.setText("");
        dateField.setText("");
        rapportTextArea.setText("");
    }

    // Méthode pour valider les champs
    public boolean validateFields() {
        if (patientIdField.getText().trim().isEmpty()) {
            showError("Veuillez entrer l'ID du patient");
            return false;
        }

        try {
            Integer.parseInt(patientIdField.getText().trim());
        } catch (NumberFormatException e) {
            showError("L'ID du patient doit être un nombre valide");
            return false;
        }

        if (dateField.getText().trim().isEmpty()) {
            showError("Veuillez entrer la date du rapport");
            return false;
        }

        if (rapportTextArea.getText().trim().isEmpty()) {
            showError("Veuillez entrer le texte du rapport");
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AddReportInterface addReportInterface = new AddReportInterface();
            addReportInterface.setVisible(true);
        });
    }
}