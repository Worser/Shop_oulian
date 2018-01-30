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

import tst.project.bean.member.MemberBean;
import tst.project.bean.member.MemberImgBean;
import tst.project.service.interfaces.MemberImgService;
import tst.project.service.interfaces.MemberService;
import tst.project.utils.TimeUtils;
import tst.project.webservice.controller.BaseController;


@Controller
@RequestMapping("/memberImgInterfaces.api")
public class MemberImgInterfaces extends BaseController{
	
	@Resource
	MemberImgService memberImgService;
	
	@Resource
	MemberService memberService;
	
	/**
	 * 添加各种图片，需填写图片类型
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "addMemberImg", method = RequestMethod.POST)
	public void addMemberImg(HttpServletRequest request,HttpServletResponse response,
			MemberBean memberBean,MemberImgBean memberImgBean) throws Exception
	{
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		
		String json = request.getParameter("json");
		
		List<MemberImgBean> imgBean = new Gson().fromJson(json, new TypeToken<List<MemberImgBean>>(){}.getType());
		if(imgBean.size()>0)
		{
			int num = 0;
			for (int i = 0; i < imgBean.size(); i++) {
				
				num = memberImgService.addMemberImg(imgBean.get(i).setMember_id(memberBean.getMember_id()+"")
						.setIs_delete("0").setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")));
				
				if(num<0)
				{
					throw new Exception("图片添加失败");
				}
			}
			
			if(num>0)
			{
				WriteMsg(response, "图片添加成功");
			}else
			{
				WriteError(response, "图片添加失败");
			}
		}else
		{
			WriteError(response, "数据为空，添加失败");
		}
		
	}
	
	/**
	 * 删除图片
	 * @param request
	 * @param response
	 * @param memberBean
	 * @param memberImgBean
	 * @throws Exception
	 */
	@RequestMapping(params = "deleteMemberImg", method = RequestMethod.POST)
	public void deleteMemberImg(HttpServletRequest request,HttpServletResponse response,
			MemberBean memberBean,MemberImgBean memberImgBean) throws Exception
	{
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		
		int num = memberImgService.deleteMemberImg(memberImgBean);
		if(num>0)
		{
			WriteMsg(response, "删除成功");
		}else
		{
			WriteError(response, "删除失败");
		}
	}
	
}
