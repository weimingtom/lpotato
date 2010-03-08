/*
 * @(#)BatchOperateTest.java 2009-4-2
 */
package com.javaeye.ema.file;

import java.io.File;

import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javaeye.ema.util.Encode;

/**
 * 批量文件操作.
 * @author 路春辉
 * @version $1.0, 2009-4-2 2009-4-2 GMT+08:00
 * @since JDK1.5
 */
public class BatchOperateTest {
	private final Logger log = LoggerFactory.getLogger(BatchOperateTest.class);
	private BatchFileOperate batchFile;

	private File sourceFile;
	private File destFile;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		sourceFile = new File("D:\\workspace\\SRTWorkSpace\\meg2.9.0_i18n");
		destFile = new File("D:\\workspace\\eclipseWorkSpace\\meg2.9.0_i18n_utf-8");
	}

	@Test
	public void batchFileOperateTest() {
		log.info("test");
		batchFile = new BatchFileOperate(Encode.GBK, Encode.UTF8, sourceFile, destFile, "js", "css", "java", "jsp");
		batchFile.conversionCode();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

}
