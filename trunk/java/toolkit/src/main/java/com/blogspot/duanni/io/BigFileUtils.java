package com.blogspot.duanni.io;

import java.io.File;

/**
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public interface BigFileUtils {
	/**
	 * 打开文件.
	 */
	void open();

	/**
	 * 打开指定的文件.如果存在已经打开的文件,会先关闭原先的文件,再打开新的文件.
	 * 
	 * @param file
	 */
	void open(File file);

	/**
	 * 关闭文件.
	 */
	void close();

	/**
	 * 设置缓冲区大小.
	 * 
	 * @param size
	 *            缓冲区大小
	 */
	void setBufferSize(int size);

	/**
	 * 从此文件读取文本的下一行。 此方法可以从该文件的当前文件指针处成功地读取字节，直到到达行结束符或文件的末尾。
	 * <p>
	 * 文本行由回车字符 ('\r') 和一个新行字符 ('\n') 结束，回车字符后面紧跟一个新行字符，
	 * 或者是文件的末尾。不使用行结束符，并且在返回的字符串中不包括结束符。
	 * </p>
	 * 
	 * @return 此文件文本的下一行，如果连一个字节也没有读取就已到达文件的末尾，则返回 null。
	 */
	String readLine();
}
