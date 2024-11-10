package com.voting.votingapp.controller;


import com.voting.votingapp.model.Poll;
import com.voting.votingapp.request.Vote;
import com.voting.votingapp.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/poll")
public class PollController {

    @Autowired
    private PollService pollService;

    @PostMapping("/create")
    public Poll createPoll(@RequestBody Poll poll){
        return pollService.createPoll(poll);
    }

    @GetMapping("/get")
    public List<Poll> getAllPoll(){
        return pollService.getAllPolls();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Poll> getPoll(@PathVariable Long id){
        return pollService.getPollById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/vote")
    public void vote(@RequestBody Vote vote){
        pollService.vote(vote.getPollId(),vote.getOptionIndex());
    }
}
