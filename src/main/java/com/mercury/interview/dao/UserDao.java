package com.mercury.interview.dao;

import com.mercury.interview.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.logging.Filter;

public interface UserDao extends JpaRepository<User,Integer> {
    User findByUsername(String username);
    // Pageable pageable, Filter filter
}
