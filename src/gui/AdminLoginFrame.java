package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdminLoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    
    public AdminLoginFrame() {
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("Online Voting System - Admin Login");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Admin Login", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        
        formPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        formPanel.add(usernameField);
        
        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Back");
        
        loginButton.addActionListener(new AdminLoginListener());
        backButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
        
        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);
        
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private class AdminLoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            
            if (authenticateAdmin(username, password)) {
                new AdminDashboardFrame().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(AdminLoginFrame.this, 
                    "Invalid admin credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        private boolean authenticateAdmin(String username, String password) {
            String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
            
            try (Connection conn = dao.DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                
                ResultSet rs = pstmt.executeQuery();
                return rs.next();
                
            } catch (SQLException ex) {
                ex.printStackTrace();
                return false;
            }
        }
    }
}