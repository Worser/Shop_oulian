package tst.project.service.controller;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tst.project.bean.member.EnterpriseAdvantageBean;
import tst.project.dao.controller.EnterpriseAdvantageDaoC;

/**
 * @author xingqianji
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EnterpriseAdvantageServiceC {
	
	@Resource
	private EnterpriseAdvantageDaoC daoC;
	
	/**
	 * 查询企业优势
	 * @param enterpriseAdvantageBean
	 * @return
	 */
	public List<EnterpriseAdvantageBean> getEnterpriseAdvantageList(EnterpriseAdvantageBean enterpriseAdvantageBean)
	{
		return daoC.getEnterpriseAdvantageList(enterpriseAdvantageBean);
	}
	
}
