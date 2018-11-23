package com.apress.v1.controller;

import com.apress.domain.Vote;
import com.apress.repository.VoteRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * 05-07-18
 *
 * @author Tom
 */
@RestController("voteControllerV1")
@RequestMapping("/v1/")
@Api(value = "votes", description = "Vote API")
public class VoteController {

    @Autowired
    private VoteRepository voteRepository;

    @RequestMapping(value = "/polls/{pollId}/votes", method = RequestMethod.POST)
    public ResponseEntity<Vote> createVote(@PathVariable Long pollId, @RequestBody Vote vote){

        vote = voteRepository.save(vote);
        // set the header for the created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newVoteUri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                                 .path("/{id}").buildAndExpand(vote.getId())
                                 .toUri();
        responseHeaders.setLocation(newVoteUri);
        return new ResponseEntity<Vote>(null, responseHeaders, HttpStatus.CREATED);

    }

    // get all votes of a given poll
    @RequestMapping(value = "/polls/{pollId}/votes", method = RequestMethod.GET)
    public Iterable<Vote> getAllVotes(@PathVariable Long pollId) {
        return voteRepository.findByPoll(pollId);
    }


}
