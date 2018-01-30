package tst.project.webservice.interfaces;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tst.project.bean.member.MemberBean;
import tst.project.bean.member.MemberSearchHistoryBean;
import tst.project.service.interfaces.MemberSearchHistoryService;
import tst.project.service.interfaces.MemberService;
import tst.project.webservice.controller.BaseController;


@Controller
@RequestMapping("/searchHistoryInterfaces.api")
public class MemberSearchHistoryInterfaces extends BaseController{
	@Resource
	MemberService memberService;
	
	@Resource
	MemberSearchHistoryService historyService;
	
	/**
	 * 查询搜索历史
	 * @param memberBean
	 * @param addressBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getSearchHistory",method = RequestMethod.POST)
	public void getSearchHistory(MemberBean memberBean, MemberSearchHistoryBean memberSearchHistoryBean,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		WriteObject(response, historyService.getSearchHistory(memberSearchHistoryBean)); 
	}
	
	/**
	 * 记录历史搜索
	 * @param memberBean
	 * @param memberSearchHistoryBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "addSearchHistory",method = RequestMethod.POST)
	public void addSearchHistory(MemberBean memberBean, MemberSearchHistoryBean memberSearchHistoryBean,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		
		if(memberSearchHistoryBean.getSearch_name()==null ||
		   memberSearchHistoryBean.getSearch_name().equals("") ||
		   memberSearchHistoryBean.getSearch_name().indexOf(" ")>=0)
		{
			WriteMsg(response, "记录成功");	
		}else
		{
			int num = historyService.addSearchHistory(memberSearchHistoryBean.setIs_delete("0").setSort("1"));
			if(num>0)
			{
			    WriteMsg(response, "记录成功");	
			}else
			{
				WritePending(response, "记录失败");
			}
		}
		
	}
	
	/**
	 * 清除历史记录
	 * @param memberBean
	 * @param memberSearchHistoryBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "removeSearchHistory",method = RequestMethod.POST)
	public void removeSearchHistory(MemberBean memberBean, MemberSearchHistoryBean memberSearchHistoryBean,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		
		int num = historyService.removeSearchHistory(memberSearchHistoryBean);
		if(num>0)
		{
		     WriteMsg(response, "清除成功");	
		}else
		{
			WritePending(response, "清除失败");
		}
	}
	
}
