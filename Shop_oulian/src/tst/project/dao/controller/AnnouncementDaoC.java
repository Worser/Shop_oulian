package tst.project.dao.controller;

import java.util.List;

import tst.project.bean.others.AnnouncementBean;
import tst.project.page.PageBean;

public interface AnnouncementDaoC {
	/**
	 * 查询系统公告
	 * @param AnnouncementBean
	 * @return
	 */
	public List<AnnouncementBean> getAnnouncementBeans(AnnouncementBean announcementBean,PageBean pageBean);
	public AnnouncementBean getAnnouncementDetail(AnnouncementBean announcementBean);

	/**
	 * 添加系统公告
	 */
	public int addAnnouncement(AnnouncementBean announcementBean);
	
	/**
	 * 删除系统公告
	 */
	public int deleteAnnouncement(AnnouncementBean announcementBean);
	
	/**
	 * 修改系统公告
	 */
	public int updateAnnouncement(AnnouncementBean announcementBean);
}
