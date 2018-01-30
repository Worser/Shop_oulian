package tst.project.service.controller;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tst.project.bean.entrust.MemberEntrustBean;
import tst.project.bean.member.MemberBean;
import tst.project.dao.controller.MemberEntrustDaoC;
import tst.project.page.PageBean;

/**
 * @author xingqianji
 *
 */
@Service
public class MemberEntrustServiceC {
	
	@Resource
	private MemberEntrustDaoC daoC;
	
	/**
	 * 查询委托信息（分页）
	 */
	public List<MemberEntrustBean> getMemberEntrustList(MemberBean memberBean,PageBean pageBean)
	{
		return daoC.getMemberEntrustList(memberBean, pageBean);
	}
	
	/**
	 * 查询委托信息（导出）
	 */
	public List<Object> exportMemberEntrustExcel(MemberBean memberBean)
	{
		return daoC.exportMemberEntrustExcel(memberBean);
	}
	
	/**
	 * 修改委托状态 ，接受/拒绝
	 */
	@Transactional
	public int updateEntrustState(MemberEntrustBean memberEntrustBean)
	{
		return daoC.updateEntrustState(memberEntrustBean);
	}
	
}
