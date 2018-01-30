package tst.project.webservice.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tst.project.bean.merchants.MerchantsAccountBean;
import tst.project.bean.others.AdviceBean;
import tst.project.page.PageBean;
import tst.project.service.controller.AdviceServiceC;
import tst.project.service.controller.SystemService;
import tst.project.webservice.controller.BaseController;


@Controller
@RequestMapping("/adviceController.api")
public class AdviceController extends BaseController {
	@Resource
	AdviceServiceC adviceServiceC;

	@Resource
	SystemService systemService;
	
	/**
	 * 提交建议或者投诉
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getMemberAdvice", method = RequestMethod.POST)
	public void getMemberAdvice(HttpServletRequest request, HttpServletResponse response,
			AdviceBean adviceBean,MerchantsAccountBean merchantsAccountBean,PageBean pageBean) {
		try {
			if(!systemService.verToken(merchantsAccountBean)){
				WritePending(response, "token failed");
				return;
			}

			WriteObject(response, adviceServiceC.getMemberAdvice(adviceBean,pageBean),pageBean.getTotal());
		} catch (Exception e) {
			WriteError(response, e.getMessage().toString());
		}
	}
	
}
