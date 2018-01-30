package tst.project.dao.controller;

import java.util.List;
import java.util.Map;

import tst.project.bean.member.MemberBean;
import tst.project.bean.member.MemberLevelBean;
import tst.project.bean.merchants.QualificationBean;
import tst.project.page.PageBean;

public interface MemberDaoC {
	
	/**
	 * 根据商家id 获得用户信息
	 * @param memberBean
	 * @return
	 */
	public MemberBean getMemberByMerchants(MemberBean memberBean);
	/**
	 * 获得分销的用户列表
	 * @param memberBean
	 * @return
	 */
	public List<MemberBean> getDistributionMembers(MemberBean memberBean,PageBean pageBean);
	
	/**
	 * 修改用户详情
	 * @param memberBean
	 * @return
	 */
	public int updateMemberDetail(MemberBean memberBean);
	/**
	 * 修改用户详情
	 * @param memberBean
	 * @return
	 */
	public int updateMemberDetailZSSG(MemberBean memberBean);
	/**
	 * 单个用户详情
	 * @return
	 */
	public MemberBean getOneMemberDetail(MemberBean memberBean);
	

	/**
	 * 单个用户详情
	 * @return
	 */
	public MemberBean getOneMemberDetailZSSG(MemberBean memberBean);
	
	/**
	 * 获得所有普通用户信息
	 * @param memberBean
	 * @return
	 */
	public List<MemberBean> getAllMembers(MemberBean memberBean,PageBean pageBean);
	
	public Map getAllMembersCount(MemberBean memberBean);
	
	
	/**
	 * 获得所有普通用户信息
	 * @param memberBean
	 * @param pageBean
	 * @return
	 */
	public List<MemberBean> getAllMembersZSSG(MemberBean memberBean,PageBean pageBean);
	
	/**
	 * 解冻或冻结账号
	 */
	public int updateIsFreeze(MemberBean memberBean);
	
	/**
	 * 查询用户等级
	 */
	public List<MemberLevelBean> getMemberLevel();
	/**
	 * 修改用户等级
	 * @return
	 */
	public int updateMemberLevel(MemberLevelBean memberLevelBean);
	
	/**
	 * 修改用户等级
	 * @return
	 */
	public int updateMemberQualificationBean(QualificationBean qualificationBean);
	/**
	 * 添加会员
	 */
	public int addMember(MemberBean memberBean);
	
	/**
	 * 导出用户excel
	 */
	public List<Object> exportMemberExcel(MemberBean memberBean);
}
