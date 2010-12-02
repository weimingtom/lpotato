package com.alisoft.nano.bench;

/**
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public interface Runner extends Runnable {

	/**
	 * 预备数据.
	 */
	void beforeRun();

	/**
	 * 清理数据.
	 */
	void afterRun();
}
