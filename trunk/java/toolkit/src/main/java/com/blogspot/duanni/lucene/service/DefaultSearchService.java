package com.blogspot.duanni.lucene.service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopFieldCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blogspot.duanni.lucene.model.LogsEntry;
import com.blogspot.duanni.lucene.model.SearchCondition;
import com.blogspot.duanni.lucene.util.SearchConditionUtils;
import com.blogspot.duanni.lucene.util.SearchPage;

/**
 * 搜索日志实现.
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public class DefaultSearchService implements SearchService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private IndexFactory indexFactory;

	@Override
	public SearchPage<LogsEntry> search(SearchPage<LogsEntry> page, SearchCondition searchCondition) {
		try {
			BooleanQuery query = SearchConditionUtils.buildQuery(searchCondition);
			long start = System.currentTimeMillis();
			// 分页获取
			TopFieldCollector topCollector = TopFieldCollector.create(indexFactory.getSort(), indexFactory
					.getIndexSearcher().maxDoc(), false, false, false, false);
			indexFactory.getIndexSearcher().search(query, topCollector);
			ScoreDoc[] docs = topCollector.topDocs(page.getFirst(), page.getPageSize()).scoreDocs;

			long totalTime = System.currentTimeMillis() - start;
			page.setTotalCount(topCollector.getTotalHits());
			page.setSearchTime(totalTime);
			logger.debug(
					"q => [{}], startTime => [{}], endTime => [{}], model => [{}], 命中 => [{}], 耗时 => [{}]",
					new Object[] { searchCondition.getQ(), searchCondition.getStartTime(),
							searchCondition.getEndTime(), searchCondition.getModel(), page.getTotalCount(), totalTime });

			page.setResult(getResult(docs));
			return page;
		} catch (Exception e) {
			logger.error("异常.查询条件 => {}", searchCondition);
			logger.error(e.getMessage(), e);
			return page;
		}
	}

	/**
	 * 组装结果.
	 * 
	 * @param docs
	 * @return
	 * @throws
	 */
	private List<LogsEntry> getResult(ScoreDoc[] docs) throws Exception {
		CopyOnWriteArrayList<LogsEntry> list = new CopyOnWriteArrayList<LogsEntry>();
		for (ScoreDoc scDoc : docs) {
			int docId = scDoc.doc;
			Document doc = indexFactory.getIndexSearcher().doc(docId);
			String content = doc.getField(LogsEntry.INDEX_FILED_CONTENT).stringValue();
			String time = doc.getField(LogsEntry.INDEX_FILED_TIME).stringValue();
			String model = doc.getField(LogsEntry.INDEX_FILED_MODEL).stringValue();
			list.add(new LogsEntry(time, model, content));
		}
		return list;
	}

	public void setIndexFactory(IndexFactory indexFactory) {
		this.indexFactory = indexFactory;
	}
}
