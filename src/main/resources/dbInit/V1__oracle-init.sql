
-- DP_BATCH_QRTZ (SPRING QUARTZ TABLE ALL)
CREATE TABLE DP_BATCH_QRTZ_JOB_DETAILS
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    JOB_NAME  VARCHAR2(200) NOT NULL,
    JOB_GROUP VARCHAR2(200) NOT NULL,
    DESCRIPTION VARCHAR2(250) NULL,
    JOB_CLASS_NAME   VARCHAR2(250) NOT NULL,
    IS_DURABLE VARCHAR2(1) NOT NULL,
    IS_NONCONCURRENT VARCHAR2(1) NOT NULL,
    IS_UPDATE_DATA VARCHAR2(1) NOT NULL,
    REQUESTS_RECOVERY VARCHAR2(1) NOT NULL,
    JOB_DATA BLOB NULL,
    CONSTRAINT DP_BATCH_QRTZ_JOB_DETAILS_PK PRIMARY KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
);
COMMENT ON COLUMN DP_BATCH_QRTZ_JOB_DETAILS.SCHED_NAME IS '스케줄러의 이름';
COMMENT ON COLUMN DP_BATCH_QRTZ_JOB_DETAILS.JOB_NAME IS '작업의 이름';
COMMENT ON COLUMN DP_BATCH_QRTZ_JOB_DETAILS.JOB_GROUP IS '작업이 속한 그룹';
COMMENT ON COLUMN DP_BATCH_QRTZ_JOB_DETAILS.DESCRIPTION IS '작업에 대한 설명';
COMMENT ON COLUMN DP_BATCH_QRTZ_JOB_DETAILS.JOB_CLASS_NAME IS '작업을 실행하는 클래스의 이름';
COMMENT ON COLUMN DP_BATCH_QRTZ_JOB_DETAILS.IS_DURABLE IS '작업이 내구성 있는지 여부 (내구성 있는 작업은 스케줄러가 실행되지 않는 동안에도 삭제되지 않음)';
COMMENT ON COLUMN DP_BATCH_QRTZ_JOB_DETAILS.IS_NONCONCURRENT IS '작업이 동시에 실행되지 않아야 하는지 여부';
COMMENT ON COLUMN DP_BATCH_QRTZ_JOB_DETAILS.IS_UPDATE_DATA IS '다음 작업 실행 시 작업 데이터(JobDataMap)를 갱신할지 여부';
COMMENT ON COLUMN DP_BATCH_QRTZ_JOB_DETAILS.REQUESTS_RECOVERY IS '작업이 실패한 후 복구를 요청하는지 여부';
COMMENT ON COLUMN DP_BATCH_QRTZ_JOB_DETAILS.JOB_DATA IS '작업 실행 시 사용될 데이터를 포함하는 BLOB';


CREATE TABLE DP_BATCH_QRTZ_TRIGGERS
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    JOB_NAME  VARCHAR2(200) NOT NULL,
    JOB_GROUP VARCHAR2(200) NOT NULL,
    DESCRIPTION VARCHAR2(250) NULL,
    NEXT_FIRE_TIME NUMBER(13) NULL,
    PREV_FIRE_TIME NUMBER(13) NULL,
    PRIORITY NUMBER(13) NULL,
    TRIGGER_STATE VARCHAR2(16) NOT NULL,
    TRIGGER_TYPE VARCHAR2(8) NOT NULL,
    START_TIME NUMBER(13) NOT NULL,
    END_TIME NUMBER(13) NULL,
    CALENDAR_NAME VARCHAR2(200) NULL,
    MISFIRE_INSTR NUMBER(2) NULL,
    JOB_DATA BLOB NULL,
    CONSTRAINT DP_BATCH_QRTZ_TRIGGERS_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    CONSTRAINT DP_BATCH_QRTZ_TRIGGERS_TO_JOBS_FK FOREIGN KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
      REFERENCES DP_BATCH_QRTZ_JOB_DETAILS(SCHED_NAME,JOB_NAME,JOB_GROUP)
);
COMMENT ON COLUMN DP_BATCH_QRTZ_TRIGGERS.SCHED_NAME IS '스케줄러의 이름';
COMMENT ON COLUMN DP_BATCH_QRTZ_TRIGGERS.TRIGGER_NAME IS '트리거의 이름';
COMMENT ON COLUMN DP_BATCH_QRTZ_TRIGGERS.TRIGGER_GROUP IS '트리거가 속한 그룹';
COMMENT ON COLUMN DP_BATCH_QRTZ_TRIGGERS.JOB_NAME IS '트리거에 의해 실행될 작업의 이름';
COMMENT ON COLUMN DP_BATCH_QRTZ_TRIGGERS.JOB_GROUP IS '작업이 속한 그룹';
COMMENT ON COLUMN DP_BATCH_QRTZ_TRIGGERS.DESCRIPTION IS '트리거에 대한 설명';
COMMENT ON COLUMN DP_BATCH_QRTZ_TRIGGERS.NEXT_FIRE_TIME IS '다음에 트리거가 발동될 예정인 시간';
COMMENT ON COLUMN DP_BATCH_QRTZ_TRIGGERS.PREV_FIRE_TIME IS '트리거가 마지막으로 발동된 시간';
COMMENT ON COLUMN DP_BATCH_QRTZ_TRIGGERS.PRIORITY IS '트리거의 우선순위';
COMMENT ON COLUMN DP_BATCH_QRTZ_TRIGGERS.TRIGGER_STATE IS '현재 트리거의 상태';
COMMENT ON COLUMN DP_BATCH_QRTZ_TRIGGERS.TRIGGER_TYPE IS '트리거의 타입';
COMMENT ON COLUMN DP_BATCH_QRTZ_TRIGGERS.START_TIME IS '트리거의 시작 시간';
COMMENT ON COLUMN DP_BATCH_QRTZ_TRIGGERS.END_TIME IS '트리거의 종료 시간';
COMMENT ON COLUMN DP_BATCH_QRTZ_TRIGGERS.CALENDAR_NAME IS '트리거와 관련된 캘린더의 이름';
COMMENT ON COLUMN DP_BATCH_QRTZ_TRIGGERS.MISFIRE_INSTR IS '트리거의 미발동 지시어';
COMMENT ON COLUMN DP_BATCH_QRTZ_TRIGGERS.JOB_DATA IS '트리거 실행 시 전달될 데이터';


