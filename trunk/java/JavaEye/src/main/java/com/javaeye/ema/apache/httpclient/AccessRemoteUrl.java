/*
 * (#)AccessRemoteUrl.java 1.0 2008-1-15 2008-3-10 
 */
package com.javaeye.ema.apache.httpclient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import com.javaeye.ema.util.Encode;

/**
 * 远程请求URL,GET和POST方式,保存cookie,保存session. 第一次调用使用
 * get请求获取cookie,如果请求不产生cookie的页面,则抛出异常. 例如请求一个css文件.
 * 
 * @author 路春晖
 * @version $1.6, 2008-1-15 2008-3-19
 * @since JDK5
 */
public class AccessRemoteUrl {
	private static final Logger logger = Logger
			.getLogger(AccessRemoteUrl.class);

	/** cookies, List的每个元素为字符串数组,此数组长度为2.结构{cookieName, cookieValue} */
	private List<String[]> cookieList = new ArrayList<String[]>();

	/** User Agent */
	private String userAgent;

	/** IE7 User-Agent */
	public static final String IE7_User_Agent = "Mozilla/4.0 (compatible; "
			+ "MSIE 7.0; Windows NT 5.1; WPS; Mozilla/4.0(Compatible Mozilla/4.0EmbeddedWB- 14.59  "
			+ "from: http://bsalsa.com/ ; .NET CLR 2.0.50727)";

