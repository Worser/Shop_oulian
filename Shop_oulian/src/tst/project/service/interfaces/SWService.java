package tst.project.service.interfaces;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.ls.LSException;

import tst.project.bean.activity.AlbumBean;
import tst.project.bean.activity.NewsBean;
import tst.project.bean.banner.ClassBannerBean;
import tst.project.bean.goods.BrandBean;
import tst.project.bean.goods.GoodsBean;
import tst.project.bean.home.HomeGoodsBean;
import tst.project.bean.home.LabelBean;
import tst.project.bean.member.MemberBean;
import tst.project.bean.merchants.MerchantsBean;
import tst.project.bean.order.OrderSWBean;
import tst.project.bean.others.CodeBean;
import tst.project.dao.interfaces.SWDao;
import tst.project.page.PageBean;


@Service
@Transactional(rollbackFor = Exception.class)
public class SWService {
	@Resource
	SWDao swDao;
	
	@Resource
	CodeService codeService;
	
	/**
	 * 企业购分类
	 * @param goodsBean
	 * @param pageBean
	 * @return
	 */
	public List<Map> getBusinessBuyClass(GoodsBean goodsBean){
		List<Map> goodsBeans=swDao.getBusinessBuyClass1(goodsBean.setParent_id("-1"));
		if(goodsBeans!=null){
			for (int i = 0; i < goodsBeans.size(); i++) {
				Map goodsBean2=goodsBeans.get(i);
				List<Map> goodsBeans2=swDao
						.getBusinessBuyClass2(new GoodsBean().setParent_id(goodsBean2.get("goods_id").toString()+""));
				if(goodsBeans2!=null){
					for (int j = 0; j < goodsBeans2.size(); j++) {
						Map goodsBean3=goodsBeans2.get(j);
						List<Map> goodsBeans3=swDao
								.getBusinessBuyClass3(new GoodsBean().setParent_id(goodsBean3.get("goods_id").toString()+""));
						goodsBean3.put("goodsBeans",goodsBeans3);
					}
				}
				goodsBean2.put("goodsBeans",goodsBeans2);
			}
		}
		return goodsBeans;
	}
	
	/**
	 * 企业购分类
	 * @param goodsBean
	 * @param pageBean
	 * @return
	 */
	public List<Map> getBusinessBuyClass(GoodsBean goodsBean,String level){
		if("1".equals(level)){
			return swDao.getBusinessBuyClass1(goodsBean);
		}else if("2".equals(level)){
			return swDao.getBusinessBuyClass2(goodsBean);
		}else{
			return swDao.getBusinessBuyClass3(goodsBean);			
		}
	}
	/**
	 * 获得订单根据id
	 * @param orderSWBean
	 * @return
	 */
	public OrderSWBean getOrderSwById(OrderSWBean orderSWBean){
		return swDao.getOrderSwById(orderSWBean);
	}
	
	/**
	 * 抢单
	 * @param orderSWBean
	 * @return
	 * @throws Exception 
	 */
	public int grabOrderSW(OrderSWBean orderSWBean) throws Exception{
		int num=swDao.updateOrderSwState(orderSWBean);
		if(num>0){
			num=swDao.grabOrderSW(orderSWBean);
			if(num<=0){
				throw new Exception("抢单失败");
			}
		}
		return num;
	}
	
	/**
	 * 我的抢单
	 * @return
	 */
	public List<OrderSWBean> getMemberGrabOrderSWs(OrderSWBean orderSWBean,PageBean pageBean){
		return swDao.getMemberGrabOrderSWs(orderSWBean, pageBean);
	}
	
	
	
	/**
	 * 删除订单
	 * @return
	 */
	public int deleteOrderSW(OrderSWBean orderSWBean){
		return swDao.deleteOrderSW(orderSWBean);
	}
	
	/**
	 * 取消订单
	 * @return
	 */
	public int cancelOrderSW(OrderSWBean orderSWBean){
		return swDao.cancelOrderSW(orderSWBean);
	}
	/**
	 * 我的发单
	 * @return
	 */
	public List<OrderSWBean> getMemberOrderSWs(OrderSWBean orderSWBean,PageBean pageBean){			
		return swDao.getMemberOrderSWs(orderSWBean, pageBean);
	}
	