CREATE TABLE DP_BATCH_QRTZ_SIMPLE_TRIGGERS
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    REPEAT_COUNT NUMBER(7) NOT NULL,
    REPEAT_INTERVAL NUMBER(12) NOT NULL,
    TIMES_TRIGGERED NUMBER(10) NOT NULL,
    CONSTRAINT DP_BATCH_QRTZ_SIMPLE_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    CONSTRAINT DP_BATCH_QRTZ_SIMPLE_TRIG_TO_TRIG_FK FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
	REFERENCES DP_BATCH_QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);
COMMENT ON COLUMN DP_BATCH_QRTZ_SIMPLE_TRIGGERS.SCHED_NAME IS '스케줄러의 이름';
COMMENT ON COLUMN DP_BATCH_QRTZ_SIMPLE_TRIGGERS.TRIGGER_NAME IS '단순 트리거의 이름';
COMMENT ON COLUMN DP_BATCH_QRTZ_SIMPLE_TRIGGERS.TRIGGER_GROUP IS '단순 트리거가 속한 그룹';
COMMENT ON COLUMN DP_BATCH_QRTZ_SIMPLE_TRIGGERS.REPEAT_COUNT IS '트리거가 반복될 총 횟수';
COMMENT ON COLUMN DP_BATCH_QRTZ_SIMPLE_TRIGGERS.REPEAT_INTERVAL IS '트리거 실행 간 반복 간격 (밀리세컨드)';
COMMENT ON COLUMN DP_BATCH_QRTZ_SIMPLE_TRIGGERS.TIMES_TRIGGERED IS '현재까지 트리거가 실행된 횟수';


CREATE TABLE DP_BATCH_QRTZ_CRON_TRIGGERS
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    CRON_EXPRESSION VARCHAR2(120) NOT NULL,
    TIME_ZONE_ID VARCHAR2(80),
    CONSTRAINT DP_BATCH_QRTZ_CRON_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    CONSTRAINT DP_BATCH_QRTZ_CRON_TRIG_TO_TRIG_FK FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
      REFERENCES DP_BATCH_QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);
COMMENT ON COLUMN DP_BATCH_QRTZ_CRON_TRIGGERS.SCHED_NAME IS '스케줄러의 이름';
COMMENT ON COLUMN DP_BATCH_QRTZ_CRON_TRIGGERS.TRIGGER_NAME IS 'CRON 트리거의 이름';
COMMENT ON COLUMN DP_BATCH_QRTZ_CRON_TRIGGERS.TRIGGER_GROUP IS 'CRON 트리거가 속한 그룹';
COMMENT ON COLUMN DP_BATCH_QRTZ_CRON_TRIGGERS.CRON_EXPRESSION IS 'CRON 트리거의 실행 시간을 정의하는 CRON 표현식';
COMMENT ON COLUMN DP_BATCH_QRTZ_CRON_TRIGGERS.TIME_ZONE_ID IS 'CRON 표현식의 시간대 식별자';

CREATE TABLE DP_BATCH_QRTZ_SIMPROP_TRIGGERS
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    STR_PROP_1 VARCHAR2(512) NULL,
    STR_PROP_2 VARCHAR2(512) NULL,
    STR_PROP_3 VARCHAR2(512) NULL,
    INT_PROP_1 NUMBER(10) NULL,
    INT_PROP_2 NUMBER(10) NULL,
    LONG_PROP_1 NUMBER(13) NULL,
    LONG_PROP_2 NUMBER(13) NULL,
    DEC_PROP_1 NUMERIC(13,4) NULL,
    DEC_PROP_2 NUMERIC(13,4) NULL,
    BOOL_PROP_1 VARCHAR2(1) NULL,
    BOOL_PROP_2 VARCHAR2(1) NULL,
    CONSTRAINT DP_BATCH_QRTZ_SIMPROP_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    CONSTRAINT DP_BATCH_QRTZ_SIMPROP_TRIG_TO_TRIG_FK FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
      REFERENCES DP_BATCH_QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);
