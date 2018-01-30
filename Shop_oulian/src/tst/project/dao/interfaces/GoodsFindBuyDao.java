package tst.project.dao.interfaces;

import java.util.List;

import tst.project.bean.goods.GoodsFindBuyBean;
import tst.project.bean.goods.OfflineQuotationBean;
import tst.project.page.PageBean;

public interface GoodsFindBuyDao {
	
	/**
	 * 添加求购
	 * @param findBuyBean
	 * @return
	 */
	public int addGoodsFindBuy(GoodsFindBuyBean findBuyBean);
	
	/**
	 * 修改求购信息
	 * @param findBuyBean
	 * @return
	 */
	public int updateGoodsFindBuy(GoodsFindBuyBean findBuyBean);
	
	/**
	 * 删除求购
	 * @param findBuyBean
	 * @return
	 */
	public int deleteGoodsFindBuy(GoodsFindBuyBean findBuyBean);
	
	/**
	 * 批量删除求购
	 * @param findBuyBean
	 * @return
	 */
	public int deleteMoreGoodsFindBuy(GoodsFindBuyBean findBuyBean);
	
	/**
	 * 查询用户是否求购过此型号
	 */
	public GoodsFindBuyBean getGoodsMemberIsFindBuy(GoodsFindBuyBean findBuyBean);
	
	/**
	 * 查询用户求购 
	 */
	public List<GoodsFindBuyBean> getMemberFindBuy(GoodsFindBuyBean findBuyBean,PageBean pageBean);
	
	/**
	 * 查询单个求购信息
	 */
	public GoodsFindBuyBean getFindBuyDatilsById(GoodsFindBuyBean findBuyBean);
	
	/**
	 * 查询单个求购信息是否过期
	 */
	public GoodsFindBuyBean getfindBuyWhetherOverdue(GoodsFindBuyBean findBuyBean);
	
	/**
	 * 一键延期
	 */
	public int batchAddTime(GoodsFindBuyBean findBuyBean);

	/**
	 * 最新求购信息（供应页面）
	 */
	public List<GoodsFindBuyBean> GetNewestFindBuyInfo(GoodsFindBuyBean findBuyBean);
	
	/**
	 * 根据品牌查询求购信息
	 */
	public List<GoodsFindBuyBean> getFindBuyByBrand(GoodsFindBuyBean findBuyBean);
	
	/**
	 *  根据品牌查询求购列表 （分页）
	 */
	public List<GoodsFindBuyBean> getBrandFindBuyListPage(GoodsFindBuyBean goodsFindBuyBean,PageBean pageBean);
	
	/**
	 * 查询型号求购总数
	 */
    public String getFindBuySumCount(GoodsFindBuyBean findBuyBean);
    
    /**
     * 根据型号查询有多少求购商
     */
    public List<GoodsFindBuyBean> getFindBuyMemberByGoodsName(GoodsFindBuyBean findBuyBean,PageBean pageBean);
    
	/**
	 * 离线报价
	 */
    public int addOfflineQuotation(OfflineQuotationBean offlineQuotationBean);
    
    /**
     * 获得求购信息
     */
    public List<GoodsFindBuyBean> getFindBuyInfo(GoodsFindBuyBean findBuyBean,PageBean pageBean);
    
    /**
     * 获得该求购信息的离线报价
     */
    public List<OfflineQuotationBean> getOfflineQuotation(OfflineQuotationBean offlineQuotationBean);
    
    /**
     * 删除报价（单个）
     */
    public int deleteQuotationById(OfflineQuotationBean offlineQuotationBean);
    
    /**
     * 相关求购信息
     */
    public List<GoodsFindBuyBean> relatedFindBuyMessage(GoodsFindBuyBean findBuyBean,PageBean pageBean);
    
    /**
     * 根据品牌查询求购信息
     */
    public List<GoodsFindBuyBean> getFindBuyBeansByBrand(GoodsFindBuyBean findBuyBean,PageBean pageBean);
}
