package com.blogspot.duanni.lucene;

import java.util.concurrent.CopyOnWriteArrayList;

import com.blogspot.duanni.lucene.replace.LogsContentReplace;

/**
 * 日志全局配置.
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public class ProjectConfigImpl implements ProjectConfig {

	private CopyOnWriteArrayList<LogsContentReplace> replaceList = new CopyOnWriteArrayList<LogsContentReplace>();

	private long lastIndexTime;

	private long lastOptimizeTime;

	@Override
	public CopyOnWriteArrayList<LogsContentReplace> getReplaceList() {
		return replaceList;
	}

	@Override
	public String replace(final String content) {
		String result = content;
		for (LogsContentReplace replace : replaceList) {
			result = replace.replace(result);
		}
		return result;
	}

	public void setReplaceList(CopyOnWriteArrayList<LogsContentReplace> replaceList) {
		this.replaceList = replaceList;
	}

	@Override
	public long getLastIndexTime() {
		return lastIndexTime;
	}

	@Override
	public void setLastIndexTime(long lastIndexTime) {
		this.lastIndexTime = lastIndexTime;
	}

	@Override
	public void setLastOptimizeTime(long currentTimeMillis) {
		this.lastOptimizeTime = currentTimeMillis;
	}

	@Override
	public long getLastOptimizeTime() {
		return lastOptimizeTime;
	}
}
