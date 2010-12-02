package com.blogspot.duanni.lucene.util;

import com.blogspot.duanni.web.util.Page;

/**
 * 专用于搜索的分页.
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public class SearchPage<T> extends Page<T> {
	/** */
	private static final long serialVersionUID = 5468285335711527703L;
	/**
	 * 搜索耗时.
	 */
	private long searchTime;

	/**
	 * 
	 */
	public SearchPage() {
		super();
	}

	/**
	 * @param pageSize
	 */
	public SearchPage(int pageSize) {
		super(pageSize);
	}

	public long getSearchTime() {
		return searchTime;
	}

	public void setSearchTime(long searchTime) {
		this.searchTime = searchTime;
	}

}
