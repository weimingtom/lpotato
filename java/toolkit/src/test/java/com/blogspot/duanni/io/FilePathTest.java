package com.blogspot.duanni.io;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.Test;

import com.blogspot.duanni.BaseTest;

/**
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public class FilePathTest extends BaseTest {

	@Test
	public void pathTest() throws Exception {
		File backupDirectory = new File("G:/temp");
		String rootPath = "G:/workspace/company/logs/logs/client";
		String srcPath = rootPath + "/2010-07-12_18.0.log";

		logger.info(FilenameUtils.getFullPath(srcPath));
		if (!srcPath.startsWith(rootPath)) {
			logger.warn("日志路径错误. logsClientDirectory => [{}], logsPath => [{}]", rootPath, srcPath);
		}

		String stuffixPath = srcPath.substring(rootPath.length());
		String destFilePath = backupDirectory.getPath() + stuffixPath;
		logger.info(destFilePath);
		String destDirectoryPath = FilenameUtils.getFullPath(destFilePath);

		File destDirectory = new File(destDirectoryPath);
		if (!destDirectory.exists()) {
			destDirectory.mkdirs();
		}

		FileUtils.moveFile(new File(srcPath), new File(destFilePath));
	}
}
