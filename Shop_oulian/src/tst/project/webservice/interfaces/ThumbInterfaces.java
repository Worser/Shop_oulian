package tst.project.webservice.interfaces;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import tst.project.bean.bbs.ThumbBean;
import tst.project.bean.member.MemberBean;
import tst.project.service.interfaces.MemberService;
import tst.project.service.interfaces.ThumbService;
import tst.project.webservice.controller.BaseController;

@Controller
@RequestMapping({"/thumbInterfaces.api"})
public class ThumbInterfaces extends BaseController
{

  @Resource
  MemberService memberService;

  @Resource
  ThumbService thumbService;

  @RequestMapping(params={"insertThumb"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public void addCollection(MemberBean memberBean, ThumbBean thumbBean, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    if (this.memberService.verificationToken(memberBean) == null) {
      WritePending(response, "token failed");
      return;
    }
    if (this.thumbService.getThumbBySearch(thumbBean) == null)
    {
      int num = this.thumbService.insertThumb(thumbBean);
      if (num > 0)
      {
        WriteObject(response, "点赞成功");
      }
      else
        WriteError(response, "点赞失败");
    }
  }

  @RequestMapping(params={"getStateByThumb"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public void getStateByThumb(MemberBean memberBean, ThumbBean thumbBean, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    WriteObject(response, this.thumbService.getStateByThumb(thumbBean));
  }

  @RequestMapping(params={"updateThumb"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public void updateThumb(MemberBean memberBean, ThumbBean thumbBean, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    if (this.memberService.verificationToken(memberBean) == null) {
      WritePending(response, "token failed");
      return;
    }
    if (this.thumbService.getThumbBySearch(thumbBean) != null) {
      int num = this.thumbService.updateThumb(thumbBean);
      if (num > 0)
      {
        WriteObject(response, "取消点赞成功");
      }
      else
        WriteError(response, "取消点赞失败");
    }
  }
}