	/**
	 * 添加订单（发单）
	 * @return
	 * @throws Exception 
	 */
	public int insertOrderSW(OrderSWBean orderSWBean,CodeBean codeBean) throws Exception{
		int num=swDao.insertOrderSW(orderSWBean);
		if(num>0){
			List<MemberBean> memberBeans=swDao.getMemberMerchants(new MemberBean());
			if(memberBeans!=null){
				for (int i = 0; i < memberBeans.size(); i++) {
					MemberBean memberBean=memberBeans.get(i);
					num=swDao.insertOrderSWMember(new OrderSWBean().setMember_id(memberBean.getMember_id()+"")
												.setOrder_id(orderSWBean.getOrder_id()));
					if(num<=0){
						throw new Exception("发单失败");
					}
					
					num=codeService.deleteCodeByMobileAndCode(codeBean);
					if(num<=0){
						throw new Exception("验证码销毁失败");
					}
				}
			}
		}
		return num;
	}
	
	/**
	 * 生物网站首页
	 * @return
	 */
	public List<GoodsBean> getHomeClassWeb(GoodsBean goodsBean){
		List<GoodsBean> goodsBeans=swDao.getGoodsClass(goodsBean.setParent_id("-1").setGoods_type("1"));
		for (int i = 0; i < goodsBeans.size(); i++) {
			GoodsBean goodsBean2=goodsBeans.get(i);
			List<GoodsBean> goodsBeans2=swDao.getGoodsClass(new GoodsBean().setParent_id(goodsBean2.getGoods_id()+"").setGoods_type("1"));
			
			for (int j = 0; j < goodsBeans2.size(); j++) {
				GoodsBean goodsBean3=goodsBeans2.get(j);
				List<GoodsBean> goodsRecommendBeans=swDao.
						getRecommendGoods(new GoodsBean().setGoods_uuid(goodsBean3.getGoods_uuid()+"").setGoods_type("2"));
				goodsBean3.setGoodsRecommendBeans(goodsRecommendBeans);
			}
			
			goodsBean2.setGoodsBeans(goodsBeans2);
		
			List<ClassBannerBean> classBannerBeans=swDao
					.getClassBanners(new ClassBannerBean().setClass_id(goodsBean2.getGoods_id()+""));
			goodsBean2.setClassBannerBeans(classBannerBeans);
			
//			List<GoodsBean> classRecommendBeans=swDao.
//					getClassRecommendGoods(new GoodsBean().setGoods_uuid(goodsBean2.getGoods_uuid()+"").setGoods_type("2"));
//			goodsBean2.setClassRecommendBeans(classRecommendBeans);
			
			List<BrandBean> brandBeans=swDao.getGoodsBrands(goodsBean2);
			goodsBean2.setBrandBeans(brandBeans);
		}		
		return goodsBeans;
	}
	
	/**
	 * 团购商品列表(只是标签)
	 * @param goodsBean
	 * @param pageBean
	 * @return
	 */
	public List<GoodsBean> getGroupGoodss(GoodsBean goodsBean,PageBean pageBean){
		return swDao.getGroupGoodss(goodsBean, pageBean);
	}
	
		
	/**
	 * 店铺下推荐的商品列表
	 * @param goodsBean
	 * @return
	 */
	public List<GoodsBean> getMerchantsRecommendGoods(GoodsBean goodsBean){
		return swDao.getMerchantsRecommendGoods(goodsBean);
	}
	/**
	 * 用户对某个动态关闭详情
	 * @param merchantsBean
	 * @return
	 */
	public MerchantsBean getOneMemberDynamicHeadlines(MerchantsBean merchantsBean){
		return swDao.getOneMemberDynamicHeadlines(merchantsBean);
	}
	/**
	 * 用户关闭动态头条
	 * @param merchantsBean
	 * @return
	 */
	public int memberCloseDynamicHeadlines(MerchantsBean merchantsBean){
		return swDao.memberCloseDynamicHeadlines(merchantsBean);
	}
	
