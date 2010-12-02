package com.blogspot.duanni.util;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * json工具类.
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public class JSONUtils {
	private static final Logger logger = LoggerFactory.getLogger(JSONUtils.class);

	private static final ObjectMapper mapper = new ObjectMapper();

	/**
	 * 获取对象的json数据.
	 * 
	 * @param obj
	 * @return
	 */
	public static String getJSON(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			logger.error("转换对象到JSON失败.", e);
			return "";
		}
	}
}
