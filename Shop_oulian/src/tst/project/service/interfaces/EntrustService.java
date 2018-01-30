package tst.project.service.interfaces;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tst.project.bean.entrust.EntrustBean;
import tst.project.dao.interfaces.EntrustDao;
import tst.project.page.PageBean;


/**
 * @author xingqianji
 *
 */
@Service
public class EntrustService {
	
	@Resource
	private EntrustDao dao;
	
	/**
	 * 添加客户委托信息 
	 * @param memberEntrustBean
	 * @return
	 */
	public int addEntrustDateil(EntrustBean entrustBean)
	{
		return dao.addEntrustDateil(entrustBean);
	}
	
	/**
	 * 根据委托订单号查询委托详情信息
	 */
	public List<EntrustBean> getEntrustDateilByNumber(EntrustBean entrustBean,PageBean pageBean)
	{
		return dao.getEntrustDateilByNumber(entrustBean, pageBean);
	}
	
}
