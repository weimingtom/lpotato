package com.javaeye.ema.util;

import java.io.Serializable;

/**
 * POST请求编码格式信息.以次指定post发送内容的编码格式.
 * @author 路春辉
 * @version $1.0, 2008-3-19 2008-3-19
 * @since JDK5
 */
public final class Encode implements Serializable {

	/** */
	private static final long serialVersionUID = -3071367715294284264L;

	private final String encode;

	static public final Encode UTF8 = createSingleton("UTF-8");

	static public final Encode GB2312 = createSingleton("GB2312");

	public static final Encode GBK = createSingleton("GBK");

	private Encode() {
		this.encode = null;
	}

	/**
	 * @param encode
	 */
	private Encode(String encode) {
		this.encode = encode;
	}

	/**
	 * @param string
	 * @return
	 */
	private static Encode createSingleton(String encode) {
		Encode postEncode = new Encode(encode);
		return postEncode;
	}

	/**
	 * @return the encode
	 */
	public String getEncode() {
		return encode;
	}

	public final String toString() {
		return encode;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((encode == null) ? 0 : encode.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Encode))
			return false;
		Encode other = (Encode) obj;
		if (encode == null) {
			if (other.encode != null)
				return false;
		} else if (!encode.equals(other.encode))
			return false;
		return true;
	}

}
