package com.blogspot.duanni;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public class BaseTest {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * 增强的for循环.
	 * 
	 * @param start
	 * @param end
	 * @param function
	 */
	protected void forEach(int start, int end, Func func) {
		new com.blogspot.duanni.forEach(start, end, func);
	}

	protected static String toJSON(Object object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
