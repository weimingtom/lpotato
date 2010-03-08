/*
 * (#)FileCopy.java 1.0 2008-3-28 2008-3-28 
 */
package com.javaeye.ema.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javaeye.ema.util.Encode;

/**
 * 文件拷贝.
 * 
 * @author 路春辉
 * @version $1.0, 2008-3-28 2008-3-28 GMT+08:00
 * @since JDK5
 */
public class FileCopy {
	private final Logger log = LoggerFactory.getLogger(FileCopy.class);

	/**
	 * Copies src file to dst file. If the dst file does not exist, it is
	 * created.8KB cache
	 * 
	 * @param src
	 * @param dst
	 * @throws IOException
	 */
	public void copy(File src, File dst) {
		//TODO copy(src,dst,encode,encode);
	}

	public void copy(File src, File dst, Encode sourceEncode, Encode destEncode) {
		if (src.isDirectory())
			return;
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			if (!dst.exists()) {
				dst.createNewFile();
			}
			br = new BufferedReader(new InputStreamReader(new FileInputStream(src), sourceEncode.getEncode()));
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dst), destEncode.getEncode()));
			// Transfer bytes from in to out
			char[] buf = new char[8192];
			int len;
			while ((len = br.read(buf)) > 0) {
				bw.write(buf, 0, len);
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException("源文件不存在,或者目标文件无法被识别.");
		} catch (IOException e) {
			throw new RuntimeException("文件读写错误.");
		} finally {
			try {
				br.close();
				bw.close();
			} catch (IOException e) {
				log.error("文件流关闭失败.");
			}
		}
	}

}
