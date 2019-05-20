package com.apress.client;

import com.apress.domain.Option;
import com.apress.domain.Poll;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 23-11-18
 *
 * @author Tom
 */
public class QuickPollClient {
    private static final String QUICK_POLL_URI_V1 = "http://localhost:8084/v1/polls";
    private RestTemplate restTemplate = new RestTemplate();

    public Poll getPollById(Long pollId) {
        return restTemplate.getForObject(QUICK_POLL_URI_V1 + "/{pollId}", Poll.class, pollId);
    }

    public List<Poll> getAllPolls() {

        ParameterizedTypeReference<List<Poll>> responseType = new ParameterizedTypeReference<List<Poll>>() {
        };
        ResponseEntity<List<Poll>> responseEntity = restTemplate.exchange(QUICK_POLL_URI_V1, HttpMethod.GET, null, responseType);
        List<Poll> allPolls = responseEntity.getBody();

        //Poll[] allPolls = restTemplate.getForEntity(QUICK_POLL_URI_V1, Poll[].class);

        return allPolls;
    }
    // create Poll
    public URI createPoll(Poll poll) {
        return restTemplate.postForLocation(QUICK_POLL_URI_V1, poll);
    }

    // update Poll
    public void updatePoll(Poll poll) {
        restTemplate.put(QUICK_POLL_URI_V1 + "/{pollId}", poll, poll.getId());
    }

    // Delete a Poll
    public void deletePoll(Long pollId) {
        restTemplate.delete(QUICK_POLL_URI_V1 + "/{pollId}", pollId);
    }
    // Delete all the Polls
    public void deleteAllPolls() {
        List<Poll> allPolls = getAllPolls();
        for (Poll p : allPolls) {
            System.out.println("Deleting poll id: " + p.getId());
            restTemplate.delete(QUICK_POLL_URI_V1 + "/{pollId}", p.getId());
        }
    }

    public static void main(String[] args) {
        QuickPollClient client = new QuickPollClient();

//        System.out.println("---- Delete all Polls first ----");
//        client.deleteAllPolls();


        System.out.println("---- Create a Poll ----");
        Poll newPoll = new Poll();
        newPoll.setQuestion("What is your favourite color ?");

        Set<Option> options = new HashSet<>();
        Option option1 = new Option();
        option1.setValue("Red");
        options.add(option1);

        Option option2 = new Option();
        option2.setValue("Blue");
        options.add(option2);

        newPoll.setOptions(options);
        URI pollLocation = client.createPoll(newPoll);
        System.out.println("Newly Created Poll Location " + pollLocation);
        //System.out.println("Poll id: " + pollLocation.getPath());


        System.out.println("---- Create a Poll ----");
        newPoll = new Poll();
        newPoll.setQuestion("What is your favourite pet ?");

        options = new HashSet<>();
        option1 = new Option();
        option1.setValue("Cat");
        options.add(option1);

        option2 = new Option();
        option2.setValue("Dog");
        options.add(option2);

        newPoll.setOptions(options);
        pollLocation = client.createPoll(newPoll);
        System.out.println("Newly Created Poll Location " + pollLocation);
        //System.out.println("Poll id: " + pollLocation.getPath());

        // get a Poll
        // How to get the id of the newly created Poll ?
        System.out.println("---- Get a Poll with id: " +  " ----");
        Poll poll = client.getPollById(43L);
        System.out.println(poll);

        // get all Polls
        System.out.println("---- Get all Polls ----");
        List<Poll> allPolls = client.getAllPolls();
        System.out.println(allPolls);

        //
        for (Poll p : allPolls) {
            System.out.println(p);
            System.out.println("--------------------");
        }


        System.out.println("---- Update a Poll ----");
        poll.setQuestion("What is your favourite colour ?");
        client.updatePoll(poll);
        System.out.println(poll);

        // Delete a Poll
        System.out.println("---- Delete Poll with id " + poll.getId() +" ----");
        client.deletePoll(poll.getId());
        for (Poll p : allPolls) {
            System.out.println(p);
            System.out.println("--------------------");
        }

    }

}
