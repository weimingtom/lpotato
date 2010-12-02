package com.blogspot.duanni.lucene.model;

/**
 * 日志实体.每条日志的组成.
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public class LogsEntry {
	public static final String INDEX_FILED_TIME = "time";

	public static final String INDEX_FILED_MODEL = "model";

	public static final String INDEX_FILED_CONTENT = "content";

	/** 生成时间 */
	private String time;

	/** 所属模块 */
	private String model;

	/** 日志主体 */
	private String content;

	/**
	 * 
	 */
	public LogsEntry() {
	}

	/**
	 * @param time
	 * @param model
	 * @param contents
	 */
	public LogsEntry(String time, String model, String contents) {
		this.time = time;
		this.model = model;
		this.content = contents;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String project) {
		this.model = project;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String contents) {
		this.content = contents;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{time : ");
		builder.append(time);
		builder.append(", model : ");
		builder.append(model);
		builder.append(", content : ");
		builder.append(content);
		builder.append("}");
		return builder.toString();
	}
}
