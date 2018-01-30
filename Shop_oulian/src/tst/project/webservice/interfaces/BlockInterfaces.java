package tst.project.webservice.interfaces;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tst.project.bean.bbs.BlockBean;
import tst.project.bean.member.MemberBean;
import tst.project.service.interfaces.BlockService;
import tst.project.service.interfaces.MemberService;
import tst.project.webservice.controller.BaseController;

@Controller
@RequestMapping("/blockInterfaces.api")
public class BlockInterfaces extends BaseController{
	@Resource
	BlockService blockService;
	
	@Resource
	MemberService memberService;
	
	/**
	 * 获得板块内容
	 * @param request
	 * @param response
	 * @param blockBean
	 */
	@RequestMapping(params = "getContent",method = RequestMethod.POST)
	public void getContent(HttpServletRequest request, HttpServletResponse response,
							BlockBean blockBean){
		WriteObject(response, blockService.getContent(blockBean));
	}
	
	@RequestMapping(params="getBlockName", method = RequestMethod.POST)
	  public void getBlockName(HttpServletRequest request, HttpServletResponse response, BlockBean blockBean)
	  {
	    WriteObject(response, this.blockService.getBlockName(blockBean));
	  }
}
