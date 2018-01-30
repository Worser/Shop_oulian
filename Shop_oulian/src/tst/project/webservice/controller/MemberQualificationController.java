package tst.project.webservice.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



import tst.project.bean.member.MemberBean;
import tst.project.bean.member.MemberQualificationBean;
import tst.project.bean.merchants.MerchantsAccountBean;
import tst.project.page.PageBean;
import tst.project.service.controller.MemberQualificationServiceC;

import tst.project.service.controller.SystemService;
import tst.project.service.interfaces.MemberImgService;
import tst.project.webservice.controller.BaseController;


@Controller
@RequestMapping("/memberQualificationController.api")
public class MemberQualificationController extends BaseController{
	
	@Resource
	MemberQualificationServiceC mqService;
	
	@Resource
	SystemService systemService;
	
	@Resource
	MemberImgService memberImgService;
	
	/**
	 * 获得用户资质认证信息（全部）
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getMemberQualification", method = RequestMethod.POST)
	public void getMemberQualification(HttpServletRequest request,HttpServletResponse response,
			MerchantsAccountBean merchantsAccountBean,MemberQualificationBean memberQualificationBean,
			PageBean pageBean) throws Exception
	{
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		List<MemberQualificationBean> memberQualificationBeans = mqService.getMemberQualification(memberQualificationBean,pageBean);
		WriteObject(response, memberQualificationBeans,pageBean.getTotal());
	}	
	
	/**
	 * 获得用户资质认证信息（单个）
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getMemberQualificationById", method = RequestMethod.POST)
	public void getMemberQualificationById(HttpServletRequest request
			,HttpServletResponse response,
			MerchantsAccountBean merchantsAccountBean
			,MemberQualificationBean memberQualificationBean) throws Exception
	{
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		WriteObject(response, mqService.getMemberQualificationById(memberQualificationBean));
	}	
	
	/**
	 * 通过资质认证
	 */
	@RequestMapping(params = "throughQualification", method = RequestMethod.POST)
	public void throughQualification(HttpServletRequest request,HttpServletResponse response,
			MerchantsAccountBean merchantsAccountBean,
			MemberQualificationBean memberQualificationBean) throws Exception
	{
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		int num = mqService.updateQualificationState(memberQualificationBean);
		if(num > 0)
		{
			int num1 = mqService.updateMemberQualificationState(new MemberBean()
			          .setMember_id(Integer.valueOf(memberQualificationBean.getMember_id())));
			if(num1 > 0)
			{
				WriteObject(response, "操作成功");
			}else
			{
				WriteError(response, "修改会员认证状态失败");
			}
			
		}else
		{
			WriteError(response, "操作失败");
		}
	}
	
	/**
	 * 拒绝资质认证
	 */
	@RequestMapping(params = "refusedQualification", method = RequestMethod.POST)
	public void refusedQualification(HttpServletRequest request,HttpServletResponse response,
			MerchantsAccountBean merchantsAccountBean,
			MemberQualificationBean memberQualificationBean) throws Exception
	{
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		int num = mqService.updateQualificationState(memberQualificationBean);
		if(num > 0)
		{
			WriteObject(response, "操作成功");
		}else
		{
			WriteError(response, "操作失败");
		}
	}
	
	/**
	 * 批量通过资质认证
	 */
	@RequestMapping(params = "batchThroughQualification", method = RequestMethod.POST)
	public void batchThroughQualification(HttpServletRequest request,HttpServletResponse response,
			MerchantsAccountBean merchantsAccountBean,
			MemberQualificationBean memberQualificationBean) throws Exception
	{
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		int num = mqService.batchUpdateQualificationState(memberQualificationBean);
		if(num > 0)
		{
			List<MemberQualificationBean> MemberQualificationBeans = mqService.getMemberQualificationBeans(memberQualificationBean);
			if(MemberQualificationBeans.size()>0)
			{
				for (int i = 0; i < MemberQualificationBeans.size(); i++) {
					int num1 = mqService.updateMemberQualificationState(new MemberBean()
			          .setMember_id(Integer.valueOf(MemberQualificationBeans.get(i).getMember_id())));
					if(num1 < 0)
					{
						throw new Exception("修改会员认证状态失败");
					}
				}
				WriteObject(response, "操作成功");
			}else
			{
				WriteObject(response, "无任何会员信息");
			}
			
		}else
		{
			WriteError(response, "操作失败");
		}
	}
	
	/**
	 * 批量拒绝资质认证
	 */
	@RequestMapping(params = "batchRefusedQualification", method = RequestMethod.POST)
	public void batchRefusedQualification(HttpServletRequest request,HttpServletResponse response,
			MerchantsAccountBean merchantsAccountBean,
			MemberQualificationBean memberQualificationBean) throws Exception
	{
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		int num = mqService.batchUpdateQualificationState(memberQualificationBean);
		if(num > 0)
		{
			WriteObject(response, "操作成功");
		}else
		{
			WriteError(response, "操作失败");
		}
	}
	
}
