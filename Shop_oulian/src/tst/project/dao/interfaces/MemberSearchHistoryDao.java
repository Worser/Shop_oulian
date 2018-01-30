package tst.project.dao.interfaces;

import java.util.List;

import tst.project.bean.member.MemberSearchHistoryBean;

public interface MemberSearchHistoryDao {
	
	/**
	 * 查询搜索历史
	 * @param memberSearchHistoryBean
	 * @return
	 * @throws Exception
	 */
	public List<MemberSearchHistoryBean> getSearchHistory(MemberSearchHistoryBean memberSearchHistoryBean) throws Exception;
	
	/**
	 * 记录搜索历史
	 * @param memberSearchHistoryBean
	 * @return
	 * @throws Exception
	 */
	public int addSearchHistory(MemberSearchHistoryBean memberSearchHistoryBean) throws Exception;
	
	/**
	 * 清除搜索历史
	 * @param memberSearchHistoryBean
	 * @return
	 * @throws Exception
	 */
	public int removeSearchHistory(MemberSearchHistoryBean memberSearchHistoryBean) throws Exception;
}
