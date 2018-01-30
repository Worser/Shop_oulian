package tst.project.service.interfaces;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tst.project.bean.member.EnterpriseAdvantageBean;
import tst.project.dao.interfaces.EnterpriseAdvantageDao;

/**
 * @author xingqianji
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EnterpriseAdvantageService {
	
	@Resource
	private EnterpriseAdvantageDao dao;
	
	/**
	 * 查询企业优势
	 * @param enterpriseAdvantageBean
	 * @return
	 */
	public List<EnterpriseAdvantageBean> getEnterpriseAdvantageList(EnterpriseAdvantageBean enterpriseAdvantageBean)
	{
		return dao.getEnterpriseAdvantageList(enterpriseAdvantageBean);
	}
	
	/**
	 * 添加企业优势
	 * @param enterpriseAdvantageBean
	 * @return
	 */
	@Transactional
	public int addEnterpriseAdvantage(EnterpriseAdvantageBean enterpriseAdvantageBean)
	{
		return dao.addEnterpriseAdvantage(enterpriseAdvantageBean);
	}
	
	/**
	 * 删除企业优势
	 * @param enterpriseAdvantageBean
	 * @return
	 */
	@Transactional
	public int deleteEnterpriseAdvantage(EnterpriseAdvantageBean enterpriseAdvantageBean)
	{
		return dao.deleteEnterpriseAdvantage(enterpriseAdvantageBean);
	}
}
