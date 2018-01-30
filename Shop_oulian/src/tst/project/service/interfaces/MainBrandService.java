package tst.project.service.interfaces;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tst.project.bean.member.MainBrandBean;
import tst.project.dao.interfaces.MainBrandDao;

/**
 * @author xingqianji
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MainBrandService {
	
	@Resource
	private MainBrandDao dao;
	
	/**
	 * 查询主营品牌
	 * @param mainBrandBean
	 * @return
	 */
	public List<MainBrandBean> getMainBrandList(MainBrandBean mainBrandBean)
	{
		return dao.getMainBrandList(mainBrandBean);
	}
	
	/**
	 * 添加主营品牌
	 * @param mainBrandBean
	 * @return
	 */
	@Transactional
	public int addMainBrand(MainBrandBean mainBrandBean)
	{
		return dao.addMainBrand(mainBrandBean);
	}
	
	/**
	 * 删除主营品牌
	 * @param mainBrandBean
	 * @return
	 */
	public int deleteMainBrand(MainBrandBean mainBrandBean)
	{
		return dao.deleteMainBrand(mainBrandBean);
	}
}
