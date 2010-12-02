package com.blogspot.duanni.lucene.util;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wltea.analyzer.lucene.IKQueryParser;

import com.blogspot.duanni.lucene.model.LogsEntry;
import com.blogspot.duanni.lucene.model.SearchCondition;

/**
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public class SearchConditionUtils {
	private static final Logger logger = LoggerFactory.getLogger(SearchConditionUtils.class);

	/**
	 * 构建查询条件.
	 * 
	 * @param searchCondition
	 * @return
	 * @throws IOException
	 */
	public static BooleanQuery buildQuery(SearchCondition searchCondition) {
		String q = searchCondition.getQ();
		String startTime = searchCondition.getStartTime();
		String endTime = searchCondition.getEndTime();
		String model = searchCondition.getModel();
		// 构造查询条件
		BooleanQuery query = new BooleanQuery();

		if (StringUtils.isNotBlank(q)) {
			try {
				Query keyQuery = IKQueryParser.parse(LogsEntry.INDEX_FILED_CONTENT, q);
				query.add(keyQuery, Occur.MUST);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

		if (StringUtils.isNotBlank(startTime) || StringUtils.isNotBlank(endTime)) {// LUCENE-38
			Query rangeQuery = new TermRangeQuery(LogsEntry.INDEX_FILED_TIME, startTime, endTime, true, true);
			query.add(rangeQuery, Occur.MUST);
		}

		if (StringUtils.isNotBlank(model)) {
			Query modelQuery = new TermQuery(new Term(LogsEntry.INDEX_FILED_MODEL, model));
			query.add(modelQuery, Occur.MUST);
		}

		return query;
	}
}
