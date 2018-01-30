package tst.project.dao.interfaces;

import java.util.List;

import tst.project.bean.banner.BannerBean;
import tst.project.bean.banner.BannerButtonBean;

public interface BannerDao {
	/**
	 * 获得所有广告列表
	 * @return
	 */
	public List<BannerBean> getAllBanners(BannerBean bannerBean);
	
	public List<BannerButtonBean> getBannerButtons(BannerButtonBean bannerButtonBean);
}
