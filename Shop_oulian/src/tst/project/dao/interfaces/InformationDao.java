package tst.project.dao.interfaces;

import java.util.List;

import tst.project.bean.information.InformationBean;
import tst.project.bean.information.InformationImgBean;
import tst.project.page.PageBean;

public interface InformationDao {
	
	/**
	 * 获得标签的咨询列表
	 * @param informationBean
	 * @param pageBean
	 * @return
	 */
	public List<InformationBean> getInformationsByTag(InformationBean informationBean,PageBean pageBean);
	
	/**
	 * 咨询图片列表
	 * @param informationImgBean
	 * @return
	 */
	public List<InformationImgBean> getInformationImgs(InformationImgBean informationImgBean);
	
	/**
	 * 咨询详情
	 * @param informationBean
	 * @return
	 */
	public InformationBean getInformationDetail(InformationBean informationBean);
	/**
	 * 修改咨询详情
	 * @param informationBean
	 * @return
	 */
	public int updateInformationDetail(InformationBean informationBean);
	/**
	 * 获得所有咨询列表
	 * @return
	 */
	public List<InformationBean> getInformations(InformationBean informationBean,PageBean pageBean);
	
	/**
	 * 首页推荐咨询
	 * @param informationBean
	 * @return
	 */
	public List<InformationBean> getRecomendInformations(InformationBean informationBean,PageBean pageBean);
	
	/**
	 * 左侧推荐咨询
	 * @param informationBean
	 * @param pageBean
	 * @return
	 */
	public List<InformationBean> getLeftRecomendInformations(InformationBean informationBean,PageBean pageBean);
}
