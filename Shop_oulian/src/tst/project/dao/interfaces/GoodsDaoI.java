package tst.project.dao.interfaces;

import java.util.List;
import java.util.Map;

import tst.project.bean.activity.ActivityBean;
import tst.project.bean.activity.GroupBuyGoodsBean;
import tst.project.bean.goods.BrandBean;
import tst.project.bean.goods.BrandPackageBean;
import tst.project.bean.goods.BrandPackageGoodsBean;
import tst.project.bean.goods.BrandPackageImgBean;
import tst.project.bean.goods.GoodsBean;
import tst.project.bean.goods.GoodsCategoriesBean;
import tst.project.bean.goods.GoodsFindBuyBean;
import tst.project.bean.goods.GoodsImgBean;
import tst.project.bean.goods.GoodsLabelBean;
import tst.project.bean.goods.GoodsNewBean;
import tst.project.bean.goods.GoodsParameterBean;
import tst.project.bean.goods.GoodsProfitBean;
import tst.project.bean.goods.GoodsServiceBean;
import tst.project.bean.goods.GoodsSupplyDataBean;
import tst.project.bean.goods.ManualBean;
import tst.project.bean.goods.SSPClassBean;
import tst.project.bean.goods.StandardBean;
import tst.project.bean.goods.StoreHouseBean;
import tst.project.bean.member.MemberBean;
import tst.project.bean.others.SearchBean;
import tst.project.page.PageBean;

public interface GoodsDaoI {
	
	/**
	 * 商家供应数量
	 * @param goodsBean
	 * @return
	 */
	public int getMerchantsGoodsCount(GoodsBean goodsBean);
	
	/**
	 * 发布供应数量
	 * @param goodsFindBuyBean
	 * @return
	 */
	public int getGoodsFindBuyCount(GoodsFindBuyBean goodsFindBuyBean);

	/**
	 * 首页热门求购
	 * @param goodsFindBuyBean
	 * @return
	 */
	public List<GoodsFindBuyBean> getHotGoodsFindBuy(GoodsFindBuyBean goodsFindBuyBean);

	/**
	 * 首页热门供应
	 * @param goodsBean
	 * @return
	 */
	public List<GoodsBean> getHotGoods(GoodsBean goodsBean);
	
	public List<MemberBean> getMerchantsByBrand(GoodsBean goodsBean);

	/**
	 * 推荐品牌
	 * @param goodsBean
	 * @return
	 */
	public List<GoodsBean> getRecommendGoodsBrand(GoodsBean goodsBean);
	
	/**
	 * 
	 * @param goodsProfitBean
	 * @return
	 */
	public List<MemberBean> getMemberAuthorizationRanking(GoodsProfitBean goodsProfitBean);
	/**
	 * 商品收益排行
	 * @param goodsProfitBean
	 * @param pageBean
	 * @return
	 */
	public List<GoodsProfitBean> getGoodsProfitsRanking(GoodsProfitBean goodsProfitBean,PageBean pageBean);
	/**
	 * 商品收益
	 * @return
	 */
	public int insertGoodsProfit(GoodsProfitBean goodsProfitBean);
	
	/**
	 * 商品收益
	 * @return
	 */
	public int updateGoodsProfit(GoodsProfitBean goodsProfitBean);
	
	/**
	 * 商品收益
	 * @return
	 */
	public GoodsProfitBean getGoodsProfitCurMonth(GoodsProfitBean goodsProfitBean);
	
	/**
	 * 
	 * @param goodsBean
	 * @return
	 */
	public GoodsBean getGoodsBrandDetail(GoodsBean goodsBean);
	
	/**
	 * 品牌实力商家榜
	 * @param goodsBean
	 * @return
	 */
	public List<MemberBean> getTopBrandMerchants(GoodsBean goodsBean);
	
	/**
	 * 品牌实力用户榜
	 * @param goodsBean
	 * @return
	 */
 	public List<MemberBean> getTopBrandMember(GoodsBean goodsBean);
	
 	/**
 	 * 品牌产品手册
 	 * @param manualBean
 	 * @return
 	 */
	public List<ManualBean> getGoodsManual(ManualBean manualBean);
	
	public List<GoodsNewBean> getGoodsNews(GoodsNewBean goodsNewBean);
	
	public List<GoodsBean> getGoodsBrands(GoodsBean goodsBean,PageBean pageBean);
	
	/**
	 * 获得热搜列表
	 * @return
	 */
	public List<SearchBean> getHotSearchs(SearchBean searchBean);
	
	
	/**
	 * 获得一个搜热
	 * @return
	 */
	public SearchBean getHotSearch(SearchBean searchBean);
	
	/**
	 * 修改搜热
	 * @return
	 */
	public int updateHotSearch(SearchBean searchBean);
	