COMMENT ON COLUMN DP_BATCH_QRTZ_SIMPROP_TRIGGERS.SCHED_NAME IS '스케줄러의 이름';
COMMENT ON COLUMN DP_BATCH_QRTZ_SIMPROP_TRIGGERS.TRIGGER_NAME IS '트리거의 이름';
COMMENT ON COLUMN DP_BATCH_QRTZ_SIMPROP_TRIGGERS.TRIGGER_GROUP IS '트리거가 속한 그룹';
COMMENT ON COLUMN DP_BATCH_QRTZ_SIMPROP_TRIGGERS.STR_PROP_1 IS '트리거에 사용되는 문자열 속성 1';
COMMENT ON COLUMN DP_BATCH_QRTZ_SIMPROP_TRIGGERS.STR_PROP_2 IS '트리거에 사용되는 문자열 속성 2';
COMMENT ON COLUMN DP_BATCH_QRTZ_SIMPROP_TRIGGERS.STR_PROP_3 IS '트리거에 사용되는 문자열 속성 3';
COMMENT ON COLUMN DP_BATCH_QRTZ_SIMPROP_TRIGGERS.INT_PROP_1 IS '트리거에 사용되는 정수 속성 1';
COMMENT ON COLUMN DP_BATCH_QRTZ_SIMPROP_TRIGGERS.INT_PROP_2 IS '트리거에 사용되는 정수 속성 2';
COMMENT ON COLUMN DP_BATCH_QRTZ_SIMPROP_TRIGGERS.LONG_PROP_1 IS '트리거에 사용되는 긴 정수 속성 1';
COMMENT ON COLUMN DP_BATCH_QRTZ_SIMPROP_TRIGGERS.LONG_PROP_2 IS '트리거에 사용되는 긴 정수 속성 2';
COMMENT ON COLUMN DP_BATCH_QRTZ_SIMPROP_TRIGGERS.DEC_PROP_1 IS '트리거에 사용되는 십진 속성 1';
COMMENT ON COLUMN DP_BATCH_QRTZ_SIMPROP_TRIGGERS.DEC_PROP_2 IS '트리거에 사용되는 십진 속성 2';
COMMENT ON COLUMN DP_BATCH_QRTZ_SIMPROP_TRIGGERS.BOOL_PROP_1 IS '트리거에 사용되는 불린 속성 1';
COMMENT ON COLUMN DP_BATCH_QRTZ_SIMPROP_TRIGGERS.BOOL_PROP_2 IS '트리거에 사용되는 불린 속성 2';


CREATE TABLE DP_BATCH_QRTZ_BLOB_TRIGGERS
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    BLOB_DATA BLOB NULL,
    CONSTRAINT DP_BATCH_QRTZ_BLOB_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    CONSTRAINT DP_BATCH_QRTZ_BLOB_TRIG_TO_TRIG_FK FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
        REFERENCES DP_BATCH_QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);
COMMENT ON COLUMN DP_BATCH_QRTZ_BLOB_TRIGGERS.SCHED_NAME IS '스케줄러의 이름';
COMMENT ON COLUMN DP_BATCH_QRTZ_BLOB_TRIGGERS.TRIGGER_NAME IS 'BLOB 데이터를 가진 트리거의 이름';
COMMENT ON COLUMN DP_BATCH_QRTZ_BLOB_TRIGGERS.TRIGGER_GROUP IS 'BLOB 데이터를 가진 트리거가 속한 그룹';
COMMENT ON COLUMN DP_BATCH_QRTZ_BLOB_TRIGGERS.BLOB_DATA IS '트리거와 관련된 BLOB 형식의 데이터';


CREATE TABLE DP_BATCH_QRTZ_CALENDARS
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    CALENDAR_NAME  VARCHAR2(200) NOT NULL,
    CALENDAR BLOB NOT NULL,
    CONSTRAINT DP_BATCH_QRTZ_CALENDARS_PK PRIMARY KEY (SCHED_NAME,CALENDAR_NAME)
);
COMMENT ON COLUMN DP_BATCH_QRTZ_CALENDARS.SCHED_NAME IS '스케줄러의 이름';
COMMENT ON COLUMN DP_BATCH_QRTZ_CALENDARS.CALENDAR_NAME IS '캘린더의 고유한 이름';
COMMENT ON COLUMN DP_BATCH_QRTZ_CALENDARS.CALENDAR IS '캘린더 데이터를 저장하는 BLOB 객체, Quartz가 정의한 포맷을 따름';

CREATE TABLE DP_BATCH_QRTZ_PAUSED_TRIGGER_GRPS
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    TRIGGER_GROUP  VARCHAR2(200) NOT NULL,
    CONSTRAINT DP_BATCH_QRTZ_PAUSED_TRIG_GRPS_PK PRIMARY KEY (SCHED_NAME,TRIGGER_GROUP)
);
COMMENT ON COLUMN DP_BATCH_QRTZ_PAUSED_TRIGGER_GRPS.SCHED_NAME IS '스케줄러의 이름';
COMMENT ON COLUMN DP_BATCH_QRTZ_PAUSED_TRIGGER_GRPS.TRIGGER_GROUP IS '일시 중단된 트리거 그룹의 이름';

CREATE TABLE DP_BATCH_QRTZ_FIRED_TRIGGERS
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    ENTRY_ID VARCHAR2(95) NOT NULL,
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    INSTANCE_NAME VARCHAR2(200) NOT NULL,
    FIRED_TIME NUMBER(13) NOT NULL,
    SCHED_TIME NUMBER(13) NOT NULL,
    PRIORITY NUMBER(13) NOT NULL,
    STATE VARCHAR2(16) NOT NULL,
    JOB_NAME VARCHAR2(200) NULL,
    JOB_GROUP VARCHAR2(200) NULL,
    IS_NONCONCURRENT VARCHAR2(1) NULL,
    REQUESTS_RECOVERY VARCHAR2(1) NULL,
    CONSTRAINT DP_BATCH_QRTZ_FIRED_TRIGGER_PK PRIMARY KEY (SCHED_NAME,ENTRY_ID)
);
COMMENT ON COLUMN DP_BATCH_QRTZ_FIRED_TRIGGERS.SCHED_NAME IS '스케줄러의 이름';
COMMENT ON COLUMN DP_BATCH_QRTZ_FIRED_TRIGGERS.ENTRY_ID IS '트리거 실행에 대한 고유 식별자';
COMMENT ON COLUMN DP_BATCH_QRTZ_FIRED_TRIGGERS.TRIGGER_NAME IS '트리거의 이름';
COMMENT ON COLUMN DP_BATCH_QRTZ_FIRED_TRIGGERS.TRIGGER_GROUP IS '트리거가 속한 그룹';
COMMENT ON COLUMN DP_BATCH_QRTZ_FIRED_TRIGGERS.INSTANCE_NAME IS '트리거를 실행한 Quartz 인스턴스의 이름';
COMMENT ON COLUMN DP_BATCH_QRTZ_FIRED_TRIGGERS.FIRED_TIME IS '트리거가 실행된 정확한 시간(밀리세컨드)';
COMMENT ON COLUMN DP_BATCH_QRTZ_FIRED_TRIGGERS.SCHED_TIME IS '트리거가 예정된 예상 실행 시간(밀리세컨드)';
COMMENT ON COLUMN DP_BATCH_QRTZ_FIRED_TRIGGERS.PRIORITY IS '트리거의 우선순위';
COMMENT ON COLUMN DP_BATCH_QRTZ_FIRED_TRIGGERS.STATE IS '트리거의 현재 상태';
COMMENT ON COLUMN DP_BATCH_QRTZ_FIRED_TRIGGERS.JOB_NAME IS '연관된 잡의 이름';
COMMENT ON COLUMN DP_BATCH_QRTZ_FIRED_TRIGGERS.JOB_GROUP IS '연관된 잡이 속한 그룹';
COMMENT ON COLUMN DP_BATCH_QRTZ_FIRED_TRIGGERS.IS_NONCONCURRENT IS '트리거가 동시에 실행될 수 없는지 여부';
COMMENT ON COLUMN DP_BATCH_QRTZ_FIRED_TRIGGERS.REQUESTS_RECOVERY IS '실패한 잡이 복구를 요청하는지의 여부';


