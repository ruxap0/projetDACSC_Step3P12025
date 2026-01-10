package hepl.DACSC.client.views;

import hepl.DACSC.model.entity.Consultation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ClientInterface extends JFrame {
    private JTable reportTables;
    private DefaultTableModel tableModel;
    private JTextField filterPatientField;
    private JTextField filterDateField;
    private JButton btnNew;
    private JButton btnRefresh;
    private JButton btnUpdateReport;
    private JButton btnLogout;
    private String currentDoctorName;
    private String currentDoctorId;

    private int idDoctor = 0;

    private ArrayList<Consultation> consultations;

    // Formateurs pour les dates et heures
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public ClientInterface(String doctorName) {
        this.currentDoctorName = doctorName;
        this.consultations = new ArrayList<>();

        setTitle("Gestion des Consultations - " + doctorName);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Header
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Center - Table
        JPanel centerPanel = createCenterPanel();
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Titre et info médecin
        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        JLabel titleLabel = new JLabel("Gestion des Consultations");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        JLabel doctorLabel = new JLabel("Médecin: " + currentDoctorName);
        doctorLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        titlePanel.add(titleLabel);
        titlePanel.add(doctorLabel);

        // Bouton déconnexion
        btnLogout = new JButton("Logout");

        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.add(btnLogout, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));

        // Panel actions
        JPanel actionPanel = createActionPanel();
        centerPanel.add(actionPanel, BorderLayout.NORTH);

        // Panel filtres
        JPanel filterPanel = createFilterPanel();
        centerPanel.add(filterPanel, BorderLayout.SOUTH);

        // Création du tableau
        JPanel tablePanel = createTablePanel();
        centerPanel.add(tablePanel, BorderLayout.CENTER);

        return centerPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Liste des Consultations"));

        // Définir les colonnes du tableau
        String[] columnNames = {
                "ID",
                "Date",
                "Patient",
                "Report"
        };

        // Créer le modèle de tableau (non-éditable)
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Rendre toutes les cellules non-éditables
            }
        };

        // Créer le tableau
        reportTables = new JTable(tableModel);
        reportTables.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        reportTables.setRowHeight(25);
        reportTables.getTableHeader().setReorderingAllowed(false);

        // Définir les largeurs de colonnes
        reportTables.getColumnModel().getColumn(0).setPreferredWidth(50);   // ID
        reportTables.getColumnModel().getColumn(1).setPreferredWidth(120);  // Date
        reportTables.getColumnModel().getColumn(2).setPreferredWidth(250);  // Patient
        reportTables.getColumnModel().getColumn(3).setPreferredWidth(400);  // Report

        JScrollPane scrollPane = new JScrollPane(reportTables);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private JPanel createActionPanel() {
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionPanel.setBorder(BorderFactory.createTitledBorder("Actions"));

        btnNew = new JButton("Add Report");

        btnRefresh = new JButton("Rafraichir");
        btnUpdateReport = new JButton("Update Report");

        actionPanel.add(btnNew);
        actionPanel.add(btnRefresh);
        actionPanel.add(btnUpdateReport);

        return actionPanel;
    }

    private JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filtres"));

        filterPanel.add(new JLabel("Id Patient:"));

        filterPatientField = new JTextField(6);
        filterPanel.add(filterPatientField);
        filterPanel.add(filterPatientField);

        return filterPanel;
    }

    /**
     * Met à jour le tableau avec la liste des consultations
     */
    public void updateTable() {
        // Vider le tableau
        tableModel.setRowCount(0);

        // Vérifier si la liste est null ou vide
        if (consultations == null || consultations.isEmpty()) {
            return;
        }

        // Ajouter chaque consultation au tableau
        for (Consultation consultation : consultations) {
            Object[] row = new Object[6];

            row[0] = consultation.getId();
            row[1] = consultation.getDate() != null ?
                    consultation.getDate().format(DATE_FORMATTER) : "";
            row[2] = consultation.getTime() != null ?
                    consultation.getTime().format(TIME_FORMATTER) : "";

            // Formatage du nom du patient
            if (consultation.getPatient() != null) {
                row[3] = consultation.getPatient().getLastName() + " " +
                        consultation.getPatient().getFirstName();
            } else {
                row[3] = "";
            }

            // Formatage du nom du médecin
            if (consultation.getDoctor() != null) {
                row[4] = consultation.getDoctor().getLastName() + " " +
                        consultation.getDoctor().getFirstName();
            } else {
                row[4] = "";
            }

            row[5] = consultation.getReason() != null ? consultation.getReason() : "";

            tableModel.addRow(row);
        }
    }

    /**
     * Retourne la consultation sélectionnée dans le tableau
     * @return la consultation sélectionnée ou null si aucune sélection
     */
    public Consultation getSelectedReport() {
        int selectedRow = reportTables.getSelectedRow();
        if (selectedRow >= 0 && selectedRow < consultations.size()) {
            return consultations.get(selectedRow);
        }
        return null;
    }

    // Action listeners and other methods
    public void addActionListener(ActionListener listener) {
        btnNew.addActionListener(listener);
        btnRefresh.addActionListener(listener);
        btnUpdateReport.addActionListener(listener);
        btnLogout.addActionListener(listener);
    }

    public ArrayList<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(ArrayList<Consultation> consultations) {
        this.consultations = consultations;
        updateTable();
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
        System.out.println("IdDoctor défini à: " + this.idDoctor);
    }

    public void showMessage(String erreur, String message) {
        JOptionPane.showMessageDialog(this, message, erreur, JOptionPane.INFORMATION_MESSAGE);
    }

    public String getFilterPatient() {
        return filterPatientField.getText().trim();
    }

    public String getFilterDate() {
        return filterDateField.getText().trim();
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> {
            ClientInterface ci = new ClientInterface("");
            ci.setVisible(true);
        });
    }

}