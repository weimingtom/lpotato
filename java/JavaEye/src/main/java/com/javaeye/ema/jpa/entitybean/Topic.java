/*
 * (#)Topic.java 1.0 2008-4-11 2008-4-11 GMT+08:00
 */
package com.javaeye.ema.jpa.entitybean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author 路春辉
 * @version $1.0, 2008-4-11 2008-4-11 GMT+08:00
 * @since JDK5
 */
@Entity(name = "T_TOPIC")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TOPIC_TYPE", discriminatorType = DiscriminatorType.INTEGER, length = 1)
@DiscriminatorValue(value = "1")
public class Topic implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2497331904231677101L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "TOPIC_ID")
	private int topicId;

	@Column(name = "TOPIC_TITLE", length = 100)
	private String topicTitle;

	@Column(name = "TOPIC_TIME")
	@Temporal(TemporalType.DATE)
	private Date topicTime;

	@Column(name = "TOPIC_VIEWS")
	private int topicViews;

	/**
	 * @return the topicId
	 */
	public int getTopicId() {
		return topicId;
	}

	/**
	 * @param topicId
	 *            the topicId to set
	 */
	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	/**
	 * @return the topicTitle
	 */
	public String getTopicTitle() {
		return topicTitle;
	}

	/**
	 * @param topicTitle
	 *            the topicTitle to set
	 */
	public void setTopicTitle(String topicTitle) {
		this.topicTitle = topicTitle;
	}

	/**
	 * @return the topicTime
	 */
	public Date getTopicTime() {
		return topicTime;
	}

	/**
	 * @param topicTime
	 *            the topicTime to set
	 */
	public void setTopicTime(Date topicTime) {
		this.topicTime = topicTime;
	}

	/**
	 * @return the topicViews
	 */
	public int getTopicViews() {
		return topicViews;
	}

	/**
	 * @param topicViews
	 *            the topicViews to set
	 */
	public void setTopicViews(int topicViews) {
		this.topicViews = topicViews;
	}
}