CREATE TABLE DP_BATCH_QRTZ_SCHEDULER_STATE
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    INSTANCE_NAME VARCHAR2(200) NOT NULL,
    LAST_CHECKIN_TIME NUMBER(13) NOT NULL,
    CHECKIN_INTERVAL NUMBER(13) NOT NULL,
    CONSTRAINT DP_BATCH_QRTZ_SCHEDULER_STATE_PK PRIMARY KEY (SCHED_NAME,INSTANCE_NAME)
);
COMMENT ON COLUMN DP_BATCH_QRTZ_SCHEDULER_STATE.SCHED_NAME IS '스케줄러의 이름으로, 여러 스케줄러 인스턴스 간 구분에 사용';
COMMENT ON COLUMN DP_BATCH_QRTZ_SCHEDULER_STATE.INSTANCE_NAME IS '스케줄러 인스턴스의 고유한 이름';
COMMENT ON COLUMN DP_BATCH_QRTZ_SCHEDULER_STATE.LAST_CHECKIN_TIME IS '해당 인스턴스가 마지막으로 체크인한 시간(밀리세컨드)';
COMMENT ON COLUMN DP_BATCH_QRTZ_SCHEDULER_STATE.CHECKIN_INTERVAL IS '체크인 간의 시간 간격(밀리세컨드)';

CREATE TABLE DP_BATCH_QRTZ_LOCKS
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    LOCK_NAME  VARCHAR2(40) NOT NULL,
    CONSTRAINT DP_BATCH_QRTZ_LOCKS_PK PRIMARY KEY (SCHED_NAME,LOCK_NAME)
);
COMMENT ON COLUMN DP_BATCH_QRTZ_LOCKS.SCHED_NAME IS '스케줄러의 이름, 클러스터 내의 다른 스케줄러 인스턴스와 구분하기 위해 사용';
COMMENT ON COLUMN DP_BATCH_QRTZ_LOCKS.LOCK_NAME IS '잠금의 이름, 특정한 잠금을 식별하기 위해 사용';

CREATE INDEX IDX_DP_BATCH_QRTZ_J_REQ_RECOVERY ON DP_BATCH_QRTZ_JOB_DETAILS(SCHED_NAME,REQUESTS_RECOVERY);
CREATE INDEX IDX_DP_BATCH_QRTZ_J_GRP ON DP_BATCH_QRTZ_JOB_DETAILS(SCHED_NAME,JOB_GROUP);

CREATE INDEX IDX_DP_BATCH_QRTZ_T_J ON DP_BATCH_QRTZ_TRIGGERS(SCHED_NAME,JOB_NAME,JOB_GROUP);
CREATE INDEX IDX_DP_BATCH_QRTZ_T_JG ON DP_BATCH_QRTZ_TRIGGERS(SCHED_NAME,JOB_GROUP);
CREATE INDEX IDX_DP_BATCH_QRTZ_T_C ON DP_BATCH_QRTZ_TRIGGERS(SCHED_NAME,CALENDAR_NAME);
CREATE INDEX IDX_DP_BATCH_QRTZ_T_G ON DP_BATCH_QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_GROUP);
CREATE INDEX IDX_DP_BATCH_QRTZ_T_STATE ON DP_BATCH_QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_STATE);
CREATE INDEX IDX_DP_BATCH_QRTZ_T_N_STATE ON DP_BATCH_QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP,TRIGGER_STATE);
CREATE INDEX IDX_DP_BATCH_QRTZ_T_N_G_STATE ON DP_BATCH_QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_GROUP,TRIGGER_STATE);
CREATE INDEX IDX_DP_BATCH_QRTZ_T_NEXT_FIRE_TIME ON DP_BATCH_QRTZ_TRIGGERS(SCHED_NAME,NEXT_FIRE_TIME);
CREATE INDEX IDX_DP_BATCH_QRTZ_T_NFT_ST ON DP_BATCH_QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_STATE,NEXT_FIRE_TIME);
CREATE INDEX IDX_DP_BATCH_QRTZ_T_NFT_MISFIRE ON DP_BATCH_QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME);
CREATE INDEX IDX_DP_BATCH_QRTZ_T_NFT_ST_MISFIRE ON DP_BATCH_QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_STATE);
CREATE INDEX IDX_DP_BATCH_QRTZ_T_NFT_ST_MISFIRE_GRP ON DP_BATCH_QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_GROUP,TRIGGER_STATE);

