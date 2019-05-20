package com.apress.client;

import com.apress.domain.Poll;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 27-11-18
 *
 * @author Tom
 */
public class QuickPollClientV2 {
    /*
    In version 2 there is option to select pages, size and do sorting.
     */

    private static final String QUICK_POLL_URI_2 =  "http://localhost:8084/v2/polls";
    private RestTemplate restTemplate = new RestTemplate();

    public PageWrapper<Poll> getAllPolls(int page, int size) {
        ParameterizedTypeReference<PageWrapper<Poll>> responseType = new ParameterizedTypeReference<PageWrapper<Poll>>() {};
        UriComponentsBuilder builder = UriComponentsBuilder
                                        .fromHttpUrl(QUICK_POLL_URI_2)
                                        .queryParam("page", page)
                                        .queryParam("size", size);

        ResponseEntity<PageWrapper<Poll>> responseEntity = restTemplate.exchange(builder.build().toUri()
                                                                   , HttpMethod.GET, null, responseType);
        return responseEntity.getBody();
    }


    // test
    public static void main(String[] args) {
        QuickPollClientV2 clientV2 = new QuickPollClientV2();

        // Get all polls per page and size
        PageWrapper<Poll> allPolls = clientV2.getAllPolls(1, 2);

        System.out.println(allPolls);


    }
}
