package com.blogspot.duanni.web.util;

import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.mapper.ActionMapping;

import com.opensymphony.xwork2.ActionContext;

/**
 * 分页struts2实现,生成html代码.<br/>
 * <b>分页css</b>
 * 
 * <pre>
 * #pagination {
 * 	padding: 5px;
 * 	float: right;
 * }
 * 
 * #pagination select {
 * 	margin-bottom:-2px;
 * 	padding:1px 3px;
 * 	border: 1px solid #006699;
 * 	color: #000;
 * 
 * }
 * 
 * #pagination a, #pagination a:link,#pagination a:visited {
 * 	padding: 2px 5px 2px 5px;
 * 	margin: 2px;
 * 	border: 1px solid #aaaadd;
 * 	text-decoration: none;
 * 	color: #006699;
 * }
 * 
 * #pagination a:hover,#pagination a:active {
 * 	border: 1px solid #006699;
 * 	color: #000;
 * 	text-decoration: none;
 * }
 * 
 * #pagination span {
 * 	padding: 2px 5px 2px 5px;
 * 	margin: 2px;
 * 	border: 1px solid #eee;
 * }
 * 
 * #pagination span.current {
 * 	padding: 2px 5px 2px 5px;
 * 	margin: 2px;
 * 	border: 1px solid #006699;
 * 	font-weight: bold;
 * 	background-color: #006699;
 * 	color: #FFF;
 * }
 * </pre>
 */
public class Struts2Page<T> extends Page<T> {

	/** html缓存 */
	private String pageHtmlBuffer;

	/** 请求uri缓存 */
	private String requestURIcache;

	/** 分页参数名,默认为p. /?p=2 */
	private String pageName = "p";

	/** 被省略的分页html代码 */
	private static final String GAP = "<span class=\"gap\">...</span>";

	/** 当前页码的html片断 */
	private static final String CURRENTCLASS = " <span class=\"current\">";

	/**
	 * 
	 */
	public Struts2Page() {
		super();
	}

	/**
	 * @param pageSize
	 */
	public Struts2Page(int pageSize) {
		super(pageSize);
	}

	@Override
	public Struts2Page<T> valueOf(org.springside.modules.orm.Page<T> springsidePage) {
		super.valueOf(springsidePage);
		return this;
	}

	/**
	 * 获取此分页的html.
	 */
	public String html() {
		StringBuilder sb = new StringBuilder();
		sb.append("<div id=\"pagination\" class=\"pagination\"/>\n");
		// 上一页
		sb.append(getPreviousPageHTML());
		// 中间页码
		sb.append(getMiddlePageHTML());
		// 下一页
		sb.append(getNextPageHTML());
		// 下拉选择框
		sb.append(getSelectPageHTML());
		sb.append("</div>");
		return sb.toString();
	}

	/**
	 * 获取前一页的html.
	 * 
	 * @return
	 */
	private String getPreviousPageHTML() {
		String previousPageHtml = "";
		if (!isHasPre() && getTotalPages() > 1) {
			previousPageHtml = "<span> < </span>\n";
		} else if (isHasPre() && getTotalPages() > 1) {// 当前页不是第一页
			previousPageHtml = "<a class=\"prev_page\" href=\"" + getRequestURIAndParam() + getPrePage()
					+ "\"> < </a>\n";
		}
		return previousPageHtml;
	}

	/**
	 * 获取下一页的HTML.
	 * 
	 * @return
	 */
	private String getNextPageHTML() {
		String lastPageHtml = "";
		if (!isHasNext() && getTotalPages() > 1) {
			lastPageHtml = "<span> > </span>";
		} else if (isHasNext() && getTotalPages() > 1) {// 当前页不是最后一页
			lastPageHtml = "<a class=\"next_page\" href=\"" + getRequestURIAndParam() + getNextPage()
					+ "\"> > </a>";
		}
		return lastPageHtml;
	}

