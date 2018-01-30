package tst.project.dao.interfaces;

import java.util.List;
import tst.project.bean.bbs.SearchTopicHistoryBean;

public abstract interface SearchTopicHistoryDao {
	public abstract List<SearchTopicHistoryBean> getSearchTopicHistory(
			SearchTopicHistoryBean paramSearchTopicHistoryBean)
			throws Exception;

	public abstract int addSearchTopicHistory(
			SearchTopicHistoryBean paramSearchTopicHistoryBean)
			throws Exception;

	public abstract int removeSearchTopicHistory(
			SearchTopicHistoryBean paramSearchTopicHistoryBean)
			throws Exception;
}