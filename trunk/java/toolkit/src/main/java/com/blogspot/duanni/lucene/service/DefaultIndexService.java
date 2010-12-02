package com.blogspot.duanni.lucene.service;

import java.io.File;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.search.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blogspot.duanni.io.BigFileUtils;
import com.blogspot.duanni.io.NioBigFileUtils;
import com.blogspot.duanni.lucene.ProjectConfig;
import com.blogspot.duanni.lucene.model.LogsEntry;
import com.blogspot.duanni.lucene.model.SearchCondition;
import com.blogspot.duanni.lucene.util.LogsEntryUtils;
import com.blogspot.duanni.lucene.util.SearchConditionUtils;

/**
 * 索引文件服务.
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public class DefaultIndexService implements IndexService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private IndexFactory indexFactory;
	private ProjectConfig projectConfig;

	private String fileDecoder;
	/** 索引优化完毕标识符 */
	private boolean optimized = true;

	/**
	 * 添加文件到索引,分词并索引.
	 */
	@Override
	public void add(String filePath) {
		File rootFile = new File(filePath);
		if (rootFile.isDirectory()) {
			logger.warn("索引文件为目录,无法添加索引. file => [{}]", rootFile);
		} else if (rootFile.canRead()) {
			addDocument(rootFile);
		}
		indexFactory.reopenIndexReader();
	}

	/**
	 * 添加文件到索引.
	 * 
	 * @param file
	 */
	private void addDocument(File file) {
		// 重用对象,加快构建索引的速度
		Field timeField = new Field(LogsEntry.INDEX_FILED_TIME, "", Field.Store.YES, Field.Index.NOT_ANALYZED);
		Field modelField = new Field(LogsEntry.INDEX_FILED_MODEL, "", Field.Store.YES, Field.Index.NOT_ANALYZED);
		Field contentField = new Field(LogsEntry.INDEX_FILED_CONTENT, "", Field.Store.YES, Field.Index.ANALYZED);
		LogsEntry logsEntry = new LogsEntry();
		BigFileUtils bigFileUtils = new NioBigFileUtils(fileDecoder);
		try {
			bigFileUtils.open(file);

			String line;
			int count = 0;
			logger.debug("indexing to directory => [{}]", file);
			while ((line = bigFileUtils.readLine()) != null) {
				count++;
				LogsEntryUtils.createLogsEntry(line, logsEntry);
				Document doc = new Document();

				timeField.setValue(logsEntry.getTime());
				doc.add(timeField);
				modelField.setValue(logsEntry.getModel());
				doc.add(modelField);
				String replacedContent = projectConfig.replace(logsEntry.getContent());
				contentField.setValue(replacedContent);
				doc.add(contentField);

				indexFactory.getIndexWriter().addDocument(doc);
			}
			logger.debug("index end. lineCount => [{}], file => [{}]", count, file);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			bigFileUtils.close();
		}
	}

	// @see org.apache.lucene.index.TestIndexWriterDelete
	@Override
	public void delete(SearchCondition searchCondition) {
		logger.info("删除索引... searchCondition => {}", searchCondition);
		Query query = SearchConditionUtils.buildQuery(searchCondition);
		try {
			indexFactory.getIndexWriter().deleteDocuments(query);
			indexFactory.getIndexWriter().commit();
			indexFactory.reopenIndexReader();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("删除完毕.");
	}

	@Override
	public void optimize() {
		logger.info("开始优化索引. optimized => [{}]", optimized);
		try {
			if (optimized) {
				optimized = false;
				indexFactory.getIndexWriter().optimize();
				optimized = true;
				projectConfig.setLastOptimizeTime(System.currentTimeMillis());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			optimized = true;
		}
		logger.info("索引优化完毕.optimized => [{}]", optimized);
	}

	public void setIndexFactory(IndexFactory indexFactory) {
		this.indexFactory = indexFactory;
	}

	public void setFileDecoder(String fileDecoder) {
		this.fileDecoder = fileDecoder;
	}

	public void setProjectConfig(ProjectConfig logsConfig) {
		this.projectConfig = logsConfig;
	}
}
