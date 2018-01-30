package tst.project.service.interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.PUT;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tst.project.bean.activity.ActivityBean;
import tst.project.bean.activity.GroupBuyGoodsBean;
import tst.project.bean.address.LocationBean;
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
import tst.project.bean.information.InformationBean;
import tst.project.bean.member.CustomerServiceBean;
import tst.project.bean.member.MemberBean;
import tst.project.bean.merchants.MerchantsBean;
import tst.project.bean.others.SearchBean;
import tst.project.dao.interfaces.GoodsDaoI;
import tst.project.dao.interfaces.GoodsSupplyDataDao;
import tst.project.module.ClassMoudle;
import tst.project.page.PageBean;
import tst.project.utils.BaiduUtils;
import tst.project.utils.CreateRandom;
import tst.project.utils.DistanceUtils;
import tst.project.utils.NumberUtils;
import tst.project.utils.TimeUtils;


@Service
@Transactional(rollbackFor = Exception.class)
public class GoodsServiceI {
	@Resource
	GoodsDaoI goodsDaoI;

	@Resource
	MerchantsServiceI merchantsServiceI;

	@Resource
	ActivityService activityService;
	
	@Resource
	MemberService memberService;

	@Resource
	GoodsSupplyDataService supplyService;
	
	@Resource
	GoodsUtilService utilService;
	
	@Resource
	GoodsServiceI goodsServiceI;

	@Resource
	OthersService othersService;

	@Resource
	LogRecordService recordService;
	
	@Resource
	InformationService informationService;
	
	/**
	 * 商家供应数量
	 * @param goodsBean
	 * @return
	 */
	public int getMerchantsGoodsCount(GoodsBean goodsBean){
		return goodsDaoI.getMerchantsGoodsCount(goodsBean);
	}
	
	public int getGoodsFindBuyCount(GoodsFindBuyBean goodsFindBuyBean){
		return goodsDaoI.getGoodsFindBuyCount(goodsFindBuyBean);
	}
	
	/**
	 * 首页热门求购
	 * @param goodsFindBuyBean
	 * @return
	 */
	public List<GoodsFindBuyBean> getHotGoodsFindBuy(GoodsFindBuyBean goodsFindBuyBean){
		return goodsDaoI.getHotGoodsFindBuy(goodsFindBuyBean);
	}
	/**
	 * 首页热门供应
	 * @param goodsBean
	 * @return
	 */
	public List<GoodsBean> getHotGoods(GoodsBean goodsBean){
		return goodsDaoI.getHotGoods(goodsBean);
	}
	
	public List<MemberBean> getMerchantsByBrand(GoodsBean goodsBean){
		return goodsDaoI.getMerchantsByBrand(goodsBean);
	}
	
	/**
	 * 推荐品牌
	 * @param goodsBean
	 * @return
	 */
	public List<GoodsBean> getRecommendGoodsBrand(GoodsBean goodsBean){
		return goodsDaoI.getRecommendGoodsBrand(goodsBean);
	}
	
	/**
	 * 
	 * @param goodsProfitBean
	 * @return
	 */
	public List<MemberBean> getMemberAuthorizationRanking(GoodsProfitBean goodsProfitBean){
		return goodsDaoI.getMemberAuthorizationRanking(goodsProfitBean);
	}
	/**
	 * 商品收益排行
	 * @param goodsProfitBean
	 * @param pageBean
	 * @return
	 */
	public List<GoodsProfitBean> getGoodsProfitsRanking(GoodsProfitBean goodsProfitBean,PageBean pageBean){
		List<GoodsProfitBean> goodsProfitBeans=goodsDaoI.getGoodsProfitsRanking(goodsProfitBean, pageBean);
		if(goodsProfitBeans!=null){
			for (int i = 0; i < goodsProfitBeans.size(); i++) {
				GoodsProfitBean goodsProfitBean2=goodsProfitBeans.get(i);
				GoodsBean goodsBean=goodsServiceI.getMerchantsGoodsData(new GoodsBean().setGoods_id(NumberUtils.Integer(goodsProfitBean2.getGoods_id())));
				goodsProfitBean2.setGoodsBean(goodsBean);
			}
		}
		return goodsProfitBeans;
	}
	
	/**
	 * 商品收益
	 * @return
	 */
	public int insertGoodsProfit(GoodsProfitBean goodsProfitBean){
		return goodsDaoI.insertGoodsProfit(goodsProfitBean);
	}
	
	/**
	 * 商品收益
	 * @return
	 */
	public int updateGoodsProfit(GoodsProfitBean goodsProfitBean){
		return goodsDaoI.updateGoodsProfit(goodsProfitBean);
	}
	
	/**
	 * 商品收益
	 * @return
	 */
	public GoodsProfitBean getGoodsProfitCurMonth(GoodsProfitBean goodsProfitBean){
		return goodsDaoI.getGoodsProfitCurMonth(goodsProfitBean);
	}
	
	/**
	 * 
	 * @param goodsBean
	 * @return
	 */
	public GoodsBean getGoodsBrandDetail(GoodsBean goodsBean){
		GoodsBean goodsBean2=goodsDaoI.getGoodsBrandDetail(goodsBean);
		String profit_time = TimeUtils.getAchTime(TimeUtils.getLastMonthBegin("yyyy-MM-01 00:00:00", 3));
		if(goodsBean2!=null){
			
			List<MemberBean> brandMerchantsBeans=goodsDaoI.getTopBrandMerchants(goodsBean);
			goodsBean2.setBrandMerchantsBeans(brandMerchantsBeans);
			
			
			List<MemberBean> brandMemberBeans=goodsDaoI.getTopBrandMember(goodsBean);
			goodsBean2.setBrandMemberBeans(brandMemberBeans);
			
			
			List<ManualBean> manualBeans=goodsDaoI.getGoodsManual(new ManualBean().setGoods_id(goodsBean2.getGoods_id()+""));
			goodsBean2.setManualBeans(manualBeans);
			
			List<GoodsNewBean> goodsNewBeans=goodsDaoI.getGoodsNews(new GoodsNewBean().setGoods_id(goodsBean2.getGoods_id()+""));
			goodsBean2.setGoodsNewBeans(goodsNewBeans);
			
			List<GoodsProfitBean> goodsProfitBeans=getGoodsProfitsRanking(new GoodsProfitBean()
					.setParent_id(goodsBean2.getGoods_id()+"")
					.setProfit_time(profit_time),new PageBean().setLimit(4));
			goodsBean2.setGoodsProfitBeans(goodsProfitBeans);
			
			List<MemberBean> memberBeans=getMemberAuthorizationRanking(new GoodsProfitBean()
											.setProfit_time(profit_time)
											.setGoods_name(goodsBean2.getGoods_name()));
			goodsBean2.setMemberBeans(memberBeans);	
			
			List<InformationBean> informationBeans=informationService
					.getInformationsByTag(new InformationBean().setInformation_tag(goodsBean2.getGoods_name()), new PageBean().setLimit(2));
			goodsBean2.setInformationBeans(informationBeans);
		}
		return goodsBean2;
	}
	
	public List<GoodsBean> getGoodsBrands(GoodsBean goodsBean,PageBean pageBean){
		List<GoodsBean> goodsBeans= goodsDaoI.getGoodsBrands(goodsBean, pageBean);
		if(goodsBeans!=null){
			String profit_time = TimeUtils.getAchTime(TimeUtils.getLastMonthBegin("yyyy-MM-01 00:00:00", 3));
			for (int i = 0; i < goodsBeans.size(); i++) {
				GoodsBean goodsBean2=goodsBeans.get(i);
				List<GoodsNewBean> goodsNewBeans=goodsDaoI.getGoodsNews(new GoodsNewBean().setGoods_id(goodsBean2.getGoods_id()+""));
				goodsBean2.setGoodsNewBeans(goodsNewBeans);
				
				List<GoodsProfitBean> goodsProfitBeans=getGoodsProfitsRanking(new GoodsProfitBean()
						.setParent_id(goodsBean2.getGoods_id()+"")
						.setProfit_time(profit_time),new PageBean().setLimit(4));
				goodsBean2.setGoodsProfitBeans(goodsProfitBeans);
				
				List<MemberBean> memberBeans=getMemberAuthorizationRanking(new GoodsProfitBean()
												.setProfit_time(profit_time)
												.setGoods_name(goodsBean2.getGoods_name()));
				goodsBean2.setMemberBeans(memberBeans);
			}
		}
		return goodsBeans;
	}
	
	public int releaseSupplyExcle(List<GoodsBean> goodsBeans,MemberBean memberBean,String merchants_id){
		String record_no = TimeUtils.getCurrentTime("yyyyMMddHHmmss") + CreateRandom.createRandom(true, 10);
		List<GoodsBean> goodsBeans2=new ArrayList<GoodsBean>();
		for (int i = 0; i < goodsBeans.size(); i++) {			
			if(goodsBeans.get(i).getGoods_stock()==null || 
					goodsBeans.get(i).getGoods_stock().equals("") ||
					NumberUtils.Integer(goodsBeans.get(i).getGoods_stock())<0){
				continue;
			}
			
			if(goodsBeans.get(i).getGoods_unit_price()==null || 
					goodsBeans.get(i).getGoods_unit_price().equals("") ||
					!NumberUtils.isNumeric(goodsBeans.get(i).getGoods_unit_price())){
				continue;
			}
			
			if(goodsBeans.get(i).getGive_time()!=null&&goodsBeans.get(i).getGive_time().length()>6){
				continue;
			}
			
			if(goodsBeans.get(i).getMin_buy_num()==null || 
					goodsBeans.get(i).getMin_buy_num().equals("") ||
					NumberUtils.Double(goodsBeans.get(i).getMin_buy_num())<0){
				continue;
			}
			
			if(goodsBeans.get(i).getData_categories()==null ||
					goodsBeans.get(i).getData_categories().equals("")){
				continue;
			}
			
			goodsBeans.get(i).setMerchants_id(merchants_id);
			goodsBeans.get(i).setMember_id(memberBean.getMember_id() + "");
			goodsBeans.get(i).setGoods_no(record_no);
			goodsBeans.get(i).setGoods_unit_price(
					String.valueOf(NumberUtils.KeepDecimal(NumberUtils.Float(goodsBeans.get(i).getGoods_unit_price()), 4)));
			
			goodsBeans2.add(goodsBeans.get(i));
		}
		

		recordService.addLogRecords(goodsBeans2);
		goodsServiceI.addGoodsSupplys(new GoodsBean().setGoods_no(record_no).setMerchants_id(merchants_id));
		recordService.updateLogRecords(new GoodsBean().setGoods_no(record_no).setMerchants_id(merchants_id));
		supplyService.addSupplyDatas(new GoodsBean().setGoods_no(record_no));
		recordService.deleteHaveGoodsName();
		return 1;
	}
	
