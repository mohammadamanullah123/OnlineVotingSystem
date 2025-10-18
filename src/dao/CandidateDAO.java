package dao;

import model.Candidate;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CandidateDAO {
    
    public List<Candidate> getAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        String sql = "SELECT * FROM candidates";
        
        // DEBUG: Start
        System.out.println("DEBUG: Starting getAllCandidates()");
        System.out.println("DEBUG: SQL: " + sql);
        // DEBUG: End
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            // DEBUG: Connection successful
            System.out.println("DEBUG: Database connection successful");
            
            while (rs.next()) {
                Candidate candidate = new Candidate();
                candidate.setCandidateId(rs.getInt("candidate_id"));
                candidate.setName(rs.getString("name"));
                candidate.setParty(rs.getString("party"));
                candidate.setSymbol(rs.getString("symbol"));
                candidate.setConstituency(rs.getString("constituency"));
                candidates.add(candidate);
                
                // DEBUG: Each candidate loaded
                System.out.println("DEBUG: Loaded candidate - " + candidate.getName() + 
                                 " (" + candidate.getParty() + ")");
            }
            
            // DEBUG: Final count
            System.out.println("DEBUG: Total candidates loaded: " + candidates.size());
            
        } catch (SQLException e) {
            // DEBUG: Error
            System.out.println("DEBUG: ERROR loading candidates: " + e.getMessage());
            e.printStackTrace();
        }
        return candidates;
    }
    
    public boolean castVote(int voterId, int candidateId) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            // Check if already voted
            String checkVote = "SELECT has_voted FROM voters WHERE voter_id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkVote);
            checkStmt.setInt(1, voterId);
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next() && rs.getBoolean("has_voted")) {
                return false; // Already voted
            }
            
            // Insert vote
            String insertVote = "INSERT INTO votes (voter_id, candidate_id) VALUES (?, ?)";
            PreparedStatement voteStmt = conn.prepareStatement(insertVote);
            voteStmt.setInt(1, voterId);
            voteStmt.setInt(2, candidateId);
            voteStmt.executeUpdate();
            
            // Mark voter as voted
            String updateVoter = "UPDATE voters SET has_voted = TRUE WHERE voter_id = ?";
            PreparedStatement voterStmt = conn.prepareStatement(updateVoter);
            voterStmt.setInt(1, voterId);
            voterStmt.executeUpdate();
            
            conn.commit();
            return true;
            
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public List<Candidate> getVoteResults() {
        List<Candidate> results = new ArrayList<>();
        String sql = "SELECT c.candidate_id, c.name, c.party, c.symbol, c.constituency, " +
                    "COUNT(v.vote_id) as vote_count " +
                    "FROM candidates c LEFT JOIN votes v ON c.candidate_id = v.candidate_id " +
                    "GROUP BY c.candidate_id, c.name, c.party, c.symbol, c.constituency " +
                    "ORDER BY vote_count DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Candidate candidate = new Candidate();
                candidate.setCandidateId(rs.getInt("candidate_id"));
                candidate.setName(rs.getString("name"));
                candidate.setParty(rs.getString("party"));
                candidate.setSymbol(rs.getString("symbol"));
                candidate.setConstituency(rs.getString("constituency"));
                candidate.setVoteCount(rs.getInt("vote_count"));
                results.add(candidate);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }
}