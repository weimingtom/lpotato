package com.blogspot.duanni.lucene.service;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Sort;

/**
 * 索引管理工厂.
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public interface IndexFactory {

	/**
	 * 获取索引搜索器.
	 * 
	 * @return
	 */
	IndexSearcher getIndexSearcher();

	/**
	 * 重新打开indexReader和indexSearcher.
	 * 
	 * @return
	 */
	boolean reopenIndexReader();

	/**
	 * 获取索引写入器.
	 * 
	 * @return
	 */
	IndexWriter getIndexWriter();

	/**
	 * 评分降序，评分一样时后索引的排前面.
	 * 结果排序.为了不影响性能,只可以更改 {@link SortField#FIELD_SCORE} 和 {@link SortField#DOC} 的排序
	 * 
	 * @return
	 */
	Sort getSort();
}
