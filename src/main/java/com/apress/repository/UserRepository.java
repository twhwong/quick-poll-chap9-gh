package com.apress.repository;

import com.apress.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * 05-11-18
 *
 * @author Tom
 */
public interface UserRepository extends CrudRepository<User, Long> {
    public User findByUsername(String username);

}
