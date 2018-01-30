package tst.project.webservice.interfaces;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tst.project.bean.ExcelBean;
import tst.project.bean.LogRecordBean;
import tst.project.bean.activity.ActivityBean;
import tst.project.bean.address.LocationBean;
import tst.project.bean.goods.BrandBean;
import tst.project.bean.goods.BrandPackageBean;
import tst.project.bean.goods.BrandPackageGoodsBean;
import tst.project.bean.goods.FilterBean;
import tst.project.bean.goods.GoodsBean;
import tst.project.bean.goods.GoodsFindBuyBean;
import tst.project.bean.goods.GoodsLabelBean;
import tst.project.bean.goods.GoodsSupplyDataBean;
import tst.project.bean.goods.StoreHouseBean;
import tst.project.bean.member.MemberBean;
import tst.project.bean.member.MemberLevelBean;
import tst.project.bean.others.SearchBean;
import tst.project.page.PageBean;
import tst.project.service.interfaces.GoodsServiceI;
import tst.project.service.interfaces.GoodsSupplyDataService;
import tst.project.service.interfaces.LogRecordService;
import tst.project.service.interfaces.MemberService;
import tst.project.service.interfaces.OthersService;
import tst.project.utils.CreateRandom;
import tst.project.utils.ExcelUtils;
import tst.project.utils.NumberUtils;
import tst.project.utils.OulianUtils;
import tst.project.utils.TimeUtils;
import tst.project.webservice.controller.BaseController;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

@Controller
@RequestMapping("/goodsInterfaces.api")
public class GoodsInterfaces extends BaseController {
	@Resource
	GoodsServiceI goodsServiceI;

	@Resource
	MemberService memberService;

	@Resource
	OthersService othersService;

	@Resource
	GoodsSupplyDataService supplyService;