	/**
	 * 获得热搜
	 * 
	 * @return
	 */
	public List<SearchBean> getHotSearchs(SearchBean searchBean) {
		return goodsDaoI.getHotSearchs(searchBean);
	}

	/**
	 * 获得一个搜热
	 * 
	 * @return
	 */
	public SearchBean getHotSearch(SearchBean searchBean) {
		return goodsDaoI.getHotSearch(searchBean);
	}

	/**
	 * 修改搜热
	 * 
	 * @return
	 */
	public int updateHotSearch(SearchBean searchBean) {
		return goodsDaoI.updateHotSearch(searchBean);
	}

	/**
	 * 添加搜热
	 * 
	 * @return
	 */
	public int inserHotSearch(SearchBean searchBean) {
		return goodsDaoI.inserHotSearch(searchBean);
	}

	/**
	 * 首页活动列表
	 * 
	 * @param activityBean
	 * @return
	 */
	public List<ActivityBean> getHomeActivitys(tst.project.bean.home.ActivityBean activityBean) {
		return goodsDaoI.getHomeActivitys(activityBean);
	}

	/**
	 * 预售商品列表
	 * 
	 * @return
	 */
	public List<GoodsBean> getPreSaleGoodss(GoodsBean goodsBean, PageBean pageBean) {
		List<GoodsBean> goodsBeans = goodsDaoI.getPreSaleGoodss(goodsBean, pageBean);
		for (int i = 0; i < goodsBeans.size(); i++) {
			List<GoodsImgBean> goodsImgBeans = goodsDaoI
					.getGoodsImgs(new GoodsImgBean().setGoods_id(goodsBeans.get(i).getGoods_id() + ""));
			goodsBeans.get(i).setGoodsImgBeans(goodsImgBeans);
		}
		return goodsBeans;
	}

	/**
	 * 热门商品
	 * 
	 * @param goodsBean
	 * @param pageBean
	 * @return
	 */
	public List<GoodsBean> getHotGoods(GoodsBean goodsBean, PageBean pageBean) {
		List<GoodsBean> goodsBeans = goodsDaoI.getHotGoods(goodsBean, pageBean);
		for (int i = 0; i < goodsBeans.size(); i++) {
			List<GoodsImgBean> goodsImgBeans = goodsDaoI
					.getGoodsImgs(new GoodsImgBean().setGoods_id(goodsBeans.get(i).getGoods_id() + ""));
			goodsBeans.get(i).setGoodsImgBeans(goodsImgBeans);
		}
		return goodsBeans;
	}

	/**
	 * 畅销商品(后台设置的)
	 * 
	 * @param goodsBean
	 * @param pageBean
	 * @return
	 */
	public List<GoodsBean> getSellingGoods(GoodsBean goodsBean, PageBean pageBean) {
		List<GoodsBean> goodsBeans = goodsDaoI.getSellingGoods(goodsBean, pageBean);
		for (int i = 0; i < goodsBeans.size(); i++) {
			List<GoodsImgBean> goodsImgBeans = goodsDaoI
					.getGoodsImgs(new GoodsImgBean().setGoods_id(goodsBeans.get(i).getGoods_id() + ""));
			goodsBeans.get(i).setGoodsImgBeans(goodsImgBeans);
		}
		return goodsBeans;
	}

	/**
	 * 畅销商品(真正的按销量排名)
	 * 
	 * @param goodsBean
	 * @param pageBean
	 * @return
	 */
	public List<GoodsBean> getSellingGoodsReal(GoodsBean goodsBean, PageBean pageBean) {
		List<GoodsBean> goodsBeans = goodsDaoI.getSellingGoodsReal(goodsBean, pageBean);
		for (int i = 0; i < goodsBeans.size(); i++) {
			List<GoodsImgBean> goodsImgBeans = goodsDaoI
					.getGoodsImgs(new GoodsImgBean().setGoods_id(goodsBeans.get(i).getGoods_id() + ""));
			goodsBeans.get(i).setGoodsImgBeans(goodsImgBeans);
		}
		return goodsBeans;
	}
	public List<Map> getOneGoodsClassParentNoV2(GoodsBean goodsBean) {
		return goodsDaoI.getGoodsClassParentV2(goodsBean);
	}
	public List<GoodsBean> getOneGoodsClassParentNo(GoodsBean goodsBean) {
		return goodsDaoI.getGoodsClassParent(goodsBean);
	}

	public List<GoodsBean> getOneGoodsClassParentPage(GoodsBean goodsBean,PageBean page) {
		return goodsDaoI.getGoodsClassParentPage(goodsBean,page);
	}
	
	public List<GoodsBean> getGoodsClassLevel(GoodsBean goodsBean, int level) {
		if (level == 1 && ClassMoudle.getInstance().getGoods1Beans() != null) {
			// return ClassMoudle.getInstance().getGoods1Beans();
		}
		if (level == 2 && ClassMoudle.getInstance().getGoods2Beans() != null&&"-1".equals(goodsBean.getParent_id())) {
			return ClassMoudle.getInstance().getGoods2Beans();
		}
		if (level == 3 && ClassMoudle.getInstance().getGoods3Beans() != null&&"-1".equals(goodsBean.getParent_id())) {
			return ClassMoudle.getInstance().getGoods3Beans();
		}

		List<GoodsBean> goodsBeans = getOneGoodsClassParentNo(goodsBean.setGoods_type("1"));
		if (level < 2) {// 只取一级
			return goodsBeans;
		}
		List<GoodsBean> goods2Beans = getGoodsClassLevel(goodsBeans, level, 2);
		
		if (level == 1) {
			//ClassMoudle.getInstance().setGoods1Beans(goods2Beans);
		} else if (level == 2&&"-1".equals(goodsBean.getParent_id())) {
			ClassMoudle.getInstance().setGoods2Beans(goods2Beans);
		} else if (level == 3&&"-1".equals(goodsBean.getParent_id())) {
			ClassMoudle.getInstance().setGoods3Beans(goods2Beans);
		}
		return goods2Beans;
	}

	
	public List<Map> getGoodsClassLevelV2(GoodsBean goodsBean, int level) {
//		if (level == 1 && ClassMoudle.getInstance().getGoods1Beans() != null) {
//			// return ClassMoudle.getInstance().getGoods1Beans();
//		}
//		if (level == 2 && ClassMoudle.getInstance().getGoods2Beans() != null&&"-1".equals(goodsBean.getParent_id())) {
//			return ClassMoudle.getInstance().getGoods2Beans();
//		}
//		if (level == 3 && ClassMoudle.getInstance().getGoods3Beans() != null&&"-1".equals(goodsBean.getParent_id())) {
//			return ClassMoudle.getInstance().getGoods3Beans();
//		}

		List<Map> goodsBeans = getOneGoodsClassParentNoV2(goodsBean.setGoods_type("1"));
		if (level < 2) {// 只取一级
			return goodsBeans;
		}
		List<Map> goods2Beans = getGoodsClassLevelV2(goodsBeans, level, 2);
		
//		if (level == 1) {
//			//ClassMoudle.getInstance().setGoods1Beans(goods2Beans);
//		} else if (level == 2&&"-1".equals(goodsBean.getParent_id())) {
//			ClassMoudle.getInstance().setGoods2Beans(goods2Beans);
//		} else if (level == 3&&"-1".equals(goodsBean.getParent_id())) {
//			ClassMoudle.getInstance().setGoods3Beans(goods2Beans);
//		}
		return goods2Beans;
	}

	/**
	 * 
	 * @param goodsBeans
	 * @param level
	 *            总共需要取层级
	 * @param start
	 *            目前取到的层级
	 * @return
	 */
	public List<GoodsBean> getGoodsClassLevel(List<GoodsBean> goodsBeans, int level, int start) {
		if (goodsBeans != null) {
			for (int i = 0; i < goodsBeans.size(); i++) {
				List<GoodsBean> goodsBeans2 = getOneGoodsClassParentNo(
						new GoodsBean().setParent_id(goodsBeans.get(i).getGoods_id() + "").setGoods_type("1"));
				if (start < level && goodsBeans2 != null) {// 目前取到的层级 小于
															// 总共需要取层级时 向下取
					goodsBeans2 = getGoodsClassLevel(goodsBeans2, level, start + 1);
				}
				goodsBeans.get(i).setGoodsBeans(goodsBeans2);
			}
		}
		return goodsBeans;
	}
	
