package com.apiQuartz.service.impl;

import com.apiQuartz.service.QuartzService;
import com.apiQuartz.service.util.QuartzJobScheduleCreator;
import com.apiBatch.domain.BatchJobReservedEntity;
import com.apiQuartz.component.QuartzBatchAdapter;
import com.apiBatch.domain.dto.BatchJobInfoDTO;
import com.apiBatch.repository.BatchRepository;
import com.apiQuartz.service.util.QuartzServiceUtil;
import com.apiWebResponse.DefaultException;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

/**
 * @author Chamith
 */
@Slf4j
@Service
@Transactional
public class QuartzServiceImpl implements QuartzService {
    static final String JOB_NAME = "jobName";
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    @Autowired
    private EntityManager entityManager;

    @Autowired
    QuartzServiceUtil quartzServiceUtil;

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private QuartzJobScheduleCreator scheduleCreator;

    @Autowired
    private QuartzBatchAdapter quartzBatchAdapter;

    /**
     * INIT SCHEDULER
     *
     * Add Batch Job Schedule - Job (Batch) -> JobDetail (Quartz)
     * Code for processing Each jobInfo
     */
    @Override
    public int assignSchedulesInDB() {
        // Get Spring Batch Job
        List<BatchJobReservedEntity> jobInfoList = batchRepository.findAll();
        Scheduler scheduler = null;

        // Init All Jobs
        try {
            scheduler = schedulerFactoryBean.getScheduler();
            for (String groupName : scheduler.getJobGroupNames()) {  // Get all job keys from the group
                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                    scheduler.deleteJob(jobKey);  // Delete the job and all its associated triggers.
                }
            }
            scheduler.clear();
            log.error("INIT ALL SCHEDULES");
        } catch (SchedulerException e) {
            log.error("Error occurred while trying to delete jobs", e);
        }

        if (!jobInfoList.isEmpty()) {
            scheduler = schedulerFactoryBean.getScheduler();
            log.info("job Info List" + jobInfoList.size());
            for (BatchJobReservedEntity jobInfo : jobInfoList) {
                try {
                    JobDetail jobDetail = quartzServiceUtil.createJobDetail(jobInfo); // Make Job Detail
                    Trigger trigger = quartzServiceUtil.createTrigger(jobInfo);                    // Make Trigger
                    if(trigger != null){
                         quartzServiceUtil.createSchedule(jobDetail, trigger, scheduler);               // Schedule Job
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
//        try {
//            schedulerFactoryBean.getScheduler().start();
//        } catch (SchedulerException e) {
//            throw new RuntimeException(e);
//        }
        return jobInfoList.size();
    }

    @Override
    public  List<BatchJobReservedEntity> readJobInfoList(){
        return batchRepository.findAll();
    }
    /**
     * addNewJobSchedule
     * @param jobInfo
     * @return
     */
    @Override
    public int addScheduledJob(BatchJobReservedEntity jobInfo) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            try {
                JobDetail jobDetail = quartzServiceUtil.createJobDetail(jobInfo); // Make Job Detail
                Trigger trigger = quartzServiceUtil.createTrigger(jobInfo);                    // Make Trigger
                quartzServiceUtil.createSchedule(jobDetail, trigger, scheduler);               // Schedule Job
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new DefaultException(e.getMessage());
            }
            List<BatchJobReservedEntity> dbJobInfoList = batchRepository.findByJobNameAndJobChannel(jobInfo.getJobName(), jobInfo.getJobChannel());
            if(dbJobInfoList.isEmpty()) {
                batchRepository.save(jobInfo);
                log.info("Added");
                return 1;
            }
            throw new DefaultException("DUPLICATE JOBNAME : "+ jobInfo.getJobChannel()+"-"+jobInfo.getJobName());
        }catch(Exception e) {
            log.error(e.getMessage(), e);
            throw new DefaultException(e.getMessage());
        }
    }
    @Override
    public int updateScheduledJob(BatchJobReservedEntity jobInfo) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            try {
                JobDetail jobDetail = quartzServiceUtil.createJobDetail(jobInfo); // Make Job Detail
                Trigger trigger = quartzServiceUtil.createTrigger(jobInfo);                    // Make Trigger
                quartzServiceUtil.createSchedule(jobDetail, trigger, scheduler);               // Schedule Job
            } catch (Exception e) {
                throw new DefaultException(e.getMessage());
            }
            List<BatchJobReservedEntity> dbJobInfoList = batchRepository.findByJobNameAndJobChannel(jobInfo.getJobName(), jobInfo.getJobChannel());
            if(!dbJobInfoList.isEmpty()) {
                batchRepository.save(jobInfo);
                log.info("updated");
                return 1;
            }
            throw new DefaultException("DUPLICATE JOBNAME : "+ jobInfo.getJobChannel()+"-"+jobInfo.getJobName());
        }catch(Exception e) {
            log.error(e.getMessage(), e);
            throw new DefaultException(e.getMessage());
        }
    }
    @Override
    public int deleteScheduledJob(BatchJobInfoDTO jobInfo) {
        try {
            schedulerFactoryBean.getScheduler().deleteJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobChannel()));
        } catch (SchedulerException e) {
            log.error("Failed to delete job - {} - {}", jobInfo.getJobChannel(), jobInfo.getJobName(), e);
            throw new DefaultException(e.getMessage());
        }
        return 1;
    }


    @Override
    public boolean runJobNow(BatchJobInfoDTO jobInfo) {
        try {
            schedulerFactoryBean.getScheduler().triggerJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobChannel()));
            return true;
        } catch (SchedulerException e) {
            log.error("Failed to start new job - {}", jobInfo.getJobName(), e);
            throw new DefaultException(e.getMessage());
        }
    }
    @Override
    public boolean pauseJob(BatchJobInfoDTO jobInfo) {
        try {
            schedulerFactoryBean.getScheduler().pauseJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobChannel()));
            return true;
        } catch (SchedulerException e) {
            log.error("Failed to pause job - {}", jobInfo.getJobName(), e);
            throw new DefaultException(e.getMessage());
        }
    }
    @Override
    public boolean resumeJob(BatchJobInfoDTO jobInfo) {
        try {
            schedulerFactoryBean.getScheduler().resumeJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobChannel()));
            return true;
        } catch (SchedulerException e) {
            log.error("Failed to resume job - {}", jobInfo.getJobName(), e);
            throw new DefaultException(e.getMessage());
        }
    }
    @Override
    public boolean unScheduleJob(String jobName) {
        try {
            return schedulerFactoryBean.getScheduler().unscheduleJob(new TriggerKey(jobName));
        } catch (SchedulerException e) {
            log.error("Failed to un-schedule job - {}", jobName, e);
            throw new DefaultException(e.getMessage());
        }
    }
}
