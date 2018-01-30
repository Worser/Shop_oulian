package tst.project.service.interfaces;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tst.project.bean.banner.BannerBean;
import tst.project.bean.banner.BannerButtonBean;
import tst.project.dao.interfaces.BannerDao;

@Service
@Transactional(rollbackFor = Exception.class)
public class BannerService {

	@Resource
	BannerDao bannerDao;
	
	/**
	 * 获得所有广告列表
	 * @return
	 */
	public List<BannerBean> getAllBanners(BannerBean bannerBean){
		List<BannerBean> bannerBeans=bannerDao.getAllBanners( bannerBean);
		if(bannerBeans!=null){
			for (int i = 0; i < bannerBeans.size(); i++) {
				List<BannerButtonBean> bannerButtonBeans=bannerDao
						.getBannerButtons(new BannerButtonBean().setBanner_id(bannerBeans.get(i).getBanner_id()+""));
				bannerBeans.get(i).setBannerButtonBeans(bannerButtonBeans);
			}
		}
		return bannerBeans;
	}
}
