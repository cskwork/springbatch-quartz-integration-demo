DROP TABLE IF EXISTS `BATCH_JOB_RESERVED`;
CREATE TABLE `DP_BATCH_JOB_RESERVED` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cron_expression` varchar(255) DEFAULT NULL,
  `cron_job` bit(1) DEFAULT NULL,
  `job_class` varchar(255) DEFAULT NULL,
  `job_group` varchar(255) DEFAULT NULL,
  `job_name` varchar(255) DEFAULT NULL,
  `repeat_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB;
