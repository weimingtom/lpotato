package com.blogspot.duanni.io;

import java.io.IOException;
import java.io.InputStream;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

/**
 * 文件读取测试.
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public class FileUtilsTest {

	private static RWFileUtils rwFileUtils;
	private static RWFileUtils wFileUtils;
	static String rwFilePath = "file-read-and-write-test.txt";
	static String wFilePath = "file-write-test.txt";
	static String coder = "UTF-8";

	@BeforeClass
	public static void beforeClass() throws IOException {
		rwFileUtils = new RWFileUtils(new ClassPathResource(rwFilePath).getFile(), coder);
		rwFileUtils.clearFile();
		wFileUtils = new RWFileUtils(new ClassPathResource(wFilePath).getFile(), coder);
		wFileUtils.clearFile();
	}

	@AfterClass
	public static void afterClass() {
		// rwFileUtils.clearFile();
		rwFileUtils.close();
		// wFileUtils.clearFile();
		wFileUtils.close();
	}

	@Before
	public void before() {
		rwFileUtils.appendToLastLine("用于测试的预热行");
	}

	@Test
	public void clearFileTest() throws Exception {
		rwFileUtils.clearFile();
		Assert.assertEquals(rwFileUtils.fileSize(), 0);
		InputStream input = new ClassPathResource(rwFilePath).getInputStream();
		Assert.assertEquals(input.available(), 0);
	}

	@Test
	public void readLastLineTest() {
		String line = "测试test line 1";
		rwFileUtils.appendToLastLine(line + "\n");
		String lastLine = rwFileUtils.readLastLine();
		Assert.assertEquals(lastLine, line);

		line = "测试another test line ";
		rwFileUtils.appendToLastLine(line);
		lastLine = rwFileUtils.readLastLine();
		Assert.assertEquals(lastLine, line);

		line = " \n 测试test line 3 \n";
		rwFileUtils.appendToLastLine(line);
		lastLine = rwFileUtils.readLastLine();
		Assert.assertEquals(lastLine, " 测试test line 3 ");

		rwFileUtils.clearFile();
		Assert.assertEquals(rwFileUtils.readLastLine(), "");
	}

	@Test
	public void readFirstLineTest() {
		rwFileUtils.clearFile();
		String line = "测试test line";
		rwFileUtils.appendToLastLine(line);
		rwFileUtils.appendToLastLine("测试another test line");
		String firstLine = rwFileUtils.readFirstLine();
		Assert.assertEquals(firstLine, line);

		rwFileUtils.clearFile();
		Assert.assertEquals(rwFileUtils.readFirstLine(), "");
	}

	@Test
	public void deleteFirstLineTest() {
		rwFileUtils.clearFile();
		String line = "测试test line";
		rwFileUtils.appendToLastLine(line);
		String line2 = "测试test line2";
		rwFileUtils.appendToLastLine(line2);

		String delLine = rwFileUtils.deleteFirstLine();
		Assert.assertEquals(delLine, line);
		String firstLine = rwFileUtils.readFirstLine();
		Assert.assertEquals(line2, firstLine);

		delLine = rwFileUtils.deleteFirstLine();
		Assert.assertEquals(delLine, line2);
		firstLine = rwFileUtils.readFirstLine();
		Assert.assertEquals(firstLine, "");
	}

	@Test
	public void writeBigByteTest() {
		rwFileUtils.clearFile();
		byte[] nullB = new byte[8191];
		String line = new String(nullB);
		rwFileUtils.appendToLastLine(line);
		rwFileUtils.appendToLastLine(line);
	}
}
