package tst.project.dao.interfaces;

import java.util.List;

import tst.project.bean.member.MemberImgBean;

public interface MemberImgDao {
	
	/**
	 * 查询图片
	 * @param memberImgBean
	 * @return
	 */
	public List<MemberImgBean> getMemberImgList(MemberImgBean memberImgBean);
	
	/**
	 * 添加图片，需填写图片类型
	 * @param memberImgBean
	 * @return
	 */
	public int addMemberImg(MemberImgBean memberImgBean);
	
	/**
	 * 删除图片
	 * @param memberImgBean
	 * @return
	 */
	public int deleteMemberImg(MemberImgBean memberImgBean);
	
	/**
	 * 修改图片
	 * @param memberImgBean
	 * @return
	 */
	public int updateMemberImg(MemberImgBean memberImgBean);
	
	/**
	 * 删除资质认证图片 
	 * @param memberImgBean
	 * @return
	 */
	public int deleteQualificationImg(MemberImgBean memberImgBean);
}
