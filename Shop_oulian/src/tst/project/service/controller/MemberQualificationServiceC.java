package tst.project.service.controller;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tst.project.bean.member.MemberBean;
import tst.project.bean.member.MemberImgBean;
import tst.project.bean.member.MemberQualificationBean;
import tst.project.dao.controller.MemberQualificationDaoC;
import tst.project.dao.controller.MemberImgDaoC;
import tst.project.page.PageBean;


/**
 * @author xingqianji
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MemberQualificationServiceC {
	
	@Resource
	private MemberQualificationDaoC dao;
	
	@Resource
	private MemberImgDaoC memberImgDaoC;
	
	/**
	 * 查询用户资质认证（全部）
	 * @param MemberQualificationBean
	 * @return
	 */
	public List<MemberQualificationBean> getMemberQualification(MemberQualificationBean memberQualificationBean,PageBean pageBean)
	{
		List<MemberQualificationBean> memberQualificationBeans = dao.getMemberQualification(memberQualificationBean,pageBean);
		if(memberQualificationBeans!=null)
		{
			for (int i = 0; i < memberQualificationBeans.size(); i++) {
				//查询营业执照图片
			    List<MemberImgBean> businessBeans = memberImgDaoC.getMemberImgList(new MemberImgBean()
			     	                .setMember_id(memberQualificationBeans.get(i).getMember_id())
			     	                .setImg_type("business_license")
			     	                .setMember_qualification_id(memberQualificationBeans.get(i).getMember_qualification_id()+""));
			     	
			    memberQualificationBeans.get(i).setBusinessBeans(businessBeans);
			    
			    //查询身份证照
			    List<MemberImgBean> idCardBeans = memberImgDaoC.getMemberImgList(new MemberImgBean()
			     	                .setMember_id(memberQualificationBeans.get(i).getMember_id())
			     	                .setImg_type("id_card")
			     	                .setMember_qualification_id(memberQualificationBeans.get(i).getMember_qualification_id()+""));
			     	
			    memberQualificationBeans.get(i).setIdCardBeans(idCardBeans);
			    
			    //查询相关授权书照
			    List<MemberImgBean> authorizationBeans = memberImgDaoC.getMemberImgList(new MemberImgBean()
			     	                .setMember_id(memberQualificationBeans.get(i).getMember_id())
			     	                .setImg_type("related_authorization")
			     	                .setMember_qualification_id(memberQualificationBeans.get(i).getMember_qualification_id()+""));
			     	
			    memberQualificationBeans.get(i).setAuthorizationBeans(authorizationBeans);
			}
		}
		return memberQualificationBeans;
	}
	
	/**
	 * 查询用户资质认证（单个）
	 * @param MemberQualificationBean
	 * @return
	 */
	public MemberQualificationBean getMemberQualificationById(MemberQualificationBean memberQualificationBean)
	{
		MemberQualificationBean memberQualificationBean1 = dao.getMemberQualificationById(memberQualificationBean);
		if(memberQualificationBean1!=null)
		{
			//查询营业执照图片
		    List<MemberImgBean> businessBeans = memberImgDaoC.getMemberImgList(new MemberImgBean()
		     	                .setMember_id(memberQualificationBean1.getMember_id())
		     	                .setImg_type("business_license")
		     	                .setMember_qualification_id(memberQualificationBean1.getMember_qualification_id()+""));
		     	
		    memberQualificationBean1.setBusinessBeans(businessBeans);
		    
		    //查询身份证照
		    List<MemberImgBean> idCardBeans = memberImgDaoC.getMemberImgList(new MemberImgBean()
		     	                .setMember_id(memberQualificationBean1.getMember_id())
		     	                .setImg_type("id_card")
		     	                .setMember_qualification_id(memberQualificationBean1.getMember_qualification_id()+""));
		     	
		    memberQualificationBean1.setIdCardBeans(idCardBeans);
		    
		    //查询相关授权书照
		    List<MemberImgBean> authorizationBeans = memberImgDaoC.getMemberImgList(new MemberImgBean()
		     	                .setMember_id(memberQualificationBean1.getMember_id())
		     	                .setImg_type("related_authorization")
		     	                .setMember_qualification_id(memberQualificationBean1.getMember_qualification_id()+""));
		     	
		    memberQualificationBean1.setAuthorizationBeans(authorizationBeans);
		}
		return memberQualificationBean1;
	}
	
	/**
	 * 修改资质认证状态
	 * @param memberQualificationBean
	 * @return
	 */
	public int updateQualificationState(MemberQualificationBean memberQualificationBean)
	{
		return dao.updateQualificationState(memberQualificationBean);
	}
	
	/**
	 * 批量修改资质认证状态
	 * @param memberQualificationBean
	 * @return
	 */
	public int batchUpdateQualificationState(MemberQualificationBean memberQualificationBean)
	{
		return dao.batchUpdateQualificationState(memberQualificationBean);
	}
	
	/**
	 * 资质认证通过后，会员就是就是认证会员
	 */
	public int updateMemberQualificationState(MemberBean memberBean)
	{
		return dao.updateMemberQualificationState(memberBean);
	}
	
	/**
	 * 查询用户资质认证（批量）
	 */
	public List<MemberQualificationBean> getMemberQualificationBeans(MemberQualificationBean memberQualificationBean)
	{
		return dao.getMemberQualificationBeans(memberQualificationBean);
	}
	
}
