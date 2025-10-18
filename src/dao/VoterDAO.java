package dao;

import model.Voter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VoterDAO {
    
    public boolean registerVoter(Voter voter) {
        String sql = "INSERT INTO voters (name, email, password, aadhar_number) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, voter.getName());
            pstmt.setString(2, voter.getEmail());
            pstmt.setString(3, voter.getPassword());
            pstmt.setString(4, voter.getAadharNumber());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Voter authenticateVoter(String email, String password) {
        String sql = "SELECT * FROM voters WHERE email = ? AND password = ? AND is_verified = TRUE";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Voter voter = new Voter();
                voter.setVoterId(rs.getInt("voter_id"));
                voter.setName(rs.getString("name"));
                voter.setEmail(rs.getString("email"));
                voter.setPassword(rs.getString("password"));
                voter.setAadharNumber(rs.getString("aadhar_number"));
                voter.setVerified(rs.getBoolean("is_verified"));
                voter.setHasVoted(rs.getBoolean("has_voted"));
                return voter;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean hasVoted(int voterId) {
        String sql = "SELECT has_voted FROM voters WHERE voter_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, voterId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getBoolean("has_voted");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean markVoted(int voterId) {
        String sql = "UPDATE voters SET has_voted = TRUE WHERE voter_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, voterId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Voter> getAllVoters() {
        List<Voter> voters = new ArrayList<>();
        String sql = "SELECT * FROM voters";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Voter voter = new Voter();
                voter.setVoterId(rs.getInt("voter_id"));
                voter.setName(rs.getString("name"));
                voter.setEmail(rs.getString("email"));
                voter.setAadharNumber(rs.getString("aadhar_number"));
                voter.setVerified(rs.getBoolean("is_verified"));
                voter.setHasVoted(rs.getBoolean("has_voted"));
                voters.add(voter);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return voters;
    }
}