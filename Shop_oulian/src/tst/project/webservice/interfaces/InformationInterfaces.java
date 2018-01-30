package tst.project.webservice.interfaces;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tst.project.bean.information.InformationBean;
import tst.project.page.PageBean;
import tst.project.service.interfaces.InformationService;
import tst.project.webservice.controller.BaseController;
@Controller
@RequestMapping("/informationInterfaces.api")
public class InformationInterfaces extends BaseController{
	
	@Resource
	InformationService informationService;
	
	
	/**
	 * 获得所有咨询列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getInformationsByTag", method = RequestMethod.POST)
	public void getInformationsByTag(InformationBean informationBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {	
		
		WriteObject(response, informationService.getInformationsByTag(informationBean,pageBean),pageBean.getTotal());
	}
	
	
	
	/**
	 * 获得咨询详情
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getInformationDetail", method = RequestMethod.POST)
	public void getInformationDetail(InformationBean informationBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {	
				
		InformationBean informationBean2=informationService.getInformationDetail(informationBean);

		String desc = readHtml(request, informationBean2.getInformation_url());
		int start = desc.indexOf("<tst>");
		int end = desc.indexOf("</tst>");
		if (start > 0 && end > 0) {
			desc = desc.substring(start + 5, end);
		}
		
		WriteObject(response,informationBean2.setInformation_url_desc(desc));
	}
	
	
	/**
	 * 获得所有咨询列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getInformations", method = RequestMethod.POST)
	public void getInformations(InformationBean informationBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {	
		
		WriteObject(response, informationService.getInformations(informationBean,pageBean),pageBean.getTotal());
	}
	
	/**
	 * 推荐咨询
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getRecomendInformations", method = RequestMethod.POST)
	public void getRecomendInformations(InformationBean informationBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {	
		
		WriteObject(response, informationService.getRecomendInformations(informationBean,pageBean));
	}
	
	/**
	 * 推荐咨询
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getLeftRecomendInformations", method = RequestMethod.POST)
	public void getLeftRecomendInformations(InformationBean informationBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {	
		
		WriteObject(response, informationService.getLeftRecomendInformations(informationBean,pageBean));
	}
}
