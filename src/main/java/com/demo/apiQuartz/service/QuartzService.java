package com.demo.apiQuartz.service;

import com.demo.apiBatch.domain.BatchJobInfo;
import com.demo.apiBatch.domain.dto.BatchJobInfoDTO;

import java.util.List;

/**
 */
public interface QuartzService {

    List<BatchJobInfo> readJobInfoList();
    int assignSchedulesInDB();
    int addScheduledJob(BatchJobInfo jobInfo);
    int updateScheduledJob(BatchJobInfo jobInfo);
    int deleteScheduledJob(BatchJobInfoDTO jobInfo);

    boolean runJobNow(BatchJobInfoDTO jobInfo);
    boolean pauseJob(BatchJobInfoDTO jobInfo);
    boolean resumeJob(BatchJobInfoDTO jobInfo);
    boolean unScheduleJob(String jobName);

}
