package com.apress.repository;

import com.apress.domain.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * 03-07-18
 *
 * @author Tom
 */
public interface VoteRepository extends CrudRepository<Vote, Long> {


    // get all votes for a given poll
    @Query(value = "select v.* from Option o, Vote v where o.poll_id = ?1 " +
            "and v.option_id = o.option_id", nativeQuery = true)
    public Iterable<Vote> findByPoll(Long pollId);

}
