package tst.project.webservice.interfaces;

import java.sql.Time;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import tst.project.service.interfaces.ActivityService;
import tst.project.service.interfaces.GoodsServiceI;
import tst.project.service.interfaces.GoodsServiceI2;
import tst.project.service.interfaces.MemberService;
import tst.project.service.interfaces.OrderService;
import tst.project.service.interfaces.OthersService;
import tst.project.utils.TimeUtils;
import tst.project.webservice.controller.BaseController;


@Controller
@RequestMapping(value="/orderInterfaces")
public class OrderInterfacesV extends BaseController{
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
	/**
	 * 支付宝付款成功返回
	 * @param memberBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "paySuccessOrderAlipay")
	public void paySuccessOrderAlipay(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String json = readJSONString(request);
		//String out_trade_no=request.getParameter("out_trade_no");
		String order_charge[] =json.split("&");
		String out_trade_no="";
		for (int i = 0; i < order_charge.length; i++) {
			String [] result=order_charge[i].split("=");
			if(result[0].equals("out_trade_no")){
				out_trade_no=result[1];
				break;
			}
		}
		
		String[] order_ids = out_trade_no.substring(0, out_trade_no.length() - 6).split("A");
		int num = orderService.paySuccessOrder(order_ids,null,null);
		if (num > 0) {
			WriteMsg(response, "支付成功");
		} else {
			WriteError(response, "支付失败");
		}
		
//		writeHtml(request, "/html/others/protocols.html", 
//				TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+"======="+out_trade_no,null);
		WriteOnlyMsg(response, "success");
	}
	
	/**
	 * 支付宝网站付款成功返回
	 * @param memberBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "paySuccessOrderAlipayWap")
	public void paySuccessOrderAlipayWap(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String param=request.getParameter("param");
		writeHtml(request, "/html/others/about_our.html",
				TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+"===="+param, null);
	}
	
}
