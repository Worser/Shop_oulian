package tst.project.service.interfaces;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tst.project.bean.bbs.CommentBean;
import tst.project.bean.bbs.ReplyBean;
import tst.project.bean.bbs.ToReplyBean;
import tst.project.bean.bbs.TopicBean;
import tst.project.bean.goods.GoodsBean;
import tst.project.bean.member.CustomerServiceBean;
import tst.project.bean.member.MainBrandBean;
import tst.project.bean.member.MemberBean;
import tst.project.bean.member.MemberLevelBean;
import tst.project.bean.member.MemberProfitBean;
import tst.project.bean.member.MemberQualificationBean;
import tst.project.dao.interfaces.MemberDao;
import tst.project.dao.interfaces.TopicDao;
import tst.project.page.PageBean;
import tst.project.utils.NumberUtils;

@Service
@Transactional(rollbackFor = Exception.class)
public class TopicService {
	
	private static final String Page = null;
	
	@Resource
	  TopicDao topicDao;

	  @Resource
	  MemberDao memberDao;

	  @Resource
	  MemberQualificationService mqService;

	  @Resource
	  MainBrandService mainBrandService;

	  @Resource
	  MemberService memberService;

	  @Resource
	  CodeService codeService;

	  @Resource
	  MessageService messageService;
	
