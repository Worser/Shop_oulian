package tst.project.dao.controller;

import java.util.List;

import tst.project.bean.member.MemberBean;
import tst.project.bean.member.MemberQualificationBean;
import tst.project.page.PageBean;

public interface MemberQualificationDaoC {
	
	/**
	 * 查询用户资质认证（全部）
	 * @param MemberQualificationBean
	 * @return
	 */
	public List<MemberQualificationBean> getMemberQualification(MemberQualificationBean memberQualificationBean,PageBean pageBean);
	
	/**
	 * 查询用户资质认证（单个）
	 * @param MemberQualificationBean
	 * @return
	 */
	public MemberQualificationBean getMemberQualificationById(MemberQualificationBean memberQualificationBean);
	
	/**
	 * 修改资质认证状态
	 * @param memberQualificationBean
	 * @return
	 */
	public int updateQualificationState(MemberQualificationBean memberQualificationBean);
	
	/**
	 * 批量修改资质认证状态
	 * @param memberQualificationBean
	 * @return
	 */
	public int batchUpdateQualificationState(MemberQualificationBean memberQualificationBean);
	
	/**
	 * 资质认证通过后，会员就是就是认证会员
	 */
	public int updateMemberQualificationState(MemberBean memberBean);
	
	/**
	 * 查询用户资质认证（批量）
	 */
	public List<MemberQualificationBean> getMemberQualificationBeans(MemberQualificationBean memberQualificationBean);
}
