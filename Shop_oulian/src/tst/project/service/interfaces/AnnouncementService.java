package tst.project.service.interfaces;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tst.project.bean.others.AnnouncementBean;
import tst.project.dao.interfaces.AnnouncementDao;
import tst.project.page.PageBean;

@Service
@Transactional(rollbackFor = Exception.class)
public class AnnouncementService {
	@Resource
	AnnouncementDao announcementDao;
	
	/**
	 * 查询系统公告
	 * @param AnnouncementBean
	 * @return
	 */
	public List<AnnouncementBean> getAnnouncementBeans(AnnouncementBean announcementBean,PageBean pageBean)
	{
		return announcementDao.getAnnouncementBeans(announcementBean, pageBean);
	}
}
