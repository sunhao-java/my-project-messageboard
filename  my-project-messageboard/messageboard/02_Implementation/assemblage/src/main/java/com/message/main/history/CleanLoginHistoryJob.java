package com.message.main.history;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.message.main.history.service.HistoryService;

/**
 * 定时清除登录日志的job
 * @author sunhao(sunhao.java@gmail.com)
 */
public class CleanLoginHistoryJob extends QuartzJobBean {
	private static final Logger logger = LoggerFactory.getLogger(CleanLoginHistoryJob.class);

	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		logger.debug("clean login history job start...");
		final HistoryService historyService = (HistoryService) context.getJobDetail().getJobDataMap().get("historyService");
		try {
			historyService.cleanLoginHistory();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		logger.debug("clean login history job end...");
	}

}
