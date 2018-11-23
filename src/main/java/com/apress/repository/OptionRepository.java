package com.apress.repository;

import com.apress.domain.Option;
import org.springframework.data.repository.CrudRepository;

/**
 * 03-07-18
 *
 * @author Tom
 */
public interface OptionRepository extends CrudRepository<Option, Long> {
}
