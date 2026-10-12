package com.mycompany.projetjava;

import com.mycompany.projetjava.models.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public SettingDialog(HomePage parent) {
        super(parent, "Réglages & Compte", true);
        this.parentFrame = parent;

        setSize(460, 510);
        setLocationRelativeTo(parent);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBackground(new Color(43, 45, 48));
        root.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

      
        JLabel lblTitle = new JLabel("Paramètres du profil");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setAlignmentX(CENTER_ALIGNMENT);
        root.add(lblTitle);

        root.add(Box.createRigidArea(new Dimension(0, 20)));

        
        User user = UserSession.getInstance().getUser();
        int userId = (user != null) ? user.getId() : -1;
        String currentName = (user != null) ? user.getName() : "Utilisateur";
        String currentVille = (user != null && user.getVille() != null) ? user.getVille() : "";
        String currentSport = fetchCurrentFavoriteSport(userId);
        boolean isPrivate = fetchCurrentVisibility(userId);

       
        JPanel form = new JPanel(new GridLayout(4, 2, 12, 14));
        form.setBackground(new Color(43, 45, 48));
        form.setMaximumSize(new Dimension(400, 150));
        
        JLabel lblNameTitle = new JLabel("Nom complet :");
        lblNameTitle.setForeground(new Color(180, 180, 180));
        JLabel lblNameVal = new JLabel(currentName);
        lblNameVal.setForeground(Color.WHITE);
        lblNameVal.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JLabel lblVilleTitle = new JLabel("Ville :");
        lblVilleTitle.setForeground(new Color(180, 180, 180));
        txtVille = new JTextField(currentVille);
        styleInputField(txtVille);

        JLabel lblSportTitle = new JLabel("Sport favori :");
        lblSportTitle.setForeground(new Color(180, 180, 180));
        String[] sports = {"Course", "Cyclisme", "Natation", "Musculation", "Marche", "Autre"};
        cbSport = new JComboBox<>(sports);
        cbSport.setBackground(new Color(60, 63, 65));
        cbSport.setForeground(Color.WHITE);
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

       
        JLabel lblPwdHint = new JLabel("(Laisser vide pour conserver le mot de passe actuel)");
        lblPwdHint.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblPwdHint.setForeground(new Color(140, 140, 140));
        lblPwdHint.setAlignmentX(CENTER_ALIGNMENT);
        root.add(Box.createRigidArea(new Dimension(0, 6)));
        root.add(lblPwdHint);

        root.add(Box.createRigidArea(new Dimension(0, 18)));

       
        chkPrivate = new JCheckBox("Compte public (rendre mes activités visibles aux autres :)");
        chkPrivate.setSelected(isPrivate);
        chkPrivate.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        chkPrivate.setForeground(Color.WHITE);
        chkPrivate.setBackground(new Color(43, 45, 48));
        chkPrivate.setFocusPainted(false);
        chkPrivate.setAlignmentX(CENTER_ALIGNMENT);
        root.add(chkPrivate);

        root.add(Box.createRigidArea(new Dimension(0, 22)));

   
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

    private void styleInputField(JTextField field) {
        field.setBackground(new Color(60, 63, 65));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 80)),
            BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));
    }

    private String fetchCurrentFavoriteSport(int userId) {
        if (userId <= 0) return "Course";
        String sql = "SELECT sport_favori FROM projetjava.users WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("sport_favori");
                }
            }
        } catch (SQLException ignored) {}
        return "Course";
    }

    private boolean fetchCurrentVisibility(int userId) {
        if (userId <= 0) return false;
        String sql = "SELECT visibilite FROM projetjava.users WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("visibilite");
                }
            }
        } catch (SQLException ignored) {}
        return false;
    }

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

        boolean changePassword = !newPassword.isEmpty();
        String sql = changePassword
            ? "UPDATE projetjava.users SET ville = ?, sport_favori = ?, visibilite = ?, password = ? WHERE id = ?"
            : "UPDATE projetjava.users SET ville = ?, sport_favori = ?, visibilite = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newVille);
            ps.setString(2, newSport);
            ps.setBoolean(3, newVisibilite);

            if (changePassword) {
                ps.setString(4, newPassword);
                ps.setInt(5, user.getId());
            } else {
                ps.setInt(4, user.getId());
            }

            ps.executeUpdate();
            user.setVille(newVille);

            JOptionPane.showMessageDialog(this, "Modifications enregistrées avec succès !");
            parentFrame.refreshActivities(); // Rafraîchit les flux, les stats et la carte
            dispose();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la mise à jour : " + ex.getMessage(), "Erreur BDD", JOptionPane.ERROR_MESSAGE);
        }
    }

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
            new AuthPage().setVisible(true);
        }
    }
}
 