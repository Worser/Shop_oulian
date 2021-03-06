package tst.project.dao.interfaces;

import java.util.List;
import java.util.Map;

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
import tst.project.page.PageBean;

public interface SWDao{
	
	/**
	 * 分类推荐广告
	 * @param classBannerBean
	 * @return
	 */
	public List<ClassBannerBean> getClassBanners(ClassBannerBean classBannerBean);
	/**
	 * 企业购分类
	 * @param goodsBean
	 * @param pageBean
	 * @return
	 */
	public List<Map> getBusinessBuyClass1(GoodsBean goodsBean);
	/**
	 * 企业购分类
	 * @param goodsBean
	 * @param pageBean
	 * @return
	 */
	public List<Map> getBusinessBuyClass2(GoodsBean goodsBean);
	/**
	 * 企业购分类
	 * @param goodsBean
	 * @param pageBean
	 * @return
	 */
	public List<Map> getBusinessBuyClass3(GoodsBean goodsBean);
	
	/**
	 * 
	 * @param memberBean
	 * @return
	 */
	public List<MemberBean> getMemberMerchants(MemberBean memberBean);
	
	/**
	 * 获得订单根据id
	 * @param orderSWBean
	 * @return
	 */
	public OrderSWBean getOrderSwById(OrderSWBean orderSWBean);
	
	/**
	 * 抢单
	 * @param orderSWBean
	 * @return
	 */
	public int grabOrderSW(OrderSWBean orderSWBean);
	/**
	 * 抢单
	 * @param orderSWBean
	 * @return
	 */
	public int updateOrderSwState(OrderSWBean orderSWBean);
	
	
	/**
	 * 我的抢单
	 * @return
	 */
	public List<OrderSWBean> getMemberGrabOrderSWs(OrderSWBean orderSWBean,PageBean pageBean);
	
	/**
	 * 取消订单
	 * @return
	 */
	public int deleteOrderSW(OrderSWBean orderSWBean);
	
	/**
	 * 取消订单
	 * @return
	 */
	public int cancelOrderSW(OrderSWBean orderSWBean);
	
	/**
	 * 我的发单
	 * @return
	 */
	public List<OrderSWBean> getMemberOrderSWs(OrderSWBean orderSWBean,PageBean pageBean);
	
	/**
	 * 添加订单（发单）
	 * @return
	 */
	public int insertOrderSW(OrderSWBean orderSWBean);
	
	/**
	 * 添加订单（发单）
	 * @return
	 */
	public int insertOrderSWMember(OrderSWBean orderSWBean);
	
	
	/**
	 * 商品下的品牌列表
	 * @param brandBean
	 * @return
	 */
	public List<BrandBean> getGoodsBrands(GoodsBean goodsBean);
	
	/**
	 * 生物网站首页
	 * @param goodsBean
	 * @return
	 */
	public List<GoodsBean> getClassRecommendGoods(GoodsBean goodsBean);
	
	/**
	 * 生物网站首页
	 * @param goodsBean
	 * @return
	 */
	public List<GoodsBean> getRecommendGoods(GoodsBean goodsBean);
	
	/**
	 * 生物网站首页
	 * @param goodsBean
	 * @return
	 */
	public List<GoodsBean> getGoodsClass(GoodsBean goodsBean);
	/**
	 * 团购商品列表(只是标签)
	 * @param goodsBean
	 * @param pageBean
	 * @return
	 */
	public List<GoodsBean> getGroupGoodss(GoodsBean goodsBean,PageBean pageBean);
	
	/**
	 * 店铺下推荐的商品列表
	 * @param goodsBean
	 * @return
	 */
	public List<GoodsBean> getMerchantsRecommendGoods(GoodsBean goodsBean);
	
	/**
	 * 用户对某个动态关闭详情
	 * @param merchantsBean
	 * @return
	 */
	public MerchantsBean getOneMemberDynamicHeadlines(MerchantsBean merchantsBean);
	/**
	 * 用户关闭动态头条
	 * @param merchantsBean
	 * @return
	 */
	public int memberCloseDynamicHeadlines(MerchantsBean merchantsBean);
	
	/**
	 * 店铺头条 热门店铺
	 * @return
	 */
	public List<MerchantsBean> getHotHeadlinesMerchants(MerchantsBean merchantsBean,PageBean pageBean);
	/**
	 * 店铺头条 动态店铺
	 * @return
	 */
	public List<MerchantsBean> getDynamicHeadlinesMerchants(MerchantsBean merchantsBean,PageBean pageBean);
	/**
	 * 发现好货 精选
	 * @param goodsBean
	 * @return
	 */
	public List<GoodsBean> getExactGoodGoodss(GoodsBean goodsBean,PageBean pageBean);
	
	/**
	 * 发现好货 专辑列表
	 * @param goodsBean
	 * @return
	 */
	public List<AlbumBean> getAlbums(AlbumBean albumBean,PageBean pageBean);
	
	
	/**
	 * 发现好货 专辑商品
	 * @param goodsBean
	 * @return
	 */
	public List<GoodsBean> getAlbumGoodGoodss(GoodsBean goodsBean,PageBean pageBean);
	
	/**
	 * 首页推荐商品列表
	 * @return
	 */
	public List<GoodsBean> getHomeGoods(GoodsBean goodsBean,PageBean pageBean);
	
	/**
	 * 首页分类标签
	 * @param homeGoodsBean
	 * @return
	 */
	public List<LabelBean> getHomeLabels(LabelBean labelBean);
	
	/**
	 * 波尔快报的分类列表
	 * @param newsBean
	 * @return
	 */
	public List<NewsBean> getNewsClass(NewsBean newsBean);
	
	/**
	 * 波尔快报的分类下商品列表
	 * @param goodsBean
	 * @return
	 */
	public List<GoodsBean> getNewsGoods(NewsBean newsBean,PageBean pageBean);
	/**
	 * 推荐快报
	 * @param newsBean
	 * @return
	 */
	public List<NewsBean> getNewsRecommendGoods(NewsBean newsBean);
	
	/**
	 * 波尔快报的精选商品列表
	 * @param goodsBean
	 * @return
	 */
	public List<GoodsBean> getNewsExactGoods(NewsBean newsBean,PageBean pageBean);
}