	/**
	 * 添加搜热
	 * @return
	 */
	public int inserHotSearch(SearchBean searchBean);
	
	
	/**
	 * 首页活动列表
	 * @param activityBean
	 * @return
	 */
	public List<ActivityBean> getHomeActivitys(tst.project.bean.home.ActivityBean activityBean);
	
	/**
	 * 预售商品列表
	 * @return
	 */
	public List<GoodsBean> getPreSaleGoodss(GoodsBean goodsBean, PageBean pageBean);
	
	
	/**
	 * 热门商品
	 * @param goodsBean
	 * @param pageBean
	 * @return
	 */
	public List<GoodsBean> getHotGoods(GoodsBean goodsBean,PageBean pageBean);
	
	/**
	 * 畅销商品(后台设置的)
	 * @param goodsBean
	 * @param pageBean
	 * @return
	 */
	public List<GoodsBean> getSellingGoods(GoodsBean goodsBean,PageBean pageBean);
	/**
	 * 畅销商品(真正的按销量排名)
	 * @param goodsBean
	 * @param pageBean
	 * @return
	 */
	public List<GoodsBean> getSellingGoodsReal(GoodsBean goodsBean,PageBean pageBean);
	
	public GoodsBean getGoodsClassById(GoodsBean goodsBean);
	/**
	 * 根据父id获得所有商品分类
	 * @param goodsBean
	 * @return
	 */
	public List<GoodsBean> getGoodsClassParent(GoodsBean goodsBean);
	
	/**
	 * 根据父id获得所有商品分类（分页）
	 * @param goodsBean
	 * @return
	 */
	public List<GoodsBean> getGoodsClassParentPage(GoodsBean goodsBean,PageBean pageBean);
	
	/**
	 * 根据父id获得所有商品分类
	 * @param goodsBean
	 * @return
	 */
	public List<Map> getGoodsClassParentV2(GoodsBean goodsBean);
	
	/**
	 * 通过数组获得参数列表
	 * @return
	 */
	public List<GoodsParameterBean> getGoodsParameterBeansByArray(Map<String,Object> params);
	
	/**
	 * 获得该分类下的活动列表
	 * 
	 * @param goodsBean
	 * @return
	 */
	public List<ActivityBean> getActivityByClass(GoodsBean goodsBean) ;
	
	/**
	 * 获得该品牌下分类列表
	 * @param goodsBean
	 * @return
	 */
	public List<GoodsBean> getClassByBrand3(GoodsBean goodsBean);
	/**
	 * 获得该品牌下分类列表
	 * @param goodsBean
	 * @return
	 */
	public List<GoodsBean> getClassByBrand1(GoodsBean goodsBean);
	/**
	 * 获得该品牌下分类列表
	 * @param goodsBean
	 * @return
	 */
	public List<GoodsBean> getClassByBrand2(GoodsBean goodsBean);
	/**
	 * 获得该分类下的品牌列表
	 * @param goodsBean
	 * @return
	 */
	public List<BrandBean> getAllBrandByClass(GoodsBean goodsBean);
	
	/**
	 * 获得该分类下的标签列表
	 * @param goodsLabelBean
	 * @return
	 */
	public List<GoodsLabelBean> getAllGoodsLabelByClass(GoodsBean goodsBean);
	/**
	 * 获得该分类下的标签列表
	 * @param goodsLabelBean
	 * @return
	 */
	public List<StoreHouseBean> getAllStoreHouseByClass(GoodsBean goodsBean);
	/**
	 * 根据各种条件 搜索商品列表
	 * @param goodsBean
	 * @param pageBean
	 * @return
	 */
	public List<GoodsBean> searchGoodsDetailList(GoodsBean goodsBean,PageBean pageBean);
	
	
	/**
	 * 修改商品浏览量
	 * @param goodsBean
	 * @return
	 */
	public int updateGoodsDetailSeenum(GoodsBean goodsBean);
	
	/**
	 * 获得商品详情
	 * @param goodsBean
	 * @return
	 */
	public GoodsBean getOneGoodsDetail(GoodsBean goodsBean);
	
	/**
	 * 商品的其他参数选择
	 * 
	 * @param goodsBean
	 * @param pageBean
	 * @return
	 */
	public List<GoodsLabelBean> getGoodsClassLabels(GoodsLabelBean goodsLabelBean) ;
	
	/**
	 * 商品的团购详情
	 * @param groupBuyGoodsBean
	 * @return
	 */
	public List<GroupBuyGoodsBean> getGroupBuyGoodss(GroupBuyGoodsBean groupBuyGoodsBean);
	
	/**
	 * 商品的规格数据
	 * @param groupBuyGoodsBean
	 * @return
	 */
	public List<StandardBean> getGoodsStandards(StandardBean standardBean) ;
	
