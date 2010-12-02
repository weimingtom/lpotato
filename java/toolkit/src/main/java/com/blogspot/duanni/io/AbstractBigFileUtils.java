package com.blogspot.duanni.io;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 大文件读取抽象类.
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public abstract class AbstractBigFileUtils implements BigFileUtils {
	/** 中转缓冲区最小缓冲大小 */
	private static final int BUFFER_SIZE = 1024;
	/** 要读取的文件 */
	private File file;
	/** 要读取的文件的解码器 */
	private CharsetDecoder decoder;
	/** 中转缓冲区大小 */
	private int bufferSize = BUFFER_SIZE;// 缓冲区大小
	/** 中转缓冲区 */
	private byte[] readerBuffer;
	/** 中转缓冲区的当前位置 */
	private int position = 0;
	/** 中转缓冲区的限制 */
	private int limit = 0;

	/**
	 * @param decoderCharsetName
	 *            解码字符集
	 */
	public AbstractBigFileUtils(String decoderCharsetName) {
		this(null, Charset.forName(decoderCharsetName));
	}

	/**
	 * @param path
	 * @param decoderCharsetName
	 *            解码字符集
	 */
	public AbstractBigFileUtils(String path, String decoderCharsetName) {
		this(new File(path), Charset.forName(decoderCharsetName));
	}

	/**
	 * @param file
	 *            要读取的文件
	 * @param decoderCharsetName
	 *            解码字符集
	 */
	public AbstractBigFileUtils(File file, String decoderCharsetName) {
		this(file, Charset.forName(decoderCharsetName));
	}

	/**
	 * @param file
	 *            要读取的文件
	 * @param decoder
	 *            解码器
	 */
	public AbstractBigFileUtils(File file, Charset decoder) {
		this.file = file;
		this.decoder = decoder.newDecoder();
	}

	/**
	 * 设置解码器.
	 * 
	 * @param decoderCharsetName
	 */
	public void setDecoder(String decoderCharsetName) {
		this.decoder = Charset.forName(decoderCharsetName).newDecoder();
	}

	/**
	 * 设置缓冲区大小.
	 * 
	 * @param size
	 *            缓冲区大小
	 */
	public void setBufferSize(int size) {
		if (size > BUFFER_SIZE)
			this.bufferSize = size;
	}

	/**
	 * 从此文件读取文本的下一行。
	 * 此方法可以从该文件的当前文件指针处成功地读取字节，直到到达行结束符或文件的末尾。
	 * <p>
	 * 文本行由回车字符 ('\r') 和一个新行字符 ('\n') 结束，回车字符后面紧跟一个新行字符，
	 * 或者是文件的末尾。不使用行结束符，并且在返回的字符串中不包括结束符。
	 * </p>
	 * 
	 * @return 此文件文本的下一行，如果连一个字节也没有读取就已到达文件的末尾，则返回 null。
	 */
	public String readLine() {
		byte c = -1;
		boolean eol = false;
		List<Byte> input = new ArrayList<Byte>();

		while (!eol) {
			switch (c = get()) {
			case '\n':
				eol = true;
				break;
			case '\r':
				eol = true;
				int oldP = position();
				if (get() != '\n') { // 当\r正好在最后一个位置时(position>=limit).
					int newP = position(); // 获取下一个字节会导致读取新的readerBuffer.
					int cur = oldP; // 这时get()方法会使 position 为 1.
					if (oldP > newP) {
						cur = 0; // 这里 oldP 总是会>= 1023. newP 总是为 1.
					}
					position(cur);
				}
				break;
			default:
				input.add(c);
				break;
			}
		}

		if ((c == -1) || input.isEmpty()) {
			return null;
		}

		byte[] bArr = new byte[input.size()];
		for (int i = 0; i < input.size(); i++) {
			bArr[i] = input.get(i);
		}

		return decode(bArr);
	}

	/**
	 * @param bArr
	 * @return
	 */
	protected abstract String decode(byte[] bArr);

	/**
	 * 获取一个字节
	 * 
	 * @return
	 */
	private byte get() {
		if (!hasRemaining()) {
			readBuffer();
			if (!hasRemaining())
				return '\n';
		}
		byte result = readerBuffer[position];
		position++;
		return result;
	}

	/**
	 * 获取中转缓冲区的位置.
	 * 
	 * @return
	 */
	protected int position() {
		return position;
	}

	/**
	 * 设置中转缓冲区的位置.
	 * 
	 * @param position
	 */
	protected void position(int position) {
		this.position = position;
	}

	/**
	 * 判断中转缓冲区在当前位置和限制之间是否有任何元素。
	 * 
	 * @return
	 */
	protected boolean hasRemaining() {
		return position < limit;
	}

	/**
	 * 设置中转缓冲区的限制.
	 * 
	 * @param limit
	 */
	protected void limit(int limit) {
		this.limit = limit;
	}

	/**
	 * 获取中转缓冲区.
	 * 
	 * @return
	 */
	protected byte[] getReaderBuffer() {
		return readerBuffer;
	}

	/**
	 * 设置中转缓冲区.
	 * 
	 * @param readerBuffer
	 */
	protected void setReaderBuffer(byte[] readerBuffer) {
		this.readerBuffer = readerBuffer;
	}

	/**
	 * 获取中转缓冲区大小.
	 * 
	 * @return
	 */
	protected int getBufferSize() {
		return bufferSize;
	}

	/**
	 * 获取要读取的文件.
	 * 
	 * @return
	 */
	protected File getFile() {
		return file;
	}

	/**
	 * 设置要读取的文件.
	 * 
	 * @param file
	 */
	protected void setFile(File file) {
		this.file = file;
	}

	/**
	 * 读取数据到中转缓冲区.
	 */
	protected abstract void readBuffer();

	protected CharsetDecoder getDecoder() {
		return decoder;
	}

}
