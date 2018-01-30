package tst.project.webservice.interfaces;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tst.project.bean.ExcelBean;
import tst.project.bean.HostBean;
import tst.project.bean.goods.GoodsProfitBean;
import tst.project.bean.goods.GoodsSupplyDataBean;
import tst.project.bean.goods.ShoppingCarBean;
import tst.project.bean.member.MemberBean;
import tst.project.bean.merchants.MerchantsBean;
import tst.project.bean.order.AssessmentBean;
import tst.project.bean.order.InviseBean;
import tst.project.bean.order.LogisticsBean;
import tst.project.bean.order.LogisticsDetailBean;
import tst.project.bean.order.OrderBean;
import tst.project.bean.order.OrderGoodsBean;
import tst.project.bean.order.OrderLineBean;
import tst.project.bean.order.OrderMerchantsBean;
import tst.project.bean.order.OrderParameterBean;
import tst.project.bean.order.OrderServiceBean;
import tst.project.bean.order.PayBean;
import tst.project.bean.order.PingOrderBean;
import tst.project.bean.order.PingSettingBean;
import tst.project.bean.order.RefundBean;
import tst.project.bean.order.RefundReasonBean;
import tst.project.bean.others.CodeBean;
import tst.project.bean.others.PercentBean;
import tst.project.page.PageBean;
import tst.project.service.interfaces.ActivityService;
import tst.project.service.interfaces.CodeService;
import tst.project.service.interfaces.GoodsServiceI;
import tst.project.service.interfaces.GoodsServiceI2;
import tst.project.service.interfaces.GoodsSupplyDataService;
import tst.project.service.interfaces.MemberService;
import tst.project.service.interfaces.OrderService;
import tst.project.service.interfaces.OthersService;
import tst.project.service.interfaces.ShoppingCarService;
import tst.project.utils.AlipayUtils;
import tst.project.utils.CreateRandom;
import tst.project.utils.ExcelUtils;
import tst.project.utils.HBRUtils;
import tst.project.utils.HttpUtils;
import tst.project.utils.MD5Util;
import tst.project.utils.NumberUtils;
import tst.project.utils.QRCodeUtils;
import tst.project.utils.RSAUtils;
import tst.project.utils.TimeUtils;
import tst.project.utils.XmlUtils;
import tst.project.webservice.controller.BaseController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pingplusplus.Pingpp;
import com.pingplusplus.model.Charge;


@Controller
@RequestMapping("/orderInterfaces.api")
public class OrderInterfaces extends BaseController {
	@Resource
	MemberService memberService;

	@Resource
	OrderService orderService;

	@Resource
	ActivityService activityService;

	@Resource
	OthersService othersService;
	
	@Resource
	GoodsServiceI goodsServiceI;
	
	@Resource
	GoodsServiceI2 goodsServiceI2;
	
	@Resource
	ShoppingCarService shoppingCarService;
	
