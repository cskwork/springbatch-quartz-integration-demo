package com.apiQuartz.listener;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerListener;

@Slf4j
public class QuartzTriggerListener implements TriggerListener {
    public static final String EXECUTION_COUNT = "EXECUTION_COUNT";

    public String getName() {
        return QuartzTriggerListener.class.getName();
    }

    /**
     * Trigger가 시작된 상태
     * 리스너 중에서 가장 먼저 시작됨
     */
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        log.info(String.format("[%s][%s]", "트리거 시작", trigger.getKey().toString()));
    }

    /**
     * Trigger 중단 여부를 확인하는 메소드
     * Job을 수행하기 전 상태
     *
     * 반환값이 false인 경우, Job 수행
     * 반환값이 true인 경우, Job을 수행하지않고 'SchedulerListtener.jobExecutionVetoed'로 넘어감
     *
     * Job 시작횟수가 3회이상이면 작업중단
     */
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        JobDataMap map = context.getJobDetail().getJobDataMap();
        int executeCount = -1;
        if (map.containsKey(EXECUTION_COUNT)) {
            executeCount = map.getInt(EXECUTION_COUNT);
        }
        log.info(String.format("[%s][%s]", "트리거 확인", trigger.getKey().toString()));

        return executeCount >= 3;
    }

    /**
     * Trigger가 중단된 상태
     */
    public void triggerMisfired(Trigger trigger) {
        log.info(String.format("[%s][%s]", "트리거 중단", trigger.getKey().toString()));
    }

    /**
     * Trigger가 완료된 상태
     */
    public void triggerComplete(Trigger trigger, JobExecutionContext context, CompletedExecutionInstruction triggerInstructionCode) {
        log.info(String.format("[%s][%s]", "트리거 완료", trigger.getKey().toString()));
    }
}