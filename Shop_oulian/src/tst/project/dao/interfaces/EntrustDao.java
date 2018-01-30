package tst.project.dao.interfaces;

/**
 * @author xingqianji
 */

import java.util.List;

import tst.project.bean.entrust.EntrustBean;
import tst.project.page.PageBean;


public interface EntrustDao {
	
	/**
	 * 添加客户委托信息 
	 * @param memberEntrustBean
	 * @return
	 */
	public int addEntrustDateil(EntrustBean entrustBean);
	
	/**
	 * 根据委托订单号查询委托详情信息
	 */
	public List<EntrustBean> getEntrustDateilByNumber(EntrustBean entrustBean,PageBean pageBean);
	
}
