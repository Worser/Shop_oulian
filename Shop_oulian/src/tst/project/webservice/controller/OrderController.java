package tst.project.webservice.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;

import tst.project.bean.ExcelBean;
import tst.project.bean.finance.ProfitBean;
import tst.project.bean.member.MemberBean;
import tst.project.bean.merchants.MerchantsAccountBean;
import tst.project.bean.order.BusinessProfitBean;
import tst.project.bean.order.LogisticsBean;
import tst.project.bean.order.OrderBean;
import tst.project.bean.order.OrderGoodsBean;
import tst.project.bean.order.PingOrderBean;
import tst.project.bean.order.RefundBean;
import tst.project.bean.order.RefundReasonBean;
import tst.project.page.PageBean;
import tst.project.service.controller.OrderServiceC;
import tst.project.service.controller.SystemService;
import tst.project.service.interfaces.OrderService;
import tst.project.utils.ExcelUtils;
import tst.project.utils.TimeUtils;

@Controller
@RequestMapping("/orderController.api")
public class OrderController extends BaseController{
	@Resource
	SystemService systemService;
	
	@Resource
	OrderServiceC orderServiceC;

	@Resource
	OrderService orderService;
	
	/**
	 * 设置订单是否开过发票
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "updateOrderInvise", method = RequestMethod.POST)
	public void updateOrderInvise(MerchantsAccountBean merchantsAccountBean, OrderBean orderBean,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		int num = orderServiceC.updateOrderInvise(orderBean);
		if (num > 0) {
			WriteMsg(response, "设置成功");
		} else {
			WriteError(response, "设置失败");
		}
	}
	
	/**
	 * 导出订单信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "exportOrderExcel", method = RequestMethod.POST)
	public void exportGoodsExcel(MerchantsAccountBean merchantsAccountBean,OrderBean orderBean,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}

		String fileName=TimeUtils.getCurrentTime("yyyyMMddHHmmss")+".xls";
		
		List<ExcelBean> excelBeans=new ArrayList<ExcelBean>();
		excelBeans.add(new ExcelBean().setName("用户ID").setType("member_id"));
		excelBeans.add(new ExcelBean().setName("昵称").setType("nick_name"));
		excelBeans.add(new ExcelBean().setName("交易号").setType("order_no"));
		excelBeans.add(new ExcelBean().setName("实收金额").setType("order_actual_price"));
		excelBeans.add(new ExcelBean().setName("商品名称").setType("goods_name"));
		excelBeans.add(new ExcelBean().setName("购买数量").setType("goods_num"));
		excelBeans.add(new ExcelBean().setName("销售价").setType("goods_price"));
		excelBeans.add(new ExcelBean().setName("订单状态").setType("order_state_show"));
		excelBeans.add(new ExcelBean().setName("邮费").setType("express_price"));
		excelBeans.add(new ExcelBean().setName("收件地址").setType("detailed_address"));
		excelBeans.add(new ExcelBean().setName("收件人").setType("name"));
		excelBeans.add(new ExcelBean().setName("电话").setType("mobile"));
		excelBeans.add(new ExcelBean().setName("快递公司").setType("logistics_pinyin"));
		excelBeans.add(new ExcelBean().setName("申请退货时间").setType("refund_time"));
		excelBeans.add(new ExcelBean().setName("备注").setType("custom_remark"));
		excelBeans.add(new ExcelBean().setName("下单时间").setType("create_time"));
		
		List<Object> orderBeans=orderServiceC.exportOrderExcel(orderBean);
		
		boolean is_success=ExcelUtils.exportExcel(request.getSession().getServletContext()
				.getRealPath("/")+"/excel/"+fileName, excelBeans,orderBeans,response);
		if(is_success){
			WriteMsg(response, "/excel/"+fileName);
		}else{
			WriteError(response, "导出失败");
		}
	}
	/**
	 * 通过订单
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "inviseOrder", method = RequestMethod.POST)
	public void inviseOrder(MerchantsAccountBean merchantsAccountBean,
			OrderBean orderBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}		
		int num=orderServiceC.inviseOrder(orderBean);
		if(num>0){
			WriteMsg(response, "成功");
		}else{
			WriteError(response, "失败");
		}
	}
	
	/**
	 * 拒绝订单
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "refuseOrder", method = RequestMethod.POST)
	public void refuseOrder(MerchantsAccountBean merchantsAccountBean,
			OrderBean orderBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}		
		int num=orderServiceC.refuseOrder(orderBean);
		if(num>0){
			WriteMsg(response, "成功");
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
	public void acceptOrder(MerchantsAccountBean merchantsAccountBean,
			OrderBean orderBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}		
		int num=orderServiceC.acceptOrder(orderBean);
		if(num>0){
			WriteMsg(response, "成功");
		}else{
			WriteError(response, "通过失败");
		}
	}
	
	
	
	/**
	 * 获得物流公司 不分页
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getOrderLogisticsNoPage", method = RequestMethod.POST)
	public void getOrderLogisticsNoPage(MerchantsAccountBean merchantsAccountBean,
			LogisticsBean logisticsBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}		
		WriteObject(response, orderServiceC.getOrderLogisticsNoPage(logisticsBean));
	}
	
	/**
	 * 获得物流公司
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getOrderLogistics", method = RequestMethod.POST)
	public void getOrderLogistics(MerchantsAccountBean merchantsAccountBean,
			LogisticsBean logisticsBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		WriteObject(response, orderServiceC.getOrderLogistics(logisticsBean,pageBean),pageBean.getTotal());
	}
	
	
	/**
	 * 添加物流公司
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "insertOrderLogistics", method = RequestMethod.POST)
	public void insertOrderLogistics(MerchantsAccountBean merchantsAccountBean,
			LogisticsBean logisticsBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		int num=orderServiceC.insertOrderLogistics(logisticsBean);
		if(num>0){
			WriteMsg(response, "添加成功");
		}else{
			WriteError(response, "添加失败");
		}
	}
	
	/**
	 * 修改物流公司
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "updateOrderLogistics", method = RequestMethod.POST)
	public void updateOrderLogistics(MerchantsAccountBean merchantsAccountBean,
			LogisticsBean logisticsBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		int num=orderServiceC.updateOrderLogistics(logisticsBean);
		if(num>0){
			WriteMsg(response, "修改成功");
		}else{
			WriteError(response, "修改失败");
		}
	}
	
	
	/**
	 * 删除物流公司
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "deleteOrderLogistics", method = RequestMethod.POST)
	public void deleteOrderLogistics(MerchantsAccountBean merchantsAccountBean,
			LogisticsBean logisticsBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		int num=orderServiceC.deleteOrderLogistics(logisticsBean);
		if(num>0){
			WriteMsg(response, "删除成功");
		}else{
			WriteError(response, "删除失败");
		}
	}
	
	
	/**
	 * 获得收益详情
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getOrderProfits", method = RequestMethod.POST)
	public void getOrderProfits(MerchantsAccountBean merchantsAccountBean,
			OrderBean orderBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		WriteObject(response, orderServiceC.getOrderProfits(orderBean));
	}
	
	/**
	 * 获得收益详情
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getBusinessProfitsTypeCount", method = RequestMethod.POST)
	public void getBusinessProfitsTypeCount(MerchantsAccountBean merchantsAccountBean,
			BusinessProfitBean businessProfitBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		WriteObject(response, orderServiceC.getBusinessProfitsTypeCount(businessProfitBean));
	}
	
	/**
	 * 获得收益详情
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getBusinessProfitsType", method = RequestMethod.POST)
	public void getBusinessProfitsType(MerchantsAccountBean merchantsAccountBean,
			BusinessProfitBean businessProfitBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		WriteObject(response, orderServiceC.getBusinessProfitsType(businessProfitBean,pageBean),pageBean.getTotal());
	}
	

	
	/**
	 * 添加退款原因
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "insertRefundReason", method = RequestMethod.POST)
	public void insertRefundReason(MerchantsAccountBean merchantsAccountBean,
			RefundReasonBean refundReasonBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		int num=orderServiceC.insertRefundReason(refundReasonBean);
		if(num>0){
			WriteMsg(response, "添加成功");
		}else{
			WriteError(response, "添加失败");
		}
	}
	/**
	 * 添加退款原因
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "updateRefundReason", method = RequestMethod.POST)
	public void updateRefundReason(MerchantsAccountBean merchantsAccountBean,
			RefundReasonBean refundReasonBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		int num=orderServiceC.updateRefundReason(refundReasonBean);
		if(num>0){
			WriteMsg(response, "修改成功");
		}else{
			WriteError(response, "修改失败");
		}
	}
	/**
	 * 添加退款原因
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "deleteRefundReason", method = RequestMethod.POST)
	public void deleteRefundReason(MerchantsAccountBean merchantsAccountBean,
			RefundReasonBean refundReasonBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		int num=orderServiceC.deleteRefundReason(refundReasonBean);
		if(num>0){
			WriteMsg(response, "删除成功");
		}else{
			WriteError(response, "删除失败");
		}
	}
	/**
	 * 商品退款可能的原因
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getRefundReasons", method = RequestMethod.POST)
	public void getRefundReasons(MerchantsAccountBean merchantsAccountBean,
			RefundReasonBean refundReasonBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		WriteObject(response, orderServiceC.getRefundReasons(refundReasonBean,pageBean),pageBean.getTotal());
	}
	/**
	 * 审核退款申请
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "updateRefundState", method = RequestMethod.POST)
	public void updateRefundState(MerchantsAccountBean merchantsAccountBean,
			RefundBean refundBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		String type=request.getParameter("type");
		int num=orderServiceC.updateRefundState(refundBean,type);
		if(num>0){
			WriteMsg(response, "操作成功");
		}else{
			WriteError(response, "操作失败");
		}
	}
	
	/**
	 * 获得所有退单申请
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getOrderRefundDetail", method = RequestMethod.POST)
	public void getOrderRefundDetail(MerchantsAccountBean merchantsAccountBean,
			RefundBean refundBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		WriteObject(response, orderServiceC.getOrderRefundDetail(refundBean));
	}
	
	/**
	 * 获得所有退单申请
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getOrderRefunds", method = RequestMethod.POST)
	public void getOrderRefunds(MerchantsAccountBean merchantsAccountBean,
			RefundBean refundBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		WriteObject(response, orderServiceC.getOrderRefunds(refundBean,pageBean),pageBean.getTotal());
	}
	
	
	
	/**
	 * 获得用户的订单列表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getMemberOrderList", method = RequestMethod.POST)
	public void getMemberOrderList(MerchantsAccountBean merchantsAccountBean,
			OrderBean orderBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		WriteObject(response, orderServiceC.getMemberOrderList(orderBean,pageBean),
				pageBean.getTotal());
	}
	
	/**
	 * 确认发货
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "confirmSendOrder", method = RequestMethod.POST)
	public void confirmSendOrder(MerchantsAccountBean merchantsAccountBean,
			OrderBean orderBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		OrderBean orderBean2=orderServiceC.getOrderDetail(orderBean);
		if(orderBean2==null){
			WriteError(response, "无此订单");
			return;
		}
		if("wait_receive".equals(orderBean.getOrder_state())&&!orderBean2.getOrder_state().equals("wait_send")){
			WriteError(response, "此订单不是未发货状态");
			return;
		}
		
		int num=orderServiceC.confirmSendOrder(orderBean);
		if(num>0){
			WriteMsg(response, "确认成功");
		}else{
			WriteError(response, "确认失败");
		}
	}
	
	
	/**
	 * 获得订单的商品详情
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getOrderGoodsDetail", method = RequestMethod.POST)
	public void getOrderGoodsDetail(MerchantsAccountBean merchantsAccountBean,
			OrderGoodsBean orderGoodsBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		WriteObject(response, orderServiceC.getOrderGoodsDetail(orderGoodsBean));
	}
	
	
	/**
	 * 获得订单的商品
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getOrderGoodss", method = RequestMethod.POST)
	public void getOrderGoodss(MerchantsAccountBean merchantsAccountBean,
			OrderGoodsBean orderGoodsBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		WriteObject(response, orderServiceC.getOrderGoodss(orderGoodsBean));
	}
	
	/**
	 * 获得商家的订单详情
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getOrderDetail", method = RequestMethod.POST)
	public void getOrderDetail(MerchantsAccountBean merchantsAccountBean,
			OrderBean orderBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		WriteObject(response, orderServiceC.getOrderDetail(orderBean));
	}
	
	/**
	 * 获得商家的订单列表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getOrderList", method = RequestMethod.POST)
	public void getOrderList(MerchantsAccountBean merchantsAccountBean,
			OrderBean orderBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		WriteObject(response, orderServiceC.getOrderList(orderBean,pageBean),
				pageBean.getTotal());
	}

	@RequestMapping(params = "paySuccessOrder", method = RequestMethod.POST)
	public void paySuccessOrder(MerchantsAccountBean merchantsAccountBean,HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		String[] order_ids=request.getParameter("order_ids").split(",");
		int num = orderService.paySuccessOrder(order_ids,"offline",null);
		if (num > 0) {
			WriteMsg(response, "支付成功");
		} else {
			WriteError(response, "支付失败");
		}
	}
	
	
	//家纺
	/**
	 * 获得店铺退款订单列表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getBussinessRefundOrders", method = RequestMethod.POST)
	public void getBussinessRefundOrders(MerchantsAccountBean merchantsAccountBean,
			RefundBean refundBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}

		WriteObject(response, orderServiceC.getBussinessRefundOrders(refundBean,pageBean));
	}
	
	/**
	 * 获得店铺退款单个订单详情
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getOneBussinessRefundOrder", method = RequestMethod.POST)
	public void getOneBussinessRefundOrder(MerchantsAccountBean merchantsAccountBean,
			RefundBean refundBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}

		WriteObject(response, orderServiceC.getOneBussinessRefundOrder(refundBean));
	}
	

	/**
	 * 获得历史收益
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getHistoryBusinessProfitsPrice", method = RequestMethod.POST)
	public void getHistoryBusinessProfitsPrice(MerchantsAccountBean merchantsAccountBean,
			OrderGoodsBean orderGoodsBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		WriteObject(response, orderServiceC.getHistoryBusinessProfitsPrice(orderGoodsBean));
	}
	
	/**
	 * 获得收益列表（家纺）
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getBusinessProfits", method = RequestMethod.POST)
	public void getBusinessProfits(MerchantsAccountBean merchantsAccountBean,
			OrderGoodsBean orderGoodsBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		WriteObject(response, orderServiceC.getBusinessProfits(orderGoodsBean,pageBean),
				pageBean.getTotal());
	}
	
	/**
	 * 获得店铺的订单列表（家纺）
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getBusinessOrderList", method = RequestMethod.POST)
	public void getBusinessOrderList(MerchantsAccountBean merchantsAccountBean,
			OrderBean orderBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		WriteObject(response, orderServiceC.getBusinessOrderList(orderBean,pageBean),
				pageBean.getTotal());
	}
	/**
	 * 获得店铺的订单详情（家纺）
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getOneBusinessOrderDetail", method = RequestMethod.POST)
	public void getOneBusinessOrderDetail(MerchantsAccountBean merchantsAccountBean,
			OrderBean orderBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		WriteObject(response, orderServiceC.getOneBusinessOrderDetail(orderBean));
	}
	
	
	/**
	 * 获得店铺的今日订单列表（家纺）
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getBusinessTodayOrderList", method = RequestMethod.POST)
	public void getBusinessTodayOrderList(MerchantsAccountBean merchantsAccountBean,
			OrderBean orderBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		String start_time=TimeUtils.getCurrentTime("yyyy-MM-dd");
		WriteObject(response, orderServiceC.getBusinessOrderList(orderBean.setStart_time(start_time),pageBean),
				pageBean.getTotal());
	}
	
	
	
}
