package tst.project.webservice.interfaces;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tst.project.bean.activity.AlbumBean;
import tst.project.bean.activity.NewsBean;
import tst.project.bean.goods.GoodsBean;
import tst.project.bean.home.HomeGoodsBean;
import tst.project.bean.home.LabelBean;
import tst.project.bean.member.MemberBean;
import tst.project.bean.merchants.MerchantsBean;
import tst.project.bean.order.OrderSWBean;
import tst.project.bean.others.CodeBean;
import tst.project.page.PageBean;
import tst.project.service.interfaces.CodeService;
import tst.project.service.interfaces.GoodsServiceI;
import tst.project.service.interfaces.MemberService;
import tst.project.service.interfaces.SWService;
import tst.project.utils.TimeUtils;
import tst.project.webservice.controller.BaseController;


@Controller
@RequestMapping("/swInterfaces.api")
public class SWInterfaces extends BaseController{
	@Resource
	SWService swService;

	@Resource
	MemberService memberService;
	
	@Resource
	CodeService codeService;

	@Resource
	GoodsServiceI goodsServiceI;
	
	/**
	 * 企业购分类
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getBusinessBuyClass", method = RequestMethod.POST)
	public void getBusinessBuyClass(GoodsBean goodsBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String level=request.getParameter("level");
		WriteObject(response, swService.getBusinessBuyClass(goodsBean,level));
	}
	
	@RequestMapping(params = "getBusinessBuyClasss", method = RequestMethod.POST)
	public void getBusinessBuyClasss(GoodsBean goodsBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		WriteObject(response, swService.getBusinessBuyClass(goodsBean));
	}
	
	
	/**
	 * 抢单
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "grabOrderSW", method = RequestMethod.POST)
	public void grabOrderSW(MemberBean memberBean,OrderSWBean orderSWBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}		
		
		OrderSWBean orderSWBean2=swService.getOrderSwById(orderSWBean);
		if(orderSWBean2==null){
			WriteError(response, "此订单不存在");
			return;
		}else if(orderSWBean2.getOrder_state().equals("end")){
			WriteError(response, "手速太慢了哦!已被人抢");
			return;
		}
		int num=swService.grabOrderSW(orderSWBean);
		if(num>0){
			WriteMsg(response, "抢单成功");
		}else{
			WriteMsg(response, "抢单失败");
		}
	}
	
	
	/**
	 * 我的抢单
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getMemberGrabOrderSWs", method = RequestMethod.POST)
	public void getMemberGrabOrderSWs(MemberBean memberBean,OrderSWBean orderSWBean,
			PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}		
		
		WriteObject(response, swService.getMemberGrabOrderSWs(orderSWBean,pageBean),pageBean.getTotal());
	}
	
	
	/**
	 * 删除订单
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "deleteOrderSW", method = RequestMethod.POST)
	public void deleteOrderSW(MemberBean memberBean,OrderSWBean orderSWBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}		
		
		int num=swService.deleteOrderSW(orderSWBean);
		if(num>0){
			WriteMsg(response, "删除成功");
		}else{
			WriteMsg(response, "删除失败");
		}
	}
	
	/**
	 * 取消订单
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "cancelOrderSW", method = RequestMethod.POST)
	public void cancelOrderSW(MemberBean memberBean,OrderSWBean orderSWBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}		
		
		int num=swService.cancelOrderSW(orderSWBean);
		if(num>0){
			WriteMsg(response, "取消成功");
		}else{
			WriteMsg(response, "取消失败");
		}
	}
	
	/**
	 * 我的发单
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getMemberOrderSWs", method = RequestMethod.POST)
	public void getMemberOrderSWs(MemberBean memberBean,OrderSWBean orderSWBean,
			PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}		
		
		WriteObject(response, swService.getMemberOrderSWs(orderSWBean,pageBean),pageBean.getTotal());;
	}
	
	/**
	 * 发单
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "insertOrderSW", method = RequestMethod.POST)
	public void insertOrderSW(MemberBean memberBean,OrderSWBean orderSWBean,CodeBean codeBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}		
		
		if(codeService.getCodeBeanByMobileAndCode(codeBean.setMobile(orderSWBean.getMember_mobile())
				.setCode_type("send_order").setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")))==null){
			WriteError(response, "此验证码已过期");
			return;
		}
		
		int num=swService.insertOrderSW(orderSWBean,codeBean);
		if(num>0){
			WriteMsg(response, "发单成功");
		}else{
			WriteMsg(response, "发单失败");
		}
	}
	
	
	/**
	 * 生物网站首页
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getHomeClassWeb", method = RequestMethod.POST)
	public void getHomeClassWeb(GoodsBean goodsBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		WriteObject(response, swService.getHomeClassWeb(goodsBean));		
	}
	
	
	
	/**
	 * 团购商品列表(只是标签)
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getGroupGoodss", method = RequestMethod.POST)
	public void getGroupGoodss(GoodsBean goodsBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		WriteObject(response, swService.getGroupGoodss(goodsBean,pageBean),pageBean.getTotal());		
	}
	
	
	/**
	 * 店铺专辑列表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getAlbums", method = RequestMethod.POST)
	public void getAlbums(AlbumBean albumBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		WriteObject(response, swService.getAlbums(albumBean,pageBean));		
	}
	
	/**
	 * 用户关闭动态头条
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "memberCloseDynamicHeadlines", method = RequestMethod.POST)
	public void memberCloseDynamicHeadlines(MemberBean memberBean,MerchantsBean merchantsBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		if(memberService.verificationToken(memberBean)==null){
			WritePending(response, "token failed");
			return;
		}
		
		MerchantsBean merchantsBean2=swService.getOneMemberDynamicHeadlines(merchantsBean);
		if(merchantsBean2!=null){
			WriteError(response, "已关闭");
			return;
		}
		
		int num=swService.memberCloseDynamicHeadlines(merchantsBean);
		if(num>0){
			WriteMsg(response, "成功关闭");
		}else{
			WriteError(response, "关闭失败");
		}
	}
	
	/**
	 * 店铺头条
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getHeadlinesMerchants", method = RequestMethod.POST)
	public void getHeadlinesMerchants(MerchantsBean merchantsBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String type=request.getParameter("type");
		if("hot".equals(type)){
			WriteObject(response, swService.getHotHeadlinesMerchants(merchantsBean,pageBean),pageBean.getTotal());			
		}else if("dynamic".equals(type)){
			WriteObject(response, swService.getDynamicHeadlinesMerchants(merchantsBean,pageBean),pageBean.getTotal());
		}
	}
	
	
	/**
	 * 发现好货
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getGoodGoodss", method = RequestMethod.POST)
	public void getGoodGoodss(GoodsBean goodsBean,AlbumBean albumBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String type=request.getParameter("type");
		if("exact".equals(type)){
			WriteObject(response, swService.getExactGoodGoodss(goodsBean,pageBean),pageBean.getTotal());			
		}else if("album".equals(type)){
			WriteObject(response, swService.getAlbumGoodGoodss(goodsBean,pageBean),pageBean.getTotal());
		}
	}
	
	
	/**
	 * 首页推荐商品列表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getHomeGoods", method = RequestMethod.POST)
	public void getHomeGoods(GoodsBean goodsBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		if(goodsBean.getGoods_id()!=0){
			GoodsBean goodsBean2=goodsServiceI.getOneGoodsDetail(goodsBean);
			goodsBean.setParent_id(goodsBean2.getParent_id());
		}
		WriteObject(response, swService.getHomeGoods(goodsBean,pageBean),pageBean.getTotal());
	}
	
	/**
	 * 首页分类
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getHomeLabels", method = RequestMethod.POST)
	public void getHomeLabels(LabelBean labelBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		WriteObject(response, swService.getHomeLabels(labelBean));
	}
	
	/**
	 * 波尔快报的分类列表
	 * @param percentBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getNewsClass", method = RequestMethod.POST)
	public void getNewsClass(NewsBean newsBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		WriteObject(response, swService.getNewsClass(newsBean));
	}
	
	/**
	 * 波尔快报的分类下商品列表
	 * @param percentBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getNewsGoods", method = RequestMethod.POST)
	public void getNewsGoods(NewsBean newsBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		WriteObject(response, swService.getNewsGoods(newsBean,pageBean),pageBean.getTotal());
	}
	
	/**
	 * 波尔快报的精选商品列表
	 * @param percentBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getNewsRecommendGoods", method = RequestMethod.POST)
	public void getNewsRecommendGoods(NewsBean newsBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		WriteObject(response, swService.getNewsRecommendGoods(newsBean));
	}
	
	/**
	 * 波尔快报的精选商品列表
	 * @param percentBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getNewsExactGoods", method = RequestMethod.POST)
	public void getNewsExactGoods(NewsBean newsBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		WriteObject(response, swService.getNewsExactGoods(newsBean,pageBean),pageBean.getTotal());
	}
}
