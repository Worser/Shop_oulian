package tst.project.dao.interfaces;

/**
 * @author xingqianji
 */

import java.util.List;

import tst.project.bean.entrust.MemberEntrustBean;
import tst.project.page.PageBean;


public interface MemberEntrustDao {
	
	/**
	 * 添加客户委托信息 
	 * @param memberEntrustBean
	 * @return
	 */
	public int addMemberEntrust(MemberEntrustBean memberEntrustBean);
	
	/**
	 * 查询委托信息
	 */
	public List<MemberEntrustBean> getMemberEntrustList(MemberEntrustBean memberEntrustBean,PageBean pageBean);
	
}
