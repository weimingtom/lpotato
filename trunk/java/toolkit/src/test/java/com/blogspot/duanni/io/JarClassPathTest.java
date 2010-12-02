package com.blogspot.duanni.io;

import java.io.IOException;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

/**
 * 测试打包到jar后获取项目路径.
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public class JarClassPathTest {

	@Test
	public void classPathTest() {
		try {
			ClassPathResource clsPathResource = new ClassPathResource("/");
			String classPathFile = clsPathResource.getURL().toString();
			System.out.println("项目根路径: spring Resource => " + classPathFile);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new JarClassPathTest().classPathTest();
	}
}
