package com.message.main.event.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.message.main.event.service.EventService;

public class CleanEventJob extends QuartzJobBean {
	private static final Logger logger = LoggerFactory.getLogger(CleanEventJob.class);

	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		logger.debug("clean event job start...");
		final EventService eventService = (EventService) context.getJobDetail().getJobDataMap().get("eventService");
		try {
			eventService.cleanEventWeekAgo();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		logger.debug("clean event job end...");
	}

}
