package hepl.DACSC.client.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddInterface extends JFrame {

    private JTextField nomPatientField;
    private JTextField prenomPatientField;
    private JButton ajouterPatientButton;

    private JTextField dateConsultationField;
    private JTextField heureConsutationField;
    private JTextField dureeConsultationField;
    private JTextField nbConsultationField;
    private JButton ajouterConsultationButton;

    // Bouton commun
    private JButton cancelButton;

    // Interface pour notifier la fermeture
    public interface WindowCloseListener {
        void onWindowClosed();
    }

    private WindowCloseListener closeListener;

    public AddInterface() {
        setTitle("Ajout d'un Patient/Consultation");
        setSize(500, 600);
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

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel patientPanel = createPatientPanel();
        tabbedPane.addTab("Patient", patientPanel);

        JPanel consultationPanel = createConsultationPanel();
        tabbedPane.addTab("Consultation", consultationPanel);

        JPanel footerPanel = createFooterPanel();

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
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

        JLabel titleLabel = new JLabel("Ajout Patient/Consultation");
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);

        headerPanel.add(titleLabel);
        return headerPanel;
    }

    private JPanel createPatientPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ScrollPane pour le contenu
        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);

        // Titre de la section
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel titleLabel = new JLabel("Ajout d'un patient");
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        contentPanel.add(titleLabel, gbc);

        // Reset gridwidth
        gbc.gridwidth = 1;

        // Nom de l'activité
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        contentPanel.add(new JLabel("Nom du patient :"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        nomPatientField = new JTextField(15);
        nomPatientField.setPreferredSize(new Dimension(200, 25));
        contentPanel.add(nomPatientField, gbc);

        // Distance parcourue
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        contentPanel.add(new JLabel("Prénom du patient :"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        prenomPatientField = new JTextField(15);
        prenomPatientField.setPreferredSize(new Dimension(200, 25));
        contentPanel.add(prenomPatientField, gbc);

        // Bouton ajouter
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 8, 5);
        ajouterPatientButton = new JButton("Ajouter Patient");
        ajouterPatientButton.setBackground(new Color(0, 128, 0)); // Green
        ajouterPatientButton.setForeground(Color.WHITE);
        ajouterPatientButton.setPreferredSize(new Dimension(150, 35));
        ajouterPatientButton.setFocusPainted(false);
        ajouterPatientButton.setBorderPainted(false);
        contentPanel.add(ajouterPatientButton, gbc);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createConsultationPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ScrollPane pour le contenu
        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);

        // Titre de la section
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel titleLabel = new JLabel("Ajout d'une consultation");
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        contentPanel.add(titleLabel, gbc);

        // Reset gridwidth
        gbc.gridwidth = 1;

        // Nom de l'activité
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        contentPanel.add(new JLabel("Date :"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        dateConsultationField = new JTextField(15);
        dateConsultationField.setPreferredSize(new Dimension(200, 25));
        contentPanel.add(dateConsultationField, gbc);

        // Distance parcourue
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        contentPanel.add(new JLabel("Heure : "), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        heureConsutationField = new JTextField(15);
        heureConsutationField.setPreferredSize(new Dimension(200, 25));
        contentPanel.add(heureConsutationField, gbc);

        // Durée de l'activité
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        contentPanel.add(new JLabel("Durée :"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        dureeConsultationField = new JTextField(15);
        dureeConsultationField.setPreferredSize(new Dimension(200, 25));
        contentPanel.add(dureeConsultationField, gbc);

        // Nombre de pas
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        contentPanel.add(new JLabel("Nombre de consultations :"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        nbConsultationField = new JTextField(15);
        nbConsultationField.setPreferredSize(new Dimension(200, 25));
        contentPanel.add(nbConsultationField, gbc);

        // Bouton ajouter
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 8, 5);
        ajouterConsultationButton = new JButton("Ajouter Consultation");
        ajouterConsultationButton.setBackground(new Color(0, 128, 0));
        ajouterConsultationButton.setForeground(Color.WHITE);
        ajouterConsultationButton.setPreferredSize(new Dimension(150, 35));
        ajouterConsultationButton.setFocusPainted(false);
        ajouterConsultationButton.setBorderPainted(false);
        contentPanel.add(ajouterConsultationButton, gbc);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(new Color(211, 211, 211)); // LightGray
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        cancelButton = new JButton("Annuler");
        cancelButton.setBackground(Color.GRAY);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setPreferredSize(new Dimension(100, 30));
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);

        footerPanel.add(cancelButton);
        return footerPanel;
    }

    // Méthode pour ajouter les listeners aux boutons
    public void addActionListeners(ActionListener listener) {
        cancelButton.addActionListener(listener);
        ajouterPatientButton.addActionListener(listener);
        ajouterConsultationButton.addActionListener(listener);
    }

    public void showMessage(String message)
    {
        JOptionPane.showMessageDialog(this, message);
    }

    public String getDateConsultation()
    {
        return dateConsultationField.getText();
    }
    public String getHeureConsultation()
    {
        return heureConsutationField.getText();
    }
    public int getDureeConsultation()
    {
        return Integer.parseInt(dureeConsultationField.getText());
    }
    public int getNbConsultation()
    {
        return Integer.parseInt(nbConsultationField.getText());
    }

    public String getNomPatient()
    {
        return nomPatientField.getText();
    }

    public String getPrenomPatient()
    {
        return prenomPatientField.getText();
    }

    // Méthodes utilitaires pour vider les champs
    public void clearPatientFields() {
        nomPatientField.setText("");
        prenomPatientField.setText("");
    }

    public void clearConsultationFields() {
        dateConsultationField.setText("");
        heureConsutationField.setText("");
        dureeConsultationField.setText("");
        nbConsultationField.setText("");
    }
}