	/**
	 * 
	 * @param goodsBeans
	 * @param level
	 *            总共需要取层级
	 * @param start
	 *            目前取到的层级
	 * @return
	 */
	public List<Map> getGoodsClassLevelV2(List<Map> goodsBeans, int level, int start) {
		if (goodsBeans != null) {
			for (int i = 0; i < goodsBeans.size(); i++) {
				List<Map> goodsBeans2 = getOneGoodsClassParentNoV2(
						new GoodsBean().setParent_id(goodsBeans.get(i).get("goods_id") + "").setGoods_type("1"));
				if (start < level && goodsBeans2 != null) {// 目前取到的层级 小于
															// 总共需要取层级时 向下取
					goodsBeans2 = getGoodsClassLevelV2(goodsBeans2, level, start + 1);
				}
				goodsBeans.get(i).put("goodsBeans",goodsBeans2);
			}
		}
		return goodsBeans;
	}

	/**
	 * 通过数组获得参数列表
	 * 
	 * @return
	 */
	public List<GoodsParameterBean> getGoodsParameterBeansByArray(Map<String, Object> params) {
		return goodsDaoI.getGoodsParameterBeansByArray(params);
	}

	/**
	 * 根据父id获得所有商品分类
	 * 
	 * @param goodsBean
	 * @return
	 */
	public List<GoodsBean> getGoodsClassOrder(GoodsBean goodsBean) {
		List<GoodsBean> goodsBeans = goodsDaoI.getGoodsClassParent(goodsBean);
		for (int i = 0; i < goodsBeans.size(); i++) {
			List<BrandBean> brandBeans = getAllBrandByClass(
					new GoodsBean().setGoods_uuid(goodsBeans.get(i).getGoods_uuid()));
			goodsBeans.get(i).setBrandBeans(brandBeans);

		}
		return goodsBeans;
	}

	/**
	 * 根据父id获得所有商品分类
	 * 
	 * @param goodsBean
	 * @return
	 */
	public List<GoodsBean> getGoodsClassParent(GoodsBean goodsBean) {
		List<GoodsBean> goodsBeans = goodsDaoI.getGoodsClassParent(goodsBean);
		for (int i = 0; i < goodsBeans.size(); i++) {
			List<BrandBean> brandBeans = getAllBrandByClass(
					new GoodsBean().setGoods_uuid(goodsBeans.get(i).getGoods_uuid()));
			goodsBeans.get(i).setBrandBeans(brandBeans);

		}
		return goodsBeans;
	}

	/**
	 * 获得该分类下的活动列表
	 * 
	 * @param goodsBean
	 * @return
	 */
	public List<ActivityBean> getActivityByClass(GoodsBean goodsBean) {
		return goodsDaoI.getActivityByClass(goodsBean);
	}

	/**
	 * 获得该品牌下分类列表
	 * 
	 * @param goodsBean
	 * @return
	 */
	public List<GoodsBean> getClassByBrand(GoodsBean goodsBean, String level) {
		if ("1".equals(level)) {
			return goodsDaoI.getClassByBrand1(goodsBean);
		} else if ("2".equals(level)) {
			return goodsDaoI.getClassByBrand2(goodsBean);
		} else {
			return goodsDaoI.getClassByBrand3(goodsBean);
		}
	}

	/**
	 * 获得该分类下的品牌列表
	 * 
	 * @param goodsBean
	 * @return
	 */
	public List<BrandBean> getAllBrandByClass(GoodsBean goodsBean) {
		return goodsDaoI.getAllBrandByClass(goodsBean);
	}

	/**
	 * 获得该分类下的标签列表
	 * 
	 * @param goodsLabelBean
	 * @return
	 */
	public List<GoodsLabelBean> getAllGoodsLabelByClass(GoodsBean goodsBean) {
		return goodsDaoI.getAllGoodsLabelByClass(goodsBean);
	}

	/**
	 * 获得该分类下的仓库列表
	 * 
	 * @param goodsLabelBean
	 * @return
	 */
	public List<StoreHouseBean> getAllStoreHouseByClass(GoodsBean goodsBean) {
		return goodsDaoI.getAllStoreHouseByClass(goodsBean);
	}

	/**
	 * 根据各种条件 搜索商品列表
	 * 
	 * @param goodsBean
	 * @param pageBean
	 * @return
	 */
	public List<GoodsBean> searchGoodsDetailList(GoodsBean goodsBean,LocationBean locationBean,PageBean pageBean) {
		List<GoodsBean> goodsBeans = goodsDaoI.searchGoodsDetailList(goodsBean, pageBean);
		for (int i = 0; i < goodsBeans.size(); i++) {
//			LocationBean locationBean2=BaiduUtils.getLLByAddress(goodsBeans.get(i).getGoods_address());
//			String distance=DistanceUtils.Distan(locationBean,locationBean2);
//			goodsBeans.get(i).setDistance(NumberUtils.KeepDecimal(distance,2)+"");
			List<GoodsImgBean> goodsImgBeans = goodsDaoI
					.getGoodsImgs(new GoodsImgBean().setGoods_id(goodsBeans.get(i).getGoods_id() + ""));
			goodsBeans.get(i).setGoodsImgBeans(goodsImgBeans);
		}
		return goodsBeans;
	}

	/**
	 * 商品的其他参数选择
	 * 
	 * @param goodsBean
	 * @param pageBean
	 * @return
	 */
	public List<GoodsLabelBean> getGoodsClassLabels(GoodsLabelBean goodsLabelBean) {
		return goodsDaoI.getGoodsClassLabels(goodsLabelBean);
	}

	/**
	 * 修改商品浏览量
	 * 
	 * @param goodsBean
	 * @return
	 */
	public int updateGoodsDetailSeenum(GoodsBean goodsBean) {
		return goodsDaoI.updateGoodsDetailSeenum(goodsBean);
	}

	/**
	 * 获得商品详情
	 * 
	 * @param goodsBean
	 * @return
	 */
	public GoodsBean getOneGoodsDetail(GoodsBean goodsBean) {
		GoodsBean goodsBean1 = goodsDaoI.getOneGoodsDetail(goodsBean);
		if (goodsBean1 != null) {
			List<GoodsImgBean> goodsImgBeans = goodsDaoI
					.getGoodsImgs(new GoodsImgBean().setGoods_id(goodsBean1.getGoods_id() + ""));
			goodsBean1.setGoodsImgBeans(goodsImgBeans);// 商品图片

			MerchantsBean merchantsBean = merchantsServiceI.getOneMerchantsDetail(
					new MerchantsBean().setMerchants_id(Integer.valueOf(goodsBean1.getMerchants_id()))
							.setMember_id(goodsBean.getMember_id()));
			goodsBean1.setMerchantsBean(merchantsBean);
		}
		return goodsBean1;
	}

	/**
	 * 商品的规格数据
	 * 
	 * @param groupBuyGoodsBean
	 * @return
	 */
	public List<StandardBean> getGoodsStandards(StandardBean standardBean) {
		return goodsDaoI.getGoodsStandards(standardBean);
	}

	/**
	 * 商品的团购详情
	 * 
	 * @param groupBuyGoodsBean
	 * @return
	 */
	public List<GroupBuyGoodsBean> getGroupBuyGoodss(GroupBuyGoodsBean groupBuyGoodsBean) {
		return goodsDaoI.getGroupBuyGoodss(groupBuyGoodsBean);
	}

	/**
	 * 商品的图片列表
	 * 
	 * @param goodsImgBean
	 * @return
	 */

	public List<GoodsImgBean> getGoodsImgs(GoodsImgBean goodsImgBean) {
		return goodsDaoI.getGoodsImgs(goodsImgBean);
	}

	/**
	 * 获得所有商品参数
	 * 
	 * @return
	 */
	public List<GoodsParameterBean> getAllParameters(GoodsParameterBean goodsParameterBean) {
		List<GoodsParameterBean> goodsParameterBeans = goodsDaoI.getAllParametersByParent(goodsParameterBean);
		if (goodsParameterBeans != null) {
			for (int i = 0; i < goodsParameterBeans.size(); i++) {
				List<GoodsParameterBean> goodsParameterBeans2 = goodsDaoI.getAllParametersByParent(goodsParameterBean
						.setParameter_type("2").setParent_id(goodsParameterBeans.get(i).getParameter_id() + ""));
				goodsParameterBeans.get(i).setGoodsParameterBeans(goodsParameterBeans2);
			}
		}
		return goodsParameterBeans;
	}

	/**
	 * 获得所有服务参数
	 * 
	 * @return
	 */
	public List<GoodsServiceBean> getAllServices(GoodsServiceBean goodsServiceBean) {
		List<GoodsServiceBean> goodsServiceBeans = goodsDaoI.getAllServiceByParent(goodsServiceBean);
		if (goodsServiceBeans != null) {
			for (int i = 0; i < goodsServiceBeans.size(); i++) {
				List<GoodsServiceBean> goodsServiceBeans2 = goodsDaoI.getAllServiceByParent(goodsServiceBean
						.setService_type("2").setParent_id(goodsServiceBeans.get(i).getService_id() + ""));
				goodsServiceBeans.get(i).setGoodsServiceBeans(goodsServiceBeans2);
			}
		}
		return goodsServiceBeans;
	}

	/**
	 * 获得品牌套餐
	 * 
	 * @param brandPackageBean
	 * @return
	 */
	public List<BrandPackageBean> getBrandPackageByBrand(BrandPackageBean brandPackageBean) {
		List<BrandPackageBean> brandPackageBeans = goodsDaoI.getBrandPackageByBrand(brandPackageBean);
		for (int i = 0; i < brandPackageBeans.size(); i++) {
			List<BrandPackageImgBean> brandPackageImgBeans = getBrandPackageImgs(
					new BrandPackageImgBean().setPackage_id(brandPackageBeans.get(i).getPackage_id() + ""));
			brandPackageBeans.get(i).setBrandPackageImgBeans(brandPackageImgBeans);
		}
		return brandPackageBeans;
	}