	/**
	 * 添加文章
	 */
	@Transactional
	public int addTopic(TopicBean topicBean){
		return topicDao.addTopic(topicBean);
	}
	public int updateTopic(TopicBean topicBean) {
		return topicDao.updateTopic(topicBean);
	}
	/**
	 * 默认所有帖子
	 */
	public List<TopicBean> getTopic(TopicBean topicBean, PageBean pageBean)
	  {
	    List<TopicBean> topicBeans = topicDao.getTopic(topicBean, pageBean);
	    if (topicBeans.size() > 0) {
	      for (int i = 0; i < topicBeans.size(); i++)
	      {
	        List<MainBrandBean> mainBeanList = mainBrandService.getMainBrandList(new MainBrandBean()
	          .setMember_id(topicBeans.get(i).getMember_id()+""));
	        topicBeans.get(i).setMainBeanList(mainBeanList);

	        List<CustomerServiceBean> getMemberCustomerServiceQQ = memberDao.getMemberCustomerServiceQQ(new CustomerServiceBean()
	          .setMember_id(topicBeans.get(i).getMember_id()+""));
	        topicBeans.get(i).setCustomerServiceBeans(getMemberCustomerServiceQQ);
	      }
	    }
	    return topicBeans;
	  }
	/**
	 * 最新贴
	 */
	public List<TopicBean> getRecentTopic(TopicBean topicBean,PageBean pageBean){
		 List<TopicBean> topicBeans = topicDao.getRecentTopic(topicBean, pageBean);
		    if (topicBeans.size() > 0) {
		      for (int i = 0; i < topicBeans.size(); i++)
		      {
		        List<MainBrandBean> mainBeanList = this.mainBrandService.getMainBrandList(new MainBrandBean()
		          .setMember_id(topicBeans.get(i).getMember_id()+""));
		        topicBeans.get(i).setMainBeanList(mainBeanList);

		        List<CustomerServiceBean> getMemberCustomerServiceQQ = this.memberDao.getMemberCustomerServiceQQ(new CustomerServiceBean()
		          .setMember_id(topicBeans.get(i).getMember_id()+""));
		        topicBeans.get(i).setCustomerServiceBeans(getMemberCustomerServiceQQ);
		      }
		    }
		    return topicBeans;
	}
	/**
	 * 增加点击量
	 */
	public int getViewCount(TopicBean topicBean) {
		return topicDao.updateTopic(topicBean);
	}
	/**
	 * 精华帖
	 */
	public List<TopicBean> getEssenceTopic(TopicBean topicBean,PageBean pageBean){
		List<TopicBean> topicBeans = topicDao.getEssenceTopic(topicBean, pageBean);
	    if (topicBeans.size() > 0) {
	      for (int i = 0; i < topicBeans.size(); i++)
	      {
	        List<MainBrandBean> mainBeanList = mainBrandService.getMainBrandList(new MainBrandBean()
	          .setMember_id(topicBeans.get(i).getMember_id()+""));
	        topicBeans.get(i).setMainBeanList(mainBeanList);

	        List<CustomerServiceBean> getMemberCustomerServiceQQ = memberDao.getMemberCustomerServiceQQ(new CustomerServiceBean()
	          .setMember_id(topicBeans.get(i).getMember_id()+""));
	        topicBeans.get(i).setCustomerServiceBeans(getMemberCustomerServiceQQ);
	      }
	    }
	    return topicBeans;
	}
	/**
	 * 帖子总数
	 */
	public int getAmount(TopicBean topicBean){
		return topicDao.getAmount(topicBean);
	}
	/**
	 * 热门帖子
	 * @param topicBean
	 * @param pageBean
	 * @return
	 */
	public List<TopicBean> getHotTopics(TopicBean topicBean, PageBean pageBean)
	  {
	    List<TopicBean> topicBeans = topicDao.getHotTopics(topicBean, pageBean);
	    if (topicBeans.size() > 0) {
	      for (int i = 0; i < topicBeans.size(); i++)
	      {
	        List<MainBrandBean> mainBeanList = this.mainBrandService.getMainBrandList(new MainBrandBean()
	          .setMember_id(topicBeans.get(i).getMember_id()+""));
	        topicBeans.get(i).setMainBeanList(mainBeanList);

	        List<CustomerServiceBean> getMemberCustomerServiceQQ = this.memberDao.getMemberCustomerServiceQQ(new CustomerServiceBean()
	          .setMember_id(topicBeans.get(i).getMember_id()+""));
	        topicBeans.get(i).setCustomerServiceBeans(getMemberCustomerServiceQQ);
	      }
	    }
	    return topicBeans;
	  }
	/**
	 * 获得帖子详情
	 * @param topicBean
	 * @return
	 */
	 public TopicBean getTopicDetail(TopicBean topicBean)
	  {
//	    int num1 = updateTopicDetail(topicBean);
	    TopicBean topicBeans = topicDao.getTopicDetail(topicBean);
	    if (topicBeans != null)
	    {
	      List<MainBrandBean> mainBeanList = this.mainBrandService.getMainBrandList(new MainBrandBean()
	        .setMember_id(topicBeans.getMember_id()+""));
	      topicBeans.setMainBeanList(mainBeanList);

	      List<CustomerServiceBean> getMemberCustomerServiceQQ = this.memberDao.getMemberCustomerServiceQQ(new CustomerServiceBean()
	        .setMember_id(topicBeans.getMember_id()+""));
	      topicBeans.setCustomerServiceBeans(getMemberCustomerServiceQQ);
	    }
	    return topicBeans;
	  }
	 /**
	  * 修改帖子详情
	  * @param topicBean
	  * @return
	  */
	  public int updateTopicDetail(TopicBean topicBean)
	  {
	    return this.topicDao.updateTopicDetail(topicBean);
	  }
	  /**
	   * 获得某个板块中所有的帖子
	   * @param topicBean
	   * @param pageBean
	   * @return
	   */
	  public List<TopicBean> getBlockAllTopics(TopicBean topicBean, PageBean pageBean)
	  {
	    return topicDao.getBlockAllTopics(topicBean, pageBean);
	  }
	  /**
	   * 获得最新板块中的所有帖子
	   */
	  public List<TopicBean> getRecentTopicsByBlock(TopicBean topicBean, PageBean pageBean)
	  {
	    return topicDao.getRecentTopicsByBlock(topicBean, pageBean);
	  }
	  /**
	   * 精华帖
	   * @param topicBean
	   * @param pageBean
	   * @return
	   */
	  public List<TopicBean> getEssenceTopicByBlock(TopicBean topicBean, PageBean pageBean)
	  {
	    return topicDao.getEssenceTopicByBlock(topicBean, pageBean);
	  }
	  /**
	   * 热搜贴
	   * @param topicBean
	   * @return
	   */
	  public List<TopicBean> getHotSearchTopics(TopicBean topicBean)
	  {
	    List<TopicBean> topicBeans = topicDao.getHotSearchTopics(topicBean);
	    if (topicBeans.size() > 0) {
	      for (int i = 0; i < topicBeans.size(); i++)
	      {
	        List<MainBrandBean> mainBeanList = this.mainBrandService.getMainBrandList(new MainBrandBean()
	          .setMember_id(topicBeans.get(i).getMember_id()+""));
	        topicBeans.get(i).setMainBeanList(mainBeanList);

	        List<CustomerServiceBean> getMemberCustomerServiceQQ = this.memberDao.getMemberCustomerServiceQQ(new CustomerServiceBean()
	          .setMember_id(topicBeans.get(i).getMember_id()+""));
	        topicBeans.get(i).setCustomerServiceBeans(getMemberCustomerServiceQQ);
	      }
	    }
	    return topicBeans;
	  }
	  /**
	   * 修改评论数
	   * @param topicBean
	   * @return
	   */
	  public int updateCommentsCount(TopicBean topicBean)
	  {
	    return topicDao.updateCommentsCount(topicBean);
	  }
	  /**
	   * 添加评论
	   * @param commentBean
	   * @return
	   */
	  public int addTopicComment(CommentBean commentBean)
	  {
	    int comment_floor = 1;
	    CommentBean commentBean1 = topicDao.getTopicCommentFloor(commentBean);
	    if (commentBean1 != null) {
	      comment_floor += commentBean1.getComment_floor();
	    }

	    int num = topicDao.addTopicComment(commentBean.setComment_floor(comment_floor));
	    if (num > 0) {
	      topicDao.updateCommentsCount(new TopicBean().setUpdate_type("add").setId(commentBean.getId()));
	    }

	    return num;
	  }
	  /**
	   * 获得评论
	   * @param commentBean
	   * @param pageBean
	   * @return
	   */
	  public List<CommentBean> getCommentBeans(CommentBean commentBean, PageBean pageBean)
	  {
	    List<CommentBean> commentBeans = topicDao.getCommentBeans(commentBean, pageBean);
	    if (commentBeans != null)
	    {
	      for (int i = 0; i < commentBeans.size(); i++) {
	        List<ReplyBean> replyBeans = this.topicDao.getTopicReplyBeans(
	          new ReplyBean().setComment_id(commentBeans.get(i).getComment_id()));
	        commentBeans.get(i).setReplyBeans(replyBeans);
	      }
	    }
	    return commentBeans;
	  }
	  /**
	   * 获得评论详情
	   * @param commentBean
	   * @return
	   */
	  public CommentBean getCommentBeanDetail(CommentBean commentBean)
	  {
	    CommentBean commentBean1 = this.topicDao.getCommentBeanDetail(commentBean);
	    if (commentBean1 != null)
	    {
	      List<ReplyBean> replyBeans = this.topicDao.getTopicReplyBeans(new ReplyBean()
	        .setComment_id(commentBean1.getComment_id()));
	      commentBean1.setReplyBeans(replyBeans);
	    }
	    return commentBean1;
	  }
	  /**
	   * 获得回复
	   * @param toReplyBean
	   * @param pageBean
	   * @return
	   */
	  public List<ToReplyBean> getToReply(ToReplyBean toReplyBean, PageBean pageBean)
	  {
	    return topicDao.getToReply(toReplyBean, pageBean);
	  }
	  /**
	   * 添加回复
	   * @param replyBean
	   * @return
	   */
	  public int addTopicReply(ReplyBean replyBean)
	  {
	    int num = topicDao.addTopicReply(replyBean);
	    if (num > 0) {
	      topicDao.updateCommentReplyCounts(new CommentBean().setComment_id(replyBean.getComment_id()));
	    }
	    return num;
	  }
	  /**
	   * 添加回复的回复
	   * @param toReplyBean
	   * @return
	   */
	  public int addToReply(ToReplyBean toReplyBean)
	  {
	    return topicDao.addToReply(toReplyBean);
	  }
	  /**
	   * 获得某个人的帖子
	   * @param topicBean
	   * @param pageBean
	   * @return
	   */
	  public List<TopicBean> memberTopicBeans(TopicBean topicBean, PageBean pageBean)
	  {
	    List<TopicBean> topicBeans = topicDao.memberTopicBeans(topicBean, pageBean);
	    return topicBeans;
	  }
	  /**
	   * 获得我评论过的帖子
	   * @param topicBean
	   * @param pageBean
	   * @return
	   */
	  public List<TopicBean> getMyCommentTopics(TopicBean topicBean, PageBean pageBean)
	  {
	    List<TopicBean> topicBeans = topicDao.getMyCommentTopics(topicBean);
	    return topicBeans;
	  }
	  /**
	   * 我回复过的帖子
	   * @param topicBean
	   * @param pageBean
	   * @return
	   */
	  public List<TopicBean> getMyReplyTopics(TopicBean topicBean, PageBean pageBean)
	  {
	    List<TopicBean> topicBeans = topicDao.getMyReplyTopics(topicBean);
	    return topicBeans;
	  }
	  /**
	   * 获得我分享的帖子
	   * @param topicBean
	   * @param pageBean
	   * @return
	   */
	  public List<TopicBean> getMyPartakeTopics(TopicBean topicBean, PageBean pageBean)
	  {
	    List<TopicBean> topicBeans = topicDao.getMyPartakeTopics(topicBean, pageBean);
	    return topicBeans;
	  }
	  
