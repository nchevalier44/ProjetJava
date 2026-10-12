package com.mycompany.projetjava;

import com.mycompany.projetjava.pages.HomePage;
import com.mycompany.projetjava.pages.AuthPage;
import com.mycompany.projetjava.models.ActivityType;
import com.mycompany.projetjava.models.User;
import com.mycompany.projetjava.services.ActivityService;
import com.mycompany.projetjava.services.UserService;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SettingDialog extends JDialog {

    private final HomePage parentFrame;
    private JTextField txtVille;
    private JComboBox<String> cbSport;
    private JPasswordField txtNewPassword;
    private JCheckBox chkPrivate;
    
    // Déclaration des services
    private final UserService userService;
    private final ActivityService activityService;

    /**
     * Open a dialog window for user settings
     * @param parent 
     */
    public SettingDialog(HomePage parent) {
        super(parent, "Réglages & Compte", true);
        this.parentFrame = parent;
        this.userService = new UserService();
        this.activityService = new ActivityService();

        setSize(460, 510);
        setLocationRelativeTo(parent);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBackground(new Color(43, 45, 48));
        root.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // Titre
        JLabel lblTitle = new JLabel("Paramètres du profil");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setAlignmentX(CENTER_ALIGNMENT);
        root.add(lblTitle);

        root.add(Box.createRigidArea(new Dimension(0, 20)));

        // Récupération des infos utilisateur actuelles depuis la session
        User user = UserSession.getInstance().getUser();
        String currentName = (user != null) ? user.getName() : "Utilisateur";
        String currentVille = (user != null && user.getVille() != null) ? user.getVille() : "";
        String currentSport = (user != null && user.getSportFavori() != null) ? user.getSportFavori() : "Course";
        boolean isPrivate = (user != null) ? user.isVisibilite() : false;

        // Formulaire (4 lignes x 2 colonnes)
        JPanel form = new JPanel(new GridLayout(4, 2, 12, 14));
        form.setBackground(new Color(43, 45, 48));
        form.setMaximumSize(new Dimension(400, 150));

        // Nom complet
        JLabel lblNameTitle = new JLabel("Nom complet :");
        lblNameTitle.setForeground(new Color(180, 180, 180));
        JLabel lblNameVal = new JLabel(currentName);
        lblNameVal.setForeground(Color.WHITE);
        lblNameVal.setFont(new Font("Segoe UI", Font.BOLD, 13));

        // Ville
        JLabel lblVilleTitle = new JLabel("Ville :");
        lblVilleTitle.setForeground(new Color(180, 180, 180));
        txtVille = new JTextField(currentVille);
        styleInputField(txtVille);

        // Sport favori (Chargement dynamique depuis la base de données)
        JLabel lblSportTitle = new JLabel("Sport favori :");
        lblSportTitle.setForeground(new Color(180, 180, 180));
        
        cbSport = new JComboBox<>();
        cbSport.setBackground(new Color(60, 63, 65));
        cbSport.setForeground(Color.WHITE);
        
        try {
            ArrayList<ActivityType> activityTypes = activityService.getAllActivityTypes();
            for (ActivityType type : activityTypes) {
                cbSport.addItem(type.getName()); 
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors du chargement des types d'activités : " + ex.getMessage());
            // Valeurs de secours au cas où la base de données ne répond pas
            cbSport.addItem("Course à pied"); 
            cbSport.addItem("Autre");
        }

        // On présélectionne le sport actuel de l'utilisateur
        if (currentSport != null && !currentSport.isBlank()) {
            cbSport.setSelectedItem(currentSport);
        }

        // Nouveau mot de passe
        JLabel lblPwdTitle = new JLabel("Nouveau mdp :");
        lblPwdTitle.setForeground(new Color(180, 180, 180));
        txtNewPassword = new JPasswordField();
        styleInputField(txtNewPassword);

        form.add(lblNameTitle);
        form.add(lblNameVal);
        form.add(lblVilleTitle);
        form.add(txtVille);
        form.add(lblSportTitle);
        form.add(cbSport);
        form.add(lblPwdTitle);
        form.add(txtNewPassword);
        root.add(form);

        // Indication mot de passe
        JLabel lblPwdHint = new JLabel("(Laisser vide pour conserver le mot de passe actuel)");
        lblPwdHint.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblPwdHint.setForeground(new Color(140, 140, 140));
        lblPwdHint.setAlignmentX(CENTER_ALIGNMENT);
        root.add(Box.createRigidArea(new Dimension(0, 6)));
        root.add(lblPwdHint);

        root.add(Box.createRigidArea(new Dimension(0, 18)));

        // Case à cocher : Compte privé / public
        chkPrivate = new JCheckBox("Compte public (rendre mes activités visibles aux autres)");
        chkPrivate.setSelected(isPrivate);
        chkPrivate.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        chkPrivate.setForeground(Color.WHITE);
        chkPrivate.setBackground(new Color(43, 45, 48));
        chkPrivate.setFocusPainted(false);
        chkPrivate.setAlignmentX(CENTER_ALIGNMENT);
        root.add(chkPrivate);

        root.add(Box.createRigidArea(new Dimension(0, 22)));

        // Bouton Enregistrer
        JButton btnSave = new JButton("Enregistrer les modifications");
        btnSave.setAlignmentX(CENTER_ALIGNMENT);
        btnSave.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        btnSave.setBackground(new Color(0, 120, 215));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnSave.setFocusPainted(false);
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.addActionListener(e -> saveSettings());
        root.add(btnSave);

        root.add(Box.createRigidArea(new Dimension(0, 12)));

        // Bouton Déconnexion
        JButton btnLogout = new JButton("Se déconnecter");
        btnLogout.setAlignmentX(CENTER_ALIGNMENT);
        btnLogout.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        btnLogout.setBackground(new Color(180, 40, 40));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnLogout.setFocusPainted(false);
        btnLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogout.addActionListener(e -> logout());
        root.add(btnLogout);

        add(root, BorderLayout.CENTER);
    }
    
    /**
     * Set written style to given text field
     * @param field 
     */
    private void styleInputField(JTextField field) {
        field.setBackground(new Color(60, 63, 65));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 80)),
            BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));
    }
    
    /**
     * Update user informations
     */
    private void saveSettings() {
        String newVille = txtVille.getText().trim();
        String newSport = (String) cbSport.getSelectedItem();
        String newPassword = new String(txtNewPassword.getPassword()).trim();
        boolean newVisibilite = chkPrivate.isSelected();

        if (newVille.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez renseigner un nom de ville.", "Champ requis", JOptionPane.WARNING_MESSAGE);
            return;
        }

        User user = UserSession.getInstance().getUser();
        if (user == null) return;

        try {
            // Appel au UserService pour mettre à jour la base de données
            boolean isUpdated = userService.updateUserSettings(user.getId(), newVille, newSport, newVisibilite, newPassword);

            if (isUpdated) {
                // Mise à jour de l'objet de session local
                user.setVille(newVille);
                user.setSportFavori(newSport);
                user.setVisibilite(newVisibilite);

                JOptionPane.showMessageDialog(this, "Modifications enregistrées avec succès !");
                parentFrame.refreshActivities(); // Rafraîchit les vues du HomePage
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Aucune modification n'a été apportée.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la mise à jour : " + ex.getMessage(), "Erreur BDD", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Logout the user and return the user to Authentification page
     */
    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Voulez-vous vraiment vous déconnecter ?",
            "Déconnexion",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            if (UserSession.getInstance() != null) {
                UserSession.getInstance().setUser(null);
            }
            dispose();
            parentFrame.dispose();
            new AuthPage().setVisible(true); // Ouvre la page de connexion
        }
    }
}