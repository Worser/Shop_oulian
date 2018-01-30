package tst.project.webservice.interfaces;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tst.project.bean.others.AnnouncementBean;
import tst.project.page.PageBean;
import tst.project.service.interfaces.AnnouncementService;
import tst.project.webservice.controller.BaseController;


@Controller
@RequestMapping("/AnnouncementInterfaces.api")
public class AnnouncementInterfaces extends BaseController {
	@Resource
	AnnouncementService announcementService;

	/**
	 *查询系统公告
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getAnnouncementBeans", method = RequestMethod.POST)
	public void insertMemberAdvice(HttpServletRequest request, HttpServletResponse response,
			PageBean pageBean,AnnouncementBean announcementBean) {

		WriteObject(response, announcementService.getAnnouncementBeans(announcementBean, pageBean),pageBean.getTotal());
	}
	
}