	@Resource
	LogRecordService recordService;
	
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getGoodsFindBuyCount", method = RequestMethod.POST)
	public void getGoodsFindBuyCount(MemberBean memberBean,GoodsFindBuyBean goodsBuyBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		
		WriteObject(response, goodsServiceI.getGoodsFindBuyCount(goodsBuyBean));
	}
	
	
	/**
	 * 首页热门求购
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getHotGoodsFindBuy", method = RequestMethod.POST)
	public void getHotGoodsFindBuy(GoodsFindBuyBean goodsBuyBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		WriteObject(response, goodsServiceI.getHotGoodsFindBuy(goodsBuyBean));
	}
	
	/**
	 * 首页热门供应
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getHotGoods", method = RequestMethod.POST)
	public void getHotGoods(GoodsBean goodsBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		WriteObject(response, goodsServiceI.getHotGoods(goodsBean));
	}
	
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "ffff", method = RequestMethod.POST)
	public void fff(GoodsBean goodsBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		WriteOnlyMsg(response, request.getSession().getServletContext().getRealPath("/"));
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getMerchantsByBrand", method = RequestMethod.POST)
	public void getMerchantsByBrand(GoodsBean goodsBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		WriteObject(response, goodsServiceI.getMerchantsByBrand(goodsBean));
	}
	
	
	
	/**
	 * 品牌列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getRecommendGoodsBrand", method = RequestMethod.POST)
	public void getRecommendGoodsBrand(GoodsBean goodsBean, PageBean pageBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		WriteObject(response, goodsServiceI.getRecommendGoodsBrand(goodsBean));
	}
	
	
	/**
	 * 品牌详情
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getGoodsBrandDetail", method = RequestMethod.POST)
	public void getGoodsBrandDetail(GoodsBean goodsBean, PageBean pageBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WriteObject(response, goodsServiceI.getGoodsBrandDetail(goodsBean));
	}
	
	/**
	 * 品牌列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getGoodsBrands", method = RequestMethod.POST)
	public void getGoodsBrands(GoodsBean goodsBean, PageBean pageBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		WriteObject(response, goodsServiceI.getGoodsBrands(goodsBean, pageBean),pageBean.getTotal());
	}
	
	
	/**
	 * 获得热搜列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getHotSearchs", method = RequestMethod.POST)
	public void getHotSearchs(SearchBean searchBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		WriteObject(response, goodsServiceI.getHotSearchs(searchBean));
	}

	/**
	 * 首页设置(服装)
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getHomeActivitys", method = RequestMethod.POST)
	public void getHomeActivitys(tst.project.bean.home.ActivityBean activityBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		WriteObject(response, goodsServiceI.getHomeActivitys(activityBean));
	}

	/**
	 * 预售商品列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getPreSaleGoodss", method = RequestMethod.POST)
	public void getPreSaleGoodss(GoodsBean goodsBean, PageBean pageBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		WriteObject(response, goodsServiceI.getPreSaleGoodss(goodsBean, pageBean));
	}

	/**
	 * 畅销商品(真正的按销量排名)
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getSellingGoodsReal", method = RequestMethod.POST)
	public void getSellingGoodsReal(GoodsBean goodsBean, PageBean pageBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		WriteObject(response, goodsServiceI.getSellingGoodsReal(goodsBean.setGoods_type("1"), pageBean));
	}

	/**
	 * 根据父id获得所有商品分类
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getOneGoodsClassParentNo", method = RequestMethod.POST)
	public void getOneGoodsClassParentNo(GoodsBean goodsBean, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		WriteObject(response, goodsServiceI.getOneGoodsClassParentNo(goodsBean.setGoods_type("1")));
	}

	/**
	 * 根据父id获得所有商品分类 根据level来判断取几级分类
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getGoodsClassLevel", method = RequestMethod.POST)
	public void getGoodsClassLevel(GoodsBean goodsBean, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String level = request.getParameter("level");
		WriteObject(response, goodsServiceI.getGoodsClassLevel(goodsBean.setGoods_type("1"),
				level == null ? 1 : Integer.valueOf(level)));
	}

	/**
	 * 根据父id获得所有商品分类 根据level来判断取几级分类
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getGoodsClassLevelV2", method = RequestMethod.POST)
	public void getGoodsClassLevelV2(GoodsBean goodsBean, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String level = request.getParameter("level");
		WriteObject(response, goodsServiceI.getGoodsClassLevelV2(goodsBean.setGoods_type("1"),
				level == null ? 1 : Integer.valueOf(level)));
	}

	/**
	 * 大众交易区所有商品分类
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getGoodsClassOrder", method = RequestMethod.POST)
	public void getGoodsClassOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<GoodsBean> goodsBeans = goodsServiceI
				.getGoodsClassOrder(new GoodsBean().setParent_id("-1").setGoods_type("1"));
		WriteObject(response, goodsBeans);
	}

	/**
	 * 获得该分类下的活动列表
	 * 
	 * @param goodsBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getFilterByClass", method = RequestMethod.POST)
	public void getFilterByClass(FilterBean filterBean, GoodsBean goodsBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<FilterBean> filterBeans = new ArrayList<FilterBean>();

		List<StoreHouseBean> storeHouseBeans = goodsServiceI.getAllStoreHouseByClass(goodsBean);
		if (storeHouseBeans != null && storeHouseBeans.size() > 0) {
			FilterBean filterBean2 = new FilterBean();
			filterBean2.setName("发货地");
			filterBean2.setType("storehouse");
			filterBean2.setStoreHouseBeans(storeHouseBeans);
			filterBeans.add(filterBean2);
		}

		List<GoodsLabelBean> goodsLabelBeans = goodsServiceI.getAllGoodsLabelByClass(goodsBean);
		if (goodsLabelBeans != null && goodsLabelBeans.size() > 0) {
			FilterBean filterBean2 = new FilterBean();
			filterBean2.setName("服务");
			filterBean2.setType("service");
			filterBean2.setGoodsLabelBeans(goodsLabelBeans);
			filterBeans.add(filterBean2);
		}

		List<BrandBean> brandBeans = goodsServiceI.getAllBrandByClass(goodsBean);
		if (brandBeans != null && brandBeans.size() > 0) {
			FilterBean filterBean2 = new FilterBean();
			filterBean2.setName("品牌");
			filterBean2.setType("brand");
			filterBean2.setBrandBeans(brandBeans);
			filterBeans.add(filterBean2);
		}

		List<ActivityBean> activityBeans = goodsServiceI.getActivityByClass(goodsBean);
		if (activityBeans != null && activityBeans.size() > 0) {
			FilterBean filterBean2 = new FilterBean();
			filterBean2.setName("活动");
			filterBean2.setType("activity");
			filterBean2.setActivityBeans(activityBeans);
			filterBeans.add(filterBean2);
		}

		WriteObject(response, filterBeans);
	}

	/**
	 * 获得该分类下的发货地列表
	 * 
	 * @param goodsBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getAllStoreHouseByClass", method = RequestMethod.POST)
	public void getAllStoreHouseByClass(GoodsBean goodsBean, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WriteObject(response, goodsServiceI.getAllStoreHouseByClass(goodsBean));
	}

	/**
	 * 获得该分类下的标签列表
	 * 
	 * @param goodsBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getAllGoodsLabelByClass", method = RequestMethod.POST)
	public void getAllGoodsLabelByClass(GoodsBean goodsBean, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WriteObject(response, goodsServiceI.getAllGoodsLabelByClass(goodsBean));
	}

	/**
	 * 获得该分类下的活动列表
	 * 
	 * @param goodsBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getActivityByClass", method = RequestMethod.POST)
	public void getActivityByClass(GoodsBean goodsBean, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WriteObject(response, goodsServiceI.getActivityByClass(goodsBean));
	}

	/**
	 * 获得该品牌下分类列表
	 * 
	 * @param goodsBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getClassByBrand", method = RequestMethod.POST)
	public void getClassByBrand(GoodsBean goodsBean, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String level = request.getParameter("level");
		WriteObject(response, goodsServiceI.getClassByBrand(goodsBean, level));
	}

	/**
	 * 获得该分类下的品牌列表
	 * 
	 * @param goodsBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getAllBrandByClass", method = RequestMethod.POST)
	public void getAllBrandByClass(GoodsBean goodsBean, PageBean pageBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WriteObject(response, goodsServiceI.getAllBrandByClass(goodsBean));
	}

	/**
	 * 根据各种条件 搜索商品列表
	 * 
	 * @param goodsBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "searchGoodsDetailList", method = RequestMethod.POST)
	public void searchGoodsDetailList(GoodsBean goodsBean, LocationBean locationBean, PageBean pageBean,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (goodsBean.getGoods_name() != null && !"".equals(goodsBean.getGoods_name())) {
			SearchBean searchBean = goodsServiceI
					.getHotSearch(new SearchBean().setSearch_name(goodsBean.getGoods_name()).setSearch_type("goods"));
			if (searchBean != null) {
				goodsServiceI.updateHotSearch(searchBean);
			} else {
				goodsServiceI.inserHotSearch(new SearchBean().setSearch_name(goodsBean.getGoods_name())
						.setSearch_type("goods").setIs_fixed("0").setSearch_count("1"));
			}
		}
		WriteObject(response, goodsServiceI.searchGoodsDetailList(goodsBean, locationBean, pageBean),
				pageBean.getTotal());
	}

	/**
	 * 获得商品详情
	 * 
	 * @param goodsBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getOneGoodsDetail", method = RequestMethod.POST)
	public void getOneGoodsDetail(GoodsBean goodsBean, MemberBean memberBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (memberBean.getBusiness_id() != null && !memberBean.getBusiness_id().equals("-1")) {
			MemberBean memberBean2 = memberService.getMemberByID(memberBean);
			if (memberBean2 != null) {
				if (memberBean2.getBusiness_id() == null || memberBean2.getBusiness_id().equals("-1")) {
					int num = memberService.updateMemberBusiness(memberBean);
					if (num <= 0) {
						WriteError(response, "查询失败");
						return;
					}
				}
			}
		}

		goodsServiceI.updateGoodsDetailSeenum(goodsBean);

		GoodsBean goodsBean1 = goodsServiceI.getOneGoodsDetail(goodsBean);
		String desc = readHtml(request, goodsBean1.getGoods_url());

		int start = desc.indexOf("<tst>");
		int end = desc.indexOf("</tst>");

		if (start > 0 && end > 0) {
			desc = desc.substring(start + 5, end);
		}
		WriteObject(response, goodsBean1.setGoods_url_content(desc));
	}

	/**
	 * 获得品牌套餐
	 * 
	 * @param goodsBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getBrandPackageByBrand", method = RequestMethod.POST)
	public void getBrandPackageByBrand(BrandPackageBean brandPackageBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WriteObject(response, goodsServiceI.getBrandPackageByBrand(brandPackageBean));
	}

	/**
	 * 获得品牌详情
	 * 
	 * @param goodsBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getBrandDetail", method = RequestMethod.POST)
	public void getBrandDetail(BrandBean brandBean, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BrandBean brandBean1 = goodsServiceI.getBrandDetail(brandBean);
		if (brandBean1 != null) {
			String desc = readHtml(request, brandBean1.getBrand_url());
			WriteObject(response, brandBean1.setBrand_url_content(desc));
		} else {
			WriteObject(response, brandBean1);
		}

	}
	

	/**
	 * 获得品牌套餐详情
	 * 
	 * @param goodsBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getBrandPackageGoodsDetail", method = RequestMethod.POST)
	public void getBrandPackageGoodsDetail(BrandPackageBean brandPackageBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WriteObject(response, goodsServiceI.getBrandPackageGoodsDetail(brandPackageBean));
	}

	/**
	 * 获得品牌套餐商品
	 * 
	 * @param goodsBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getBrandPackageGoods", method = RequestMethod.POST)
	public void getBrandPackageByBrand(BrandPackageGoodsBean brandPackageGoodsBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WriteObject(response, goodsServiceI.getBrandPackageGoods(brandPackageGoodsBean));
	}

	// 欧联.
	/**
	 * 查询商品数据类别
	 */
	@RequestMapping(params = "getGoodsCategories", method = RequestMethod.POST)
	public void getGoodsCategories(HttpServletRequest request, HttpServletResponse response) throws Exception {
		WriteObject(response, goodsServiceI.getGoodsCategories());
	}

