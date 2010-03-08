/*
 * (#)ProjectPath.java 1.0 2008-3-28 2008-3-28 GMT+08:00
 */
package com.javaeye.ema.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;

/**
 * 获取当前工程或者当前jar包所在路径.
 * 
 * @author 路春辉
 * @version $1.0, 2008-3-28 2008-3-28 GMT+08:00
 * @since JDK5
 */
public class ProjectPath {
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public static String getAppProjectPath() throws IOException {
		try {
			URL url = ProjectPath.class.getProtectionDomain().getCodeSource()
										.getLocation();
			String filePath = URLDecoder.decode(url.getPath(), "UTF-8");
			if (filePath.endsWith(".jar"))
				filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
			return filePath;
		} catch (UnsupportedEncodingException ue) {
			throw new UnsupportedEncodingException("路径用utf-8解码出现错误.");
		}
	}

	/**
	 * 获取获取webc工程的工程决定路径.
	 * 在Eclipse下,由于编译后的class文件都在.../myProject/WebRoot/WEB-INF/classes下.
	 * 此时得到的路径为.../myProject/
	 * 
	 * @return
	 * @throws IOException
	 */
	public static String getWebProjectPath() throws IOException {
		try {
			File srcFile = new File(getAppProjectPath());
			URI webPath = srcFile.getParentFile().getParentFile()
									.getParentFile().toURI();
			return URLDecoder.decode(webPath.toURL().getPath(), "UTF-8");
		} catch (UnsupportedEncodingException ue) {
			throw new UnsupportedEncodingException("路径用utf-8解码出现错误.");
		}
	}
}
