package tst.project.dao.controller;

/**
 * @author xingqianji
 */

import java.util.List;

import tst.project.bean.entrust.EntrustBean;
import tst.project.page.PageBean;


public interface EntrustDaoC {
	
	/**
	 * 根据委托订单号查询委托详情信息
	 */
	public List<EntrustBean> getEntrustDateilByNumber(EntrustBean entrustBean,PageBean pageBean);
	
	/**
	 * 根据委托订单号查询委托详情信息  （不分页）
	 */
	public List<Object> getEntrustDateilByNumberNoPage(EntrustBean entrustBean);
	
}
