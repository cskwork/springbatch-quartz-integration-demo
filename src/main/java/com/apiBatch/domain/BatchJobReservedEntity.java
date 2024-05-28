package com.apiBatch.domain;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.json.simple.JSONObject;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.apiBatch.domain.dto.BatchJobReservedDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "DP_BATCH_JOB_RESERVED")
public class BatchJobReservedEntity {
	@Id
	@Column(name = "id", length = 36)
	private String id;

	@PrePersist
	public void generateUUID() {
		if (id == null) {
			id = UUIDUtil.generateType1UUID().toString();
		}
	}

	@Column(name = "TASK_ID", length = 100)
	private String taskId;

	@Column(name = "JOB_TYPE", length = 100, columnDefinition = "TEXT COMMENT '잡 종류 (MANUAL - 수동 실행)'")
	private String jobType;

	@Column(name = "CRON_EXPRESSION", length = 100, columnDefinition = "TEXT COMMENT '잡 스케줄러 - 크론 표현식'")
	private String cronExpression;
	@Column(name = "CRON_EXPRESSION_MSG", length = 255, columnDefinition = "TEXT COMMENT '잡 스케줄러 - 크론 표현식 설명'")
	private String cronExpressionMsg;

	@Column(name = "JOB_CHANNEL", length = 100, columnDefinition = "TEXT COMMENT '잡 채널/분류'")
	private String jobChannel;
	@Column(name = "JOB_NAME", length = 255)
	private String jobName;
	@Column(name = "JOB_PARAMS", columnDefinition = "TEXT COMMENT '잡에서 사용하는 파라미터'")
	private String jobParams;

	@Column(name = "USE_YN", length = 1, columnDefinition = "CHAR(1) COMMENT '잡 활성화 여부 (Y 활성화, N 비활성화)'")
	private String useYn;

	@Column(name = "DISPLAY_YN", length = 1, columnDefinition = "CHAR(1) COMMENT '잡 화면 표시 여부 (Y 활성화, N 비활성화)'")
	private String displayYn;

	@CreatedDate
	@Column(name = "REG_DATE")
	private LocalDateTime regDate;
	@LastModifiedDate
	@Column(name = "MOD_DATE")
	private LocalDateTime modDate;

	@Column(name = "API_START_DATE", length = 100, columnDefinition = "API 배치 시작일'")
	private String apiStartDate;
	@Column(name = "API_END_DATE", length = 100, columnDefinition = "API 배치 종료일'")
	private String apiEndDate;

	@Column(name = "BATCH_STATUS", length = 100, columnDefinition = "TEXT COMMENT '배치 상태'")
	private String batchStatus;
	@Column(name = "BATCH_LAST_EXECUTED", columnDefinition = "TEXT COMMENT '배치 최근 실행 시간'")
	private String batchLastExecuted;
	@Column(name = "BATCH_TIME_TAKEN", length = 100, columnDefinition = "TEXT COMMENT '배치 작업 시간'")
	private String batchTimeTaken;
	@Column(name = "BATCH_RUN_SERVER", length = 100, columnDefinition = "TEXT COMMENT '배치가 실행된 서버'")
	private String batchRunServer;

	public static BatchJobReservedEntity toEntity(BatchJobReservedDTO batchJobDto) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		String jobParamsStr = null;

		if (batchJobDto.getJobParams() != null && !batchJobDto.getJobParams().isEmpty()) {
			jobParamsStr = mapper.writeValueAsString(batchJobDto.getJobParams());
		}

		return BatchJobReservedEntity.builder()
			.id(batchJobDto.getId())
			.taskId(batchJobDto.getTaskId())
			.jobType(batchJobDto.getType())
			.cronExpression(batchJobDto.getCronExpression())
			.jobChannel(batchJobDto.getJobChannel())
			.jobName(batchJobDto.getJobName())
			.jobParams(jobParamsStr)
			.useYn(batchJobDto.getUseYn())
			.cronExpressionMsg(batchJobDto.getCronExpressionMsg())
			.batchStatus(batchJobDto.getBatchStatus())
			.batchLastExecuted(batchJobDto.getBatchLastExecuted())
			.batchTimeTaken(batchJobDto.getBatchTimeTaken())
			.displayYn(batchJobDto.getDisplayYn())
			.build();
	}

	public static BatchJobReservedEntity toEntity(BatchJobReservedEntity batchJobReservedEntity, BatchJobReservedDTO batchJobDto) throws IOException {
		String jobParamsStr = null;

		if (batchJobDto.getJobParams() != null && !batchJobDto.getJobParams().isEmpty()) {
			ObjectMapper mapper = new ObjectMapper();
			jobParamsStr = mapper.writeValueAsString(batchJobDto.getJobParams());
		}

		batchJobReservedEntity.setCronExpression(batchJobDto.getCronExpression());
		batchJobReservedEntity.setCronExpressionMsg(batchJobDto.getCronExpressionMsg());

		batchJobReservedEntity.setJobType(batchJobDto.getJobType());
		batchJobReservedEntity.setJobChannel(batchJobDto.getJobChannel());
		batchJobReservedEntity.setJobName(batchJobDto.getJobName());

		batchJobReservedEntity.setJobParams(jobParamsStr);
		batchJobReservedEntity.setDisplayYn(batchJobDto.getDisplayYn());
		batchJobReservedEntity.setUseYn(batchJobDto.getUseYn());
		return batchJobReservedEntity;
	}

	public BatchJobReservedEntity(String id, String taskId, String type, String cronExpression,
		String jobChannel, String jobName, JSONObject jobParams,
		String useYn, String displayYn, LocalDateTime regDate, LocalDateTime modDate) {
		this.id = id;
		this.taskId = taskId;
		this.jobType = type;
		this.cronExpression = cronExpression;
		this.jobChannel = jobChannel;
		this.jobName = jobName;

		ObjectMapper mapper = new ObjectMapper();
		try {
			// Convert JSONObject to string representation for storage.
			if (jobParams != null) {
				this.jobParams = mapper.writeValueAsString(jobParams);
			}
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Error converting JSONObject to string.", e);
		}

		this.displayYn = displayYn;
		this.useYn = useYn;
		this.regDate = regDate;
		this.modDate = modDate;
	}

}