	/**
	 * 获取中间的HTML.
	 * 
	 * @return
	 */
	private String getMiddlePageHTML() {
		StringBuilder sb = new StringBuilder();
		if (getTotalPages() > 1 && getTotalPages() < 8) {// 页数大于1显示页码.
			// 如果最多7页
			for (int i = 1; i <= getTotalPages(); i++) {
				if (getPageNo() == i) {// 如果是当前页
					sb.append(CURRENTCLASS + getPageNo() + "</span>\n");
					continue;
				}
				sb.append("<a href=\"" + getRequestURIAndParam() + i + "\">" + i + "</a>\n");
			}
		} else if (getTotalPages() >= 8) {// 大于7个页码
			sb.append(getPrevious2PageHTML());// 最前面2个页码和当前页的前一个页码
			sb.append(CURRENTCLASS + getPageNo() + "</span>\n");// 当前页
			sb.append(getNext2PageHTML());// 最后面2个页码和当前页的后一个页码
		}
		return sb.toString();
	}

	/**
	 * 最后面2个页码和当前页的后一个页码.
	 * 
	 * @return
	 */
	private String getNext2PageHTML() {
		int currPageNo = getPageNo();
		StringBuilder sb = new StringBuilder();
		if (getTotalPages() <= currPageNo)
			return "";
		if (getTotalPages() - currPageNo >= 1) {// 当前页的后一个页码
			sb.append("<a href=\"" + getRequestURIAndParam() + getNextPage() + "\">" + getNextPage()
					+ "</a>\n");
			if (getTotalPages() - currPageNo >= 4) {
				sb.append(GAP);// 大于3加...
			}
		}
		if (getTotalPages() - currPageNo >= 3) {// 当前页的后一个页码, 最后一个页码,倒数第二个页码
			sb.append("<a href=\"" + getRequestURIAndParam() + (getTotalPages() - 1) + "\">"
					+ (getTotalPages() - 1) + "</a>\n");
		}
		if (getTotalPages() - currPageNo >= 2) {// 当前页的后一个页码,最后一个页码
			sb.append("<a href=\"" + getRequestURIAndParam() + getTotalPages() + "\">" + getTotalPages()
					+ "</a>\n");
		}
		return sb.toString();
	}

	/**
	 * 最前面2个页码和当前页的前一个页码.
	 * 
	 * @return
	 */
	private String getPrevious2PageHTML() {
		int currPageNo = getPageNo();
		StringBuilder sb = new StringBuilder();
		if (currPageNo <= 1)// 如果是第一页
			return "";
		if (currPageNo - 1 >= 2) {// 当前页的前一页,第一页
			sb.append("<a href=\"" + getRequestURIAndParam() + 1 + "\">" + 1 + "</a>\n");
		}
		if (currPageNo - 1 >= 3) {// 当前页的前一页,第一页,第二页
			sb.append("<a href=\"" + getRequestURIAndParam() + 2 + "\">" + 2 + "</a>\n");
		}
		if (currPageNo - 1 >= 1) {// 当前页的前一页
			if (currPageNo - 1 >= 4) {
				sb.append(GAP);
			}
			sb.append("<a href=\"" + getRequestURIAndParam() + getPrePage() + "\">" + getPrePage()
					+ "</a>\n");
		}

		return sb.toString();
	}

	/**
	 * 分页下拉列表.
	 * 
	 * @return
	 */
	private String getSelectPageHTML() {
		String selectPageHtml = "";
		if (getTotalPages() > 1) {
			selectPageHtml = "\n<select onchange=\"location.href=this.value\">";
			for (int i = 1; i <= getTotalPages(); i++) {
				selectPageHtml += "<option ";
				if (i == getPageNo()) {
					selectPageHtml += "selected=\"selected\" ";
				}
				selectPageHtml += "value=\"" + getRequestURIAndParam() + i + "\">" + i + "</option>";
			}
			selectPageHtml += "</select>";
		}
		return selectPageHtml;
	}

