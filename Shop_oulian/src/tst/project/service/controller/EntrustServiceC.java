package tst.project.service.controller;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import tst.project.bean.entrust.EntrustBean;
import tst.project.dao.controller.EntrustDaoC;
import tst.project.page.PageBean;


/**
 * @author xingqianji
 *
 */
@Service
public class EntrustServiceC {
	
	@Resource
	private EntrustDaoC daoC;
	
	/**
	 * 根据委托订单号查询委托详情信息
	 */
	public List<EntrustBean> getEntrustDateilByNumber(EntrustBean entrustBean,PageBean pageBean)
	{
		return daoC.getEntrustDateilByNumber(entrustBean, pageBean);
	}

	/**
	 * 根据委托订单号查询委托详情信息  （不分页）
	 */
	public List<Object> getEntrustDateilByNumberNoPage(EntrustBean entrustBean)
	{
		return daoC.getEntrustDateilByNumberNoPage(entrustBean);
	}
}