	/**
	 * post请求.
	 * 
	 * @param url
	 *            请求的url
	 * @param headers
	 *            请求要设置的Header信息
	 * @return 请求的页面内容.
	 * @throws IOException
	 *             网络异常
	 */
	public byte[] simpleGet(String url, String[][] headers) throws IOException {
		logger.info("get请求url:[" + url + "]");
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		// 创建GET方法的实例
		GetMethod getMethod = new GetMethod(url);
		// 设置 User-Agent
		setRequestHeaders(getMethod, new String[][] { { "User-Agent",
				getUserAgent() } });
		// 设置cookie
		if (!cookieList.isEmpty())
			setRequestHeaders(getMethod, new String[][] { { "Cookie",
					getCookieAsString() } });
		// 设置传入的Header
		setRequestHeaders(getMethod, headers);
		try {
			// 执行getMethod
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				logger.info("请求失败, 返回代码:[" + getMethod.getStatusLine() + "]");
			}
			// 获取cookie,如果有的话.
			if (cookieList.isEmpty()) {
				Header header = getMethod.getResponseHeader("Set-Cookie");
				if (header != null) {
					String cookies = header.getValue();
					String cookie[] = new String[2];
					cookie[0] = cookies.substring(cookies.indexOf("=") + 1,
							cookies.indexOf(";"));
					cookie[1] = cookies.substring(0, cookies.indexOf("="));
					cookieList.add(cookie);
				}
			}
			return getMethod.getResponseBody();
		} catch (IOException e) {
			logger.error("get发生网络异常.\n" + e.getMessage());
			throw e;
		} finally {
			// 释放连接
			getMethod.releaseConnection();
		}
	}

	/**
	 * post请求.
	 * 
	 * @param url
	 *            请求的url
	 * @param headers
	 *            请求要设置的Header信息
	 * @return 请求的页面内容.
	 * @throws IOException
	 *             网络异常
	 */
	private GetMethod simpleGet(String url, String[][] headers,
			GetMethod getMethod) throws IOException {

		return getMethod;
	}

	/**
	 * post请求.以utf8编码发送post请求
	 * 
	 * @param url
	 *            请求的url
	 * @param nameValues
	 *            要传递的post参数
	 * @param headers
	 *            要设置的Header
	 * @return 请求的页面内容.
	 * @throws IOException
	 *             网络异常
	 */
	public byte[] simplePost(String url, String[][] nameValues,
			String[][] headers) throws IOException {
		return simplePost(url, nameValues, headers, Encode.UTF8);
	}

	/**
	 * post请求.
	 * 
	 * @param url
	 *            请求的url
	 * @param nameValues
	 *            要传递的post参数
	 * @param headers
	 *            要设置的Header
	 * @param postEncode
	 *            请求编码.已此编码格式发送请求.
	 * @return 请求的页面内容.
	 * @throws IOException
	 *             网络异常
	 */
	public byte[] simplePost(String url, String[][] nameValues,
			String[][] headers, Encode postEncode) throws IOException {
		logger.info("post请求:[" + url + "]");
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod;
		if (!postEncode.equals(Encode.UTF8))
			postMethod = new PostMethodEncode(url, postEncode);
		else
			postMethod = new PostMethod(url);
		// 填入各个表单域的值
		setRequestNameValues(postMethod, nameValues);
		// 设置请求头部
		setRequestHeaders(postMethod, headers);

		// 执行postMethod
		int statusCode;
		try {
			statusCode = httpClient.executeMethod(postMethod);

			// HttpClient对于要求接受后继服务的请求，象POST和PUT等不能自动处理转发
			// 301或者302
			if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
					|| statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
				// 从头中取出转向的地址
				Header locationHeader = postMethod
						.getResponseHeader("location");
				String location = null;
				if (locationHeader != null) {
					location = locationHeader.getValue();
					logger.info("页面跳转到:[" + location + "]");
				} else {
					logger.info("Location field value is null.");
				}
			}
			// 获取 cookie
			Header header = postMethod.getResponseHeader("Set-Cookie");
			if (header != null) {
				String cookies = header.getValue();
				String[] cookie = new String[2];
				cookie[0] = cookies.substring(cookies.indexOf("=") + 1, cookies
						.indexOf(";"));
				cookie[1] = cookies.substring(0, cookies.indexOf("="));
				cookieList.add(cookie);
			}
			logger.info("post statusCode:[" + statusCode + "]");
			return postMethod.getResponseBody();
		} catch (IOException e) {
			logger.error("请求异常." + e.getMessage());
			throw e;
		} finally {
			postMethod.releaseConnection();// 释放连接
		}
	}

	/**
	 * 设置请求头部.
	 * 
	 * @param postMethod
	 * @param headers
	 */
	private void setRequestHeaders(HttpMethodBase requestMethod,
			String[][] headers) {
		if (headers == null || headers.length == 0)
			return;
		for (int i = 0; i < headers.length; i++) {
			String[] header = headers[i];
			requestMethod.setRequestHeader(header[0], header[1]);
		}
	}

	/**
	 * 设置请求参数.
	 * 
	 * @param postMethod
	 * @param nameValues
	 */
	private void setRequestNameValues(PostMethod postMethod,
			String[][] nameValues) {
		if (nameValues == null || nameValues.length == 0)
			return;

		NameValuePair[] parametersBody = new NameValuePair[nameValues.length];
		for (int i = 0; i < nameValues.length; i++) {
			String[] nameValue = nameValues[i];
			parametersBody[i] = new NameValuePair(nameValue[0], nameValue[1]);
		}
		// 将表单的值放入postMethod中
		postMethod.setRequestBody(parametersBody);
	}

	/**
	 * 获取userAgent
	 * 
	 * @return the userAgent
	 */
	public String getUserAgent() {
		if (userAgent == null)
			return IE7_User_Agent;
		return userAgent;
	}

	/**
	 * 设置userAgent
	 * 
	 * @param userAgent
	 *            the userAgent to set
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	/**
	 * @return the cookieList
	 */
	public List<String[]> getCookieList() {
		return cookieList;
	}

	/**
	 * @param cookieList
	 *            the cookieList to set
	 */
	public void setCookieList(List<String[]> cookieList) {
		this.cookieList = cookieList;
	}

	/**
	 * 返回当前所有cookie信息.以字符串形式返回.如果不存在cookie返回空. 形式如下:<br>
	 * cookieName=cookieValue[;cookieName1=cookieValue1]
	 * 
	 * @return 返回当前的cookie
	 */
	public String getCookieAsString() {
		String cookies = "";
		for (int i = 0; i < cookieList.size(); i++) {
			cookies = cookieList.get(i)[0] + "=" + cookieList.get(i)[1];
			if (cookieList.size() > 1 && i <= cookieList.size() - 1)
				cookies += ";";
		}
		return cookies;
	}

	/**
	 * 测试远程登录
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		AccessRemoteUrl tru = new AccessRemoteUrl();
		String url = "http://211.90.246.113/sp/";// 请求登录页面
		// String htmlSource = new String(tru.simpleGet(url));// 获取页面html源码
		// System.out.println(htmlSource);
		url = "http://211.90.246.113/sp/RandomPic.aspx";// 请求验证码页面
		InputStream byteResponseStream = new ByteArrayInputStream(tru
				.simpleGet(url, null));
		// ImgIdent imgIdent = new ImgIdent(byteResponseStream);
		String random = "";

		// 设置请求参数
		String[][] nameValues = {
				{ "tbSPCode", "99999905" },// 用户域,用户名
				{ "tbSPPwd", "ring8888" },// 密码域, 密码
				{ "txtRandom", random },// 随机码域, 随机码
				{
						"__VIEWSTATE",
						"/wEPDwULLTExODU4NjcwMjRkGAEFHl9fQ29udHJvbHNSZ"
								+ "XF1aXJlUG9zdEJhY2tLZXlfXxYBBQdpYkxvZ2lu" },// 其他
				{ "__EVENTTARGET", "" }, { "__EVENTARGUMENT", "" },
				{ "ibLogin.x", "0" }, { "ibLogin.y", "0" } };
		// 设置头部
		String[][] headers = { { "User-Agent", tru.getUserAgent() },// User-Agent
				{ "Cookie", tru.getCookieAsString() },// half-cookie
				{ "Referer", "http://211.90.246.113/sp/" } };

		String loginurl = "http://211.90.246.113/sp/login.aspx";
		byte[] result = tru.simplePost(loginurl, nameValues, headers);
		// 请求铃声列表
		url = "http://211.90.246.113/sp/Manage/spSelfTone.aspx";
		String[][] headers1 = { { "Cookie", tru.getCookieAsString() } };
		String[][] nameValues1 = {// 请求参数
				{ "__EVENTTARGET", "AspNetPager1" },
				{ "__EVENTARGUMENT", "2" },// 当前页码
				{ "__LASTFOCUS", "" }, { "tbSongName", "" },
				{ "tbDownStart", "" }, { "tbDownEnd", "" },
				{ "tbSingerName", "" }, { "tbTimeStart", "" },
				{ "tbTimeEnd", "" }, { "tbRingCode", "" },
				{ "dropRingState", "0" },
				{ "ucRingCategory$ddlParentCategory", "-1" },
				{ "ucRingCategory$ddlRingCategory", "-1" },
				{ "tbPageSize", "10" } };
		result = tru.simplePost(url, nameValues1, headers1);
		String htmlSource = new String(result, "utf-8");
		System.out.println(htmlSource + "\n" + htmlSource.length());
		// TestCatchRing tcr = new TestCatchRing();
		// tcr.testCR(htmlSource);
	}
}