	/**
	 * 获得单个品牌详情
	 * 
	 * @param brandPackageBean
	 * @return
	 */
	public BrandBean getBrandDetail(BrandBean brandBean) {
		BrandBean brandBean2 = goodsDaoI.getBrandDetail(brandBean);
		if (brandBean2 != null) {
			List<BrandPackageBean> brandPackageBeans = goodsDaoI
					.getBrandPackageByBrand(new BrandPackageBean().setBrand_id(brandBean.getBrand_id() + ""));
			for (int i = 0; i < brandPackageBeans.size(); i++) {
				List<BrandPackageImgBean> brandPackageImgBeans = getBrandPackageImgs(
						new BrandPackageImgBean().setPackage_id(brandPackageBeans.get(i).getPackage_id() + ""));
				brandPackageBeans.get(i).setBrandPackageImgBeans(brandPackageImgBeans);
			}
			brandBean2.setBrandPackageBeans(brandPackageBeans);
		}
		return brandBean2;
	}

	/**
	 * 获得品牌套餐图片
	 * 
	 * @param brandPackageBean
	 * @return
	 */
	public List<BrandPackageImgBean> getBrandPackageImgs(BrandPackageImgBean brandPackageImgBean) {
		return goodsDaoI.getBrandPackageImgs(brandPackageImgBean);
	}

	/**
	 * 获得品牌套餐详情
	 * 
	 * @param brandPackageBean
	 * @return
	 */
	public BrandPackageBean getBrandPackageGoodsDetail(BrandPackageBean brandPackageBean) {
		BrandPackageBean brandPackageBean2 = goodsDaoI.getBrandPackageGoodsDetail(brandPackageBean);
		if (brandPackageBean2 != null) {
			brandPackageBean2.setBrandPackageImgBeans(getBrandPackageImgs(
					new BrandPackageImgBean().setPackage_id(brandPackageBean.getPackage_id() + "")));
			brandPackageBean2.setBrandPackageGoodsBeans(getBrandPackageGoods(
					new BrandPackageGoodsBean().setPackage_id(brandPackageBean.getPackage_id() + "")));
		}
		return brandPackageBean2;
	}

	/**
	 * 获得品牌套餐商品
	 * 
	 * @param brandPackageBean
	 * @return
	 */
	public List<BrandPackageGoodsBean> getBrandPackageGoods(BrandPackageGoodsBean brandPackageGoodsBean) {
		List<BrandPackageGoodsBean> brandPackageGoodsBeans = goodsDaoI.getBrandPackageGoods(brandPackageGoodsBean);
		for (int i = 0; i < brandPackageGoodsBeans.size(); i++) {
			GoodsBean goodsBean = getOneGoodsDetail(
					new GoodsBean().setGoods_id(Integer.valueOf(brandPackageGoodsBeans.get(i).getGoods_id())));
			brandPackageGoodsBeans.get(i).setGoodsBean(goodsBean);
		}
		return brandPackageGoodsBeans;
	}

	// 顺手拍

	/**
	 * 所有商品
	 * 
	 * @return
	 */
	public List<GoodsBean> getAllGoods(GoodsBean goodsBean, PageBean pageBean) {
		List<GoodsBean> goodsBeans = goodsDaoI.getAllGoods(goodsBean, pageBean);
		for (int i = 0; i < goodsBeans.size(); i++) {
			List<GoodsImgBean> goodsImgBeans = goodsDaoI
					.getGoodsImgs(new GoodsImgBean().setGoods_id(goodsBeans.get(i).getGoods_id() + ""));
			goodsBeans.get(i).setGoodsImgBeans(goodsImgBeans);
		}
		return goodsBeans;
	}

	/**
	 * 顺手拍七个标签 分类
	 * 
	 * @param goodsBean
	 * @return
	 */
	public List<SSPClassBean> getSSPGoodsClass(SSPClassBean sspClassBean) {
		return goodsDaoI.getSSPGoodsClass(sspClassBean);
	}

	/**
	 * 促销标签
	 * 
	 * @return
	 */
	public List<GoodsBean> getPromotionGoods(GoodsBean goodsBean, PageBean pageBean) {
		List<GoodsBean> goodsBeans = goodsDaoI.getPromotionGoods(goodsBean, pageBean);
		for (int i = 0; i < goodsBeans.size(); i++) {
			List<GoodsImgBean> goodsImgBeans = goodsDaoI
					.getGoodsImgs(new GoodsImgBean().setGoods_id(goodsBeans.get(i).getGoods_id() + ""));
			goodsBeans.get(i).setGoodsImgBeans(goodsImgBeans);
		}
		return goodsBeans;
	}

	/**
	 * 礼物标签
	 * 
	 * @return
	 */
	public List<GoodsBean> getGiftGoods(GoodsBean goodsBean, PageBean pageBean) {
		List<GoodsBean> goodsBeans = goodsDaoI.getGiftGoods(goodsBean, pageBean);
		for (int i = 0; i < goodsBeans.size(); i++) {
			List<GoodsImgBean> goodsImgBeans = goodsDaoI
					.getGoodsImgs(new GoodsImgBean().setGoods_id(goodsBeans.get(i).getGoods_id() + ""));
			goodsBeans.get(i).setGoodsImgBeans(goodsImgBeans);
		}
		return goodsBeans;
	}

	/**
	 * 生鲜标签
	 * 
	 * @return
	 */
	public List<GoodsBean> getFreshGoods(GoodsBean goodsBean, PageBean pageBean) {
		List<GoodsBean> goodsBeans = goodsDaoI.getFreshGoods(goodsBean, pageBean);
		for (int i = 0; i < goodsBeans.size(); i++) {
			List<GoodsImgBean> goodsImgBeans = goodsDaoI
					.getGoodsImgs(new GoodsImgBean().setGoods_id(goodsBeans.get(i).getGoods_id() + ""));
			goodsBeans.get(i).setGoodsImgBeans(goodsImgBeans);
		}
		return goodsBeans;
	}

	/**
	 * 母婴标签
	 * 
	 * @return
	 */
	public List<GoodsBean> getBabyGoods(GoodsBean goodsBean, PageBean pageBean) {
		List<GoodsBean> goodsBeans = goodsDaoI.getBabyGoods(goodsBean, pageBean);
		for (int i = 0; i < goodsBeans.size(); i++) {
			List<GoodsImgBean> goodsImgBeans = goodsDaoI
					.getGoodsImgs(new GoodsImgBean().setGoods_id(goodsBeans.get(i).getGoods_id() + ""));
			goodsBeans.get(i).setGoodsImgBeans(goodsImgBeans);
		}
		return goodsBeans;
	}

	/**
	 * 女士标签
	 * 
	 * @return
	 */
	public List<GoodsBean> getLadyGoods(GoodsBean goodsBean, PageBean pageBean) {
		List<GoodsBean> goodsBeans = goodsDaoI.getLadyGoods(goodsBean, pageBean);
		for (int i = 0; i < goodsBeans.size(); i++) {
			List<GoodsImgBean> goodsImgBeans = goodsDaoI
					.getGoodsImgs(new GoodsImgBean().setGoods_id(goodsBeans.get(i).getGoods_id() + ""));
			goodsBeans.get(i).setGoodsImgBeans(goodsImgBeans);
		}
		return goodsBeans;
	}

	/**
	 * 特色标签
	 * 
	 * @return
	 */
	public List<GoodsBean> getFeatureGoods(GoodsBean goodsBean, PageBean pageBean) {
		List<GoodsBean> goodsBeans = goodsDaoI.getFeatureGoods(goodsBean, pageBean);
		for (int i = 0; i < goodsBeans.size(); i++) {
			List<GoodsImgBean> goodsImgBeans = goodsDaoI
					.getGoodsImgs(new GoodsImgBean().setGoods_id(goodsBeans.get(i).getGoods_id() + ""));
			goodsBeans.get(i).setGoodsImgBeans(goodsImgBeans);
		}
		return goodsBeans;
	}

	/**
	 * 进口商品
	 * 
	 * @return
	 */
	public List<GoodsBean> getImportGoods(GoodsBean goodsBean, PageBean pageBean) {
		List<GoodsBean> goodsBeans = goodsDaoI.getImportGoods(goodsBean, pageBean);
		for (int i = 0; i < goodsBeans.size(); i++) {
			List<GoodsImgBean> goodsImgBeans = goodsDaoI
					.getGoodsImgs(new GoodsImgBean().setGoods_id(goodsBeans.get(i).getGoods_id() + ""));
			goodsBeans.get(i).setGoodsImgBeans(goodsImgBeans);
		}
		return goodsBeans;
	}

	// 家纺
	/**
	 * 首页热门商品
	 * 
	 * @param goodsBean
	 * @param pageBean
	 * @return
	 */
	public List<GoodsBean> getHomeHotGoods(GoodsBean goodsBean, PageBean pageBean) {
		List<GoodsBean> goodsBeans = goodsDaoI.getHomeHotGoods(goodsBean, pageBean);
		for (int i = 0; i < goodsBeans.size(); i++) {
			List<GoodsImgBean> goodsImgBeans = goodsDaoI
					.getGoodsImgs(new GoodsImgBean().setGoods_id(goodsBeans.get(i).getGoods_id() + ""));
			goodsBeans.get(i).setGoodsImgBeans(goodsImgBeans);
		}
		return goodsBeans;
	}

	/**
	 * 首页最新商品
	 * 
	 * @param goodsBean
	 * @param pageBean
	 * @return
	 */
	public List<GoodsBean> getNewHotGoods(GoodsBean goodsBean, PageBean pageBean) {
		List<GoodsBean> goodsBeans = goodsDaoI.getNewHotGoods(goodsBean, pageBean);
		for (int i = 0; i < goodsBeans.size(); i++) {
			List<GoodsImgBean> goodsImgBeans = goodsDaoI
					.getGoodsImgs(new GoodsImgBean().setGoods_id(goodsBeans.get(i).getGoods_id() + ""));
			goodsBeans.get(i).setGoodsImgBeans(goodsImgBeans);
		}
		return goodsBeans;
	}

