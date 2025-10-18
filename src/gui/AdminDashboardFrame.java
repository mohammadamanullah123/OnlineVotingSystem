package gui;

import dao.CandidateDAO;
import dao.VoterDAO;
import model.Candidate;
import model.Voter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminDashboardFrame extends JFrame {
    private CandidateDAO candidateDAO;
    private VoterDAO voterDAO;
    
    public AdminDashboardFrame() {
        this.candidateDAO = new CandidateDAO();
        this.voterDAO = new VoterDAO();
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("Online Voting System - Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Results Tab
        tabbedPane.addTab("Election Results", createResultsPanel());
        
        // Voters Tab
        tabbedPane.addTab("Voter Management", createVotersPanel());
        
        // Logout button
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
        logoutPanel.add(logoutButton);
        
        mainPanel.add(logoutPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }
    
    private JPanel createResultsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Election Results", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        List<Candidate> results = candidateDAO.getVoteResults();
        
        String[] columnNames = {"Rank", "Candidate", "Party", "Symbol", "Constituency", "Votes"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        
        int rank = 1;
        for (Candidate candidate : results) {
            model.addRow(new Object[]{
                rank++,
                candidate.getName(),
                candidate.getParty(),
                candidate.getSymbol(),
                candidate.getConstituency(),
                candidate.getVoteCount()
            });
        }
        
        JTable resultsTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(resultsTable);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createVotersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Registered Voters", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        List<Voter> voters = voterDAO.getAllVoters();
        
        String[] columnNames = {"Voter ID", "Name", "Email", "Aadhar", "Verified", "Voted"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        
        for (Voter voter : voters) {
            model.addRow(new Object[]{
                voter.getVoterId(),
                voter.getName(),
                voter.getEmail(),
                voter.getAadharNumber(),
                voter.isVerified() ? "Yes" : "No",
                voter.hasVoted() ? "Yes" : "No"
            });
        }
        
        JTable votersTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(votersTable);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
}