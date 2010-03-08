/*
 * @(#)BatchFile.java 2009-4-2
 */
package com.javaeye.ema.file;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javaeye.ema.util.Encode;

/**
 * 批量文件操作.
 * @author 路春辉
 * @version $1.0, 2009-4-2 2009-4-2 GMT+08:00
 * @since JDK1.5
 */
public class BatchFileOperate {

	private final Logger log = LoggerFactory.getLogger(BatchFileOperate.class);
	/**来源文件编码格式*/
	private Encode sourceEncode;

	/**目标文件编码格式*/
	private Encode destEncode;

	/**来源文件*/
	private File sourceFile;

	/**目标文件*/
	private File destFile;

	/**要过滤的文件格式列表*/
	private Map<String, String> fileFormat;
	/**源文件的原始前缀*/
	private static String SOURCE_FILE_PREFIX;
	/**目标文件的原始前缀*/
	private static String DEST_FILE_PREFIX;

	private FileCopy fileCopy;

	/**
	 * @param sourceEncode
	 * @param destEncode
	 * @param sourceFile
	 * @param destFile
	 */
	public BatchFileOperate(Encode sourceEncode, Encode destEncode, File sourceFile, File destFile) {
		this.sourceEncode = sourceEncode;
		this.destEncode = destEncode;
		this.sourceFile = sourceFile;
		this.destFile = destFile;
		SOURCE_FILE_PREFIX = sourceFile.getPath();
		DEST_FILE_PREFIX = destFile.getPath();
	}

	/**
	 * @param sourceEncode
	 * @param destEncode
	 * @param sourceFile
	 * @param destFile
	 * @param fileFormat
	 */
	public BatchFileOperate(Encode sourceEncode, Encode destEncode, File sourceFile, File destFile,
			String... formatArray) {
		this(sourceEncode, destEncode, sourceFile, destFile);
		setFileFormat(formatArray);
	}

	/**
	 * 批量转换文件编码.
	 */
	public void conversionCode() {
		if (sourceFile.isFile()) {
			copy(sourceFile);
			return;
		}
		recursiveConversionFile(sourceFile);
	}

	/**
	 * 递归源文件,把源文件内容copy到目标文件.不存在文件或者目录则创建.
	 * @param sourceFile 
	 * @param destFile 不变
	 */
	private void recursiveConversionFile(File sourceFile) {
		log.info("转换目录:{}", sourceFile.getPath());
		File[] listFile = sourceFile.listFiles();
		for (File file : listFile) {
			if (file.isFile()) {//是文件,copy
				copy(file);
			} else if (file.isDirectory()) {//是目录,创建
				if (!isTheSourceDirectoryInsideTheDestDirectory(file)) {
					createDestDirectory(file);
				}
				recursiveConversionFile(file);//递归
			}
		}
	}

	/**
	 * 创建目标目录
	 * @param sourceFile2
	 * @param destFile2
	 */
	private void createDestDirectory(File sourceFile) {
		getNewDest(sourceFile).mkdirs();
	}

	/**
	 * 源文件在目标文件中是否存在.
	 * 
	 * @param sourceFile
	 * @param destFile 
	 * @return
	 */
	private boolean isTheSourceDirectoryInsideTheDestDirectory(File sourceFile) {
		return getNewDest(sourceFile).exists();
	}

	private File getNewDest(File sourceFile) {
		String sourceFilePath = sourceFile.getPath();
		String newDestFilePath = DEST_FILE_PREFIX + sourceFilePath.substring(SOURCE_FILE_PREFIX.length());
		return new File(newDestFilePath);
	}

	/**
	 * Copies src file to dst file. If the dst file does not exist, it is
	 * created.8KB cache
	 * 
	 * @param src
	 * @param dst
	 */
	public void copy(File src) {
		if (!matchFileFormat(src))
			return;
		if (fileCopy == null)
			fileCopy = new FileCopy();
		fileCopy.copy(src, getNewDest(src), sourceEncode, destEncode);
	}

	private boolean matchFileFormat(File src) {
		String filePath = src.getPath();
		String fileFormat = filePath.substring(filePath.lastIndexOf(".") + 1);
		return getFileFormat().containsKey(fileFormat);
	}

	public Encode getSourceEncode() {
		return sourceEncode;
	}

	public Encode getDestEncode() {
		return destEncode;
	}

	public File getSourceFile() {
		return sourceFile;
	}

	public File getDestFile() {
		return destFile;
	}

	public Map<String, String> getFileFormat() {
		if (fileFormat == null)
			createFileFormat();
		return Collections.unmodifiableMap(fileFormat);
	}

	private void setFileFormat(String... formatArray) {
		if (fileFormat == null)
			createFileFormat();
		for (String key : formatArray)
			fileFormat.put(key, key);
	}

	private void createFileFormat() {
		fileFormat = new HashMap<String, String>();
	}
}
