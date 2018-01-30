package tst.project.service.interfaces;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;

import com.google.gson.Gson;
import com.pingplusplus.Pingpp;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.Refund;

import tst.project.bean.HostBean;
import tst.project.bean.activity.ActivityBean;
import tst.project.bean.activity.GroupBuyBean;
import tst.project.bean.activity.GroupBuyGoodsBean;
import tst.project.bean.activity.GroupBuyMemberBean;
import tst.project.bean.activity.PromotionGoodsBean;
import tst.project.bean.address.AddressBean;
import tst.project.bean.distribution.DistributionBean;
import tst.project.bean.goods.GoodsBean;
import tst.project.bean.goods.GoodsImgBean;
import tst.project.bean.goods.GoodsParameterBean;
import tst.project.bean.goods.GoodsProfitBean;
import tst.project.bean.goods.GoodsServiceBean;
import tst.project.bean.goods.GoodsSupplyDataBean;
import tst.project.bean.goods.ShoppingCarBean;
import tst.project.bean.member.CouponBean;
import tst.project.bean.member.CustomerServiceBean;
import tst.project.bean.member.IntegralBean;
import tst.project.bean.member.MemberBean;
import tst.project.bean.member.MemberProfitBean;
import tst.project.bean.member.MessageBean;
import tst.project.bean.merchants.MerchantsBean;
import tst.project.bean.order.AssessmentBean;
import tst.project.bean.order.AssessmentImgBean;
import tst.project.bean.order.BusinessProfitBean;
import tst.project.bean.order.InviseBean;
import tst.project.bean.order.LogisticsBean;
import tst.project.bean.order.LogisticsDetailBean;
import tst.project.bean.order.OrderBean;
import tst.project.bean.order.OrderGoodsBean;
import tst.project.bean.order.OrderLineBean;
import tst.project.bean.order.OrderMerchantsBean;
import tst.project.bean.order.OrderParameterBean;
import tst.project.bean.order.OrderServiceBean;
import tst.project.bean.order.PingSettingBean;
import tst.project.bean.order.RefundBean;
import tst.project.bean.order.RefundImgBean;
import tst.project.bean.order.RefundReasonBean;
import tst.project.bean.others.PercentBean;
import tst.project.bean.others.VerificationBean;
import tst.project.dao.interfaces.OrderDao;
import tst.project.page.PageBean;
import tst.project.utils.CreateRandom;
import tst.project.utils.HBRUtils;
import tst.project.utils.Kuaidi100Utils;
import tst.project.utils.NumberUtils;
import tst.project.utils.TimeUtils;
import tst.project.utils.VerificationCodeUtils;
import tst.project.utils.XmlUtils;

@Service
@Transactional(rollbackFor = { Exception.class })
public class OrderService {
	@Resource
	OrderDao orderDao;

	@Resource
	AddressService addressService;

	@Resource
	GoodsServiceI goodsServiceI;

	@Resource
	MerchantsServiceI merchantsServiceI;

	@Resource
	ShoppingCarService shoppingCarService;

	@Resource
	ActivityService activityService;

	@Resource
	MemberService memberService;

	@Resource
	OthersService othersService;

	@Resource
	CouponService couponService;

	@Resource
	GoodsServiceI2 goodsServiceI2;

	@Resource
	GoodsSupplyDataService goodsSupplyDataService;

	@Resource
	CodeService codeService;

	@Resource
	MessageService messageService;

	@Resource
	GoodsSupplyDataService supplyService;

	/**
	 * 设置订单是否开过发票
	 * @param orderBean
	 * @return
	 */
	public int updateOrderInvise(OrderBean orderBean){
		return orderDao.updateOrderInvise(orderBean);
	}
	
	/**
	 * 提醒发货
	 * 
	 * @param orderBean
	 * @return
	 */
	public int remarkSendTime(OrderBean orderBean) {
		int num = orderDao.remarkSendTime(orderBean);
		if (num > 0) {
			OrderBean orderBean2 = orderDao.getOneOrderDetail(orderBean);
			MemberBean memberBean2 = memberService
					.getMemberByID(new MemberBean().setMember_id(NumberUtils.Integer(orderBean2.getMember_id())));
			MemberBean memberBean3 = memberService
					.getMemberByMerchants(new MemberBean().setMerchants_id(orderBean2.getMerchants_id()));
			 System.out.println("卖家=" + memberBean3.getCompany_name());
			if (memberBean2.getMemberLevelBean() != null
					&& "1".equals(memberBean2.getMemberLevelBean().getIs_wait_receive())) {
//				VerificationBean verificationBean = codeService.getVerificationSetting(new VerificationBean());
//				VerificationCodeUtils.sendContent(verificationBean, memberBean2.getMember_account(),
//						memberBean2.getMemberLevelBean().getWait_receive_desc()
//								.replace("merchants_name", memberBean3.getCompany_name())
//								.replace("company_name", memberBean2.getCompany_name())
//								.replace("order_no", orderBean2.getOrder_no()));
				messageService.addMessage(
						 new MessageBean().setMessage_type("0").setMember_name(memberBean2.getCompany_name()).setMerchants_name(memberBean3.getCompany_name())
				          .setMember_id(memberBean3.getMember_id()+"").setMessage_title("提醒发货")
				          .setMessage_content(memberBean2.getMemberLevelBean().getWait_assessment_desc()
				          .replace("company_name", memberBean3.getCompany_name())
				          .replace("order_no", orderBean2.getOrder_no())));
			}
		}
		return num;
	}

	/**
	 * 确认发货
	 * 
	 * @param orderBean
	 * @return
	 */
	public int confirmSendOrder(OrderBean orderBean, boolean is_send) {
		int num = orderDao.confirmSendOrder(orderBean);
		if (num > 0) {
			OrderBean orderBean2 = orderDao.getOneOrderDetail(orderBean);
			List<OrderGoodsBean> orderGoodsBean1 = orderDao.getOneOrderGoodsByGoodss(new OrderGoodsBean().setOrder_id(orderBean.getOrder_id()+""));
			MemberBean memberBean2 = memberService
					.getMemberByID(new MemberBean().setMember_id(NumberUtils.Integer(orderBean2.getMember_id())));
			MemberBean memberBean3 = memberService
					.getMemberByMerchants(new MemberBean().setMerchants_id(orderBean2.getMerchants_id()));
			if (is_send) {
				if (memberBean2.getMemberLevelBean() != null
						&& "1".equals(memberBean2.getMemberLevelBean().getIs_wait_receive())) {
//					VerificationBean verificationBean = codeService.getVerificationSetting(new VerificationBean());
//					VerificationCodeUtils.sendContent(verificationBean, memberBean2.getMember_account(),
//							memberBean2.getMemberLevelBean().getWait_receive_desc()
//									.replace("merchants_name", memberBean3.getCompany_name())
//									.replace("company_name", memberBean2.getCompany_name())
//									.replace("order_no", orderBean2.getOrder_no()));
					messageService.addMessage(
							new MessageBean().setMessage_type("1").setMember_name(memberBean2.getCompany_name()).setMerchants_name(memberBean3.getCompany_name())
					          .setMember_id(memberBean2.getMember_id()+"").setMessage_title("确认发货")
					          .setMessage_content(memberBean2.getMemberLevelBean().getWait_receive_desc()
					          .replace("company_name", memberBean2.getCompany_name())
					          .replace("order_no", orderBean2.getOrder_no())));
				}
			}
		}
		return num;
	}

	public List<Map> getGoodsTimeRanking(GoodsProfitBean goodsProfitBean) {
		return orderDao.getGoodsTimeRanking(goodsProfitBean);
	}

	/**
	 * 会员型号成交最低单价
	 * 
	 * @param goodsProfitBean
	 * @return
	 */
	public Map getGoodsUtilPriceMin(GoodsProfitBean goodsProfitBean) {
		return orderDao.getGoodsUtilPriceMin(goodsProfitBean);
	}

	/**
	 * 会员型号成交最高单价
	 * 
	 * @param goodsProfitBean
	 * @return
	 */
	public Map getGoodsUtilPriceMax(GoodsProfitBean goodsProfitBean) {
		return orderDao.getGoodsUtilPriceMax(goodsProfitBean);
	}

	/**
	 * 会员品牌成交额占比
	 * 
	 * @param goodsProfitBean
	 * @return
	 */
	public List<Map> getMerchantsBrandStatistics(GoodsProfitBean goodsProfitBean) {
		return orderDao.getMerchantsBrandStatistics(goodsProfitBean);
	}

	/**
	 * 平台品牌统计
	 * 
	 * @param orderBean
	 * @return
	 */
	public Map getGoodsBrandRanking(GoodsProfitBean goodsProfitBean) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map> maps = orderDao.getGoodsBrandRanking(goodsProfitBean);