	/**
	 * 店铺头条 热门店铺
	 * @return
	 */
	public List<MerchantsBean> getHotHeadlinesMerchants(MerchantsBean merchantsBean,PageBean pageBean){
		List<MerchantsBean> merchantsBeans=swDao.getHotHeadlinesMerchants(merchantsBean, pageBean);
		for (int i = 0; i < merchantsBeans.size(); i++) {
			List<GoodsBean> goodsBeans=getMerchantsRecommendGoods(new GoodsBean()
					.setMerchants_id(merchantsBeans.get(i).getMerchants_id()+""));
			merchantsBeans.get(i).setGoodsBeans(goodsBeans);
		}
		return merchantsBeans;
	}
	
	/**
	 * 店铺头条 动态店铺
	 * @return
	 */
	public List<MerchantsBean> getDynamicHeadlinesMerchants(MerchantsBean merchantsBean,PageBean pageBean){
		List<MerchantsBean> merchantsBeans=swDao.getDynamicHeadlinesMerchants(merchantsBean, pageBean);
		for (int i = 0; i < merchantsBeans.size(); i++) {
			List<GoodsBean> goodsBeans=getMerchantsRecommendGoods(new GoodsBean()
					.setMerchants_id(merchantsBeans.get(i).getMerchants_id()+""));
			merchantsBeans.get(i).setGoodsBeans(goodsBeans);
		}
		return merchantsBeans;
	}
	
	/**
	 * 发现好货 精选
	 * @param goodsBean
	 * @return
	 */
	public List<GoodsBean> getExactGoodGoodss(GoodsBean goodsBean,PageBean pageBean){
		return swDao.getExactGoodGoodss(goodsBean,pageBean);
	}
	/**
	 * 发现好货 专辑列表
	 * @param goodsBean
	 * @return
	 */
	public List<AlbumBean> getAlbums(AlbumBean albumBean,PageBean pageBean){
		return swDao.getAlbums(albumBean, pageBean);
	}
	/**
	 * 发现好货 专辑
	 * @param goodsBean
	 * @return
	 */
	public List<GoodsBean> getAlbumGoodGoodss(GoodsBean goodsBean,PageBean pageBean){
		return swDao.getAlbumGoodGoodss(goodsBean,pageBean);
	}
	/**
	 * 首页推荐商品列表
	 * @return
	 */
	public List<GoodsBean> getHomeGoods(GoodsBean goodsBean,PageBean pageBean){
		List<GoodsBean> goodsBeans=swDao.getHomeGoods(goodsBean,pageBean);
//		if(goodsBeans==null||goodsBeans.size()>0){
//			return swDao.getHomeGoods(new GoodsBean(),pageBean);
//		}
		return goodsBeans;
	}
	
	/**
	 * 首页分类标签
	 * @param homeGoodsBean
	 * @return
	 */
	public List<LabelBean> getHomeLabels(LabelBean labelBean){
		return swDao.getHomeLabels(labelBean);
	}
	
	/**
	 * 波尔快报的分类列表
	 * @param newsBean
	 * @return
	 */
	public List<NewsBean> getNewsClass(NewsBean newsBean){
		return swDao.getNewsClass(newsBean);
	}
	
	/**
	 * 波尔快报的分类下商品列表
	 * @param goodsBean
	 * @return
	 */
	public List<GoodsBean> getNewsGoods(NewsBean newsBean,PageBean pageBean){
		return swDao.getNewsGoods(newsBean,pageBean);
	}
	
	/**
	 * 推荐快报
	 * @param newsBean
	 * @return
	 */
	public List<NewsBean> getNewsRecommendGoods(NewsBean newsBean){
		return swDao.getNewsRecommendGoods(newsBean);
	}
	/**
	 * 波尔快报的精选商品列表
	 * @param goodsBean
	 * @return
	 */
	public List<GoodsBean> getNewsExactGoods(NewsBean newsBean,PageBean pageBean){
		return swDao.getNewsExactGoods(newsBean,pageBean);
	}
}
