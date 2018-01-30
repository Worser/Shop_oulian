package tst.project.service.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tst.project.bean.banner.BannerBean;
import tst.project.bean.banner.BannerButtonBean;
import tst.project.dao.controller.BannerDaoC;
import tst.project.page.PageBean;

@Service
@Transactional(rollbackFor = Exception.class)
public class BannerServiceC {
	@Resource
	BannerDaoC bannerDaoC;
	/**
	 * 广告按钮列表
	 * @param bannerButtonBean
	 * @return
	 */
	public List<BannerButtonBean> getButtonBanners(BannerButtonBean bannerButtonBean){
		return bannerDaoC.getButtonBanners(bannerButtonBean);
	}
	
	/**
	 * 修改广告按钮
	 * @param bannerButtonBean
	 * @return
	 */
	public int updateButtonBanner(BannerButtonBean bannerButtonBean){
		return bannerDaoC.updateButtonBanner(bannerButtonBean);
	}
	
	/**
	 * 删除广告按钮
	 * @param bannerButtonBean
	 * @return
	 */
	public int deleteButtonBanner(BannerButtonBean bannerButtonBean){
		return bannerDaoC.deleteButtonBanner(bannerButtonBean);
	}
	
	/**
	 * 添加广告按钮
	 * @param bannerButtonBean
	 * @return
	 */
	public int insertButtonBanner(BannerButtonBean bannerButtonBean){
		return bannerDaoC.insertButtonBanner(bannerButtonBean);
	}
	
	/**
	 * 获得所有广告列表
	 * @return
	 */
	public List<BannerBean> getAllBanners(BannerBean bannerBean,PageBean pageBean){
		return bannerDaoC.getAllBanners( bannerBean,pageBean);
	}
	/**
	 * 添加商品
	 * @param bannerBean
	 * @return
	 */
	public int insertBanner(BannerBean bannerBean){
		return bannerDaoC.insertBanner( bannerBean);
	}
	
	/**
	 * 修改商品
	 * @param bannerBean
	 * @return
	 */
	public int updateBanner(BannerBean bannerBean){
		return bannerDaoC.updateBanner(bannerBean);
	}
	
	/**
	 * 删除商品
	 * @param bannerBean
	 * @return
	 */
	public int deleteBanner(BannerBean bannerBean){
		return bannerDaoC.deleteBanner(bannerBean);
	}
}
