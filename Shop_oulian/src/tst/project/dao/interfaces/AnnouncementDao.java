package tst.project.dao.interfaces;

import java.util.List;

import tst.project.bean.others.AnnouncementBean;
import tst.project.page.PageBean;

public interface AnnouncementDao {
	
	/**
	 * 查询系统公告
	 * @param AnnouncementBean
	 * @return
	 */
	public List<AnnouncementBean> getAnnouncementBeans(AnnouncementBean announcementBean,PageBean pageBean);
	
}