	/**
	 * 获取请求参数列表.
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private Map getRequestParameterMap() {
		return ServletActionContext.getRequest().getParameterMap();// HttpServletRequest
																	// parametersMap
	}

	/**
	 * 获取请求URI.不带参数. /contextPath/path.ext
	 * 
	 * @return
	 */
	private String getRequestURI() {
		String uri = ServletActionContext.getRequest().getContextPath();
		uri += getUriFromActionMapping(ServletActionContext.getActionMapping());
		logger.debug("strutsPage实现,拼接好的URI:[{}]", uri);
		return uri;
	}

	/**
	 * @see org.apache.struts2.dispatcher.mapper.DefaultActionMapper#getUriFromActionMapping(org.apache.struts2
	 *      .dispatcher .mapper.ActionMapping)
	 */
	private String getUriFromActionMapping(ActionMapping mapping) {
		StringBuilder uri = new StringBuilder();

		if (mapping.getNamespace() != null) {
			uri.append(mapping.getNamespace());
			if (!"/".equals(mapping.getNamespace())) {
				uri.append("/");
			}
		}
		String name = mapping.getName();

		if (name.indexOf('?') != -1) {
			name = name.substring(0, name.indexOf('?'));
		}
		uri.append(name);

		if (null != mapping.getMethod() && !"".equals(mapping.getMethod())) {
			uri.append("!").append(mapping.getMethod());
		}

		String extension = mapping.getExtension();
		if (extension == null) {
			// Look for the current extension, if available
			ActionContext context = ActionContext.getContext();
			if (context != null) {
				ActionMapping orig = (ActionMapping) context.get(ServletActionContext.ACTION_MAPPING);
				if (orig != null) {
					extension = orig.getExtension();
				}
			}
		}

		if (extension != null) {
			if (extension.length() == 0 || (extension.length() > 0 && uri.indexOf('.' + extension) == -1)) {
				if (extension.length() > 0) {
					uri.append(".").append(extension);
				}
			}
		}

		return uri.toString();
	}

	/**
	 * /contextPath/url.ext?[param=value&]p=
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private String getRequestURIAndParam() {
		if (requestURIcache != null)
			return requestURIcache;
		String requestURI = getRequestURI();
		Map parameterMap = getRequestParameterMap();
		StringBuffer queryString = new StringBuffer();
		for (Iterator iterator = parameterMap.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry entry = (Map.Entry) iterator.next();
			String name = (String) entry.getKey();
			Object value = entry.getValue();
			if (value instanceof Iterable) {
				for (Iterator iter = ((Iterable) value).iterator(); iter.hasNext();) {
					Object paramValue = iter.next();
					queryString.append(buildParameterSubString(name, paramValue.toString()));
				}
			} else if (value instanceof Object[]) {
				Object[] array = (Object[]) value;
				for (Object element : array) {
					queryString.append(buildParameterSubString(name, element.toString()));
				}
			} else {
				queryString.append(buildParameterSubString(name, value.toString()));
			}
		}

		requestURIcache = requestURI + "?" + queryString + getPageName() + "=";
		logger.debug("拼接好的requestURIcache:[{}]", requestURIcache);
		return requestURIcache;
	}

	private String buildParameterSubString(String name, String value) {
		String result = "";
		if (name.equals(getPageName())) {
			logger.debug("忽略分页参数:{}={}", name, value);
			return result;
		}
		result = name + "=";
		try {
			result += URLEncoder.encode(value, "UTF-8");
		} catch (Exception e) {
			logger.warn("URL 参数编码异常,返回原值:{}", value);
			result += value;
		}
		return result + "&";
	}

	/**
	 * 返回组装好的html代码.需要在页面用css控制显示效果.有缓存.
	 */
	@Override
	public String toString() {
		if (pageHtmlBuffer == null) {
			pageHtmlBuffer = html();
		}
		return pageHtmlBuffer;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
}