	/**
	 * 首页畅销商品(后台设置的)
	 * 
	 * @param goodsBean
	 * @param pageBean
	 * @return
	 */
	public List<GoodsBean> getHomeSellingGoods(GoodsBean goodsBean, PageBean pageBean) {
		List<GoodsBean> goodsBeans = goodsDaoI.getHomeSellingGoods(goodsBean, pageBean);
		for (int i = 0; i < goodsBeans.size(); i++) {
			List<GoodsImgBean> goodsImgBeans = goodsDaoI
					.getGoodsImgs(new GoodsImgBean().setGoods_id(goodsBeans.get(i).getGoods_id() + ""));
			goodsBeans.get(i).setGoodsImgBeans(goodsImgBeans);
		}
		return goodsBeans;
	}

	/**
	 * 分类热门商品
	 * 
	 * @param goodsBean
	 * @param pageBean
	 * @return
	 */
	public List<GoodsBean> getClassHotGoods(GoodsBean goodsBean, PageBean pageBean) {
		List<GoodsBean> goodsBeans = goodsDaoI.getClassHotGoods(goodsBean, pageBean);
		for (int i = 0; i < goodsBeans.size(); i++) {
			List<GoodsImgBean> goodsImgBeans = goodsDaoI
					.getGoodsImgs(new GoodsImgBean().setGoods_id(goodsBeans.get(i).getGoods_id() + ""));
			goodsBeans.get(i).setGoodsImgBeans(goodsImgBeans);
		}
		return goodsBeans;
	}

	/**
	 * 分类畅销商品(后台设置的)
	 * 
	 * @param goodsBean
	 * @param pageBean
	 * @return
	 */
	public List<GoodsBean> getClassSellingGoods(GoodsBean goodsBean, PageBean pageBean) {
		List<GoodsBean> goodsBeans = goodsDaoI.getClassSellingGoods(goodsBean, pageBean);
		for (int i = 0; i < goodsBeans.size(); i++) {
			List<GoodsImgBean> goodsImgBeans = goodsDaoI
					.getGoodsImgs(new GoodsImgBean().setGoods_id(goodsBeans.get(i).getGoods_id() + ""));
			goodsBeans.get(i).setGoodsImgBeans(goodsImgBeans);
		}
		return goodsBeans;
	}

	/**
	 * 分类畅销商品(后台设置的)
	 * 
	 * @param goodsBean
	 * @param pageBean
	 * @return
	 */
	public List<GoodsBean> getClassPriceSortGoods(GoodsBean goodsBean, PageBean pageBean) {
		List<GoodsBean> goodsBeans = goodsDaoI.getClassPriceSortGoods(goodsBean, pageBean);
		for (int i = 0; i < goodsBeans.size(); i++) {
			List<GoodsImgBean> goodsImgBeans = goodsDaoI
					.getGoodsImgs(new GoodsImgBean().setGoods_id(goodsBeans.get(i).getGoods_id() + ""));
			goodsBeans.get(i).setGoodsImgBeans(goodsImgBeans);
		}
		return goodsBeans;
	}
	
	//欧联
	/**
	 * 查询商品数据类别
	 */
	public List<GoodsCategoriesBean> getGoodsCategories()
	{
		return goodsDaoI.getGoodsCategories();
	}
	
	/**
	 * 查询单个品牌信息
	 */
	public GoodsBean getOneBrandDateil(GoodsBean goodsBean)
	{
		return goodsDaoI.getOneBrandDateil(goodsBean);
	}
	
	/**
	 * 查询该商品是否可供应（平台是否有该商品）
	 */
	public GoodsBean getGoodsIsSupply(GoodsBean goodsBean)
	{
		return goodsDaoI.getGoodsIsSupply(goodsBean);
	}
	
	/**
	 * 查询该商品客户是否已经供应
	 */
	public GoodsBean getGoodsMerchantsIsSupply(GoodsBean goodsBean)
	{
		return goodsDaoI.getGoodsMerchantsIsSupply(goodsBean);
	}
	
	/**
	 * 添加商品供应
	 */
	public int addGoodsSupply(GoodsBean goodsBean)
	{
		return goodsDaoI.addGoodsSupply(goodsBean);
	}
	
	/**
	 * 添加商品供应
	 */
	public int addGoodsSupplys(GoodsBean goodsBean)
	{
		return goodsDaoI.addGoodsSupplys(goodsBean);
	}
	
	/**
	 * 查询商品供应总数据（包含供应商等数据）
	 */
	public GoodsBean getGoodsData(GoodsBean goodsBean)
	{
		GoodsBean goodsBean1 = goodsDaoI.getGoodsData(goodsBean);
		if(goodsBean1==null)
		{
			goodsBean1 = goodsDaoI.getGoodsByAlias(goodsBean);
			if(goodsBean1==null)
			{
				return goodsBean1;
			}
		}		
		//获得型号基本信息（单价区间，最小起订量等）
		goodsBean1 = utilService.GoodsBasicInfo(goodsBean1);
//		//获得型号基本信息（单价区间，最小起订量等）
//		GoodsBean goodsBean2 = goodsDaoI.getGoodsBasicDetail(new GoodsBean()
//		                      .setGoods_name(goodsBean1.getGoods_name()));
//		goodsBean1.setPrice_interval(goodsBean2.getPrice_interval());
//		goodsBean1.setBuy_num_interval(goodsBean2.getBuy_num_interval());
//		goodsBean1.setMin_buy_num(goodsBean2.getMin_buy_num());
//		goodsBean1.setTotalStock(goodsBean2.getTotalStock());
		
//		//获得型号总供应数 
//		String totalStock = goodsDaoI.getTotalStock(new GoodsBean()
//		                    .setGoods_name(goodsBean1.getGoods_name()));
//		goodsBean1.setTotalStock(totalStock);
		
//		List<GoodsBean> goodsBeans = goodsDaoI.getSupplyGoodsBeans(goodsBean);
//		if(goodsBeans.size()>0)
//		{
//		    for (int i = 0; i < goodsBeans.size(); i++) {
//				MemberBean memberBean = memberService.getMemberByMerchants(new MemberBean()
//				                       .setMerchants_id(goodsBeans.get(i).getMerchants_id()));
//				
//				goodsBeans.get(i).setMemberBean(memberBean);
//				
//				List<GoodsSupplyDataBean> supplyDataBeans = supplyService.getGoodsSupplyData(new GoodsSupplyDataBean()
//				                                            .setGoods_id(goodsBeans.get(i).getGoods_id()+""));
//				
//				goodsBeans.get(i).setSupplyDataBeans(supplyDataBeans);
//				
//				List<CustomerServiceBean> customerBeans = memberService.getServiceQQByMerchantsId(new CustomerServiceBean()
//                .setMerchants_id(goodsBeans.get(i).getMerchants_id()));
//
//                goodsBeans.get(i).setCustomerBeans(customerBeans);
//			}
//		    goodsBean1.setGoodsBeans(goodsBeans);
//		}
		return goodsBean1;
	}
	
	/**
	 * 查询商品供应总数据
	 */
	public GoodsBean getGoodsDataNo(GoodsBean goodsBean)
	{
		GoodsBean goodsBean1 = goodsDaoI.getGoodsData(goodsBean);
		if(goodsBean1!=null)
		{
			//获得型号基本信息（单价区间，最小起订量等）
			goodsBean1 = utilService.GoodsBasicInfo(goodsBean1);
//			//获得型号基本信息（单价区间，最小起订量等）
//			GoodsBean goodsBean2 = goodsDaoI.getGoodsBasicDetail(new GoodsBean()
//			                      .setGoods_name(goodsBean1.getGoods_name()));
//			goodsBean1.setPrice_interval(goodsBean2.getPrice_interval());
//			goodsBean1.setBuy_num_interval(goodsBean2.getBuy_num_interval());
//			goodsBean1.setMin_buy_num(goodsBean2.getMin_buy_num());
//			goodsBean1.setTotalStock(goodsBean2.getTotalStock());
			
//			//获得型号总供应数 
//			String totalStock = goodsDaoI.getTotalStock(new GoodsBean()
//			                    .setGoods_name(goodsBean1.getGoods_name()));
//			goodsBean1.setTotalStock(totalStock);
		}
		return goodsBean1;
	}
	
	/**
	 * 查询除平台外该商品数据 
	 */
	public List<GoodsBean> getSupplyGoodsBeans(GoodsBean goodsBean)
	{
		return goodsDaoI.getSupplyGoodsBeans(goodsBean);
	}
	
	/**
	 * 查询用户商品列表
	 */
	public Map getMemberGoodsList(GoodsBean goodsBean)
	{
		Map map=new HashMap<String, Object>();
		int count=goodsDaoI.getMemberGoodsListCount(goodsBean);
		map.put("total",count);
		List<GoodsBean> goodsBeans = goodsDaoI.getMemberGoodsList(goodsBean);
		map.put("goodsBeans",goodsBeans);
		
	if(goodsBeans.size()>0)
		{
			for (int i = 0; i < goodsBeans.size(); i++) {		
//				System.out.println("goodsBeans==="+goodsBeans.get(i).getMin_pack_num());
//				GoodsBean goodsBean2 = goodsDaoI.getGoodsData(new GoodsBean()
//				                    .setGoods_name(goodsBeans.get(i).getGoods_name()));
//				if(goodsBean2!=null)
//				{
//					goodsBeans.get(i).setGoods_name(goodsBean2.getGoods_name());
//				}
//				
//				List<GoodsSupplyDataBean> supplyDataBeans = supplyService.getGoodsSupplyData(new GoodsSupplyDataBean()
//                                         .setGoods_id(goodsBeans.get(i).getGoods_id()+""));
				
			GoodsBean goodsBean1 = goodsDaoI.getGoodsIsSupply(new GoodsBean().setGoods_name(goodsBeans.get(i).getGoods_name()));
				if(goodsBean1!=null)
				{
					goodsBeans.get(i).setGoods_desc(goodsBean1.getGoods_desc());
					if(goodsBeans.get(i).getMin_pack_num().equals("0")){
						goodsBeans.get(i).setMin_pack_num(goodsBean1.getMin_pack_num());
						
						System.out.println("goodsBean1==="+goodsBean1.getMin_pack_num());
					}else{
						
						System.out.println("goodsBeans==="+goodsBeans.get(i).getMin_pack_num());
					}
				}
//			goodsBeans.get(i).setSupplyDataBeans(supplyDataBeans);
			}
		}
		return map;
	}
		
