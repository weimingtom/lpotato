package com.blogspot.duanni.util;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public class ByteTest {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	public void byteTest() throws Exception {
		byte[] sourceByte = { -26, -75, -117, -24, -81, -107, 49, 76, 105, 110, 101, 10 };
		String sourceCoder = "UTF-8";
		String line1 = "测试1Line\n";
		byte[] line1Byte = line1.getBytes(sourceCoder);
		logger.debug("byteArray => {}", Arrays.toString(line1Byte));
		logger.debug("sourceByte convert to String => {}", new String(sourceByte, sourceCoder));
		Assert.assertArrayEquals(sourceByte, line1Byte);
	}

	@Test
	public void byteBufferCoderTest() throws Exception {
		Charset targetSet = Charset.forName("UTF-8");
		CharsetDecoder targetCoder = targetSet.newDecoder();

		byte[] sourceByte = { -26, -75, -117, -24, -81, -107, 49, 76, 105, 110, 101, 10 };
		ByteBuffer sourceBB = ByteBuffer.allocate(sourceByte.length);
		sourceBB.put(sourceByte);
		sourceBB.flip();

		CharBuffer out = targetCoder.decode(sourceBB);
		String result = out.toString();
		logger.info(result);
		Assert.assertEquals("测试1Line\n", result);
	}

}
