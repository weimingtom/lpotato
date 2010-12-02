package com.blogspot.duanni;

/**
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public class forEach {

	/**
	 * @param start
	 * @param end
	 * @param func
	 */
	public forEach(int start, int end, Func func) {
		boolean stopFlag = false;
		boolean continueFlag = false;
		for (int i = start; i < end; i++) {
			func.excute(i, stopFlag, continueFlag);
			boolean continueFlagTemp = continueFlag;
			continueFlag = false;
			if (stopFlag)
				break;
			if (continueFlagTemp)
				continue;
		}
	}

}
