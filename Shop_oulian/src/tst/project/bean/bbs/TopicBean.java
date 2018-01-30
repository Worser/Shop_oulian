package tst.project.bean.bbs;

import java.util.List;

import tst.project.bean.member.CustomerServiceBean;
import tst.project.bean.member.MainBrandBean;
import tst.project.bean.member.MemberBean;
import tst.project.bean.member.MemberQualificationBean;

public class TopicBean {
	private int id;									//主题ID
	private String blockId;							//存储主题所属版块的ID值
	private String blockName;
	private int member_id;							//主题作者id
	private String merchants_id;
	private String company_remark;					//主题作者
	private String level_name;
	private String head_path;
	private String company_name;
	private String email_account;
	private String phone;
	private String register_assress;
	private String register_phone;
	private int member_level;
	private String enterprise_remark;
	private String topicTitle;						//主题标题
	private String topicContent;					//主题内容
	private int topicReplyCount;					//主题的回复数
	private int topicViewCount;						//主题的浏览次数
	private int searchTopicCount;
	private ReplyBean lastReply;					//主题的最后回复帖
	private String topicType;						//主题类型：2-置顶；1-精华；0-普通
	private String topicStatus;						//主题状态：2-开发；1-锁定；0-关闭
	private String topicExistReply;					//是否存在回复帖子的标认识：1-有；0-无
	private String topicCreateTime;					//主题的发表时间
	private String topicOperateTime;				//主题被操作的时间
	private String create_time;
	private String topicContentText;
	private int thumb_count;
	private int thumb_state;
	private int topicShareCount;					//主题分享次数
	private int topicCommentCount;					//主题评论次数
	private int topicCollectionCount;				//主题收藏次数
	private int topicRewardCount;					//主题打赏次数
	private int topicReward;						//主题打赏金额
	private int is_delete;							//主题是否删除
	private String is_freeze;
	private int topic_state;
	private String is_thumb;
	private String sort_type;
	private String update_type;
	private List<MainBrandBean> mainBeanList;
	private MemberBean memberBean;
	private List<CustomerServiceBean> customerServiceBeans;
	private MemberQualificationBean memberQualificationBean;
	public int getId() {
		return id;
	}
	public TopicBean setId(int id) {
		this.id = id;
		return this;
	}
	public String getBlockId() {
		return blockId;
	}
	public String getMerchants_id() {
		return merchants_id;
	}
	public TopicBean setMerchants_id(String merchants_id) {
		this.merchants_id = merchants_id;
		return this;
	}
	public TopicBean setBlockId(String blockId) {
		this.blockId = blockId;
		return this;
	}
	public String getBlockName() {
		return blockName;
	}
	public TopicBean setBlockName(String blockName) {
		this.blockName = blockName;
		return this;
	}
	public int getMember_id() {
		return member_id;
	}
	public TopicBean setMember_id(int member_id) {
		this.member_id = member_id;
		return this;
	}
	public String getCompany_remark() {
		return company_remark;
	}
	public TopicBean setCompany_remark(String company_remark) {
		this.company_remark = company_remark;
		return this;
	}
	public String getLevel_name() {
		return level_name;
	}
	public TopicBean setLevel_name(String level_name) {
		this.level_name = level_name;
		return this;
	}
	public String getHead_path() {
		return head_path;
	}
	public TopicBean setHead_path(String head_path) {
		this.head_path = head_path;
		return this;
	}
	public String getCompany_name() {
		return company_name;
	}
	public TopicBean setCompany_name(String company_name) {
		this.company_name = company_name;
		return this;
	}
	public String getEmail_account() {
		return email_account;
	}
	public TopicBean setEmail_account(String email_account) {
		this.email_account = email_account;
		return this;
	}
	public String getPhone() {
		return phone;
	}
	public TopicBean setPhone(String phone) {
		this.phone = phone;
		return this;
	}
	public String getRegister_assress() {
		return register_assress;
	}
	public TopicBean setRegister_assress(String register_assress) {
		this.register_assress = register_assress;
		return this;
	}
	public String getRegister_phone() {
		return register_phone;
	}
	public TopicBean setRegister_phone(String register_phone) {
		this.register_phone = register_phone;
		return this;
	}
	public int getMember_level() {
		return member_level;
	}
	public TopicBean setMember_level(int member_level) {
		this.member_level = member_level;
		return this;
	}
	public String getEnterprise_remark() {
		return enterprise_remark;
	}
	public TopicBean setEnterprise_remark(String enterprise_remark) {
		this.enterprise_remark = enterprise_remark;
		return this;
	}
	public String getTopicTitle() {
		return topicTitle;
	}
	public TopicBean setTopicTitle(String topicTitle) {
		this.topicTitle = topicTitle;
		return this;
	}
	public String getTopicContent() {
		return topicContent;
	}
	public TopicBean setTopicContent(String topicContent) {
		this.topicContent = topicContent;
		return this;
	}
	public int getTopicReplyCount() {
		return topicReplyCount;
	}
	public TopicBean setTopicReplyCount(int topicReplyCount) {
		this.topicReplyCount = topicReplyCount;
		return this;
	}
	public int getTopicViewCount() {
		return topicViewCount;
	}
	public TopicBean setTopicViewCount(int topicViewCount) {
		this.topicViewCount = topicViewCount;
		return this;
	}
	public int getSearchTopicCount() {
		return searchTopicCount;
	}
	public TopicBean setSearchTopicCount(int searchTopicCount) {
		this.searchTopicCount = searchTopicCount;
		return this;
	}
	public ReplyBean getLastReply() {
		return lastReply;
	}
	public TopicBean setLastReply(ReplyBean lastReply) {
		this.lastReply = lastReply;
		return this;
	}
	public String getTopicType() {
		return topicType;
	}
	public TopicBean setTopicType(String topicType) {
		this.topicType = topicType;
		return this;
	}
	public String getTopicStatus() {
		return topicStatus;
	}
	public TopicBean setTopicStatus(String topicStatus) {
		this.topicStatus = topicStatus;
		return this;
	}
	public String getTopicExistReply() {
		return topicExistReply;
	}
	public TopicBean setTopicExistReply(String topicExistReply) {
		this.topicExistReply = topicExistReply;
		return this;
	}
	public String getTopicCreateTime() {
		return topicCreateTime;
	}
	public TopicBean setTopicCreateTime(String topicCreateTime) {
		this.topicCreateTime = topicCreateTime;
		return this;
	}
	public String getTopicOperateTime() {
		return topicOperateTime;
	}
	public TopicBean setTopicOperateTime(String topicOperateTime) {
		this.topicOperateTime = topicOperateTime;
		return this;
	}
	public String getCreate_time() {
		return create_time;
	}
	public TopicBean setCreate_time(String create_time) {
		this.create_time = create_time;
		return this;
	}
	public String getTopicContentText() {
		return topicContentText;
	}
	public TopicBean setTopicContentText(String topicContentText) {
		this.topicContentText = topicContentText;
		return this;
	}
	public int getThumb_count() {
		return thumb_count;
	}
	public TopicBean setThumb_count(int thumb_count) {
		this.thumb_count = thumb_count;
		return this;
	}
	public int getThumb_state() {
		return thumb_state;
	}
	public TopicBean setThumb_state(int thumb_state) {
		this.thumb_state = thumb_state;
		return this;
	}
	public int getTopicShareCount() {
		return topicShareCount;
	}
	public TopicBean setTopicShareCount(int topicShareCount) {
		this.topicShareCount = topicShareCount;
		return this;
	}
	public int getTopicCommentCount() {
		return topicCommentCount;
	}
	public TopicBean setTopicCommentCount(int topicCommentCount) {
		this.topicCommentCount = topicCommentCount;
		return this;
	}
	public int getTopicCollectionCount() {
		return topicCollectionCount;
	}
	public TopicBean setTopicCollectionCount(int topicCollectionCount) {
		this.topicCollectionCount = topicCollectionCount;
		return this;
	}
	public int getTopicRewardCount() {
		return topicRewardCount;
	}
	public TopicBean setTopicRewardCount(int topicRewardCount) {
		this.topicRewardCount = topicRewardCount;
		return this;
	}
	public int getTopicReward() {
		return topicReward;
	}
	public TopicBean setTopicReward(int topicReward) {
		this.topicReward = topicReward;
		return this;
	}
	public int getIs_delete() {
		return is_delete;
	}
	public TopicBean setIs_delete(int is_delete) {
		this.is_delete = is_delete;
		return this;
	}
	public String getIs_freeze() {
		return is_freeze;
	}
	public TopicBean setIs_freeze(String is_freeze) {
		this.is_freeze = is_freeze;
		return this;
	}
	public int getTopic_state() {
		return topic_state;
	}
	public TopicBean setTopic_state(int topic_state) {
		this.topic_state = topic_state;
		return this;
	}
	public String getIs_thumb() {
		return is_thumb;
	}
	public TopicBean setIs_thumb(String is_thumb) {
		this.is_thumb = is_thumb;
		return this;
	}
	public String getSort_type() {
		return sort_type;
	}
	public TopicBean setSort_type(String sort_type) {
		this.sort_type = sort_type;
		return this;
	}
	public String getUpdate_type() {
		return update_type;
	}
	public TopicBean setUpdate_type(String update_type) {
		this.update_type = update_type;
		return this;
	}
	public List<MainBrandBean> getMainBeanList() {
		return mainBeanList;
	}
	public TopicBean setMainBeanList(List<MainBrandBean> mainBeanList) {
		this.mainBeanList = mainBeanList;
		return this;
	}
	public MemberBean getMemberBean() {
		return memberBean;
	}
	public TopicBean setMemberBean(MemberBean memberBean) {
		this.memberBean = memberBean;
		return this;
	}
	public List<CustomerServiceBean> getCustomerServiceBeans() {
		return customerServiceBeans;
	}
	public TopicBean setCustomerServiceBeans(
			List<CustomerServiceBean> customerServiceBeans) {
		this.customerServiceBeans = customerServiceBeans;
		return this;
	}
	public MemberQualificationBean getMemberQualificationBean() {
		return memberQualificationBean;
	}
	public TopicBean setMemberQualificationBean(
			MemberQualificationBean memberQualificationBean) {
		this.memberQualificationBean = memberQualificationBean;
		return this;
	}
	
}
