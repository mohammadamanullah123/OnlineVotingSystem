package gui;

import dao.VoterDAO;
import model.Voter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationFrame extends JFrame {
    private JTextField nameField, emailField, aadharField;
    private JPasswordField passwordField, confirmPasswordField;
    private VoterDAO voterDAO;
    
    public RegistrationFrame() {
        voterDAO = new VoterDAO();
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("Online Voting System - Voter Registration");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Voter Registration", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        
        formPanel.add(new JLabel("Full Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);
        
        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);
        
        formPanel.add(new JLabel("Aadhar Number:"));
        aadharField = new JTextField();
        formPanel.add(aadharField);
        
        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);
        
        formPanel.add(new JLabel("Confirm Password:"));
        confirmPasswordField = new JPasswordField();
        formPanel.add(confirmPasswordField);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton registerButton = new JButton("Register");
        JButton backButton = new JButton("Back to Login");
        
        registerButton.addActionListener(new RegisterListener());
        backButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
        
        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);
        
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private class RegisterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String aadhar = aadharField.getText().trim();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            
            // Validation
            if (name.isEmpty() || email.isEmpty() || aadhar.isEmpty() || 
                password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(RegistrationFrame.this, 
                    "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(RegistrationFrame.this, 
                    "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (aadhar.length() != 12 || !aadhar.matches("\\d+")) {
                JOptionPane.showMessageDialog(RegistrationFrame.this, 
                    "Aadhar number must be 12 digits", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Voter voter = new Voter(name, email, password, aadhar);
            boolean success = voterDAO.registerVoter(voter);
            
            if (success) {
                JOptionPane.showMessageDialog(RegistrationFrame.this, 
                    "Registration successful! Please wait for verification.", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                new LoginFrame().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(RegistrationFrame.this, 
                    "Registration failed. Email or Aadhar may already be registered.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}