	/**
	 * 商品的图片列表
	 * @param goodsImgBean
	 * @return
	 */
	public List<GoodsImgBean> getGoodsImgs(GoodsImgBean goodsImgBean);
	
	/**
	 * 获得所有商品参数
	 * @return
	 */
	public List<GoodsParameterBean> getAllParametersByParent(GoodsParameterBean goodsParameterBean);
	/**
	 * 获得所有服务参数
	 * @return
	 */
	public List<GoodsServiceBean> getAllServiceByParent(GoodsServiceBean goodsServiceBean);
	
	
	/**
	 * 获得品牌套餐
	 * @param brandPackageBean
	 * @return
	 */
	public List<BrandPackageBean> getBrandPackageByBrand(BrandPackageBean brandPackageBean);
	
	/**
	 * 获得品牌详情
	 * @param brandBean
	 * @return
	 */
	public BrandBean getBrandDetail(BrandBean brandBean);
	/**
	 * 获得品牌套餐图片
	 * @param brandPackageBean
	 * @return
	 */
	public List<BrandPackageImgBean> getBrandPackageImgs(BrandPackageImgBean brandPackageImgBean);
	/**
	 * 获得品牌套餐详情
	 * @param brandPackageBean
	 * @return
	 */
	public BrandPackageBean getBrandPackageGoodsDetail(BrandPackageBean brandPackageBean);
	/**
	 * 获得品牌套餐商品
	 * @param brandPackageBean
	 * @return
	 */
	public List<BrandPackageGoodsBean> getBrandPackageGoods(BrandPackageGoodsBean brandPackageGoodsBean);
	
	
	//顺手拍
	
	/**
	 *所有商品
	 * @return
	 */
	public List<GoodsBean> getAllGoods(GoodsBean goodsBean,PageBean pageBean);
	/**
	 * 顺手拍七个标签 分类
	 * @param goodsBean
	 * @return
	 */
	public List<SSPClassBean> getSSPGoodsClass(SSPClassBean sspClassBean);
	
	/**
	 * 促销标签
	 * 
	 * @return
	 */
	public List<GoodsBean> getPromotionGoods(GoodsBean goodsBean, PageBean pageBean);

	/**
	 * 礼物标签
	 * @return
	 */
	public List<GoodsBean> getGiftGoods(GoodsBean goodsBean,PageBean pageBean);
	
	
	/**
	 * 生鲜标签
	 * @return
	 */
	public List<GoodsBean> getFreshGoods(GoodsBean goodsBean,PageBean pageBean);
	
	
	/**
	 * 母婴标签
	 * @return
	 */
	public List<GoodsBean> getBabyGoods(GoodsBean goodsBean,PageBean pageBean);
	
	
	/**
	 * 女士标签
	 * @return
	 */
	public List<GoodsBean> getLadyGoods(GoodsBean goodsBean,PageBean pageBean);
	
	
	/**
	 * 特色标签
	 * @return
	 */
	public List<GoodsBean> getFeatureGoods(GoodsBean goodsBean,PageBean pageBean);
	
	/**
	 * 进口标签
	 * @return
	 */
	public List<GoodsBean> getImportGoods(GoodsBean goodsBean,PageBean pageBean);
	
	
	//家纺
	/**
	 * 首页热门商品
	 * @param goodsBean
	 * @param pageBean
	 * @return
	 */
	public List<GoodsBean> getHomeHotGoods(GoodsBean goodsBean,PageBean pageBean);
	/**
	 * 首页最新商品
	 * 
	 * @param goodsBean
	 * @param pageBean
	 * @return
	 */
	public List<GoodsBean> getNewHotGoods(GoodsBean goodsBean,PageBean pageBean) ;
	/**
	 * 首页畅销商品(后台设置的)
	 * @param goodsBean
	 * @param pageBean
	 * @return
	 */
	public List<GoodsBean> getHomeSellingGoods(GoodsBean goodsBean,PageBean pageBean);
	
	
	/**
	 * 分类热门商品
	 * @param goodsBean
	 * @param pageBean
	 * @return
	 */
	public List<GoodsBean> getClassHotGoods(GoodsBean goodsBean,PageBean pageBean);
	
	/**
	 * 分类畅销商品(后台设置的)
	 * @param goodsBean
	 * @param pageBean
	 * @return
	 */
	public List<GoodsBean> getClassSellingGoods(GoodsBean goodsBean,PageBean pageBean);
	
	/**
	 * 分类畅销商品(后台设置的)
	 * @param goodsBean
	 * @param pageBean
	 * @return
	 */
	public List<GoodsBean> getClassPriceSortGoods(GoodsBean goodsBean,PageBean pageBean);
	
	
	//欧联
	/**
	 * 查询商品数据类别
	 */
	public List<GoodsCategoriesBean> getGoodsCategories();
	
