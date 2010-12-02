package com.blogspot.duanni.dbunit;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.dbunit.Assertion;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.dataset.xml.XmlDataSetWriter;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.blogspot.duanni.spring.SpringTxTestCase;

/**
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public class DbUnitUtils extends SpringTxTestCase {
	private IDatabaseConnection conn;

	public static final String SCHEMA_NAME = "test1";

	@Autowired
	private DataSource dataSource;

	@Before
	public void initDbunit() throws Exception {
		conn = new DatabaseConnection(DataSourceUtils.getConnection(dataSource), SCHEMA_NAME);
	}

	// @Test
	public void exportData() {
		exportTable("/home/test1/temp/user.xml", "user");
	}

	@Test
	public void importData() {
		String insertFile = "dbunit/user/insert.xml";
		importTable(insertFile);
		verifyDataSet(insertFile);
	}

	/**
	 * 验证file中包含的表中的数据和数据库中的相应表的数据是否一致
	 * 
	 * @param xmlFileClassPath
	 * @throws Exception
	 */
	protected void verifyDataSet(String xmlFileClassPath) {
		try {
			IDataSet expected = new XmlDataSet(new ClassPathResource(xmlFileClassPath).getInputStream());
			IDataSet dataset = conn.createDataSet();
			for (String tableName : expected.getTableNames()) {
				Assertion.assertEquals(expected.getTable(tableName), dataset.getTable(tableName));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 导出数据到指定文件
	 * 
	 * @param file
	 * @param connection
	 * @throws Exception
	 */
	private void exportTable(String file, String tableName) {
		QueryDataSet dataSet = new QueryDataSet(conn);
		try {
			File outputFile = new File(file);
			FileUtils.touch(outputFile);
			dataSet.addTable(tableName);
			Writer writer = new FileWriter(file);
			XmlDataSetWriter w = new XmlDataSetWriter(writer);
			w.setIncludeColumnComments(true);
			w.write(dataSet);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 导入数据到表
	 * 
	 * @param xmlFileClassPath
	 * @param tableName
	 */
	public void importTable(String xmlFileClassPath) {
		try {
			IDataSet dataSet = new XmlDataSet(new ClassPathResource(xmlFileClassPath).getInputStream());
			DatabaseOperation.CLEAN_INSERT.execute(conn, dataSet);
			flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
