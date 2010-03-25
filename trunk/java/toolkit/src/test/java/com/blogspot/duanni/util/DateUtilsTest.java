package com.blogspot.duanni.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.Assert;

/**
 * 日期时间
 * 
 * @author duanni (lch)
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public class DateUtilsTest {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	public void stringToDate() throws Exception {
		String dStr1 = "2010-01-25 00:00:00";
		Calendar c1 = getCalendar(dStr1);
		Assert.assertEquals(25, c1.get(Calendar.DAY_OF_MONTH));
	}

	private Calendar getCalendar(String str) throws ParseException {
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
		Date d = df.parse(str);
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return c;
	}
}
