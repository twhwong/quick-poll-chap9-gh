package com.apress.v3.controller;

import com.apress.domain.Poll;
import com.apress.dto.error.ErrorDetail;
import com.apress.exception.ResourceNotFoundException;
import com.apress.repository.PollRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

/**
 * 03-07-18
 *
 * @author Tom
 */

@RestController("pollControllerV3")
@RequestMapping({"/v3/", "/oauth2/v3/"})
@Api(value = "polls", description = "Poll API")  // a way to customize api name and description in swagger UI
public class PollController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PollRepository pollRepository;

    @ApiOperation(value = "Get all polls", response = Poll.class) // description of the method for swagger UI
    @RequestMapping(value = "/polls", method = RequestMethod.GET, produces = "application/json")
    // specifies what it produces, otherwise default */* will be shown in swagger UI
    public ResponseEntity<Page<Poll>> getAllPolls(Pageable pageable){
        // Listing 4-16, Book Spring Rest: allPolls are not used anymore ?!
        Page<Poll> allPolls = pollRepository.findAll(pageable);
        return new ResponseEntity<>(allPolls, HttpStatus.OK);
    }


    // @Valid instructs Spring to perform data validation after binding the user-submitted data.
    @RequestMapping(value="/polls", method = RequestMethod.POST)
    @ApiOperation(value = "Create a new Poll", response = Void.class, notes = "The newly created poll Id will be sent in the location response header")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Poll created succesfully", response = Void.class),
                            @ApiResponse(code = 500, message = "Error creating Poll", response = ErrorDetail.class)})
    public ResponseEntity<Poll> createPoll(@Valid @RequestBody Poll poll) {

        poll = pollRepository.save(poll);
        // Set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newPollUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(poll.getId())
                .toUri();
        responseHeaders.setLocation(newPollUri);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    protected void verifyPoll(Long pollId) throws ResourceNotFoundException {
        Poll poll = pollRepository.findById(pollId).orElse(null);
        if (poll == null) {
            // own custom exception here
            throw new ResourceNotFoundException("Poll with id " + pollId + " not found");
        }

    }

    @RequestMapping(value = "/polls/{pollId}", method = RequestMethod.GET)
    @ApiOperation(value = "Retrieve a Poll associated with the pollId", response = Poll.class)
    public ResponseEntity<Poll> getPoll(@PathVariable Long pollId) {
        // Check if the poll can be found, if not, do the error handling
        verifyPoll(pollId);

        // findOne is now findById
        Poll poll = pollRepository.findById(pollId).orElse(null);
        return new ResponseEntity<>(poll, HttpStatus.OK);

    }

    @RequestMapping(value = "/polls/{pollId}", method = RequestMethod.PUT)
    public ResponseEntity<Poll> updatePoll(@RequestBody Poll poll, @PathVariable Long pollId) {
        verifyPoll(pollId);
        // this updates the whole entity
        //Poll p = pollRepository.save(poll); // not ok, this creates a new one
        // Check first
        Poll currentPoll = pollRepository.findById(pollId).orElse(null);
        if (currentPoll == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        poll.setId(pollId); // set id first in order to update the existing object
        pollRepository.save(poll);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/polls/{pollId}", method = RequestMethod.DELETE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Poll deleted succesfully", response = Void.class),
            @ApiResponse(code = 500, message = "Error deleting Poll", response = ErrorDetail.class)})
    public ResponseEntity<Poll> deletePoll(@PathVariable Long pollId) {
        verifyPoll(pollId);
        pollRepository.deleteById(pollId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}