CREATE INDEX IDX_DP_BATCH_QRTZ_FT_TRIG_INST_NAME ON DP_BATCH_QRTZ_FIRED_TRIGGERS(SCHED_NAME,INSTANCE_NAME);
CREATE INDEX IDX_DP_BATCH_QRTZ_FT_INST_JOB_REQ_RCVRY ON DP_BATCH_QRTZ_FIRED_TRIGGERS(SCHED_NAME,INSTANCE_NAME,REQUESTS_RECOVERY);
CREATE INDEX IDX_DP_BATCH_QRTZ_FT_J_G ON DP_BATCH_QRTZ_FIRED_TRIGGERS(SCHED_NAME,JOB_NAME,JOB_GROUP);
CREATE INDEX IDX_DP_BATCH_QRTZ_FT_JG ON DP_BATCH_QRTZ_FIRED_TRIGGERS(SCHED_NAME,JOB_GROUP);
CREATE INDEX IDX_DP_BATCH_QRTZ_FT_T_G ON DP_BATCH_QRTZ_FIRED_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP);
CREATE INDEX IDX_DP_BATCH_QRTZ_FT_TG ON DP_BATCH_QRTZ_FIRED_TRIGGERS(SCHED_NAME,TRIGGER_GROUP);

-- DP_BATCH_JOB_RESERVED
CREATE TABLE DP_BATCH_JOB_RESERVED (
  ID VARCHAR2(36) NOT NULL,
  TASK_ID varchar2(255) NOT NULL,
  JOB_TYPE varchar2(100) DEFAULT NULL,
  CRON_EXPRESSION varchar2(100) DEFAULT NULL,
  CRON_EXPRESSION_MSG clob,
  JOB_CHANNEL varchar2(100) DEFAULT NULL,
  JOB_NAME varchar2(255) DEFAULT NULL,
  JOB_PARAMS clob,
  USE_YN varchar2(1) DEFAULT NULL,
  DISPLAY_YN varchar2(1) DEFAULT NULL,
  REG_DATE timestamp(0) DEFAULT NULL,
  MOD_DATE timestamp(0) DEFAULT NULL,
  BATCH_STATUS varchar2(100) DEFAULT NULL,
  BATCH_LAST_EXECUTED timestamp(0) DEFAULT NULL,
  BATCH_TIME_TAKEN varchar2(100) DEFAULT NULL,
  BATCH_RUN_SERVER varchar2(100) DEFAULT NULL,
  API_START_DATE varchar2(50) DEFAULT NULL,
  API_END_DATE varchar2(50) DEFAULT NULL,
  PRIMARY KEY (ID)
);
COMMENT ON COLUMN DP_BATCH_JOB_RESERVED.ID IS '고유 식별자';
COMMENT ON COLUMN DP_BATCH_JOB_RESERVED.TASK_ID IS '작업 식별자';
COMMENT ON COLUMN DP_BATCH_JOB_RESERVED.JOB_TYPE IS '잡의 타입';
COMMENT ON COLUMN DP_BATCH_JOB_RESERVED.CRON_EXPRESSION IS 'Cron 스케줄 표현식';
COMMENT ON COLUMN DP_BATCH_JOB_RESERVED.CRON_EXPRESSION_MSG IS 'Cron 표현식 실행 날짜';
COMMENT ON COLUMN DP_BATCH_JOB_RESERVED.JOB_CHANNEL IS '잡이 실행되는 채널';
COMMENT ON COLUMN DP_BATCH_JOB_RESERVED.JOB_NAME IS '잡의 이름';
COMMENT ON COLUMN DP_BATCH_JOB_RESERVED.JOB_PARAMS IS '잡 파라미터';
COMMENT ON COLUMN DP_BATCH_JOB_RESERVED.USE_YN IS '사용 여부(Y 또는 N)';
COMMENT ON COLUMN DP_BATCH_JOB_RESERVED.DISPLAY_YN IS '화면 표시 여부(Y 또는 N)';
COMMENT ON COLUMN DP_BATCH_JOB_RESERVED.REG_DATE IS '등록 시각';
COMMENT ON COLUMN DP_BATCH_JOB_RESERVED.MOD_DATE IS '수정 시각';
COMMENT ON COLUMN DP_BATCH_JOB_RESERVED.BATCH_STATUS IS '배치 작업의 상태';
COMMENT ON COLUMN DP_BATCH_JOB_RESERVED.BATCH_LAST_EXECUTED IS '마지막으로 실행된 배치 작업 시각';
COMMENT ON COLUMN DP_BATCH_JOB_RESERVED.BATCH_TIME_TAKEN IS '배치 작업 수행 시간';
COMMENT ON COLUMN DP_BATCH_JOB_RESERVED.BATCH_RUN_SERVER IS '배치 작업을 실행한 서버';
COMMENT ON COLUMN DP_BATCH_JOB_RESERVED.API_START_DATE IS 'API 실행 범위 시작 일자';
COMMENT ON COLUMN DP_BATCH_JOB_RESERVED.API_END_DATE IS 'API 실행 범위 종료 일자';

-- DP_BATCH (SPRING BATCH TABLE ALL)
CREATE TABLE DP_BATCH_JOB_INSTANCE  (
	JOB_INSTANCE_ID NUMBER(19,0)  NOT NULL PRIMARY KEY ,
	VERSION NUMBER(19,0) ,
	JOB_NAME VARCHAR2(100 char) NOT NULL,
	JOB_KEY VARCHAR2(32 char) NOT NULL,
	constraint JOB_INST_UN unique (JOB_NAME, JOB_KEY)
) SEGMENT CREATION IMMEDIATE;
COMMENT ON COLUMN DP_BATCH_JOB_INSTANCE.JOB_INSTANCE_ID IS 'DP_BATCH_JOB_INSTANCE 테이블의 PK';
COMMENT ON COLUMN DP_BATCH_JOB_INSTANCE.VERSION IS '락에 사용되는 레코드 버전';
COMMENT ON COLUMN DP_BATCH_JOB_INSTANCE.JOB_NAME IS '수행한 Batch Job Name';
COMMENT ON COLUMN DP_BATCH_JOB_INSTANCE.JOB_KEY IS '잡 이름과 잡 파라미터 해시 값으로, JobInstance를 고유하게 식별하는데 사용하는 값';


