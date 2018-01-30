package tst.project.webservice.interfaces;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import tst.project.bean.bbs.CollectionTopicBean;
import tst.project.bean.member.MemberBean;
import tst.project.page.PageBean;
import tst.project.service.interfaces.CollectionTopicService;
import tst.project.service.interfaces.MemberService;
import tst.project.webservice.controller.BaseController;

@Controller
@RequestMapping({"/collectionTopicInterfaces.api"})
public class CollectionTopicInterfaces extends BaseController
{

  @Resource
  CollectionTopicService collectionTopicService;

  @Resource
  MemberService memberService;

  @RequestMapping(params={"insertCollectionTopic"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public void insertCollectionTopic(HttpServletRequest request, HttpServletResponse response, CollectionTopicBean collectionTopicBean, MemberBean memberBean, PageBean pageBean)
    throws Exception
  {
    if (this.memberService.verificationToken(memberBean) == null) {
      WritePending(response, "token failed");
      return;
    }
    if (this.collectionTopicService.getCollectionBySearch(collectionTopicBean) == null) {
      int num = this.collectionTopicService.insertCollectionTopic(collectionTopicBean);
      if (num > 0)
        WriteMsg(response, "收藏成功");
      else
        WriteError(response, "收藏失败");
    }
  }

  @RequestMapping(params={"updateCollcetionTopic"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public void updateCollcetionTopic(HttpServletRequest request, HttpServletResponse response, CollectionTopicBean collectionTopicBean, MemberBean memberBean, PageBean pageBean)
    throws Exception
  {
    if (this.memberService.verificationToken(memberBean) == null) {
      WritePending(response, "token failed");
      return;
    }
    int num = this.collectionTopicService.updateCollcetionTopic(collectionTopicBean.setCollection_state("1"));
    if (num > 0)
      WriteObject(response, "取消收藏成功");
    else
      WriteObject(response, "取消收藏成功");
  }

  @RequestMapping(params={"getStateByCollection"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public void getStateByCollection(MemberBean memberBean, CollectionTopicBean collectionTopicBean, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    if (this.memberService.verificationToken(memberBean) == null) {
      WritePending(response, "token failed");
      return;
    }
    WriteObject(response, this.collectionTopicService.getStateByCollection(collectionTopicBean));
  }
}