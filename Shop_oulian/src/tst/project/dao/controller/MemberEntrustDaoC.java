package tst.project.dao.controller;

/**
 * @author xingqianji
 */

import java.util.List;

import tst.project.bean.entrust.MemberEntrustBean;
import tst.project.bean.member.MemberBean;
import tst.project.page.PageBean;


public interface MemberEntrustDaoC {
	
	/**
	 * 查询委托信息（分页）
	 */
	public List<MemberEntrustBean> getMemberEntrustList(MemberBean memberBean,PageBean pageBean);
	
	/**
	 * 查询委托信息（导出）
	 */
	public List<Object> exportMemberEntrustExcel(MemberBean memberBean);
	
	/**
	 * 修改委托状态 ，接受/拒绝
	 */
	public int updateEntrustState(MemberEntrustBean memberEntrustBean);
	
}
