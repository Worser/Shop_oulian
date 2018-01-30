package tst.project.service.controller;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tst.project.bean.bbs.CommentBean;
import tst.project.bean.bbs.ReplyBean;
import tst.project.bean.bbs.TopicBean;
import tst.project.bean.member.MemberBean;
import tst.project.dao.controller.TopicDaoC;
import tst.project.page.PageBean;

@Service
@Transactional(rollbackFor = { Exception.class })
public class TopicServiceC {

	@Resource
	TopicDaoC topicDaoC;

	@Resource
	MemberServiceC memberServiceC;

	/**
	 * 获得所有帖子
	 * 
	 * @param topicBean
	 * @param pageBean
	 * @return
	 */
	public List<TopicBean> getAllTopics(TopicBean topicBean, PageBean pageBean) {
		return topicDaoC.getAllTopics(topicBean, pageBean);
	}

	/**
	 * 删除/恢复帖子
	 * 
	 * @param topicBean
	 * @return
	 */
	public int deleteTopic(TopicBean topicBean) {
		return topicDaoC.deleteTopic(topicBean);
	}

	/**
	 * 获得帖子详情
	 * 
	 * @param topicBean
	 * @return
	 */
	public TopicBean getTopicDetail(TopicBean topicBean) {
		TopicBean topicBeans = topicDaoC.getTopicDetail(topicBean);
		if (topicBeans != null) {
			MemberBean memberBean = memberServiceC
					.getOneMemberDetail(new MemberBean().setMember_id(Integer
							.valueOf(topicBeans.getMerchants_id()).intValue()));
			topicBeans.setMemberBean(memberBean);
		}
		return topicBeans;
	}

	/**
	 * 获得帖子评论
	 * 
	 * @param commentBean
	 * @param pageBean
	 * @return
	 */
	public List<CommentBean> getTopicComments(CommentBean commentBean,
			PageBean pageBean) {
		List<CommentBean> commentBeans = topicDaoC.getTopicComments(
				commentBean, pageBean);

		return commentBeans;
	}

	/**
	 * 获得评论的回复
	 * 
	 * @param replyBean
	 * @param pageBean
	 * @return
	 */
	public List<ReplyBean> getCommentReplys(ReplyBean replyBean,
			PageBean pageBean) {
		List<ReplyBean> replyBeans = topicDaoC.getCommentReplys(replyBean,
				pageBean);

		return replyBeans;
	}

	/**
	 * 删除评论
	 * 
	 * @param commentBean
	 * @return
	 * @throws Exception
	 */
	public int deleteComment(CommentBean commentBean) throws Exception {
		return topicDaoC.deleteComment(commentBean);
	}

	/**
	 * 修改帖子详情
	 * 1.设置帖子为精品
	 * @param topicBean
	 * @return
	 * @throws Exception
	 */
	public int updateTopicDetail(TopicBean topicBean) throws Exception {
		return topicDaoC.updateTopicDetail(topicBean);
	}
}