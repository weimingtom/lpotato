package com.blogspot.duanni.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 国际化及资源国际化. Servlet Filter implementation class I18nFilter
 */
public class I18nFilter implements Filter {
	/** request 请求中的 i18n 参数名,格式 zh-CN,en-US,zh,en */
	public static final String REQUEST_I18N_PARAM = "hl";

	/** 区域国际化资源 */
	public static final String REQUEST_CUSTOM_I18N_RESOURCE = "customI18nResource";

	private static final Logger LOG = LoggerFactory.getLogger(I18nFilter.class);

	private static String customResourceSuffix;

	/**
	 * Default constructor.
	 */
	public I18nFilter() {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		LOG.debug("i18nFilter :{");
		String i18n = request.getParameter(REQUEST_I18N_PARAM);// 先取request请求
		if (i18n == null || "".equals(i18n)) {
			i18n = (String) request.getSession().getAttribute(UserDetailsHolder.USER_I18N);
			if (i18n == null || "".equals(i18n)) {
				i18n = StringUtils.toLanguageTag(request.getLocale());
				LOG.debug("browser locale.");
				setUserLocale(request, i18n);
			}
		} else {
			setUserLocale(request, i18n);
		}
		LOG.debug("i18nFilter }");
		chain.doFilter(request, resp);
	}

	private void setUserLocale(HttpServletRequest request, String i18n) {
		String customI18nResource = ProjectContextHolder.getContextPath() + "/i18n/custom/" + i18n + "/"
				+ customResourceSuffix;
		request.getSession().setAttribute(REQUEST_CUSTOM_I18N_RESOURCE, customI18nResource);
		request.getSession().setAttribute(UserDetailsHolder.USER_I18N, i18n);
		LOG.debug("customI18nResource => {}", customI18nResource);
		LOG.debug("current locale:[{}]", i18n);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		customResourceSuffix = (String) fConfig.getServletContext().getAttribute(
				InitGxtListener.APPLICATION_CUSTOM_RESOURCE_SUFFIX);
		LOG.debug("filter init : customResourceSuffix => {}", customResourceSuffix);
	}

}
