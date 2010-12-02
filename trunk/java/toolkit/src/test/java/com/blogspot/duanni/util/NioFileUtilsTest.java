package com.blogspot.duanni.util;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blogspot.duanni.io.BigFileUtils;
import com.blogspot.duanni.io.NioBigFileUtils;
import com.blogspot.duanni.io.StreamBigFileUtils;

/**
 * json工具类.
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public class NioFileUtilsTest {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private static final int MIN_BUFFER_SIZE = 1024 * 8;
	private static final int MAX_BUFFER_SIZE = 1024 * 1024;
	private BigFileUtils bigFileUtils;

	@Test
	public void readLineTwoFiles() throws IOException {
		int currentBufferSize = MIN_BUFFER_SIZE;

		bigFileUtils = new StreamBigFileUtils("UTF-8");

		while (currentBufferSize <= MAX_BUFFER_SIZE) {
			bigFileUtils.setBufferSize(currentBufferSize);
			logger.debug("bufferSize => [{}]", currentBufferSize);
			bigFileReadTest(currentBufferSize);
			currentBufferSize *= 2;
		}
	}

	private void bigFileReadTest(int currentBufferSize) {
		String rootPath = "G:/workspace/company/logs-server/logs";
		File openDir = new File(rootPath);
		// String path = "logback-logic-test.2010-07-01_17-38.0.log";

		if (openDir.canRead() && openDir.isDirectory()) {
			File[] list = openDir.listFiles();
			// for (File file : list) {
			File file = list[1];
			// if (file == null || file.isDirectory())
			// continue;
			bigFileUtils.open(file);
			try {
				long start = System.currentTimeMillis();
				String line;
				while ((line = bigFileUtils.readLine()) != null) {
					line.length();
					// logger.info("line => [{}]", line);
				}
				logger.debug("读取文件 => [{}] 耗时 => [{}] bufferSize => [{}] KB", new Object[] {
						file.getPath(), System.currentTimeMillis() - start, currentBufferSize / 1024 });
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				bigFileUtils.close();
			}
			// }
		}

	}

	// @Test
	public void readLineTest() {
		String path = "G:/workspace/company/logs-server/src/test/resource/nioFileUtilTest.txt";
		NioBigFileUtils fileUtils = new NioBigFileUtils(path, "UTF-8");
		fileUtils.open();
		try {
			String line;
			while ((line = fileUtils.readLine()) != null) {
				logger.info("line => [{}]", line);
			}
		} finally {
			fileUtils.close();
		}
	}

}