	/**
	 * 查询单个品牌信息
	 */
	public GoodsBean getOneBrandDateil(GoodsBean goodsBean);
	
	/**
	 * 查询该商品是否可供应（平台是否有该商品）
	 */
	public GoodsBean getGoodsIsSupply(GoodsBean goodsBean);
	
	/**
	 * 查询该商品客户是否已经供应
	 */
	public GoodsBean getGoodsMerchantsIsSupply(GoodsBean goodsBean);
	
	/**
	 * 添加商品供应
	 */
	public int addGoodsSupply(GoodsBean goodsBean);
	/**
	 * 添加商品供应
	 */
	public int addGoodsSupplys(GoodsBean goodsBean);
	/**
	 * 查询商品供应总数据（平台）
	 */
	public GoodsBean getGoodsData(GoodsBean goodsBean);
	
	/**
	 * 查询除平台外该商品数据 
	 */
	public List<GoodsBean> getSupplyGoodsBeans(GoodsBean goodsBean);
	
	/**
	 * 查询除平台外该商品数据 
	 */
	public int getSupplyGoodsBeansCount(GoodsBean goodsBean);
	
	/**
	 * 查询除平台外该商品数据 （分页）
	 */
	public List<GoodsBean> getSupplyGoodsBeans(GoodsBean goodsBean,PageBean pageBean);
	
	/**
	 * 查询用户商品列表
	 */
	public int getMemberGoodsListCount(GoodsBean goodsBean);
	/**
	 * 查询用户商品列表
	 */
	public List<GoodsBean> getMemberGoodsList(GoodsBean goodsBean);
	
	/**
	 * 根据品牌查询商品
	 */
	public List<GoodsBean> getGoodsByBrand(GoodsBean goodsBean);
	
	/**
	 * 根据品牌查询商品(分页)
	 */
	public List<GoodsBean> getGoodsByBrandPage(GoodsBean goodsBean,PageBean pageBean);
	
	/**
	 * 修改商品信息
	 */
	public int updateGoodsDateil(GoodsBean goodsBean);
	
	/**
	 * 查询商品供应总数据（商家型号点进去）
	 */
	public GoodsBean getMerchantsGoodsData(GoodsBean goodsBean);
	
	/**
	 * 最新供应消息
	 */
	public List<GoodsBean> GetNewestSupplyInfo(GoodsBean goodsBean);
	
	/**
	 * 热销商品列表
	 */
	public List<GoodsBean> getSellHotGoods(GoodsBean goodsBean);
	
	/**
	 * 批量上下架 
	 */
	public int batchUpAndDown(GoodsBean goodsBean);
	
	/**
	 * 删除型号
	 */
	public int deleteGoodsName(GoodsBean goodsBean);
	
	/**
	 * 批量删除型号
	 */
	public int batchDeleteGoodsName(GoodsBean goodsBean);
	
	/**
	 * 获得型号基本信息（单价区间，最小起订量等）
	 */
	public GoodsBean getGoodsBasicDetail(GoodsBean goodsBean);
	
	/**
	 * 获得型号总供应数
	 */
	public String getTotalStock(GoodsBean goodsBean);
	
	/**
	 * 根据型号别名查询该型号
	 */
	public GoodsBean getGoodsByAlias(GoodsBean goodsBean);
	
	/**
	 * 全部删除
	 */
	public int allGoodsDelete(GoodsBean goodsBean);
	
	/**
	 * 全部上架/下架 
	 */
	public int allGoodsStateUpdate(GoodsBean goodsBean);
	
	public int allGoodsSupplyStateUpdate(GoodsSupplyDataBean goodsSupplyDataBean);
	/**
	 * 供应列表筛选
	 */
	public List<GoodsBean> SupplyScreeningList(GoodsBean goodsBean,PageBean pageBean);
	
	/**
	 * 商家导出型号列表
	 */
	public List<Object> exportMemberGoodsExcel(GoodsBean goodsBean);
	
	/**
	 * 平台商品搜索（首页）
	 */
	public List<GoodsBean> getGoodsDataList(GoodsBean goodsBean,PageBean pageBean);
	
	/**
	 * 查询该品牌下供应下供应该品牌的型号
	 */
	public List<GoodsBean> getBrandMemberGoodsName(GoodsBean goodsBean,PageBean pageBean);
	
	/**
	 * 查询可替换商品
	 */
	public List<GoodsBean> replaceableGoods(GoodsBean goodsBean);
	
	/**
	 * 相关供应消息
	 */
    public  List<GoodsBean> relatedSupplyMessage(GoodsBean goodsBean,PageBean pageBean);	
}
