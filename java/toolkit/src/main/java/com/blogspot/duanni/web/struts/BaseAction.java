package com.blogspot.duanni.web.struts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springside.modules.web.struts2.Struts2Utils;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * Action基类.
 */
public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T>, Preparable {

	/**
	 * 全局成功页面.
	 */
	public static final String GLOBAL_SUCCESS = "globalSuccess";

	/** */
	private static final long serialVersionUID = -1712704704392114730L;

	/** 非静态方法的子类记录日志可以直接使用此属性 */
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	/** action 对应的主要实体 */
	protected T entity;

	/** 主键 */
	protected Long id;

	/**
	 * 输出json.
	 * 
	 * @param result
	 */
	public void renderJson(String result) {
		renderJson(result, "");
	}

	/**
	 * 输出json.
	 * 
	 * @param result
	 * @param cause
	 */
	public void renderJson(String result, String cause) {
		String json = "{\"result\" : \"" + result + "\", \"cause\" : \"" + cause + "\"}";
		Struts2Utils.renderText(json);
	}

	public T getModel() {
		return entity;
	}

	/**
	 * 实现空的prepare()函数,屏蔽所有Action函数公共的二次绑定.
	 */
	public void prepare() {
		// 忽略
	}

	/**
	 * 功能首页.
	 * 
	 * @return
	 */
	public String index() {
		return SUCCESS;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

}
