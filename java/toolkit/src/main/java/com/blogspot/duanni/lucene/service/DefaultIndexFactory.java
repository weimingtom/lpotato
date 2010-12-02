package com.blogspot.duanni.lucene.service;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wltea.analyzer.lucene.IKAnalyzer;
import org.wltea.analyzer.lucene.IKSimilarity;

/**
 * 默认索引管理工厂.
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public class DefaultIndexFactory implements IndexFactory {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	/** 分词器 */
	private Analyzer analyzer;
	private Directory directory;
	private File indexFile;

	/** 写入器缓存大小,默认 512MB */
	private double ramBufferSizeMB = 512;
	/** 关闭写入器前是否优化索引 */
	private boolean optimizeBeforeClose = true;

	private IndexWriter indexWriter;
	private IndexReader indexReader;
	private IndexSearcher indexSearcher;
	private Sort sort;

	/**
	 * 初始化索引.不存在就创建.
	 */
	public void init() throws Exception {
		logger.info("初始化索引开始...");

		directory = NIOFSDirectory.open(indexFile);
		analyzer = new IKAnalyzer();
		if (!indexFile.exists()) {
			indexFile.mkdirs();
			createIndex();
		}
		logger.info(
				"indexFilePath => [{}], Directory => [{}], Analyzer => [{}], ramBufferSizeMB => [{}], MaxFieldLength => [{}], optimizeBeforeClose => [{}]",
				new Object[] { indexFile.getPath(), directory.getClass().getName(), analyzer.getClass().getName(),
						ramBufferSizeMB, IndexWriter.MaxFieldLength.LIMITED, optimizeBeforeClose });

		indexWriter = new IndexWriter(directory, analyzer, false, IndexWriter.MaxFieldLength.LIMITED);
		indexWriter.setRAMBufferSizeMB(ramBufferSizeMB);

		indexReader = IndexReader.open(directory, true);
		logger.info("创建 IndexReader.");

		indexSearcher = new IndexSearcher(indexReader);
		indexSearcher.setSimilarity(new IKSimilarity());
		logger.info("创建 IndexSearcher. 相似度评估器 Similarity => [{}]", indexSearcher.getSimilarity().getClass().getName());

		// 评分降序，评分一样时后索引的排前面. 结果排序.为了不影响性能,只可以更改 {@link SortField#FIELD_SCORE} 和 {@link
		// SortField#DOC} 的排序
		SortField[] sortFields = new SortField[] { SortField.FIELD_SCORE, new SortField(null, SortField.DOC, true) };
		sort = new Sort(sortFields);
		logger.info("创建 Sort => [{}].", sort.toString());
		logger.info("初始化索引完毕.");
	}

	/**
	 * 关闭索引写入器.
	 * 
	 * @throws Exception
	 */
	public void destroy() {
		logger.info("optimizeBeforeClose => [{}], 开始关闭索引...");
		try {
			indexWriter.commit();
			if (optimizeBeforeClose) {
				logger.info("关闭索引写入器前优化索引.耗时操作(3-5min),请耐心等待...");
				indexWriter.optimize();
				logger.info("优化完毕.");
			}
			indexWriter.close();
			indexSearcher.close();
			indexReader.clone();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("关闭索引完毕.");
	}

	@Override
	public IndexSearcher getIndexSearcher() {
		return indexSearcher;
	}

	@Override
	public IndexWriter getIndexWriter() {
		return indexWriter;
	}

	@Override
	public boolean reopenIndexReader() {
		boolean result = true;
		try {
			synchronized (this) {
				if (!this.indexReader.isCurrent()) {
					IndexReader indexReader = this.indexReader.reopen(true);
					IndexSearcher indexSearcher = new IndexSearcher(indexReader);
					indexSearcher.setSimilarity(new IKSimilarity());
					logger.info("重新创建 IndexSearcher. 相似度评估器 Similarity => [{}]", indexSearcher.getSimilarity()
							.getClass().getName());
					this.indexReader = indexReader;
					this.indexSearcher = indexSearcher;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = false;
		}
		logger.info("重新打开indexReader 和 indexSearcher , result => {}", result);
		return result;
	}

	/**
	 * 创建索引.
	 * 
	 * @throws Exception
	 */
	private void createIndex() throws Exception {
		logger.info("索引目录不存在,开始创建空索引...");
		IndexWriter createWriter = new IndexWriter(directory, analyzer, true, IndexWriter.MaxFieldLength.LIMITED);
		createWriter.addDocument(new Document());
		createWriter.commit();
		createWriter.optimize();
		createWriter.close();
		logger.info("创建空索引完毕.");
	}

	/**
	 * 索引目录路径.
	 * 
	 * @param indexPath
	 */
	public void setIndexPath(String indexPath) {
		indexFile = new File(indexPath);
	}

	/**
	 * 写入器缓存大小.
	 * 
	 * @param ramBufferSizeMB
	 */
	public void setRamBufferSizeMB(double ramBufferSizeMB) {
		this.ramBufferSizeMB = ramBufferSizeMB;
	}

	/**
	 * 关闭写入器前是否优化索引.
	 * 
	 * @param optimizeBeforeClose
	 */
	public void setOptimizeBeforeClose(boolean optimizeBeforeClose) {
		this.optimizeBeforeClose = optimizeBeforeClose;
	}

	public Sort getSort() {
		return sort;
	}

}
