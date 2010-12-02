package com.blogspot.duanni.web;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.blogspot.duanni.SpringBaseTest;
import com.blogspot.duanni.lucene.model.LogsEntry;
import com.blogspot.duanni.lucene.model.SearchCondition;
import com.blogspot.duanni.lucene.service.IndexService;
import com.blogspot.duanni.lucene.service.SearchService;
import com.blogspot.duanni.lucene.util.SearchPage;

/**
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public class SearchActionTest extends SpringBaseTest {
	@Autowired
	private SearchService searchService;

	@Autowired
	private IndexService indexService;

	private SearchPage<LogsEntry> page;
	private SearchCondition condition;

	@Before
	public void before() {
		page = new SearchPage<LogsEntry>(10);
		condition = new SearchCondition();
	}

	@Test
	public void index() {
		page.setPageSize(1);
		page.setPageNo(0);
		condition.setQ("道具");
		searchService.search(page, condition);
		logger.info("size => [{}]", page.getTotalCount());
		logger.info("content => [{}]", toJSON(page));
		indexService.delete(condition);
		searchService.search(page, condition);

		Assert.assertEquals(page.getTotalCount(), 0);
	}

	@Test
	public void exceptionNoResultTest() {
		page.setPageSize(5);
		page.setPageNo(0);
		condition.setQ("µÀ¾ß");
		searchService.search(page, condition);
		Assert.assertEquals(page.getTotalCount(), -1);
	}
}
