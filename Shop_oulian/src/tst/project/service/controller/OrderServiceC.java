package tst.project.service.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.elasticsearch.monitor.jvm.JvmInfo.Mem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pingplusplus.Pingpp;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.Refund;
import tst.project.bean.finance.ProfitBean;
import tst.project.bean.goods.GoodsBean;
import tst.project.bean.member.CouponBean;
import tst.project.bean.member.MemberBean;
import tst.project.bean.order.BusinessProfitBean;
import tst.project.bean.order.LogisticsBean;
import tst.project.bean.order.OrderBean;
import tst.project.bean.order.OrderGoodsBean;
import tst.project.bean.order.OrderParameterBean;
import tst.project.bean.order.OrderServiceBean;
import tst.project.bean.order.PingSettingBean;
import tst.project.bean.order.RefundBean;
import tst.project.bean.order.RefundImgBean;
import tst.project.bean.order.RefundReasonBean;
import tst.project.dao.controller.OrderDaoC;
import tst.project.page.PageBean;
import tst.project.utils.NumberUtils;


@Service
@Transactional(rollbackFor = Exception.class)
public class OrderServiceC {
	@Resource
	OrderDaoC orderDaoC;
	
	@Resource
	GoodsService goodsService;
	
	@Resource
	MemberServiceC memberServiceC;
	
	@Resource
	CouponServiceC couponServiceC;
	
	@Resource
	OthersServiceC othersServiceC;

	/**
	 * 设置订单是否开过发票
	 * @param orderBean
	 * @return
	 */
	public int updateOrderInvise(OrderBean orderBean){
		return orderDaoC.updateOrderInvise(orderBean);
	}
	
	/**
	 * 导出订单列表
	 * @param orderBean
	 * @return
	 */
	public List<Object> exportOrderExcel(OrderBean orderBean){
		return orderDaoC.exportOrderExcel(orderBean);
	}
	
	public int inviseOrder(OrderBean orderBean){
		return orderDaoC.inviseOrder(orderBean);
	}
	/**
	 * 拒绝订单
	 * @param orderBean
	 * @return
	 */
	public int refuseOrder(OrderBean orderBean){
		return orderDaoC.refuseOrder(orderBean);
	}
	
	/**
	 * 通过订单
	 * @param orderBean
	 * @return
	 */
	public int acceptOrder(OrderBean orderBean){
		return orderDaoC.acceptOrder(orderBean);
	}
	
	/**
	 * 获得物流公司 不分页
	 * @param logisticsBean
	 * @return
	 */
	public List<LogisticsBean> getOrderLogisticsNoPage(LogisticsBean logisticsBean){
		return orderDaoC.getOrderLogisticsNoPage(logisticsBean);
	}
	
	/**
	 * 获得物流公司
	 * @param logisticsBean
	 * @return
	 */
	public List<LogisticsBean> getOrderLogistics(LogisticsBean logisticsBean,PageBean pageBean){
		return orderDaoC.getOrderLogistics(logisticsBean, pageBean);
	}
	
	/**
	 * 添加物流公司
	 * @param logisticsBean
	 * @return
	 */
	public int insertOrderLogistics(LogisticsBean logisticsBean){
		return orderDaoC.insertOrderLogistics(logisticsBean);
	}
	
	/**
	 * 修改物流公司
	 * @param logisticsBean
	 * @return
	 */
	public int updateOrderLogistics(LogisticsBean logisticsBean){
		return orderDaoC.updateOrderLogistics(logisticsBean);
	}

	/**
	 * 删除物流公司
	 * @param logisticsBean
	 * @return
	 */
	public int deleteOrderLogistics(LogisticsBean logisticsBean){
		return orderDaoC.deleteOrderLogistics(logisticsBean);
	}
	
	/**
	 * 订单收益统计
	 * @param profitBean
	 * @return
	 */
	public Map getOrderProfits(OrderBean orderBean){
		return orderDaoC.getOrderProfits(orderBean);
	}
	
	/**
	 * 获得收益详情统计
	 * @param businessProfitBean
	 * @param pageBean
	 * @return
	 */
	public ProfitBean getBusinessProfitsTypeCount(BusinessProfitBean businessProfitBean){
		ProfitBean profitBean=orderDaoC.getBusinessProfitsTypeCount(businessProfitBean);
		if(profitBean==null){
			profitBean=new ProfitBean();
		}
		return profitBean;
	}
	
