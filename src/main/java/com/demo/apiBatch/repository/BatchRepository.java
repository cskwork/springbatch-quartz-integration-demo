package com.demo.apiBatch.repository;

import com.demo.apiBatch.domain.BatchJobInfo;
import com.demo.apiBatch.domain.dto.BatchJobInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;

import java.math.BigInteger;
import java.util.List;

/**
 * @author Chamith
 */
@Repository
public interface BatchRepository extends JpaRepository<BatchJobInfo, Integer> {
    List<BatchJobInfo> findByJobNameAndJobChannel(String jobName, String jobChannel);

}
