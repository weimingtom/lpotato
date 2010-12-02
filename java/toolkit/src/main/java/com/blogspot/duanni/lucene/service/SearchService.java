package com.blogspot.duanni.lucene.service;

import com.blogspot.duanni.lucene.model.LogsEntry;
import com.blogspot.duanni.lucene.model.SearchCondition;
import com.blogspot.duanni.lucene.util.SearchPage;

/**
 * 搜索日志接口.
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public interface SearchService {

	/**
	 * 搜索日志.
	 * 
	 * @param page
	 *            分页条件
	 * @param searchCondition
	 *            搜索条件
	 * @return 查询分页结果
	 */
	SearchPage<LogsEntry> search(SearchPage<LogsEntry> page, SearchCondition searchCondition);
}
