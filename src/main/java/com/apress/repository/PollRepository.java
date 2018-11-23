package com.apress.repository;

import com.apress.domain.Poll;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 03-07-18
 *
 * @author Tom
 */
public interface PollRepository extends PagingAndSortingRepository<Poll, Long> {
}