	/**
	 * 获得收益详情
	 * @param businessProfitBean
	 * @param pageBean
	 * @return
	 */
	public List<BusinessProfitBean> getBusinessProfitsType(BusinessProfitBean businessProfitBean,PageBean pageBean){
		return orderDaoC.getBusinessProfitsType(businessProfitBean, pageBean);
	}
	
	
	/**
	 * 添加退款原因
	 * @param refundReasonBean
	 * @return
	 */
	public int insertRefundReason(RefundReasonBean refundReasonBean){
		return orderDaoC.insertRefundReason(refundReasonBean);
	}
	/**
	 * 修改退款原因
	 * @param refundReasonBean
	 * @return
	 */
	public int updateRefundReason(RefundReasonBean refundReasonBean){
		return orderDaoC.updateRefundReason(refundReasonBean);
	}
	/**
	 * 删除退款原因
	 * @param refundReasonBean
	 * @return
	 */
	public int deleteRefundReason(RefundReasonBean refundReasonBean){
		return orderDaoC.deleteRefundReason(refundReasonBean);
	}
	
	/**
	 * 商品退款可能的原因
	 * @return
	 */
	public List<RefundReasonBean> getRefundReasons(RefundReasonBean refundReasonBean,PageBean pageBean){
		return orderDaoC.getRefundReasons(refundReasonBean,pageBean);
	}
	/**
	 * 审核退款申请
	 * @throws Exception 
	 */
	public int updateRefundState(RefundBean refundBean,String type) throws Exception{
		int num=0;
		if("end".equals(refundBean.getRefund_state())){//确认退款给用户
			RefundBean refundBean2=orderDaoC.getOrderRefundDetail(refundBean);
			MemberBean memberBean=memberServiceC.
					getOneMemberDetail(new MemberBean().setMember_id(Integer.valueOf(refundBean2.getMember_id())));
			OrderBean orderBean=getOrderDetail(new OrderBean().setOrder_id(Integer.valueOf(refundBean2.getOrder_id())));
			
			OrderGoodsBean orderGoodsBean = 
					orderDaoC.getOrderGoodssByGoods(new OrderGoodsBean().setOrder_id(refundBean2.getOrder_id())
							.setOrder_goods_id(Integer.valueOf(refundBean2.getOrder_goods_id())));
						
			float member_integral=Float.valueOf(memberBean.getIntegral());
			
			String member_coupon_id="";
			
			float refund_price=Float.valueOf(refundBean2.getRefund_price());
			
			if(orderBean.getMember_coupon_id()!=null
					&&!orderBean.getMember_coupon_id().equals("")
					&&!orderBean.getMember_coupon_id().equals("-1")){//使用了优惠卷  先退还优惠卷
				float coupon_price=Float.valueOf(orderBean.getCoupon_price());
				if(refund_price>=coupon_price){//并且退还金额大于优惠卷优惠金额
					member_coupon_id=orderBean.getMember_coupon_id();
					refund_price-=coupon_price;
				}
				
				int k=couponServiceC.updateCouponState(new CouponBean()
						.setMember_coupon_id(orderBean.getMember_coupon_id())
						.setCoupon_state("not_used"));
				if(k<=0){
					throw new Exception("返还优惠卷失败");
				}
			}
			
			float refund_give_integral=Float.valueOf(refundBean2.getRefund_give_integral());
		
			if("1".equals(orderGoodsBean.getIs_give_integral())){//商品赠送积分了
				refund_give_integral=Float.valueOf(orderGoodsBean.getGive_integral_value())*Integer.valueOf(refundBean.getRefund_count());			
			}
			
			float order_deduct_integral=0;
			float order_deduct_percent=0;
			float refund_deduct_integral=0;
			if("1".equals(orderBean.getIs_deduct_integral())){//订单抵扣积分了
				order_deduct_integral=NumberUtils.Float(orderBean.getDeduct_integral_value());
				//order_deduct_price=Float.valueOf(orderBean1.getDeduct_integral_price());
				order_deduct_percent=Float.valueOf(orderBean.getDeduct_integral_percent());
			}
				

			if(order_deduct_integral>(refund_price*100)/order_deduct_percent){//抵扣的积分 大于退款的金额 
				refund_price=0;//则无需返回钱给用户
				refund_deduct_integral=(refund_price*100)/order_deduct_percent;//返还的积分应该是现金的钱
			}else{
				refund_deduct_integral=order_deduct_integral;
				refund_price-=order_deduct_integral*order_deduct_percent/100;//积分全退 返还的钱减去积分的钱
			}	
					
			if(member_integral+refund_deduct_integral<refund_give_integral){
				throw new Exception("用户剩余积分不足");
			}
			
			int k=memberServiceC.updateMemberDetail(new MemberBean()
								.setMember_id(Integer.valueOf(refundBean2.getMember_id()))
								.setIntegral((member_integral+refund_deduct_integral-refund_give_integral)+""));
			if(k<=0){
				throw new Exception("用户积分更新失败");
			}	
			
			String order_state="";
			List<OrderGoodsBean> orderGoodsBeans=orderDaoC.getOrderGoodss(new OrderGoodsBean().setOrder_id(refundBean2.getOrder_id()));
			if(orderGoodsBeans!=null 
					&&orderGoodsBeans.size()==1
					&&(orderGoodsBean.getGoods_num()+"").equals(refundBean2.getRefund_count())){//此订单只有一个商品 并且退款数量和购买数量相等 则订单状态改变
				order_state="end";	
			}else{
				order_state=orderBean.getOrder_state();
			}
			int h=orderDaoC.updateOrderDetail(new OrderBean().
					setOrder_id(Integer.valueOf(refundBean2.getOrder_id()))
					.setOrder_total_price(NumberUtils.KeepDecimal(Float.valueOf(orderBean.getOrder_actual_price())-refund_price, 2)+"")
					.setMember_coupon_id("-1")
					.setDeduct_integral_value(NumberUtils.KeepDecimal(NumberUtils.Float(orderBean.getDeduct_integral_value())-order_deduct_integral, 2)+"")
					.setDeduct_integral_price(NumberUtils.KeepDecimal(NumberUtils.Float(orderBean.getDeduct_integral_price())
							-order_deduct_integral*order_deduct_percent/100, 2)+"")
					.setGive_integral_value(NumberUtils.KeepDecimal(Float.valueOf(orderBean.getGive_integral_value())-refund_give_integral, 2)+"")
					.setOrder_state(order_state));
			
			if(h<=0){
				throw new Exception("订单状态更新失败");
			}
			
			num=orderDaoC.updateRefundState(refundBean
											.setRefund_price(NumberUtils.KeepDecimal(refund_price, 2)+"")
											.setRefund_give_integral(NumberUtils.KeepDecimal(refund_give_integral, 2)+"")
											.setRefund_deduct_integral(NumberUtils.KeepDecimal(refund_deduct_integral, 2)+"")
											.setMember_coupon_id(member_coupon_id));
			if(num<=0){
				throw new Exception("退款状态更新失败");
			}		
			
			
			double total_price=0;
			total_price=orderGoodsBean.getTotal_price()-Float.valueOf(refundBean2.getRefund_price());
			num=orderDaoC.updateOrderGoodsDetail(new OrderGoodsBean()
					.setOrder_goods_id(orderGoodsBean.getOrder_goods_id())
					.setTotal_price(total_price));
			if(num<=0){
				throw new Exception("订单商品更新失败");
			}		
			
			if(refund_price>0){
//				if (type.equals("ssp")) {
//					Pingpp.apiKey = "sk_live_vLu9eDuXH0mDy1SerT5mLe9C";
//				} else if (type.equals("jf")) {
//					Pingpp.apiKey = "sk_live_m1qnPKijfbb1GuLuv9CuD0a9";
//				}
				PingSettingBean pingSettingBean=othersServiceC.getPingSetting(new PingSettingBean().setPing_type("1"));
				
				Pingpp.apiKey = pingSettingBean.getPing_app_key();
				
				Pingpp.privateKeyPath = getClass().getResource("/").getFile().toString()+"/rsa_private_key.pem";	
				Charge ch = Charge.retrieve(refundBean2.getRefund_order_no());//ch_id 是已付款的订单号
			    Map<String, Object> refundMap = new HashMap<String, Object>();
			    refundMap.put("amount", refund_price*100);//refund_price*100
			    refundMap.put("description", "退款");
			    Refund re = ch.getRefunds().create(refundMap);	
			}
			
			
		}else{
			num=orderDaoC.updateRefundState(refundBean);
		}
		return num;
	}
	
