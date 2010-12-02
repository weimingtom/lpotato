package com.blogspot.duanni.io;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件随机读写工具类.
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public class RWFileUtils {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	/** 要读取的文件 */
	private RandomAccessFile randomAccessFile;
	/** 要读取的文件的解码器 */
	private Charset charset;

	/**
	 * 
	 */
	public RWFileUtils() {
		super();
	}

	/**
	 * 
	 */
	public RWFileUtils(String filePath, String charsetName) {
		this(new File(filePath), charsetName);
	}

	/**
	 * 
	 */
	public RWFileUtils(File file, String charsetName) {
		this(file, charsetName, false);

	}

	/**
	 * @throws RuntimeException
	 */
	public RWFileUtils(File file, String charsetName, boolean readOnly) {
		String mode = "rw";
		if (readOnly)
			mode = "r";
		try {
			this.randomAccessFile = new RandomAccessFile(file, mode);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		this.charset = Charset.forName(charsetName);
	}

	/**
	 * 清空文件.
	 * 
	 * @throws RuntimeException
	 */
	public void clearFile() {
		try {
			randomAccessFile.setLength(0);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 返回文件字节大小.
	 * 
	 * @throws RuntimeException
	 */
	public long fileSize() {
		try {
			return randomAccessFile.length();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 添加数据到文件最后一行.如果行尾没有换行符'\n',则自动添加.
	 * 
	 * @param line
	 * @throws RuntimeException
	 */
	public void appendToLastLine(String line) {
		try {
			if (!line.endsWith("\n")) {
				line = line + "\n";
			}
			byte[] lineByte = line.getBytes(charset);
			long fileSize = randomAccessFile.length();
			randomAccessFile.seek(fileSize);
			randomAccessFile.write(lineByte);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 删除第一行数据.
	 * 
	 * @return
	 */
	public String deleteFirstLine() {
		try {
			String firstLine = readFirstLine();
			int firstLineLength = firstLine.getBytes(charset).length + 1;
			// delete
			boolean eof = false;
			long readPosition = firstLineLength;
			long writePosition = 0;
			long fileSize = randomAccessFile.length() - firstLineLength;

			int c = -1;
			while (!eof) {// 覆盖数据
				randomAccessFile.seek(readPosition);
				c = randomAccessFile.read();
				readPosition += 1;
				if (c == -1) {
					eof = true;
					break;
				}
				randomAccessFile.seek(writePosition);
				randomAccessFile.write(c);
				writePosition += 1;
			}
			randomAccessFile.setLength(fileSize);

			return firstLine;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取文件第一行.使用换行符'\n'标识行.
	 * 
	 * @return
	 * @throws RuntimeException
	 */
	public String readFirstLine() {
		try {
			if (randomAccessFile.length() == 0)
				return "";
			randomAccessFile.seek(0);
			long lineStart = 0;
			long lineEnd = 0;
			boolean eol = false;

			while (!eol) {
				switch (randomAccessFile.read()) {
				case -1:
				case '\n':
					lineEnd = randomAccessFile.getFilePointer() - 1;
					eol = true;
					break;
				case '\r':
					// 忽略 windows
					break;
				}
			}

			if (lineStart > lineEnd) {
				throw new IndexOutOfBoundsException();
			}
			int lineLength = (int) (lineEnd - lineStart);
			if (lineLength == 0)
				return "";

			byte[] bArr = new byte[lineLength];
			randomAccessFile.seek(lineStart);
			randomAccessFile.read(bArr);

			return new String(bArr, charset);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 读取文件的最后一行.使用换行符'\n'标识行.
	 * 
	 * @return
	 * @throws RuntimeException
	 */
	public String readLastLine() {
		try {
			int fileSize = (int) randomAccessFile.length();
			if (fileSize == 0)
				return "";
			int c = -1;
			long lineStart = fileSize;
			long lineEnd = fileSize;

			randomAccessFile.seek(--fileSize);
			c = randomAccessFile.read();// '\n'
			if (c == '\n') {// 忽略 windows '\r'
				lineEnd = fileSize;
			}

			while (true) {
				randomAccessFile.seek(--fileSize);
				c = randomAccessFile.read();
				if (c == '\n') {
					lineStart = randomAccessFile.getFilePointer();
					break;
				}
			}

			if (lineStart > lineEnd) {
				throw new IndexOutOfBoundsException();
			}
			int lineLength = (int) (lineEnd - lineStart);
			if (lineLength == 0)
				return "";

			byte[] bArr = new byte[lineLength];
			randomAccessFile.seek(lineStart);
			randomAccessFile.read(bArr);

			return new String(bArr, charset);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 关闭文件流.
	 */
	public void close() {
		try {
			randomAccessFile.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
