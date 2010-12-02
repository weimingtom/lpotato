package com.blogspot.duanni.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.blogspot.duanni.SpringBaseTest;
import com.blogspot.duanni.io.BigFileUtils;
import com.blogspot.duanni.io.NioBigFileUtils;
import com.blogspot.duanni.lucene.model.LogsEntry;
import com.blogspot.duanni.lucene.util.LogsEntryUtils;

/**
 * 测试索引文件.
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public class IndexFilesTest extends SpringBaseTest {
	private static final File INDEX_DIR = new File("index");
	private static final String DIRECTORY = "/";
	@Autowired
	private ProjectConfig logsConfig;

	@Test
	public void directoryTest() throws Exception {
		File file = getLogsDirectory();
		logger.debug("path => [{}]", file.getPath());
		Assert.assertTrue(file.exists());
		Assert.assertTrue(file.isDirectory());
		Assert.assertTrue(file.list().length > 0);
	}

	/**
	 * @return
	 * @throws IOException
	 */
	private File getLogsDirectory() {
		File classPathFile = null;
		try {
			classPathFile = new ClassPathResource(DIRECTORY).getFile();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		File rootFile = classPathFile.getParentFile().getParentFile();
		File file = new File(rootFile, "logs/agent/agent.2010-07-08_09-53.0.log");
		return file;
	}

	@Test
	public void indexTest() throws Exception {
		File dFile = getLogsDirectory();
		Analyzer analyzer = new IKAnalyzer();
		Directory directory = null;
		directory = NIOFSDirectory.open(INDEX_DIR);
		if (!INDEX_DIR.exists()) {
			IndexWriter initWriter = new IndexWriter(directory, analyzer, true, IndexWriter.MaxFieldLength.LIMITED);
			initWriter.addDocument(new Document());
			initWriter.commit();
			initWriter.optimize();
			initWriter.close();
		}

		IndexWriter writer = new IndexWriter(directory, analyzer, false, IndexWriter.MaxFieldLength.LIMITED);
		writer.setRAMBufferSizeMB(768);
		if (dFile.canRead() && dFile.isDirectory()) {
			File[] childFile = dFile.listFiles();
			for (File file : childFile) {
				logger.info("adding file => [{}]", file);
				logger.info("indexing to directory => [{}]", INDEX_DIR);
				addDocument(writer, file);
				logger.info("index end.");
			}
		} else {
			File file = dFile;
			logger.info("adding file => [{}]", file);
			logger.info("indexing to directory => [{}]", INDEX_DIR);
			addDocument(writer, file);
			logger.info("index end.");
		}
		logger.info("Optimizing...");
		writer.optimize();
		writer.close();
	}

	/**
	 * Makes a document for a File.
	 * <p>
	 * The document has three fields:
	 * <ul>
	 * <li><code>path</code>--containing the pathname of the file, as a stored,
	 * untokenized field;
	 * <li><code>modified</code>--containing the last modified date of the file as a field
	 * as created by <a href="lucene.document.DateTools.html">DateTools</a>; and
	 * <li><code>contents</code>--containing the full contents of the file, as a Reader
	 * field;
	 */
	public void addDocument(IndexWriter writer, File f) {
		// Add the contents of the file to a field named "contents". Specify a Reader,
		// so that the text of the file is tokenized and indexed, but not stored.
		// Note that FileReader expects the file to be in the system's default encoding.
		// If that's not the case searching for special characters will fail.
		BigFileUtils fileUtil = new NioBigFileUtils("UTF-8");
		int count = 0;
		try {
			fileUtil.open(f);
			// 按行读取文件并加入到content中。
			// 当readLine方法返回null时表示文件读取完毕。

			Field timeField = new Field(LogsEntry.INDEX_FILED_TIME, "", Field.Store.YES, Field.Index.NOT_ANALYZED);
			Field modelField = new Field(LogsEntry.INDEX_FILED_MODEL, "", Field.Store.YES, Field.Index.NOT_ANALYZED);
			Field contentField = new Field(LogsEntry.INDEX_FILED_CONTENT, "", Field.Store.YES, Field.Index.ANALYZED);
			LogsEntry logsEntry = new LogsEntry();

			String line;
			while ((line = fileUtil.readLine()) != null) {
				count++;
				LogsEntryUtils.createLogsEntry(line, logsEntry);
				Document doc = new Document();
				// make a new, empty document

				// Add the path of the file as a field named "path". Use a field that is
				// indexed (i.e. searchable), but don't tokenize the field into words.
				// doc.add(new Field(INDEX_NAME_PATH, f.getPath(), Field.Store.YES,
				// Field.Index.NOT_ANALYZED));

				// Add the last modified date of the file a field named "modified". Use
				// a field that is indexed (i.e. searchable), but don't tokenize the field
				// into words.
				// doc.add(new Field(INDEX_NAME_MODIFIED,
				// DateTools.timeToString(f.lastModified(),
				// DateTools.Resolution.MINUTE), Field.Store.YES,
				// Field.Index.NOT_ANALYZED));
				timeField.setValue(logsEntry.getTime());
				doc.add(timeField);
				modelField.setValue(logsEntry.getModel());
				doc.add(modelField);
				String replacedContent = logsConfig.replace(logsEntry.getContent());
				contentField.setValue(replacedContent);
				doc.add(contentField);

				if (count % 1000 == 0) {
					logger.info("flush writer count {}", count);
				}
				try {
					writer.addDocument(doc);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			logger.info("file line count {}", count);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 最后要在finally中将reader对象关闭
			fileUtil.close();
		}

		// return the document
		// return doc;
	}
}