	/**
	 * 退款申请详情
	 * @param refundBean
	 * @return
	 */
	public RefundBean getOrderRefundDetail(RefundBean refundBean){
		RefundBean refundBean2=orderDaoC.getOrderRefundDetail(refundBean);
		if(refundBean2!=null){
			List<RefundImgBean> refundImgBeans=getRefundImgs(new RefundImgBean().setRefund_id(refundBean2.getRefund_id()+""));
			refundBean2.setRefundImgBeans(refundImgBeans);
		}	
		return refundBean2;
	}
	/**
	 * 获得所有退单申请
	 * @return
	 */
	public List<RefundBean> getOrderRefunds(RefundBean refundBean,PageBean pageBean){
		return orderDaoC.getOrderRefunds(refundBean,pageBean);
	}
	
	/**
	 * 获得店铺退款订单列表
	 * @param refundBean
	 * @return
	 */
	public List<RefundBean> getBussinessRefundOrders(RefundBean refundBean,PageBean pageBean){
		List<RefundBean> refundBeans=orderDaoC.getBussinessRefundOrders(refundBean,pageBean);
		if(refundBeans!=null){
			for (int i = 0; i < refundBeans.size(); i++) {
				MemberBean memberBean=memberServiceC.
						getOneMemberDetail(new MemberBean().setMember_id(Integer.valueOf(refundBeans.get(i).getMember_id())));
				refundBeans.get(i).setMemberBean(memberBean);
				
				
				GoodsBean goodsBean=goodsService.getOneGoodsDetail(new GoodsBean()
						.setGoods_id(Integer.valueOf(refundBeans.get(i).
								getGoods_id())));
				refundBeans.get(i).setGoodsBean(goodsBean);
				
				List<OrderParameterBean> orderParameterBeans=
						getOrderGoodsParameters(new OrderParameterBean().
								setOrder_goods_id(refundBeans.get(i).getOrder_goods_id())
								.setOrder_id(refundBeans.get(i).getOrder_id()));
				refundBeans.get(i).setOrderParameterBeans(orderParameterBeans);
				
				List<OrderServiceBean> orderServiceBeans=
						getOrderGoodsServices(new OrderServiceBean().
								setOrder_goods_id(refundBeans.get(i).getOrder_goods_id())
								.setOrder_id(refundBeans.get(i).getOrder_id()));
				refundBeans.get(i).setOrderServiceBeans(orderServiceBeans);	
				
				List<RefundImgBean> refundImgBeans=
						getRefundImgs(new RefundImgBean().setRefund_id(refundBeans.get(i).getRefund_id()+""));
				refundBeans.get(i).setRefundImgBeans(refundImgBeans);
			}		
		}
		return refundBeans;
	}
	
	
	/**
	 * 获得店铺退款单个订单详情
	 * @param refundBean
	 * @return
	 */
	public RefundBean getOneBussinessRefundOrder(RefundBean refundBean){
		RefundBean refundBean2=orderDaoC.getOneBussinessRefundOrder(refundBean);
		if(refundBean2!=null){
			
				MemberBean memberBean=memberServiceC.
						getOneMemberDetail(new MemberBean().setMember_id(Integer.valueOf(refundBean2.getMember_id())));
				refundBean2.setMemberBean(memberBean);
				
				GoodsBean goodsBean=goodsService.getOneGoodsDetail(new GoodsBean()
						.setGoods_id(Integer.valueOf(refundBean2.getGoods_id())));
				refundBean2.setGoodsBean(goodsBean);
				
				List<OrderParameterBean> orderParameterBeans=
						getOrderGoodsParameters(new OrderParameterBean().
								setOrder_goods_id(refundBean2.getOrder_goods_id())
								.setOrder_id(refundBean2.getOrder_id()));
				refundBean2.setOrderParameterBeans(orderParameterBeans);
				
				List<OrderServiceBean> orderServiceBeans=
						getOrderGoodsServices(new OrderServiceBean().
								setOrder_goods_id(refundBean2.getOrder_goods_id())
								.setOrder_id(refundBean2.getOrder_id()));
				refundBean2.setOrderServiceBeans(orderServiceBeans);	
				
				List<RefundImgBean> refundImgBeans=
						getRefundImgs(new RefundImgBean().setRefund_id(refundBean2.getRefund_id()+""));
				refundBean2.setRefundImgBeans(refundImgBeans);
		}
		return refundBean2;
	}
	/**
	 * 单个退款图片详情
	 * @param refundBean
	 * @return
	 */
	public List<RefundImgBean> getRefundImgs(RefundImgBean refundImgBean){
		return orderDaoC.getRefundImgs(refundImgBean);
	}
	
