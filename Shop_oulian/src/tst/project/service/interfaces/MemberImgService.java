package tst.project.service.interfaces;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tst.project.bean.member.MemberImgBean;
import tst.project.dao.interfaces.MemberImgDao;

/**
 * @author xingqianji
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MemberImgService {
	
	@Resource
	private MemberImgDao dao;
	
	/**
	 * 查询图片
	 * @param memberImgBean
	 * @return
	 */
	public List<MemberImgBean> getMemberImgList(MemberImgBean memberImgBean)
	{
		return dao.getMemberImgList(memberImgBean);
	}
	
	/**
	 * 添加图片，需填写图片类型
	 * @param memberImgBean
	 * @return
	 */
	@Transactional
	public int addMemberImg(MemberImgBean memberImgBean)
	{
		return dao.addMemberImg(memberImgBean);
	}
	
	/**
	 * 删除图片
	 * @param memberImgBean
	 * @return
	 */
	@Transactional
	public int deleteMemberImg(MemberImgBean memberImgBean)
	{
		return dao.deleteMemberImg(memberImgBean);
	}
	
	/**
	 * 修改图片
	 * @param memberImgBean
	 * @return
	 */
	@Transactional
	public int updateMemberImg(MemberImgBean memberImgBean)
	{
	    return dao.updateMemberImg(memberImgBean);	
	}
	
	/**
	 * 删除资质认证图片 
	 * @param memberImgBean
	 * @return
	 */
	public int deleteQualificationImg(MemberImgBean memberImgBean)
	{
		return dao.deleteQualificationImg(memberImgBean);
	}
}