	  public List<TopicBean> getMemberActives(TopicBean topicBean)
	  {
	    List<TopicBean> topicBeans = topicDao.getMemberActives(topicBean);
	    return topicBeans;
	  }

	  public List<TopicBean> getTopicDateList(TopicBean topicBean, PageBean pageBean)
	  {
	    List<TopicBean> topicBeans = new ArrayList();
	    topicBeans = topicDao.getTopicDateList(topicBean, pageBean);
	    return topicBeans;
	  }
	  
	  public int updateSearchTopics(TopicBean topicBean)
	  {
	    return topicDao.updateSearchTopics(topicBean);
	  }
	  /**
	   * 删除帖子
	   * @param topicBean
	   * @return
	   */
	  public int recycleTopic(TopicBean topicBean)
	  {
	    return topicDao.recycleTopic(topicBean);
	  }
	  /**
	   * 恢复帖子
	   * @param topicBean
	   * @return
	   */
	  public int recoveryTopics(TopicBean topicBean)
	  {
	    return topicDao.recoveryTopics(topicBean);
	  }
	  /**
	   * 删除过的帖子（列表）
	   * @param topicBean
	   * @param pageBean
	   * @return
	   */
	  public List<TopicBean> recycleTopicList(TopicBean topicBean, PageBean pageBean)
	  {
	    List<TopicBean> topicBeans = topicDao.recycleTopicList(topicBean, pageBean);
	    return topicBeans;
	  }
	  /**
	   * 删除帖子
	   * @param topicBean
	   * @return
	   */
	  public int deleteTopic(TopicBean topicBean)
	  {
	    return topicDao.deleteTopic(topicBean);
	  }

