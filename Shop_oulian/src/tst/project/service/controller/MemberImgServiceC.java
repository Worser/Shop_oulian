package tst.project.service.controller;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tst.project.bean.member.MemberImgBean;
import tst.project.dao.controller.MemberImgDaoC;
/**
 * @author xingqianji
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MemberImgServiceC {
	
	@Resource
	private MemberImgDaoC imgDaoC;
	
	/**
	 * 查询图片
	 * @param memberImgBean
	 * @return
	 */
	public List<MemberImgBean> getMemberImgList(MemberImgBean memberImgBean)
	{
		return imgDaoC.getMemberImgList(memberImgBean);
	}

}
