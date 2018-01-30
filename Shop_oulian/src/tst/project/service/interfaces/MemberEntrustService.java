package tst.project.service.interfaces;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tst.project.bean.entrust.MemberEntrustBean;
import tst.project.dao.interfaces.MemberEntrustDao;
import tst.project.page.PageBean;


/**
 * @author xingqianji
 *
 */
@Service
public class MemberEntrustService {
	
	@Resource
	private MemberEntrustDao dao;
	
	/**
	 * 添加客户委托信息 
	 * @param memberEntrustBean
	 * @return
	 */
	@Transactional
	public int addMemberEntrust(MemberEntrustBean memberEntrustBean)
	{
		return dao.addMemberEntrust(memberEntrustBean);
	}
	
	/**
	 * 查询委托信息
	 */
	public List<MemberEntrustBean> getMemberEntrustList(MemberEntrustBean memberEntrustBean,PageBean pageBean)
	{
		return dao.getMemberEntrustList(memberEntrustBean, pageBean);
	}
	
}
