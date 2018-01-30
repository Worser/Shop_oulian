package tst.project.service.interfaces;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tst.project.bean.member.MemberBean;
import tst.project.bean.member.MemberImgBean;
import tst.project.bean.member.MemberQualificationBean;
import tst.project.dao.interfaces.MemberImgDao;
import tst.project.dao.interfaces.MemberQualificationDao;

/**
 * @author xingqianji
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MemberQualificationService {
	
	@Resource
	private MemberQualificationDao dao;
	
	@Resource
	private MemberImgDao memberImgDao;
	
	/**
	 * 查询用户资质认证
	 * @param MemberQualificationBean
	 * @return
	 */
	public MemberQualificationBean getMemberQualification(MemberQualificationBean memberQualificationBean)
	{
		MemberQualificationBean memberQualificationBean1 = dao.getMemberQualification(memberQualificationBean);
		if(memberQualificationBean1!=null)
		{
			//查询营业执照图片
		    List<MemberImgBean> businessBeans = memberImgDao.getMemberImgList(new MemberImgBean()
		     	                .setMember_id(memberQualificationBean1.getMember_id())
		     	                .setImg_type("business_license")
		     	                .setMember_qualification_id(memberQualificationBean1.getMember_qualification_id()+""));
		     	
		    memberQualificationBean1.setBusinessBeans(businessBeans);
		    
		    //查询身份证照
		    List<MemberImgBean> idCardBeans = memberImgDao.getMemberImgList(new MemberImgBean()
		     	                .setMember_id(memberQualificationBean1.getMember_id())
		     	                .setImg_type("id_card")
		     	                .setMember_qualification_id(memberQualificationBean1.getMember_qualification_id()+""));
		     	
		    memberQualificationBean1.setIdCardBeans(idCardBeans);
		    
		    //查询相关授权书照
		    List<MemberImgBean> authorizationBeans = memberImgDao.getMemberImgList(new MemberImgBean()
		     	                .setMember_id(memberQualificationBean1.getMember_id())
		     	                .setImg_type("related_authorization")
		     	                .setMember_qualification_id(memberQualificationBean1.getMember_qualification_id()+""));
		     	
		    memberQualificationBean1.setAuthorizationBeans(authorizationBeans);
		}
		return memberQualificationBean1;
	}
	
	
	public MemberBean getQualificationByCode(MemberBean memberBean){
		return dao.getQualificationByCode(memberBean);
	}
	/**
	 * 添加资质认证
	 * @param memberQualificationBean
	 * @return
	 */
	public int addMemberQualification(MemberQualificationBean memberQualificationBean)
	{
		return dao.addMemberQualification(memberQualificationBean);
	}
	
	/**
	 * 修改资质认证
	 * @param memberQualificationBean
	 * @return
	 */
	public int updateMemberQualification(MemberQualificationBean memberQualificationBean)
	{
		return dao.updateMemberQualification(memberQualificationBean);
	}
	
	/**
	 * 查询公司资质认证是否已被添加
	 */
	public MemberQualificationBean getCompanyIsHave(MemberQualificationBean memberQualificationBean)
	{
		return dao.getCompanyIsHave(memberQualificationBean);
	}
	
}