	/**
	 * 获得历史收益
	 * @param orderGoodsBean
	 * @return
	 */
	public String getHistoryBusinessProfitsPrice(OrderGoodsBean orderGoodsBean){
		return orderDaoC.getHistoryBusinessProfitsPrice(orderGoodsBean);
	}
	/**
	 * 商户的收益
	 * @param orderGoodsBean
	 * @param pageBean
	 * @return
	 */
	public List<OrderGoodsBean> getBusinessProfits(OrderGoodsBean orderGoodsBean,PageBean pageBean){
		return orderDaoC.getBusinessProfits(orderGoodsBean, pageBean);
	}
	/**
	 * 获得店铺的订单列表（家纺）
	 * @param orderBean
	 * @param pageBean
	 * @return
	 */
	public List<OrderBean> getBusinessOrderList(OrderBean orderBean,PageBean pageBean){
	    List<OrderBean> orderBeans=orderDaoC.getBusinessOrderList(orderBean, pageBean);
	    for (int i = 0; i < orderBeans.size(); i++) {
	    
	    	
	    	List<OrderGoodsBean> orderGoodsBeans=
	    			getBusinessOrderGodoss(new OrderGoodsBean().
	    					setBusiness_id(orderBean.getMerchants_id())
	    					.setOrder_id(orderBeans.get(i).getOrder_id()+"")
	    					.setMerchants_account_id(orderBean.getMerchants_account_id()));
	    	
	    	orderBeans.get(i).setOrderGoodsBeans(orderGoodsBeans);
		}
		return orderBeans;
	}
	/**
	 * 获得店铺的订单详情（家纺）
	 * @param orderBean
	 * @param pageBean
	 * @return
	 */
	public OrderBean getOneBusinessOrderDetail(OrderBean orderBean){
	    OrderBean orderBean2=orderDaoC.getOneBusinessOrderDetail(orderBean);
	    MemberBean  memberBean=memberServiceC
    			.getOneMemberDetail(new MemberBean().setMember_id(Integer.valueOf(orderBean2.getMember_id())));
	    orderBean2.setMemberBean(memberBean);
	    if(orderBean2!=null){
	    	List<OrderGoodsBean> orderGoodsBeans=
	    			getBusinessOrderGodoss(new OrderGoodsBean().
	    					setBusiness_id(orderBean.getMerchants_id())
	    					.setOrder_id(orderBean2.getOrder_id()+""));
	    	
	    	orderBean2.setOrderGoodsBeans(orderGoodsBeans);
		}
		return orderBean2;
	}
	
