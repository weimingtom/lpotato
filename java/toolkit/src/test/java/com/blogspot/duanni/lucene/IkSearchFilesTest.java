package com.blogspot.duanni.lucene;

/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopFieldCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKQueryParser;
import org.wltea.analyzer.lucene.IKSimilarity;

import com.blogspot.duanni.BaseTest;
import com.blogspot.duanni.lucene.model.LogsEntry;

/** Simple command-line based search demo. */
public class IkSearchFilesTest extends BaseTest {

	static final File INDEX_DIR = new File("index");
	static Directory directory = null;
	static IndexSearcher isearcher;
	static IndexReader reader;
	static Sort sort;

	@BeforeClass
	public static void beforeSearch() throws Exception {
		directory = NIOFSDirectory.open(INDEX_DIR);
		reader = IndexReader.open(directory, true); // only
		// 实例化搜索器
		isearcher = new IndexSearcher(reader);
		// 在索引器中使用IKSimilarity相似度评估器
		isearcher.setSimilarity(new IKSimilarity());

		// 评分降序，评分一样时后索引的排前面
		SortField[] sortFields = new SortField[] { SortField.FIELD_SCORE, new SortField(null, SortField.DOC, true) };
		sort = new Sort(sortFields);
	}

	@AfterClass
	public static void afterSearch() throws Exception {
		reader.close();
	}

	/** Index all text files under a directory. */
	// @Test
	public void searchTest() {
		try {
			String keyword = "操作道具";
			// 使用IKQueryParser查询分析器构造Query对象
			Query query = IKQueryParser.parse(LogsEntry.INDEX_FILED_CONTENT, keyword);

			// 搜索相似度最高的5条记录
			int querySize = 5;
			TopDocs topDocs = isearcher.search(query, null, querySize, sort);
			logger.info("命中：{}", topDocs.totalHits);
			// 输出结果
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			for (int i = 0; i < scoreDocs.length; i++) {
				Document targetDoc = isearcher.doc(scoreDocs[i].doc);
				logger.info("内容：{}", targetDoc.toString());
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Test
	public void searchByRangeTimesTest() {
		try {
			String keyword = "操作";
			// 使用IKQueryParser查询分析器构造Query对象
			Query query = IKQueryParser.parse(LogsEntry.INDEX_FILED_CONTENT, keyword);

			String startTime = "2010-07-08 09:53:12";
			String endTime = "2010-07-08 09:53:13";
			Query rangeQuery = new TermRangeQuery(LogsEntry.INDEX_FILED_TIME, startTime, endTime, false, false);
			Query queryModel = new TermQuery(new Term(LogsEntry.INDEX_FILED_MODEL, "map"));

			BooleanQuery booleanQuery = new BooleanQuery();
			booleanQuery.add(queryModel, Occur.SHOULD);
			booleanQuery.add(query, Occur.SHOULD);
			booleanQuery.add(rangeQuery, Occur.MUST);

			// 搜索相似度最高的5条记录
			int querySize = 10000;
			TopDocs topDocs = isearcher.search(booleanQuery, null, querySize, sort);
			logger.info("命中：{}", topDocs.totalHits);
			// 输出结果
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			for (int i = 0; i < 5; i++) {
				int docId = scoreDocs[i].doc;
				Document targetDoc = isearcher.doc(docId);
				logger.info("doc : {} 内容：{}", docId, targetDoc.toString());
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

	}

	@Test
	public void searchByRangeTimesPagintionTest() {
		try {
			String keyword = "操作";
			// 使用IKQueryParser查询分析器构造Query对象
			Query query = IKQueryParser.parse(LogsEntry.INDEX_FILED_CONTENT, keyword);

			TopFieldCollector topCollector = TopFieldCollector.create(sort, isearcher.maxDoc(), false, false, false,
					false);
			// 搜索相似度最高的5条记录
			int pageNo = 1;
			int pageSize = 5;
			isearcher.search(query, topCollector);
			ScoreDoc[] docs = topCollector.topDocs((pageNo - 1) * pageSize, pageSize).scoreDocs;
			logger.info("命中：{}", topCollector.getTotalHits());
			// 输出结果
			for (ScoreDoc scdoc : docs) {
				int docId = scdoc.doc;
				Document targetDoc = isearcher.doc(docId);
				logger.info("doc : {} 内容：{}", docId, targetDoc.toString());
			}

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

	}
}
