package tst.project.dao.interfaces;

import java.util.List;

import tst.project.bean.member.EnterpriseAdvantageBean;

public interface EnterpriseAdvantageDao {
	
	/**
	 * 查询企业优势
	 * @param enterpriseAdvantageBean
	 * @return
	 */
	public List<EnterpriseAdvantageBean> getEnterpriseAdvantageList(EnterpriseAdvantageBean enterpriseAdvantageBean);
	
	/**
	 * 添加企业优势
	 * @param enterpriseAdvantageBean
	 * @return
	 */
	public int addEnterpriseAdvantage(EnterpriseAdvantageBean enterpriseAdvantageBean);
	
	/**
	 * 删除企业优势
	 * @param enterpriseAdvantageBean
	 * @return
	 */
	public int deleteEnterpriseAdvantage(EnterpriseAdvantageBean enterpriseAdvantageBean);
}
