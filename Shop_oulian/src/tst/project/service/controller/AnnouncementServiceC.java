package tst.project.service.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tst.project.bean.others.AnnouncementBean;
import tst.project.dao.controller.AnnouncementDaoC;
import tst.project.page.PageBean;

@Service
@Transactional(rollbackFor = Exception.class)
public class AnnouncementServiceC {
	@Resource
	AnnouncementDaoC announcementDaoC;
	
	/**
	 * 查询系统公告
	 * @param AnnouncementBean
	 * @return
	 */
	public List<AnnouncementBean> getAnnouncementBeans(AnnouncementBean announcementBean,PageBean pageBean)
	{
		return announcementDaoC.getAnnouncementBeans(announcementBean, pageBean);
	}
	
	public AnnouncementBean getAnnouncementDetail(AnnouncementBean announcementBean){
		return announcementDaoC.getAnnouncementDetail(announcementBean);
	}
	
	/**
	 * 添加系统公告
	 */
	@Transactional
	public int addAnnouncement(AnnouncementBean announcementBean)
	{
		return announcementDaoC.addAnnouncement(announcementBean);
	}
	
	/**
	 * 删除系统公告
	 */
	@Transactional
	public int deleteAnnouncement(AnnouncementBean announcementBean)
	{
		return announcementDaoC.deleteAnnouncement(announcementBean);
	}
	
	/**
	 * 修改系统公告
	 */
	@Transactional
	public int updateAnnouncement(AnnouncementBean announcementBean)
	{
		return announcementDaoC.updateAnnouncement(announcementBean);
	}
}
