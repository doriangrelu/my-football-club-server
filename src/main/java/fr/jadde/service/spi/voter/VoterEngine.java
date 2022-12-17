package fr.jadde.service.spi.voter;

public interface VoterEngine {

    VoterEngine addVoter(Voter voter);

    VoterEngine removeVoter(Voter voter);

}
