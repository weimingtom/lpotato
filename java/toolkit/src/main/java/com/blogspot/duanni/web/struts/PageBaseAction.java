package com.blogspot.duanni.web.struts;

import com.blogspot.duanni.web.util.Struts2Page;

/**
 * 带有分页的action.
 */
public abstract class PageBaseAction<T> extends BaseAction<T> {

	/**  */
	private static final long serialVersionUID = 7000036216731302503L;

	/** 分页,默认每页10条 */
	protected Struts2Page<T> page = new Struts2Page<T>();

	/**
	 * @return the page
	 */
	public Struts2Page<T> getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(Struts2Page<T> page) {
		this.page = page;
	}

	/**
	 * 获取当前页
	 * 
	 * @return the pageNo
	 */
	public int getP() {
		return page.getPageNo();
	}

	/**
	 * 设置当前页
	 * 
	 * @param pageNo the pageNo to set
	 */
	public void setP(int pageNo) {
		page.setPageNo(pageNo);
	}
}
