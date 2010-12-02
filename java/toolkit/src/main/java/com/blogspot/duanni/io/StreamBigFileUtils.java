package com.blogspot.duanni.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public class StreamBigFileUtils extends AbstractBigFileUtils {

	private BufferedInputStream bufferedInputStream;

	/**
	 * @param decoderCharsetName
	 */
	public StreamBigFileUtils(String decoderCharsetName) {
		super(decoderCharsetName);
	}

	/**
	 * @param file
	 * @param decoder
	 */
	public StreamBigFileUtils(File file, Charset decoder) {
		super(file, decoder);
	}

	/**
	 * @param file
	 * @param decoderCharsetName
	 */
	public StreamBigFileUtils(File file, String decoderCharsetName) {
		super(file, decoderCharsetName);
	}

	/**
	 * @param path
	 * @param decoderCharsetName
	 */
	public StreamBigFileUtils(String path, String decoderCharsetName) {
		super(path, decoderCharsetName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see BigFileUtils#open()
	 */
	@Override
	public void open() {
		if (bufferedInputStream != null)
			return;
		try {
			bufferedInputStream = new BufferedInputStream(new FileInputStream(getFile()), getBufferSize());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see BigFileUtils#open(java.io.File)
	 */
	@Override
	public void open(File file) {
		if (bufferedInputStream != null)
			close();
		setFile(file);
		open();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see BigFileUtils#close()
	 */
	@Override
	public void close() {
		if (bufferedInputStream != null) {
			try {
				bufferedInputStream.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		bufferedInputStream = null;
		setReaderBuffer(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractBigFileUtils#readBuffer()
	 */
	@Override
	protected void readBuffer() {
		try {
			int bufferSize = getBufferSize();

			if (bufferedInputStream.available() <= 0) {
				bufferSize = 0;
				limit(0);
				position(0);
				return;
			}
			if (bufferedInputStream.available() < bufferSize) {
				bufferSize = bufferedInputStream.available();
			}

			limit(bufferSize);
			position(0);
			setReaderBuffer(new byte[bufferSize]);
			bufferedInputStream.read(getReaderBuffer());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractBigFileUtils#decode(byte[])
	 */
	@Override
	protected String decode(byte[] bArr) {
		return new String(bArr, getDecoder().charset());
	}

}
