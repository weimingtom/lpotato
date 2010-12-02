package com.blogspot.duanni.lucene.replace;

/**
 * 日志内容替换接口.
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public interface LogsContentReplace {

	/**
	 * 替换日志内容.
	 * 
	 * @param logsContent
	 * @return
	 */
	String replace(final String logsContent);
}