	  public MemberBean getMemeberDetialByTopics(MemberBean memberBean)
	  {
	    MemberBean memberBean1 = memberDao.getMemberByID(memberBean);
	    if (memberBean1 != null)
	    {
	      List<GoodsBean> goodsSalesBeans = memberDao.getMemberSalesGoods(new MemberProfitBean()
	        .setMerchants_id(memberBean1.getMerchants_id()).setMember_id(memberBean.getMember_account()));
	      memberBean1.setGoodsSalesBeans(goodsSalesBeans);

	      MemberLevelBean memberLevelBean = memberDao.getMemberLevelDetail(new MemberLevelBean()
	        .setLevel_id(NumberUtils.Integer(memberBean1.getMember_level())));
	      memberBean1.setMemberLevelBean(memberLevelBean);

	      MemberQualificationBean memberQualificationBean1 = mqService.getMemberQualification(new MemberQualificationBean()
	        .setMember_id(memberBean.getMember_id()+""));
	      if (memberQualificationBean1 != null)
	      {
	        memberBean1.setQualification_state(memberQualificationBean1.getQualification_state());
	        memberBean1.setMemberQualificationBean(memberQualificationBean1);
	      }

	      List<MainBrandBean> list1 = this.mainBrandService.getMainBrandList(new MainBrandBean()
	        .setMember_id(String.valueOf(memberBean1.getMember_id())));

	      List<CustomerServiceBean> customerServiceBeans = memberDao
	        .getServiceQQByMerchantsId(new CustomerServiceBean().setMerchants_id(memberBean1.getMerchants_id()));
	      memberBean1.setCustomerServiceBeans(customerServiceBeans);

	      memberBean1.setMainBeanList(list1);
	    }
	    return memberBean1;
	  }
	  /**
	   * 我收藏的帖子
	   * @param topicBean
	   * @param pageBean
	   * @return
	   */
	  public List<TopicBean> getMyCollections(TopicBean topicBean, PageBean pageBean)
	  {
	    return topicDao.getMyCollections(topicBean, pageBean);
	  }
}
