package tst.project.service.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tst.project.bean.member.MemberBean;
import tst.project.bean.member.MemberImgBean;
import tst.project.bean.member.MemberLevelBean;
import tst.project.bean.merchants.QualificationBean;
import tst.project.dao.controller.MemberDaoC;
import tst.project.dao.controller.MemberImgDaoC;
import tst.project.page.PageBean;
@Service
@Transactional(rollbackFor = Exception.class)
public class MemberServiceC {
	@Resource
	MemberDaoC memberDaoC;
	
	@Resource
	MemberImgDaoC imgDaoC;
	
	/**
	 * 根据商家id 获得用户信息
	 * @param memberBean
	 * @return
	 */
	public MemberBean getMemberByMerchants(MemberBean memberBean){
		return memberDaoC.getMemberByMerchants(memberBean);
	}
	
	/**
	 * 获得分销的用户列表
	 * @param memberBean
	 * @return
	 */
	public List<MemberBean> getDistributionMembers(MemberBean memberBean,PageBean pageBean){
		return memberDaoC.getDistributionMembers(memberBean,pageBean);
	}
	/**
	 * 修改用户详情
	 * @param memberBean
	 * @return
	 */
	public int updateMemberDetail(MemberBean memberBean){
		int num=memberDaoC.updateMemberDetail(memberBean);
		if(num>0){
			if(!"1".equals(memberBean.getIs_certification_vip())){
				memberDaoC.updateMemberQualificationBean(new QualificationBean().setMember_id(memberBean.getMember_id()+""));	
			}
		}
		return num;
	}
	/**
	 * 修改用户详情
	 * @param memberBean
	 * @return
	 */
	public int updateMemberDetailZSSG(MemberBean memberBean){
		return memberDaoC.updateMemberDetailZSSG(memberBean);
	}
	/**
	 * 单个用户详情
	 * @return
	 */
	public MemberBean getOneMemberDetail(MemberBean memberBean){
		
		MemberBean memberBean1 = memberDaoC.getOneMemberDetail(memberBean);
		if(memberBean1!=null)
		{
			//将用户的授权品牌拼成字符串展示
			String bread = imgDaoC.getMemberAuthorizationBrand(new MemberImgBean()
			            .setMember_id(memberBean.getMember_id()+""));
			
			memberBean1.setBrand_name(bread);
		}
		return memberBean1;
	}
	
	
	/**
	 * 单个用户详情
	 * @return
	 */
	public MemberBean getOneMemberDetailZSSG(MemberBean memberBean){
		return memberDaoC.getOneMemberDetailZSSG(memberBean);
	}
	
	/**
	 * 获得所有普通用户信息
	 * @param memberBean
	 * @param pageBean
	 * @return
	 */
	public List<MemberBean> getAllMembers(MemberBean memberBean,PageBean pageBean){
		List<MemberBean> memberBeans = memberDaoC.getAllMembers(memberBean, pageBean);
		if(memberBeans!=null)
		{
			for (int i = 0; i < memberBeans.size(); i++) {
				//将用户的授权品牌拼成字符串展示
				String bread = imgDaoC.getMemberAuthorizationBrand(new MemberImgBean()
				            .setMember_id(memberBeans.get(i).getMember_id()+""));
				memberBeans.get(i).setBrand_name(bread);
			}
		}
		return memberBeans;
	}
	
	public Map getAllMembersCount(MemberBean memberBean){
		return memberDaoC.getAllMembersCount(memberBean);
	}
	
	/**
	 * 获得所有普通用户信息
	 * @param memberBean
	 * @param pageBean
	 * @return
	 */
	public List<MemberBean> getAllMembersZSSG(MemberBean memberBean,PageBean pageBean){
		return memberDaoC.getAllMembersZSSG(memberBean, pageBean);
	}
	
	/**
	 * 解冻或冻结账号
	 */
	public int updateIsFreeze(MemberBean memberBean)
	{
		return memberDaoC.updateIsFreeze(memberBean);
	}
	
	/**
	 * 查询用户等级
	 */
	public List<MemberLevelBean> getMemberLevel()
	{
		return memberDaoC.getMemberLevel();
	}
	/**
	 * 修改用户等级
	 * @return
	 */
	public int updateMemberLevel(MemberLevelBean memberLevelBean){
		return memberDaoC.updateMemberLevel(memberLevelBean);
	}
	/**
	 * 添加会员
	 */
	@Transactional
	public int addMember(MemberBean memberBean)
	{
		return memberDaoC.addMember(memberBean);
	}
	
	/**
	 * 导出用户excel
	 */
	public List<Object> exportMemberExcel(MemberBean memberBean)
	{
		return memberDaoC.exportMemberExcel(memberBean);
	}
}
