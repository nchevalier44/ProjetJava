package com.mycompany.projetjava;

import com.mycompany.projetjava.models.Activity;
import com.mycompany.projetjava.services.ActivityService;
import com.mycompany.projetjava.services.UserService;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Nathan
 */
public class HomePage extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(HomePage.class.getName());
    

    /**
     * Creates new form HomePage
     */
    public HomePage() {
        initComponents();
        refreshActivities();
    }
    
    public void refreshActivities(){
        fillFluxTab();
        fillMyActivitiesTab(); 
    }
    
    private void fillFluxTab() {
        javax.swing.JPanel container = new javax.swing.JPanel();
        container.setLayout(new javax.swing.BoxLayout(container, javax.swing.BoxLayout.Y_AXIS));

        container.setBackground(new java.awt.Color(43, 45, 48)); 

        container.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 15)));

        ArrayList<ActivityCard> cards = getActivityCards(true);
        if (!cards.isEmpty()) {
            for (ActivityCard card : cards) {
                container.add(card);
                container.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 15)));
            }
        } else {
            JLabel empty = new JLabel("Aucune activité n'a été trouvée");
            empty.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
            container.add(empty);
        }

        container.add(javax.swing.Box.createVerticalGlue());

        fluxTab.setViewportView(container);
        fluxTab.getVerticalScrollBar().setUnitIncrement(16);
    }

    private void fillMyActivitiesTab() {
        javax.swing.JPanel container = new javax.swing.JPanel();
        container.setLayout(new javax.swing.BoxLayout(container, javax.swing.BoxLayout.Y_AXIS));

        container.setBackground(new java.awt.Color(43, 45, 48)); 

        container.add(createAddActivityButtonPanel());

        container.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 15)));

        ArrayList<ActivityCard> cards = getActivityCards(false);
        if (!cards.isEmpty()) {
            for (ActivityCard card : cards) {
                container.add(card);
                container.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 15)));
            }
        } else {
            JLabel empty = new JLabel("Aucune activité n'a été trouvée");
            empty.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
            container.add(empty);
        }

        container.add(javax.swing.Box.createVerticalGlue());

        myActivitiesTab.setViewportView(container);
        myActivitiesTab.getVerticalScrollBar().setUnitIncrement(16);
    }
        
    private ArrayList<ActivityCard> getActivityCards(boolean others){
        ActivityService as = new ActivityService();
        ArrayList<ActivityCard> cards = new ArrayList();
        try{
            ArrayList<Activity> activities = as.getAllUserActivities(others);   

            for(Activity a : activities){
                ActivityCard c = new ActivityCard();
                c.setActivity(a);
                cards.add(c);
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(this, 
                "Error while loading your activities!",
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
        return cards;
    }
    
    private javax.swing.JPanel createAddActivityButtonPanel() {
        javax.swing.JPanel topPanel = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER));
        topPanel.setBackground(new java.awt.Color(43, 45, 48)); 
        topPanel.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 70)); 

        javax.swing.JButton btnAdd = new javax.swing.JButton("Ajouter une nouvelle activité");

        try {
            javax.swing.ImageIcon iconPlus = new javax.swing.ImageIcon(getClass().getResource("/new_icon.png"));
            btnAdd.setIcon(iconPlus);
            btnAdd.setIconTextGap(12);
        } catch (Exception e) {
            System.out.println("Icône introuvable : vérifiez le chemin du fichier.");
        }

        btnAdd.setBackground(new java.awt.Color(0, 120, 215)); 
        btnAdd.setForeground(java.awt.Color.WHITE);
        btnAdd.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 15));
        btnAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdd.setFocusPainted(false);
        btnAdd.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 24, 12, 24));

        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddActivityDialog dialog = new AddActivityDialog(HomePage.this, true);
                dialog.setVisible(true);
                refreshActivities();
            }
        });

        topPanel.add(btnAdd);
    
        return topPanel;
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        fluxTab = new javax.swing.JScrollPane();
        myActivitiesTab = new javax.swing.JScrollPane();
        accountTab = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.addTab("Flux", fluxTab);
        jTabbedPane1.addTab("Mes activités", myActivitiesTab);

        javax.swing.GroupLayout accountTabLayout = new javax.swing.GroupLayout(accountTab);
        accountTab.setLayout(accountTabLayout);
        accountTabLayout.setHorizontalGroup(
            accountTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 913, Short.MAX_VALUE)
        );
        accountTabLayout.setVerticalGroup(
            accountTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 265, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Mon compte", accountTab);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new HomePage().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel accountTab;
    private javax.swing.JScrollPane fluxTab;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JScrollPane myActivitiesTab;
    // End of variables declaration//GEN-END:variables
}
