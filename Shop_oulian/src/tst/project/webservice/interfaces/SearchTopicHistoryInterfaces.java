package tst.project.webservice.interfaces;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import tst.project.bean.bbs.SearchTopicHistoryBean;
import tst.project.bean.member.MemberBean;
import tst.project.service.interfaces.MemberService;
import tst.project.service.interfaces.SearchTopicHistoryService;
import tst.project.webservice.controller.BaseController;

@Controller
@RequestMapping({"/searchTopicHistoryInterfaces.api"})
public class SearchTopicHistoryInterfaces extends BaseController
{

  @Resource
  MemberService memberService;

  @Resource
  SearchTopicHistoryService topichistoryService;

  @RequestMapping(params={"addSearchTopicHistory"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public void addSearchTopicHistory(MemberBean memberBean, SearchTopicHistoryBean searchTopicHistoryBean, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    if ((searchTopicHistoryBean.getSearch_name() == null) || 
      (searchTopicHistoryBean.getSearch_name().equals("")) || 
      (searchTopicHistoryBean.getSearch_name().indexOf(" ") >= 0))
    {
      WriteMsg(response, "记录成功");
    }
    else {
      int num = this.topichistoryService.addSearchHistory(searchTopicHistoryBean);
      if (num > 0)
      {
        WriteMsg(response, "记录成功");
      }
      else
        WritePending(response, "记录失败");
    }
  }
}