package tst.project.service.interfaces;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tst.project.bean.member.MemberSearchHistoryBean;
import tst.project.dao.interfaces.MemberSearchHistoryDao;

/**
 * @author xingqianji
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MemberSearchHistoryService {
	
	@Resource
	private MemberSearchHistoryDao dao;
	
	/**
	 * 查询搜索历史
	 * @param memberSearchHistoryBean
	 * @return
	 * @throws Exception
	 */
	public List<MemberSearchHistoryBean> getSearchHistory(MemberSearchHistoryBean memberSearchHistoryBean) throws Exception
	{
		return dao.getSearchHistory(memberSearchHistoryBean);
	}
	
	/**
	 * 记录搜索历史
	 * @param memberSearchHistoryBean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int addSearchHistory(MemberSearchHistoryBean memberSearchHistoryBean) throws Exception
	{
		return dao.addSearchHistory(memberSearchHistoryBean);
	}
	
	/**
	 * 清除搜索历史
	 * @param memberSearchHistoryBean
	 * @return
	 * @throws Exception
	 */
	public int removeSearchHistory(MemberSearchHistoryBean memberSearchHistoryBean) throws Exception
	{
		return dao.removeSearchHistory(memberSearchHistoryBean);
	}
	
}
