package com.apiQuartz.service;

import com.apiBatch.domain.BatchJobReservedEntity;
import com.apiBatch.domain.dto.BatchJobInfoDTO;

import java.util.List;

/**
 */
public interface QuartzService {

    List<BatchJobReservedEntity> readJobInfoList();
    int assignSchedulesInDB();
    int addScheduledJob(BatchJobReservedEntity jobInfo);
    int updateScheduledJob(BatchJobReservedEntity jobInfo);
    int deleteScheduledJob(BatchJobInfoDTO jobInfo);

    boolean runJobNow(BatchJobInfoDTO jobInfo);
    boolean pauseJob(BatchJobInfoDTO jobInfo);
    boolean resumeJob(BatchJobInfoDTO jobInfo);
    boolean unScheduleJob(String jobName);

}