		float total_price = orderDao.getGoodsBrandRankingTotal(goodsProfitBean);
		map.put("brandBeans", maps);
		map.put("total_price", total_price + "");
		return map;
	}

	/**
	 * 平台品牌统计
	 * 
	 * @param orderBean
	 * @return
	 */
	public Map getGoodsBrandRankingNo1(GoodsProfitBean goodsProfitBean) {
		Map<String, Object> map = new HashMap<String, Object>();

		Map map1 = orderDao.getGoodsBrandRankingNo1(goodsProfitBean);
		float total_price = orderDao.getGoodsRankingTotal(goodsProfitBean);
		map.put("brandBean", map1);
		map.put("total_price", total_price + "");
		return map;
	}

	/**
	 * 单个品牌统计
	 * 
	 * @param orderBean
	 * @return
	 */
	public Map getOneGoodsBrandStatistics(GoodsProfitBean goodsProfitBean) {
		Map<String, Object> map = new HashMap<String, Object>();
		float total_price = orderDao
				.getGoodsBrandRankingTotal(new GoodsProfitBean().setGoods_id(goodsProfitBean.getGoods_id())
						.setStart_time(goodsProfitBean.getStart_time()).setEnd_time(goodsProfitBean.getEnd_time()));
		float merchant_total_price = orderDao.getGoodsBrandRankingTotal(goodsProfitBean);
		map.put("merchant_total_price", merchant_total_price + "");
		map.put("total_price", total_price + "");
		return map;
	}

	/**
	 * 获得商家的订单列表
	 * 
	 * @return
	 */
	public List<OrderBean> getMerchantsOrders(OrderBean orderBean, PageBean pageBean) {
		List<OrderBean> orderBeans = orderDao.getMerchantsOrders(orderBean, pageBean);
		if (orderBeans != null) {
			for (int i = 0; i < orderBeans.size(); i++) {
				OrderBean orderBean2 = orderBeans.get(i);
				List<OrderGoodsBean> orderGoodsBeans = getOrderGoodss(
						new OrderGoodsBean().setOrder_id(orderBean2.getOrder_id() + ""));
				orderBean2.setOrderGoodsBeans(orderGoodsBeans);
			}
		}
		return orderBeans;
	}
	/**
	 * 
	 * @param orderGoodsBean
	 * @return
	 */
	public List<OrderGoodsBean> getOrderGoodsBean(OrderGoodsBean orderGoodsBean)
	 {
	   List<OrderGoodsBean> orderGoodsBeans = orderDao.getOrderGoodsBean(orderGoodsBean);
	    return orderGoodsBeans;
	 }
	/**
	 * 导出销售订单
	 * 
	 * @param orderBean
	 * @return
	 */
	public List<Object> exportMerchantsOrders(OrderBean orderBean) {
		return orderDao.exportMerchantsOrders(orderBean);
	}

	/**
	 * 拒绝订单
	 * 
	 * @param orderBean
	 * @return
	 */
	public int refuseOrder(OrderBean orderBean) {
		int num = orderDao.refuseOrder(orderBean);
		if (num > 0) {
			List<OrderGoodsBean> orderGoodsBean1 = orderDao.getOneOrderGoodsByGoodss(new OrderGoodsBean().setOrder_id(orderBean.getOrder_id()+""));
			List<OrderGoodsBean> orderGoodsBeans = getOrderGoodss(
					new OrderGoodsBean().setOrder_id(orderBean.getOrder_id() + ""));
			if (orderGoodsBeans != null) {
				for (int i = 0; i < orderGoodsBeans.size(); i++) {
					goodsSupplyDataService.updateSupplyStock(new GoodsSupplyDataBean()
							.setSupply_id(NumberUtils.Integer(orderGoodsBeans.get(i).getSupply_id()))
							.setGoods_stock(orderGoodsBeans.get(i).getGoods_num()));
				}
			}
			OrderBean orderBean2 = orderDao.getOneOrderDetail(orderBean);
			MemberBean memberBean2 = memberService
					.getMemberByID(new MemberBean().setMember_id(NumberUtils.Integer(orderBean2.getMember_id())));
			MemberBean memberBean3 = memberService
					.getMemberByMerchants(new MemberBean().setMerchants_id(orderBean2.getMerchants_id()));
			 System.out.println("leveBean" + memberBean2.getMemberLevelBean());
			if (memberBean2.getMemberLevelBean() != null
					&& "1".equals(memberBean2.getMemberLevelBean().getIs_refuse())) {
//				VerificationBean verificationBean = codeService.getVerificationSetting(new VerificationBean());
//				VerificationCodeUtils.sendContent(verificationBean, memberBean2.getMember_account(),
//						memberBean2.getMemberLevelBean().getRefuse_desc()
//								.replace("company_name", memberBean3.getCompany_name())
//								.replace("nick_name", memberBean2.getNick_name())
//								.replace("order_no", orderBean2.getOrder_no()));
				messageService.addMessage(
						new MessageBean().setMessage_type("1").setMember_name(memberBean2.getCompany_name()).setMerchants_name(memberBean3.getCompany_name())
				          .setMember_id(memberBean2.getMember_id()+"").setMessage_title("拒绝订单")
				          .setMessage_content(memberBean2.getMemberLevelBean().getRefuse_desc()
				          .replace("company_name", memberBean2.getCompany_name())
				          .replace("nick_name", memberBean2.getNick_name())
				          .replace("order_no", orderBean2.getOrder_no())));
			}

			// VerificationCodeUtils.sendContent(verificationBean,memberBean2.getMember_account(),"亲爱的"+memberBean2.getNick_name()+"会员，卖家已经拒绝您"+orderBean2.getOrder_no()+"订单，请及时与卖家沟通。");
		}
		return num;
	}
	public int updateRefuseReason(OrderBean orderBean){
		int num  = orderDao.updateRefuseReason(orderBean);
		return num;
		}
	/**
	 * 通过订单
	 * 
	 * @param orderBean
	 * @return
	 */
	public int acceptOrder(OrderBean orderBean) {
		int num = orderDao.acceptOrder(orderBean);
		System.out.println("num===="+num);
		if (num > 0) {
//			List<OrderGoodsBean> orderGoodsBean1 = orderDao.getOneOrderGoodsByGoodss(new OrderGoodsBean().setOrder_id(orderBean.getOrder_id()+""));
			OrderBean orderBean2 = orderDao.getOneOrderDetail(orderBean);
			MemberBean memberBean2 = memberService
					.getMemberByID(new MemberBean().setMember_id(NumberUtils.Integer(orderBean2.getMember_id())));

			MemberBean memberBean3 = memberService
					.getMemberByMerchants(new MemberBean().setMerchants_id(orderBean2.getMerchants_id()));

			if (memberBean2.getMemberLevelBean() != null
					&& "1".equals(memberBean2.getMemberLevelBean().getIs_accept())) {
//				VerificationBean verificationBean = codeService.getVerificationSetting(new VerificationBean());
//				VerificationCodeUtils.sendContent(verificationBean, memberBean2.getMember_account(),
//						memberBean2.getMemberLevelBean().getAccept_desc()
//								.replace("company_name", memberBean3.getCompany_name())
//								.replace("nick_name", memberBean2.getNick_name())
//								.replace("order_no", orderBean2.getOrder_no()));
				messageService.addMessage(
						new MessageBean().setMessage_type("1").setMember_name(memberBean2.getCompany_name()).setMerchants_name(memberBean3.getCompany_name())
				          .setMember_id(memberBean2.getMember_id()+"").setMessage_title("接受订单")
				          .setMessage_content(memberBean2.getMemberLevelBean().getAccept_desc()
				          .replace("company_name", memberBean2.getCompany_name())
				          .replace("nick_name", memberBean2.getNick_name())
				          .replace("order_no", orderBean2.getOrder_no())));
			}
			// VerificationCodeUtils.sendContent(verificationBean,memberBean2.getMember_account(),"亲爱的"+memberBean2.getNick_name()+"会员，卖家已经同意您"+orderBean2.getOrder_no()+"订单，支付有效时间30分钟之内，超出将自动取消订单，请您及时登录后台确认。");
		}
		return num;
	}

	/**
	 * 月进步冠军
	 */
	public GoodsProfitBean getMerchantsProgressNo1(GoodsProfitBean goodsProfitBean) {
		GoodsProfitBean goodsProfitBean2 = orderDao.getMerchantsProgressNo1(goodsProfitBean);
		if (goodsProfitBean2 != null) {
			MemberBean memberBean = memberService
					.getMemberByMerchants(new MemberBean().setMerchants_id(goodsProfitBean2.getMerchants_id()));
			goodsProfitBean2.setMemberBean(memberBean);
		}
		return goodsProfitBean2;
	}

	/**
	 * 月交易冠军
	 */
	public GoodsProfitBean getMerchantsCountNo1(GoodsProfitBean goodsProfitBean) {
		GoodsProfitBean goodsProfitBean2 = orderDao.getMerchantsCountNo1(goodsProfitBean);
		if (goodsProfitBean2 != null) {
			MemberBean memberBean = memberService
					.getMemberByMerchants(new MemberBean().setMerchants_id(goodsProfitBean2.getMerchants_id()));
			goodsProfitBean2.setMemberBean(memberBean);
		}
		return goodsProfitBean2;
	}

	/**
	 * 月销量冠军
	 */
	public GoodsProfitBean getMerchantsSalesNo1(GoodsProfitBean goodsProfitBean) {
		GoodsProfitBean goodsProfitBean2 = orderDao.getMerchantsSalesNo1(goodsProfitBean);
		if (goodsProfitBean2 != null) {
			MemberBean memberBean = memberService
					.getMemberByMerchants(new MemberBean().setMerchants_id(goodsProfitBean2.getMerchants_id()));
			goodsProfitBean2.setMemberBean(memberBean);
		}
		return goodsProfitBean2;
	}

	/**
	 * 商家销量排行 按月 按商品
	 * 
	 * @param goodsProfitBean
	 * @param pageBean
	 * @return
	 */
	public List<GoodsProfitBean> getMerchantsSalesRanking(GoodsProfitBean goodsProfitBean, PageBean pageBean) {
		List<GoodsProfitBean> goodsProfitBeans = orderDao.getMerchantsSalesRanking(goodsProfitBean, pageBean);
		if (goodsProfitBeans != null) {
			for (int i = 0; i < goodsProfitBeans.size(); i++) {
				GoodsProfitBean goodsProfitBean2 = goodsProfitBeans.get(i);
				MemberBean memberBean = memberService
						.getMemberByMerchants(new MemberBean().setMerchants_id(goodsProfitBean2.getMerchants_id()));
				goodsProfitBean2.setMemberBean(memberBean);

				goodsProfitBean2.setSales_no((i + 1) + (pageBean.getPage() - 1) * 10 + "");
			}
		}
		return goodsProfitBeans;
	}

	/**
	 * 物流信息
	 * 
	 * @return
	 */
	public List<LogisticsBean> getOrderlogistics(LogisticsBean logisticsBean) {
		return orderDao.getOrderlogistics(logisticsBean);
	}

	/**
	 * 获得订单发票内容
	 * 
	 * @param inviseBean
	 * @return
	 */
	public List<InviseBean> getOrderInviseContents(InviseBean inviseBean) {
		return orderDao.getOrderInviseContents(inviseBean);
	}

	/**
	 * 线下订单
	 * 
	 * @param orderLineBean
	 * @return
	 */
	public List<OrderLineBean> getOrderLines(OrderLineBean orderLineBean, PageBean pageBean) {
		return orderDao.getOrderLines(orderLineBean, pageBean);
	}

	public List<BusinessProfitBean> getBusinessProfitByOrderGoods(BusinessProfitBean businessProfitBean) {
		return orderDao.getBusinessProfitByOrderGoods(businessProfitBean);
	}

	/**
	 * 获得订单原因列表
	 * 
	 * @param refundReasonBean
	 * @return
	 */
	public List<RefundReasonBean> getRefundsReasons(RefundReasonBean refundReasonBean) {
		return orderDao.getRefundsReasons(refundReasonBean);
	}

	/**
	 * 用户的退款列表 各个状态统计
	 * 
	 * @param refundReasonBean
	 * @return
	 */
	public Map getMemberRefundCount(RefundBean refundBean) {
		return orderDao.getMemberRefundCount(refundBean);
	}

	/**
	 * 用户的退款列表
	 * 
	 * @param refundBean
	 * @return
	 */
	public List<RefundBean> getMemberRefunds(RefundBean refundBean, PageBean pageBean) {
		List<RefundBean> refundBeans = orderDao.getMemberRefunds(refundBean, pageBean);
		if (refundBeans != null) {
			for (int i = 0; i < refundBeans.size(); i++) {
				MemberBean memberMerchantsBean = memberService
						.getMemberByMerchants(new MemberBean().setMerchants_id(refundBeans.get(i).getMerchants_id()));
				refundBeans.get(i).setMemberMerchantsBean(memberMerchantsBean);

				OrderGoodsBean orderGoodsBean = getOrderGoodssByGoods(
						new OrderGoodsBean().setOrder_id(refundBeans.get(i).getOrder_id())
								.setOrder_goods_id(Integer.valueOf(refundBeans.get(i).getOrder_goods_id())));
				refundBeans.get(i).setOrderGoodsBean(orderGoodsBean);

				List<OrderParameterBean> orderParameterBeans = orderDao.getOrderParameters(
						new OrderParameterBean().setOrder_goods_id(refundBeans.get(i).getOrder_goods_id())
								.setOrder_id(refundBeans.get(i).getOrder_id()));
				refundBeans.get(i).setOrderParameterBeans(orderParameterBeans);

				List<OrderServiceBean> orderServiceBeans = orderDao.getOrderServices(
						new OrderServiceBean().setOrder_goods_id(refundBeans.get(i).getOrder_goods_id())
								.setOrder_id(refundBeans.get(i).getOrder_id()));
				refundBeans.get(i).setOrderServiceBeans(orderServiceBeans);

				GoodsBean goodsBean = goodsServiceI.getOneGoodsDetail(
						new GoodsBean().setGoods_id(Integer.valueOf(refundBeans.get(i).getGoods_id())));
				refundBeans.get(i).setGoodsBean(goodsBean);

				List<RefundImgBean> refundImgBeans = getRefundImgs(
						new RefundImgBean().setRefund_id(refundBeans.get(i).getRefund_id() + ""));
				refundBeans.get(i).setRefundImgBeans(refundImgBeans);
			}
		}
		return refundBeans;
	}

	/**
	 * 单个退款详情
	 * 
	 * @param refundBean
	 * @return
	 */
	public RefundBean getRefundDetail(RefundBean refundBean) {
		RefundBean refundBean2 = orderDao.getRefundDetail(refundBean);
		if (refundBean2 != null) {
			GoodsBean goodsBean = goodsServiceI
					.getOneGoodsDetail(new GoodsBean().setGoods_id(Integer.valueOf(refundBean2.getGoods_id())));
			refundBean2.setGoodsBean(goodsBean);

			List<OrderParameterBean> orderParameterBeans = orderDao.getOrderParameters(new OrderParameterBean()
					.setOrder_goods_id(refundBean2.getOrder_goods_id()).setOrder_id(refundBean2.getOrder_id()));
			refundBean2.setOrderParameterBeans(orderParameterBeans);

			List<OrderServiceBean> orderServiceBeans = orderDao.getOrderServices(new OrderServiceBean()
					.setOrder_goods_id(refundBean2.getOrder_goods_id()).setOrder_id(refundBean2.getOrder_id()));
			refundBean2.setOrderServiceBeans(orderServiceBeans);

			List<RefundImgBean> refundImgBeans = getRefundImgs(
					new RefundImgBean().setRefund_id(refundBean2.getRefund_id() + ""));
			refundBean2.setRefundImgBeans(refundImgBeans);
		}
		return refundBean2;
	}

	/**
	 * 单个退款图片详情
	 * 
	 * @param refundBean
	 * @return
	 */
	public List<RefundImgBean> getRefundImgs(RefundImgBean refundImgBean) {
		return orderDao.getRefundImgs(refundImgBean);
	}

	/**
	 * 查询某个商品的订单详情
	 * 
	 * @param refundBean
	 * @return
	 */
	public RefundBean getRefundByGoods(RefundBean refundBean) {
		return orderDao.getRefundByGoods(refundBean);
	}

	/**
	 * 订单退款
	 * 
	 * @param refundBean
	 * @param files
	 * @return
	 * @throws Exception
	 */
	public int refundOrder(RefundBean refundBean, String[] files) throws Exception {
		int num = insertRefund(refundBean);
		if (num > 0) {
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					int h = insertRefundImg(
							new RefundImgBean().setRefund_id(refundBean.getRefund_id() + "").setRefund_img(files[i]));
					if (h <= 0) {
						throw new Exception("退款图片入库失败");
					}
				}
			}
		}
		return num;
	}

	/**
	 * 订单退款
	 * 
	 * @param refundBean
	 * @param files
	 * @return
	 * @throws Exception
	 */
	public int refundOrder(RefundBean refundBean, List<String> files) throws Exception {
		int num = insertRefund(refundBean);
		if (num > 0) {
			if (files != null) {
				for (int i = 0; i < files.size(); i++) {
					int h = insertRefundImg(new RefundImgBean().setRefund_id(refundBean.getRefund_id() + "")
							.setRefund_img(files.get(i)));
					if (h <= 0) {
						throw new Exception("退款图片入库失败");
					}
				}
			}
		}
		return num;
	}

	/**
	 * 退款信息入库
	 * 
	 * @param refundBean
	 * @param files
	 * @return
	 * @throws Exception
	 */
	public int insertRefund(RefundBean refundBean) throws Exception {
		OrderBean orderBean = orderDao
				.getOneOrderDetail(new OrderBean().setOrder_id(Integer.valueOf(refundBean.getOrder_id())));
		// float refund_price = Float.valueOf(refundBean.getRefund_price()) +
		// Float.valueOf(orderBean.getRefund_price());
		// float refund_give_integral =
		// Float.valueOf(refundBean.getRefund_give_integral())
		// + Float.valueOf(orderBean.getRefund_give_integral());
		// float refund_deduct_integral =
		// Float.valueOf(refundBean.getRefund_deduct_integral())
		// + Float.valueOf(orderBean.getRefund_deduct_integral());
		// int k = orderDao.updateOrderDetail(new
		// OrderBean().setOrder_id(Integer.valueOf(refundBean.getOrder_id()))
		// .setRefund_coupon_id(refundBean.getMember_coupon_id())
		// .setRefund_deduct_integral(refund_deduct_integral + "")
		// .setRefund_give_integral(refund_give_integral +
		// "").setRefund_price(refund_price + ""));
		// if (k <= 0) {
		// throw new Exception("订单状态更新失败");
		// }

		int num = orderDao.insertRefund(refundBean.setRefund_order_no(orderBean.getOrder_pay_no()));
		if (num <= 0) {
			throw new Exception("退款申请失败");
		}
		return num;
	}

	/**
	 * 退款图片信息入库
	 * 
	 * @param refundBean
	 * @param files
	 * @return
	 */
	public int insertRefundImg(RefundImgBean refundImgBean) {
		return orderDao.insertRefundImg(refundImgBean);
	}

	/**
	 * 添加订单
	 * 
	 * @param orderBean
	 * @return
	 * @throws Exception
	 */
	public String insertRechargeOrder(OrderBean orderBean) throws Exception {
		String order_no = TimeUtils.getCurrentTime("yyyyMMddHHmmss") + CreateRandom.createRandom(false, 10);

		return insertRechargeOrder(
				orderBean.setOrder_no(order_no).setOrder_actual_price(orderBean.getOrder_total_price()));
	}

	/**
	 * 添加订单
	 * 
	 * @param orderBean
	 * @return
	 * @throws Exception
	 */
	public Map insertOrder(OrderMerchantsBean orderMerchantsBean) throws Exception {
		AddressBean addressBean = addressService
				.getAddressById(new AddressBean().setAddress_id(Integer.valueOf(orderMerchantsBean.getAddress_id())));// 地址详情
		if (addressBean == null) {
			throw new Exception("地址不存在");
		}
		List<OrderBean> orderBeans = orderMerchantsBean.getOrderBeans();
			LogisticsBean logisticsBeans = orderDao.getLogisticsCompanyDetail(
					new LogisticsBean().setLogistics_pinyin(orderMerchantsBean.getLogistics_pinyin()));
			if (logisticsBeans == null) {
				throw new Exception("暂不支持该物流公司!");

		}
//		List<OrderBean> orderBeans = orderMerchantsBean.getOrderBeans();
		int num = -1;
		String shop_car_ids = orderMerchantsBean.getShopping_car_ids();
		if (shop_car_ids != null && shop_car_ids.length() > 0) {
			String[] car_ids = shop_car_ids.split(",");
			for (int i = 0; i < car_ids.length; i++) {
				int m = shoppingCarService.deleteShoppingCar(new ShoppingCarBean()
						.setCar_id(Integer.valueOf(car_ids[i])).setMember_id(orderMerchantsBean.getMember_id()));
				if (m <= 0) {
					throw new Exception("购物车删除失败");
				}
			}
		}

		// PercentBean percentBean = othersService.getPercent(new
		// PercentBean().setPercent_type("integral"));// 积分抵扣比例
		MemberBean memberBean = memberService
				.getMemberByID(new MemberBean().setMember_id(Integer.valueOf(orderMerchantsBean.getMember_id())));
		if (memberBean == null) {
			throw new Exception("用户不存在");
		}

		double order_total_actual_price = 0;
		if (orderBeans != null) {// 订单列表不为null
			String[] order_ids = new String[orderBeans.size()];
			for (int i = 0; i < orderBeans.size(); i++) {// 订单 按商家 入库
				float order_total_price = 0;// 订单总价
				float order_actual_price = 0;// 实际支付金额
				System.out.println("i=" + i);
				OrderBean orderBean = orderBeans.get(i);
				System.out.println("卖家merchants_id=" + orderBean.getMerchants_id());
				MerchantsBean merchantsBean = merchantsServiceI.getOneMerchantsDetail(
						new MerchantsBean().setMerchants_id(Integer.valueOf(orderBean.getMerchants_id())));
				if (merchantsBean == null) {
					throw new Exception("此商家已下架或已删除");
				}

				MemberBean memberBean2 = memberService
						.getMemberByMerchants(new MemberBean().setMerchants_id(orderBean.getMerchants_id()));
//				if (memberBean2.getMemberLevelBean() != null
//						&& "1".equals(memberBean2.getMemberLevelBean().getIs_confirm())) {
//					VerificationBean verificationBean = codeService.getVerificationSetting(new VerificationBean());
//					VerificationCodeUtils.sendContent(verificationBean, memberBean2.getMember_account(),
//							memberBean2.getMemberLevelBean().getConfirm_desc()
//									.replace("company_name", memberBean2.getCompany_name())
//									.replace("member_name", memberBean.getCompany_name()));
//					messageService.addMessage(
//							new MessageBean().setMember_id(memberBean2.getMember_id() + "").setMessage_title("订单消息")
//									.setMessage_content(memberBean2.getMemberLevelBean().getConfirm_desc()
//											.replace("company_name", memberBean2.getCompany_name()).replace(
//													"member_name", memberBean.getCompany_name())));
//				}

				orderBean.setMemberBean(memberBean);
				orderBean.setMerchantsBean(merchantsBean);
				orderBean.setAddressBean(addressBean);
				String order_no = TimeUtils.getCurrentTime("yyyyMMddHHmmss") + CreateRandom.createRandom(true, 10);
				orderBean.setOrder_state("wait_review");// 订单状态 等待审核
				orderBean.setOrder_no(order_no);// 订单号
				orderBean.setMember_id(orderMerchantsBean.getMember_id());// 用户id
				orderBean.setAddress_id(orderMerchantsBean.getAddress_id());// 地址id

				orderBean.setOrder_type(orderMerchantsBean.getOrder_type());// 订单类型
				orderBean.setMember_group_buy_id(orderMerchantsBean.getMember_group_buy_id());// 用户团购id
				orderBean.setBusiness_id(memberBean.getBusiness_id());
				orderBean.setMerchants_account_id(memberBean.getMerchants_account_id());

//				orderBean.setLogistics_pinyin(orderMerchantsBean.getLogistics_pinyin());
//				orderBean.setLogistics_type(orderMerchantsBean.getLogistics_type());
				orderBean.setLogistics_pinyin(orderBeans.get(i).getLogistics_pinyin());
		        orderBean.setLogistics_type(orderBeans.get(i).getLogistics_type());
//				if (logisticsBean != null) {
//					orderBean.setLogistics_name(logisticsBean.getLogistics_name());
//				}
		        String cur_time = TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
		        orderBean.setCur_time(cur_time);
		        System.out.println("cur_time===" + orderBean.getCur_time());

		        String end_time = TimeUtils.getTimeHoursAfter("yyyy-MM-dd HH:mm:ss", orderBean.getCur_time(), 12);
		        orderBean.setEnd_time(end_time);
		        System.out.println("end_time===" + orderBean.getEnd_time());
		        if (orderBeans != null) { 
		          orderBean.setLogistics_name(((OrderBean)orderBeans.get(i)).getLogistics_name());
		        }

				num = orderDao.insertOrder(orderBean);// 订单入库
				order_ids[i] = orderBean.getOrder_id() + "";

				if (num > 0) {
					List<OrderGoodsBean> orderGoodsBeans = orderBean.getOrderGoodsBeans();
					if ((memberBean2.getMemberLevelBean() != null) && ("1".equals(memberBean2.getMemberLevelBean().getIs_confirm())))
			          {
			            this.messageService.addMessage(
			              new MessageBean().setMessage_type("0").setMember_name(memberBean.getCompany_name()).setMerchants_name(memberBean2.getCompany_name())
			              .setMember_id(memberBean2.getMember_id()+"").setMessage_title("订单消息")
			              .setMessage_content(
			              memberBean2.getMemberLevelBean().getConfirm_desc()
			              .replace("company_name", memberBean2.getCompany_name())
			              .replace("member_name", memberBean2.getCompany_name())
			              .replace("order_no", orderBean.getOrder_no())));

			            System.out.println("给" + memberBean2.getCompany_name() + "发送订单消息成功！");

			            this.messageService.addMessage(
			              new MessageBean().setMessage_type("1").setMember_name(memberBean.getCompany_name()).setMerchants_name(memberBean2.getCompany_name())
			              .setMember_id(memberBean.getMember_id()+"").setMessage_title("订单消息")
			              .setMessage_content(memberBean.getMemberLevelBean().getConfirm_desc_purchase()
			              .replace("company_name", memberBean.getCompany_name())));
			          }
					for (int j = 0; j < orderGoodsBeans.size(); j++) {// 订单商品入库
						OrderGoodsBean orderGoodsBean = orderGoodsBeans.get(j);// 获得商家订单
																				// //
																				// 单个商品
						GoodsBean goodsBean = goodsServiceI.getMerchantsGoodsData(
								new GoodsBean().setGoods_id(Integer.valueOf(orderGoodsBean.getGoods_id())));// 获得商品详情
						if (goodsBean == null || "0".equals(goodsBean.getGoods_state())) {
							throw new Exception("此商品下架或者已删除");
						}
						orderGoodsBean.setGoodsBean(goodsBean);

						GoodsSupplyDataBean goodsSupplyDataBean = goodsSupplyDataService
								.getGoodsSupplyDataByID(new GoodsSupplyDataBean()
										.setSupply_id(NumberUtils.Integer(orderGoodsBean.getSupply_id())));
						if (goodsSupplyDataBean == null) {
							throw new Exception("此商品类型已下架");
						}

						if (goodsSupplyDataBean.getGoods_stock() < orderGoodsBean.getGoods_num() && (Integer.valueOf(goodsSupplyDataBean.getData_categories()).intValue() != 2)) {
							throw new Exception("不好意思!库存不足");
						}

						System.out.print(goodsSupplyDataBean.getGoods_stock() - orderGoodsBean.getGoods_num()
								+ "=========" + goodsSupplyDataBean.getSupply_id());
						num = goodsSupplyDataService.updateSupplyStock(goodsSupplyDataBean
								.setGoods_stock(goodsSupplyDataBean.getGoods_stock() - orderGoodsBean.getGoods_num()));
						if (num <= 0) {
							throw new Exception("库存更新失败");
						}
						orderGoodsBean.setGoods_unit_price(goodsSupplyDataBean.getGoods_unit_price());
						orderGoodsBean.setData_categories(goodsSupplyDataBean.getData_categories());
						orderGoodsBean.setGive_time(goodsSupplyDataBean.getGive_time());
						orderGoodsBean.setMin_buy_num(goodsSupplyDataBean.getMin_buy_num());
						orderGoodsBean.setMin_pack_num(goodsSupplyDataBean.getMin_buy_num());
						double total_price = 0;
						int k = orderDao.insertOrderGoods(orderGoodsBean.setOrder_id(orderBean.getOrder_id() + ""));
						if (k <= 0) {
							throw new Exception("商品入库失败");
						}

						if (orderMerchantsBean.getOrder_type().equals("goods")) {// 正常商品下单
							order_total_price += Float.valueOf(goodsSupplyDataBean.getGoods_unit_price())
									* Float.valueOf(orderGoodsBean.getGoods_num());// 总价加上商品的钱
																					// 乘以
																					// 数量

							order_actual_price += Float.valueOf(goodsSupplyDataBean.getGoods_unit_price())
									* Float.valueOf(orderGoodsBean.getGoods_num());// 总价加上商品的钱
							// 乘以
							// 数量

							total_price += Float.valueOf(goodsSupplyDataBean.getGoods_unit_price())
									* Float.valueOf(orderGoodsBean.getGoods_num());// 总价加上商品的钱
							// 乘以
							// 数量
						}

						num = orderDao.updateOrderGoods(new OrderGoodsBean()
								.setOrder_goods_id(orderGoodsBean.getOrder_goods_id()).setTotal_price(total_price));
						if (num <= 0) {
							throw new Exception("订单商品更新失败");
						}
					}
				}

				double invise_price = 0;
				if ("increment".equals(orderBean.getInvoice_type())) {
					invise_price = order_actual_price * 17 / 100;
					order_actual_price += invise_price;
					order_total_price += invise_price;
				}

				PercentBean percentBean = othersService
						.getPercent(new PercentBean().setPercent_type("min_order_price"));

//				double express_free_price = NumberUtils.Double(logisticsBean.getLogistics_free_price());
				double express_price = 0;
				orderBean.setMin_order_price(percentBean.getPercent_value());
				if (order_actual_price < NumberUtils.Double(percentBean.getPercent_value())) {
					orderBean.setIs_min_order("1");
					orderBean.setIs_free_express("1");// 免邮
				} else {
					orderBean.setIs_min_order("0");

					if ("common".equals(orderMerchantsBean.getLogistics_type())) {
//						express_price = order_actual_price * NumberUtils.Double(logisticsBean.getLogistics_common())
//								/ 100;
					} else if ("fast".equals(orderMerchantsBean.getLogistics_type())) {
//						express_price = order_actual_price * NumberUtils.Double(logisticsBean.getLogistics_fast())
//								/ 100;
					} else if ("no".equals(orderMerchantsBean.getLogistics_type())) {

					} else {
						throw new Exception("不存在此类型!");
					}

//					if (express_free_price > order_actual_price) {// 不免邮
//						order_total_price += express_price;// 订单总价 加上邮费
//						order_actual_price += express_price;
//						orderBean.setIs_free_express("0");// 不免邮
//					} else {
//						orderBean.setIs_free_express("1");// 免邮
//					}
				}

//				orderBean.setExpress_free_price(NumberUtils.KeepDecimal(express_free_price, 2) + "");// 满多少包邮
				orderBean.setExpress_price(NumberUtils.KeepDecimal(express_price, 2) + "");// 邮费

				// /*
				// * 会员折扣 放在邮费计算后面
				// */
				// if ("1".equals(memberBean.getIs_vip())) {// 用户是vip
				// PercentBean percentBean2 = othersService.getPercent(new
				// PercentBean().setPercent_type("member"));
				// if (percentBean2 != null) {
				// float member_discount =
				// Float.valueOf(percentBean2.getPercent_value());
				// order_actual_price = order_actual_price * member_discount;
				// orderBean.setMember_discount(member_discount + "");
				// }
				// }

				order_total_actual_price += order_actual_price;
				int m = updateOrderDetail(
						orderBean.setOrder_total_price(NumberUtils.KeepDecimal(order_total_price, 2) + "")
								.setOrder_actual_price(order_actual_price + "").setInvise_price(invise_price + ""));
				if (m <= 0) {
					throw new Exception("订单更新失败");
				}
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("order_ids", Arrays.toString(order_ids).replaceAll("^\\[| |\\]$", ""));
			map.put("order_actual_price", NumberUtils.KeepDecimal(order_total_actual_price, 2) + "");
			return map;
		} 
			return null;// 未选择商品
		
	}

	/**
	 * 添加订单 何柏瑞
	 * 
	 * @param orderBean
	 * @return
	 * @throws Exception
	 */
	public String insertOrderHBR(OrderMerchantsBean orderMerchantsBean) throws Exception {
		AddressBean addressBean = addressService
				.getAddressById(new AddressBean().setAddress_id(Integer.valueOf(orderMerchantsBean.getAddress_id())));// 地址详情
		if (addressBean == null) {
			throw new Exception("地址不存在");
		}

		List<OrderBean> orderBeans = orderMerchantsBean.getOrderBeans();
		int num = -1;
		String shop_car_ids = orderMerchantsBean.getShopping_car_ids();
		if (shop_car_ids != null && shop_car_ids.length() > 0) {
			String[] car_ids = shop_car_ids.split(",");
			for (int i = 0; i < car_ids.length; i++) {
				int m = shoppingCarService.deleteShoppingCar(new ShoppingCarBean()
						.setCar_id(Integer.valueOf(car_ids[i])).setMember_id(orderMerchantsBean.getMember_id()));
				if (m <= 0) {
					throw new Exception("购物车删除失败");
				}
			}
		}

		PercentBean percentBean = othersService.getPercent(new PercentBean().setPercent_type("integral"));// 积分抵扣比例
		MemberBean memberBean = memberService
				.getMemberByID(new MemberBean().setMember_id(Integer.valueOf(orderMerchantsBean.getMember_id())));

		if (memberBean == null) {
			throw new Exception("用户不存在");
		}

		if (orderBeans != null) {// 订单列表不为null
			String[] order_ids = new String[orderBeans.size()];
			float member_integral_price = (Float.valueOf(memberBean.getIntegral())
					* Float.valueOf(percentBean.getPercent_value())) / 100;

			boolean is_used_coupon = false;// 判断优惠卷是否已经用在某个商家店铺了
			float give_integral_value = 0;

			for (int i = 0; i < orderBeans.size(); i++) {// 订单 按商家 入库
				float order_total_price = 0;// 订单总价
				float express_price = 0;// 订单总邮费

				OrderBean orderBean = orderBeans.get(i);

				MerchantsBean merchantsBean = merchantsServiceI.getOneMerchantsDetail(
						new MerchantsBean().setMerchants_id(Integer.valueOf(orderBean.getMerchants_id())));
				if (merchantsBean == null) {
					throw new Exception("此商家已下架或已删除");
				}

				orderBean.setMerchantsBean(merchantsBean);
				orderBean.setAddressBean(addressBean);

				String order_no = TimeUtils.getCurrentTime("yyyyMMddHHmmss");
				orderBean.setOrder_state("wait_pay");// 订单状态
				orderBean.setOrder_no(order_no);// 订单号
				orderBean.setMember_id(orderMerchantsBean.getMember_id());// 用户id
				orderBean.setAddress_id(orderMerchantsBean.getAddress_id());// 地址id

				orderBean.setOrder_type(orderMerchantsBean.getOrder_type());// 订单类型
				orderBean.setMember_group_buy_id(orderMerchantsBean.getMember_group_buy_id());// 用户团购id

				orderBean.setBusiness_id(memberBean.getBusiness_id());
				orderBean.setMerchants_account_id(memberBean.getMerchants_account_id());
				num = orderDao.insertOrder(orderBean);// 订单入库
				order_ids[i] = orderBean.getOrder_id() + "";

				if (num > 0) {
					List<OrderGoodsBean> orderGoodsBeans = orderBean.getOrderGoodsBeans();
					for (int j = 0; j < orderGoodsBeans.size(); j++) {// 订单商品入库
						OrderGoodsBean orderGoodsBean = orderGoodsBeans.get(j);// 获得商家订单
																				// 单个商品
						GoodsBean goodsBean = goodsServiceI.getOneGoodsDetail(
								new GoodsBean().setGoods_id(Integer.valueOf(orderGoodsBean.getGoods_id())));// 获得商品详情

						if (goodsBean == null) {
							throw new Exception("此商品下架或者已删除");
						}

						if (NumberUtils.Integer(goodsBean.getGoods_stock()) < orderGoodsBean.getGoods_num()) {
							throw new Exception("不好意思!库存不足");
						}

						orderGoodsBean.setGoodsBean(goodsBean);

						double total_price = 0;
						int k = orderDao.insertOrderGoods(orderGoodsBean.setOrder_id(orderBean.getOrder_id() + ""));
						if (k <= 0) {
							throw new Exception("商品入库失败");
						}

						GroupBuyGoodsBean groupBuyGoodsBean = new GroupBuyGoodsBean();
						if ("group_buy".equals(orderMerchantsBean.getOrder_type())) {// 团购下单
							groupBuyGoodsBean = activityService
									.getGoodsGroupBuyByMember(new GroupBuyMemberBean().setMember_group_buy_id(
											Integer.valueOf(orderMerchantsBean.getMember_group_buy_id())));
							goodsBean.setGroup_buy_price(groupBuyGoodsBean.getGroup_buy_price());
						}
						PromotionGoodsBean promotionGoodsBean = new PromotionGoodsBean();
						if ("time_limit".equals(orderMerchantsBean.getOrder_type())) {// 限时促销下单
							promotionGoodsBean = activityService.getOnePromotionGoods(
									new PromotionGoodsBean().setGoods_id(orderGoodsBean.getGoods_id())
											.setPromotion_id(orderGoodsBean.getPromotion_id()));
							if (promotionGoodsBean == null) {
								throw new Exception("此促销商品不存在");
							}

							goodsBean.setPromotion_price(promotionGoodsBean.getPromotion_price());
							goodsBean.setPromotion_goods_id(promotionGoodsBean.getPromotion_goods_id() + "");
						}

						List<OrderParameterBean> orderParameterBeans = orderGoodsBean.getOrderParameterBeans();// 订单商品参数列表
						List<OrderServiceBean> orderServiceBeans = orderGoodsBean.getOrderServiceBeans();// 订单商品
																											// 服务列表
						if (orderParameterBeans != null) {
							for (int l = 0; l < orderParameterBeans.size(); l++) {
								GoodsParameterBean goodsParameterBean = orderDao
										.getOneParameter(new GoodsParameterBean().setParameter_id(
												Integer.valueOf(orderParameterBeans.get(l).getParameter_id())));

								if (goodsParameterBean == null) {
									throw new Exception("无此参数");
								}
								OrderParameterBean orderParameterBean = new OrderParameterBean()
										.setParameter_id(goodsParameterBean.getParameter_id() + "")
										.setParameter_price(goodsParameterBean.getParameter_price());

								int m = orderDao.insertOrderParameter(
										orderParameterBean.setOrder_id(orderBean.getOrder_id() + "")
												.setOrder_goods_id(orderGoodsBean.getOrder_goods_id() + ""));
								if (m <= 0) {
									throw new Exception("商品参数入库失败");
								}
								order_total_price += Float.valueOf(orderParameterBean.getParameter_price())
										* Float.valueOf(orderGoodsBean.getGoods_num());// 总价加上参数的钱
																						// 乘以
																						// 数量
								total_price += Float.valueOf(orderParameterBean.getParameter_price())
										* Float.valueOf(orderGoodsBean.getGoods_num());
							}
						}
						if (orderServiceBeans != null) {
							for (int l = 0; l < orderServiceBeans.size(); l++) {

								GoodsServiceBean goodsServiceBean = orderDao.getOneService(new GoodsServiceBean()
										.setService_id(Integer.valueOf(orderServiceBeans.get(l).getService_id())));

								if (goodsServiceBean == null) {
									throw new Exception("无此服务");
								}

								OrderServiceBean orderServiceBean = new OrderServiceBean()
										.setService_id(goodsServiceBean.getService_id() + "")
										.setService_price(goodsServiceBean.getService_price());

								int m = orderDao
										.insertOrderService(orderServiceBean.setOrder_id(orderBean.getOrder_id() + "")
												.setOrder_goods_id(orderGoodsBean.getOrder_goods_id() + ""));
								if (m <= 0) {
									throw new Exception("商品服务入库失败");
								}
								order_total_price += Float.valueOf(orderServiceBean.getService_price())
										* Float.valueOf(orderGoodsBean.getGoods_num());// 总价加上服务的钱乘以数量
								total_price += Float.valueOf(orderServiceBean.getService_price())
										* Float.valueOf(orderGoodsBean.getGoods_num());// 总价加上服务的钱乘以数量
							}
						}

						if ("1".equals(goodsBean.getIs_give_integral())) {
							give_integral_value += Float.valueOf(goodsBean.getGive_integral_value())
									* Float.valueOf(orderGoodsBean.getGoods_num());// 赠送积分
																					// 乘以数量
						}

						if (goodsBean.getIs_express().equals("0")) {// 计算邮费
							express_price += Float.valueOf(goodsBean.getExpress_price());
						}

						if (orderMerchantsBean.getOrder_type().equals("goods")) {// 正常商品下单
							order_total_price += Float.valueOf(goodsBean.getGoods_now_price())
									* Float.valueOf(orderGoodsBean.getGoods_num());// 总价加上商品的钱
																					// 乘以
																					// 数量
							total_price += Float.valueOf(goodsBean.getGoods_now_price())
									* Float.valueOf(orderGoodsBean.getGoods_num());// 总价加上商品的钱
							// 乘以
							// 数量
						} else if (orderMerchantsBean.getOrder_type().equals("group_buy")) {// 团购下单
							order_total_price += Float.valueOf(groupBuyGoodsBean.getGroup_buy_price())
									* Float.valueOf(orderGoodsBean.getGoods_num());// 总价加上商品的团购价
																					// 乘以
																					// 数量
							total_price += Float.valueOf(groupBuyGoodsBean.getGroup_buy_price())
									* Float.valueOf(orderGoodsBean.getGoods_num());// 总价加上商品的团购价
							// 乘以
							// 数量
						} else if (orderMerchantsBean.getOrder_type().equals("time_limit")) {// 限时促销下单
							order_total_price += Float.valueOf(promotionGoodsBean.getPromotion_price())
									* Float.valueOf(orderGoodsBean.getGoods_num());// 总价加上商品的促销价
																					// 乘以
																					// 数量
							total_price += Float.valueOf(promotionGoodsBean.getPromotion_price())
									* Float.valueOf(orderGoodsBean.getGoods_num());// 总价加上商品的促销价
							// 乘以
							// 数量
						}

						JFUtils.insertProfit(orderGoodsBean, memberBean, total_price, othersService, orderDao);// 收益分配

						num = orderDao.updateOrderGoods(
								new OrderGoodsBean().setOrder_goods_id(orderGoodsBean.getOrder_goods_id())
										.setTotal_price(orderGoodsBean.getTotal_price()));
						if (num <= 0) {
							throw new Exception("订单商品更新失败");
						}
					}
				}

				/*
				 * 邮费计算
				 */
				float express_free_price = Float.valueOf(merchantsBean.getExpress_free_price());// 商家设置的订单满多少免邮
				if (express_free_price > express_price) {// 满邮 大于 订单的邮费 则
															// 订单总价需加上 邮费
					order_total_price += express_price;// 订单总价 加上邮费
					orderBean.setIs_free_express("0");// 不免邮
				} else {
					orderBean.setIs_free_express("1");// 免邮
				}

				orderBean.setExpress_free_price(express_free_price + "");// 满多少包邮
				orderBean.setExpress_price(express_price + "");// 邮费

				if (num > 0) {// 计算活动的钱 在加上邮费之后
					List<OrderGoodsBean> orderGoodsBeans = orderBean.getOrderGoodsBeans();
					for (int j = 0; j < orderGoodsBeans.size(); j++) {// 订单商品入库
						OrderGoodsBean orderGoodsBean = orderGoodsBeans.get(j);// 获得商家订单

						List<ActivityBean> activityBeans = activityService.getGoodsActivity(
								new ActivityBean().setGoods_id(Integer.valueOf(orderGoodsBean.getGoods_id())));// 商品参加的活动列表

						ActivityBean activityBean = null;
						if (activityBeans != null) {
							for (int l = 0; l < activityBeans.size(); l++) {// 一个商品
																			// 只能参加一个活动
								ActivityBean activityBean2 = activityBeans.get(l);
								if (activityBean == null) {
									if ("give".equals(activityBean2.getActivity_type()) && orderGoodsBean
											.getGoods_num() >= activityBean2.getGiveBean().getGive_need_count()) {
										activityBean = activityBean2;
									} else if ("reduce".equals(activityBean2.getActivity_type())
											&& order_total_price >= activityBean2.getReduceBean()
													.getReduce_need_price()) {
										activityBean = activityBean2;
									} else if ("gift".equals(activityBean2.getActivity_type())
											&& order_total_price >= activityBean2.getGiftBean().getGift_need_price()) {
										activityBean = activityBean2;
									}
								} else if ("give".equals(activityBean2.getActivity_type())) {
									if (activityBean2.getGiveBean() != null
											&& activityBean2.getGiveBean().getGive_need_count() > activityBean
													.getGiveBean().getGive_need_count()
											&& orderGoodsBean.getGoods_num() >= activityBean2.getGiveBean()
													.getGive_need_count()) {
										activityBean = activityBean2;
									}
								} else if ("reduce".equals(activityBean2.getActivity_type())) {
									if (activityBean2.getReduceBean() != null
											&& activityBean2.getReduceBean().getReduce_need_price() > activityBean
													.getReduceBean().getReduce_need_price()
											&& order_total_price >= activityBean2.getReduceBean()
													.getReduce_need_price()) {
										activityBean = activityBean2;
									}
								} else if ("gift".equals(activityBean2.getActivity_type())) {
									if (activityBean2.getGiftBean() != null
											&& activityBean2.getGiftBean().getGift_need_price() > activityBean
													.getGiftBean().getGift_need_price()
											&& order_total_price >= activityBean2.getGiftBean().getGift_need_price()) {
										activityBean = activityBean2;
									}
								}
							}
						}

						if (activityBean != null) {// 该商品 参加了活动
							if ("give".equals(activityBean.getActivity_type())) {
								orderGoodsBean.setActivity_type("give");
								orderGoodsBean.setGive_need_count(activityBean.getGiveBean().getGive_need_count());
								orderGoodsBean.setGive_count(activityBean.getGiveBean().getGive_count());
							} else if ("reduce".equals(activityBean.getActivity_type())) {
								orderGoodsBean.setActivity_type("reduce");
								orderGoodsBean
										.setReduce_need_price(activityBean.getReduceBean().getReduce_need_price());
								orderGoodsBean.setReduce_price(activityBean.getReduceBean().getReduce_price());
								order_total_price -= activityBean.getReduceBean().getReduce_price();
							} else if ("gift".equals(activityBean.getActivity_type())) {
								orderGoodsBean.setActivity_type("gift");
								orderGoodsBean.setGift_desc(activityBean.getGiftBean().getGift_desc());
							}
						}

						num = orderDao
								.updateOrderGoods(orderGoodsBean.setOrder_goods_id(orderGoodsBean.getOrder_goods_id())
										.setTotal_price(orderGoodsBean.getTotal_price()));
						if (num <= 0) {
							throw new Exception("订单商品更新失败");
						}
					}
				}

				DistributionUtils.insertProfit(memberBean, new OrderBean().setOrder_id(orderBean.getOrder_id())
						.setOrder_total_price(order_total_price + ""), memberService, othersService, orderDao);// 何柏瑞的分成

				/*
				 * 会员折扣 放在邮费计算后面
				 */
				if ("1".equals(memberBean.getIs_vip())) {// 用户是vip
					PercentBean percentBean2 = othersService.getPercent(new PercentBean().setPercent_type("member"));
					if (percentBean2 != null) {
						float member_discount = Float.valueOf(percentBean2.getPercent_value());
						order_total_price = order_total_price * member_discount;
						orderBean.setMember_discount(member_discount + "");
					}
				}

				/*
				 * 计算优惠券 需放在邮费和折扣计算后面
				 */
				if (!is_used_coupon) {// 此次下单 优惠卷未用过
					if (orderMerchantsBean.getMember_coupon_id() != null
							&& !orderMerchantsBean.getMember_coupon_id().equals("")) {// 用户选择优惠卷了
						CouponBean couponBean = couponService.getCouponByMemberCouponId(
								new CouponBean().setMember_coupon_id(orderMerchantsBean.getMember_coupon_id()));
						if (couponBean == null) {
							throw new Exception("此优惠券不可用状态");
						}

						float coupon_full_price = Float.valueOf(couponBean.getCoupon_full_price());// 满多少可用优惠券
						if (coupon_full_price <= order_total_price) {// 满足优惠券
																		// 满额条件
							order_total_price -= Float.valueOf(couponBean.getCoupon_price());// 订单总价
																								// 减去优惠卷的钱
							is_used_coupon = true;
						}

						orderBean.setMember_coupon_id(orderMerchantsBean.getMember_coupon_id());
						orderBean.setCoupon_full_price(coupon_full_price + "");
						orderBean.setCoupon_price(couponBean.getCoupon_price());

						int k = couponService.updateCouponState(couponBean.setCoupon_state("already_used"));// 用完
																											// 更新优惠卷状态
						if (k <= 0) {
							throw new Exception("优惠卷使用失败");
						}
					}

				}

				/*
				 * 积分抵扣计算
				 */
				orderBean.setGive_integral_value(NumberUtils.KeepDecimal(give_integral_value, 2) + "");
				orderBean.setIs_deduct_integral(orderMerchantsBean.getIs_deduct_integral());
				orderBean.setDeduct_integral_percent(percentBean.getPercent_value());// 抵扣的积分的百分比
				if ("1".equals(orderMerchantsBean.getIs_deduct_integral())) {// 是否选择抵扣积分
					if (member_integral_price >= order_total_price) {// 用户积分抵扣的钱大于等于订单的钱
						orderBean.setDeduct_integral_value(NumberUtils.KeepDecimal(order_total_price * 100, 2) + "");
						orderBean.setDeduct_integral_price(NumberUtils.KeepDecimal(order_total_price, 2) + "");
						member_integral_price = member_integral_price - order_total_price;// 用户积分减去订单的钱
						order_total_price = 0;// 订单实付金额为0
						orderBean.setOrder_state("wait_send");// 如果全用积分付款
																// 则订单直接变成待发货状态

						float member_integral = 0;// 全额付款 则把积分赠送
						member_integral += Float.valueOf(memberBean.getIntegral());
						int k = memberService.updateMemberDetail(
								new MemberBean().setMember_id(Integer.valueOf(orderMerchantsBean.getMember_id()))
										.setIntegral((member_integral + give_integral_value) + ""));
						if (k <= 0) {
							throw new Exception("客户赠送积分失败");
						}
					} else {// 用户积分抵扣的钱小于订单的钱
						orderBean
								.setDeduct_integral_value(NumberUtils.KeepDecimal(member_integral_price * 100, 2) + "");
						orderBean.setDeduct_integral_price(NumberUtils.KeepDecimal(member_integral_price, 2) + "");
						order_total_price = order_total_price - member_integral_price;// 订单总价减去用户积分的钱
						member_integral_price = 0;// 用户积分变为0
						orderBean.setOrder_state("wait_pay");
					}
				}

				int m = orderDao.updateOrderDetail(
						orderBean.setOrder_total_price(NumberUtils.KeepDecimal(order_total_price, 2) + ""));
				if (m <= 0) {
					throw new Exception("订单更新失败");
				}

			}

			// if ("1".equals(orderMerchantsBean.getIs_deduct_integral())) {//
			// 是否选择抵扣积分
			// memberService.updateMemberDetail(
			// new
			// MemberBean().setMember_id(Integer.valueOf(orderMerchantsBean.getMember_id()))
			// .setIntegral((member_integral_price * 100) + ""));// 积分算完
			// // 更新用户积分
			// }

			if (orderMerchantsBean.getMember_coupon_id() != null
					&& !orderMerchantsBean.getMember_coupon_id().equals("")) {// 用户选择优惠卷了
				if (!is_used_coupon) {// 优惠卷满额要求 没有订单满足条件
					throw new Exception("订单金额未满足优惠卷满额要求");
				}
			}

			return Arrays.toString(order_ids).replaceAll("^\\[| |\\]$", "");
		} else {
			return null;// 未选择商品
		}
	}

	/**
	 * 获得订单列表
	 * 
	 * @return
	 */
	public List<OrderBean> getOrdersByPayNo(OrderBean orderBean) {
		return orderDao.getOrdersByPayNo(orderBean);
	}

	/**
	 * 订单发票内容
	 * 
	 * @param orderBean
	 * @param pageBean
	 * @return
	 */
	public List<OrderBean> getInviseOrders(OrderBean orderBean, PageBean pageBean) {
		return orderDao.getInviseOrders(orderBean, pageBean);
	}

	/**
	 * 导出采购订单
	 * 
	 * @param orderBean
	 * @return
	 */
	public List<Object> exportOrders(OrderBean orderBean) {
		return orderDao.exportOrders(orderBean);
	}

	/**
	 * 获得订单列表
	 * 
	 * @return
	 */
	public List<OrderBean> getOrders(OrderBean orderBean, PageBean pageBean) {
		List<OrderBean> orderBeans = orderDao.getOrders(orderBean, pageBean);

		String cur_time = TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
		for (int i = 0; i < orderBeans.size(); i++) {
			OrderBean orderBean1 = orderBeans.get(i);
			orderBean1.setCur_time(cur_time);
			if ("wait_review".equals(orderBean1.getOrder_state())) {
				String end_time = TimeUtils.getTimeHoursAfter("yyyy-MM-dd HH:mm:ss", orderBean1.getCreate_time(), 12);
				orderBean1.setEnd_time(end_time);
				try {
					int refuse_time_mis = TimeUtils.getSecCompareDate(end_time, cur_time, "yyyy-MM-dd HH:mm:ss");
					orderBean1.setRefuse_time_mis(refuse_time_mis);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			if ("wait_pay".equals(orderBean1.getOrder_state())) {
				String end_time = TimeUtils.getTimeMinuteAfter("yyyy-MM-dd HH:mm:ss", orderBean1.getAccept_time(), 2);
				orderBean1.setEnd_time(end_time);
			}

			List<OrderGoodsBean> orderGoodsBeans = getOrderGoodss(
					new OrderGoodsBean().setOrder_id(orderBean1.getOrder_id() + ""));
			orderBean1.setOrderGoodsBeans(orderGoodsBeans);

			MemberBean memberBean = memberService
					.getMemberByMerchants(new MemberBean().setMerchants_id(orderBean1.getMerchants_id()));
			orderBean1.setMemberMerchantsBean(memberBean);

			MerchantsBean merchantsBean = merchantsServiceI.getOneMerchantsDetail(
					new MerchantsBean().setMerchants_id(Integer.valueOf(orderBean1.getMerchants_id())));
			orderBean1.setMerchantsBean(merchantsBean);
		}
		return orderBeans;
	}

	/**
	 * 获得订单列表 每个状态统计
	 * 
	 * @param orderBean
	 * @return
	 */
	public Map getOrdersCount(OrderBean orderBean) {
		return orderDao.getOrdersCount(orderBean);
	}

	/**
	 * 获得订单的物流详情列表
	 * 
	 * @param map
	 * @return
	 */
	public List<LogisticsDetailBean> getOrderLogisticsDetails(LogisticsDetailBean logisticsDetailBean) {
		List<LogisticsDetailBean> logisticsDetailBeans = orderDao.getOrderLogisticsDetails(logisticsDetailBean);

		return logisticsDetailBeans;
	}

	/**
	 * 单个订单详情
	 * 
	 * @param orderBean
	 * @return
	 */
	public OrderBean getOneOrderDetail(OrderBean orderBean) {
		OrderBean orderBean1 = orderDao.getOneOrderDetail(orderBean);
		String cur_time = TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
		if (orderBean1 != null) {
			orderBean1.setCur_time(cur_time);
			if ("wait_review".equals(orderBean1.getOrder_state())) {
				String end_time = TimeUtils.getTimeHoursAfter("yyyy-MM-dd HH:mm:ss", orderBean1.getCreate_time(), 12);
				orderBean1.setEnd_time(end_time);
				try {
					int refuse_time_mis = TimeUtils.getSecCompareDate(end_time, cur_time, "yyyy-MM-dd HH:mm:ss");
					orderBean1.setRefuse_time_mis(refuse_time_mis);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			if ("wait_pay".equals(orderBean1.getOrder_state())) {
				String end_time = TimeUtils.getTimeMinuteAfter("yyyy-MM-dd HH:mm:ss", orderBean1.getAccept_time(), 2);
				orderBean1.setEnd_time(end_time);
			}

			HostBean hostBean = othersService.getHost(new HostBean().setHost_type("1"));
			orderBean1.setHostBean(hostBean);

			List<OrderGoodsBean> orderGoodsBeans = getOrderGoodss(
					new OrderGoodsBean().setOrder_id(orderBean1.getOrder_id() + ""));
			orderBean1.setOrderGoodsBeans(orderGoodsBeans);
			for (int j = 0; j < orderGoodsBeans.size(); j++) {
				List<GoodsImgBean> goodsImgBeans = goodsServiceI
						.getGoodsImgs(new GoodsImgBean().setGoods_id(orderGoodsBeans.get(j).getGoods_id()));
				orderGoodsBeans.get(j).setGoodsImgBeans(goodsImgBeans);
			}

			if (orderBean1.getLogistics_pinyin() != null && orderBean1.getLogistics_no() != null
					&& !"".equals(orderBean1.getLogistics_pinyin()) && !"".equals(orderBean1.getLogistics_no())) {
				List<LogisticsDetailBean> logisticsDetailBeans = Kuaidi100Utils
						.getLogistics(orderBean1.getLogistics_no(), orderBean1.getLogistics_pinyin());// orderDao.getOrderLogisticsDetails(new
																										// LogisticsDetailBean());
				orderBean1.setLogisticsDetailBeans(logisticsDetailBeans);
			}

			MemberBean memberMerchantsBean = memberService
					.getMemberByMerchants(new MemberBean().setMerchants_id(orderBean1.getMerchants_id()));
			orderBean1.setMemberMerchantsBean(memberMerchantsBean);

			MerchantsBean merchantsBean = merchantsServiceI.getOneMerchantsDetail(
					new MerchantsBean().setMerchants_id(Integer.valueOf(orderBean1.getMerchants_id())));
			orderBean1.setMerchantsBean(merchantsBean);
		}
		return orderBean1;
	}

	/**
	 * 单个订单详情
	 * 
	 * @param orderBean
	 * @return
	 */
	public OrderBean getOneOrderDetailZSSG(OrderBean orderBean) {
		return orderDao.getOneOrderDetail(orderBean);
	}

	/**
	 * 
	 * @param orderBean
	 * @return
	 */
	public int payOrderOffline(OrderBean orderBean) {
		return orderDao.payOrderOffline(orderBean);
	}

	/**
	 * 获得订单商品列表
	 * 
	 * @return
	 */
	public List<OrderGoodsBean> getOrderGoodss(OrderGoodsBean orderGoodsBean) {
		List<OrderGoodsBean> orderGoodsBeans = orderDao.getOrderGoodss(orderGoodsBean);
		// for (int j = 0; j < orderGoodsBeans.size(); j++) {
		// List<GoodsImgBean> goodsImgBeans = goodsServiceI
		// .getGoodsImgs(new
		// GoodsImgBean().setGoods_id(orderGoodsBeans.get(j).getGoods_id()));
		// orderGoodsBeans.get(j).setGoodsImgBeans(goodsImgBeans);
		// }
		return orderGoodsBeans;
	}

	/**
	 * 获得单个订单商品
	 * 
	 * @return
	 */
	public OrderGoodsBean getOrderGoodssByGoods(OrderGoodsBean orderGoodsBean) {
		OrderGoodsBean orderGoodsBean2 = orderDao.getOrderGoodssByGoods(orderGoodsBean);

		List<OrderParameterBean> orderParameterBeans = orderDao
				.getOrderParameters(new OrderParameterBean().setOrder_id(orderGoodsBean.getOrder_id() + "")
						.setOrder_goods_id(orderGoodsBean2.getOrder_goods_id() + ""));
		orderGoodsBean2.setOrderParameterBeans(orderParameterBeans);

		List<OrderServiceBean> orderServiceBeans = orderDao
				.getOrderServices(new OrderServiceBean().setOrder_id(orderGoodsBean.getOrder_id() + "")
						.setOrder_goods_id(orderGoodsBean2.getOrder_goods_id() + ""));
		orderGoodsBean2.setOrderServiceBeans(orderServiceBeans);

		return orderGoodsBean2;
	}

	/**
	 * 取消订单
	 * 
	 * @param orderBean
	 * @return
	 * @throws Exception
	 */
	public int cancelOrder(OrderBean orderBean) throws Exception {
		int num = orderDao.cancelOrder(orderBean);
		if (num > 0) {
			MemberBean memberBean = memberService
					.getMemberByID(new MemberBean().setMember_id(Integer.valueOf(orderBean.getMember_id())));
			float member_integral = Float.valueOf(memberBean.getIntegral());// 用户现有积分
			if ("1".equals(orderBean.getIs_deduct_integral())) {// 如果抵扣积分了
																// 要先返还积分
				member_integral += Float.valueOf(orderBean.getDeduct_integral_value());
			}

			int k = memberService.updateMemberDetail(new MemberBean()
					.setMember_id(Integer.valueOf(orderBean.getMember_id())).setIntegral(member_integral + ""));
			if (k <= 0) {
				throw new Exception("客户积分返还失败");
			}

			if (orderBean.getMember_coupon_id() != null && !orderBean.getMember_coupon_id().equals("")) {// 退款优惠卷
				int h = couponService.updateCouponState(new CouponBean()
						.setMember_coupon_id(orderBean.getMember_coupon_id()).setCoupon_state("not_used"));
				if (h <= 0) {
					throw new Exception("客户优惠卷返还失败");
				}
			}
		}
		return num;
	}
	/**
	 * 恢复订单
	 * @param orderBean
	 * @return
	 * @throws Exception
	 */
	public OrderBean recoverylOrder(OrderBean orderBean)
		    throws Exception
		  {
		    OrderBean orderBeans = orderDao.getOneOrderDetail(orderBean);

		    return orderBeans;
		  }

	/**
	 * 取消订单
	 * 
	 * @param orderBean
	 * @return
	 * @throws Exception
	 */
	public int cancelPayOrder(OrderBean orderBean) throws Exception {
		int num = orderDao.cancelOrder(orderBean);
		if (num > 0) {
			MemberBean memberBean = memberService
					.getMemberByID(new MemberBean().setMember_id(Integer.valueOf(orderBean.getMember_id())));
			float member_integral = Float.valueOf(memberBean.getIntegral());// 用户现有积分
			if ("1".equals(orderBean.getIs_deduct_integral())) {// 如果抵扣积分了
																// 要先返还积分
				member_integral += Float.valueOf(orderBean.getDeduct_integral_value());
			}

			int k = memberService.updateMemberDetail(new MemberBean()
					.setMember_id(Integer.valueOf(orderBean.getMember_id())).setIntegral(member_integral + ""));
			if (k <= 0) {
				throw new Exception("客户积分返还失败");
			}

			if (orderBean.getMember_coupon_id() != null && !orderBean.getMember_coupon_id().equals("")) {// 退款优惠卷
				int h = couponService.updateCouponState(new CouponBean()
						.setMember_coupon_id(orderBean.getMember_coupon_id()).setCoupon_state("not_used"));
				if (h <= 0) {
					throw new Exception("客户优惠卷返还失败");
				}
			}

			PingSettingBean pingSettingBean = othersService.getPingSetting(new PingSettingBean().setPing_type("1"));
			Pingpp.apiKey = pingSettingBean.getPing_app_key();
			Pingpp.privateKeyPath = getClass().getResource("/").getFile().toString() + "/rsa_private_key.pem";
			Charge ch = Charge.retrieve(orderBean.getOrder_pay_no());// ch_id
																		// 是已付款的订单号
			Map<String, Object> refundMap = new HashMap<String, Object>();
			refundMap.put("amount", 1);// orderBean.getOrder_actual_price()
			refundMap.put("description", "退款");
			Refund re = ch.getRefunds().create(refundMap);

			System.out.println(new Gson().toJson(re));
		}
		return num;
	}

	/**
	 * 更新订单信息
	 * 
	 * @param orderBean
	 * @return
	 */
	public int updateOrderDetail(OrderBean orderBean) {
		return orderDao.updateOrderDetail(orderBean);
	}

	/**
	 * 删除订单
	 * 
	 * @return
	 */
	public int deleteOrder(OrderBean orderBean) {
		return orderDao.deleteOrder(orderBean);
	}
	/**
	 * 批量删除订单
	 * @param orderBean
	 * @return
	 */
	public int deleteOrders(OrderBean orderBean) {
		return orderDao.deleteOrders(orderBean);
	}
	/**
	 * 删除订单中的型号
	 * @param orderGoodsBean
	 * @return
	 */
	public int deleteGoodsByOrder(OrderGoodsBean orderGoodsBean)
	  {
	    int num = orderDao.deleteGoodsByOrder(orderGoodsBean);
	    return num;
	  }
	/**
	 * 根据某个型号获得订单ID
	 * @param orderGoodsBean
	 * @return
	 */
	public OrderGoodsBean getOrderId(OrderGoodsBean orderGoodsBean) {
	    OrderGoodsBean orderGoodsBean1 = orderDao.getOrderId(orderGoodsBean);
	    return orderGoodsBean1;
	  }

	/**
	 * 付款订单改变状态
	 * 
	 * @param orderBean
	 * @return
	 * @throws Exception
	 */
	public int paySuccessOrder(String[] order_ids, String pay_way, String password) throws Exception {
		int num = 0;
		String order_state = "wait_send";
		String member_id = "";

		float order_actual_price = 0;
		MemberBean memberBean = null;

		for (int i = 0; i < order_ids.length; i++) {
			OrderBean orderBean2 = getOneOrderDetail(new OrderBean().setOrder_id(Integer.valueOf(order_ids[i])));
			if ("recharge".equals(orderBean2.getOrder_source_way())) {
				num = orderDao.payOrder(new OrderBean().setOrder_id(Integer.valueOf(order_ids[i])).setOrder_state("end")
						.setPay_way(pay_way == null ? "online" : pay_way));
			} else {
				if (!"wait_pay".equals(orderBean2.getOrder_state())) {
					throw new Exception("此订单已付款");
				}

				order_actual_price += NumberUtils.Float(orderBean2.getOrder_actual_price());

				member_id = orderBean2.getMember_id();
				memberBean = memberService
						.getMemberByID(new MemberBean().setMember_id(Integer.valueOf(orderBean2.getMember_id())));
				
				MemberBean memberBean1 = memberService
				          .getMemberByMerchants(new MemberBean().setMerchants_id(orderBean2.getMerchants_id()));
				
				OrderGoodsBean orderGoodsBean1 = orderDao.getOneOrderGoodsByGoods(new OrderGoodsBean().setOrder_id(orderBean2.getOrder_id()+""));

				String profit_time = TimeUtils.getAchTime(TimeUtils.getCurrentTime("yyyy-MM-01 00:00:00"));

				List<OrderGoodsBean> orderGoodsBeans = getOrderGoodss(new OrderGoodsBean().setOrder_id(order_ids[i]));
				for (int j = 0; j < orderGoodsBeans.size(); j++) {
					OrderGoodsBean orderGoodsBean = orderGoodsBeans.get(j);
					GoodsBean goodsBean = goodsServiceI.getMerchantsGoodsData(
							new GoodsBean().setGoods_id(Integer.valueOf(orderGoodsBean.getGoods_id())));
					int year_sales = goodsBean.getYear_sales() + orderGoodsBean.getGoods_num();
					int day_sales = goodsBean.getDay_sales() + orderGoodsBean.getGoods_num();
					int month_sales = goodsBean.getMonth_sales() + orderGoodsBean.getGoods_num();
					int goods_stock = NumberUtils.Integer(goodsBean.getGoods_stock()) - orderGoodsBean.getGoods_num();
					GoodsProfitBean goodsProfitBean = goodsServiceI
							.getGoodsProfitCurMonth(new GoodsProfitBean().setGoods_id(orderGoodsBean.getGoods_id())
									.setOrder_goods_id(orderGoodsBean.getOrder_goods_id() + "")
									.setProfit_time(profit_time).setProfit_type("total"));
					if (goodsProfitBean != null) {
						num = goodsServiceI.updateGoodsProfit(
								new GoodsProfitBean().setGoods_profit_id(goodsProfitBean.getGoods_profit_id())
										.setProfit_money(orderGoodsBean.getTotal_price() + ""));
						if (num <= 0) {
							throw new Exception("商品收益记录更新失败");
						}
					} else {
						num = goodsServiceI.insertGoodsProfit(
								new GoodsProfitBean().setGoods_id(orderGoodsBean.getGoods_id()).setMember_id(member_id)
										.setOrder_goods_id(orderGoodsBean.getOrder_goods_id() + "")
										.setOrder_id(orderBean2.getOrder_id() + "").setProfit_time(profit_time)
										.setProfit_money(orderGoodsBean.getTotal_price() + "").setProfit_type("total")
										.setParent_id(goodsBean.getParent_id()));
						if (num <= 0) {
							throw new Exception("商品收益记录添加失败");
						}
					}

					num = goodsServiceI
							.insertGoodsProfit(new GoodsProfitBean().setGoods_id(orderGoodsBean.getGoods_id())
									.setMember_id(member_id).setOrder_goods_id(orderGoodsBean.getOrder_goods_id() + "")
									.setOrder_id(orderBean2.getOrder_id() + "").setProfit_time(profit_time)
									.setProfit_money(orderGoodsBean.getTotal_price() + "").setProfit_type("detail")
									.setMerchants_id(orderGoodsBean.getMerchants_id())
									.setParent_id(goodsBean.getParent_id() == null ? "-1" : goodsBean.getParent_id()));
					if (num <= 0) {
						throw new Exception("商品收益记录更新失败");
					}
					int k = goodsServiceI2.updateGoodsDetail(new GoodsBean().setGoods_id(goodsBean.getGoods_id())
							.setYear_sales(year_sales).setDay_sales(day_sales).setMonth_sales(month_sales)
							.setGoods_stock(goods_stock + ""));
					if (k <= 0) {
						/*
						 * GoodsSupplyDataBean supplyBean =
						 * supplyService.getGoodsSupplyDataByID(new
						 * GoodsSupplyDataBean()
						 * .setSupply_id(Integer.valueOf(orderGoodsBean.
						 * getSupply_id()))); if(supplyBean!=null) { int
						 * supply_stock = supplyBean.getGoods_stock() -
						 * orderGoodsBean.getGoods_num();
						 * supplyService.updateSupplyStock(new
						 * GoodsSupplyDataBean()
						 * .setSupply_id(Integer.valueOf(orderGoodsBean.
						 * getSupply_id())) .setGoods_stock(supply_stock)); }
						 */
						throw new Exception("更新商品信息失败");
					}
				}

				MemberProfitBean memberProfitBean = memberService.getMemberProfitCurMonth(
						new MemberProfitBean().setMember_id(member_id).setMerchants_id(orderBean2.getMerchants_id())
								.setProfit_time(profit_time).setProfit_type("total"));
				if (memberProfitBean != null) {
					num = memberService.updateMemberProfit(
							new MemberProfitBean().setMember_profit_id(memberProfitBean.getMember_profit_id())
									.setProfit_money(order_actual_price + ""));
					if (num <= 0) {
						throw new Exception("收益记录更新失败");
					}
				} else {
					num = memberService.insertMemberProfit(new MemberProfitBean().setMember_id(member_id)
							.setMerchants_id(orderBean2.getMerchants_id()).setProfit_time(profit_time)
							.setProfit_money(order_actual_price + "").setProfit_type("total"));
					if (num <= 0) {
						throw new Exception("收益记录添加失败");
					}
				}

				num = memberService.insertMemberProfit(
						new MemberProfitBean().setMember_id(member_id).setMerchants_id(orderBean2.getMerchants_id())
								.setProfit_time(profit_time).setOrder_id(orderBean2.getOrder_id() + "")
								.setProfit_money(order_actual_price + "").setProfit_type("detail"));
				if (num <= 0) {
					throw new Exception("收益记录失败");
				}

				num = orderDao.payOrder(new OrderBean().setOrder_id(Integer.valueOf(order_ids[i]))
						.setOrder_state(order_state).setPay_way(pay_way == null ? "online" : pay_way));
				if (num <= 0) {
					throw new Exception("订单更新失败");
				}

				MemberBean memberBean2 = memberService
						.getMemberByMerchants(new MemberBean().setMerchants_id(orderBean2.getMerchants_id()));

				if (memberBean2.getMemberLevelBean() != null
						&& "1".equals(memberBean2.getMemberLevelBean().getIs_wait_send())) {
//					VerificationBean verificationBean = codeService.getVerificationSetting(new VerificationBean());
//					VerificationCodeUtils.sendContent(verificationBean, memberBean2.getMember_account(),
//							memberBean2.getMemberLevelBean().getWait_send_desc()
//									.replace("member_name", memberBean.getCompany_name())
//									.replace("company_name", memberBean2.getCompany_name())
//									.replace("order_no", orderBean2.getOrder_no()));
					messageService.addMessage(
							 new MessageBean().setMessage_type("0").setMerchants_name(memberBean2.getCompany_name()).setMember_name(memberBean.getCompany_name())
					            .setMember_id(memberBean2.getMember_id()+"").setMessage_title("订单支付成功")
					            .setMessage_content(memberBean2.getMemberLevelBean().getWait_send_desc()
					            .replace("company_name", memberBean.getCompany_name())
					            .replace("order_no", orderBean2.getOrder_no())));
				}

			}
		}

		if ("balance".equals(pay_way)) {
			float balance = NumberUtils.Float(memberBean.getBalance()) - order_actual_price;
			if (balance < 0) {
				throw new Exception("余额不足");
			}

			num = memberService.updateMemberDetail(
					new MemberBean().setMember_id(memberBean.getMember_id()).setBalance(balance + ""));
			if (num < 0) {
				throw new Exception("余额更新失败");
			}
		}
		return num;
	}
	/**
	 * 
	 * @param orderGoodsBean
	 * @return
	 */
	 public List<OrderGoodsBean> getOneOrderGoodsByGoodss(OrderGoodsBean orderGoodsBean)
	  {
	    return orderDao.getOneOrderGoodsByGoodss(orderGoodsBean);
	  }
	 /**
	  * 
	  * @param orderGoodsBean	
	  * @return
	  */
	 public OrderGoodsBean getOneOrderGoodsByGoods(OrderGoodsBean orderGoodsBean)
	  {
	    return orderDao.getOneOrderGoodsByGoods(orderGoodsBean);
	  }

	/**
	 * 确认收货
	 * 
	 * @param orderBean
	 * @return
	 */
	public int confirmOrder(OrderBean orderBean, boolean is_receipt) {
		int num = this.orderDao.confirmOrder(orderBean);
	    if (num > 0)
	    {
	      OrderBean orderBean2 = orderDao.getOneOrderDetail(orderBean);

	      List<OrderGoodsBean> orderGoodsBean1 = orderDao.getOneOrderGoodsByGoodss(new OrderGoodsBean().setOrder_id(orderBean.getOrder_id()+""));

	      MemberBean memberBean3 = memberService
	        .getMemberByMerchants(new MemberBean().setMerchants_id(orderBean2.getMerchants_id()));

	      MemberBean memberBean2 = memberService
	        .getMemberByID(new MemberBean().setMember_id(NumberUtils.Integer(orderBean2.getMember_id())));

	      if ((is_receipt) && 
	        (memberBean2.getMemberLevelBean() != null) && 
	        ("1".equals(memberBean2.getMemberLevelBean().getIs_confirm_receipt())))
	      {
	        this.messageService.addMessage(
	          new MessageBean().setMessage_type("0").setMember_name(memberBean2.getCompany_name())
	          .setMerchants_name(memberBean3.getCompany_name()).setMember_id(memberBean3.getMember_id()+"")
	          .setMessage_title("确认收货").setMessage_content(memberBean2.getMemberLevelBean()
	          .getConfirm_receipt().replace("company_name", memberBean3.getCompany_name())
	          .replace("order_no", orderBean2.getOrder_no())));
	      }
	    }

	    return num;
	}

	/**
	 * 可追加评价订单
	 * 
	 * @param orderBean
	 * @param pageBean
	 * @return
	 */
	public List<OrderBean> getCanAddAssessmentOrder(OrderBean orderBean, PageBean pageBean) {
		List<OrderBean> orderBeans = orderDao.getCanAddAssessmentOrder(orderBean, pageBean);
		for (int i = 0; i < orderBeans.size(); i++) {
			OrderBean orderBean1 = orderBeans.get(i);
			List<OrderGoodsBean> orderGoodsBeans = getOrderGoodss(
					new OrderGoodsBean().setOrder_id(orderBean1.getOrder_id() + ""));
			orderBean1.setOrderGoodsBeans(orderGoodsBeans);

			MerchantsBean merchantsBean = merchantsServiceI.getOneMerchantsDetail(
					new MerchantsBean().setMerchants_id(Integer.valueOf(orderBean1.getMerchants_id())));
			orderBean1.setMerchantsBean(merchantsBean);
		}
		return orderBeans;
	}

	public int updateAssessmentOrderRemark(AssessmentBean assessmentBean) {
		return orderDao.updateAssessmentOrderRemark(assessmentBean);
	}

	/**
	 * 评价订单
	 * 
	 * @param assessmentBeans
	 * @return
	 * @throws Exception
	 */
	public int assessmentOrder(List<AssessmentBean> assessmentBeans) throws Exception {
		int num = 0;
		String order_id = "";
		for (int i = 0; i < assessmentBeans.size(); i++) {
			AssessmentBean assessmentBean = assessmentBeans.get(i);
			order_id = assessmentBean.getOrder_id();
			num = orderDao.insertAssessmentOrder(assessmentBean);
			List<AssessmentImgBean> assessmentImgBeans = null;
			if (num > 0) {
				assessmentImgBeans = assessmentBean.getAssessmentImgBeans();
				if (assessmentImgBeans != null) {
					for (int j = 0; j < assessmentImgBeans.size(); j++) {
						AssessmentImgBean assessmentImgBean = assessmentImgBeans.get(j);
						int k = orderDao.insertAssessmentImg(
								assessmentImgBean.setAssessment_id(assessmentBean.getAssessment_id() + ""));
						if (k <= 0) {
							throw new Exception("添加失败 回滚");
						}
					}
				}
			} else {
				throw new Exception("添加失败 回滚");
			}
			if (assessmentBean.getAssessment_type().equals("merchants")) {// 如果评价的是商家
																			// //
																			// 则更新商家的星级
				MerchantsBean merchantsBean = merchantsServiceI.getOneMerchantsDetail(
						new MerchantsBean().setMerchants_id(Integer.valueOf(assessmentBean.getRelation_id())));
				int star1 = 0;
				int star2 = 0;
				int star3 = 0;

				int assessment_count = merchantsBean.getAssessment_count();
				int good_assessment_count = merchantsBean.getGood_assessment_count();
				int bad_assessment_count = merchantsBean.getBad_assessment_count();
				int in_assessment_count = merchantsBean.getIn_assessment_count();
				if ((Float.valueOf(assessmentBean.getAssessment_star1())
						+ Float.valueOf(assessmentBean.getAssessment_star2())
						+ Float.valueOf(assessmentBean.getAssessment_star3())) * 10 / 3 > 30) {
					good_assessment_count += 1;
				} else if ((Float.valueOf(assessmentBean.getAssessment_star1())
						+ Float.valueOf(assessmentBean.getAssessment_star2())
						+ Float.valueOf(assessmentBean.getAssessment_star3())) * 10 / 3 == 30) {
					in_assessment_count += 1;
				} else {
					bad_assessment_count += 1;
				}

				if (merchantsBean != null) {
					star1 = NumberUtils
							.KeepDecimal((Float.valueOf(assessmentBean.getAssessment_star1()) * assessment_count
									+ Float.valueOf(merchantsBean.getMerchants_star1())) / (assessment_count + 1));

					star2 = NumberUtils
							.KeepDecimal((Float.valueOf(assessmentBean.getAssessment_star2()) * assessment_count
									+ Float.valueOf(merchantsBean.getMerchants_star2())) / (assessment_count + 1));

					star3 = NumberUtils
							.KeepDecimal((Float.valueOf(assessmentBean.getAssessment_star3()) * assessment_count
									+ Float.valueOf(merchantsBean.getMerchants_star3())) / (assessment_count + 1));
				}
				assessment_count += 1;
				int h = orderDao.updateMerchantsStar(new MerchantsBean()
						.setMerchants_id(Integer.valueOf(assessmentBean.getRelation_id()))
						.setMerchants_star1(star1 + "").setMerchants_star2(star2 + "").setMerchants_star3(star3 + "")
						.setAssessment_count(assessment_count).setGood_assessment_count(good_assessment_count)
						.setBad_assessment_count(bad_assessment_count).setIn_assessment_count(in_assessment_count));
				if (h <= 0) {
					throw new Exception("更新商家星级失败 回滚");
				}

			} else if (assessmentBean.getAssessment_type().equals("goods")) {// 如果评价的商品
				// //
				// 则更新商品的星级
				GoodsBean goodsBean = goodsServiceI.getOneGoodsDetail(
						new GoodsBean().setGoods_id(Integer.valueOf(assessmentBean.getRelation_id())));

				int star1 = 0;
				int star2 = 0;
				int star3 = 0;
				int assessment_count = goodsBean.getAssessment_count() + 1;
				int good_assessment_count = goodsBean.getGood_assessment_count();
				int bad_assessment_count = goodsBean.getBad_assessment_count();
				int in_assessment_count = goodsBean.getIn_assessment_count();
				int assessment_img_count = goodsBean.getAssessment_img_count();
				if (assessmentImgBeans != null && assessmentImgBeans.size() > 0) {
					assessment_img_count += 1;
				}
				if ((Float.valueOf(assessmentBean.getAssessment_star1())
						+ Float.valueOf(assessmentBean.getAssessment_star2())
						+ Float.valueOf(assessmentBean.getAssessment_star3())) * 10 / 3 > 30) {
					good_assessment_count += 1;
				} else if ((Float.valueOf(assessmentBean.getAssessment_star1())
						+ Float.valueOf(assessmentBean.getAssessment_star2())
						+ Float.valueOf(assessmentBean.getAssessment_star3())) * 10 / 3 == 30) {
					in_assessment_count += 1;
				} else {
					bad_assessment_count += 1;
				}

				double good_assessment_percent = NumberUtils
						.KeepDecimal((float) good_assessment_count * 100 / (float) assessment_count, 2);
				double bad_assessment_percent = NumberUtils
						.KeepDecimal((float) bad_assessment_count * 100 / (float) assessment_count, 2);
				double in_assessment_percent = NumberUtils
						.KeepDecimal((float) in_assessment_count * 100 / (float) assessment_count, 2);

				if (goodsBean != null) {
					star1 = NumberUtils.KeepDecimal((Float.valueOf(assessmentBean.getAssessment_star1())
							+ Float.valueOf(goodsBean.getGoods_star1())) / 2);

					star2 = NumberUtils.KeepDecimal((Float.valueOf(assessmentBean.getAssessment_star2())
							+ Float.valueOf(goodsBean.getGoods_star2())) / 2);

					star3 = NumberUtils.KeepDecimal((Float.valueOf(assessmentBean.getAssessment_star3())
							+ Float.valueOf(goodsBean.getGoods_star3())) / 2);
				}

				int h = goodsServiceI2.updateGoodsDetailStart(new GoodsBean()
						.setGoods_id(Integer.valueOf(assessmentBean.getRelation_id())).setGoods_star1(star1 + "")
						.setGoods_star2(star2 + "").setGoods_star3(star3 + "").setAssessment_count(assessment_count)
						.setGood_assessment_count(good_assessment_count).setBad_assessment_count(bad_assessment_count)
						.setIn_assessment_count(in_assessment_count)
						.setGood_assessment_percent(good_assessment_percent + "")
						.setBad_assessment_percent(bad_assessment_percent + "")
						.setIn_assessment_percent(in_assessment_percent + "")
						.setAssessment_img_count(assessment_img_count));
				if (h <= 0) {
					throw new Exception("更新商品星级失败 回滚");
				}
			}
		}

		// num = orderDao.updateDistribution(new
		// DistributionBean().setOrder_id(order_id).setDistribution_state("end"));

		num = orderDao.assessmentOrder(new OrderBean().setOrder_id(Integer.valueOf(order_id)));
		if (num <= 0) {
			throw new Exception("订单状态改变失败 回滚");
		}
		return num;
	}

	/**
	 * 用户评价列表
	 * 
	 * @param assessmentBean
	 * @param pageBean
	 * @return
	 */
	public List<AssessmentBean> getMemberAssessments(AssessmentBean assessmentBean, PageBean pageBean) {
		List<AssessmentBean> assessmentBeans = orderDao.getMemberAssessments(assessmentBean, pageBean);
		for (int i = 0; i < assessmentBeans.size(); i++) {
			AssessmentBean assessmentBean2 = assessmentBeans.get(i);
			List<AssessmentImgBean> assessmentImgBeans = orderDao.getOrderAssessmentImgs(
					new AssessmentImgBean().setAssessment_id(assessmentBean2.getAssessment_id() + ""));
			assessmentBean2.setAssessmentImgBeans(assessmentImgBeans);

			if ("goods".equals(assessmentBean2.getAssessment_type())) {
				GoodsBean goodsBean = goodsServiceI.getOneGoodsDetail(
						new GoodsBean().setGoods_id(Integer.valueOf(assessmentBean2.getRelation_id())));
				assessmentBean2.setGoodsBean(goodsBean);
			}

			if ("merchants".equals(assessmentBean2.getAssessment_type())) {
				// MerchantsBean merchantsBean =
				// merchantsServiceI.getOneMerchantsDetail(
				// new
				// MerchantsBean().setMerchants_id(Integer.valueOf(assessmentBean2.getRelation_id())));
				// assessmentBean2.setMerchantsBean(merchantsBean);

				MemberBean memberBean = memberService
						.getMemberByMerchants(new MemberBean().setMerchants_id(assessmentBean2.getRelation_id()));
				assessmentBean2.setMemberBean(memberBean);

				OrderBean orderBean = orderDao
						.getOneOrderDetail(new OrderBean().setOrder_id(Integer.valueOf(assessmentBean2.getOrder_id())));

				assessmentBean2.setOrderBean(orderBean);
			}
		}
		return assessmentBeans;
	}

	/**
	 * 用户评价列表
	 * 
	 * @param assessmentBean
	 * @param pageBean
	 * @return
	 */
	public List<AssessmentBean> getMemberAssessmentsV2(AssessmentBean assessmentBean, PageBean pageBean) {
		List<AssessmentBean> assessmentBeans = orderDao.getMemberAssessmentsV2(assessmentBean, pageBean);
		for (int i = 0; i < assessmentBeans.size(); i++) {
			AssessmentBean assessmentBean2 = assessmentBeans.get(i);

			if ("goods".equals(assessmentBean2.getAssessment_type())) {
				GoodsBean goodsBean = goodsServiceI.getOneGoodsDetail(
						new GoodsBean().setGoods_id(Integer.valueOf(assessmentBean2.getRelation_id())));
				assessmentBean2.setGoodsBean(goodsBean);
			}

			if ("merchants".equals(assessmentBean2.getAssessment_type())) {
				MerchantsBean merchantsBean = merchantsServiceI.getOneMerchantsDetail(
						new MerchantsBean().setMerchants_id(Integer.valueOf(assessmentBean2.getRelation_id())));
				assessmentBean2.setMerchantsBean(merchantsBean);
			}

			List<AssessmentBean> assessmentBeans2 = orderDao
					.getMemberAssessments(assessmentBean2.setAssessment_type(""));
			assessmentBean2.setAssessmentBeans(assessmentBeans2);
			for (int j = 0; j < assessmentBeans2.size(); j++) {
				AssessmentBean assessmentBean3 = assessmentBeans2.get(j);
				List<AssessmentImgBean> assessmentImgBeans = orderDao.getOrderAssessmentImgs(
						new AssessmentImgBean().setAssessment_id(assessmentBean3.getAssessment_id() + ""));
				assessmentBean3.setAssessmentImgBeans(assessmentImgBeans);

			}

		}
		return assessmentBeans;
	}

	/**
	 * 评价列表
	 * 
	 * @param assessmentBean
	 * @return
	 */
	public List<AssessmentBean> getOrderAssessments(AssessmentBean assessmentBean, String type, PageBean pageBean) {
		List<AssessmentBean> assessmentBeans = new ArrayList<AssessmentBean>();
		if ("img".equals(type)) {
			assessmentBeans = orderDao.getOrderAssessmentsImg(assessmentBean, pageBean);
		} else if ("good".equals(type)) {
			assessmentBeans = orderDao.getOrderAssessmentsGood(assessmentBean, pageBean);
		} else if ("bad".equals(type)) {
			assessmentBeans = orderDao.getOrderAssessmentsBad(assessmentBean, pageBean);
		} else if ("in".equals(type)) {
			assessmentBeans = orderDao.getOrderAssessmentsIn(assessmentBean, pageBean);
		} else {
			assessmentBeans = orderDao.getOrderAssessments(assessmentBean, pageBean);
		}
		for (int i = 0; i < assessmentBeans.size(); i++) {
			AssessmentBean assessmentBean2 = assessmentBeans.get(i);
			List<AssessmentImgBean> assessmentImgBeans = orderDao.getOrderAssessmentImgs(
					new AssessmentImgBean().setAssessment_id(assessmentBean2.getAssessment_id() + ""));
			assessmentBean2.setAssessmentImgBeans(assessmentImgBeans);

			if (assessmentBean2.getMember_id() != null) {
				MemberBean memberBean = memberService
						.getMemberByID(new MemberBean().setMember_id(Integer.valueOf(assessmentBean2.getMember_id())));
				assessmentBean2.setMemberBean(memberBean);
			}

			OrderBean orderBean = orderDao
					.getOneOrderDetail(new OrderBean().setOrder_id(Integer.valueOf(assessmentBean2.getOrder_id())));

			assessmentBean2.setOrderBean(orderBean);
		}
		return assessmentBeans;
	}

	/**
	 * 评价图片列表
	 * 
	 * @param assessmentImgBean
	 * @return
	 */
	public List<AssessmentImgBean> getOrderAssessmentImgs(AssessmentImgBean assessmentImgBean) {
		return orderDao.getOrderAssessmentImgs(assessmentImgBean);
	}
}