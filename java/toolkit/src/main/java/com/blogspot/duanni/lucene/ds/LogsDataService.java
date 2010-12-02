package com.blogspot.duanni.lucene.ds;

import java.util.List;
import java.util.Map;

import com.blogspot.duanni.lucene.model.ClientLogsIndex;


/**
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public interface LogsDataService {

	/**
	 * 获取未索引的日志信息. <filePath, id>
	 * 
	 * @return
	 */
	Map<String, Integer> getUnIndexForMap();

	/**
	 * 添加未索引日志到数据库.
	 * 
	 * @param filePath
	 */
	void addLogsFile(String filePath);

	/**
	 * 日志文件已被索引.更新到数据库.
	 * 
	 * @param clientLogsIndex
	 */
	void indexedLogs(ClientLogsIndex clientLogsIndex);

	/**
	 * 获取索引未索引日志.
	 * 
	 * @return
	 */
	List<ClientLogsIndex> getUnIndex();

}
