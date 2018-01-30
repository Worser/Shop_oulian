package tst.project.service.interfaces;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tst.project.bean.information.InformationBean;
import tst.project.bean.information.InformationImgBean;
import tst.project.dao.interfaces.InformationDao;
import tst.project.page.PageBean;

@Service
@Transactional(rollbackFor = { Exception.class })
public class InformationService {
	@Resource
	InformationDao informationDao;
	
	/**
	 * 获得标签的咨询列表
	 * @param informationBean
	 * @param pageBean
	 * @return
	 */
	public List<InformationBean> getInformationsByTag(InformationBean informationBean,PageBean pageBean){
		return informationDao.getInformationsByTag(informationBean, pageBean);
	}
	
	/**
	 * 咨询详情
	 * @param informationBean
	 * @return
	 */
	public InformationBean getInformationDetail(InformationBean informationBean){
		int num=updateInformationDetail(informationBean);
		InformationBean informationBean1=informationDao.getInformationDetail(informationBean);
		if(informationBean1!=null){
			List<InformationImgBean> informationImgBeans=informationDao
					.getInformationImgs(new InformationImgBean().setInformation_id(informationBean.getInformation_id()));
			informationBean1.setInformationImgBeans(informationImgBeans);
		}
		return informationBean1;
	}
	
	/**
	 * 修改咨询详情
	 * @param informationBean
	 * @return
	 */
	public int updateInformationDetail(InformationBean informationBean){
		return informationDao.updateInformationDetail(informationBean);
	}
	/**
	 * 获得所有咨询列表
	 * @return
	 */
	public List<InformationBean> getInformations(InformationBean informationBean,PageBean pageBean){
		return informationDao.getInformations(informationBean,pageBean);
	}
	
	/**
	 * 首页推荐咨询
	 * @param informationBean
	 * @return
	 */
	public List<InformationBean> getRecomendInformations(InformationBean informationBean,PageBean pageBean){
		List<InformationBean> informationBeans=informationDao.getRecomendInformations(informationBean.setParent_id(-1),pageBean);
		if(informationBeans!=null){
			for (int i = 0; i < informationBeans.size(); i++) {
				InformationBean informationBean2=informationBeans.get(i);
				List<InformationBean> informationBeans2=informationDao
						.getRecomendInformations(new InformationBean().setParent_id(informationBean2.getInformation_id()),new PageBean().setLimit(3));
				informationBean2.setInformationBeans(informationBeans2);
			}
		}
		return informationBeans;
	}
	/**
	 * 左侧推荐咨询
	 * @param informationBean
	 * @param pageBean
	 * @return
	 */
	public List<InformationBean> getLeftRecomendInformations(InformationBean informationBean,PageBean pageBean){
		List<InformationBean> informationBeans=informationDao.getLeftRecomendInformations(informationBean.setParent_id(-1),pageBean);
		
		return informationBeans;
	}
}
