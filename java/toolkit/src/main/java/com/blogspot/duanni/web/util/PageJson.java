package com.blogspot.duanni.web.util;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * page转json工具类.
 */
public class PageJson {
	private static final Logger logger = LoggerFactory.getLogger(PageJson.class);

	private static JsonConfig PAGE_JSONCONFIG;

	/** json对象的结果集标识符. { nextPage : "", prePage : "", ... ,resultList : [{}...]} */
	public static final String RESULT_LIST = "resultList";

	/**
	 * 把page转换为json,不包括其中的集合属性.
	 * 
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String toJSON(Page page) {
		logger.info("开始转换为json.");
		JSONObject jsonObj = PageJson.toJSONObject(page);
		String json = jsonObj.toString();
		logger.info("转换json结束.");
		return json;
	}

	@SuppressWarnings("unchecked")
	public static JSONObject toJSONObject(Page page) {
		JSONObject json = JSONObject.fromObject(page, getPageJsonConfig());
		return json;
	}

	/**
	 * 转换过滤规则.去掉page中的集合属性,不做转换.
	 */
	public static JsonConfig getPageJsonConfig() {
		if (PAGE_JSONCONFIG != null)
			return PAGE_JSONCONFIG;
		PAGE_JSONCONFIG = new JsonConfig();
		PAGE_JSONCONFIG.registerPropertyExclusions(Page.class, new String[] { "resultList", "resultMap",
				"result" });
		return PAGE_JSONCONFIG;
	}
}
