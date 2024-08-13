package com.programming.techie.repository;

import com.programming.techie.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<User, Integer> {

    int deleteUserById(Integer id);
}
