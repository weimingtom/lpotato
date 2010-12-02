package com.blogspot.duanni.lucene.task;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.blogspot.duanni.lucene.ds.LogsDataService;
import com.blogspot.duanni.lucene.model.ClientLogsIndex;
import com.blogspot.duanni.lucene.service.CleanUpLogsService;
import com.blogspot.duanni.lucene.service.IndexService;

/**
 * 增量构建索引.索引完成后移动到备份目录,记录相关索引信息. 索引当前开始到上一次作业之间所有未索引的日志.
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public class AddDocumentTask extends QuartzJobBean {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private LogsDataService logsDataService;
	private IndexService indexService;
	private CleanUpLogsService cleanUpLogsService;

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		List<ClientLogsIndex> logsList = logsDataService.getUnIndex();
		logger.info("开始索引日志文件 file count => [{}]...", logsList.size());
		for (ClientLogsIndex logs : logsList) {
			indexService.add(logs.getFile());
			cleanUpLogsService.backUp(logs);
		}
		logger.info("日志文件索引完毕.");
	}

	public void setLogsDataService(LogsDataService logsDataService) {
		this.logsDataService = logsDataService;
	}

	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}

	public void setCleanUpLogsService(CleanUpLogsService cleanUpLogsService) {
		this.cleanUpLogsService = cleanUpLogsService;
	}

}
