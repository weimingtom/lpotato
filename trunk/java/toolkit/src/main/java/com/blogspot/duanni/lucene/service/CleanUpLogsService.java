package com.blogspot.duanni.lucene.service;

import com.blogspot.duanni.lucene.model.ClientLogsIndex;

/**
 * 日志整理服务.
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public interface CleanUpLogsService {

	/**
	 * 整理日志.记录未索引的日志路径到未索引数据库.
	 */
	void cleanUp();

	/**
	 * 备份日志.将日志文件移动到备份目录,记录到已索引数据库.
	 * 
	 * @param clientLogsIndex
	 *            已索引的日志文件.
	 */
	void backUp(ClientLogsIndex clientLogsIndex);
}
