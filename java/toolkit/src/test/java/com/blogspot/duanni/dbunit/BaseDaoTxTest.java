package com.blogspot.duanni.dbunit;

import javax.sql.DataSource;

import org.dbunit.Assertion;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.DefaultTable;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Assert;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.blogspot.duanni.spring.SpringTxTestCase;

/**
 * 
 * @author duanni (lch)
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public class BaseDaoTxTest extends SpringTxTestCase {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private String schema;

	private IDatabaseConnection conn;

	@Before
	public void initDbunit() throws Exception {
		conn = new DatabaseConnection(DataSourceUtils.getConnection(dataSource), schema);
	}

	public IDatabaseConnection getConection(){
		return conn;
	}
	/**
	 * 清空file中包含的表中的数据，并插入file中指定的数据
	 * 
	 * @param xmlFileClassPath
	 * @throws Exception
	 */
	protected void setUpDataSet(String xmlFileClassPath) {
		try {
			IDataSet dataSet = new XmlDataSet(new ClassPathResource(xmlFileClassPath).getInputStream());
			DatabaseOperation.CLEAN_INSERT.execute(conn, dataSet);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 验证file中包含的表中的数据和数据库中的相应表的数据是否一致
	 * 
	 * @param xmlFileClassPath
	 * @throws Exception
	 */
	protected void verifyDataSet(String xmlFileClassPath) throws Exception {
		IDataSet expected = new XmlDataSet(new ClassPathResource(xmlFileClassPath).getInputStream());
		IDataSet dataset = conn.createDataSet();
		for (String tableName : expected.getTableNames()) {
			Assertion.assertEquals(expected.getTable(tableName), dataset.getTable(tableName));
		}

	}

	/**
	 * 清空指定的表中的数据
	 * 
	 * @param tableName
	 * @throws Exception
	 */
	protected void clearTable(String tableName) {
		try {
			DefaultDataSet dataset = new DefaultDataSet();
			dataset.addTable(new DefaultTable(tableName));
			DatabaseOperation.DELETE_ALL.execute(conn, dataset);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 验证指定的表为空
	 * 
	 * @param tableName
	 */
	protected void verifyEmpty(String tableName) throws Exception {
		Assert.assertEquals(0, conn.createDataSet().getTable(tableName).getRowCount());
	}
}
