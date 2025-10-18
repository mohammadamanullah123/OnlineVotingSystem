package gui;

import dao.CandidateDAO;
import model.Candidate;
import model.Voter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VotingFrame extends JFrame {
    private Voter voter;
    private CandidateDAO candidateDAO;
    private List<Candidate> candidates;
    private ButtonGroup candidateGroup;
    
    public VotingFrame(Voter voter) {
        this.voter = voter;
        this.candidateDAO = new CandidateDAO();
        this.candidates = candidateDAO.getAllCandidates();
        
        // DEBUG: Check constructor
        System.out.println("DEBUG: VotingFrame constructor called");
        System.out.println("DEBUG: Voter: " + voter.getName());
        System.out.println("DEBUG: Candidates count: " + candidates.size());
        
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("Online Voting System - Cast Your Vote");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        
        // DEBUG: Check UI initialization
        System.out.println("DEBUG: initializeUI() called");
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Select Your Candidate", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        JPanel candidatesPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        candidateGroup = new ButtonGroup();
        
        // DEBUG: Check before loop
        System.out.println("DEBUG: Starting candidate loop, count: " + candidates.size());
        
        for (Candidate candidate : candidates) {
            // DEBUG: Each candidate
            System.out.println("DEBUG: Adding candidate: " + candidate.getName());
            
            JPanel candidatePanel = new JPanel(new BorderLayout());
            candidatePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            candidatePanel.setBackground(Color.WHITE);
            
            JRadioButton radioButton = new JRadioButton();
            radioButton.setActionCommand(String.valueOf(candidate.getCandidateId()));
            candidateGroup.add(radioButton);
            
            // FIXED: Simple text without HTML
            String candidateText = candidate.getName() + " - " + candidate.getParty() + 
                                  " (" + candidate.getSymbol() + ") | Constituency: " + 
                                  candidate.getConstituency();
            JLabel infoLabel = new JLabel(candidateText);
            infoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            candidatePanel.add(radioButton, BorderLayout.WEST);
            candidatePanel.add(infoLabel, BorderLayout.CENTER);
            candidatesPanel.add(candidatePanel);
        }
        
        // DEBUG: After loop
        System.out.println("DEBUG: Finished adding candidates");
        
        JScrollPane scrollPane = new JScrollPane(candidatesPanel);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton voteButton = new JButton("Cast Vote");
        JButton logoutButton = new JButton("Logout");
        
        voteButton.addActionListener(new VoteListener());
        logoutButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
        
        buttonPanel.add(voteButton);
        buttonPanel.add(logoutButton);
        
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        // DEBUG: UI complete
        System.out.println("DEBUG: UI setup complete");
    }
    
    private class VoteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            String selectedCandidateId = null;
            if (candidateGroup.getSelection() != null) {
                selectedCandidateId = candidateGroup.getSelection().getActionCommand();
            }
            if (selectedCandidateId == null) {
                JOptionPane.showMessageDialog(VotingFrame.this, 
                    "Please select a candidate", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int candidateId = Integer.parseInt(selectedCandidateId);
            int confirm = JOptionPane.showConfirmDialog(VotingFrame.this, 
                "Are you sure you want to cast your vote? This action cannot be undone.", 
                "Confirm Vote", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = candidateDAO.castVote(voter.getVoterId(), candidateId);
                if (success) {
                    JOptionPane.showMessageDialog(VotingFrame.this, 
                        "Vote cast successfully! Thank you for voting.", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    new LoginFrame().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(VotingFrame.this, 
                        "Failed to cast vote. You may have already voted.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}