	/**
	 * 品牌商品列表（供应）
	 */
	public List<GoodsBean> getBrandGoodsList(PageBean pageBean,GoodsBean goodsBean)
	{
		List<GoodsBean> goodsBeans = getOneGoodsClassParentPage(goodsBean.setGoods_type("1")
				                    .setParent_id("-1"),pageBean);
//		if(goodsBeans.size()>0)
//		{
//			for (int i = 0; i < goodsBeans.size(); i++) {
//				
////				GoodsBean Brand = goodsDaoI.getOneBrandDateil(new GoodsBean().setGoods_id(goodsBeans.get(i).getGoods_id()));
//				
//				List<GoodsBean> goodsBean1 = goodsDaoI.getGoodsByBrandPage(new GoodsBean()
//				                          .setGoods_uuid(goodsBeans.get(i).getGoods_uuid()),new PageBean().setLimit(6));
////				if(goodsBean1!=null&&goodsBean1.size()>0)
////				{
////					for (int j = 0; j < goodsBean1.size(); j++) {
////						//获得型号基本信息（单价区间，最小起订量等）
////						GoodsBean goodsBean2 = goodsDaoI.getGoodsBasicDetail(new GoodsBean()
////						                      .setGoods_name(goodsBean1.get(j).getGoods_name()));
////						goodsBean1.get(j).setPrice_interval(goodsBean2.getPrice_interval());
////						goodsBean1.get(j).setBuy_num_interval(goodsBean2.getBuy_num_interval());
////						goodsBean1.get(j).setMin_buy_num(goodsBean2.getMin_buy_num());
////						goodsBean1.get(j).setTotalStock(goodsBean2.getTotalStock());
////						
//////						//获得型号总供应数 
//////						String totalStock = goodsDaoI.getTotalStock(new GoodsBean()
//////						                    .setGoods_name(goodsBean1.get(j).getGoods_name()));
//////						goodsBean1.get(j).setTotalStock(totalStock);
////						
////						goodsBean1.get(j).setBrand_name(goodsBeans.get(i).getGoods_name());
////						goodsBean1.get(j).setBrand_img(goodsBeans.get(i).getGoods_img());
////					}
////				}
//				goodsBeans.get(i).setGoodsBeans(goodsBean1);
//
//			}
//			return goodsBeans;
//		}else
//		{
//			return goodsBeans;
//		}
		
		return goodsBeans;
	}
	
	/**
	 * 根据品牌查询商品(分页)
	 */
	public List<GoodsBean> getGoodsByBrandPage(GoodsBean goodsBean,PageBean pageBean)
	{
		List<GoodsBean> beans = goodsDaoI.getGoodsByBrandPage(goodsBean, pageBean);
		if(beans.size() > 0)
		{
			for (int i = 0; i < beans.size(); i++) {
				
				//获得型号基本信息（单价区间，最小起订量等）S
				GoodsBean goodsBean2 = goodsDaoI.getGoodsBasicDetail(new GoodsBean()
				                      .setGoods_name(beans.get(i).getGoods_name()));
				beans.get(i).setPrice_interval(goodsBean2.getPrice_interval());
				beans.get(i).setBuy_num_interval(goodsBean2.getBuy_num_interval());
				beans.get(i).setMin_buy_num(goodsBean2.getMin_buy_num());
				beans.get(i).setTotalStock(goodsBean2.getTotalStock());
				
//				//获得型号总供应数 
//				String totalStock = goodsDaoI.getTotalStock(new GoodsBean()
//				                    .setGoods_name(beans.get(i).getGoods_name()));
//				beans.get(i).setTotalStock(totalStock);
			}
		}
		return beans;
	}
	
	/**
	 * 查询商品详情（欧联--商家端）
	 */
	public GoodsBean getGoodsDetail(GoodsBean goodsBean) {
		GoodsBean goodsBean1 = goodsDaoI.getOneGoodsDetail(goodsBean);
		if (goodsBean1 != null) {
			List<GoodsSupplyDataBean> supplyBeans = supplyService.getGoodsSupplyData(new GoodsSupplyDataBean()
			                                      .setGoods_id(goodsBean.getGoods_id()+""));
			goodsBean1.setSupplyDataBeans(supplyBeans);
			
			GoodsBean goodsBean2 = goodsDaoI.getGoodsIsSupply(new GoodsBean().setGoods_name(goodsBean1.getGoods_name()));
			if(goodsBean2!=null)
			{
				goodsBean1.setGoods_desc(goodsBean2.getGoods_desc());
				goodsBean1.setGoods_img(goodsBean2.getGoods_img());
				goodsBean1.setBrand_name(goodsBean2.getBrand_name());
				if(goodsBean1.getMin_pack_num().equals("0")){
					goodsBean1.setMin_pack_num(goodsBean2.getMin_pack_num());
				}
				else {
					goodsBean1.setMin_pack_num(goodsBean1.getMin_pack_num());
					}
				}
			}
			
		return goodsBean1;
	}
	
	/**
	 * 修改商品信息
	 * @throws Exception 
	 */
	public int updateGoodsDateil(GoodsBean goodsBean,List<GoodsSupplyDataBean> dataBeans) throws Exception
	{
		int num=goodsDaoI.updateGoodsDateil(goodsBean);
		if(num>0){
			supplyService.deleteSupplyData(new GoodsSupplyDataBean().setGoods_id(goodsBean.getGoods_id() + ""));
			if (dataBeans.size() > 0) {
				for (int i = 0; i < (dataBeans.size()>4?4: dataBeans.size()); i++) {
					GoodsSupplyDataBean dataBean = dataBeans.get(i);
					if(dataBean.getSupply_id()!=null&&dataBean.getSupply_id()!=0){
						dataBean.setGoods_unit_price(String.valueOf(NumberUtils.KeepDecimal(NumberUtils.Float(dataBean.getGoods_unit_price()), 4)));
						int num2 = supplyService.updateSupplyData(dataBean);
						if (num2 < 0) {
							throw new Exception("添加商品供应数据失败");
						}
					}else{
						dataBean.setGoods_unit_price(String.valueOf(NumberUtils.KeepDecimal(NumberUtils.Float(dataBean.getGoods_unit_price()), 4)));
						int num2 = supplyService.addSupplyData(dataBean.setGoods_id(goodsBean.getGoods_id() + "")
								.setIs_delete("0").setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"))
								.setGoods_name(goodsBean.getGoods_name()));
						if (num2 < 0) {
							throw new Exception("添加商品供应数据失败");
						}
					}
				}
			}
		}
		
		return num;
	}
	
	/**
	 * 根据型号查询有多少供应商供应
	 */
	public Map getMemberByGoodsName(GoodsBean goodsBean)
	{
		Map map =new HashMap<String, Object>();
		int total= goodsDaoI.getSupplyGoodsBeansCount(goodsBean);
		map.put("total", total);
		List<GoodsBean> goodsBeans = goodsDaoI.getSupplyGoodsBeans(goodsBean);
		if(goodsBeans.size()>0)
		{
		    for (int i = 0; i < goodsBeans.size(); i++) {
				MemberBean memberBean = memberService.getMemberByMerchants(new MemberBean()
				                       .setMerchants_id(goodsBeans.get(i).getMerchants_id()));
				
				goodsBeans.get(i).setMemberBean(memberBean);
				
//				List<GoodsSupplyDataBean> supplyDataBeans = supplyService.getGoodsSupplyData(new GoodsSupplyDataBean()
//				                                            .setGoods_id(goodsBeans.get(i).getGoods_id()+""));
//				
//				goodsBeans.get(i).setSupplyDataBeans(supplyDataBeans);
				
				GoodsBean goodsBean1 = goodsDaoI.getGoodsIsSupply(new GoodsBean().setGoods_name(goodsBeans.get(i).getGoods_name()));
					if(goodsBean1!=null)
					{
						goodsBeans.get(i).setGoods_desc(goodsBean1.getGoods_desc());
						if(goodsBeans.get(i).getMin_pack_num().equals("0")){
							goodsBeans.get(i).setMin_pack_num(goodsBean1.getMin_pack_num());
						}
					}
				List<CustomerServiceBean> customerBeans = memberService.getServiceQQByMerchantsId(new CustomerServiceBean()
                .setMerchants_id(goodsBeans.get(i).getMerchants_id()));

                goodsBeans.get(i).setCustomerBeans(customerBeans);
			}
		}
		map.put("goodsBeans", goodsBeans);

		return map;
	}
	