	/**
	 * 发布供应（JSON）
	 */
	@RequestMapping(params = "releaseSupplyJson", method = RequestMethod.POST)
	public void releaseSupplyJson(HttpServletRequest request, HttpServletResponse response, MemberBean memberBean)
			throws Exception {
		
		MemberBean memberBean2=memberService.verificationToken(memberBean);
		if (memberBean2 == null) {
			WritePending(response, "token failed");
			return;
		}

		String json = request.getParameter("json");	
		List<GoodsBean> goodsBeans = new Gson().fromJson(json, new TypeToken<List<GoodsBean>>() {}.getType());
		
		MemberLevelBean memberLevelBean=memberService
				.getMemberLevelDetail(new MemberLevelBean().setLevel_id(NumberUtils.Integer(memberBean2.getMember_level())));
		int count=goodsServiceI.getMerchantsGoodsCount(new GoodsBean());
		if(goodsBeans.size()+count > memberLevelBean.getRelease_info_number()){
			WriteError(response, "已超出您发布条数限制!");
			return;
		}
		
		if (goodsBeans.size() > 0) {
			for (int i = 0; i < goodsBeans.size(); i++) {
				if (goodsBeans.get(i).getGoods_name() != null && !goodsBeans.get(i).getGoods_name().equals("")) {
					GoodsBean goodsBean = goodsBeans.get(i);
					List<GoodsSupplyDataBean> dataBeans = goodsBeans.get(i).getSupplyDataBeans();
					// 查询型号是否存在
					GoodsBean goodsBean1 = goodsServiceI.getGoodsIsSupply(goodsBean);
					if (goodsBean1 != null) {
						GoodsBean goodsBean2 = goodsServiceI
								.getGoodsMerchantsIsSupply(goodsBean.setMerchants_id(memberBean.getMerchants_id()));
						if (goodsBean2 != null) {
							for (int j = 0; j < dataBeans.size(); j++) {
								List<GoodsSupplyDataBean> supplyCount = supplyService.getGoodsSupplyData(
										new GoodsSupplyDataBean().setGoods_id(goodsBean2.getGoods_id() + ""));
								if (supplyCount.size() >= 4) {
									WriteError(response, "该型号供应数据大于4条");
									return;
								}

								GoodsSupplyDataBean dataBean = dataBeans.get(j);
								dataBean.setGoods_unit_price(String.valueOf(
										NumberUtils.KeepDecimal(NumberUtils.Float(dataBean.getGoods_unit_price()), 4)));

								// 判断供应数据是否重复，如果重复就跳过
								List<GoodsSupplyDataBean> dataBean1 = supplyService.isSupplyDataRepeat(
										dataBean.setGoods_id(goodsBean2.getGoods_id() + "").setIs_delete("0"));
								if (dataBean1 == null || dataBean1.size() == 0) {
									int num1 = supplyService.addSupplyData(
											dataBean.setGoods_id(goodsBean2.getGoods_id() + "").setIs_delete("0")
													.setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"))
													.setUpdate_time(TimeUtils.getTimeDayAfter("yyyy-MM-dd HH:mm:ss", new Date(), 30))
													.setGoods_name(goodsBean2.getGoods_name()));

									if (num1 < 0) {
										throw new Exception("添加商品供应数据失败");
									}
								}

							}
						} else {
							int num = goodsServiceI.addGoodsSupply(goodsBean.setIs_delete("0")
									.setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")).setGoods_type("2")
									.setGoods_state("1").setMerchants_id(memberBean.getMerchants_id()));
							if (num > 0) {
								for (int j = 0; j < dataBeans.size(); j++) {
									List<GoodsSupplyDataBean> supplyCount = supplyService.getGoodsSupplyData(
											new GoodsSupplyDataBean().setGoods_id(goodsBean.getGoods_id() + ""));
									if (supplyCount.size() >= 4) {
										WriteError(response, "该型号供应数据大于4条");
										return;
									}
									GoodsSupplyDataBean dataBean = dataBeans.get(j);
									dataBean.setGoods_unit_price(String.valueOf(NumberUtils
											.KeepDecimal(Double.valueOf(dataBean.getGoods_unit_price()), 4)));

									// 判断供应数据是否重复，如果重复就跳过
									List<GoodsSupplyDataBean> dataBean1 = supplyService.isSupplyDataRepeat(
											dataBean.setGoods_id(goodsBean.getGoods_id() + "").setIs_delete("0"));
									if (dataBean1 == null || dataBean1.size() == 0) {
										int num1 = supplyService.addSupplyData(
												dataBean.setGoods_id(goodsBean.getGoods_id() + "").setIs_delete("0")
														.setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"))
														.setUpdate_time(TimeUtils.getTimeDayAfter("yyyy-MM-dd HH:mm:ss", new Date(), 30))
														.setGoods_name(goodsBean.getGoods_name()));

										if (num1 < 0) {
											throw new Exception("添加商品供应数据失败");
										}
									}
								}
							} else {
								WriteError(response, "发布供应失败" + goodsBean.getGoods_name());
							}
						}

					} else {
						LogRecordBean recordBean = new LogRecordBean();
						String record_no = TimeUtils.getCurrentTime("yyyyMMddHHmmss") + CreateRandom.createRandom(true, 10);
						 for (int k = 0; k < dataBeans.size(); k++) {
							 GoodsSupplyDataBean dataBean = (GoodsSupplyDataBean)dataBeans.get(k);
				              recordBean.setMember_id(memberBean.getMember_id()+"");
				              recordBean.setMerchants_id(memberBean.getMerchants_id());
				              recordBean.setRecord_name(goodsBean.getGoods_name());
				              recordBean.setRecord_type("no_supply_goods");
				              recordBean.setRecord_stock(dataBean.getGoods_stock()+"");
				              recordBean.setRecord_min_buy_num(dataBean.getMin_buy_num());
				              recordBean.setRecord_min_pack_num(goodsBean.getMin_pack_num());
				              recordBean.setRecord_unit_price(dataBean.getGoods_unit_price());
				              recordBean.setRecord_no(record_no);
				              recordBean.setRecord_data_categories(dataBean.getData_categories());
				              recordBean.setRecord_give_time(dataBean.getGive_time());
						 }

						int num = recordService.addLogRecord(recordBean);
//						 goodsServiceI.addGoodsSupplys(new GoodsBean().setGoods_no(record_no).setMerchants_id(memberBean.getMerchants_id()));
				         recordService.updateLogRecords(new GoodsBean().setGoods_no(record_no).setMerchants_id(memberBean.getMerchants_id()));
//				         supplyService.addSupplyDatas(new GoodsBean().setGoods_no(record_no));
				         recordService.deleteHaveGoodsName();
						if (num > 0) {
							GoodsBean goodsBean2 = goodsServiceI
									.getGoodsMerchantsIsSupply(goodsBean.setMerchants_id(memberBean.getMerchants_id()));
							if (goodsBean2 != null) {
								for (int j = 0; j < dataBeans.size(); j++) {
									List<GoodsSupplyDataBean> supplyCount = supplyService.getGoodsSupplyData(
											new GoodsSupplyDataBean().setGoods_id(goodsBean2.getGoods_id() + ""));
									if (supplyCount.size() >= 4) {
										WriteError(response, "该型号供应数据大于4条");
										return;
									}

									GoodsSupplyDataBean dataBean = dataBeans.get(j);
									dataBean.setGoods_unit_price(String.valueOf(NumberUtils
											.KeepDecimal(Double.valueOf(dataBean.getGoods_unit_price()), 4)));

									// 判断供应数据是否重复，如果重复就跳过
									List<GoodsSupplyDataBean> dataBean1 = supplyService.isSupplyDataRepeat(
											dataBean.setGoods_id(goodsBean2.getGoods_id() + "").setIs_delete("0"));
									if (dataBean1 == null || dataBean1.size() == 0) {
										int num1 = supplyService.addSupplyData(
												dataBean.setGoods_id(goodsBean2.getGoods_id() + "").setIs_delete("0")
														.setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"))
														.setUpdate_time(TimeUtils.getTimeDayAfter("yyyy-MM-dd HH:mm:ss", new Date(), 30))
														.setGoods_name(goodsBean2.getGoods_name()));

										if (num1 < 0) {
											throw new Exception("添加商品供应数据失败");
										}
									}
								}
							} else {
								int num1 = goodsServiceI.addGoodsSupply(goodsBean.setIs_delete("0")
										.setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"))
										.setGoods_type("2").setGoods_state("1")
										.setMerchants_id(memberBean.getMerchants_id()).setIs_approved("0"));
								if (num1 > 0) {
									for (int j = 0; j < dataBeans.size(); j++) {
										List<GoodsSupplyDataBean> supplyCount = supplyService.getGoodsSupplyData(
												new GoodsSupplyDataBean().setGoods_id(goodsBean.getGoods_id() + ""));
										if (supplyCount.size() >= 4) {
											WriteError(response, "该型号供应数据大于4条");
											return;
										}

										GoodsSupplyDataBean dataBean = dataBeans.get(j);
										dataBean.setGoods_unit_price(String.valueOf(NumberUtils
												.KeepDecimal(Double.valueOf(dataBean.getGoods_unit_price()), 4)));

										// 判断供应数据是否重复，如果重复就跳过
										List<GoodsSupplyDataBean> dataBean1 = supplyService.isSupplyDataRepeat(
												dataBean.setGoods_id(goodsBean.getGoods_id() + "").setIs_delete("0"));
										if (dataBean1 == null || dataBean1.size() == 0) {
											int num2 = supplyService.addSupplyData(
													dataBean.setGoods_id(goodsBean.getGoods_id() + "").setIs_delete("0")
															.setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"))
															.setUpdate_time(TimeUtils.getTimeDayAfter("yyyy-MM-dd HH:mm:ss", new Date(), 30))
															.setGoods_name(goodsBean.getGoods_name()));

											if (num2 < 0) {
												throw new Exception("添加商品供应数据失败");
											}
										}
									}
								} else {
									WriteError(response, "发布供应失败" + goodsBean.getGoods_name());
								}
							}
						} else {
							WriteError(response, "记录平台未有型号失败");
						}

					}
				}
			}
			WriteMsg(response, "发布供应成功");
		} else {
			WriteError(response, "无任何数据");
		}
	}

