/*
 * (#)TempTest.java 1.0 2008-3-28 2008-3-28 GMT+08:00
 */
package com.javaeye.ema.test;

/**
 * @author 路春辉
 * @version $1.0, 2008-3-28 2008-3-28 GMT+08:00
 * @since JDK5
 */
public class TempTest {

	public static StringBuffer doSomething(StringBuffer buff) {
		buff = new StringBuffer();
		buff.append("Hello World");
		return buff;
	}

	public static void main(String[] args) {
		StringBuffer buff = new StringBuffer();
		buff.append("Hello");
		doSomething(buff);
		System.out.println(buff);
	}

}
