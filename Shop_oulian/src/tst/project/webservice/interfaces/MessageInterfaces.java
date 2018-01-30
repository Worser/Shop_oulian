package tst.project.webservice.interfaces;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tst.project.bean.member.MemberBean;
import tst.project.bean.member.MessageBean;
import tst.project.page.PageBean;
import tst.project.service.interfaces.MemberService;
import tst.project.service.interfaces.MessageService;
import tst.project.webservice.controller.BaseController;


@Controller
@RequestMapping("/messageInterfaces.api")
public class MessageInterfaces extends BaseController {
	@Resource
	MessageService messageService;

	@Resource
	MemberService memberService;

	/**
	 * 添加系统消息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "addMessage", method = RequestMethod.POST)
	public void addMessage(HttpServletRequest request, HttpServletResponse response,
			MessageBean messageBean,MemberBean memberBean) {
		try {
			if (memberService.verificationToken(memberBean) == null) {
				WritePending(response, "token failed");
				return;
			}

			int num = messageService.addMessage(messageBean);
			if (num >= 0) {
				WriteObject(response, "添加消息成功");
			} else {
				WriteError(response, "添加消息失败!");
			}
		} catch (Exception e) {
			WriteError(response, e.getMessage().toString());
		}
	}
	
	
	/**
	 * 查询系统消息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getMessageBean", method = RequestMethod.POST)
	public void getMessageBean(HttpServletRequest request, HttpServletResponse response,
			MessageBean messageBean,MemberBean memberBean,
			PageBean pageBean) {
		try {
			if (memberService.verificationToken(memberBean) == null) {
				WritePending(response, "token failed");
				return;
			}

			WriteObject(response, messageService.getMessageBean(messageBean, pageBean),pageBean.getTotal());
		} catch (Exception e) {
			WriteError(response, e.getMessage().toString());
		}
	}
	
	
	/**
	 * 查询有多少未读信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getNotReadMessageCount", method = RequestMethod.POST)
	public void getNotReadMessageCount(HttpServletRequest request, HttpServletResponse response,
			MessageBean messageBean,MemberBean memberBean) {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}

		WriteObject(response, messageService.getNotReadMessageCount(messageBean));
	}
	
	/**
	 * 查询单个消息详情 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getOneMessageDateil", method = RequestMethod.POST)
	public void getOneMessageDateil(HttpServletRequest request, HttpServletResponse response,
			MessageBean messageBean,MemberBean memberBean) {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}

		messageService.messageIsRead(messageBean);
		WriteObject(response, messageService.getOneMessageDateil(messageBean));
	}
	
	/**
	 * 全部设为已读
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "allMessageIsRead", method = RequestMethod.POST)
	public void allMessageIsRead(HttpServletRequest request, HttpServletResponse response,
			MessageBean messageBean,MemberBean memberBean) {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}

		int num = messageService.allMessageIsRead(messageBean);
		if(num > 0)
		{
			WriteObject(response, "全部标记成功");
		}else
		{
			WriteError(response, "全部标记失败");
		}
		
	}
	
	/**
	 * 批量删除
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "batchDelete", method = RequestMethod.POST)
	public void batchDelete(HttpServletRequest request, HttpServletResponse response,
			MessageBean messageBean,MemberBean memberBean) {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}

		int num = messageService.batchDelete(messageBean);
		if(num > 0)
		{
			WriteObject(response, "批量删除成功");
		}else
		{
			WriteError(response, "批量删除失败");
		}
	}
	
	/**
	 * 全部删除
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "allDelete", method = RequestMethod.POST)
	public void allDelete(HttpServletRequest request, HttpServletResponse response,
			MessageBean messageBean,MemberBean memberBean) {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}

		int num = messageService.allDelete(messageBean);
		if(num > 0)
		{
			WriteObject(response, "全部删除成功");
		}else
		{
			WriteError(response, "全部删除失败");
		}
	}
	
	/**
	 * 单个删除
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "oneDelete", method = RequestMethod.POST)
	public void oneDelete(HttpServletRequest request, HttpServletResponse response,
			MessageBean messageBean,MemberBean memberBean) {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}

		int num = messageService.oneDelete(messageBean);
		if(num > 0)
		{
			WriteObject(response, "删除成功");
		}else
		{
			WriteError(response, "删除失败");
		}
	}
}