CREATE TABLE DP_BATCH_JOB_EXECUTION  (
	JOB_EXECUTION_ID NUMBER(19,0)  NOT NULL PRIMARY KEY ,
	VERSION NUMBER(19,0)  ,
	JOB_INSTANCE_ID NUMBER(19,0) NOT NULL,
	CREATE_TIME TIMESTAMP NOT NULL,
	START_TIME TIMESTAMP DEFAULT NULL ,
	END_TIME TIMESTAMP DEFAULT NULL ,
	STATUS VARCHAR2(10 char) ,
	EXIT_CODE VARCHAR2(2500 char) ,
	EXIT_MESSAGE VARCHAR2(2500 char) ,
	LAST_UPDATED TIMESTAMP,
	JOB_CONFIGURATION_LOCATION VARCHAR(2500 char) NULL,
	constraint JOB_INST_EXEC_FK foreign key (JOB_INSTANCE_ID)
	references DP_BATCH_JOB_INSTANCE(JOB_INSTANCE_ID)
) SEGMENT CREATION IMMEDIATE;
COMMENT ON COLUMN DP_BATCH_JOB_EXECUTION.JOB_EXECUTION_ID IS 'PK';
COMMENT ON COLUMN DP_BATCH_JOB_EXECUTION.VERSION IS '락에 사용되는 레코드 버전';
COMMENT ON COLUMN DP_BATCH_JOB_EXECUTION.JOB_INSTANCE_ID IS 'BATCH_JOB_INSTANCE 참조하는 외래키';
COMMENT ON COLUMN DP_BATCH_JOB_EXECUTION.CREATE_TIME IS '레코드 생성된 시간';
COMMENT ON COLUMN DP_BATCH_JOB_EXECUTION.START_TIME IS '잡 실행이 시작된 시간';
COMMENT ON COLUMN DP_BATCH_JOB_EXECUTION.END_TIME IS '잡 실행이 완료된 시간';
COMMENT ON COLUMN DP_BATCH_JOB_EXECUTION.STATUS IS '잡 실행의 배치 상태';
COMMENT ON COLUMN DP_BATCH_JOB_EXECUTION.EXIT_CODE IS '잡 실행의 종료코드';
COMMENT ON COLUMN DP_BATCH_JOB_EXECUTION.EXIT_MESSAGE IS 'EXIT_CODE와 관련된 메세지나 스택 트레이스';
COMMENT ON COLUMN DP_BATCH_JOB_EXECUTION.LAST_UPDATED IS '레코드가 마지막으로 갱신된 시간';

CREATE TABLE DP_BATCH_JOB_EXECUTION_PARAMS  (
	JOB_EXECUTION_ID NUMBER(19,0) NOT NULL ,
	TYPE_CD VARCHAR2(6 char) NOT NULL ,
	KEY_NAME VARCHAR2(100 char) NOT NULL ,
	STRING_VAL VARCHAR2(250 char) ,
	DATE_VAL TIMESTAMP DEFAULT NULL ,
	LONG_VAL NUMBER(19,0) ,
	DOUBLE_VAL NUMBER ,
	IDENTIFYING CHAR(1) NOT NULL ,
	constraint JOB_EXEC_PARAMS_FK foreign key (JOB_EXECUTION_ID)
	references DP_BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) SEGMENT CREATION IMMEDIATE;
COMMENT ON COLUMN DP_BATCH_JOB_EXECUTION_PARAMS.JOB_EXECUTION_ID IS 'PK';
COMMENT ON COLUMN DP_BATCH_JOB_EXECUTION_PARAMS.TYPE_CD IS '파라미터 값의 타입을 나타내는 문자열';
COMMENT ON COLUMN DP_BATCH_JOB_EXECUTION_PARAMS.KEY_NAME IS '파라미터 이름';
COMMENT ON COLUMN DP_BATCH_JOB_EXECUTION_PARAMS.STRING_VAL IS '타입이 String인 경우 파라미터의 값';
COMMENT ON COLUMN DP_BATCH_JOB_EXECUTION_PARAMS.DATE_VAL IS '타입이 Date인 경우 파라미터의 값';
COMMENT ON COLUMN DP_BATCH_JOB_EXECUTION_PARAMS.LONG_VAL IS '타입이 Long인 경우 파라미터의 값';
COMMENT ON COLUMN DP_BATCH_JOB_EXECUTION_PARAMS.DOUBLE_VAL IS '타입이 Double인 경우 파라미터의 값';
COMMENT ON COLUMN DP_BATCH_JOB_EXECUTION_PARAMS.IDENTIFYING IS '파라미터가 식별되는지 여부를 나타내는 플래그';


CREATE TABLE DP_BATCH_JOB_EXECUTION_CONTEXT  (
	JOB_EXECUTION_ID NUMBER(19,0) NOT NULL PRIMARY KEY,
	SHORT_CONTEXT VARCHAR2(2500 char) NOT NULL,
	SERIALIZED_CONTEXT CLOB ,
	constraint JOB_EXEC_CTX_FK foreign key (JOB_EXECUTION_ID)
	references DP_BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) SEGMENT CREATION IMMEDIATE;