	@Resource
	CodeService codeService;
	
	
	@Resource
	GoodsSupplyDataService goodsSupplyDataService;
	/**
	 * 设置订单是否开过发票
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "updateOrderInvise", method = RequestMethod.POST)
	public void updateOrderInvise(MemberBean memberBean, OrderBean orderBean,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		
		int num = orderService.updateOrderInvise(orderBean);
		if (num > 0) {
			WriteMsg(response, "设置成功");
		} else {
			WriteError(response, "设置失败");
		}
	}
	
	/**
	 * 提醒发货
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "remarkSendTime", method = RequestMethod.POST)
	public void remarkSendTime(MemberBean memberBean, OrderBean orderBean,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		OrderBean orderBean2=orderService.getOneOrderDetail(orderBean);
		if(orderBean2==null){
			WriteError(response, "此订单不存在");
			return;
		}
		
		if(!"wait_send".equals(orderBean2.getOrder_state())){
			WriteError(response, "此订单不可提醒");
			return;
		}
		
		if(TimeUtils.getDayCompareDate(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")
				,orderBean2.getRemark_send_time(), "yyyy-MM-dd HH:mm:ss")<1&&TimeUtils.getDayCompareDate(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")
						,orderBean2.getRemark_send_time(), "yyyy-MM-dd HH:mm:ss")>=0){
			WriteError(response, "24小时内只可提醒1次!");
			return;
		}
		
		int num = orderService.remarkSendTime(orderBean);
		if (num > 0) {
			WriteMsg(response, "提醒成功");
		} else {
			WriteError(response, "提醒失败");
		}
	}
	
	/**
	 * 确认发货
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "updateOrderLogisticsNo", method = RequestMethod.POST)
	public void updateOrderLogisticsNo(MemberBean memberBean, OrderBean orderBean,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		
		int num = orderService.confirmSendOrder(orderBean,false);
		if (num > 0) {
			WriteMsg(response, "确认成功");
		} else {
			WriteError(response, "确认失败");
		}
	}
	/**
	 * 确认发货
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "confirmSendOrder", method = RequestMethod.POST)
	public void confirmSendOrder(MemberBean memberBean, OrderBean orderBean,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}

		OrderBean orderBean2 = orderService.getOneOrderDetail(orderBean);
		if (orderBean2 == null) {
			WriteError(response, "无此订单");
			return;
		}
		if (!orderBean2.getOrder_state().equals("wait_send")) {
			WriteError(response, "此订单不是未发货状态");
			return;
		}

		int num = orderService.confirmSendOrder(orderBean.setOrder_state("wait_receive"),true);
		if (num > 0) {
			WriteMsg(response, "确认成功");
		} else {
			WriteError(response, "确认失败");
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getGoodsTimeRanking", method = RequestMethod.POST)
	public void getGoodsTimeRanking(MemberBean memberBean,
			GoodsProfitBean goodsProfitBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(goodsProfitBean.getStart_time()==null){
			WriteError(response, "请选择开始时间");
			return;
		}
		
		if(goodsProfitBean.getEnd_time()==null){
			WriteError(response, "请选择结束时间");
			return;
		}
		
		String start_time=goodsProfitBean.getStart_time()+" 00:00:00";
		String end_time=goodsProfitBean.getEnd_time()+" 23:59:00";

		int day=TimeUtils.getDayCompareDate(end_time, start_time, "yyyy-MM-dd HH:mm:ss");
		PercentBean percentBean=othersService.getPercent(new PercentBean().setPercent_type("time_ranking"));
		if(day>NumberUtils.Float(percentBean.getPercent_value())){
			WriteError(response, "超过最大查询天数!");
			return;
		}
				
		WriteObject(response, orderService.getGoodsTimeRanking(goodsProfitBean));
	}
	
	
	/**
	 * 会员型号成交最低单价
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getGoodsUtilPriceMin", method = RequestMethod.POST)
	public void getGoodsUtilPriceMin(MemberBean memberBean,
			GoodsProfitBean goodsProfitBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WriteObject(response, orderService.getGoodsUtilPriceMin(goodsProfitBean));
	}
	
	
	/**
	 * 会员型号成交最高单价
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getGoodsUtilPriceMax", method = RequestMethod.POST)
	public void getGoodsUtilPriceMax(MemberBean memberBean,
			GoodsProfitBean goodsProfitBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WriteObject(response, orderService.getGoodsUtilPriceMax(goodsProfitBean));
	}
	
	
	/**
	 * 会员品牌统计
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getMerchantsBrandStatistics", method = RequestMethod.POST)
	public void getMerchantsBrandStatistics(MemberBean memberBean,
			GoodsProfitBean goodsProfitBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MemberBean memberBean2=memberService.verificationToken(memberBean);
		if (memberBean2 == null) {
			WritePending(response, "token failed");
			return;
		}
		WriteObject(response, orderService
				.getMerchantsBrandStatistics(goodsProfitBean.setMerchants_id(memberBean2.getMerchants_id())));
	}
	
	/**
	 * 所有品牌统计
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getGoodsBrandRanking", method = RequestMethod.POST)
	public void getGoodsBrandRanking(MemberBean memberBean,
			GoodsProfitBean goodsProfitBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MemberBean memberBean2=memberService.verificationToken(memberBean);
		if (memberBean2 == null) {
			WritePending(response, "token failed");
			return;
		}
		WriteObject(response, orderService.getGoodsBrandRanking(goodsProfitBean));
	}
	
	
	/**
	 * 所有品牌统计
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getGoodsBrandRankingNo1", method = RequestMethod.POST)
	public void getGoodsBrandRankingNo1(MemberBean memberBean,
			GoodsProfitBean goodsProfitBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		WriteObject(response, orderService.getGoodsBrandRankingNo1(goodsProfitBean));
	}
	
	
	/**
	 * 单个品牌统计
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getOneGoodsBrandStatistics", method = RequestMethod.POST)
	public void getOneGoodsBrandStatistics(MemberBean memberBean,
			GoodsProfitBean goodsProfitBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MemberBean memberBean2=memberService.verificationToken(memberBean);
		if (memberBean2 == null) {
			WritePending(response, "token failed");
			return;
		}
		WriteObject(response, orderService
				.getOneGoodsBrandStatistics(goodsProfitBean.setMerchants_id(memberBean2.getMerchants_id())));
	}
	
	/**
	 * 获得商家的订单列表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getMerchantsOrders", method = RequestMethod.POST)
	public void getMerchantsOrders(MemberBean memberBean,
			OrderBean orderBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MemberBean memberBean2=memberService.verificationToken(memberBean);
		if (memberBean2 == null) {
			WritePending(response, "token failed");
			return;
		}
		WriteObject(response, orderService.getMerchantsOrders(orderBean.setMerchants_id(memberBean2.getMerchants_id()),pageBean),pageBean.getTotal());
	}
	
	/**
	 * 获得订单列表
	 * 
	 * @param memberBean
	 * @param orderBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "exportMerchantsOrders", method = RequestMethod.POST)
	public void exportMerchantsOrders(MemberBean memberBean, OrderBean orderBean, PageBean pageBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MemberBean memberBean2=memberService.verificationToken(memberBean);
		if (memberBean2 == null) {
			WritePending(response, "token failed");
			return;
		}
		
		String fileName=TimeUtils.getCurrentTime("yyyyMMddHHmmss")+CreateRandom.createRandom(true, 6)+".xls";
		
		List<ExcelBean> excelBeans=new ArrayList<ExcelBean>();
		excelBeans.add(new ExcelBean().setName("用户ID").setType("member_id"));
		excelBeans.add(new ExcelBean().setName("昵称").setType("nick_name"));
		excelBeans.add(new ExcelBean().setName("交易号").setType("order_no"));
		excelBeans.add(new ExcelBean().setName("实收金额").setType("order_actual_price"));
		excelBeans.add(new ExcelBean().setName("商品名称").setType("goods_name"));
		excelBeans.add(new ExcelBean().setName("购买数量").setType("goods_num"));
		excelBeans.add(new ExcelBean().setName("销售价").setType("goods_unit_price"));
		
		excelBeans.add(new ExcelBean().setName("品牌").setType("brand_name"));
		excelBeans.add(new ExcelBean().setName("最小购买量").setType("min_buy_num"));
		excelBeans.add(new ExcelBean().setName("最小包装量").setType("min_pack_num"));
		excelBeans.add(new ExcelBean().setName("交期").setType("give_time"));
		excelBeans.add(new ExcelBean().setName("类型").setType("data_categories"));

		excelBeans.add(new ExcelBean().setName("订单状态").setType("order_state_show"));
		excelBeans.add(new ExcelBean().setName("邮费").setType("express_price"));
		excelBeans.add(new ExcelBean().setName("收件地址").setType("detailed_address"));
		excelBeans.add(new ExcelBean().setName("收件人").setType("name"));
		excelBeans.add(new ExcelBean().setName("电话").setType("mobile"));
		excelBeans.add(new ExcelBean().setName("快递公司").setType("logistics_pinyin"));
		excelBeans.add(new ExcelBean().setName("备注").setType("custom_remark"));
		
		excelBeans.add(new ExcelBean().setName("下单时间").setType("create_time"));
		List<Object> orderBeans=orderService.exportMerchantsOrders(orderBean.setMerchants_id(memberBean2.getMerchants_id()));
		
		boolean is_success=ExcelUtils.exportExcel(request.getSession().getServletContext()
				.getRealPath("/")+"/excel/"+fileName, excelBeans,orderBeans,response);
		if(is_success){
			WriteMsg(response, "/excel/"+fileName);
		}else{
			WriteError(response, "导出失败");
		}
		
	}
	
	/**
	 * 拒绝订单
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "refuseOrder", method = RequestMethod.POST)
	public void refuseOrder(MemberBean memberBean,
			OrderBean orderBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		int num=orderService.refuseOrder(orderBean);
		if(num>0){
			WriteMsg(response, "拒绝成功");
			int num1 = orderService.updateRefuseReason(orderBean);
				if(num1>0){
				}
		}else{
			WriteError(response, "拒绝失败");
		}
	}
	
	/**
	 * 通过订单
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "acceptOrder", method = RequestMethod.POST)
	public void acceptOrder(MemberBean memberBean,
			OrderBean orderBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
				
		int num=orderService.acceptOrder(orderBean);
		if(num>0){
			WriteMsg(response, "通过成功");
		}else{
			WriteError(response, "通过失败");
		}
	}
	
	
	/**
	 * 上月销量冠军会员
	 * @param orderLineBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getMerchantsProgressNo1", method = RequestMethod.POST)
	public void getMerchantsProgressNo1(GoodsProfitBean goodsProfitBean,PageBean pageBean
			,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String profit_time=TimeUtils.getAchTime(TimeUtils.getLastMonthBegin("yyyy-MM-01 00:00:00", 3));
		WriteObject(response, orderService.getMerchantsProgressNo1(goodsProfitBean.setProfit_time(profit_time)));
	}
	
	/**
	 * 上月销量冠军会员
	 * @param orderLineBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getMerchantsCountNo1", method = RequestMethod.POST)
	public void getMerchantsCountNo1(GoodsProfitBean goodsProfitBean,PageBean pageBean
			,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String profit_time=TimeUtils.getAchTime(TimeUtils.getLastMonthBegin("yyyy-MM-01 00:00:00", 3));
		WriteObject(response, orderService.getMerchantsCountNo1(goodsProfitBean.setProfit_time(profit_time)));
	}
	
	/**
	 * 上月销量冠军会员
	 * @param orderLineBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getMerchantsSalesNo1", method = RequestMethod.POST)
	public void getMerchantsSalesNo1(GoodsProfitBean goodsProfitBean,PageBean pageBean
			,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String profit_time=TimeUtils.getAchTime(TimeUtils.getLastMonthBegin("yyyy-MM-01 00:00:00", 3));
		WriteObject(response, orderService.getMerchantsSalesNo1(goodsProfitBean.setProfit_time(profit_time)));
	}
	
	/**
	 * 商家销量排行 按月来
	 * @param orderLineBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getMerchantsSalesRanking", method = RequestMethod.POST)
	public void getMerchantsSalesRanking(GoodsProfitBean goodsProfitBean,PageBean pageBean
			,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String profit_time=TimeUtils.getAchTime(TimeUtils.getLastMonthBegin("yyyy-MM-01 00:00:00", 3));
		WriteObject(response, orderService.getMerchantsSalesRanking(goodsProfitBean.setProfit_time(profit_time),pageBean),pageBean.getTotal());
	}
	
	/**
	 * 物流列表
	 * @param orderLineBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getOrderlogistics", method = RequestMethod.POST)
	public void test(LogisticsBean logisticsBean,HttpServletRequest request,HttpServletResponse response) throws Exception{

		WriteObject(response, orderService.getOrderlogistics(logisticsBean));
	}

	
	/**
	 * 获得订单发票内容
	 * @param orderLineBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getOrderInviseContents", method = RequestMethod.POST)
	public void getOrderInviseContents(InviseBean inviseBean,HttpServletRequest request,HttpServletResponse response) throws Exception{
		WriteObject(response, orderService.getOrderInviseContents(inviseBean));
	}

	
	@RequestMapping(params = "test", method = RequestMethod.POST)
	public void test(MemberBean memberBean, 
			OrderLineBean getOrderLine,PageBean pageBean,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String appid = "wx0bb583c2e89057d8"; 
	    String paternerKey = "4c6e02c0bc38b3557a27e552789c30e1"; 
	    String attach="测试";  
	    String body="测试购买支付";  
	    String mch_id="1231009902";  
	    String nonce_str=CreateRandom.createRandom(false, 32);  
	    String openid="oMLXUs8BZLBhgpdQYeqkk0SC8f8A";  
	    String out_trade_no= TimeUtils.getCurrentTime("yyyyMMddHHmmss");  
	    String spbill_create_ip=HttpUtils.getRemortIP(request);  
	    String total_fee="1"; 
	    String trade_type="JSAPI";
	    String notify_url="http://hbr.tstweiguanjia.com/orderInterfaces.api?test";  
	    String sign_type="MD5";  
	    
	    Map<String, String> paraMap = new HashMap<String, String>(); 
	    paraMap.put("appid", appid); 
	    paraMap.put("attach",attach); 
	    paraMap.put("body", body); 
	    paraMap.put("mch_id", mch_id); 
	    paraMap.put("nonce_str",nonce_str); 
	   
	    paraMap.put("openid",openid); 
	    paraMap.put("out_trade_no", out_trade_no); 
	    paraMap.put("spbill_create_ip", spbill_create_ip); 
	    paraMap.put("total_fee",total_fee); 
	    paraMap.put("trade_type",trade_type); 
	    paraMap.put("notify_url", notify_url);
	    paraMap.put("sign_type", sign_type); 
	    
	    
	    String wait_sign="appid="+appid
	    			+"&attach="+attach
	    			+"&body="+body
	    			+"&mch_id="+mch_id
	    			+"&nonce_str="+nonce_str
	    			+"&notify_url="+notify_url
	    			+"&openid="+openid
	    			+"&out_trade_no="+out_trade_no
	    			+"&sign_type="+sign_type
	    			+"&spbill_create_ip="+spbill_create_ip
	    			+"&total_fee="+total_fee
	    			+"&trade_type="+trade_type
	    			+"&key="+paternerKey;
	    
	    String sign=MD5Util.md5EncodeOrigin(wait_sign).toUpperCase();
	    
	    paraMap.put("sign", sign); 
	    
	 // 统一下单 https://apimchweixinqqcom/pay/unifiedorder 
	    String url = "https://api.mch.weixin.qq.com/pay/unifiedorder"; 
	  
	    String xml = XmlUtils.ArrayToXml(paraMap); 
	    
	    String pre_id = HttpUtils.testPost(url,xml);
	    
	    WriteOnlyMsg(response, pre_id+"==="+xml);
	}


	/**
	 * 获得线下订单
	 * @param orderLineBean
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "getOrderLines", method = RequestMethod.POST)
	public void getOrderLines(MemberBean memberBean, 
			OrderLineBean getOrderLine,PageBean pageBean,HttpServletRequest request,HttpServletResponse response){
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		
		WriteObject(response, orderService.getOrderLines(getOrderLine,pageBean));
	}
	
	/**
	 * 获得订单原因列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getRefundsReasons", method = RequestMethod.POST)
	public void getRefundsReasons(MemberBean memberBean, RefundReasonBean refundReasonBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WriteObject(response, orderService.getRefundsReasons(refundReasonBean));
	}
	
	/**
	 * 用户的退款列表 各个状态统计
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getMemberRefundCount", method = RequestMethod.POST)
	public void getMemberRefundCount(MemberBean memberBean, PageBean pageBean,RefundBean refundBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		WriteObject(response, orderService.getMemberRefundCount(refundBean));
	}

	/**
	 * 获得退款订单列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getMemberRefunds", method = RequestMethod.POST)
	public void getRefundOrders(MemberBean memberBean, PageBean pageBean,RefundBean refundBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		WriteObject(response, orderService.getMemberRefunds(refundBean,pageBean),pageBean.getTotal());
	}

	/**
	 * 获得退款订单列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getRefundDetail", method = RequestMethod.POST)
	public void getRefundDetail(MemberBean memberBean, RefundBean refundBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}

		WriteObject(response, orderService.getRefundDetail(refundBean));
	}

	/**
	 * 订单退款(图片路径)
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "refundOrderNoFile", method = RequestMethod.POST)
	public void refundOrderNoFile(MemberBean memberBean, RefundBean refundBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		MemberBean memberBean2 = memberService.verificationToken(memberBean);
		if (memberBean2 == null) {
			WritePending(response, "token failed");
			return;
		}

		RefundBean refundBean2 = orderService.getRefundByGoods(refundBean);
		if (refundBean2 != null) {
			WriteError(response, "此订单商品已申请退款");
			return;
		}

		OrderBean orderBean1 = orderService
				.getOneOrderDetail(new OrderBean().setOrder_id(Integer.valueOf(refundBean.getOrder_id())));
		if (orderBean1 == null) {
			WriteError(response, "此订单不存在");
			return;
		}

		if (orderBean1.getOrder_state().equals("cancle") || orderBean1.getOrder_state().equals("wait_pay")
				|| orderBean1.getOrder_state().equals("end")) {
			WriteError(response, "此订单状态不可申请退款");
			return;
		}

		OrderGoodsBean orderGoodsBean = orderService
				.getOrderGoodssByGoods(new OrderGoodsBean().setOrder_id(refundBean.getOrder_id())
						.setOrder_goods_id(Integer.valueOf(refundBean.getOrder_goods_id())));
		if (orderGoodsBean == null) {
			WriteError(response, "此订单已不存在");
			return;
		}

		if (Integer.valueOf(orderGoodsBean.getGoods_num()) < Integer.valueOf(refundBean.getRefund_count())) {
			WriteError(response, "退款数量大于购买数量了");
			return;
		}

		float refund_price = 0;// 需要返回用户金钱

		if (orderBean1.getOrder_type().equals("goods")) {
			refund_price += NumberUtils.Float(orderGoodsBean.getGoods_price())
					* Float.valueOf(refundBean.getRefund_count());
		} else if (orderBean1.getOrder_type().equals("group_buy")) {
			refund_price +=NumberUtils.Float(orderGoodsBean.getGroup_buy_price())
					* Float.valueOf(refundBean.getRefund_count());
		} else if (orderBean1.getOrder_type().equals("time_limit")) {
			refund_price += NumberUtils.Float(orderGoodsBean.getPromotion_price())
					* Float.valueOf(refundBean.getRefund_count());
		}

		List<OrderParameterBean> orderParameterBeans = orderGoodsBean.getOrderParameterBeans();
		for (int i = 0; i < orderParameterBeans.size(); i++) {
			refund_price += NumberUtils.Float(orderParameterBeans.get(i).getParameter_price())
					* NumberUtils.Float(refundBean.getRefund_count());
		}

		List<OrderServiceBean> orderServiceBeans = orderGoodsBean.getOrderServiceBeans();
		for (int i = 0; i < orderServiceBeans.size(); i++) {
			refund_price += NumberUtils.Float(orderServiceBeans.get(i).getService_price())
					* NumberUtils.Float(refundBean.getRefund_count());
		}

		// if("1".equals(orderBean1.getIs_free_express())){//邮费都不退。。。
		//
		// }
		//
		// String member_coupon_id="";
		// if(orderBean1.getMember_coupon_id()!=null&&!orderBean1.getMember_coupon_id().equals("")){//使用了优惠卷
		// 先退还优惠卷
		// if(orderBean1.getRefund_coupon_id()==null||orderBean1.getRefund_coupon_id().equals("")){//未在退款中使用过
		// float coupon_price=Float.valueOf(orderBean1.getCoupon_price());
		// if(refund_price>=coupon_price){//并且退还金额大于优惠卷优惠金额
		// member_coupon_id=orderBean1.getMember_coupon_id();
		// refund_price-=coupon_price;
		// }
		// }
		// }
		//
		// float refund_deduct_integral=0;//需要返回用户积分
		// float refund_give_integral=0;//需要返回平台积分
		//
		// float order_deduct_integral=0;//订单用户使用抵扣的积分值
		// //float order_deduct_price=0;//订单用户积分抵扣的钱
		// float order_deduct_percent=0;
		// if("1".equals(orderGoodsBean.getIs_give_integral())){//商品赠送积分了
		// refund_give_integral=Float.valueOf(orderGoodsBean.getGive_integral_value())*Integer.valueOf(refundBean.getRefund_count());
		// }
		//
		// if("1".equals(orderBean1.getIs_deduct_integral())){//订单抵扣积分了
		// order_deduct_integral=Float.valueOf(orderBean1.getDeduct_integral_value())-
		// Float.valueOf(orderBean1.getRefund_deduct_integral());
		// //order_deduct_price=Float.valueOf(orderBean1.getDeduct_integral_price());
		// order_deduct_percent=Float.valueOf(orderBean1.getDeduct_integral_percent());
		// }
		//
		// if(order_deduct_integral>(refund_price*100)/order_deduct_percent){//抵扣的积分
		// 大于退款的金额
		// refund_price=0;//则无需返回钱给用户
		// refund_deduct_integral=(refund_price*100)/order_deduct_percent;//返还的积分应该是现金的钱
		// }else{
		// refund_deduct_integral=order_deduct_integral;
		// refund_price-=order_deduct_integral*order_deduct_percent/100;//积分全退
		// 返还的钱减去积分的钱
		// }
		//
		// float member_integral=Float.valueOf(memberBean2.getIntegral());
		// if(member_integral+refund_deduct_integral<refund_give_integral){
		// WriteError(response, "非常抱歉您的积分已不足,申请失败");
		// return;
		// }

		String imgs = request.getParameter("imgs");
		
		
		String refund_no=TimeUtils.getCurrentTime("yyyyMMddHHmmss");
		int num = orderService.refundOrder(refundBean.
				setRefund_state("wait_review").setRefund_price(refund_price + "").setRefund_no(refund_no),
				imgs == null ? null : (imgs.length() > 0 ? imgs.split(",") : null));
		if (num >= 0) {
			WriteObject(response, "我们已收到您的退款申请了!请耐心等待啊");
		} else {
			WriteError(response, "哎呀!太遗憾了,我们没能成功收到您的退款申请!");
		}
	}

	/**
	 * 订单退款
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "refundOrder", method = RequestMethod.POST)
	public void refundOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HashMap<String, Object> maps = getJsonWithImgs(request, "/images/order");
		if (maps.get("result").equals("failed")) {
			WriteError(response, "上传失败");
			return;
		}

		MemberBean memberBean = new Gson().fromJson(maps.get("string").toString(), MemberBean.class);
		RefundBean refundBean = new Gson().fromJson(maps.get("string").toString(), RefundBean.class);
		List<String> mapFiles = (List<String>) maps.get("file");

		MemberBean memberBean2 = memberService.verificationToken(memberBean);
		if (memberBean2 == null) {
			WritePending(response, "token failed");
			return;
		}

		RefundBean refundBean2 = orderService.getRefundByGoods(refundBean);
		if (refundBean2 != null) {
			WriteError(response, "此订单商品已申请退款");
			return;
		}

		OrderBean orderBean1 = orderService
				.getOneOrderDetail(new OrderBean().setOrder_id(Integer.valueOf(refundBean.getOrder_id())));
		if (orderBean1 == null) {
			WriteError(response, "此订单不存在");
			return;
		}
		
		if (orderBean1.getOrder_state().equals("cancle") || orderBean1.getOrder_state().equals("wait_pay")
				|| orderBean1.getOrder_state().equals("end")) {
			WriteError(response, "此订单状态不可申请退款");
			return;
		}

		OrderGoodsBean orderGoodsBean = orderService
				.getOrderGoodssByGoods(new OrderGoodsBean().setOrder_id(refundBean.getOrder_id())
						.setOrder_goods_id(Integer.valueOf(refundBean.getOrder_goods_id())));
		if (orderGoodsBean == null) {
			WriteError(response, "此订单已不存在");
			return;
		}

		if (Integer.valueOf(orderGoodsBean.getGoods_num()) < Integer.valueOf(refundBean.getRefund_count())) {
			WriteError(response, "退款数量大于购买数量了");
			return;
		}

		float refund_price = 0;// 需要返回用户金钱

		if (orderBean1.getOrder_type().equals("goods")) {
			refund_price += Float.valueOf(orderGoodsBean.getGoods_price())
					* Float.valueOf(refundBean.getRefund_count());
		} else if (orderBean1.getOrder_type().equals("group_buy")) {
			refund_price += Float.valueOf(orderGoodsBean.getGroup_buy_price())
					* Float.valueOf(refundBean.getRefund_count());
		} else if (orderBean1.getOrder_type().equals("time_limit")) {
			refund_price += Float.valueOf(orderGoodsBean.getPromotion_price())
					* Float.valueOf(refundBean.getRefund_count());
		}

		List<OrderParameterBean> orderParameterBeans = orderGoodsBean.getOrderParameterBeans();
		for (int i = 0; i < orderParameterBeans.size(); i++) {
			refund_price += Float.valueOf(orderParameterBeans.get(i).getParameter_price())
					* Float.valueOf(refundBean.getRefund_count());
		}

		List<OrderServiceBean> orderServiceBeans = orderGoodsBean.getOrderServiceBeans();
		for (int i = 0; i < orderServiceBeans.size(); i++) {
			refund_price += Float.valueOf(orderServiceBeans.get(i).getService_price())
					* Float.valueOf(refundBean.getRefund_count());
		}

		// if("1".equals(orderBean1.getIs_free_express())){//邮费都不退。。。
		//
		// }
		//
		//
		// String member_coupon_id="";
		// if(orderBean1.getMember_coupon_id()!=null&&!orderBean1.getMember_coupon_id().equals("")){//使用了优惠卷
		// 先退还优惠卷
		// if(orderBean1.getRefund_coupon_id()==null||orderBean1.getRefund_coupon_id().equals("")){//未在退款中使用过
		// float coupon_price=Float.valueOf(orderBean1.getCoupon_price());
		// if(refund_price>=coupon_price){//并且退还金额大于优惠卷优惠金额
		// member_coupon_id=orderBean1.getMember_coupon_id();
		// refund_price-=coupon_price;
		// }
		// }
		// }
		//
		// float refund_deduct_integral=0;//需要返回用户积分
		// float refund_give_integral=0;//需要返回平台积分
		//
		//
		// float order_deduct_integral=0;//订单用户使用抵扣的积分值
		// //float order_deduct_price=0;//订单用户积分抵扣的钱
		// float order_deduct_percent=0;
		// if("1".equals(orderGoodsBean.getIs_give_integral())){//商品赠送积分了
		// refund_give_integral=Float.valueOf(orderGoodsBean.getGive_integral_value())*Integer.valueOf(refundBean.getRefund_count());
		// }
		//
		// if("1".equals(orderBean1.getIs_deduct_integral())){//订单抵扣积分了
		// order_deduct_integral=Float.valueOf(orderBean1.getDeduct_integral_value())-
		// Float.valueOf(orderBean1.getRefund_deduct_integral());
		// //order_deduct_price=Float.valueOf(orderBean1.getDeduct_integral_price());
		// order_deduct_percent=Float.valueOf(orderBean1.getDeduct_integral_percent());
		// }
		//
		// if(order_deduct_integral>(refund_price*100)/order_deduct_percent){//抵扣的积分
		// 大于退款的金额
		// refund_price=0;//则无需返回钱给用户
		// refund_deduct_integral=(refund_price*100)/order_deduct_percent;//返还的积分应该是现金的钱
		// }else{
		// refund_deduct_integral=order_deduct_integral;
		// refund_price-=order_deduct_integral*order_deduct_percent/100;//积分全退
		// 返还的钱减去积分的钱
		// }

		// float member_integral=Float.valueOf(memberBean2.getIntegral());
		// if(member_integral+refund_deduct_integral<refund_give_integral){
		// WriteError(response, "非常抱歉您的积分已不足,申请失败");
		// return;
		// }
		//
		int num = orderService.refundOrder(refundBean.setRefund_state("wait_review").setRefund_price(refund_price + ""),
				mapFiles);
		if (num >= 0) {
			WriteObject(response, "我们已收到您的退款申请了!请耐心等待啊");
		} else {
			WriteError(response, "哎呀!太遗憾了,我们没能成功收到您的退款申请!");
		}

	}

	/**
	 * 获得订单列表
	 * 
	 * @param memberBean
	 * @param orderBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getOrders", method = RequestMethod.POST)
	public void getOrders(MemberBean memberBean, OrderBean orderBean, PageBean pageBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		
		WriteObject(response, orderService.getOrders(orderBean, pageBean),pageBean.getTotal());
	}
	
	
	/**
	 * 获得订单列表
	 * 
	 * @param memberBean
	 * @param orderBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "exportOrders", method = RequestMethod.POST)
	public void exportOrders(MemberBean memberBean, OrderBean orderBean, PageBean pageBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		
		String fileName=TimeUtils.getCurrentTime("yyyyMMddHHmmss")+CreateRandom.createRandom(true, 6)+".xls";
		
		List<ExcelBean> excelBeans=new ArrayList<ExcelBean>();
		excelBeans.add(new ExcelBean().setName("用户ID").setType("member_id"));
		excelBeans.add(new ExcelBean().setName("昵称").setType("nick_name"));
		excelBeans.add(new ExcelBean().setName("交易号").setType("order_no"));
		excelBeans.add(new ExcelBean().setName("实收金额").setType("order_actual_price"));
		excelBeans.add(new ExcelBean().setName("商品名称").setType("goods_name"));
		excelBeans.add(new ExcelBean().setName("购买数量").setType("goods_num"));
		excelBeans.add(new ExcelBean().setName("销售价").setType("goods_unit_price"));
		
		excelBeans.add(new ExcelBean().setName("品牌").setType("brand_name"));
		excelBeans.add(new ExcelBean().setName("最小购买量").setType("min_buy_num"));
		excelBeans.add(new ExcelBean().setName("最小包装量").setType("min_pack_num"));
		excelBeans.add(new ExcelBean().setName("交期").setType("give_time"));
		excelBeans.add(new ExcelBean().setName("类型").setType("data_categories"));

		excelBeans.add(new ExcelBean().setName("订单状态").setType("order_state_show"));
		excelBeans.add(new ExcelBean().setName("邮费").setType("express_price"));
		excelBeans.add(new ExcelBean().setName("收件地址").setType("detailed_address"));
		excelBeans.add(new ExcelBean().setName("收件人").setType("name"));
		excelBeans.add(new ExcelBean().setName("电话").setType("mobile"));
		excelBeans.add(new ExcelBean().setName("快递公司").setType("logistics_pinyin"));
		excelBeans.add(new ExcelBean().setName("备注").setType("custom_remark"));
		
		excelBeans.add(new ExcelBean().setName("下单时间").setType("create_time"));

		List<Object> orderBeans=orderService.exportOrders(orderBean);
		
		boolean is_success=ExcelUtils.exportExcel(request.getSession().getServletContext()
				.getRealPath("/")+"/excel/"+fileName, excelBeans,orderBeans,response);
		if(is_success){
			WriteMsg(response, "/excel/"+fileName);
		}else{
			WriteError(response, "导出失败");
		}
		
	}
	/**
	 * 订单发票内容
	 * 
	 * @param memberBean
	 * @param orderBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getInviseOrders", method = RequestMethod.POST)
	public void getInviseOrders(MemberBean memberBean, OrderBean orderBean, PageBean pageBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		WriteObject(response, orderService.getInviseOrders(orderBean, pageBean),pageBean.getTotal());
	}
	/**
	 * 获得订单列表 每个状态统计
	 * 
	 * @param memberBean
	 * @param orderBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getOrdersCount", method = RequestMethod.POST)
	public void getOrdersCount(MemberBean memberBean, OrderBean orderBean, PageBean pageBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		MemberBean memberBean2=memberService.verificationToken(memberBean) ;
		if (memberBean2 == null) {
			WritePending(response, "token failed");
			return;
		}

		WriteObject(response, orderService.getOrdersCount(orderBean.setMerchants_id(memberBean2.getMerchants_id())));
	}

	/**
	 * 获得单个订单详情
	 * 
	 * @param memberBean
	 * @param orderBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getOneOrderDetail", method = RequestMethod.POST)
	public void getOneOrderDetail(MemberBean memberBean, OrderBean orderBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}

		WriteObject(response, orderService.getOneOrderDetail(orderBean));
	}

	/**
	 * 订单物流详情
	 * 
	 * @param orderLineBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getOrderLogisticsDetails", method = RequestMethod.POST)
	public void getOrderLogisticsDetails(MemberBean memberBean,LogisticsDetailBean logisticsDetailBean,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		
		WriteObject(response, orderService.getOrderLogisticsDetails(logisticsDetailBean));
	}
	
	/** 余额充值订单
	 * 
	 * @param goodsBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "insertRechargeOrder", method = RequestMethod.POST)
	public void insertRechargeOrder(MemberBean memberBean,PayBean payBean,OrderBean orderBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try{
			MemberBean memberBean2=memberService.verificationToken(memberBean);
			if (memberBean2 == null) {
				WritePending(response, "token failed");
				return;
			}
			if(NumberUtils.Integer(orderBean.getOrder_total_price())<0){
				WriteError(response, "充值金额非法");
				return;
			}
			
			String order_id = orderService.insertRechargeOrder(orderBean);
			
			
			
			PingSettingBean pingSettingBean=othersService.getPingSetting(new PingSettingBean().setPing_type("1"));
			
			Pingpp.apiKey = pingSettingBean.getPing_app_key();
			

			Pingpp.privateKeyPath = getClass().getResource("/").getFile().toString() + "/rsa_private_key.pem";
			Map<String, Object> chargeParams = new HashMap<String, Object>();

			String order_pay_no = order_id + TimeUtils.getCurrentTime("HHmmss");

			chargeParams.put("order_no", order_pay_no);
			chargeParams.put("amount",NumberUtils.Double( orderBean.getOrder_total_price())*100);// total_price*100
			Map<String, String> app = new HashMap<String, String>();

			app.put("id", pingSettingBean.getPing_app_id());

			chargeParams.put("app", app);
			chargeParams.put("channel", payBean.getChannel());
			chargeParams.put("currency", "cny");
			chargeParams.put("client_ip", request.getRemoteAddr());
			chargeParams.put("subject", "余额充值");
			chargeParams.put("body", "余额充值");

			if (payBean.getChannel().equals("wx_pub")) {
				Map<String, Object> extra = new HashMap<String, Object>();
				extra.put("open_id", memberBean2.getWx_pub_openid());// 用户在商户微信公众号下的唯一标识，获取方式可参考
																		// WxPubOAuthExample.java
				chargeParams.put("extra", extra);
			}else if (payBean.getChannel().equals("alipay_pc_direct")) {
				HostBean hostBean=othersService.getHost(new HostBean().setHost_type("1"));
				Map<String, Object> extra = new HashMap<String, Object>();
				extra.put("success_url", hostBean.getHost_url()+"/pay_end.html");
				chargeParams.put("extra", extra);
			}else if (payBean.getChannel().equals("wx_pub_qr")) {
				Map<String, Object> extra = new HashMap<String, Object>();
				extra.put("product_id", CreateRandom.createRandom(false, 20));// 用户在商户微信公众号下的唯一标识，获取方式可参考
																		// WxPubOAuthExample.java
				chargeParams.put("extra", extra);
			}
			
			Charge charge = Charge.create(chargeParams);
			orderService.updateOrderDetail(new OrderBean()
						.setOrder_id(Integer.valueOf(order_id))
						.setOrder_pay_no(charge.getId()));
			
			if(payBean.getChannel().equals("wx_pub_qr")){
				String file_name=TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+CreateRandom.createRandom(true, 6)+".png";
				QRCodeUtils.CreateQrcode(request, "/images/qrcode/wx_pub_qr/"+file_name, charge.getCredential().get("wx_pub_qr").toString());
				WriteMsg(response, "/images/qrcode/wx_pub_qr/"+file_name);	
			}else{
				WriteObject(response, charge);					
			}
		}catch(Exception e){
			WriteError(response, e.getMessage());
		}
	}
	 
	 
	/**
	 * 下单
	 * 
	 * @param goodsBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "insertOrder", method = RequestMethod.POST)
	public void insertOrder(MemberBean memberBean, OrderBean orderBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//try{
			if (memberService.verificationToken(memberBean) == null) {
				WritePending(response, "token failed");
				return;
			}
			String json = request.getParameter("json");
			OrderMerchantsBean orderMerchantsBean = new Gson().fromJson(json, OrderMerchantsBean.class);
			
			Map<String,String> map = orderService.insertOrder(orderMerchantsBean);
			if (map==null) {
				WriteError(response, "未选择商品");
			} else {
				WriteMsg(response, map.get("order_ids"));
			}
//		}catch(Exception e){
//			WriteError(response, e.getMessage());
//		}
	}


	/**
	 * 下单
	 * 
	 * @param goodsBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "insertOrderHBR", method = RequestMethod.POST)
	public void insertOrderHBR(MemberBean memberBean, OrderBean orderBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try{
			if (memberService.verificationToken(memberBean) == null) {
				WritePending(response, "token failed");
				return;
			}
			String json = request.getParameter("json");
			OrderMerchantsBean orderMerchantsBean = new Gson().fromJson(json, OrderMerchantsBean.class);
			
			String num = orderService.insertOrderHBR(orderMerchantsBean);
			if (num.equals("-1000")) {
				WriteError(response, "未选择商品");
			} else if (num.equals("-2000")) {
				WriteError(response, "地址已不存在");
			} else if (num.equals("-3000")) {
				WriteError(response, "此商家已下架或已删除");
			} else if (num.equals("-1")) {
				WriteError(response, "下单失败");
			} else {
				WriteMsg(response, num);
			}
		}catch(Exception e){
			WriteError(response, e.getMessage());
		}
	}
	
	/**
	 * 取消订单
	 * 
	 * @param memberBean
	 * @param orderBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "cancelOrder", method = RequestMethod.POST)
	public void cancelOrder(MemberBean memberBean, OrderBean orderBean, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if (memberService.verificationToken(memberBean) == null) {
				WritePending(response, "token failed");
				return;
			}

			OrderBean orderBean1 = orderService.getOneOrderDetail(orderBean);
			if(orderBean1==null){
				WriteError(response, "此订单不存在");
				return;
			}
			if (orderBean1.getOrder_state().equals("wait_pay")|| orderBean1.getOrder_state().equals("wait_review")) {
				
			}else{
				WriteError(response, "此状态不可取消订单");
				return;
			}

			int num = orderService.cancelOrder(orderBean1);
			if (num > 0) {
				WriteMsg(response, "取消成功");
			} else {
				WriteError(response, "取消失败");
			}
		} catch (Exception e) {
			WriteError(response, e.getMessage() == null ? "取消失败" : e.getMessage().toString());
		}
	}
	/**
	 * 恢复订单
	 * @param memberBean
	 * @param orderBean
	 * @param request
	 * @param response
	 * @param orderGoodsBean
	 * @throws Exception
	 */
	@RequestMapping(params="recoverylOrder", method=RequestMethod.POST)
	 public void recoverylOrder(MemberBean memberBean, OrderBean orderBean, HttpServletRequest request, HttpServletResponse response, OrderGoodsBean orderGoodsBean)
			    throws Exception
			  {
			    if (memberService.verificationToken(memberBean) == null) {
			      WritePending(response, "token failed");
			      return;
			    }
			    String cur_time = TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			    OrderBean orderBeans = orderService.getOneOrderDetail(orderBean);
			    OrderGoodsBean orderGoodsBeans = orderService.getOneOrderGoodsByGoods(new OrderGoodsBean().setOrder_id(orderBeans.getOrder_id()+""));
			    System.out.println("goods_id:" + orderGoodsBeans.getGoods_id());
			    System.out.println("supply_id:" + orderGoodsBeans.getGoods_id());
			    System.out.println("goods_num:" + orderGoodsBeans.getGoods_num());
			    System.out.println("create_time:" + cur_time);
			    GoodsSupplyDataBean goodsSupplyDataBean = goodsSupplyDataService
			      .getGoodsSupplyDataByID(new GoodsSupplyDataBean()
			      .setSupply_id(Integer.valueOf(NumberUtils.Integer(orderGoodsBeans.getSupply_id()))));
			    if (orderGoodsBeans.getGoods_num() > goodsSupplyDataBean.getGoods_stock()) {
			      System.out.println("供应库存：" + goodsSupplyDataBean.getGoods_stock());
			      WriteObject(response,"库存不足，恢复失效！");
			      return;
			    }
			    if ((orderGoodsBeans.getGoods_unit_price() != goodsSupplyDataBean.getGoods_unit_price()) || 
			      (orderGoodsBeans.getMin_buy_num() != goodsSupplyDataBean.getMin_buy_num())) {
			      
			      int num = shoppingCarService.insertShoppingCar(new ShoppingCarBean().setMember_id(orderBeans.getMember_id()).setMerchants_id(orderBeans.getMerchants_id())
			        .setGoods_id(orderGoodsBeans.getGoods_id()).setGoods_num(orderGoodsBeans.getGoods_num()+"").setSupply_id(orderGoodsBeans.getSupply_id())
			        .setCreate_time(cur_time));
			      if (num > 0)
			        System.out.println("添加成功！");
			      else {
			        throw new Exception("添加失败！");
			      }
			    }
			    WriteObject(response, "恢复成功");
			  }
	/**
	 * 取消 已付款  待发货订单
	 * 
	 * @param memberBean
	 * @param orderBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "cancelPayOrder", method = RequestMethod.POST)
	public void cancelPayOrder(MemberBean memberBean, OrderBean orderBean, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if (memberService.verificationToken(memberBean) == null) {
				WritePending(response, "token failed");
				return;
			}

			OrderBean orderBean1 = orderService.getOneOrderDetail(orderBean);
			if (orderBean1 != null && !orderBean1.getOrder_state().equals("wait_send")) {
				WriteError(response, "此状态不可取消订单");
				return;
			}

			int num = orderService.cancelPayOrder(orderBean1);
			if (num > 0) {
				WriteMsg(response, "取消成功");
			} else {
				WriteError(response, "取消失败");
			}
		} catch (Exception e) {
			WriteError(response, e.getMessage() == null ? "取消失败" : e.getMessage().toString());
		}
	}

	/**
	 * 删除已完成订单
	 * 
	 * @param memberBean
	 * @param orderBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "deleteOrders", method = RequestMethod.POST)
	public void deleteOrders(MemberBean memberBean, OrderBean orderBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}

		int num = orderService.deleteOrders(orderBean);
		if (num > 0) {
			WriteMsg(response, "删除成功");
		} else {
			WriteError(response, "删除失败");
		}
	}

	
	/**
	 * 删除已完成订单
	 * 
	 * @param memberBean
	 * @param orderBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "deleteOrder", method = RequestMethod.POST)
	public void deleteOrder(MemberBean memberBean, OrderBean orderBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}

		OrderBean orderBean1 = orderService.getOneOrderDetail(orderBean);
		if (orderBean1 != null && orderBean1.getOrder_state().equals("end")
				&&!orderBean1.getOrder_state().equals("cancel")&&!orderBean1.getOrder_state().equals("wait_assessment")) {
			WriteError(response, "此状态订单不可删除");
			return;
		}

		if(orderBean1 == null){
			WriteError(response, "此订单不存在");
			return;
		}else if(orderBean1.getOrder_state().equals("end")){
			
		}else if(orderBean1.getOrder_state().equals("cancel")){
			
		}else if(orderBean1.getOrder_state().equals("wait_assessment")){
			
		}else{
			WriteError(response, "此状态订单不可删除");  
			return;
		}
		int num = orderService.deleteOrder(orderBean);
		if (num > 0) {
			WriteMsg(response, "删除成功");
		} else {
			WriteError(response, "删除失败");
		}
	}
	/**
	 * 删除订单中的型号
	 * @param memberBean
	 * @param orderGoodsBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="deleteGoodsByOrder", method=RequestMethod.POST)
	  public void deleteGoodsByOrder(MemberBean memberBean, OrderGoodsBean orderGoodsBean, HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	  {
	    if (memberService.verificationToken(memberBean) == null) {
	      WritePending(response, "token failed");
	      return;
	    }
	    int num = orderService.deleteGoodsByOrder(orderGoodsBean);
	    OrderGoodsBean orderGoodsBean1 = orderService.getOrderId(orderGoodsBean);
	    if (num > 0) {
	      System.out.println("order_id=====" + orderGoodsBean1.getOrder_id());
	      List<OrderGoodsBean> orderGoodsBean2 = orderService.getOrderGoodsBean(orderGoodsBean.setOrder_id(orderGoodsBean1.getOrder_id()));
	      OrderBean orderBeans = orderService.getOneOrderDetail(new OrderBean().setOrder_id(Integer.valueOf(orderGoodsBean1.getOrder_id()).intValue()));
	      double order_total_actual_price = 0.0D;
	      if (orderGoodsBean2 != null) {
	        for (int i = 0; i < orderGoodsBean2.size(); i++) {
	          GoodsSupplyDataBean goodsSupplyDataBean = goodsSupplyDataService
	            .getGoodsSupplyDataByID(new GoodsSupplyDataBean()
	            .setSupply_id(Integer.valueOf(NumberUtils.Integer(((OrderGoodsBean)orderGoodsBean2.get(i)).getSupply_id()))));
	          float order_total_price = 0.0F;
	          float order_actual_price = 0.0F;

	          order_total_price = order_total_price + Float.valueOf(goodsSupplyDataBean.getGoods_unit_price()).floatValue() * 
	            Float.valueOf(((OrderGoodsBean)orderGoodsBean2.get(i)).getGoods_num()).floatValue();

	          order_actual_price = order_actual_price + Float.valueOf(goodsSupplyDataBean.getGoods_unit_price()).floatValue() * 
	            Float.valueOf(((OrderGoodsBean)orderGoodsBean2.get(i)).getGoods_num()).floatValue();

	          double invise_price = 0.0D;
	          invise_price = order_actual_price * 17.0F / 100.0F;
	          order_actual_price = (float)(order_actual_price + invise_price);
	          order_total_price = (float)(order_total_price + invise_price);
	          order_total_actual_price += order_actual_price;
	          int n = orderService.updateOrderDetail(
	        	orderBeans.setOrder_total_price(NumberUtils.KeepDecimal(order_total_actual_price, 2)+"")
	            .setOrder_actual_price(order_total_actual_price+"").setInvise_price(invise_price+""));
	          if (n <= 0) {
	            throw new Exception("订单更新失败");
	          }
	        }
	      }
	      WriteMsg(response, "删除成功");
	    } else {
	      WriteError(response, "删除失败");
	    }
	  }

	
	/**
	 * 付款订单
	 * 
	 * @param memberBean
	 * @param orderBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "payOrder", method = RequestMethod.POST)
	public void payOrder(MemberBean memberBean, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		
		// OrderBean orderBean1 = orderService.getOneOrderDetail(orderBean);
		// if (orderBean1 != null &&
		// !orderBean1.getOrder_state().equals("wait_pay")) {
		// WriteError(response, "此状态不可付款下单");
		// return;
		// }

		String[] order_ids = request.getParameter("order_ids").split(",");
		int num = orderService.paySuccessOrder(order_ids,null,null);
		if (num > 0) {
			WriteMsg(response, "付款成功");
		} else {
			WriteError(response, "付款失败");
		}
	}

	/**
	 *
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "payOrderOffline", method = RequestMethod.POST)
	public void payOrderOffline(MemberBean memberBean,OrderBean orderBean, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MemberBean memberBean2 = memberService.verificationToken(memberBean);
		if (memberBean2 == null) {
			WritePending(response, "token failed");
			return;
		}
		
		String[] order_ids = request.getParameter("order_ids").split(",");
		for (int i = 0; i < order_ids.length; i++) {
			OrderBean orderBean2 = orderService
					.getOneOrderDetail(new OrderBean().setOrder_id(Integer.valueOf(order_ids[i])));
			if(orderBean2!=null&&orderBean2.getOrder_state().equals("wait_pay")){
				
			}else{
				WriteError(response, "非待付款订单!");
				return;
			}
			
			List<OrderGoodsBean> orderGoodsBeans=orderService
					.getOrderGoodss(new OrderGoodsBean().setOrder_id(order_ids[i]));
			for (int j = 0; j < orderGoodsBeans.size(); j++) {
				OrderGoodsBean orderGoodsBean=orderGoodsBeans.get(j);
				GoodsSupplyDataBean goodsSupplyDataBean=goodsSupplyDataService.getGoodsSupplyDataByID(new GoodsSupplyDataBean()
							.setSupply_id(NumberUtils.Integer(orderGoodsBean.getSupply_id())));	
				if(goodsSupplyDataBean==null){
					WriteError(response, "此商品类型已下架");
					return;
				}
				
//				if (goodsSupplyDataBean.getGoods_stock() < orderGoodsBean.getGoods_num()) {
//					WriteError(response, "不好意思!库存不足");
//					return;
//				}
			}
		}
		
		int num=orderService.payOrderOffline(orderBean);
		if(num<=0){
			WriteError(response, "操作失败");
		}else{
			WriteMsg(response, "请尽快付款!等待平台审核");
		}
	}
	
	/**
	 * 支付订单
	 * 
	 * @param memberBean
	 * @param orderBean
	 * @param pageBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "payRealOrders", method = RequestMethod.POST)
	public void payRealOrders(MemberBean memberBean, OrderBean orderBean, PayBean payBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		MemberBean memberBean2 = memberService.verificationToken(memberBean);
		String order_no = "";
		float total_price = 0;
		String body = "便宜实惠!";
		String type = request.getParameter("type");// 公司类别

		if (memberBean2 == null) {
			WritePending(response, "token failed");
			return;
		}

		int integral = 0;
		boolean is_stock = true;// 判断库存是否有

		String[] order_ids = request.getParameter("order_ids").split(",");
		for (int i = 0; i < order_ids.length; i++) {
			OrderBean orderBean2 = orderService
					.getOneOrderDetail(new OrderBean().setOrder_id(Integer.valueOf(order_ids[i])));
			total_price += Float.valueOf(orderBean2.getOrder_actual_price());
			order_no += orderBean2.getOrder_id() + "A";

			List<OrderGoodsBean> orderGoodsBeans=orderService
					.getOrderGoodss(new OrderGoodsBean().setOrder_id(order_ids[i]));
			for (int j = 0; j < orderGoodsBeans.size(); j++) {
				OrderGoodsBean orderGoodsBean=orderGoodsBeans.get(j);
//				GoodsBean goodsBean = goodsServiceI
//						.getOneGoodsDetail(new GoodsBean().setGoods_id(Integer.valueOf(orderGoodsBeans.get(j).getGoods_id())));

				GoodsSupplyDataBean goodsSupplyDataBean=goodsSupplyDataService.getGoodsSupplyDataByID(new GoodsSupplyDataBean()
							.setSupply_id(NumberUtils.Integer(orderGoodsBean.getSupply_id())));	
				if(goodsSupplyDataBean==null){
					WriteError(response, "此商品类型已下架");
					return;
				}
				
//				if (goodsSupplyDataBean.getGoods_stock() < orderGoodsBean.getGoods_num()) {
//					WriteError(response, "不好意思!库存不足");
//					return;
//				}
			}
		}

		if (total_price == 0) {// 如果订单已用积分付款成功 则不需要真实支付
			WritePending(response, "pay_success");
		}

		PingSettingBean pingSettingBean=othersService.getPingSetting(new PingSettingBean().setPing_type("1"));
		
		Pingpp.apiKey = pingSettingBean.getPing_app_key();
		

		Pingpp.privateKeyPath = getClass().getResource("/").getFile().toString() + "/rsa_private_key.pem";
		Map<String, Object> chargeParams = new HashMap<String, Object>();

		String order_pay_no = order_no.substring(0, order_no.length() - 1) + TimeUtils.getCurrentTime("HHmmss");

		chargeParams.put("order_no", order_pay_no);
		chargeParams.put("amount", total_price*100);// total_price*100
		Map<String, String> app = new HashMap<String, String>();

		app.put("id", pingSettingBean.getPing_app_id());

		chargeParams.put("app", app);
		chargeParams.put("channel", payBean.getChannel());
		chargeParams.put("currency", "cny");
		chargeParams.put("client_ip", request.getRemoteAddr());
		chargeParams.put("subject", "商品购买");
		chargeParams.put("body", body.substring(0, body.length() > 20 ? 20 : body.length()));

		if (payBean.getChannel().equals("wx_pub")) {
			Map<String, Object> extra = new HashMap<String, Object>();
			extra.put("open_id", memberBean2.getWx_pub_openid());// 用户在商户微信公众号下的唯一标识，获取方式可参考
																	// WxPubOAuthExample.java
			chargeParams.put("extra", extra);
		}else if (payBean.getChannel().equals("alipay_pc_direct")) {
			HostBean hostBean=othersService.getHost(new HostBean().setHost_type("1"));
			Map<String, Object> extra = new HashMap<String, Object>();
			extra.put("success_url", hostBean.getHost_url()+"#/orders_waitPay?type=orders_waitPay");
			chargeParams.put("extra", extra);
		}else if (payBean.getChannel().equals("wx_pub_qr")) {
			Map<String, Object> extra = new HashMap<String, Object>();
			extra.put("product_id", CreateRandom.createRandom(false, 20));// 用户在商户微信公众号下的唯一标识，获取方式可参考
																	// WxPubOAuthExample.java
			chargeParams.put("extra", extra);
		}
		Charge charge = Charge.create(chargeParams);
		for (int i = 0; i < order_ids.length; i++) {
			orderService.updateOrderDetail(new OrderBean()
					.setOrder_id(Integer.valueOf(order_ids[i]))
					.setOrder_pay_no(charge.getId()));
		}

		if(payBean.getChannel().equals("wx_pub_qr")){
			String file_name=TimeUtils.getCurrentTime("yyyyMMddHHmmss")+CreateRandom.createRandom(true, 6)+".png";
			QRCodeUtils.CreateQrcode(request, "/images/qrcode/wx_pub_qr/"+file_name, charge.getCredential().get("wx_pub_qr").toString());
			WriteMsg(response, "/images/qrcode/wx_pub_qr/"+file_name);	
		}else{
			WriteObject(response, charge);					
		}
			
	}
	
	
	/**
	 *
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "payOrderAlipay", method = RequestMethod.POST)
	public void payOrderAlipay(MemberBean memberBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MemberBean memberBean2 = memberService.verificationToken(memberBean);
//		if (memberBean2 == null) {
//			WritePending(response, "token failed");
//			return;
//		}	
		String order_no = "";
		float total_price = 0;

		String[] order_ids = request.getParameter("order_ids").split(",");
		for (int i = 0; i < order_ids.length; i++) {
			OrderBean orderBean2 = orderService
					.getOneOrderDetail(new OrderBean().setOrder_id(Integer.valueOf(order_ids[i])));
			total_price += Float.valueOf(orderBean2.getOrder_total_price());

			order_no += orderBean2.getOrder_id() + "A";
		}

		if (total_price == 0) {// 如果订单已用积分付款成功 则不需要真实支付
			WritePending(response, "pay_success");
		}
		
		String order_pay_no = order_no.substring(0, order_no.length() - 1) + TimeUtils.getCurrentTime("HHmmss");		
		String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAK5tBMN+bVcUJIaqikdHhGobdjQJPpfeEJLWK0egXawbQB6isRpG7fxl8Ydi/f+Rxcm8saFe5JCeDORBnlBDvBxIuF7Kq97M727TcX2eov248SjucCvs3G22VBNLblOBCj36+ut3ZPCZxb8WrfLR09Z1p6K6t3jvxdqAWYxaYh8jAgMBAAECgYBJENfnAIXS7VgjpTrJgxbfz+MjByBWQ6qTp+13UqEKoFuquaIQjNfSW+3iNcCKl0jQKiAKphJ+1gY2c5paqxUX3mLp6/1gNZMz5eHN8zr6wpwMJpIZsamLziKzFWOaBkQ/7k4ifxExSTgDd3yGDY1g1oMwOvNzByWWfdXlz7IvmQJBAOW1r78RxPbdpzQ/REy7cxU6Ux5uUkOO9BTIChS8EKUZOz729COBNN/5SgeNJXEdYAQJPCp4OUcxTVptC56k8fUCQQDCY48BY2YA5T0w5wwOUTtEyYZZI4U8GFFvDn24QHuOedNeELJW2TiACWFvQChEo/CEzzXR+2/wHbw3iQITReW3AkA7n8xNaRxaA7Cp5B4jWKiHgwseI/6gYJPEbSQIu8QKRqFVN56pNJl35WosyZtFYB2TDp6aW3hqnUZ5yUIx3JidAkEAqycLl7ZT7n/og7E63TRKkgM6JivvOH1U3gqNSPiFkWnUX+MlkvBJHfqkbla33c/WiqkDAzJ2WZ1kxiT8zcliOwJAShk02uup7qVg+VPbon/Ijd0QD7b0meaDjkxoiG2TDHnKS26FWWIK1PCeoM5ShPKe+5C6w8e4Hi3KMmxAhklNlw==";
		double rate=AlipayUtils.getAlipayRate("2088421968201881", privateKey,"USD");

		Map<String, String> map=new HashMap<String, String>();
		map.put("service", "mobile.securitypay.pay");
		map.put("partner", "2088421968201881");
		map.put("_input_charset", "utf-8");
		map.put("out_trade_no",order_pay_no);
		map.put("subject","荷柏瑞商品");
		map.put("payment_type","1");
		map.put("seller_id", "artzone@hb-china.com.cn");
		//map.put("total_fee", NumberUtils.KeepDecimal(0.1/rate, 2)+"");//NumberUtils.KeepDecimal(total_price/rate, 2);
		map.put("rmb_fee", total_price+"");//total_price

		map.put("forex_biz", "FP");
		map.put("currency", "USD");
		map.put("body", "很好的商品");
		map.put("notify_url", "http://hbr.tstweiguanjia.com/orderInterfaces/paySuccessOrderAlipay");

		String wait_pay=AlipayUtils.createLinkString(map);//待签名字符串

		String sign=RSAUtils.sign(wait_pay, privateKey, "utf-8");
		map.put("notify_url", URLEncoder.encode("http://hbr.tstweiguanjia.com/orderInterfaces/paySuccessOrderAlipay","utf-8"));
		map.put("sign",  URLEncoder.encode(sign, "utf-8"));
		map.put("sign_type", "RSA");
		String pay=AlipayUtils.createLinkString(map);//支付字符串
		
		//writeHtml(request, "/html/others/protocols.html", pay,null);
		for (int i = 0; i < order_ids.length; i++) {
			orderService.updateOrderDetail(
				new OrderBean().setOrder_id(Integer.valueOf(order_ids[i])).setOrder_charge(pay));
		}
		WriteMsg(response, pay);
	}
	

	
	/**
	 *获得 alipay汇率
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getAlipayRate", method = RequestMethod.POST)
	public void getAlipayRate(MemberBean memberBean, RefundReasonBean refundReasonBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAK5tBMN+bVcUJIaqikdHhGobdjQJPpfeEJLWK0egXawbQB6isRpG7fxl8Ydi/f+Rxcm8saFe5JCeDORBnlBDvBxIuF7Kq97M727TcX2eov248SjucCvs3G22VBNLblOBCj36+ut3ZPCZxb8WrfLR09Z1p6K6t3jvxdqAWYxaYh8jAgMBAAECgYBJENfnAIXS7VgjpTrJgxbfz+MjByBWQ6qTp+13UqEKoFuquaIQjNfSW+3iNcCKl0jQKiAKphJ+1gY2c5paqxUX3mLp6/1gNZMz5eHN8zr6wpwMJpIZsamLziKzFWOaBkQ/7k4ifxExSTgDd3yGDY1g1oMwOvNzByWWfdXlz7IvmQJBAOW1r78RxPbdpzQ/REy7cxU6Ux5uUkOO9BTIChS8EKUZOz729COBNN/5SgeNJXEdYAQJPCp4OUcxTVptC56k8fUCQQDCY48BY2YA5T0w5wwOUTtEyYZZI4U8GFFvDn24QHuOedNeELJW2TiACWFvQChEo/CEzzXR+2/wHbw3iQITReW3AkA7n8xNaRxaA7Cp5B4jWKiHgwseI/6gYJPEbSQIu8QKRqFVN56pNJl35WosyZtFYB2TDp6aW3hqnUZ5yUIx3JidAkEAqycLl7ZT7n/og7E63TRKkgM6JivvOH1U3gqNSPiFkWnUX+MlkvBJHfqkbla33c/WiqkDAzJ2WZ1kxiT8zcliOwJAShk02uup7qVg+VPbon/Ijd0QD7b0meaDjkxoiG2TDHnKS26FWWIK1PCeoM5ShPKe+5C6w8e4Hi3KMmxAhklNlw==";
		double result=AlipayUtils.getAlipayRate("2088421968201881", privateKey,"USD");
		WriteMsg(response,result+"");
	}
	
	
	/**
	 * 余额支付
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "payBalanceSuccessOrder", method = RequestMethod.POST)
	public void payBalanceSuccessOrder(MemberBean memberBean,CodeBean codeBean,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try{
			PercentBean percentBean=othersService.getPercent(new PercentBean().setPercent_type("balance_pay"));
			if(percentBean!=null&&!percentBean.getPercent_value().equals("true")){
				WriteError(response, "余额支付暂不开放");
				return;
			}
			MemberBean memberBean1=memberService.verificationToken(memberBean);
			if (memberBean1 == null) {
				WritePending(response, "token failed");
				return;
			}
			
			if(codeService.getCodeBeanByMobileAndCode(codeBean.setMobile(memberBean1.getMember_account())
					.setCode_type("balance_pay").setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")))==null){
				WriteError(response, "此验证码已过期");
				return;
			}
		
			if(memberBean1.getBalance_password()==null||"".equals(memberBean1.getBalance_password())){
				WriteError(response, "请先设置支付密码");
				return;
			} 
			
			if(!memberBean1.getBalance_password().equals(MD5Util.md5Encode(memberBean.getBalance_password()))){
				WriteError(response, "支付密码错误");
				return;
			}
	
			String[] order_ids = request.getParameter("order_ids").split(",");
			int num = orderService.paySuccessOrder(order_ids,"balance",null);
			if (num > 0) {
				WriteMsg(response, "支付成功");
			} else {
				WriteError(response, "支付失败");
			}
		}catch(Exception e){
			WriteError(response, e.getMessage());
		}
	}
	
	
	/**
	 * 支付宝网站只会成功回调支付
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "payAlipayWapSuccessOrder", method = RequestMethod.POST)
	public void payAlipayWapSuccessOrder(OrderBean orderBean,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//try{
			String result="";
			List<OrderBean> orderBeans=orderService.getOrdersByPayNo(orderBean);
			for (int i = 0; i < orderBeans.size(); i++) {
				result+=orderBeans.get(i).getOrder_id();
				if(i<orderBeans.size()-1){
					result+=",";
				}
			}
			
			String[] order_ids = result.split(",");
			int num = orderService.paySuccessOrder(order_ids,null,null);
			if (num > 0) {
				WriteMsg(response, "支付成功");
			} else {
				WriteError(response, "支付失败");
			}
//		}catch(Exception e){
//			WriteError(response, e.getMessage());
//		}
	}
	
	/**
	 * 储值卡支付
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "payStoredSuccessOrder", method = RequestMethod.POST)
	public void payStoredSuccessOrder(MemberBean memberBean,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	//	try{
//			HBRUtils.cardDealData("15026592830","11","hhhhh","1");
		    HBRUtils.svcardDeal();
			//HBRUtils.updateScore();
//			HBRUtils.selectScore();
//			HBRUtils.getScore();
//			HBRUtils.refundOrder("15026592830","11","hhhhh","0.1");
//			MemberBean memberBean1=memberService.verificationToken(memberBean);
//			if (memberBean1 == null) {
//				WritePending(response, "token failed");
//				return;
//			}
//	
//			String[] order_ids = request.getParameter("order_ids").split(",");
//			int num = orderService.paySuccessOrder(order_ids,"stored");
//			if (num > 0) {
//				WriteMsg(response, "支付成功");
//			} else {
//				WriteError(response, "支付失败");
//			}
//		}catch(Exception e){
//			WriteError(response, e.getMessage());
//		}
	}
	
	/**
	 * 余额支付
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "payTrustSuccessOrder", method = RequestMethod.POST)
	public void payTrustSuccessOrder(MemberBean memberBean,CodeBean codeBean,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try{
			PercentBean percentBean=othersService.getPercent(new PercentBean().setPercent_type("trust_pay"));
			if(percentBean!=null&&!percentBean.getPercent_value().equals("true")){
				WriteError(response, "信用额度支付暂不开放");
				return;
			}
			
			MemberBean memberBean1=memberService.verificationToken(memberBean);
			if (memberBean1 == null) {
				WritePending(response, "token failed");
				return;
			}

			if(codeService.getCodeBeanByMobileAndCode(codeBean.setMobile(memberBean1.getMember_account())
					.setCode_type("trust_pay").setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")))==null){
				WriteError(response, "此验证码已过期");
				return;
			}
			
			if(memberBean1.getTrust_password()==null||"".equals(memberBean1.getTrust_password())){
				WriteError(response, "请先设置支付密码");
				return;
			} 
			
			if(!memberBean1.getTrust_password().equals(MD5Util.md5Encode(memberBean.getTrust_password()))){
				WriteError(response, "支付密码错误");
				return;
			}
	
			String[] order_ids = request.getParameter("order_ids").split(",");
			int num = orderService.paySuccessOrder(order_ids,"trust",null);
			if (num > 0) {
				WriteMsg(response, "支付成功");
			} else {
				WriteError(response, "支付失败");
			}
		}catch(Exception e){
			WriteError(response, e.getMessage());
		}
	}
	
	@RequestMapping(params = "paySuccessOrder", method = RequestMethod.POST)
	public void paySuccessOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    //writeHtml(request,"/html/others/about_our.html","123",null);
		String json = readJSONString(request);
		//writeHtml(request,"/html/others/about_our.html",json,null);
		PingOrderBean pingOrderBean = new Gson().fromJson(json, PingOrderBean.class);
		String orderNo = pingOrderBean.getData().getObject().getOrder_no();
		String[] order_ids = orderNo.substring(0, orderNo.length() - 6).split("A");

		int num = orderService.paySuccessOrder(order_ids,null,null);
		if (num > 0) {
			WriteMsg(response, "支付成功");
		} else {
			WriteError(response, "支付失败");
		}
	}

	/**
	 * 确认收货
	 * 
	 * @param memberBean
	 * @param orderBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "confirmOrder", method = RequestMethod.POST)
	public void confirmOrder(MemberBean memberBean, OrderBean orderBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}

		OrderBean orderBean1 = orderService.getOneOrderDetail(orderBean);
		if (orderBean1 != null && !orderBean1.getOrder_state().equals("wait_receive")) {
			WriteError(response, "此状态不可确认收货");
			return;
		}

		int num = orderService.confirmOrder(orderBean,true);
		if (num > 0) {
			WriteMsg(response, "确认成功");
		} else {
			WriteError(response, "确认失败");
		}
	}

	/**
	 * 上传评价订单图片
	 * 
	 * @param merchantsBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "uploadAssessmentImg")
	public void uploadAssessmentImg(MerchantsBean merchantsBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String json = uploadFile(request, "/images/assessment/");
		if (json.equals("-1")) {
			WriteError(response, "文件不可为空");
		} else if (json.equals("-2")) {
			WriteError(response, "上传失败");
		} else {
			WriteMsg(response, json);
		}
	}
	
	/**
	 * 可追加评价订单
	 * 
	 * @param memberBean
	 * @param orderBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getCanAddAssessmentOrder", method = RequestMethod.POST)
	public void getCanAddAssessmentOrder(MemberBean memberBean,OrderBean orderBean,PageBean pageBean,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		
		WriteObject(response, orderService.getCanAddAssessmentOrder(orderBean, pageBean),pageBean.getTotal());
	}

	/**
	 * 评价订单
	 * 
	 * @param memberBean
	 * @param orderBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "updateAssessmentOrderRemark", method = RequestMethod.POST)
	public void updateAssessmentOrderRemark(MemberBean memberBean, AssessmentBean assessmentBean,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}

		int num = orderService.updateAssessmentOrderRemark(assessmentBean);
		if (num > 0) {
			WriteMsg(response, "评价成功");
		} else {
			WriteError(response, "评价失败");
		}
	}
	
	/**
	 * 评价订单
	 * 
	 * @param memberBean
	 * @param orderBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "assessmentOrder", method = RequestMethod.POST)
	public void assessmentOrder(MemberBean memberBean, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}

		String json = request.getParameter("json");
		List<AssessmentBean> assessmentBeans = new Gson().fromJson(json, new TypeToken<List<AssessmentBean>>() {}.getType());

		if (assessmentBeans != null && assessmentBeans.size() > 0) {
			OrderBean orderBean1 = orderService.getOneOrderDetail(
					new OrderBean().setOrder_id(Integer.valueOf(assessmentBeans.get(0).getOrder_id())));
			if (orderBean1 != null 
					&&!orderBean1.getOrder_state().equals("end")
					&&!orderBean1.getOrder_state().equals("wait_assessment")) {
				WriteError(response, "此状态不可评价订单");
				return;
			}
		}

		int num = orderService.assessmentOrder(assessmentBeans);
		if (num > 0) {
			WriteMsg(response, "评价成功");
		} else {
			WriteError(response, "评价失败");
		}
	}

	/**
	 * 获得订单评价
	 * 
	 * @param memberBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getOrderAssessments", method = RequestMethod.POST)
	public void getOrderAssessments(MemberBean memberBean, AssessmentBean assessmentBean, PageBean pageBean,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String type=request.getParameter("type");
		WriteObject(response, orderService.getOrderAssessments(assessmentBean,type,pageBean),pageBean.getTotal());
	}

	
	
	/**
	 * 获得订单评价
	 * 
	 * @param memberBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getMemberAssessments", method = RequestMethod.POST)
	public void getMemberAssessments(MemberBean memberBean, AssessmentBean assessmentBean, PageBean pageBean,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}

		WriteObject(response, orderService.getMemberAssessments(assessmentBean, pageBean), pageBean.getTotal());
	}
	
	/**
	 * 获得订单评价
	 * 
	 * @param memberBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getMemberAssessmentsV2", method = RequestMethod.POST)
	public void getMemberAssessmentsV2(MemberBean memberBean, AssessmentBean assessmentBean, PageBean pageBean,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
//		if (memberService.verificationToken(memberBean) == null) {
//			WritePending(response, "token failed");
//			return;
//		}

		WriteObject(response, orderService.getMemberAssessmentsV2(assessmentBean, pageBean), pageBean.getTotal());
	}
}