	/**
	 * 查询商品供应总数据（包含供应商等数据）
	 */
	public GoodsBean getMerchantsGoodsData(GoodsBean goodsBean)
	{
		GoodsBean goodsBean1 = goodsDaoI.getMerchantsGoodsData(goodsBean);
		if(goodsBean1!=null)
		{
			
			//获得型号基本信息（单价区间，最小起订量等）
			goodsBean1 = utilService.GoodsBasicInfo(goodsBean1);
//			//获得型号基本信息（单价区间，最小起订量等）
//			GoodsBean goodsBean3 = goodsDaoI.getGoodsBasicDetail(new GoodsBean()
//			                      .setGoods_name(goodsBean1.getGoods_name()));
//			goodsBean1.setPrice_interval(goodsBean3.getPrice_interval());
//			goodsBean1.setBuy_num_interval(goodsBean3.getBuy_num_interval());
//			goodsBean1.setMin_buy_num(goodsBean3.getMin_buy_num());
//			goodsBean1.setTotalStock(goodsBean3.getTotalStock());
			
//			//获得型号总供应数 
//			String totalStock = goodsDaoI.getTotalStock(new GoodsBean()
//			                    .setGoods_name(goodsBean1.getGoods_name()));
//			goodsBean1.setTotalStock(totalStock);
			
			//1-现货 2-期货 3-促销
			List<GoodsSupplyDataBean> supplyDataBeans = supplyService.getGoodsSupplyData(new GoodsSupplyDataBean()
			                          .setGoods_id(goodsBean1.getGoods_id()+""));
			
			List<GoodsSupplyDataBean> supplyDataBean1 = supplyService.getGoodsSupplyData(new GoodsSupplyDataBean()
            .setGoods_id(goodsBean1.getGoods_id()+"")
            .setData_categories("1"));
			
			List<GoodsSupplyDataBean> supplyDataBean2 = supplyService.getGoodsSupplyData(new GoodsSupplyDataBean()
            .setGoods_id(goodsBean1.getGoods_id()+"")
            .setData_categories("2"));
			
			List<GoodsSupplyDataBean> supplyDataBean3 = supplyService.getGoodsSupplyData(new GoodsSupplyDataBean()
            .setGoods_id(goodsBean1.getGoods_id()+"")
            .setData_categories("3"));
			
			goodsBean1.setSupplyDataBeans(supplyDataBeans);
			goodsBean1.setSupplyDataBean1(supplyDataBean1);
			goodsBean1.setSupplyDataBean2(supplyDataBean2);
			goodsBean1.setSupplyDataBean3(supplyDataBean3);
			
			GoodsBean goodsBean2 = goodsDaoI.getGoodsData(new GoodsBean().setGoods_name(goodsBean1.getGoods_name()));
			if(goodsBean2!=null)
			{
			    goodsBean1.setGoods_img(goodsBean2.getGoods_img());
			    goodsBean1.setGoods_address(goodsBean2.getGoods_address());
			    goodsBean1.setGoodsBean(goodsBean2);
			}
			
			MemberBean memberBean = memberService.getMemberByMerchants(new MemberBean()
                       .setMerchants_id(goodsBean1.getMerchants_id()));
			
			if(memberBean!=null)
			{
				goodsBean1.setMemberMerchantsBean(memberBean);
				goodsBean1.setMerchants_name(memberBean.getCompany_name());
			}
			List<CustomerServiceBean> customerBeans = memberService.getServiceQQByMerchantsId(new CustomerServiceBean()
            .setMerchants_id(goodsBean1.getMerchants_id()));

            goodsBean1.setCustomerBeans(customerBeans);
		}
		return goodsBean1;
	}
	
	/**
	 * 最新供应消息
	 */
	public List<GoodsBean> GetNewestSupplyInfo(GoodsBean goodsBean)
	{
		List<GoodsBean> goodsBeans = goodsDaoI.GetNewestSupplyInfo(goodsBean);
		if(goodsBeans.size() == 0)
		{
			List<GoodsBean> goodsBeans1 = goodsDaoI.GetNewestSupplyInfo(goodsBean.setIs_sql("1"));
			if(goodsBeans1.size()>0)
			{
				for (int i = 0; i < goodsBeans1.size(); i++) {
					//获得型号基本信息（单价区间，最小起订量等）
					GoodsBean goodsBean2 = goodsDaoI.getGoodsBasicDetail(new GoodsBean()
					                      .setGoods_name(goodsBeans.get(i).getGoods_name()));
					goodsBeans.get(i).setPrice_interval(goodsBean2.getPrice_interval());
					goodsBeans.get(i).setBuy_num_interval(goodsBean2.getBuy_num_interval());
					goodsBeans.get(i).setMin_buy_num(goodsBean2.getMin_buy_num());
					goodsBeans.get(i).setTotalStock(goodsBean2.getTotalStock());
					
//					//获得型号总供应数 
//					String totalStock = goodsDaoI.getTotalStock(new GoodsBean()
//					                    .setGoods_name(goodsBeans.get(i).getGoods_name()));
//					goodsBeans.get(i).setTotalStock(totalStock);
					
					//查询平台提供型号的资料
					GoodsBean goodsBean1 = goodsDaoI.getGoodsData(new GoodsBean()
					      .setGoods_name(goodsBeans1.get(i).getGoods_name()));
					if(goodsBean1!=null)
					{
						goodsBeans1.get(i).setGoods_img(goodsBean1.getGoods_img());	
					}
					
					//查询供应商名称
					MemberBean memberBean = memberService.getMemberByMerchants(new MemberBean()
                       .setMerchants_id(goodsBeans1.get(i).getMerchants_id()));
					
					goodsBeans1.get(i).setMemberBean(memberBean);
				}
			}
			goodsBeans.addAll(goodsBeans1);
		}else if(goodsBeans.size() < 16)
		{
			List<GoodsBean> goodsBeans1 = goodsDaoI.GetNewestSupplyInfo(goodsBean.setIs_sql("2"));
			if(goodsBeans1.size()>0)
			{
				for (int i = 0; i < goodsBeans1.size(); i++) {
					//获得型号基本信息（单价区间，最小起订量等）
					GoodsBean goodsBean2 = goodsDaoI.getGoodsBasicDetail(new GoodsBean()
					                      .setGoods_name(goodsBeans.get(i).getGoods_name()));
					goodsBeans.get(i).setPrice_interval(goodsBean2.getPrice_interval());
					goodsBeans.get(i).setBuy_num_interval(goodsBean2.getBuy_num_interval());
					goodsBeans.get(i).setMin_buy_num(goodsBean2.getMin_buy_num());
					goodsBeans.get(i).setTotalStock(goodsBean2.getTotalStock());
					
//					//获得型号总供应数 
//					String totalStock = goodsDaoI.getTotalStock(new GoodsBean()
//					                    .setGoods_name(goodsBeans.get(i).getGoods_name()));
//					goodsBeans.get(i).setTotalStock(totalStock);
					
					//查询平台提供型号的资料
					GoodsBean goodsBean1 = goodsDaoI.getGoodsData(new GoodsBean()
					      .setGoods_name(goodsBeans1.get(i).getGoods_name()));
					if(goodsBean1!=null)
					{
						goodsBeans1.get(i).setGoods_img(goodsBean1.getGoods_img());	
					}
					
					//查询供应商名称
					MemberBean memberBean = memberService.getMemberByMerchants(new MemberBean()
                       .setMerchants_id(goodsBeans1.get(i).getMerchants_id()));
					
					goodsBeans1.get(i).setMemberBean(memberBean);
				}
			}
			goodsBeans.addAll(goodsBeans1);
		}else if(goodsBeans.size() >= 16)
		{
			for (int i = 0; i < goodsBeans.size(); i++) {
				
				//获得型号基本信息（单价区间，最小起订量等）
				GoodsBean goodsBean2 = goodsDaoI.getGoodsBasicDetail(new GoodsBean()
				                      .setGoods_name(goodsBeans.get(i).getGoods_name()));
				goodsBeans.get(i).setPrice_interval(goodsBean2.getPrice_interval());
				goodsBeans.get(i).setBuy_num_interval(goodsBean2.getBuy_num_interval());
				goodsBeans.get(i).setMin_buy_num(goodsBean2.getMin_buy_num());
				goodsBeans.get(i).setTotalStock(goodsBean2.getTotalStock());
				
//				//获得型号总供应数 
//				String totalStock = goodsDaoI.getTotalStock(new GoodsBean()
//				                    .setGoods_name(goodsBeans.get(i).getGoods_name()));
//				goodsBeans.get(i).setTotalStock(totalStock);
				
				//查询平台提供型号的资料
				GoodsBean goodsBean1 = goodsDaoI.getGoodsData(new GoodsBean()
				      .setGoods_name(goodsBeans.get(i).getGoods_name()));
				if(goodsBean1!=null)
				{
					goodsBeans.get(i).setGoods_img(goodsBean1.getGoods_img());	
				}
				
				//查询供应商名称
				MemberBean memberBean = memberService.getMemberByMerchants(new MemberBean()
                   .setMerchants_id(goodsBeans.get(i).getMerchants_id()));
				
				goodsBeans.get(i).setMemberBean(memberBean);
			}
		}
		return goodsBeans;
	}
	
	/**
	 * 热销商品列表
	 */
	public List<GoodsBean> getSellHotGoods(GoodsBean goodsBean)
	{
		List<GoodsBean> goodsBeans = goodsDaoI.getSellHotGoods(goodsBean);
		if(goodsBeans.size() > 0)
		{
			for (int i = 0; i < goodsBeans.size(); i++) {
				//获得型号基本信息（单价区间，最小起订量等）
				GoodsBean goodsBean2 = goodsDaoI.getGoodsBasicDetail(new GoodsBean()
				                      .setGoods_name(goodsBeans.get(i).getGoods_name()));
				goodsBeans.get(i).setPrice_interval(goodsBean2.getPrice_interval());
				goodsBeans.get(i).setBuy_num_interval(goodsBean2.getBuy_num_interval());
				goodsBeans.get(i).setMin_buy_num(goodsBean2.getMin_buy_num());
				goodsBeans.get(i).setTotalStock(goodsBean2.getTotalStock());
				
//				//获得型号总供应数 
//				String totalStock = goodsDaoI.getTotalStock(new GoodsBean()
//				                    .setGoods_name(goodsBeans.get(i).getGoods_name()));
//				goodsBeans.get(i).setTotalStock(totalStock);
			}
		}
		return goodsBeans;
	}
	
