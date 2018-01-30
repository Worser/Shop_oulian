package tst.project.webservice.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import sun.print.resources.serviceui;
import tst.project.bean.HostBean;
import tst.project.bean.merchants.MerchantsAccountBean;
import tst.project.bean.others.AdviceBean;
import tst.project.bean.others.HtmlBean;
import tst.project.bean.others.HtmlStyleBean;
import tst.project.bean.others.PercentBean;
import tst.project.page.PageBean;
import tst.project.service.controller.OthersServiceC;
import tst.project.service.controller.SystemService;
import tst.project.utils.TimeUtils;

@Controller
@RequestMapping("/othersController.api")
public class OthersController extends BaseController{
	@Resource
	OthersServiceC othersServiceC;
	
	@Resource
	SystemService systemService;
	
	@RequestMapping(params = "deleteAdvice", method = RequestMethod.POST)
	public void deleteAdvice(MerchantsAccountBean merchantsAccountBean,
			AdviceBean adviceBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		int num=othersServiceC.deleteAdvice(adviceBean);
		if(num>0){
			WriteMsg(response, "删除成功");
		}else{
			WriteError(response, "删除失败");
		}
	}
	@RequestMapping(params = "getAdviceDetail", method = RequestMethod.POST)
	public void getAdviceDetail(MerchantsAccountBean merchantsAccountBean,
			AdviceBean adviceBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		WriteObject(response, othersServiceC.getAdviceDetail(adviceBean));
	}
	@RequestMapping(params = "getAdvices", method = RequestMethod.POST)
	public void getAdvices(MerchantsAccountBean merchantsAccountBean,
			AdviceBean adviceBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		WriteObject(response, othersServiceC.getAdvices(adviceBean,pageBean),pageBean.getTotal());
	}
	
	
	@RequestMapping(params = "getPercents", method = RequestMethod.POST)
	public void getPercents(MerchantsAccountBean merchantsAccountBean,PercentBean percentBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		WriteObject(response, othersServiceC.getPercents(percentBean));
	}
	
	@RequestMapping(params = "getPercent", method = RequestMethod.POST)
	public void getPercent(MerchantsAccountBean merchantsAccountBean,PercentBean percentBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		WriteObject(response, othersServiceC.getPercent(percentBean));
	}
	@RequestMapping(params = "updatePercents", method = RequestMethod.POST)
	public void updatePercents(MerchantsAccountBean merchantsAccountBean,PercentBean percentBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		String json=request.getParameter("json");
		List<PercentBean> percentBeans=new Gson().fromJson(json, new TypeToken<List<PercentBean>>(){}.getType());
		int num=othersServiceC.updatePercents(percentBeans);
		if(num>0){
			WriteMsg(response, "设置成功");
		}else{
			WriteError(response, "设置失败");
		}
	}
	
	@RequestMapping(params = "updatePercent", method = RequestMethod.POST)
	public void updatePercent(MerchantsAccountBean merchantsAccountBean,PercentBean percentBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		int num=othersServiceC.updatePercent(percentBean);
		if(num>0){
			WriteMsg(response, "设置成功");
		}else{
			WriteError(response, "设置失败");
		}
	}
	
	@RequestMapping(params = "getHost", method = RequestMethod.POST)
	public void getHost( HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WriteObject(response, othersServiceC.getHost(new HostBean().setHost_type("1")));
	}
	
	@RequestMapping(params = "updateHost", method = RequestMethod.POST)
	public void updateHost(MerchantsAccountBean merchantsAccountBean,HostBean hostBean, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		int num=othersServiceC.updateHost(hostBean);
		if(num>0){
			WriteMsg(response, "修改成功");
		}else{
			WriteError(response, "修改失败");
		}
	}
	
	@RequestMapping(params = "getBannerUrlHtml")
	public void getBannerUrlHtml(HttpServletRequest request, HttpServletResponse response) {
		String url=request.getParameter("url");
		WriteOnlyMsg(response,  readHtml(request,url));
	}

