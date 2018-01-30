package tst.project.service.interfaces;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tst.project.bean.goods.GoodsBean;
import tst.project.bean.goods.GoodsFindBuyBean;
import tst.project.bean.goods.GoodsSupplyDataBean;
import tst.project.bean.goods.OfflineQuotationBean;
import tst.project.bean.member.CustomerServiceBean;
import tst.project.bean.member.MemberBean;
import tst.project.dao.interfaces.GoodsDaoI;
import tst.project.dao.interfaces.GoodsFindBuyDao;
import tst.project.page.PageBean;

/**
 * @author xingqianji
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GoodsFindBuyService {
	
	@Resource
	private GoodsFindBuyDao dao;
	
	@Resource
	private GoodsServiceI goodsServiceI;
	
	@Resource
	GoodsSupplyDataService supplyService;
	
	@Resource
	MemberService memberService;
	
	@Resource
	GoodsDaoI goodsDaoI;
	
	/**
	 * 添加求购
	 * @param findBuyBean
	 * @return
	 */
	public int addGoodsFindBuy(GoodsFindBuyBean findBuyBean)
	{
		return dao.addGoodsFindBuy(findBuyBean);
	}
	
	/**
	 * 修改求购信息
	 * @param findBuyBean
	 * @return
	 */
	public int updateGoodsFindBuy(GoodsFindBuyBean findBuyBean)
	{
		return dao.updateGoodsFindBuy(findBuyBean);
	}
	
	/**
	 * 删除求购
	 * @param findBuyBean
	 * @return
	 */
	public int deleteGoodsFindBuy(GoodsFindBuyBean findBuyBean)
	{
		return dao.deleteGoodsFindBuy(findBuyBean);
	}

	/**
	 * 批量删除求购
	 * @param findBuyBean
	 * @return
	 */
	public int deleteMoreGoodsFindBuy(GoodsFindBuyBean findBuyBean)
	{
		return dao.deleteMoreGoodsFindBuy(findBuyBean);
	}
	
	/**
	 * 查询用户是否求购过此型号
	 */
	public GoodsFindBuyBean getGoodsMemberIsFindBuy(GoodsFindBuyBean findBuyBean)
	{
		return dao.getGoodsMemberIsFindBuy(findBuyBean);
	}
	
	/**
	 * 查询用户求购 
	 */
	public List<GoodsFindBuyBean> getMemberFindBuy(GoodsFindBuyBean findBuyBean,PageBean pageBean)
	{
		return dao.getMemberFindBuy(findBuyBean,pageBean);
	}
	
	/**
	 * 查询单个求购信息
	 */
	public GoodsFindBuyBean getFindBuyDatilsById(GoodsFindBuyBean findBuyBean)
	{
		return dao.getFindBuyDatilsById(findBuyBean);
	}
	
	/**
	 * 查询单个求购信息是否过期
	 */
	public GoodsFindBuyBean getfindBuyWhetherOverdue(GoodsFindBuyBean findBuyBean)
	{
		return dao.getfindBuyWhetherOverdue(findBuyBean);
	}
	
	/**
	 * 一键延期
	 */
	public int batchAddTime(GoodsFindBuyBean findBuyBean)
	{
		return dao.batchAddTime(findBuyBean);
	}
	
	/**
	 * 最新求购信息（供应页面）
	 */
	public List<GoodsFindBuyBean> GetNewestFindBuyInfo(GoodsFindBuyBean findBuyBean)
	{
		return dao.GetNewestFindBuyInfo(findBuyBean);
	}
	
	/**
	 * 快速搜索（匹配满足条件的供应数据）
	 */
	public GoodsBean getMoreSearch(GoodsBean goodsBean)
	{
		GoodsBean goodsBean1 = goodsServiceI.getGoodsIsSupply(goodsBean);
		if(goodsBean1!=null)
		{
			//获得型号基本信息（单价区间，最小起订量等）
			GoodsBean goodsBean2 = goodsServiceI.getGoodsBasicDetail(new GoodsBean()
			                      .setGoods_name(goodsBean1.getGoods_name()));
			goodsBean1.setPrice_interval(goodsBean2.getPrice_interval());
			goodsBean1.setBuy_num_interval(goodsBean2.getBuy_num_interval());
			
			//获得型号总供应数 
			String totalStock = goodsServiceI.getTotalStock(new GoodsBean()
			                    .setGoods_name(goodsBean1.getGoods_name()));
			goodsBean1.setTotalStock(totalStock);
			
			List<GoodsBean> goodsBeans = goodsServiceI.getSupplyGoodsBeans(goodsBean.setData_categories("1").setLimit(5));
			if(goodsBeans.size()>0)
			{
			    for (int i = 0; i < goodsBeans.size(); i++) {
					MemberBean memberBean = memberService.getMemberByMerchants(new MemberBean()
					                       .setMerchants_id(goodsBeans.get(i).getMerchants_id()));
					
					goodsBeans.get(i).setMemberBean(memberBean);
					
					List<GoodsSupplyDataBean> supplyDataBeans = supplyService.getGoodsSupplyData(new GoodsSupplyDataBean()
					                                            .setGoods_id(goodsBeans.get(i).getGoods_id()+""));
					
					goodsBeans.get(i).setSupplyDataBeans(supplyDataBeans);
				}
			    goodsBean1.setGoodsBeans(goodsBeans);
			}
		}
		return goodsBean1;
	}
	
	
	/**
	 * 品牌求购列表（求购）
	 */
	public List<GoodsBean> getBrandFindBuyList(GoodsBean goodsBean,PageBean pageBean)
	{
		List<GoodsBean> goodsBeans = goodsServiceI.getOneGoodsClassParentPage(goodsBean.setGoods_type("1")
                       .setParent_id("-1"), pageBean);
		if(goodsBeans.size() > 0)
		{
		    for (int i = 0; i < goodsBeans.size(); i++) {
				List<GoodsFindBuyBean> findBuyBeans = dao.getFindBuyByBrand(new GoodsFindBuyBean()
				                      .setGoods_uuid(goodsBeans.get(i).getGoods_uuid()));
				if(findBuyBeans.size() > 0)
				{
					List<GoodsBean> goodsBeans1 = new ArrayList<GoodsBean>();
					for (int j = 0; j < findBuyBeans.size(); j++) {
						//获得总求购数
						String totalFindBuyCount = dao.getFindBuySumCount(new GoodsFindBuyBean()
						                  .setGoods_name(findBuyBeans.get(j).getGoods_name()));
						
						//获得平台该型号信息
						GoodsBean goodsBean1 = goodsServiceI.getGoodsData(new GoodsBean()
						                  .setGoods_name(findBuyBeans.get(j).getGoods_name()));
						
						goodsBean1.setTotalFindBuyCount(totalFindBuyCount);
					
						goodsBeans1.add(goodsBean1);
					}
					goodsBeans.get(i).setGoodsBeans(goodsBeans1);
				}
			}
		}
		return goodsBeans;
	}
	
	/**
	 * 根据型号查询该详情（从平台商品点进去）
	 */
	public GoodsBean getGoodsDataByName(GoodsBean goodsBean)
	{
		GoodsBean goodsBean1 = goodsDaoI.getGoodsData(goodsBean);
		if(goodsBean1!=null)
		{
			//获得品牌信息
			GoodsBean brand = goodsServiceI.getOneBrandDateil(new GoodsBean()
			                  .setGoods_id(Integer.valueOf(goodsBean1.getParent_id())));
			if(brand!=null)
			{
				goodsBean1.setBrand_name(brand.getGoods_name());
			}
			
			//获得总求购数
			String totalFindBuyCount = dao.getFindBuySumCount(new GoodsFindBuyBean()
			                  .setGoods_name(goodsBean1.getGoods_name()));
			
			goodsBean1.setTotalFindBuyCount(totalFindBuyCount);
		}
		return goodsBean1;
	}
	
	/**
	 * 根据型号查询有多少求购商
	 */
	public List<GoodsFindBuyBean> getFindBuyMemberByGoodsName(GoodsFindBuyBean goodsFindBuyBean,PageBean pageBean)
	{
		List<GoodsFindBuyBean> beans = dao.getFindBuyMemberByGoodsName(goodsFindBuyBean, pageBean);
		if(beans.size() > 0)
		{
			for (int i = 0; i < beans.size(); i++) {
				
				MemberBean memberBean =  memberService.getMemberByIDNoFull(new MemberBean()
                           .setMember_id(Integer.valueOf(beans.get(i).getMember_id())));

				if(memberBean!=null)
				{
					beans.get(i).setMemberBean(memberBean);
				}
				
				List<CustomerServiceBean> customerServiceBeans =
					memberService.getMemberCustomerServiceQQ(new CustomerServiceBean()
					    .setMember_id(beans.get(i).getMember_id()));
				
				beans.get(i).setCustomerServiceBeans(customerServiceBeans);
			}
		}
		return beans;
	}
	
	/**
	 * 根据品牌查询求购列表 （分页）
	 */
	public List<GoodsFindBuyBean> getBrandFindBuyListPage(GoodsFindBuyBean goodsFindBuyBean,PageBean pageBean)
	{
		List<GoodsFindBuyBean> findBuyBeans = dao.getBrandFindBuyListPage(goodsFindBuyBean, pageBean);
		if(findBuyBeans.size() > 0)
		{
			for (int i = 0; i < findBuyBeans.size(); i++) {
				GoodsBean goodsBean = goodsServiceI.getGoodsData(new GoodsBean()
				                     .setGoods_name(findBuyBeans.get(i).getGoods_name()));
				
				if(goodsBean!=null)
				{
					findBuyBeans.get(i).setGoods_img(goodsBean.getGoods_img());
					
					GoodsBean brand = goodsServiceI.getOneBrandDateil(new GoodsBean()
			          .setGoods_id(Integer.valueOf(goodsBean.getParent_id())));
					
					if(brand!=null)
					{
						findBuyBeans.get(i).setBrand_img(brand.getGoods_img());
						findBuyBeans.get(i).setBrand_name(brand.getGoods_name());
					}
				}
			}
		}
		return findBuyBeans;
	}
	
	
	/**
	 * 根据型号查询该详情（从商家点进去）
	 */
	public GoodsFindBuyBean getOneFindBuyById(GoodsFindBuyBean goodsFindBuyBean)
	{
		GoodsFindBuyBean goodsFindBuyBean1 = dao.getFindBuyDatilsById(goodsFindBuyBean);
		if(goodsFindBuyBean1!=null)
		{
			GoodsBean goodsBean = goodsServiceI.getGoodsData(new GoodsBean()
			          .setGoods_name(goodsFindBuyBean1.getGoods_name()));
			if(goodsBean!=null)
			{
				//加入平台照片
				goodsFindBuyBean1.setGoods_img(goodsBean.getGoods_img());
				
				//获得该型号所属品牌
				if(goodsBean.getParent_id()!=null ||
				   !goodsBean.getParent_id().equals(""))
				{
					GoodsBean brand = goodsServiceI.getOneBrandDateil(new GoodsBean()
	                  .setGoods_id(Integer.valueOf(goodsBean.getParent_id())));
					
					if(brand!=null)
					{
						goodsFindBuyBean1.setBrand_name(brand.getGoods_name());
					}
				}
				
			}
			
			//获得求购者信息
			MemberBean memberBean = memberService.getMemberByID(new MemberBean()
			           .setMember_id(Integer.valueOf(goodsFindBuyBean1.getMember_id())));
			
			goodsFindBuyBean1.setMemberBean(memberBean);
		}
		return goodsFindBuyBean1;
	}
	
	/**
	 * 离线报价
	 */
	@Transactional
    public int addOfflineQuotation(OfflineQuotationBean offlineQuotationBean)
    {
    	return dao.addOfflineQuotation(offlineQuotationBean);
    }
    
    /**
     * 获得求购信息
     */
    public List<GoodsFindBuyBean> getFindBuyInfo(GoodsFindBuyBean findBuyBean,PageBean pageBean)
    {
    	List<GoodsFindBuyBean> findBuyBeans = dao.getFindBuyInfo(findBuyBean, pageBean);
    	if(findBuyBeans.size() > 0)
    	{
    		for (int i = 0; i < findBuyBeans.size(); i++) {
    			
    			List<OfflineQuotationBean> offlineQuotationBeans = dao.getOfflineQuotation(new OfflineQuotationBean()
    			                         .setFind_id(findBuyBeans.get(i).getFind_id()+""));
    			
    			if(offlineQuotationBeans !=null)
    			{
    				for (int j = 0; j < offlineQuotationBeans.size(); j++) {
    					List<CustomerServiceBean> customerServiceBeans =
    						memberService.getMemberCustomerServiceQQ(new CustomerServiceBean()
    						    .setMember_id(offlineQuotationBeans.get(j).getMember_id()));
    					
    					offlineQuotationBeans.get(j).setCustomerServiceBeans(customerServiceBeans);
					}
    			}
    			findBuyBeans.get(i).setOfflineQuotationBeans(offlineQuotationBeans);
			}
    	}
    	return findBuyBeans;
    }
    
    /**
     * 获得该求购信息的离线报价
     */
    public List<OfflineQuotationBean> getOfflineQuotation(OfflineQuotationBean offlineQuotationBean)
    {
    	return dao.getOfflineQuotation(offlineQuotationBean);
    }
    
    /**
     * 删除报价（单个）
     */
    @Transactional
    public int deleteQuotationById(OfflineQuotationBean offlineQuotationBean)
    {
    	return dao.deleteQuotationById(offlineQuotationBean);
    }
    
    /**
     * 相关求购信息
     */
    public List<GoodsFindBuyBean> relatedFindBuyMessage(GoodsFindBuyBean findBuyBean,PageBean pageBean)
    {
    	return dao.relatedFindBuyMessage(findBuyBean, pageBean);
    }
    
    /**
     * 根据品牌查询求购信息
     */
    public List<GoodsFindBuyBean> getFindBuyBeansByBrand(GoodsFindBuyBean findBuyBean,PageBean pageBean)
    {
    	List<GoodsFindBuyBean> findBuyBeans = dao.getFindBuyBeansByBrand(findBuyBean, pageBean);
    	if(findBuyBeans.size() > 0)
		{
			for (int i = 0; i < findBuyBeans.size(); i++) {
				
				MemberBean memberBean =  memberService.getMemberByIDNoFull(new MemberBean()
                           .setMember_id(Integer.valueOf(findBuyBeans.get(i).getMember_id())));

				if(memberBean!=null)
				{
					findBuyBeans.get(i).setMemberBean(memberBean);
				}
				
				List<CustomerServiceBean> customerServiceBeans =
					memberService.getMemberCustomerServiceQQ(new CustomerServiceBean()
					    .setMember_id(findBuyBeans.get(i).getMember_id()));
				
				findBuyBeans.get(i).setCustomerServiceBeans(customerServiceBeans);
			}
		}
    	return findBuyBeans;
    }
}