	/**
	 * 批量上下架 
	 */
	public int batchUpAndDown(GoodsBean goodsBean)
	{
		return goodsDaoI.batchUpAndDown(goodsBean);
	}
	
	/**
	 * 删除型号
	 */
	public int deleteGoodsName(GoodsBean goodsBean)
	{
		return goodsDaoI.deleteGoodsName(goodsBean);
	}
	
	/**
	 * 批量删除型号
	 */
	public int batchDeleteGoodsName(GoodsBean goodsBean)
	{
		return goodsDaoI.batchDeleteGoodsName(goodsBean);
	}
	
	/**
	 * 获得型号基本信息（单价区间，最小起订量等）
	 */
	public GoodsBean getGoodsBasicDetail(GoodsBean goodsBean)
	{
		return goodsDaoI.getGoodsBasicDetail(goodsBean);
	}
	
	/**
	 * 获得型号总供应数
	 */
	public String getTotalStock(GoodsBean goodsBean)
	{
		return goodsDaoI.getTotalStock(goodsBean);
	}
	
	/**
	 * 精确筛选
	 */
	public GoodsBean getAccurateScreening(GoodsBean goodsBean)
	{
		return goodsBean;
	}
	
	/**
	 * 根据型号别名查询该型号
	 */
	public GoodsBean getGoodsByAlias(GoodsBean goodsBean)
	{
		return goodsDaoI.getGoodsByAlias(goodsBean);
	}
	
	/**
	 * 全部删除
	 */
	public int allGoodsDelete(GoodsBean goodsBean)
	{
		return goodsDaoI.allGoodsDelete(goodsBean);
	}
	
	/**
	 * 全部上架/下架 
	 */
	public int allGoodsStateUpdate(GoodsBean goodsBean)
	{
		return goodsDaoI.allGoodsStateUpdate(goodsBean);
	}
	/**
	 * 
	 * @param goodsSupplyDataBean
	 * @return
	 */
	public int allGoodsSupplyStateUpdate(GoodsSupplyDataBean goodsSupplyDataBean)
	  {
	    return goodsDaoI.allGoodsSupplyStateUpdate(goodsSupplyDataBean);
	  }
	
	/**
	 * 供应列表筛选
	 */
	public Map SupplyScreeningList(GoodsBean goodsBean,PageBean pageBean)
	{
		Map map = new HashMap();
//		if(goodsBean.getMerchants_id()!=null &&
//			!goodsBean.getMerchants_id().equals(""))
//		{
			//供应商信息
			MemberBean memberBean = memberService.getMemberByMerchants(new MemberBean()
			                    .setMerchants_id(goodsBean.getMerchants_id()));
			
			map.put("memberBean", memberBean);
			
			//供应商型号数据
			List<GoodsBean> goodsBeans = goodsDaoI.SupplyScreeningList(goodsBean, pageBean);
			if(goodsBeans.size() > 0)
			{
				for (int i = 0; i < goodsBeans.size(); i++) {
					
					List<GoodsSupplyDataBean> supplyDataBeans = supplyService.getGoodsSupplyData(new GoodsSupplyDataBean()
	                            .setGoods_id(goodsBeans.get(i).getGoods_id()+""));

	                goodsBeans.get(i).setSupplyDataBeans(supplyDataBeans);
	                
	                List<CustomerServiceBean> customerBeans = memberService.getServiceQQByMerchantsId(new CustomerServiceBean()
	                                        .setMerchants_id(goodsBeans.get(i).getMerchants_id()));
	                
	                goodsBeans.get(i).setCustomerBeans(customerBeans);
				} 
			}
			map.put("goodsBeans", goodsBeans);
			return map;
//		}else if(goodsBean.getGoods_name()!=null &&
//				!goodsBean.getGoods_name().equals(""))
//		{
//			List<GoodsBean> goodsBeans = new ArrayList<GoodsBean>();
//			//平台是否提供该型号
//			GoodsBean goodsBean1 =  goodsDaoI.getGoodsIsSupply(goodsBean);
//			if(goodsBean1!=null)
//			{
//				//获得型号基本信息（单价区间，最小起订量等）
//				GoodsBean goodsBean2 = goodsDaoI.getGoodsBasicDetail(new GoodsBean()
//				                      .setGoods_name(goodsBean1.getGoods_name()));
//				goodsBean1.setPrice_interval(goodsBean2.getPrice_interval());
//				goodsBean1.setBuy_num_interval(goodsBean2.getBuy_num_interval());
//				goodsBean1.setMin_buy_num(goodsBean2.getMin_buy_num());
//				goodsBean1.setTotalStock(goodsBean2.getTotalStock());
//				
//				//获得型号总供应数 
//				String totalStock = goodsDaoI.getTotalStock(new GoodsBean()
//				                    .setGoods_name(goodsBean1.getGoods_name()));
//				goodsBean1.setTotalStock(totalStock);
//				
//				MemberBean memberBean = memberService.getMemberByMerchants(new MemberBean()
//                .setMerchants_id(goodsBean1.getMerchants_id()));
//
//				goodsBean1.setMemberBean(memberBean);
//				
//				List<GoodsSupplyDataBean> supplyDataBeans = supplyService.getGoodsSupplyData(new GoodsSupplyDataBean()
//				                                     .setGoods_id(goodsBean1.getGoods_id()+""));
//				
//				goodsBean1.setSupplyDataBeans(supplyDataBeans);
//				
//				List<CustomerServiceBean> customerBeans = memberService.getServiceQQByMerchantsId(new CustomerServiceBean()
//				.setMerchants_id(goodsBean1.getMerchants_id()));
//				
//				goodsBean1.setCustomerBeans(customerBeans);
//				
//				goodsBeans.add(goodsBean1);
//				
//				map.put("goodsBeans", goodsBeans);
//				map.put("memberBean", goodsBeans);
//				return map;
//			}else
//			{
//				map.put("goodsBeans", "");
//				map.put("memberBean", "");
//				return map;
//			}
			
//		}else
//		{
//			List<GoodsBean> goodsBeans = goodsDaoI.getBrandMemberGoodsName(goodsBean, pageBean);
//			if(goodsBeans.size() > 0)
//			{
//				for (int i = 0; i < goodsBeans.size(); i++) {
//					
//					List<GoodsSupplyDataBean> supplyDataBeans = supplyService.getGoodsSupplyData(new GoodsSupplyDataBean()
//	                            .setGoods_id(goodsBeans.get(i).getGoods_id()+""));
//
//	                goodsBeans.get(i).setSupplyDataBeans(supplyDataBeans);
//	                
//	                List<CustomerServiceBean> customerBeans = memberService.getServiceQQByMerchantsId(new CustomerServiceBean()
//                    .setMerchants_id(goodsBeans.get(i).getMerchants_id()));
//
//                    goodsBeans.get(i).setCustomerBeans(customerBeans);
//				} 
//			}
//			map.put("goodsBeans", goodsBeans);
//			map.put("memberBean", "");
//			return map;
//		}
		
	}
	
	/**
	 * 商家导出型号列表
	 */
	public List<Object> exportMemberGoodsExcel(GoodsBean goodsBean)
	{
		return goodsDaoI.exportMemberGoodsExcel(goodsBean);
	}
	
	/**
	 * 平台商品搜索（首页）
	 */
	public List<GoodsBean> getGoodsDataList(GoodsBean goodsBean,PageBean pageBean)
	{
		List<GoodsBean> goodsBeans = new ArrayList<GoodsBean>();
		goodsBeans = goodsDaoI.getGoodsDataList(goodsBean, pageBean);
		if(goodsBeans.size() > 0)
		{
			for (int i = 0; i < goodsBeans.size(); i++) {
				List<GoodsBean> repalceBeans = goodsDaoI.replaceableGoods(new GoodsBean()
				                .setReplace_name(goodsBeans.get(i).getReplace_name()));
				
				goodsBeans.get(i).setReplaceBeans(repalceBeans);
			}
			return goodsBeans;
		}else
		{
			GoodsBean goodsBean1 = goodsDaoI.getGoodsByAlias(goodsBean);
			if(goodsBean1==null)
			{
				return goodsBeans;
			}
			
			//获得型号基本信息（单价区间，最小起订量等）
			goodsBean1 = utilService.GoodsBasicInfo(goodsBean1);
			
//			//获得型号基本信息（单价区间，最小起订量等）
//			GoodsBean goodsBean2 = goodsDaoI.getGoodsBasicDetail(new GoodsBean()
//			                      .setGoods_name(goodsBean1.getGoods_name()));
//			goodsBean1.setPrice_interval(goodsBean2.getPrice_interval());
//			goodsBean1.setBuy_num_interval(goodsBean2.getBuy_num_interval());
//			goodsBean1.setMin_buy_num(goodsBean2.getMin_buy_num());
//			
//			//获得型号总供应数 
//			String totalStock = goodsDaoI.getTotalStock(new GoodsBean()
//			                    .setGoods_name(goodsBean1.getGoods_name()));
//			goodsBean1.setTotalStock(totalStock);

			//查询可替换型号
			List<GoodsBean> repalceBeans = goodsDaoI.replaceableGoods(new GoodsBean()
            .setReplace_name(goodsBean1.getReplace_name()));

            goodsBean1.setReplaceBeans(repalceBeans);

			goodsBeans.add(goodsBean1);
			return goodsBeans;
		}
	}
	
	/**
	 * 查询可替换商品
	 */
	public List<GoodsBean> replaceableGoods(GoodsBean goodsBean)
	{
		List<GoodsBean> goodsBeans = goodsDaoI.replaceableGoods(goodsBean);
		return goodsBeans;
	}
	
	/**
	 * 相关供应消息
	 */
    public  List<GoodsBean> relatedSupplyMessage(GoodsBean goodsBean,PageBean pageBean)
    {
    	return goodsDaoI.relatedSupplyMessage(goodsBean, pageBean);
    }
}