	@RequestMapping(params = "setBannerUrlHtml")
	public void setBannerUrlHtml(HttpServletRequest request, HttpServletResponse response) {
		String desc=request.getParameter("desc");
		String url=request.getParameter("url");
		if(writeHtml(request,url,desc,othersServiceC.getHtmlStyle(new HtmlStyleBean().setStyle_type("banner")))){
			WriteMsg(response, "保存成功");
		}else{
			WriteError(response, "保存失败");
		}
	}
	
	@RequestMapping(params = "getAboutOurUrlHtml")
	public void getAboutOurUrlHtml(HttpServletRequest request, HttpServletResponse response) {
		String url=request.getParameter("url");
		WriteOnlyMsg(response,  readHtml(request,url));
	}

	@RequestMapping(params = "setAboutOurUrlHtml")
	public void setAboutOurUrlHtml(HttpServletRequest request, HttpServletResponse response) {
		String desc=request.getParameter("desc");
		String url=request.getParameter("url");
		if(writeHtml(request,url,desc,othersServiceC.getHtmlStyle(new HtmlStyleBean().setStyle_type("about_our")))){
			WriteMsg(response, "保存成功");
		}else{
			WriteError(response, "保存失败");
		}
	}
	
	@RequestMapping(params = "getShopCarUrlHtml")
	public void getShopCarUrlHtml(HttpServletRequest request, HttpServletResponse response) {
		String url=request.getParameter("url");
		WriteOnlyMsg(response,  readHtml(request,url));
	}

	@RequestMapping(params = "setShopCarUrlHtml")
	public void setShopCarUrlHtml(HttpServletRequest request, HttpServletResponse response) {
		String desc=request.getParameter("desc");
		String url=request.getParameter("url");
		if(writeHtml(request,url,desc,othersServiceC.getHtmlStyle(new HtmlStyleBean().setStyle_type("shop_car")))){
			WriteMsg(response, "保存成功");
		}else{
			WriteError(response, "保存失败");
		}
	}
	
	/**
	 * 
	 * @param merchantsAccountBean
	 * @param htmlBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getHelpClasss", method = RequestMethod.POST)
	public void getHelpClasss(MerchantsAccountBean merchantsAccountBean,
			HtmlBean htmlBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		WriteObject(response, othersServiceC.getHelpClasss(htmlBean));
	}

	
	/**
	 * 添加帮助中心
	 * @param merchantsAccountBean
	 * @param htmlBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "insertHelpDetail", method = RequestMethod.POST)
	public void insertHelpDetail(MerchantsAccountBean merchantsAccountBean,
			HtmlBean htmlBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		if("2".equals(htmlBean.getHtml_type())){
			String fileName=TimeUtils.getCurrentTime("yyyyMMddHHmmss")+".html";	
			String path="/html/help_center/";
			writeHtml(request, path+fileName,"产品展示",othersServiceC.getHtmlStyle(new HtmlStyleBean().setStyle_type("goods")));
			htmlBean.setHtml_url(path+fileName);	
		}
		
		int num=othersServiceC.insertHelpDetail(htmlBean);
		if(num>0){
			WriteMsg(response, "添加成功");
		}else{
			WriteError(response, "添加失败");
		}
	}
	/**
	 * 修改帮助中心
	 * @param merchantsAccountBean
	 * @param htmlBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "updateHelpDetail", method = RequestMethod.POST)
	public void updateHelpDetail(MerchantsAccountBean merchantsAccountBean,
			HtmlBean htmlBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		int num=othersServiceC.updateHelpDetail(htmlBean);
		if(num>0){
			WriteMsg(response, "修改成功");
		}else{
			WriteError(response, "修改失败");
		}
	}
	/**
	 * 删除帮助中心
	 * @param merchantsAccountBean
	 * @param htmlBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "deleteHelpDetail", method = RequestMethod.POST)
	public void deleteHelpDetail(MerchantsAccountBean merchantsAccountBean,
			HtmlBean htmlBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		int num=othersServiceC.deleteHelpDetail(htmlBean);
		if(num>0){
			WriteMsg(response, "删除成功");
		}else{
			WriteError(response, "删除失败");
		}
	}
	

}