	/**
	 * 获得店铺的订单商品详情（家纺）
	 * @param orderGoodsBean
	 * @return
	 */
	public List<OrderGoodsBean> getBusinessOrderGodoss(OrderGoodsBean orderGoodsBean){
		List<OrderGoodsBean> orderGoodsBeans=orderDaoC.getBusinessOrderGodoss(orderGoodsBean);
		for (int i = 0; i < orderGoodsBeans.size(); i++) {
			GoodsBean goodsBean=goodsService.getOneGoodsDetail(new GoodsBean().
					setGoods_id(Integer.valueOf(orderGoodsBeans.get(i).getGoods_id())));
			orderGoodsBeans.get(i).setGoodsBean(goodsBean);
			
			
			List<OrderParameterBean> orderParameterBeans=getOrderGoodsParameters(new OrderParameterBean()
					.setOrder_goods_id(orderGoodsBeans.get(i).getOrder_goods_id()+"")
					.setOrder_id(orderGoodsBeans.get(i).getOrder_id()));
			orderGoodsBeans.get(i).setOrderParameterBeans(orderParameterBeans);
			
			List<OrderServiceBean> orderServiceBeans=getOrderGoodsServices(new OrderServiceBean()
					.setOrder_goods_id(orderGoodsBeans.get(i).getOrder_goods_id()+"")
					.setOrder_id(orderGoodsBeans.get(i).getOrder_id()));
			orderGoodsBeans.get(i).setOrderServiceBeans(orderServiceBeans);
		}
		return orderGoodsBeans;
	}
	
