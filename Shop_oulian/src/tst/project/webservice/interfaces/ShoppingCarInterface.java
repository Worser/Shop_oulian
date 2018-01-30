package tst.project.webservice.interfaces;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import tst.project.bean.goods.GoodsSupplyDataBean;
import tst.project.bean.goods.ShoppingCarBean;
import tst.project.bean.member.MemberBean;
import tst.project.bean.others.CodeBean;
import tst.project.page.PageBean;
import tst.project.service.controller.MerchantsService;
import tst.project.service.interfaces.CodeService;
import tst.project.service.interfaces.GoodsSupplyDataService;
import tst.project.service.interfaces.MemberService;
import tst.project.service.interfaces.ShoppingCarService;
import tst.project.utils.NumberUtils;
import tst.project.utils.TimeUtils;
import tst.project.webservice.controller.BaseController;

@Controller
@RequestMapping("/shoppingCarInterfaces.api")
public class ShoppingCarInterface extends BaseController {

	@Resource
	MemberService memberService;

	@Resource
	ShoppingCarService shoppingCarService;
	
	@Resource
	CodeService codeService;
	
	@Resource
	GoodsSupplyDataService goodsSupplyDataService;
	
	/**
	 * 分享购物车
	 * 
	 * @param goodsBean
	 * @return
	 */
	@RequestMapping(params = "shareMyShoppingCar", method = RequestMethod.POST)
	public void shareMyShoppingCar(MemberBean memberBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {		
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		
		//MemberBean memberBean2=memberService.
		
		WriteObject(response, shoppingCarService.getMemberShoppingCarCount(memberBean));
	}
	
	
	/**
	 * 购物车数量
	 * 
	 * @param goodsBean
	 * @return
	 */
	@RequestMapping(params = "getMemberShoppingCarCount", method = RequestMethod.POST)
	public void getMemberShoppingCarCount(MemberBean memberBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {		
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		
		WriteObject(response, shoppingCarService.getMemberShoppingCarCount(memberBean));
	}
	
	
	/**
	 * 添加购物车
	 * 
	 * @param goodsBean
	 * @return
	 */
	@RequestMapping(params = "insertShoppingCar", method = RequestMethod.POST)
	public void insertShoppingCar(MemberBean memberBean,
			ShoppingCarBean shoppingCarBean,GoodsSupplyDataBean goodsSupplyDataBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {		
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		
		GoodsSupplyDataBean goodsSupplyDataBean2=
				goodsSupplyDataService.getGoodsSupplyDataByID(goodsSupplyDataBean);
		if(goodsSupplyDataBean2==null){
			WriteError(response, "供应数据不存在!");
			return;
		}
		
		
		ShoppingCarBean shoppingCarBean2=shoppingCarService
				.getShoppingCarByGoodsIdAndMemberId(shoppingCarBean);
		if (shoppingCarBean2 != null) {//购物车已有此商品
			if(goodsSupplyDataBean2.getGoods_stock()<NumberUtils.Integer(shoppingCarBean2.getGoods_num())
					+NumberUtils.Integer(shoppingCarBean.getGoods_num())){
				WriteError(response, "库存不足");
				return;
			}
			int num=shoppingCarService.updateShoppingCarNum(shoppingCarBean2
					.setGoods_num(Integer.valueOf(shoppingCarBean2.getGoods_num())+Integer.valueOf(shoppingCarBean.getGoods_num())+""));
			if(num>0){
				WriteMsg(response, shoppingCarBean.getCar_id()+"");
			}else{
				WriteError(response, "添加失败");
			}
		}else{	
			if(goodsSupplyDataBean2.getGoods_stock()<NumberUtils.Integer(shoppingCarBean.getGoods_num())){
				WriteError(response, "库存不足");
				return;
			}
			
			int num = shoppingCarService.insertShoppingCar(shoppingCarBean);
			if (num > 0) {
				WriteMsg(response, shoppingCarBean.getCar_id() + "");
			} else {
				WriteError(response, "添加失败");
			}
		}
	}
	
	
	/** 获得自己的购物车列表
	 * 
	 * @param memberBean
	 * @param shoppingCarBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getShoppingCars", method = RequestMethod.POST)
	public void getShoppingCars(MemberBean memberBean,PageBean pageBean,
			ShoppingCarBean shoppingCarBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}

		WriteObject(response,shoppingCarService.getShoppingCars(shoppingCarBean,pageBean));
	 }
	
	

	
	
	/** 获得自己的购物车列表
	 * 
	 * @param memberBean
	 * @param shoppingCarBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getShoppingCarsWithCarids", method = RequestMethod.POST)
	public void getShoppingCarsWithCarids(MemberBean memberBean,PageBean pageBean,
			ShoppingCarBean shoppingCarBean, CodeBean codeBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		MemberBean memberBean1=memberService.verificationToken(memberBean);
		if (memberBean1 == null) {
			WritePending(response, "token failed");
			return;
		}

		if(codeBean.getCode()!=null){
			if(codeService.getCodeBeanByMobileAndCode(codeBean.setMobile(memberBean1.getMember_account())
					.setCode_type("apply_cash").setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")))==null){
				WriteError(response, "此验证码已过期");
				return;
			}	
		}
			
		String ids=request.getParameter("car_ids");
		String[] car_ids=null;
		if(ids!=null&&ids.length()>0){
			car_ids=ids.split(",");
		}
		WriteObject(response,shoppingCarService.getShoppingCarsWithCarids(shoppingCarBean,car_ids));
	}
	
	@RequestMapping(params = "deleteShoppingCar", method = RequestMethod.POST)
	public void deleteShoppingCar(MemberBean memberBean,
			ShoppingCarBean shoppingCarBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		int num=shoppingCarService.deleteShoppingCar(shoppingCarBean);
		if(num>0){
			WriteMsg(response, "删除成功");
		}else{
			WriteError(response, "删除失败");
		}
	}
	
	@RequestMapping(params = "deleteShoppingCars", method = RequestMethod.POST)
	public void deleteShoppingCars(MemberBean memberBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		String[] car_ids=request.getParameter("car_ids").split(",");
		if(car_ids==null||car_ids.length<=0){
			WriteError(response, "请先选择要删除的商品");
			return;
		}
		int num=shoppingCarService.deleteShoppingCars(car_ids,memberBean);
		if(num>0){
			WriteMsg(response, "删除成功");
		}else{
			WriteError(response, "删除失败");
		}
	}

	@RequestMapping(params = "updateShoppingCarNum", method = RequestMethod.POST)
	public void updateShoppingCarNum(MemberBean memberBean,
			ShoppingCarBean shoppingCarBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		
		int num=shoppingCarService.updateShoppingCarNum(shoppingCarBean);
		if(num>0){
			WriteMsg(response, "修改成功");
		}else{
			WriteError(response, "修改失败");
		}
	}
	
	
	@RequestMapping(params = "updateShoppingCarNums", method = RequestMethod.POST)
	public void updateShoppingCarNums(MemberBean memberBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		
		String cars=request.getParameter("cars");
		List<ShoppingCarBean> shoppingCarBeans=new Gson().fromJson(cars,new TypeToken<List<ShoppingCarBean>>() {}.getType());
		
		int num=shoppingCarService.updateShoppingCarNums(shoppingCarBeans,memberBean);
		if(num>0){
			WriteMsg(response, "修改成功");
		}else{
			WriteError(response, "修改失败");
		}
	}
}
