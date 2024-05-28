package com.apiBatch.repository;

import com.apiBatch.domain.BatchJobReservedEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Chamith
 */
@Repository
public interface BatchRepository extends JpaRepository<BatchJobReservedEntity, Integer> {
    List<BatchJobReservedEntity> findByJobNameAndJobChannel(String jobName, String jobChannel);

}
