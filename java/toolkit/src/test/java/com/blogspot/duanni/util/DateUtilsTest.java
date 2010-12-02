package com.blogspot.duanni.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

/**
 * 日期时间
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public class DateUtilsTest {

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
