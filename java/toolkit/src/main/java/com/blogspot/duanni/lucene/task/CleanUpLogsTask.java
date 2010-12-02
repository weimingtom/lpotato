package com.blogspot.duanni.lucene.task;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.blogspot.duanni.lucene.service.CleanUpLogsService;

/**
 * 整理日志文件.
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public class CleanUpLogsTask extends QuartzJobBean {
	private CleanUpLogsService cleanUpLogsService;

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		cleanUpLogsService.cleanUp();
	}

	public void setCleanUpLogsService(CleanUpLogsService cleanUpLogsService) {
		this.cleanUpLogsService = cleanUpLogsService;
	}

}