COMMENT ON COLUMN DP_BATCH_JOB_EXECUTION_CONTEXT.JOB_EXECUTION_ID IS 'PK';
COMMENT ON COLUMN DP_BATCH_JOB_EXECUTION_CONTEXT.SHORT_CONTEXT IS 'Trim처리된 SERIALIZED_CONTEXT';
COMMENT ON COLUMN DP_BATCH_JOB_EXECUTION_CONTEXT.SERIALIZED_CONTEXT IS '직렬화된 ExecutionContext';

CREATE SEQUENCE DP_BATCH_STEP_EXECUTION_SEQ START WITH 0 MINVALUE 0 MAXVALUE 9223372036854775807 NOCYCLE;
CREATE SEQUENCE DP_BATCH_JOB_EXECUTION_SEQ START WITH 0 MINVALUE 0 MAXVALUE 9223372036854775807 NOCYCLE;
CREATE SEQUENCE DP_BATCH_JOB_SEQ START WITH 0 MINVALUE 0 MAXVALUE 9223372036854775807 NOCYCLE;

CREATE TABLE DP_BATCH_STEP_EXECUTION  (
	STEP_EXECUTION_ID NUMBER(19,0)  NOT NULL PRIMARY KEY ,
	VERSION NUMBER(19,0) NOT NULL,
	STEP_NAME VARCHAR2(100 char) NOT NULL,
	JOB_EXECUTION_ID NUMBER(19,0) NOT NULL,
	START_TIME TIMESTAMP NOT NULL ,
	END_TIME TIMESTAMP DEFAULT NULL ,
	STATUS VARCHAR2(10 char) ,
	COMMIT_COUNT NUMBER(19,0) ,
	READ_COUNT NUMBER(19,0) ,
	FILTER_COUNT NUMBER(19,0) ,
	WRITE_COUNT NUMBER(19,0) ,
	READ_SKIP_COUNT NUMBER(19,0) ,
	WRITE_SKIP_COUNT NUMBER(19,0) ,
	PROCESS_SKIP_COUNT NUMBER(19,0) ,
	ROLLBACK_COUNT NUMBER(19,0) ,
	EXIT_CODE VARCHAR2(2500 char) ,
	EXIT_MESSAGE VARCHAR2(2500 char) ,
	LAST_UPDATED TIMESTAMP,
	constraint JOB_EXEC_STEP_FK foreign key (JOB_EXECUTION_ID)
	references DP_BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) SEGMENT CREATION IMMEDIATE;

COMMENT ON COLUMN DP_BATCH_STEP_EXECUTION.STEP_EXECUTION_ID IS 'PK';
COMMENT ON COLUMN DP_BATCH_STEP_EXECUTION.VERSION IS '락에 사용되는 레코드의 버전';
COMMENT ON COLUMN DP_BATCH_STEP_EXECUTION.STEP_NAME IS '스텝 이름';
COMMENT ON COLUMN DP_BATCH_STEP_EXECUTION.JOB_EXECUTION_ID IS 'BATCH_JOB_EXECUTION을 참조하는 외래키';
COMMENT ON COLUMN DP_BATCH_STEP_EXECUTION.START_TIME IS '스텝 실행 시작 시간';
COMMENT ON COLUMN DP_BATCH_STEP_EXECUTION.END_TIME IS '스텝 실행 완료 시간';
COMMENT ON COLUMN DP_BATCH_STEP_EXECUTION.STATUS IS '스텝의 배치 상태';
COMMENT ON COLUMN DP_BATCH_STEP_EXECUTION.COMMIT_COUNT IS '스텝 실행중 커밋된 트랜잭션 수';
COMMENT ON COLUMN DP_BATCH_STEP_EXECUTION.READ_COUNT IS '읽은 아이템 수';
COMMENT ON COLUMN DP_BATCH_STEP_EXECUTION.FILTER_COUNT IS '아이템 프로세서가 null을 반환해 필터링된 아이템 수';
COMMENT ON COLUMN DP_BATCH_STEP_EXECUTION.WRITE_COUNT IS '기록된 아이템 수';
COMMENT ON COLUMN DP_BATCH_STEP_EXECUTION.READ_SKIP_COUNT IS 'ItemReader내에서 예외가 발생해 건너뛴 아이템 수';
COMMENT ON COLUMN DP_BATCH_STEP_EXECUTION.WRITE_SKIP_COUNT IS 'ItemProcessor 내에서 예외가 발생해 건너뛴 아이템 수';
COMMENT ON COLUMN DP_BATCH_STEP_EXECUTION.PROCESS_SKIP_COUNT IS 'ItemWriter 내에서 예외가 발생해 건너뛴 아이템 수';
COMMENT ON COLUMN DP_BATCH_STEP_EXECUTION.ROLLBACK_COUNT IS '스텝 실행에서 롤백된 트랜잭션 수';
COMMENT ON COLUMN DP_BATCH_STEP_EXECUTION.EXIT_CODE IS '스텝의 종료코드';
COMMENT ON COLUMN DP_BATCH_STEP_EXECUTION.EXIT_MESSAGE IS '스텝 실행에서 반환된 메세지나 스택 트레이스';
COMMENT ON COLUMN DP_BATCH_STEP_EXECUTION.LAST_UPDATED IS '레코드가 마지막으로 업데이트 된 시간';

CREATE TABLE DP_BATCH_STEP_EXECUTION_CONTEXT  (
	STEP_EXECUTION_ID NUMBER(19,0) NOT NULL PRIMARY KEY,
	SHORT_CONTEXT VARCHAR2(2500 char) NOT NULL,
	SERIALIZED_CONTEXT CLOB ,
	constraint STEP_EXEC_CTX_FK foreign key (STEP_EXECUTION_ID)
	references DP_BATCH_STEP_EXECUTION(STEP_EXECUTION_ID)
) SEGMENT CREATION IMMEDIATE;
COMMENT ON COLUMN DP_BATCH_STEP_EXECUTION_CONTEXT.STEP_EXECUTION_ID IS 'PK';
COMMENT ON COLUMN DP_BATCH_STEP_EXECUTION_CONTEXT.SHORT_CONTEXT IS 'Trim처리된 SERIALIZED_CONTEXT';
COMMENT ON COLUMN DP_BATCH_STEP_EXECUTION_CONTEXT.SERIALIZED_CONTEXT IS '직렬화된 ExecutionContext';

