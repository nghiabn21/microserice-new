package com.programming.techie.repo;

import com.programming.techie.entity.FailureRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FailureRepository extends JpaRepository<FailureRecord, Integer> {

    List<FailureRecord> findByStatus(String status);

}
