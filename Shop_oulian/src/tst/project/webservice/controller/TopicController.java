package tst.project.webservice.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tst.project.bean.bbs.CommentBean;
import tst.project.bean.bbs.ReplyBean;
import tst.project.bean.bbs.TopicBean;
import tst.project.bean.merchants.MerchantsAccountBean;
import tst.project.page.PageBean;
import tst.project.service.controller.SystemService;
import tst.project.service.controller.TopicServiceC;

@Controller
@RequestMapping("/topicController.api")
public class TopicController extends BaseController {

	@Resource
	TopicServiceC topicServiceC;

	@Resource
	SystemService systemService;

	/**
	 * 获得所有帖子
	 * 
	 * @param topicBean
	 * @param pageBean
	 * @param request
	 * @param response
	 * @param merchantsAccountBean
	 * @throws Exception
	 */
	@RequestMapping(params = "getAllTopics", method = RequestMethod.POST)
	public void getAllTopics(TopicBean topicBean, PageBean pageBean,
			HttpServletRequest request, HttpServletResponse response,
			MerchantsAccountBean merchantsAccountBean) throws Exception {
		if (!systemService.verToken(merchantsAccountBean)) {
			WritePending(response, "token failed");
			return;
		}
		WriteObject(response, topicServiceC.getAllTopics(topicBean, pageBean),
				pageBean.getTotal());
	}

	/**
	 * 删除/恢复帖子
	 * 
	 * @param topicBean
	 * @param pageBean
	 * @param request
	 * @param response
	 * @param merchantsAccountBean
	 * @throws Exception
	 */
	@RequestMapping(params = "deleteTopic", method = RequestMethod.POST)
	public void deleteTopic(TopicBean topicBean, PageBean pageBean,
			HttpServletRequest request, HttpServletResponse response,
			MerchantsAccountBean merchantsAccountBean) throws Exception {
		if (!systemService.verToken(merchantsAccountBean)) {
			WritePending(response, "token failed");
			return;
		}
		int num = topicServiceC.deleteTopic(topicBean);
		if (num > 0) {
			WriteObject(response, "操作成功");
		} else
			WriteError(response, "操作失败");
	}

	/**
	 * 获得帖子详情
	 * 
	 * @param topicBean
	 * @param request
	 * @param response
	 * @param merchantsAccountBean
	 * @throws Exception
	 */
	@RequestMapping(params = "getTopicDetail", method = RequestMethod.POST)
	public void getTopicDetail(TopicBean topicBean, HttpServletRequest request,
			HttpServletResponse response,
			MerchantsAccountBean merchantsAccountBean) throws Exception {
		if (!systemService.verToken(merchantsAccountBean)) {
			WritePending(response, "token failed");
			return;
		}
		WriteObject(response, topicServiceC.getTopicDetail(topicBean));
	}

	/**
	 * 获得帖子评论
	 * 
	 * @param commentBean
	 * @param pageBean
	 * @param request
	 * @param response
	 * @param merchantsAccountBean
	 * @throws Exception
	 */
	@RequestMapping(params = "getTopicComments", method = RequestMethod.POST)
	public void getTopicComments(CommentBean commentBean, PageBean pageBean,
			HttpServletRequest request, HttpServletResponse response,
			MerchantsAccountBean merchantsAccountBean) throws Exception {
		if (!systemService.verToken(merchantsAccountBean)) {
			WritePending(response, "token failed");
			return;
		}
		WriteObject(response,
				topicServiceC.getTopicComments(commentBean, pageBean),
				pageBean.getTotal());
	}

	/**
	 * 获得评论的回复
	 * 
	 * @param replyBean
	 * @param pageBean
	 * @param request
	 * @param response
	 * @param merchantsAccountBean
	 * @throws Exception
	 */
	@RequestMapping(params = "getCommentReplys", method = RequestMethod.POST)
	public void getCommentReplys(ReplyBean replyBean, PageBean pageBean,
			HttpServletRequest request, HttpServletResponse response,
			MerchantsAccountBean merchantsAccountBean) throws Exception {
		if (!systemService.verToken(merchantsAccountBean)) {
			WritePending(response, "token failed");
			return;
		}
		WriteObject(response,
				topicServiceC.getCommentReplys(replyBean, pageBean),
				pageBean.getTotal());
	}

	/**
	 * 删除评论
	 * 
	 * @param commentBean
	 * @param request
	 * @param response
	 * @param merchantsAccountBean
	 * @throws Exception
	 */
	@RequestMapping(params = "deleteComment", method = RequestMethod.POST)
	public void deleteComment(CommentBean commentBean,
			HttpServletRequest request, HttpServletResponse response,
			MerchantsAccountBean merchantsAccountBean) throws Exception {
		if (!systemService.verToken(merchantsAccountBean)) {
			WritePending(response, "token failed");
			return;
		}
		int num = topicServiceC.deleteComment(commentBean);
		if (num > 0) {
			WriteObject(response, "操作成功");
		} else
			WriteError(response, "操作失败");
		
	}

	/**
	 * 修改帖子详情
	 * 1.设置帖子为精品
	 * @param commentBean
	 * @param topicBean
	 * @param request
	 * @param response
	 * @param merchantsAccountBean
	 * @throws Exception
	 */
	@RequestMapping(params = "updateTopicDetail", method = RequestMethod.POST)
	public void updateTopicDetail(CommentBean commentBean, TopicBean topicBean,
			HttpServletRequest request, HttpServletResponse response,
			MerchantsAccountBean merchantsAccountBean) throws Exception {
		if (!systemService.verToken(merchantsAccountBean)) {
			WritePending(response, "token failed");
			return;
		}
		int num = topicServiceC.updateTopicDetail(topicBean);
		if (num > 0)
			WriteMsg(response, "修改成功");
		else
			WriteMsg(response, "修改失败");
	}
}