-- DP_BATCH_COLLECTION_STATUS
CREATE TABLE "DP_BATCH_COLLECTION_STATUS"
   (
    "CHANNEL_ID" VARCHAR2(100) NOT NULL ENABLE,
	"CALL_ID" VARCHAR2(100) NOT NULL ENABLE,
	"RCRD_ID" VARCHAR2(100) NOT NULL ENABLE,
	"COLLECTION_TYPE" VARCHAR2(2) DEFAULT 'N',
	"ANALYSIS_TYPE" VARCHAR2(2) DEFAULT 'N',
	"STATISTICS_TYPE" VARCHAR2(2) DEFAULT 'N',
	"CALL_OCC_YMD" NUMBER(10,0) DEFAULT '-1' NOT NULL ENABLE,
	"CALL_OCC_YMDHM" NUMBER(19,0) DEFAULT '-1' NOT NULL ENABLE,
	"CALL_OCC_TIME" VARCHAR2(32) DEFAULT '-1',
	"MOD_DT" DATE DEFAULT NULL,
	"REG_DT" DATE DEFAULT NULL,
	CONSTRAINT PK_DP_BATCH_COLLECTION_STATUS PRIMARY KEY (CHANNEL_ID, CALL_ID, RCRD_ID, CALL_OCC_YMD)
   );

CREATE INDEX "DP_COLLECTION_STATUS_INFO_IDX" ON "DP_BATCH_COLLECTION_STATUS" ("CHANNEL_ID", "CALL_OCC_YMD");

COMMENT ON TABLE DP_BATCH_COLLECTION_STATUS IS '수집, 분석, 통계 대상 콜 단위 기록 테이블';
COMMENT ON COLUMN DP_BATCH_COLLECTION_STATUS.CHANNEL_ID IS '채널 아이디';
COMMENT ON COLUMN DP_BATCH_COLLECTION_STATUS.CALL_ID IS '콜 문서를 식별하는 아이디';
COMMENT ON COLUMN DP_BATCH_COLLECTION_STATUS.RCRD_ID IS '녹취 정보를 식별하는 아이디';
COMMENT ON COLUMN DP_BATCH_COLLECTION_STATUS.COLLECTION_TYPE IS '수집 여부';
COMMENT ON COLUMN DP_BATCH_COLLECTION_STATUS.ANALYSIS_TYPE IS '분석 여부';
COMMENT ON COLUMN DP_BATCH_COLLECTION_STATUS.STATISTICS_TYPE IS '통계 여부';
COMMENT ON COLUMN DP_BATCH_COLLECTION_STATUS.CALL_OCC_YMD IS '콜이 발생한 일자';
COMMENT ON COLUMN DP_BATCH_COLLECTION_STATUS.CALL_OCC_YMDHM IS '콜이 발생한 일시';
COMMENT ON COLUMN DP_BATCH_COLLECTION_STATUS.CALL_OCC_TIME IS '콜이 발생한 시간';
COMMENT ON COLUMN DP_BATCH_COLLECTION_STATUS.MOD_DT IS '마지막 분석 일시';
COMMENT ON COLUMN DP_BATCH_COLLECTION_STATUS.REG_DT IS 'TA_META 등록일시';

-- DP_BATCH_LOG (작업관리 배치 실행 완료 후 적재하는 용도)
CREATE TABLE DP_BATCH_LOG (
    JOBID NUMBER(19,0) NOT NULL,
    JOBCHANNEL VARCHAR2(100),
    JOBNAME VARCHAR2(255),
    CRONEXPRESSION VARCHAR2(100),
    STEPNAME VARCHAR2(100 CHAR),
    STEPSTATUS VARCHAR2(10 CHAR),
    STEPEXITMESSAGE VARCHAR2(2500 CHAR),
    BATCHSTARTTIME VARCHAR2(76),
    BATCHENDTIME VARCHAR2(76),
    BATCHLASTUPDATED VARCHAR2(76),
    NEXTFIRETIME VARCHAR2(100)
) SEGMENT CREATION IMMEDIATE ;

CREATE INDEX IDX_BATCHSTARTTIME ON DP_BATCH_LOG(BATCHSTARTTIME);

COMMENT ON COLUMN DP_BATCH_LOG.JOBID IS '잡ID';
COMMENT ON COLUMN DP_BATCH_LOG.JOBCHANNEL IS '잡채널';
COMMENT ON COLUMN DP_BATCH_LOG.JOBNAME IS '작업명';
COMMENT ON COLUMN DP_BATCH_LOG.CRONEXPRESSION IS '크론표현식';
COMMENT ON COLUMN DP_BATCH_LOG.STEPNAME IS '단계명';
COMMENT ON COLUMN DP_BATCH_LOG.STEPSTATUS IS '단계상태';
COMMENT ON COLUMN DP_BATCH_LOG.STEPEXITMESSAGE IS '성공 여부 메시지';
COMMENT ON COLUMN DP_BATCH_LOG.BATCHSTARTTIME IS '배치 시작 시간';
COMMENT ON COLUMN DP_BATCH_LOG.BATCHENDTIME IS '배치 완료 시간';
COMMENT ON COLUMN DP_BATCH_LOG.BATCHLASTUPDATED IS '배치 최근 실행 날짜';
COMMENT ON COLUMN DP_BATCH_LOG.NEXTFIRETIME IS '트리거 다음 실행 시간';