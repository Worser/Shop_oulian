package tst.project.dao.interfaces;

import java.util.List;

import tst.project.bean.member.MainBrandBean;

public interface MainBrandDao {
	
	/**
	 * 查询主营品牌
	 * @param mainBrandBean
	 * @return
	 */
	public List<MainBrandBean> getMainBrandList(MainBrandBean mainBrandBean);
	
	/**
	 * 添加主营品牌
	 * @param mainBrandBean
	 * @return
	 */
	public int addMainBrand(MainBrandBean mainBrandBean);
	
	/**
	 * 删除主营品牌
	 * @param mainBrandBean
	 * @return
	 */
	public int deleteMainBrand(MainBrandBean mainBrandBean);
}
