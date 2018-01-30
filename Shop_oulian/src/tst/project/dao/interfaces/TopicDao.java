package tst.project.dao.interfaces;

import java.util.List;

import tst.project.bean.bbs.CommentBean;
import tst.project.bean.bbs.ReplyBean;
import tst.project.bean.bbs.ToReplyBean;
import tst.project.bean.bbs.TopicBean;
import tst.project.bean.member.MemberBean;
import tst.project.page.PageBean;

public interface TopicDao {
	/**
	 * 发布帖子
	 */
	public int addTopic(TopicBean topicBean);

	public int updateTopic(TopicBean topicBean);

	/**
	 * 默认帖子
	 */
	public List<TopicBean> getTopic(TopicBean topicBean,PageBean pageBean);

	/**
	 * 最新帖
	 */
	public List<TopicBean> getRecentTopic(TopicBean topicBean,PageBean pageBean);

	/**
	 * 精华帖
	 */
	public List<TopicBean> getEssenceTopic(TopicBean topicBean,PageBean pageBean);

	/**
	 * 热门帖子
	 * 
	 * @param paramTopicBean
	 * @param paramPageBean
	 * @return
	 */
	public   List<TopicBean> getHotTopics(TopicBean topicBean,
			PageBean pageBean);

	/**
	 * 帖子总数
	 */
	public int getAmount(TopicBean topicBean);

	public  TopicBean getTopicDetail(TopicBean topicBean);

	/**
	 * 增加点击量
	 */
	public int getViewCount(TopicBean topicBean);

	public  List<TopicBean> getRecentTopicsByBlock(
			TopicBean topicBean, PageBean pageBean);

	public   List<TopicBean> getEssenceTopicByBlock(
			TopicBean topicBean, PageBean pageBean);

	public   List<TopicBean> getHotSearchTopics(TopicBean topicBean);

	public   int updateTopicDetail(TopicBean topicBean);

	public   int updateThumbCount(TopicBean topicBean);

	public   int updateTopicCollectionCount(TopicBean topicBean);

	public   int updateCommentsCount(TopicBean topicBean);

	public   int updateCollectionsCount(TopicBean topicBean);

	public   int updateCommentReplyCounts(CommentBean commentBean);

	public   int addTopicComment(CommentBean commentBean);

	public   CommentBean getTopicCommentFloor(
			CommentBean commentBean);
	/**
	 * 获得某个板块中所有的帖子
	 * @param paramTopicBean
	 * @param paramPageBean
	 * @return
	 */
	public abstract List<TopicBean> getBlockAllTopics(TopicBean paramTopicBean, PageBean paramPageBean);
	 
	public   List<CommentBean> getCommentBeans(
			CommentBean commentBean, PageBean pageBean);

	public   CommentBean getCommentBeanDetail(
			CommentBean commentBean);

	public   int addTopicReply(ReplyBean replyBean);

	public   int addToReply(ToReplyBean toReplyBean);

	public   List<ReplyBean> getTopicReplyBeans(
			ReplyBean replyBean, PageBean pageBean);

	public   List<ReplyBean> getTopicReplyBeans(ReplyBean replyBean);

	public   List<ToReplyBean> getToReply(ToReplyBean toReplyBean,
			PageBean pageBean);

	public   List<TopicBean> memberTopicBeans(TopicBean topicBean,
			PageBean pageBean);

	public   List<TopicBean> getMyCommentTopics(TopicBean topicBean);

	public   List<TopicBean> getMyReplyTopics(TopicBean topicBean);

	public   List<TopicBean> getMyPartakeTopics(
			TopicBean topicBean, PageBean pageBean);

	public   List<TopicBean> getMemberActives(TopicBean topicBean);

	public   List<TopicBean> getTopicDateList(TopicBean topicBean,
			PageBean pageBean);

	public   int updateSearchTopics(TopicBean topicBean);

	public   int recycleTopic(TopicBean topicBean);

	public   int recoveryTopics(TopicBean topicBean);

	public   List<TopicBean> recycleTopicList(TopicBean topicBean,
			PageBean pageBean);

	public   int deleteTopic(TopicBean topicBean);

	public   List<MemberBean> getMemberInformation(
			MemberBean memberBean);

	public   List<TopicBean> getMyCollections(TopicBean topicBean,
			PageBean pageBean);

	public   List<MemberBean> getMemeberDetialByTopics(
			MemberBean memberBean);
}
