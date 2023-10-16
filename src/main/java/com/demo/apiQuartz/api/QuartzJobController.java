package com.demo.apiQuartz.api;


import com.demo.apiBatch.domain.BatchJobInfo;
import com.demo.apiBatch.domain.dto.BatchJobInfoDTO;
import com.demo.apiQuartz.service.QuartzService;
import com.demo.apiWebResponse.domain.ApiResponse;
import com.demo.apiWebResponse.domain.enums.StatusEnum;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author csk
 */
@RestController
@NoArgsConstructor(force = true)
@RequestMapping("/batch")
public class QuartzJobController {
    @Autowired
    private final QuartzService schedulerService;

    @PostMapping("/assignSchedulesInDB")
    public ResponseEntity<ApiResponse> assignSchedulesInDB() {
        int addAllCount = schedulerService.assignSchedulesInDB();
        return new ResponseEntity<>(
                new ApiResponse(
                    StatusEnum.OK,
                    "TOTAL BATCH JOBS ADDED",
                    addAllCount
                ), HttpStatus.OK);
    }

    @GetMapping("/scheduledJob")
    public ResponseEntity<ApiResponse> readJobInfoList() {
        return new ResponseEntity<>(
                new ApiResponse(
                        StatusEnum.OK,
                        "BATCH JOB LIST",
                        schedulerService.readJobInfoList()
                ), HttpStatus.OK);
    }

    @PostMapping("/addScheduledJob")
    public ResponseEntity<ApiResponse> scheduleNewJob(@RequestBody BatchJobInfo jobInfo) {
        int addJobCount = schedulerService.addScheduledJob(jobInfo);
        return new ResponseEntity<>(
                new ApiResponse(
                        StatusEnum.OK,
                        "BATCH JOB ADDED",
                        addJobCount
                ), HttpStatus.OK);
    }

    @PostMapping("/updateScheduledJob")
    public ResponseEntity<ApiResponse> updateScheduleJob(@RequestBody BatchJobInfo jobInfo) {
        int updateJobCount = schedulerService.updateScheduledJob(jobInfo);
        return new ResponseEntity<>(
                new ApiResponse(
                        StatusEnum.OK,
                        "BATCH JOB UPDATED",
                        updateJobCount
                ), HttpStatus.OK);
    }

    @DeleteMapping("/deleteScheduledJob")
    public ResponseEntity<ApiResponse> deleteJob(@RequestBody BatchJobInfoDTO jobInfo) {
        int deleteJobCount =  schedulerService.deleteScheduledJob(jobInfo);
        return new ResponseEntity<>(
                new ApiResponse(
                        StatusEnum.OK,
                        "BATCH JOB DELETED",
                        deleteJobCount
                ), HttpStatus.OK);
    }


    @DeleteMapping("/unScheduleJob/{jobName}")
    public boolean unScheduleJob(@PathVariable String jobName) {
        return schedulerService.unScheduleJob(jobName);
    }


    @PostMapping("/pauseJob")
    public boolean pauseJob(@RequestBody BatchJobInfoDTO jobInfo) {
        return schedulerService.pauseJob(jobInfo);
    }

    @PostMapping("/resumeJob")
    public boolean resumeJob(@RequestBody BatchJobInfoDTO jobInfo) {
        return schedulerService.resumeJob(jobInfo);
    }

    @PostMapping("/runJobNow")
    public boolean startJobNow(@RequestBody BatchJobInfoDTO jobInfo) {
        return schedulerService.runJobNow(jobInfo);
    }
}
