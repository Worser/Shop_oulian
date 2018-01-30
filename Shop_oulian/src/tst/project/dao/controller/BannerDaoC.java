package tst.project.dao.controller;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tst.project.bean.banner.BannerBean;
import tst.project.bean.banner.BannerButtonBean;
import tst.project.page.PageBean;

public interface BannerDaoC {
	/**
	 * 广告按钮列表
	 * @param bannerButtonBean
	 * @return
	 */
	public List<BannerButtonBean> getButtonBanners(BannerButtonBean bannerButtonBean);
	/**
	 * 添加广告按钮
	 * @param bannerButtonBean
	 * @return
	 */
	public int insertButtonBanner(BannerButtonBean bannerButtonBean);
	
	/**
	 * 修改广告按钮
	 * @param bannerButtonBean
	 * @return
	 */
	public int updateButtonBanner(BannerButtonBean bannerButtonBean);
	
	/**
	 * 删除广告按钮
	 * @param bannerButtonBean
	 * @return
	 */
	public int deleteButtonBanner(BannerButtonBean bannerButtonBean);
	
	/**
	 * 获得所有广告列表
	 * @return
	 */
	public List<BannerBean> getAllBanners(BannerBean bannerBean,PageBean pageBean);
	
	/**
	 * 添加商品
	 * @param bannerBean
	 * @return
	 */
	public int insertBanner(BannerBean bannerBean);
	
	/**
	 * 修改商品
	 * @param bannerBean
	 * @return
	 */
	public int updateBanner(BannerBean bannerBean);
	
	/**
	 * 删除商品
	 * @param bannerBean
	 * @return
	 */
	public int deleteBanner(BannerBean bannerBean);
}
