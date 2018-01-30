package tst.project.dao.interfaces;

import tst.project.bean.member.MemberBean;
import tst.project.bean.member.MemberQualificationBean;

public interface MemberQualificationDao {
	
	public MemberBean getQualificationByCode(MemberBean memberBean);

	/**
	 * 查询用户资质认证
	 * @param MemberQualificationBean
	 * @return
	 */
	public MemberQualificationBean getMemberQualification(MemberQualificationBean memberQualificationBean);
	
	/**
	 * 添加资质认证
	 * @param memberQualificationBean
	 * @return
	 */
	public int addMemberQualification(MemberQualificationBean memberQualificationBean);
	
	/**
	 * 修改资质认证
	 * @param memberQualificationBean
	 * @return
	 */
	public int updateMemberQualification(MemberQualificationBean memberQualificationBean);
	
	/**
	 * 查询公司资质认证是否已被添加
	 */
	public MemberQualificationBean getCompanyIsHave(MemberQualificationBean memberQualificationBean);
	
}
