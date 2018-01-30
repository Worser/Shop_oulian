package tst.project.webservice.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tst.project.bean.merchants.MerchantsAccountBean;
import tst.project.bean.others.AnnouncementBean;
import tst.project.page.PageBean;
import tst.project.service.controller.AnnouncementServiceC;
import tst.project.service.controller.SystemService;
import tst.project.webservice.controller.BaseController;


@Controller
@RequestMapping("/announcementController.api")
public class AnnouncementController extends BaseController {
	@Resource
	AnnouncementServiceC announcementServiceC;

	@Resource
	SystemService systemService;
	
	/**
	 * 查询系统公告
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getAnnouncementBeans", method = RequestMethod.POST)
	public void getAnnouncementBeans(HttpServletRequest request, HttpServletResponse response,
			MerchantsAccountBean merchantsAccountBean,PageBean pageBean,
			AnnouncementBean announcementBean) {
		try {
			if(!systemService.verToken(merchantsAccountBean)){
				WritePending(response, "token failed");
				return;
			}

			WriteObject(response, announcementServiceC.getAnnouncementBeans(announcementBean, pageBean),pageBean.getTotal());
		} catch (Exception e) {
			WriteError(response, e.getMessage().toString());
		}
	}
	
	/**
	 * 查询系统公告
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getAnnouncementDetail", method = RequestMethod.POST)
	public void getAnnouncementDetail(HttpServletRequest request, HttpServletResponse response,
			MerchantsAccountBean merchantsAccountBean,
			AnnouncementBean announcementBean) {
		try {
			if(!systemService.verToken(merchantsAccountBean)){
				WritePending(response, "token failed");
				return;
			}

			WriteObject(response, announcementServiceC.getAnnouncementDetail(announcementBean));
		} catch (Exception e) {
			WriteError(response, e.getMessage().toString());
		}
	}
	
	/**
	 * 添加系统公告
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "addAnnouncement", method = RequestMethod.POST)
	public void addAnnouncement(HttpServletRequest request, HttpServletResponse response,
			MerchantsAccountBean merchantsAccountBean,
			AnnouncementBean announcementBean) {
		try {
			if(!systemService.verToken(merchantsAccountBean)){
				WritePending(response, "token failed");
				return;
			}
			
            int num = announcementServiceC.addAnnouncement(announcementBean);
            if(num > 0)
            {
            	WriteObject(response, "添加成功");
            }else
            {
            	WriteError(response, "添加失败");
            }
		} catch (Exception e) {
			WriteError(response, e.getMessage().toString());
		}
	}
	
	/**
	 * 删除系统公告
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "deleteAnnouncement", method = RequestMethod.POST)
	public void deleteAnnouncement(HttpServletRequest request, HttpServletResponse response,
			MerchantsAccountBean merchantsAccountBean,
			AnnouncementBean announcementBean) {
		try {
			if(!systemService.verToken(merchantsAccountBean)){
				WritePending(response, "token failed");
				return;
			}
			
            int num = announcementServiceC.deleteAnnouncement(announcementBean);
            if(num > 0)
            {
            	WriteObject(response, "操作成功");
            }else
            {
            	WriteError(response, "操作失败");
            }
		} catch (Exception e) {
			WriteError(response, e.getMessage().toString());
		}
	}
	
	/**
	 * 修改系统公告
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "updateAnnouncement", method = RequestMethod.POST)
	public void updateAnnouncement(HttpServletRequest request, HttpServletResponse response,
			MerchantsAccountBean merchantsAccountBean,
			AnnouncementBean announcementBean) {
		try {
			if(!systemService.verToken(merchantsAccountBean)){
				WritePending(response, "token failed");
				return;
			}
			
            int num = announcementServiceC.updateAnnouncement(announcementBean);
            if(num > 0)
            {
            	WriteObject(response, "操作成功");
            }else
            {
            	WriteError(response, "操作失败");
            }
		} catch (Exception e) {
			WriteError(response, e.getMessage().toString());
		}
	}
}
