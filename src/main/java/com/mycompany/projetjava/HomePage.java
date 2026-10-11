package com.mycompany.projetjava;

import com.mycompany.projetjava.models.Activity;
import com.mycompany.projetjava.services.ActivityService;
import com.mycompany.projetjava.services.UserService;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import javax.swing.event.MouseInputListener;

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
        jTabbedPane1.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
        @Override
        protected int calculateTabWidth(int tabPlacement, int tabIndex, java.awt.FontMetrics metrics) {
            int tabCount = tabPane.getTabCount();
            if (tabCount > 0 && tabPane.getWidth() > 0) {
                return tabPane.getWidth() / tabCount;
            }
            return super.calculateTabWidth(tabPlacement, tabIndex, metrics);
        }
    });

    // Écouteur pour recalculer la largeur si la fenêtre est redimensionnée
    jTabbedPane1.addComponentListener(new java.awt.event.ComponentAdapter() {
        @Override
        public void componentResized(java.awt.event.ComponentEvent e) {
            jTabbedPane1.revalidate();
            jTabbedPane1.repaint();
        }
    });

    refreshActivities();

        this.setSize(800, 600);
    }
    
    public void refreshActivities(){
        fillFluxTab();
        fillMyActivitiesTab(); 
        fillAccountTab();
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

    
        
    private ArrayList<ActivityCard> getActivityCards(boolean others){
        ActivityService as = new ActivityService();
        ArrayList<ActivityCard> cards = new ArrayList();
        try{
            ArrayList<Activity> activities = as.getAllActivities(others);   

            for(Activity a : activities){
                ActivityCard c = new ActivityCard(!others);
                c.setActivity(a);
                cards.add(c);
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(this, 
                "Une erreur est survenue durant le chargement des activités !",
                "Erreur : Base de donnée",
                JOptionPane.ERROR_MESSAGE
            );
        }
        return cards;
    }
    
private void fillMyActivitiesTab() {
        javax.swing.JPanel container = new javax.swing.JPanel();
        container.setLayout(new javax.swing.BoxLayout(container, javax.swing.BoxLayout.Y_AXIS));
        container.setBackground(new java.awt.Color(43, 45, 48)); 

        container.add(createHeaderPanel());

        container.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 15)));

       
        container.add(createWeeklySummaryPanel());

        container.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 20)));

     
        ArrayList<ActivityCard> cards = getActivityCards(false);
        if (!cards.isEmpty()) {
            for (ActivityCard card : cards) {
                container.add(card);
                container.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 15)));
            }
        } else {
            JLabel empty = new JLabel("Aucune activité enregistrée pour le moment");
            empty.setForeground(java.awt.Color.LIGHT_GRAY);
            empty.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
            container.add(empty);
        }

        container.add(javax.swing.Box.createVerticalGlue());

        myActivitiesTab.setViewportView(container);
        myActivitiesTab.getVerticalScrollBar().setUnitIncrement(16);
    }

    private javax.swing.JPanel createHeaderPanel() {
        javax.swing.JPanel headerPanel = new javax.swing.JPanel(new java.awt.BorderLayout(15, 0));
        headerPanel.setBackground(new java.awt.Color(43, 45, 48));
        headerPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 16, 12, 16));
        headerPanel.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 65));

       
        javax.swing.JButton btnAdd = new javax.swing.JButton("Nouvelle activité");
        try {
            javax.swing.ImageIcon iconPlus = new javax.swing.ImageIcon(getClass().getResource("/new_icon.png"));
            btnAdd.setIcon(iconPlus);
            btnAdd.setIconTextGap(8);
        } catch (Exception ignored) {}

        btnAdd.setBackground(new java.awt.Color(0, 120, 215));
        btnAdd.setForeground(java.awt.Color.WHITE);
        btnAdd.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        btnAdd.setFocusPainted(false);
        btnAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdd.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnAdd.addActionListener(evt -> {
            ActivityDialog dialog = new ActivityDialog(HomePage.this, true);
            dialog.setVisible(true);
            refreshActivities();
        });
        headerPanel.add(btnAdd, java.awt.BorderLayout.WEST);

        
        JLabel lblProfile = new JLabel("Mon Profil", javax.swing.SwingConstants.CENTER);
        lblProfile.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 17));
        lblProfile.setForeground(java.awt.Color.WHITE);
        headerPanel.add(lblProfile, java.awt.BorderLayout.CENTER);

        
        javax.swing.JButton btnSettings = new javax.swing.JButton("Réglages ⚙");
        btnSettings.setBackground(new java.awt.Color(60, 63, 65));
        btnSettings.setForeground(java.awt.Color.WHITE);
        btnSettings.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        btnSettings.setFocusPainted(false);
        btnSettings.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSettings.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnSettings.addActionListener(evt -> {
        SettingDialog settings = new SettingDialog(HomePage.this);
           settings.setVisible(true);
            });
        headerPanel.add(btnSettings, java.awt.BorderLayout.EAST);

        return headerPanel;
    }

    /**
     * Panneau récapitulatif hebdomadaire
     */
 private String formatDuration(int totalSeconds) {
        if (totalSeconds == 0) {
            return "0 min";
        }
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        if (hours > 0) {
            return hours + " h " + minutes + " min";
        }
        return minutes + " min";
    }