	/**
	 * 获得用户的订单列表
	 * @return
	 */
	public List<OrderBean> getMemberOrderList(OrderBean orderBean,PageBean pageBean){
		return orderDaoC.getMemberOrderList(orderBean,pageBean);
	}
	
	/**
	 * 确认发货
	 * @param orderBean
	 * @return
	 */
	public int confirmSendOrder(OrderBean orderBean){
		return orderDaoC.confirmSendOrder(orderBean);
	}
	
	/**
	 * 获得订单的商品详情
	 * @param orderGoodsBean
	 * @return
	 */
	public OrderGoodsBean getOrderGoodsDetail(OrderGoodsBean orderGoodsBean){
		OrderGoodsBean orderGoodsBean2=orderDaoC.getOrderGoodsDetail(orderGoodsBean);
		if(orderGoodsBean2!=null){
			List<OrderParameterBean> orderParameterBeans=getOrderGoodsParameters(new OrderParameterBean()
																						.setOrder_goods_id(orderGoodsBean2.getOrder_goods_id()+"")
																						.setOrder_id(orderGoodsBean2.getOrder_id()));
			orderGoodsBean2.setOrderParameterBeans(orderParameterBeans);
			
			List<OrderServiceBean> orderServiceBeans=getOrderGoodsServices(new OrderServiceBean()
																			.setOrder_goods_id(orderGoodsBean2.getOrder_goods_id()+"")
																			.setOrder_id(orderGoodsBean2.getOrder_id()));
			orderGoodsBean2.setOrderServiceBeans(orderServiceBeans);
		}
		return orderGoodsBean2;
	}
	
	/**
	 * 获得订单的商品的参数列表
	 * @param orderGoodsBean
	 * @return
	 */
	public List<OrderParameterBean> getOrderGoodsParameters(OrderParameterBean orderParameterBean){
		return orderDaoC.getOrderGoodsParameters(orderParameterBean);
	}
	
	/**
	 * 获得订单的商品的服务列表
	 * @param orderGoodsBean
	 * @return
	 */
	public List<OrderServiceBean> getOrderGoodsServices(OrderServiceBean orderServiceBean){
		return orderDaoC.getOrderGoodsServices(orderServiceBean);
	}
	
	/**
	 * 获得订单的商品
	 * @param orderGoodsBean
	 * @return
	 */
	public List<OrderGoodsBean> getOrderGoodss(OrderGoodsBean orderGoodsBean){
		return orderDaoC.getOrderGoodss(orderGoodsBean);
	}
	
	/**
	 * 获得商家的订单详情
	 * @return
	 */
	public OrderBean getOrderDetail(OrderBean orderBean){
		OrderBean orderBean2=orderDaoC.getOrderDetail(orderBean);
		if(orderBean2!=null){
			MemberBean memberMerchantsBean=memberServiceC.getMemberByMerchants(new MemberBean().setMerchants_id(orderBean2.getMerchants_id()));
			orderBean2.setMemberMerchantsBean(memberMerchantsBean);
		}
		return orderBean2;
	}
	
	/**
	 * 获得商家的订单列表
	 * @return
	 */
	public List<OrderBean> getOrderList(OrderBean orderBean,PageBean pageBean){
		return orderDaoC.getOrderList(orderBean,pageBean);
	}
}
