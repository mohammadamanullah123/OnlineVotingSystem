package model;

public class Candidate {
    private int candidateId;
    private String name;
    private String party;
    private String symbol;
    private String constituency;
    private int voteCount;
    
    // Constructors
    public Candidate() {}
    
    public Candidate(String name, String party, String symbol, String constituency) {
        this.name = name;
        this.party = party;
        this.symbol = symbol;
        this.constituency = constituency;
    }
    
    // Getters and Setters
    public int getCandidateId() { return candidateId; }
    public void setCandidateId(int candidateId) { this.candidateId = candidateId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getParty() { return party; }
    public void setParty(String party) { this.party = party; }
    
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    
    public String getConstituency() { return constituency; }
    public void setConstituency(String constituency) { this.constituency = constituency; }
    
    public int getVoteCount() { return voteCount; }
    public void setVoteCount(int voteCount) { this.voteCount = voteCount; }
}
