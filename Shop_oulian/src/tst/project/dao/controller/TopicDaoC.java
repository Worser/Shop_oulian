package tst.project.dao.controller;

import java.util.List;
import tst.project.bean.bbs.CommentBean;
import tst.project.bean.bbs.ReplyBean;
import tst.project.bean.bbs.TopicBean;
import tst.project.page.PageBean;

public interface TopicDaoC {
	/**
	 * 获得所有帖子
	 * 
	 * @param topicBean
	 * @param pageBean
	 * @return
	 */
	public List<TopicBean> getAllTopics(TopicBean topicBean, PageBean pageBean);

	/**
	 * 删除/恢复帖子
	 * 
	 * @param topicBean
	 * @return
	 */
	public int deleteTopic(TopicBean topicBean);

	/**
	 * ' 获得帖子详情
	 * 
	 * @param topicBean
	 * @return
	 */
	public TopicBean getTopicDetail(TopicBean topicBean);

	/**
	 * 获得帖子评论
	 * 
	 * @param commentBean
	 * @param pageBean
	 * @return
	 */
	public List<CommentBean> getTopicComments(CommentBean commentBean,
			PageBean pageBean);

	/**
	 * 获得评论的回复
	 * 
	 * @param replyBean
	 * @param pageBean
	 * @return
	 */
	public List<ReplyBean> getCommentReplys(ReplyBean replyBean,
			PageBean pageBean);

	/**
	 * 删除评论
	 * 
	 * @param commentBean
	 * @return
	 */
	public int deleteComment(CommentBean commentBean);

	/**
	 * 修改帖子详情1.设置帖子为精品
	 * 
	 * @param topicBean
	 * @return
	 */
	public int updateTopicDetail(TopicBean topicBean);
}