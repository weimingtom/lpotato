package com.blogspot.duanni.lucene.task;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.blogspot.duanni.lucene.service.IndexService;

/**
 * 定时优化索引.
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public class IndexOptimizeTask extends QuartzJobBean {
	private IndexService indexService;

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		indexService.optimize();
	}

	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}
}
