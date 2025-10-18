package gui;

import dao.VoterDAO;
import model.Voter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private VoterDAO voterDAO;
    
    public LoginFrame() {
        voterDAO = new VoterDAO();
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("Online Voting System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel titleLabel = new JLabel("Voter Login", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        
        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);
        
        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        JButton adminButton = new JButton("Admin Login");
        
        loginButton.addActionListener(new LoginListener());
        registerButton.addActionListener(e -> openRegistration());
        adminButton.addActionListener(e -> openAdminLogin());
        
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        buttonPanel.add(adminButton);
        
        // Add components to main panel
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());
            
            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(LoginFrame.this, 
                    "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Voter voter = voterDAO.authenticateVoter(email, password);
            if (voter != null) {
                if (voter.hasVoted()) {
                    JOptionPane.showMessageDialog(LoginFrame.this, 
                        "You have already voted!", "Information", 
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    new VotingFrame(voter).setVisible(true);
                    dispose();
                }
            } else {
                JOptionPane.showMessageDialog(LoginFrame.this, 
                    "Invalid credentials or account not verified", "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void openRegistration() {
        new RegistrationFrame().setVisible(true);
        dispose();
    }
    
    private void openAdminLogin() {
        new AdminLoginFrame().setVisible(true);
        dispose();
    }
}