private javax.swing.JPanel createWeeklySummaryPanel() {
        javax.swing.JPanel summaryCard = new javax.swing.JPanel();
        summaryCard.setLayout(new java.awt.GridLayout(1, 3, 12, 0));
        summaryCard.setBackground(new java.awt.Color(55, 57, 61));
        summaryCard.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(70, 72, 75), 1),
            javax.swing.BorderFactory.createEmptyBorder(12, 16, 12, 16)
        ));
        summaryCard.setMaximumSize(new java.awt.Dimension(740, 75));
        summaryCard.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);

        // Valeurs par défaut si aucune donnée ou en cas d'erreur
        int nbActivities = 0;
        String totalTime = "0 min";
        String favoriteSport = "Aucun";

        // Récupération des données dynamiques depuis la base de données
        try {
            ActivityService as = new ActivityService();
            ActivityService.WeeklySummary summary = as.getWeeklySummary();

            nbActivities = summary.count;
            totalTime = formatDuration(summary.totalDurationSeconds);
            favoriteSport = summary.topSport;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Ajout des blocs avec les vraies données
        summaryCard.add(createStatBlock("Activités cette semaine", String.valueOf(nbActivities)));
        summaryCard.add(createStatBlock("Temps d'effort total", totalTime));
        summaryCard.add(createStatBlock("Discipline principale", favoriteSport));

        return summaryCard;
    }
    private javax.swing.JPanel createStatBlock(String title, String value) {
        javax.swing.JPanel block = new javax.swing.JPanel(new java.awt.BorderLayout(0, 4));
        block.setBackground(new java.awt.Color(55, 57, 61));

        JLabel lblTitle = new JLabel(title, javax.swing.SwingConstants.CENTER);
        lblTitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        lblTitle.setForeground(new java.awt.Color(180, 180, 180));

        JLabel lblVal = new JLabel(value, javax.swing.SwingConstants.CENTER);
        lblVal.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 15));
        lblVal.setForeground(java.awt.Color.WHITE);

        block.add(lblTitle, java.awt.BorderLayout.NORTH);
        block.add(lblVal, java.awt.BorderLayout.CENTER);
        return block;
    }
private void fillAccountTab() {
        // 1. Définition du User-Agent requis par les serveurs OSM
        System.setProperty("http.agent", "ProjetJavaSwingStudentApp/1.0 (contact: test@univ.fr)");

        accountTab.removeAll();
        accountTab.setLayout(new java.awt.BorderLayout());

        // 2. Récupération de la ville et de ses coordonnées
        com.mycompany.projetjava.models.User user = UserSession.getInstance().getUser();
        String nomVille = (user != null && user.getVille() != null) ? user.getVille() : "Nantes";
        GeoUtils.Coordinates coords = GeoUtils.getCoordinates(nomVille);

        JXMapViewer mapViewer = new JXMapViewer();

    TileFactoryInfo info = new TileFactoryInfo(
        1, 15, 17, 256, true, true,
        "https://server.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer/tile",
        "x", "y", "z"
    ) {
        @Override
        public String getTileUrl(int x, int y, int zoom) {
            int z = 17 - zoom;
            return this.baseURL + "/" + z + "/" + y + "/" + x;
        }
    };

    DefaultTileFactory tileFactory = new DefaultTileFactory(info);
    tileFactory.setThreadPoolSize(8);
    mapViewer.setTileFactory(tileFactory);

        // 4. Centrage sur la ville
        GeoPosition center = new GeoPosition(coords.lat, coords.lon);
        mapViewer.setZoom(4);
        mapViewer.setAddressLocation(center);

        // 5. Interactions souris (glisser-déposer + zoom molette)
        MouseInputListener mia = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mia);
        mapViewer.addMouseMotionListener(mia);
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapViewer));

        // 6. Intégration dans l'onglet
        accountTab.add(mapViewer, java.awt.BorderLayout.CENTER);
        accountTab.revalidate();
        accountTab.repaint();
    }
private java.awt.Image getMapImage(double lat, double lon, int width, int height) {
        try {
            // Service de cartes statiques Wikimedia / OSM (fiable et direct)
            String urlStr = "https://maps.wikimedia.org/img/osm-intl,13," 
                            + lat + "," + lon + "," 
                            + width + "x" + height + ".png";

            java.net.URL url = new java.net.URI(urlStr).toURL();
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
            
            // Un User-Agent complet est obligatoire pour éviter le blocage HTTP 403
            conn.setRequestProperty("User-Agent", "ProjetJavaSwingStudentApp/1.0 (contact: test@univ.fr)");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.connect();

            int code = conn.getResponseCode();
            if (code == 200) {
                return javax.imageio.ImageIO.read(conn.getInputStream());
            } else {
                System.err.println("Erreur HTTP serveur carto : " + code);
            }
        } catch (Exception e) {
            System.err.println("Exception récupération carte : " + e.getMessage());
            e.printStackTrace();
        }
        return null;
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

        jTabbedPane1.addTab("Carte", accountTab);

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
