package tst.project.webservice.interfaces;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tst.project.bean.bbs.CommentBean;
import tst.project.bean.bbs.ReplyBean;
import tst.project.bean.bbs.ToReplyBean;
import tst.project.bean.bbs.TopicBean;
import tst.project.bean.entrust.EntrustBean;
import tst.project.bean.member.MemberBean;
import tst.project.page.PageBean;
import tst.project.service.interfaces.MemberService;
import tst.project.service.interfaces.TopicService;
import tst.project.webservice.controller.BaseController;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;


@Controller
@RequestMapping("/topicInterfaces.api")
public class TopicInterfaces extends BaseController{
	@Resource
	TopicService topicService;
	
	@Resource
	MemberService memberService;
	
	/**
	 * 添加文章
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="addTopic",method=RequestMethod.POST)
	public void addTopic(HttpServletRequest request, HttpServletResponse response,
						 MemberBean memberBean) throws Exception {
		
		if(memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		
		String json = request.getParameter("json");
		List<TopicBean> topicBeans = new Gson().fromJson(json, new TypeToken<List<TopicBean>>(){}.getType());
		if(topicBeans != null) {
			for(int i = 0; i < topicBeans.size(); i++) {
				TopicBean topicBean = topicBeans.get(i);
				int num = topicService.addTopic(topicBean);
				if(num < 0) {
					throw new Exception("帖子添加失败");
				}else {
					WriteObject(response, "帖子发布成功");
				}
			}
		}else {
			WriteObject(response, "无数据");
		}
//		String member_id = request.getParameter("member_id");
		/*Gson gs = new Gson();
		Map<String,String> map = gs.fromJson(json, Map.class);
		System.out.println(map.size());
		System.out.println("=====");
		System.out.println(map.get("blockId"));*/
//		String[] strArray = json.split(";");
//		for(int i = 0; i < map.size(); i++){
			/*TopicBean topicBean = new TopicBean();
			topicBean.setBlockId(Integer.parseInt(map.get("blockId")));
			topicBean.setTopicTitle(map.get("topicTitle"));
			topicBean.setTopicContent(map.get("topicContent"));
			topicBean.setMember_id(Integer.parseInt(map.get("member_id")));
			int num = topicService.addTopic(topicBean);
			if(num < 0){
				throw new Exception("发布帖子失败");
			}else {
				WriteObject(response, "帖子发布成功");
			}*/
		}
	/**
	 * 默认帖子
	 * @param request
	 * @param response
	 * @param topicBean
	 */
	@RequestMapping(params="getTopic",method=RequestMethod.POST)
	public void getTopic(HttpServletRequest request, HttpServletResponse response,
							TopicBean topicBean,PageBean pageBean){
		WriteObject(response, topicService.getTopic(topicBean,pageBean),pageBean.getTotal());
	}
	/**
	 * 最新帖
	 * @param request
	 * @param response
	 * @param topicBean
	 * @param memberBean
	 */
	@RequestMapping(params="getRecentTopic",method=RequestMethod.POST)
	public void getRecentTopic(HttpServletRequest request, HttpServletResponse response,
								TopicBean topicBean,PageBean pageBean){
		WriteObject(response, topicService.getRecentTopic(topicBean,pageBean),pageBean.getTotal());
	}
	/**
	 * 精华帖
	 * @param request
	 * @param response
	 * @param topicBean
	 * @param memberBean
	 */
	@RequestMapping(params="getEssenceTopic",method=RequestMethod.POST)
	public void getEssenceTopic(HttpServletRequest request, HttpServletResponse response,
								TopicBean topicBean, MemberBean memberBean,PageBean pageBean){
		if(memberService.verificationToken(memberBean) == null){
			WritePending(response,"token failed");
			return;
		}
		WriteObject(response, topicService.getRecentTopic(topicBean,pageBean),pageBean.getTotal());
	}
	/**
	 * 获得热门帖子
	 * @param request
	 * @param response
	 * @param topicBean
	 * @param memberBean
	 * @param pageBean
	 */
	@RequestMapping(params="getHotTopics", method=RequestMethod.POST)
	  public void getHotTopics(HttpServletRequest request, HttpServletResponse response, TopicBean topicBean, MemberBean memberBean, PageBean pageBean)
	  {
	    if (memberService.verificationToken(memberBean) == null) {
	      WritePending(response, "token failed");
	    }
	    WriteObject(response, topicService.getHotTopics(topicBean, pageBean), pageBean.getTotal());
	  }
	/**
	 * 帖子总数
	 * @param request
	 * @param response
	 * @param topicBean
	 * @param memberBean
	 */
	@RequestMapping(params="getAmount",method=RequestMethod.POST)
	public void getAmount(HttpServletRequest request, HttpServletResponse response,
								TopicBean topicBean, MemberBean memberBean){
		if(memberService.verificationToken(memberBean) == null){
			WritePending(response,"token failed");
			return;
		}
		WriteObject(response, topicService.getAmount(topicBean));
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @param topicBean
	 * @param pageBean
	 * @throws Exception
	 */
	@RequestMapping(params="getBlockAllTopics", method=RequestMethod.POST)
	  public void getBlockAllTopics(HttpServletRequest request, HttpServletResponse response, TopicBean topicBean, PageBean pageBean)
	    throws Exception
	  {
	    WriteObject(response, this.topicService.getBlockAllTopics(
	      topicBean, pageBean), pageBean.getTotal());
	  }
	/**
	 * 
	 * @param request
	 * @param response
	 * @param topicBean
	 * @param pageBean
	 * @throws Exception
	 */
	@RequestMapping(params="getRecentTopicsByBlock", method=RequestMethod.POST)
	  public void getRecentTopicsByBlock(HttpServletRequest request, HttpServletResponse response, TopicBean topicBean, PageBean pageBean)
	    throws Exception
	  {
	    WriteObject(response, topicService.getRecentTopicsByBlock(
	      topicBean, pageBean), pageBean.getTotal());
	  }
	/**
	 * 
	 * @param request
	 * @param response
	 * @param topicBean
	 * @param pageBean
	 * @throws Exception
	 */
	@RequestMapping(params="getEssenceTopicByBlock", method=RequestMethod.POST)
	  public void getEssenceTopicByBlock(HttpServletRequest request, HttpServletResponse response, TopicBean topicBean, PageBean pageBean)
	    throws Exception
	  {
	    WriteObject(response, topicService.getEssenceTopicByBlock(
	      topicBean, pageBean), pageBean.getTotal());
	  }
	/**
	 * 
	 * @param request
	 * @param response
	 * @param topicBean
	 * @param pageBean
	 * @throws Exception
	 */
	  @RequestMapping(params="getHotSearchTopics", method=RequestMethod.POST)
	  public void getHotSearchTopics(HttpServletRequest request, HttpServletResponse response, TopicBean topicBean, PageBean pageBean)
	    throws Exception
	  {
	    WriteObject(response, topicService.getHotSearchTopics(
	      topicBean));
	  }
	  /**
	   * 
	   * @param topicBean
	   * @param request
	   * @param response
	   * @throws Exception
	   */
	  @RequestMapping(params="getTopicDetail", method=RequestMethod.POST)
	  public void getTopicDetail(TopicBean topicBean, HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	  {
	    int id = Integer.parseInt(request.getParameter("id"));
	    TopicBean topicBean1 = new TopicBean();
	    topicBean1.setId(id);
	    WriteObject(response, topicService.getTopicDetail(topicBean1));
	  }
	  /**
	   * 
	   * @param request
	   * @param memberBean
	   * @param replyBean
	   * @param response
	   */
	  @RequestMapping(params="addTopicReply", method=RequestMethod.POST)
	  public void addTopicReply(HttpServletRequest request, MemberBean memberBean, ReplyBean replyBean, HttpServletResponse response)
	  {
	    if (memberService.verificationToken(memberBean) == null) {
	      WritePending(response, "token failed");
	      return;
	    }
	    int num = topicService.addTopicReply(replyBean);
	    if (num > 0)
	      WriteObject(response, "回复成功!");
	    else
	      WriteObject(response, "回复失败!");
	  }
	  /**
	   * 
	   * @param request
	   * @param memberBean
	   * @param toReplyBean
	   * @param response
	   */
	  @RequestMapping(params="addToReply", method=RequestMethod.POST)
	  public void addToReply(HttpServletRequest request, MemberBean memberBean, ToReplyBean toReplyBean, HttpServletResponse response)
	  {
	    if (memberService.verificationToken(memberBean) == null) {
	      WritePending(response, "token failed");
	      return;
	    }
	    int num = topicService.addToReply(toReplyBean);
	    if (num > 0)
	      WriteObject(response, "回复成功!");
	    else
	      WriteObject(response, "回复失败!");
	  }
	  /**
	   * 添加帖子评论
	   * @param memberBean
	   * @param request
	   * @param commentBean
	   * @param response
	   * @throws Exception
	   */
	  @RequestMapping(params="addTopicComment", method=RequestMethod.POST)
	  public void addTopicComment(MemberBean memberBean, HttpServletRequest request, CommentBean commentBean, HttpServletResponse response)
	    throws Exception
	  {
	    if (memberService.verificationToken(memberBean) == null) {
	      WritePending(response, "token failed");
	      return;
	    }

	    int num = topicService.addTopicComment(commentBean);
	    if (num > 0)
	      WriteObject(response, "评论成功！");
	    else
	      WriteObject(response, "评论失败！");
	  }
	  /**
	   * 
	   * @param request
	   * @param commentBean
	   * @param pageBean
	   * @param response
	   * @throws Exception
	   */
	  @RequestMapping(params="getCommentBeans", method=RequestMethod.POST)
	  public void getCommentBeans(HttpServletRequest request, CommentBean commentBean, PageBean pageBean, HttpServletResponse response)
	    throws Exception
	  {
	    int id = Integer.parseInt(request.getParameter("id"));
	    CommentBean commentBean1 = new CommentBean();
	    commentBean1.setId(id);
	    WriteObject(response, topicService.getCommentBeans(commentBean, pageBean), 
	      pageBean.getTotal());
	  }
	  /**
	   * 
	   * @param request
	   * @param commentBean
	   * @param pageBean
	   * @param response
	   * @throws Exception
	   */
	  @RequestMapping(params="getCommentBeanDetail", method=RequestMethod.POST)
	  public void getCommentBeanDetail(HttpServletRequest request, CommentBean commentBean, PageBean pageBean, HttpServletResponse response)
	    throws Exception
	  {
	    WriteObject(response, topicService.getCommentBeanDetail(commentBean));
	  }
	  /**
	   * 
	   * @param request
	   * @param toReplyBean
	   * @param pageBean
	   * @param response
	   * @throws Exception
	   */
	  @RequestMapping(params="getToReply", method=RequestMethod.POST)
	  public void getToReply(HttpServletRequest request, ToReplyBean toReplyBean, PageBean pageBean, HttpServletResponse response)
	    throws Exception
	  {
	    WriteObject(response, topicService.getToReply(toReplyBean, pageBean), pageBean.getTotal());
	  }
	  /**
	   * 
	   * @param topicBean
	   * @param request
	   * @param response
	   * @param memberBean
	   * @param pageBean
	   * @throws Exception
	   */
	  @RequestMapping(params="memberTopicBeans", method=RequestMethod.POST)
	  public void memberTopicBeans(TopicBean topicBean, HttpServletRequest request, HttpServletResponse response, MemberBean memberBean, PageBean pageBean)
	    throws Exception
	  {
	    if (memberService.verificationToken(memberBean) == null) {
	      WritePending(response, "token failed");
	      return;
	    }
	    WriteObject(response, topicService.memberTopicBeans(topicBean, pageBean), pageBean.getTotal());
	  }
	  /**
	   * 
	   * @param topicBean
	   * @param request
	   * @param response
	   * @param memberBean
	   * @param pageBean
	   * @throws Exception
	   */
	  @RequestMapping(params="getMyCommentTopics", method=RequestMethod.POST)
	  public void getMyCommentTopics(TopicBean topicBean, HttpServletRequest request, HttpServletResponse response, MemberBean memberBean, PageBean pageBean)
	    throws Exception
	  {
	    if (memberService.verificationToken(memberBean) == null) {
	      WritePending(response, "token failed");
	      return;
	    }
	    WriteObject(response, topicService.getMyCommentTopics(topicBean, pageBean), pageBean.getTotal());
	  }
	  /**
	   * 
	   * @param topicBean
	   * @param request
	   * @param response
	   * @param memberBean
	   * @param pageBean
	   * @throws Exception
	   */
	  @RequestMapping(params="getMyReplyTopics", method=RequestMethod.POST)
	  public void getMyReplyTopics(TopicBean topicBean, HttpServletRequest request, HttpServletResponse response, MemberBean memberBean, PageBean pageBean)
	    throws Exception
	  {
	    if (memberService.verificationToken(memberBean) == null) {
	      WritePending(response, "token failed");
	      return;
	    }
	    WriteObject(response, topicService.getMyReplyTopics(topicBean, pageBean), pageBean.getTotal());
	  }
	  /**
	   * 
	   * @param topicBean
	   * @param request
	   * @param response
	   * @param memberBean
	   * @param pageBean
	   * @throws Exception
	   */
	  @RequestMapping(params="getMyPartakeTopics", method=RequestMethod.POST)
	  public void myPartakeTopics(TopicBean topicBean, HttpServletRequest request, HttpServletResponse response, MemberBean memberBean, PageBean pageBean)
	    throws Exception
	  {
	    if (memberService.verificationToken(memberBean) == null) {
	      WritePending(response, "token failed");
	      return;
	    }
	    WriteObject(response, topicService.getMyPartakeTopics(
	      topicBean, pageBean), pageBean.getTotal());
	  }
	  /**
	   * 
	   * @param topicBean
	   * @param request
	   * @param response
	   * @param memberBean
	   * @throws Exception
	   */
	  @RequestMapping(params="getMemberActives", method=RequestMethod.POST)
	  public void getMemberActives(TopicBean topicBean, HttpServletRequest request, HttpServletResponse response, MemberBean memberBean)
	    throws Exception
	  {
	    WriteObject(response, topicService.getMemberActives(
	      topicBean));
	  }
	  /**
	   * 
	   * @param topicBean
	   * @param request
	   * @param response
	   * @param memberBean
	   * @param pageBean
	   * @throws Exception
	   */
	  @RequestMapping(params="getTopicDateList", method=RequestMethod.POST)
	  public void getTopicDateList(TopicBean topicBean, HttpServletRequest request, HttpServletResponse response, MemberBean memberBean, PageBean pageBean)
	    throws Exception
	  {
	    String topicTitle = request.getParameter("topicTitle");
	    TopicBean topicBean1 = new TopicBean();
	    topicBean1.setTopicTitle(topicTitle);

	    int num = topicService.updateSearchTopics(topicBean1);
	    WriteObject(response, topicService.getTopicDateList(
	      topicBean, pageBean), pageBean.getTotal());
	  }
	  /**
	   * 
	   * @param topicBean
	   * @param request
	   * @param response
	   * @param memberBean
	   * @throws Exception
	   */
	  @RequestMapping(params="recycleTopic", method=RequestMethod.POST)
	  public void recycleTopic(TopicBean topicBean, HttpServletRequest request, HttpServletResponse response, MemberBean memberBean)
	    throws Exception
	  {
	    if (memberService.verificationToken(memberBean) == null) {
	      WritePending(response, "token failed");
	      return;
	    }
	    int num = topicService.recycleTopic(topicBean);
	    if (num > 0)
	      WriteObject(response, "回收成功");
	    else
	      WriteObject(response, "回收失败");
	  }
	  /**
	   * 
	   * @param topicBean
	   * @param request
	   * @param response
	   * @param memberBean
	   * @throws Exception
	   */
	  @RequestMapping(params="recoveryTopics", method=RequestMethod.POST)
	  public void recoveryTopics(TopicBean topicBean, HttpServletRequest request, HttpServletResponse response, MemberBean memberBean)
	    throws Exception
	  {
	    if (memberService.verificationToken(memberBean) == null) {
	      WritePending(response, "token failed");
	      return;
	    }
	    int num = topicService.recoveryTopics(topicBean);
	    if (num > 0)
	      WriteObject(response, "恢复成功");
	    else
	      WriteObject(response, "恢复失败");
	  }
	  /**
	   * 
	   * @param topicBean
	   * @param request
	   * @param response
	   * @param memberBean
	   * @param pageBean
	   * @throws Exception
	   */
	  @RequestMapping(params="recycleTopicList", method=RequestMethod.POST)
	  public void recycleTopicList(TopicBean topicBean, HttpServletRequest request, HttpServletResponse response, MemberBean memberBean, PageBean pageBean)
	    throws Exception
	  {
	    if (memberService.verificationToken(memberBean) == null) {
	      WritePending(response, "token failed");
	      return;
	    }
	    WriteObject(response, topicService.recycleTopicList(
	      topicBean, pageBean), pageBean.getTotal());
	  }
	  /**
	   * 
	   * @param topicBean
	   * @param request
	   * @param response
	   * @param memberBean
	   * @throws Exception
	   */
	  @RequestMapping(params="deleteTopic", method=RequestMethod.POST)
	  public void deleteTopic(TopicBean topicBean, HttpServletRequest request, HttpServletResponse response, MemberBean memberBean)
	    throws Exception
	  {
	    if (memberService.verificationToken(memberBean) == null) {
	      WritePending(response, "token failed");
	      return;
	    }
	    int num = topicService.deleteTopic(topicBean);
	    if (num > 0)
	      WriteObject(response, "删除成功");
	    else
	      WriteObject(response, "删除失败");
	  }
	  /**
	   * 
	   * @param topicBean
	   * @param request
	   * @param response
	   * @param memberBean
	   * @param pageBean
	   * @throws Exception
	   */
	  @RequestMapping(params="getMyCollections", method=RequestMethod.POST)
	  public void getMyCollections(TopicBean topicBean, HttpServletRequest request, HttpServletResponse response, MemberBean memberBean, PageBean pageBean)
	    throws Exception
	  {
	    if (memberService.verificationToken(memberBean) == null) {
	      WritePending(response, "token failed");
	      return;
	    }
	    WriteObject(response, topicService.getMyCollections(topicBean, pageBean), pageBean.getTotal());
	  }
}
