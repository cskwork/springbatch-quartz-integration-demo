package com.demo.apiBatch.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 */
@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Table(name = "DP_BATCH_JOB_RESERVED")
public class BatchJobInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cron_expression")
    private String cronExpression;

    @Column(name = "is_cron_job")
    private Boolean isCronJob;

    @Column(name = "job_channel")
    private String jobChannel;

    @Column(name = "job_class")
    private String jobClass;

    @Column(name = "job_name")
    private String jobName;

    @Column(name = "job_params", columnDefinition="TEXT")
    private String jobParams;

    @Column(name="repeat_time")
    private Long repeatTime;

    @Column(name="task_id")
    private String taskId;

    @CreatedDate
    @Column(name = "regDate")
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "modDate")
    private LocalDateTime modDate;


    @Builder
    public BatchJobInfo(String jobClass, String jobChannel, String cronExpression, String jobName, Long repeatTime, boolean isCronJob, String jobParams){
        this.jobName = jobName;
        this.jobChannel = jobChannel;
        this.jobClass = jobClass;
        this.cronExpression = cronExpression;
        this.repeatTime = repeatTime;
        this.isCronJob= isCronJob;
        this.jobParams = jobParams;
    }

}
