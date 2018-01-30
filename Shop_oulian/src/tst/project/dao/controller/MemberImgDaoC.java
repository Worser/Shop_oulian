package tst.project.dao.controller;

import java.util.List;

import tst.project.bean.member.MemberImgBean;

public interface MemberImgDaoC {
	
	/**
	 * 查询图片
	 * @param memberImgBean
	 * @return
	 */
	public List<MemberImgBean> getMemberImgList(MemberImgBean memberImgBean);
	
	/**
	 * 查询用户授权品牌（字符串） 
	 */
	public String getMemberAuthorizationBrand(MemberImgBean memberImgBean);
	
}
