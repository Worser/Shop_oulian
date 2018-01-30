package tst.project.dao.controller;

import java.util.List;

import tst.project.bean.member.EnterpriseAdvantageBean;

public interface EnterpriseAdvantageDaoC {
	
	/**
	 * 查询企业优势
	 * @param enterpriseAdvantageBean
	 * @return
	 */
	public List<EnterpriseAdvantageBean> getEnterpriseAdvantageList(EnterpriseAdvantageBean enterpriseAdvantageBean);
	
}
