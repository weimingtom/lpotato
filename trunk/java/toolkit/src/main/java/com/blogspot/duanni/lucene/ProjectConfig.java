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
public interface ProjectConfig {

	/**
	 * 获取替换序列.
	 * 
	 * @return
	 */
	CopyOnWriteArrayList<LogsContentReplace> getReplaceList();

	/**
	 * 使用配置的替换序列替换传入的内容.
	 * 
	 * @param content
	 * @return
	 */
	String replace(final String content);

	/**
	 * 获取最后一次索引的时间.
	 * 
	 * @return
	 */
	long getLastIndexTime();

	/**
	 * 设置最后一次索引时间.
	 * 
	 * @param lastIndexTime
	 */
	void setLastIndexTime(long lastIndexTime);

	/**
	 * 设置最后一次索引优化时间.
	 * 
	 * @param currentTimeMillis
	 */
	void setLastOptimizeTime(long currentTimeMillis);

	/**
	 * 获取最后一次索引优化时间.
	 * 
	 * @return
	 */
	long getLastOptimizeTime();
}
