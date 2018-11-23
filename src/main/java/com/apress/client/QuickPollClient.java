package com.apress.client;

import com.apress.domain.Poll;
import org.springframework.web.client.RestTemplate;

/**
 * 23-11-18
 *
 * @author Tom
 */
public class QuickPollClient {
    private static final String QUICK_POLL_URI_V1 = "http://localhost:8084/v1/polls\n";
    private RestTemplate restTemplate = new RestTemplate();

    public Poll getPollById(Long pollId) {
        return restTemplate.getForObject(QUICK_POLL_URI_V1 + "/{pollId}", Poll.class, pollId);
    }



//    public static void main(String[] args) {
//        QuickPollClient client = new QuickPollClient();
//        Poll poll = client.getPollById(1L);
//        System.out.println(poll);
//    }

}
