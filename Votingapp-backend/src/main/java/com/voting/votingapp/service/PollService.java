package com.voting.votingapp.service;

import com.voting.votingapp.model.OptionVote;
import com.voting.votingapp.model.Poll;
import com.voting.votingapp.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PollService {

    @Autowired
    private PollRepository pollRepository;

    public Poll createPoll(Poll poll) {
        return pollRepository.save(poll);
    }

    public List<Poll> getAllPolls() {
        return pollRepository.findAll();
    }

    public Optional<Poll> getPollById(Long id) {
        return pollRepository.findById(id);
    }

    public void vote(Long pollId, int optionIndex) {

        //Getting poll from DB
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(()->new RuntimeException("Poll not found"));

        //Getting all options
        List<OptionVote> options=poll.getOptions();

        //For invalid index
        if(optionIndex<0 || optionIndex>= options.size())
            throw new IllegalArgumentException("Invalid Index");

        //Get selected option
        OptionVote selectedOption = options.get(optionIndex);

        //Increment vote for selected option
        selectedOption.setVoteCount(selectedOption.getVoteCount() +1);

        //saving incremented option in database
        pollRepository.save(poll);
    }
}
