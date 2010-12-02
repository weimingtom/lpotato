package com.blogspot.duanni;

/**
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public abstract class Func {

	/**
	 * 当stop为true时,循环中断.
	 * 
	 * @param i
	 * @param stopFlag
	 * @param continueFlag
	 */
	public abstract void excute(int i, boolean stopFlag, boolean continueFlag);
}
