package com.blogspot.duanni.io;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * 用java nio实现的文件随机读取类.
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public class NioBigFileUtils extends AbstractBigFileUtils {

	private AccessMode accessMode;
	private FileChannel fileChannel;
	private MappedByteBuffer mappedByteBuffer;

	/**
	 * @param decoderCharsetName
	 *            解码字符集
	 */
	public NioBigFileUtils(String decoderCharsetName) {
		this(null, Charset.forName(decoderCharsetName));
	}

	/**
	 * @param path
	 * @param decoderCharsetName
	 *            解码字符集
	 */
	public NioBigFileUtils(String path, String decoderCharsetName) {
		this(new File(path), Charset.forName(decoderCharsetName));
	}

	/**
	 * @param file
	 *            要读取的文件
	 * @param decoderCharsetName
	 *            解码字符集
	 */
	public NioBigFileUtils(File file, String decoderCharsetName) {
		this(file, Charset.forName(decoderCharsetName));
	}

	/**
	 * @param file
	 *            要读取的文件
	 * @param decoder
	 *            解码器
	 */
	public NioBigFileUtils(File file, Charset decoder) {
		this(file, decoder, AccessMode.R);
	}

	/**
	 * @param file
	 *            要读取的文件
	 * @param decoder
	 *            解码器
	 * @param mode
	 *            访问模式
	 */
	private NioBigFileUtils(File file, Charset decoder, AccessMode mode) {
		super(file, decoder);
		this.accessMode = mode;
	}

	/**
	 * 打开文件,映射到内存.
	 */
	public void open() {
		// 映射到内存 http://jiangzhengjun.javaeye.com/blog/515745
		if (fileChannel != null)
			return;
		try {
			fileChannel = new RandomAccessFile(getFile(), accessMode.getMode()).getChannel();
			mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, (int) getFile().length());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 打开指定的文件.
	 * 
	 * @param file
	 */
	public void open(File file) {
		if (fileChannel != null)
			close();
		// fileChannel = null;
		setFile(file);
		open();
	}

	/**
	 * 关闭文件.
	 */
	public void close() {
		if (fileChannel != null && fileChannel.isOpen()) {
			try {
				fileChannel.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		fileChannel = null;
		setReaderBuffer(null);
	}

	/**
	 * 
	 */
	protected void readBuffer() {
		int bufferSize = getBufferSize();
		if (!mappedByteBuffer.hasRemaining()) {
			bufferSize = 0;
			limit(0);
			position(0);
			return;
		}
		if (mappedByteBuffer.remaining() < bufferSize) {
			bufferSize = mappedByteBuffer.remaining();
		}

		limit(bufferSize);
		position(0);
		setReaderBuffer(new byte[bufferSize]);
		mappedByteBuffer.get(getReaderBuffer());
	}

	/**
	 * 文件访问模式. {@link RandomAccessFile#RandomAccessFile(File, String)}
	 * 
	 * @author duanni
	 * @author Last changed by: $Author$
	 * @version $Revision$ $Date$
	 * @since <t-version>
	 */
	public enum AccessMode {
		R {
			@Override
			public String getMode() {
				return "r";
			}
		},
		RW {
			@Override
			public String getMode() {
				return "rw";
			}
		},
		RWS {
			@Override
			public String getMode() {
				return "rws";
			}
		},
		RWD {
			@Override
			public String getMode() {
				return "rwd";
			}
		};

		abstract String getMode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractBigFileUtils#decode(byte[])
	 */
	@Override
	protected String decode(byte[] bArr) {
		ByteBuffer bbLine = ByteBuffer.allocate(bArr.length);
		bbLine.put(bArr);
		bbLine.flip();

		CharBuffer out;
		try {
			out = getDecoder().decode(bbLine);
			String result = out.toString();
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
