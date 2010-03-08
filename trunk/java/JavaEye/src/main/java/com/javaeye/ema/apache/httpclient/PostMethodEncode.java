package com.javaeye.ema.apache.httpclient;

import org.apache.commons.httpclient.methods.PostMethod;

import com.javaeye.ema.util.Encode;

/**
 * 指定编码post请求.使用指定的编码.
 * 
 * @author 路春晖
 * @version $1.5, 2008-3-10 2008-3-19
 * @since JDK5
 */
public class PostMethodEncode extends PostMethod {
	private Encode postEncode;
	
	/**
	 * 使用指定的url和{@link Encode}构造一个PostMethod.
	 * @param url url
	 * @param postEncode 发送post请求的编码格式.
	 */
	public PostMethodEncode(String url, Encode postEncode) {
		super(url);
		this.postEncode = postEncode;
	}

	public String getRequestCharSet() {
		// return super.getRequestCharSet();
		return postEncode.getEncode();
	}
}
