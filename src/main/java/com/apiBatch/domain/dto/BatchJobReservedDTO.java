package com.apiBatch.domain.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.json.simple.JSONObject;

import com.apiBatch.domain.BatchJobReservedEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@ApiModel(description = "배치 등록")
public class BatchJobReservedDTO implements Serializable {

	@ApiModelProperty(value = "ID", example = "1b5e9c10-018e-1000-a00f-5f89b0796c00", required = false)
	private String id;

	@ApiModelProperty(value = "작업 ID", example = "1b5e9c10-018e-1000-a00f-5f89b0796c00", required = false)
	private String taskId;

	@ApiModelProperty(value = "잡 종류", example = "yearlyMonth", required = false)
	private String jobType;

	@ApiModelProperty(value = "잡 채널", example = "STT", required = false)
	private String jobChannel;

	@ApiModelProperty(value = "잡 이름", example = "allInsightBatchJob", required = false)
	private String jobName;

	@ApiModelProperty(value = "잡 파라미터", example = "{\"AUTO\":\"Y\",\"COLLECT\":\"N\",\"ANALYSIS\":\"Y\",\"STATISTIC\":\"Y\",\"collectionId\":\"N\","
		+ "\"channelId\":\"NINE\"}", required = false)
	private JSONObject jobParams;

	@ApiModelProperty(value = "잡 사용 여부", example = "Y", required = false)
	private String useYn;

	@ApiModelProperty(value = "잡 화면 표시 여부", example = "Y", required = false)
	private String displayYn;

	@ApiModelProperty(value = "잡 자동/수동 실행 여부", example = "Y", required = false)
	private String AUTO;

	@ApiModelProperty(value = "등록일", example = "2024-03-08 09:02:49.000", required = false)
	private LocalDateTime regDate;

	@ApiModelProperty(value = "수정일", example = "2024-03-08 09:02:49.000", required = false)
	private LocalDateTime modDate;

	@ApiModelProperty(value = "잡 실행 범위 : 시작일", example = "2024-01-01", required = false)
	private String apiStartDate;

	@ApiModelProperty(value = "잡 실행 범위 : 종료일", example = "2024-01-05", required = false)
	private String apiEndDate;

	@ApiModelProperty(value = "잡 실행 상태", example = "COMPLETED", required = false)
	private String batchStatus;

	@ApiModelProperty(value = "배치 최근 실행 시간", example = "2024-03-12 16:06:01.000", required = false)
	private String batchLastExecuted;

	@ApiModelProperty(value = "배치 소요 시간", example = "[0]시간 [1]분 [26.651]초 ", required = false)
	private String batchTimeTaken;

	@ApiModelProperty(value = "배치 실행 주최/서버", example = "DESKTOP-41K4BMT/10.200.0.160", required = false)
	private String batchRunServer;

	@ApiModelProperty(value = "배치 스케줄 표현식", example = "0 0/30 * 1/1 * ? *", required = false)
	private String cronExpression;

	@ApiModelProperty(value = "배치 스케줄 다음 실행 일정", example = "[Tue Apr 02 17:00:00 KST 2024, Tue Apr 02 19:00:00 KST 2024]", required = false)
	private String cronExpressionMsg;

	// CRON TIME
	@ApiModelProperty(value = "크론 종류", example = "매일 every , 시작 시간 지정 startAt , 매일 everyDay , 주중만 everyWeekDay  , 매월 everyMonth, 월 지정 dayMonth , 매년 "
		+ "월 지정 yearlyMonth, 매년", required = false)
	private String type;
	@ApiModelProperty(value = "크론 탭 - 주기", example = "매 시간 hourly, 매일 daily, 매주 weekly, 매월 monthly, 매년 yearly", required = false)
	private String menu;
	@ApiModelProperty(value = "크론 - 매 N 분", example = "매 N 분", required = false)
	private Long minutes;
	@ApiModelProperty(value = "크론 - 매 N 시간", example = "매 N 시간", required = false)
	private Long hourly;
	@ApiModelProperty(value = "크론 - N 개월 마다", example = "N 개월 마다", required = false)
	private Long monthly;
	@ApiModelProperty(value = "크론 - 일별", example = "MON,TUE,WED,THU,FRI,SAT,SUN", required = false)
	private String days;
	@ApiModelProperty(value = "크론 - N 일 마다", example = "N 일 마다", required = false)
	private Long dayNo;
	@ApiModelProperty(value = "크론 - N 개월 마다", example = "N 개월 마다", required = false)
	private Long month;
	@ApiModelProperty(value = "크론 - N 번째 주", example = "N 번째 주", required = false)
	private Long weekNo;
	@ApiModelProperty(value = "크론 - 요일", example = "Monday ~ Sunday N 요일", required = false)
	private String dayName;
	@ApiModelProperty(value = "크론 - 월", example = "January ~ December N 월", required = false)
	private String monthName;
	@ApiModelProperty(value = "크론 - 시간", example = "00:00", required = false)
	private String time;
	@ApiModelProperty(value = "크론", example = "5", required = false)
	private int timeout;
	// /CRON TIME

	public static BatchJobReservedDTO fromEntity(BatchJobReservedEntity batchJob) {
		JSONObject jobParamsMap = new JSONObject();
		String jobParam = batchJob.getJobParams();

		try {
			ObjectMapper mapper = new ObjectMapper();
			if (jobParam != null && !jobParam.isEmpty()) {
				jobParamsMap = mapper.readValue(jobParam, JSONObject.class);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			log.error(jobParam);
		}

		return BatchJobReservedDTO.builder()
			.id(batchJob.getId())
			.taskId(batchJob.getTaskId())
			.jobType(batchJob.getJobType())
			.cronExpression(batchJob.getCronExpression())
			.cronExpressionMsg(batchJob.getCronExpressionMsg())
			.jobChannel(batchJob.getJobChannel())
			.jobName(batchJob.getJobName())
			.jobParams(jobParamsMap)
			.batchRunServer(batchJob.getBatchRunServer())
			.useYn(batchJob.getUseYn())
			.displayYn(batchJob.getDisplayYn())
			.apiStartDate(batchJob.getApiStartDate())
			.apiEndDate(batchJob.getApiEndDate())
			.build();
	}
}