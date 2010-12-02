package com.blogspot.duanni.lucene.service;

import com.blogspot.duanni.lucene.model.SearchCondition;

/**
 * 索引文件服务接口.
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public interface IndexService {

	/**
	 * 添加文件到索引.
	 * 
	 * @param filePath
	 */
	void add(String filePath);

	/**
	 * 根据查询条件删除索引.
	 * 
	 * @param searchCondition
	 *            查询条件
	 */
	void delete(SearchCondition searchCondition);

	/**
	 * 优化索引.警告:非常耗时,不要经常操作.
	 */
	void optimize();
}
