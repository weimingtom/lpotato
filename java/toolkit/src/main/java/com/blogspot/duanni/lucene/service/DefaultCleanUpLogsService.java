package com.blogspot.duanni.lucene.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blogspot.duanni.lucene.ds.LogsDataService;
import com.blogspot.duanni.lucene.model.ClientLogsIndex;

/**
 * 默认日志整理服务.
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public class DefaultCleanUpLogsService implements CleanUpLogsService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private File backupPath;
	/** 客户端日志文件存储目录 */
	private File logsClientDirectory;
	private LogsDataService logsDataService;

	/**
	 * 整理日志.记录未索引的日志路径到未索引数据库.
	 */
	@Override
	public void cleanUp() {
		logger.info("开始整理日志文件...");
		File[] childLogsDirectory = logsClientDirectory.listFiles();
		List<String> logsFiles = new ArrayList<String>();
		for (File file : childLogsDirectory) {// 取 logs/client下子目录中的文件
			if (file.isDirectory())
				getLogsFiles(file, logsFiles);
		}

		Map<String, Integer> unIndexLogsFileMap = logsDataService.getUnIndexForMap();
		for (String filePath : logsFiles) {
			if (!unIndexLogsFileMap.containsKey(filePath)) {
				logsDataService.addLogsFile(filePath);
			}
		}
		logger.info("日志文件整理完毕.");
	}

	/**
	 * 备份日志.将日志文件移动到备份目录,记录到已索引数据库.
	 */
	@Override
	public void backUp(ClientLogsIndex logs) {
		String srcPath = logs.getFile();
		String rootPath = logsClientDirectory.getPath();
		if (!srcPath.startsWith(rootPath)) {
			logger.error("日志路径错误. logsClientDirectory => [{}], logsPath => [{}]", rootPath, srcPath);
			return;
		}

		String stuffixPath = srcPath.substring(rootPath.length());
		String destFilePath = backupPath.getPath() + stuffixPath;
		String destDirectoryPath = FilenameUtils.getFullPath(destFilePath);

		File destDirectory = new File(destDirectoryPath);
		if (!destDirectory.exists()) {
			destDirectory.mkdirs();
		}

		logger.debug("移动日志文件. srcPath => [{}], destFilePath => [{}]", srcPath, destFilePath);
		try {
			FileUtils.moveFile(new File(srcPath), new File(destFilePath));
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return;
		}
		logs.setBackupFile(destFilePath);
		logsDataService.indexedLogs(logs);
	}

	/**
	 * 初始化数据.
	 */
	public void init() {
		logger.info("\n备份已索引日志存放文件. file => [{}]\n客户端日志存放目录. file => [{}]", new Object[] { backupPath,
				logsClientDirectory });
	}

	/**
	 * 清理数据.
	 */
	public void destroy() {

	}

	public void setBackupPath(String backupPath) {
		this.backupPath = new File(backupPath);
		checkFileExists(this.backupPath, "备份已索引日志文件目录");
	}

	/**
	 * 根据程序运行目录,设置客户端日志存放目录.
	 * 
	 * @param logsClientDirectoryPath
	 */
	public void setLogsClientDirectoryPath(String logsClientDirectoryPath) {
		try {
			logsClientDirectory = new File(logsClientDirectoryPath);
			checkFileExists(logsClientDirectory, "客户端日志存放目录");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param file
	 */
	private void checkFileExists(File file, String configName) {
		if (!file.exists()) {
			throw new NullPointerException("设置错误," + configName + "不存在." + logsClientDirectory);
		}
	}

	public void setLogsDataService(LogsDataService logsDataService) {
		this.logsDataService = logsDataService;
		if (logsDataService == null)
			throw new NullPointerException("logsDataService is null");
	}

	/**
	 * @param file
	 * @param logsFiles
	 */
	private void getLogsFiles(File file, List<String> logsFiles) {
		if (file.isDirectory()) {
			File[] childFiles = file.listFiles();
			for (File child : childFiles) {
				getLogsFiles(child, logsFiles);
			}
		} else if (file.isFile()) {
			String path = file.getPath();
			if (path.endsWith(".log"))
				logsFiles.add(file.getPath());
		}
	}
}