	/**
	 * 上传发布供应excel文件
	 */
	@RequestMapping(params = "uploadSupplyExcel", method = RequestMethod.POST)
	public void uploadSupplyExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String json = uploadFile(request, "/excel/supply/");
		if (json.equals("-1")) {
			WriteError(response, "文件不可为空");
			return;
		} else if (json.equals("-2")) {
			WriteError(response, "上传失败");
			return;
		} else {
			WriteObject(response, json);
		}
	}

	/**
	 * 发布供应（excle）
	 */
	@RequestMapping(params = "test", method = RequestMethod.POST)
	public void test(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MemberBean memberBean=new MemberBean().setNick_name("122112");
		WriteMsg(response, new Gson().toJson(memberBean));
	}
	/**
	 * 发布供应（excle）
	 */
	@RequestMapping(params = "releaseSupplyExcle", method = RequestMethod.POST)
	public void releaseSupplyExcle(HttpServletRequest request, HttpServletResponse response, MemberBean memberBean)
			throws Exception {

		try {
			String a = TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			
			MemberBean memberBean2=memberService.verificationToken(memberBean);
			if (memberBean2 == null) {
				WritePending(response, "token failed");
				return;
			}

			String json = request.getParameter("json");
			String result = ExcelUtils.readExcel(request.getSession().getServletContext().getRealPath("/") + json);

//			String result = ExcelUtils.readExcel("C:\\Users\\JOSY.Lenovo-PC\\Desktop\\product_template.xlsx");
			String merchants_id = request.getParameter("merchants_id");
			// 替换供应excle中的字段
			result = OulianUtils.replaceSupplyField(result);

			List<GoodsBean> goodsBeans = new Gson().fromJson(result, new TypeToken<List<GoodsBean>>() {
			}.getType());
			
			MemberLevelBean memberLevelBean=memberService
					.getMemberLevelDetail(new MemberLevelBean().setLevel_id(NumberUtils.Integer(memberBean2.getMember_level())));
			int count=goodsServiceI.getMerchantsGoodsCount(new GoodsBean());
			if(goodsBeans.size()+count>memberLevelBean.getRelease_info_number()){
				WriteError(response, "已超出您发布条数限制!");
				return;
			}
			
			goodsServiceI.releaseSupplyExcle(goodsBeans,memberBean,merchants_id);
			
			WriteMsg(response, "添加成功");
		} catch (Exception e) {
			WriteError(response, "添加失败:"+e.getMessage());
		}
	}

	
	/**
	 * 发布供应（快速粘贴） 只能一对一
	 */
	@RequestMapping(params = "releaseSupplyPaste", method = RequestMethod.POST)
	public void releaseSupplyPaste(HttpServletRequest request,
			HttpServletResponse response,
			MemberBean memberBean) throws Exception
	{
		
		MemberBean memberBean2=memberService.verificationToken(memberBean);
		if (memberBean2== null) {
			WritePending(response, "token failed");
			return;
		}
		
		String json = request.getParameter("json");
		json = json.substring(0, json.length()-1);
		
		if(json!=null && !json.equals(""))
		{
			
				String[] strArray = json.split(";");
				
				MemberLevelBean memberLevelBean=memberService
						.getMemberLevelDetail(new MemberLevelBean().setLevel_id(NumberUtils.Integer(memberBean2.getMember_level())));
				int count=goodsServiceI.getMerchantsGoodsCount(new GoodsBean());
				if(strArray.length+count>memberLevelBean.getRelease_info_number()){
					WriteError(response, "已超出您发布条数限制!");
					return;
				}
				
				for (int i = 0; i < strArray.length; i++) {
					GoodsBean goodsBean = new GoodsBean();
					GoodsSupplyDataBean supplyBean = new GoodsSupplyDataBean();
					String a = strArray[i].toString();
					String[] strArray1 = a.split(","); 
					
					goodsBean.setGoods_name(strArray1[0]); //型号
					goodsBean.setMin_pack_num(strArray1[1]); //最小包装量
					//goodsBean.setGoods_stock(Integer.valueOf(strArray1[2])); //库存
					
					supplyBean.setGoods_stock(Integer.valueOf(strArray1[2])); //库存
					supplyBean.setMin_buy_num(strArray1[3]); //最小起订量
					supplyBean.setGoods_unit_price(String.valueOf(NumberUtils.KeepDecimal(NumberUtils.Float(strArray1[4]), 4))); //单价
					supplyBean.setData_categories(strArray1[5]); //类型，比如是现货、期货、促销
					supplyBean.setGive_time(strArray1[6]); //交期
					
					//查询型号是否存在
					GoodsBean goodsBean1 = goodsServiceI.getGoodsIsSupply(goodsBean);
					if(goodsBean1!=null)
					{
						GoodsBean goodsBean2 = goodsServiceI.getGoodsMerchantsIsSupply(goodsBean.setMerchants_id(memberBean.getMerchants_id()));
						if(goodsBean2!=null)
						{
								List<GoodsSupplyDataBean> supplyCount = supplyService.getGoodsSupplyData(new GoodsSupplyDataBean().setGoods_id(goodsBean2.getGoods_id()+""));
								if(supplyCount.size()>=4)
								{
									WriteError(response, "该型号供应数据大于4条");
									return;
								}
								
								List<GoodsSupplyDataBean> dataBean = supplyService.isSupplyDataRepeat(supplyBean.setGoods_id(goodsBean2.getGoods_id()+"")
										            .setIs_delete("0"));
								if(dataBean==null || dataBean.size()==0)
								{
									int num1 = supplyService.addSupplyData(supplyBean.setGoods_id(goodsBean2.getGoods_id()+"")
											.setIs_delete("0").setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"))
											.setGoods_name(goodsBean2.getGoods_name()));
									
									if(num1<0)
									{
									     throw new Exception("添加商品供应数据失败");	
									}
								}
							
						}else
						{
							int num = goodsServiceI.addGoodsSupply(goodsBean
									.setIs_delete("0").setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"))
									.setGoods_type("2").setGoods_state("1").setMerchants_id(memberBean.getMerchants_id())
									.setIs_approved("0"));
							if(num>0)
							{
									List<GoodsSupplyDataBean> supplyCount = supplyService.getGoodsSupplyData(new GoodsSupplyDataBean().setGoods_id(goodsBean.getGoods_id()+""));
									if(supplyCount.size()>=4)
									{
										WriteError(response, "该型号供应数据大于4条");
										return;
									}
								
									List<GoodsSupplyDataBean> dataBean = supplyService.isSupplyDataRepeat(supplyBean.setGoods_id(goodsBean.getGoods_id()+"")
								            .setIs_delete("0"));
									if(dataBean==null || dataBean.size()==0)
									{
										int num1 = supplyService.addSupplyData(supplyBean.setGoods_id(goodsBean.getGoods_id()+"")
								
												.setIs_delete("0").setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"))
												.setGoods_name(goodsBean.getGoods_name()));
										
										if(num1<0)
										{
										     throw new Exception("添加商品供应数据失败");	
										}
									}
					
							}else
							{
								WriteError(response, "发布供应失败"+goodsBean.getGoods_name());
							}
						}
						
					}else
					{
						LogRecordBean recordBean = new LogRecordBean();
						recordBean.setMember_id(memberBean.getMember_id()+"");
						recordBean.setRecord_name(goodsBean.getGoods_name());
						recordBean.setRecord_type("no_supply_goods");
						
						int num = recordService.addLogRecord(recordBean);
						if(num>0)
						{
							GoodsBean goodsBean2 = goodsServiceI.getGoodsMerchantsIsSupply(goodsBean.setMerchants_id(memberBean.getMerchants_id()));
							if(goodsBean2!=null)
							{
								List<GoodsSupplyDataBean> supplyCount = supplyService.getGoodsSupplyData(new GoodsSupplyDataBean().setGoods_id(goodsBean2.getGoods_id()+""));
								if(supplyCount.size()>=4)
								{
									WriteError(response, "该型号供应数据大于4条");
									return;
								}
								
								List<GoodsSupplyDataBean> dataBean = supplyService.isSupplyDataRepeat(supplyBean.setGoods_id(goodsBean2.getGoods_id()+"")
							            .setIs_delete("0"));
								if(dataBean==null || dataBean.size()==0)
								{
									int num1 = supplyService.addSupplyData(supplyBean.setGoods_id(goodsBean2.getGoods_id()+"")
											.setIs_delete("0").setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"))
											.setGoods_name(goodsBean2.getGoods_name()));
									
									if(num1<0)
									{
									     throw new Exception("添加商品供应数据失败");	
									}
								}
							
							}else
							{
								int num1 = goodsServiceI.addGoodsSupply(goodsBean
										.setIs_delete("0").setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"))
										.setGoods_type("2").setGoods_state("1").setMerchants_id(memberBean.getMerchants_id())
										.setIs_approved("0"));
								if(num1>0)
								{
									List<GoodsSupplyDataBean> supplyCount = supplyService.getGoodsSupplyData(new GoodsSupplyDataBean().setGoods_id(goodsBean.getGoods_id()+""));
									if(supplyCount.size()>=4)
									{
										WriteError(response, "该型号供应数据大于4条");
										return;
									}
									
									List<GoodsSupplyDataBean> dataBean = supplyService.isSupplyDataRepeat(supplyBean.setGoods_id(goodsBean.getGoods_id()+"")
								            .setIs_delete("0"));
									if(dataBean==null || dataBean.size()==0)
									{
										int num2 = supplyService.addSupplyData(supplyBean.setGoods_id(goodsBean.getGoods_id()+"")
												.setIs_delete("0").setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"))
												.setGoods_name(goodsBean.getGoods_name()));
										
										if(num2<0)
										{
										     throw new Exception("添加商品供应数据失败");	
										}
									}
								
								}else
								{
									WriteError(response, "发布供应失败"+goodsBean.getGoods_name());
								}
							}
						}else
						{
							WriteError(response, "记录平台未有型号失败");
						}
					}
				}
			 WriteObject(response, "发布供应成功");
		}else
		{
			WriteError(response, "数据为空");
		}
	}

	/**
	 * 多项搜索（快速粘贴）
	 */
	@RequestMapping(params = "moreSearch", method = RequestMethod.POST)
	public void moreSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<GoodsBean> goodsBeans = new ArrayList<GoodsBean>();

		// 获得文本，进行分割
		String json = request.getParameter("json");
		json = json.substring(0, json.length() - 1);

		if (json != null && !json.equals("")) {
			String[] strArray = json.split(";");
			for (int i = 0; i < strArray.length; i++) {

				String[] strArray1 = strArray[i].split(",");

				GoodsBean goodsBean = new GoodsBean();
				goodsBean.setGoods_name(strArray1[0]);
				goodsBean.setGoods_stock(Integer.valueOf(strArray1[1])+"");

				GoodsBean goodsBean1 = goodsServiceI.getGoodsData(goodsBean);
				if (goodsBean1 != null) {
					goodsBeans.add(goodsBean1);
				}
			}
			WriteObject(response, goodsBeans);
		} else {
			WriteError(response, "数据为空");
		}
	}

	/**
	 * 查询用户商品列表
	 */
	@RequestMapping(params = "getMemberGoodsList", method = RequestMethod.POST)
	public void getMemberGoodsList(HttpServletRequest request, HttpServletResponse response, MemberBean memberBean,
			GoodsBean goodsBean, PageBean pageBean) throws Exception {
		
		MemberBean memberBean1=memberService.verificationToken(memberBean);
		if (memberBean1 == null) {
			WritePending(response, "token failed");
			return;
		}
		
		Map map= goodsServiceI.getMemberGoodsList(goodsBean.setMerchants_id(memberBean1.getMerchants_id()));
		WriteObject(response,map.get("goodsBeans"),NumberUtils.Integer( map.get("total").toString()));
	}

	/**
	 * 查询单个品牌信息
	 */
	@RequestMapping(params = "getOneBrandDateil", method = RequestMethod.POST)
	public void getOneBrandDateil(HttpServletRequest request, HttpServletResponse response, GoodsBean goodsBean)
			throws Exception {
		WriteObject(response, goodsServiceI.getOneBrandDateil(goodsBean));
	}

	/**
	 * 品牌商品列表（供应）
	 */
	@RequestMapping(params = "getOneGoodsClassParentPage", method = RequestMethod.POST)
	public void getOneGoodsClassParentPage(HttpServletRequest request, HttpServletResponse response, PageBean pageBean,
			GoodsBean goodsBean) throws Exception {
		System.out.print(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
		WriteObject(response, goodsServiceI.getOneGoodsClassParentPage(goodsBean.setGoods_type("1")
                .setParent_id("-1"),pageBean), pageBean.getTotal());
		System.out.print(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
	}

	
	/**
	 * 品牌商品列表（供应）
	 */
	@RequestMapping(params = "getBrandGoodsList", method = RequestMethod.POST)
	public void getBrandGoodsList(HttpServletRequest request, HttpServletResponse response, PageBean pageBean,
			GoodsBean goodsBean) throws Exception {
		WriteObject(response, goodsServiceI.getBrandGoodsList(pageBean, goodsBean), pageBean.getTotal());
	}

	/**
	 * 查询商品详情（欧联--商家端）
	 */
	@RequestMapping(params = "getGoodsDetail", method = RequestMethod.POST)
	public void getGoodsDetail(HttpServletRequest request, HttpServletResponse response, PageBean pageBean,
			GoodsBean goodsBean, MemberBean memberBean) throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}

		WriteObject(response, goodsServiceI.getGoodsDetail(goodsBean));
	}

	/**
	 * 修改商品信息
	 */
	@RequestMapping(params = "updateGoodsDateil", method = RequestMethod.POST)
	public void updateGoodsDateil(HttpServletRequest request, HttpServletResponse response, MemberBean memberBean,
			GoodsBean goodsBean) throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}

		String json = request.getParameter("json");
		List<GoodsSupplyDataBean> dataBeans = new Gson().fromJson(json, new TypeToken<List<GoodsSupplyDataBean>>() {
		}.getType());

		int num = goodsServiceI.updateGoodsDateil(goodsBean,dataBeans);
		if (num > 0) {
			WriteObject(response, "修改成功");
		} else {
			WriteError(response, "修改失败");
		}
	}

	/**
	 * 根据型号查询该详情（从平台商品点进去）
	 */
	@RequestMapping(params = "getGoodsDataByName", method = RequestMethod.POST)
	public void getGoodsDataByName(HttpServletRequest request, HttpServletResponse response, GoodsBean goodsBean)
			throws Exception {
		WriteObject(response, goodsServiceI.getGoodsData(goodsBean));
	}

	/**
	 * 根据型号查询有多少供应商供应
	 */
	@RequestMapping(params = "getMemberByGoodsName", method = RequestMethod.POST)
	public void getMemberByGoodsName(HttpServletRequest request, HttpServletResponse response, GoodsBean goodsBean)
			throws Exception {
		Map map= goodsServiceI.getMemberByGoodsName(goodsBean);
		WriteObject(response,map.get("goodsBeans"));
	}

	/**
	 * 根据品牌查询型号
	 */
	@RequestMapping(params = "getGoodsByBrandPage", method = RequestMethod.POST)
	public void getGoodsByBrandPage(HttpServletRequest request, HttpServletResponse response, GoodsBean goodsBean,
			PageBean pageBean) throws Exception {
		WriteObject(response, goodsServiceI.getGoodsByBrandPage(goodsBean, pageBean), pageBean.getTotal());
	}

	/**
	 * 根据型号查询该详情（商家型号点进去）
	 */
	@RequestMapping(params = "getMerchantsGoodsDataByName", method = RequestMethod.POST)
	public void getMerchantsGoodsDataByName(HttpServletRequest request, HttpServletResponse response,
			GoodsBean goodsBean) throws Exception {
		WriteObject(response, goodsServiceI.getMerchantsGoodsData(goodsBean));
	}

	/**
	 * 供应栏（首页）
	 */
	@RequestMapping(params = "GetSupplyBar", method = RequestMethod.POST)
	public void GetSupplyBar(HttpServletRequest request, HttpServletResponse response) throws Exception {

	}

	/**
	 * 最新供应信息（求购页面）
	 */
	@RequestMapping(params = "GetNewestSupplyInfo", method = RequestMethod.POST)
	public void GetNewestSupplyInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		WriteObject(response, goodsServiceI.GetNewestSupplyInfo(new GoodsBean()));
	}

	/**
	 * 热销商品列表
	 */
	@RequestMapping(params = "getSellHotGoods", method = RequestMethod.POST)
	public void getSellHotGoods(HttpServletRequest request, HttpServletResponse response) throws Exception {
		WriteObject(response, goodsServiceI.getSellHotGoods(new GoodsBean()));
	}

	/**
	 * 批量上下架
	 */
	@RequestMapping(params = "batchUpAndDown", method = RequestMethod.POST)
	public void batchUpAndDown(HttpServletRequest request, HttpServletResponse response, GoodsBean goodsBean,
			MemberBean memberBean) throws Exception {
		MemberBean memberBean2=memberService.verificationToken(memberBean);
		if (memberBean2 == null) {
			WritePending(response, "token failed");
			return;
		}
		
		MemberLevelBean memberLevelBean=memberService
				.getMemberLevelDetail(new MemberLevelBean().setLevel_id(NumberUtils.Integer(memberBean2.getMember_level())));
		String cur_time=TimeUtils.getCurrentTime("yyyy-MM-dd 00:00:00");
		String update_time1 = TimeUtils.getTimeDayAfter("yyyy-MM-dd 00:00:00", new Date(), 30);
		int count=0;
		if(cur_time.equals(memberBean2.getRefresh_time())){
			count=NumberUtils.Integer(memberBean2.getRefresh_count());
		}
		
		if(count > NumberUtils.Integer(memberLevelBean.getRefresh_count())){
			WriteError(response, "上架次数!已达上限!");
			return;
		}else{
			memberService.updateMemberDetail(new MemberBean()
					.setMember_id(memberBean2.getMember_id())
					.setRefresh_time(cur_time)
					.setRefresh_count((count+1)+""));
			supplyService.updateSupplyData(new GoodsSupplyDataBean()
		      .setUpdate_time(update_time1));
		}

		int num = goodsServiceI.batchUpAndDown(goodsBean);
		if (num > 0) {
			WriteObject(response, "操作成功");
		} else {
			WriteError(response, "操作失败");
		}
	}

	/**
	 * 删除型号
	 */
	@RequestMapping(params = "deleteGoodsName", method = RequestMethod.POST)
	public void deleteGoodsName(HttpServletRequest request, HttpServletResponse response, GoodsBean goodsBean,
			MemberBean memberBean) throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}

		int num = goodsServiceI.deleteGoodsName(goodsBean);
		if (num > 0) {
			WriteObject(response, "操作成功");
		} else {
			WriteError(response, "操作失败");
		}
	}

	/**
	 * 批量删除型号
	 */
	@RequestMapping(params = "batchDeleteGoodsName", method = RequestMethod.POST)
	public void batchDeleteGoodsName(HttpServletRequest request, HttpServletResponse response, GoodsBean goodsBean,
			MemberBean memberBean) throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}

		int num = goodsServiceI.batchDeleteGoodsName(goodsBean);
		if (num > 0) {
			WriteObject(response, "操作成功");
		} else {
			WriteError(response, "操作失败");
		}
	}

	/**
	 * 精确筛选（供应）
	 */
	@RequestMapping(params = "getAccurateScreening", method = RequestMethod.POST)
	public void getAccurateScreening(HttpServletRequest request, HttpServletResponse response, GoodsBean goodsBean)
			throws Exception {
		// 平台是否提供该型号
		GoodsBean goodsBean1 = goodsServiceI.getGoodsIsSupply(goodsBean);
		if (goodsBean1 != null) {
			// 获得型号基本信息（单价区间，最小起订量等）
			GoodsBean goodsBean2 = goodsServiceI
					.getGoodsBasicDetail(new GoodsBean().setGoods_name(goodsBean1.getGoods_name()));
			goodsBean1.setPrice_interval(goodsBean2.getPrice_interval());
			goodsBean1.setBuy_num_interval(goodsBean2.getBuy_num_interval());

			// 获得型号总供应数
			String totalStock = goodsServiceI.getTotalStock(new GoodsBean().setGoods_name(goodsBean1.getGoods_name()));
			goodsBean1.setTotalStock(totalStock);

			WriteObject(response, goodsBean1);
		} else {
			WriteError(response, "平台暂无该型号");
		}
	}

	/**
	 * 全部删除
	 */
	@RequestMapping(params = "allGoodsDelete", method = RequestMethod.POST)
	public void allGoodsDelete(HttpServletRequest request, HttpServletResponse response, GoodsBean goodsBean,
			MemberBean memberBean) throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}

		int num = goodsServiceI.allGoodsDelete(goodsBean);
		if (num > 0) {
			supplyService.deleteSupplyDataAll(new GoodsSupplyDataBean()
	          .setMerchants_id(goodsBean.getMerchants_id()));
			WriteObject(response, "操作成功");
		} else {
			WriteError(response, "操作失败");
		}
	}

	/**
	 * 全部上架/下架
	 */
	@RequestMapping(params = "allGoodsStateUpdate", method = RequestMethod.POST)
	public void allGoodsStateUpdate(HttpServletRequest request, HttpServletResponse response, GoodsBean goodsBean,
			MemberBean memberBean) throws Exception {
		MemberBean memberBean2=memberService.verificationToken(memberBean);
		if (memberBean2 == null) {
			WritePending(response, "token failed");
			return;
		}
		
		MemberLevelBean memberLevelBean=memberService
				.getMemberLevelDetail(new MemberLevelBean().setLevel_id(NumberUtils.Integer(memberBean2.getMember_level())));
		String cur_time=TimeUtils.getCurrentTime("yyyy-MM-dd 00:00:00");
		int count=0;
		if(cur_time.equals(memberBean2.getRefresh_time())){
			count=NumberUtils.Integer(memberBean2.getRefresh_count());
		}
		
		if(count>NumberUtils.Integer(memberLevelBean.getRefresh_count())){
			WriteError(response, "上架次数!已达上限!");
			return;
		}else{
			memberService.updateMemberDetail(new MemberBean()
					.setMember_id(memberBean2.getMember_id())
					.setRefresh_time(cur_time)
					.setRefresh_count((count+1)+""));
		}
		
		int num = goodsServiceI.allGoodsStateUpdate(goodsBean);
		if (num > 0) {
			WriteObject(response, "操作成功");
		} else {
			WriteError(response, "操作失败");
		}
	}

	/**
	 * 供应筛选列表
	 */
	@RequestMapping(params = "SupplyScreeningList", method = RequestMethod.POST)
	public void SupplyScreeningList(HttpServletRequest request, HttpServletResponse response, PageBean pageBean,
			GoodsBean goodsBean, MemberBean memberBean) throws Exception {
		if (goodsBean.getGoods_name() != null && !"".equals(goodsBean.getGoods_name())) {
			goodsBean.setGoods_name(goodsBean.getGoods_name().replaceAll(" ", ""));
			SearchBean searchBean = goodsServiceI
					.getHotSearch(new SearchBean().setSearch_name(goodsBean.getGoods_name()).setSearch_type("goods"));
			if (searchBean != null) {
				goodsServiceI.updateHotSearch(searchBean);
			} else {
				goodsServiceI.inserHotSearch(new SearchBean().setSearch_name(goodsBean.getGoods_name())
						.setSearch_type("goods").setIs_fixed("0").setSearch_count("1"));
			}
		}
		WriteObject(response, goodsServiceI.SupplyScreeningList(goodsBean, pageBean),pageBean.getTotal());
	}
	/**
	 * 商家导出型号列表
	 */
	@RequestMapping(params = "exportMerchantsGoodsExcel", method = RequestMethod.POST)
	public void exportMerchantsGoodsExcel(HttpServletRequest request, HttpServletResponse response, GoodsBean goodsBean,
			MemberBean memberBean) throws Exception {
//		if (memberService.verificationToken(memberBean) == null) {
//			WritePending(response, "token failed");
//			return;
//		}
		
		MemberBean memberBean2=memberService.getMemberByMerchants(memberBean);
		if(memberBean2==null){
			WriteError(response, "此商家不存在");
			return;
		}
		
		if(!"1".equals(memberBean2.getIs_down_goods())){
			WriteError(response, "此商家不可下载");
			return;
		}

		String fileName = TimeUtils.getCurrentTime("yyyyMMddHHmmss") + ".xls";

		List<ExcelBean> excelBeans = new ArrayList<ExcelBean>();
		excelBeans.add(new ExcelBean().setName("型号").setType("goods_name"));
		excelBeans.add(new ExcelBean().setName("最小包装量").setType("min_pack_num"));
		excelBeans.add(new ExcelBean().setName("库存数量").setType("goods_stock"));
		excelBeans.add(new ExcelBean().setName("最小起订量").setType("min_buy_num"));
		excelBeans.add(new ExcelBean().setName("单价").setType("goods_unit_price"));
		excelBeans.add(new ExcelBean().setName("类型").setType("data_categories_show"));
		excelBeans.add(new ExcelBean().setName("交期").setType("give_time"));
		excelBeans.add(new ExcelBean().setName("状态").setType("goods_state_show"));

		List<Object> goodsBeans = goodsServiceI.exportMemberGoodsExcel(goodsBean);

		boolean is_success = ExcelUtils.exportExcel(
				request.getSession().getServletContext().getRealPath("/") + "/excel/" + fileName, excelBeans,
				goodsBeans, response);
		if (is_success) {
			WriteMsg(response, "/excel/" + fileName);
		} else {
			WriteError(response, "导出失败");
		}
	}

	
	/**
	 * 商家导出型号列表
	 */
	@RequestMapping(params = "exportMemberGoodsExcel", method = RequestMethod.POST)
	public void exportMemberGoodsExcel(HttpServletRequest request, HttpServletResponse response, GoodsBean goodsBean,
			MemberBean memberBean) throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}

		String fileName = TimeUtils.getCurrentTime("yyyyMMddHHmmss") + ".xls";

		List<ExcelBean> excelBeans = new ArrayList<ExcelBean>();
		excelBeans.add(new ExcelBean().setName("型号").setType("goods_name"));
		excelBeans.add(new ExcelBean().setName("最小包装量").setType("min_pack_num"));
		excelBeans.add(new ExcelBean().setName("库存数量").setType("goods_stock"));
		excelBeans.add(new ExcelBean().setName("最小起订量").setType("min_buy_num"));
		excelBeans.add(new ExcelBean().setName("单价").setType("goods_unit_price"));
		excelBeans.add(new ExcelBean().setName("类型").setType("data_categories_show"));
		excelBeans.add(new ExcelBean().setName("交期").setType("give_time"));
		excelBeans.add(new ExcelBean().setName("状态").setType("goods_state_show"));

		List<Object> goodsBeans = goodsServiceI.exportMemberGoodsExcel(goodsBean);

		boolean is_success = ExcelUtils.exportExcel(
				request.getSession().getServletContext().getRealPath("/") + "/excel/" + fileName, excelBeans,
				goodsBeans, response);
		if (is_success) {
			WriteMsg(response, "/excel/" + fileName);
		} else {
			WriteError(response, "导出失败");
		}
	}

	
	/**
	 * 首页搜索框
	 */
	@RequestMapping(params = "HomePageSearch", method = RequestMethod.POST)
	public void HomePageSearch(HttpServletRequest request,
			HttpServletResponse response,PageBean pageBean,
			GoodsBean goodsBean) throws Exception
	{
		if (goodsBean.getGoods_name() != null && !"".equals(goodsBean.getGoods_name())) {
			goodsBean.setGoods_name(goodsBean.getGoods_name().replaceAll(" ", ""));
			SearchBean searchBean = goodsServiceI
					.getHotSearch(new SearchBean().setSearch_name(goodsBean.getGoods_name()).setSearch_type("goods"));
			if (searchBean != null) {
				goodsServiceI.updateHotSearch(searchBean);
			} else {
				goodsServiceI.inserHotSearch(new SearchBean().setSearch_name(goodsBean.getGoods_name())
						.setSearch_type("goods").setIs_fixed("0").setSearch_count("1"));
			}
		}
		WriteObject(response, goodsServiceI.getGoodsDataList(goodsBean, pageBean),pageBean.getTotal());
	}
	
	/**
	 * 查询可替换商品
	 */
	@RequestMapping(params = "replaceableGoods", method = RequestMethod.POST)
	public void replaceableGoods(HttpServletRequest request,
			HttpServletResponse responses,GoodsBean goodsBean)
	{
		WriteObject(responses, goodsServiceI.replaceableGoods(goodsBean));
	}
	
	/**
	 * 相关供应消息 
	 */
	@RequestMapping(params = "relatedSupplyMessage", method = RequestMethod.POST)
	public void relatedSupplyMessage(HttpServletRequest request,
			HttpServletResponse responses,GoodsBean goodsBean,
			PageBean pageBean)
	{
		WriteObject(responses, goodsServiceI.relatedSupplyMessage(goodsBean,pageBean));
	}
	
}
