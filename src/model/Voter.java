package model;

public class Voter {
    private int voterId;
    private String name;
    private String email;
    private String password;
    private String aadharNumber;
    private boolean isVerified;
    private boolean hasVoted;
    
    // Constructors
    public Voter() {}
    
    public Voter(String name, String email, String password, String aadharNumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.aadharNumber = aadharNumber;
        this.isVerified = false;
        this.hasVoted = false;
    }
    
    // Getters and Setters
    public int getVoterId() { return voterId; }
    public void setVoterId(int voterId) { this.voterId = voterId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getAadharNumber() { return aadharNumber; }
    public void setAadharNumber(String aadharNumber) { this.aadharNumber = aadharNumber; }
    
    public boolean isVerified() { return isVerified; }
    public void setVerified(boolean verified) { isVerified = verified; }
    
    public boolean hasVoted() { return hasVoted; }
    public void setHasVoted(boolean hasVoted) { this.hasVoted = hasVoted; }
}