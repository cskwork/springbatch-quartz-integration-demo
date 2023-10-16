package com.demo.apiBatch.domain.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class BatchJobInfoDTO {
    private int id;
    private String taskId;
    private String jobName;
    private String jobChannel;
    private String jobClass;
    private String cronExpression;
    private Long repeatTime;
    private Boolean isCronJob;
    private String jobParams;
}