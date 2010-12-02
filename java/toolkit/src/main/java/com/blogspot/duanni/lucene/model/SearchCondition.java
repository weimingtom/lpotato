package com.blogspot.duanni.lucene.model;

/**
 * * 搜索条件. <b>可以使用的搜索语法:</b>
 * <table>
 * <tr>
 * <th>运算符</th>
 * <th>定义</th>
 * <th>示例</th>
 * </tr>
 * <tr>
 * <th>after:<br/>
 * before:</th>
 * <td>搜索在特定时间段内记录的日志.日期必须采用年-月-日 时:分:秒 的格式(参考:
 * {@link SearchCondition#TIME_FORMATTER})。</td>
 * <td>示例 -<b> after:2004-04-16 before:2004-04-18</b> 含义 - 在 2004 年 4 月 16 日到
 * 2004 年 4 月 18 日之间记录的日志。 更确切地说，是在 2004 年 4 月 16 日上午12：00（或 00:00）到 2004 年 4 月
 * 18 日上午12：00（或 00:00）之间记录的日志。</td>
 * </tr>
 * <tr>
 * <th>in:</th>
 * <td>在指定的模块中搜索日志.可搜索模块:地图,逻辑,验证,任务</td>
 * <td>示例 - in:地图<br/>
 * 含义 - 发生在<b>地图</b>服务器的日志.</td>
 * </tr>
 * </table>
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public class SearchCondition {
	/** 搜索日志日期格式(ISO8601) yyyy-MM-dd HH:mm:ss */
	public static final String TIME_FORMATTER = "yyyy-MM-dd HH:mm:ss";

	/** 搜索在特定时间段内记录的日志.日期必须采用年-月-日 的格式。 */
	public static final String SYNTAX_AFTER_TIME = "after;";

	/** 搜索在特定时间段内记录的日志.日期必须采用年-月-日 的格式。 */
	public static final String SYNTAX_BEFORE_TIME = "before:";

	/** 搜索在特定模块内记录的日志. */
	public static final String SYNTAX_IN = "in:";

	/** 搜索关键字 */
	private String q;

	/** 起始时间 */
	private String startTime;

	/** 终止时间 */
	private String endTime;

	/** 模块 */
	private String model;

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{q : ");
		builder.append(q);
		builder.append(", startTime : ");
		builder.append(startTime);
		builder.append(", endTime : ");
		builder.append(endTime);
		builder.append(", model : ");
		builder.append(model);
		builder.append("}");
		return builder.toString();
	}

}
