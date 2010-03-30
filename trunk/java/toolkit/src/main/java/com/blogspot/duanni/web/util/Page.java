package com.blogspot.duanni.web.util;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 包装springside的page工具类.添加jdbcTemplate查询结果支持.
 */
public class Page<T> {

	private org.springside.modules.orm.Page<T> springsidePage;

	/** jdbcTemplate查询结果 */
	private List<Map<String, Object>> resultList;

	/** jdbcTemplate查询结果 */
	private Map<String, Object> resultMap;

	protected Logger logger = LoggerFactory.getLogger(getClass());

	// 构造函数
	public Page() {
		springsidePage = new org.springside.modules.orm.Page<T>(10);
	}

	/**
	 * @param view
	 * @param springsidePage
	 */
	public Page(int pageSize) {
		springsidePage = new org.springside.modules.orm.Page<T>(pageSize);
	}

	public Page<T> valueOf(org.springside.modules.orm.Page<T> springsidePage) {
		this.springsidePage = springsidePage;
		return this;
	}

	/**
	 * 原始springside page对象.
	 * 
	 * @return the springsidePage
	 */
	public org.springside.modules.orm.Page<T> sourceSpringsidePage() {
		return springsidePage;
	}

	/**
	 * jdbcTemplate结果.
	 */
	public void setResultList(List<Map<String, Object>> resultList) {
		this.resultList = resultList;
	}

	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}

	public List<Map<String, Object>> getResultList() {
		return resultList;
	}

	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	// 代理springside方法
	public int getFirst() {
		return springsidePage.getFirst();
	}

	public int getNextPage() {
		return springsidePage.getNextPage();
	}

	public String getOrder() {
		return springsidePage.getOrder();
	}

	public String getOrderBy() {
		return springsidePage.getOrderBy();
	}

	public int getPageNo() {
		return springsidePage.getPageNo();
	}

	public int getPageSize() {
		return springsidePage.getPageSize();
	}

	public int getPrePage() {
		return springsidePage.getPrePage();
	}

	public List<T> getResult() {
		return springsidePage.getResult();
	}

	public long getTotalCount() {
		return springsidePage.getTotalCount();
	}

	public long getTotalPages() {
		return springsidePage.getTotalPages();
	}

	public boolean isAutoCount() {
		return springsidePage.isAutoCount();
	}

	public boolean isHasNext() {
		return springsidePage.isHasNext();
	}

	public boolean isHasPre() {
		return springsidePage.isHasPre();
	}

	public boolean isOrderBySetted() {
		return springsidePage.isOrderBySetted();
	}

	public void setAutoCount(boolean autoCount) {
		springsidePage.setAutoCount(autoCount);
	}

	public void setOrder(String order) {
		springsidePage.setOrder(order);
	}

	public void setOrderBy(String orderBy) {
		springsidePage.setOrderBy(orderBy);
	}

	public void setPageNo(int pageNo) {
		springsidePage.setPageNo(pageNo);
	}

	public void setPageSize(int pageSize) {
		springsidePage.setPageSize(pageSize);
	}

	public void setResult(List<T> result) {
		springsidePage.setResult(result);
	}

	public void setTotalCount(long totalCount) {
		springsidePage.setTotalCount(totalCount);
	}
}
