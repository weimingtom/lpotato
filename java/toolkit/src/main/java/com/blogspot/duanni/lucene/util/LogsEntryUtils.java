package com.blogspot.duanni.lucene.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blogspot.duanni.lucene.model.LogsEntry;

/**
 * 日志实体工具类.
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public class LogsEntryUtils {
	private static final Logger logger = LoggerFactory.getLogger(LogsEntryUtils.class);
	/** 日志分割字符 */
	private static final String separator = "\t";

	/**
	 * 根据单条日志创建日志实体.
	 * 
	 * @param logs
	 * @return
	 */
	public static LogsEntry createLogsEntry(String logs) {
		LogsEntry logsEntry = new LogsEntry();
		createLogsEntry(logs, logsEntry);
		return logsEntry;
	}

	/**
	 * 复用logsEntry对象.
	 * 
	 * @param line
	 * @param logsEntry
	 */
	public static void createLogsEntry(String logs, LogsEntry logsEntry) {
		String[] arr = logs.split(separator, 3);

		if (arr[0] != null) {
			logsEntry.setTime(arr[0]);
		} else {
			logger.warn("time(arr[0]) is null.");
		}
		if (arr[1] != null) {
			logsEntry.setModel(arr[1]);
		} else {
			logger.warn("model(arr[1] is null.");
		}
		if (arr[2] != null) {
			logsEntry.setContent(arr[2]);
		} else {
			logger.warn("content(arr[2] is null.");
		}
	}
}
