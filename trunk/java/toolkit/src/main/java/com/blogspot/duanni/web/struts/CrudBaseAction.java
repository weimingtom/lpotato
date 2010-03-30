package com.blogspot.duanni.web.struts;

/**
 * Struts2中典型CRUD Action的规范基类. 声明对Preparable,ModelDriven接口的使用,并规范了CRUD函数和返回值的命名.
 */
public abstract class CrudBaseAction<T> extends PageBaseAction<T> {

	/** */
	private static final long serialVersionUID = -101975463048859732L;

	/**
	 * 进行增删改操作后,以redirect方式重新打开action默认页的result名.
	 */
	public static final String RELOAD = "reload";

	// CRUD函数 //

	/**
	 * Action函数,新增或修改Entity. 建议return RELOAD.
	 */
	public abstract String save();

	/**
	 * Action函数,删除Entity. 建议return RELOAD.
	 */
	public abstract String delete();

	/**
	 * Action函数,修改Entity.二次绑定参数会绑定web页面恶意注入的参数.这里不使用 springside提供的save方法.手动在update方法里面设置参数. return RELOAD.
	 */
	public abstract String update();

	/**
	 * 在save()前执行二次绑定.
	 */
	public void prepareSave() {
		prepareModel();
	}

	/**
	 * 在input()前执行二次绑定.
	 */
	public void prepareInput() {
		prepareModel();
	}

	/**
	 * 在update()前执行二次绑定.
	 */
	public void prepareUpdate() {
		prepareModel();
	}

	/**
	 * input,save,update预备数据. 等同于prepare()的内部函数,供prepardMethodName()函数调用.
	 */
	protected abstract void prepareModel();
}
