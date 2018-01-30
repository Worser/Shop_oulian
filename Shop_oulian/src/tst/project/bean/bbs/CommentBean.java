package tst.project.bean.bbs;

import java.util.List;
import tst.project.bean.member.MemberBean;

public class CommentBean {
	private int id;
	private int blockId;
	private int member_id;
	private MemberBean member;
	private int comment_id;
	private String company_name;
	private String company_remark;
	private String level_name;
	private String head_path;
	private String commentContent;
	private int comment_floor;
	private int commentReplyCount;
	private String commentTime;
	private String is_delete;
	private List<ReplyBean> replyBeans;

	public int getId() {
		return id;
	}

	public CommentBean setId(int id) {
		this.id = id;
		return this;
	}

	public int getBlockId() {
		return blockId;
	}

	public CommentBean setBlockId(int blockId) {
		this.blockId = blockId;
		return this;
	}

	public int getMember_id() {
		return member_id;
	}

	public CommentBean setMember_id(int member_id) {
		this.member_id = member_id;
		return this;
	}

	public MemberBean getMember() {
		return member;
	}

	public CommentBean setMember(MemberBean member) {
		this.member = member;
		return this;
	}

	public int getComment_id() {
		return comment_id;
	}

	public CommentBean setComment_id(int comment_id) {
		this.comment_id = comment_id;
		return this;
	}

	public String getCompany_name() {
		return company_name;
	}

	public CommentBean setCompany_name(String company_name) {
		this.company_name = company_name;
		return this;
	}

	public String getCompany_remark() {
		return company_remark;
	}

	public CommentBean setCompany_remark(String company_remark) {
		this.company_remark = company_remark;
		return this;
	}

	public String getLevel_name() {
		return level_name;
	}

	public CommentBean setLevel_name(String level_name) {
		this.level_name = level_name;
		return this;
	}

	public String getHead_path() {
		return head_path;
	}

	public CommentBean setHead_path(String head_path) {
		this.head_path = head_path;
		return this;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public CommentBean setCommentContent(String commentContent) {
		this.commentContent = commentContent;
		return this;
	}

	public int getComment_floor() {
		return comment_floor;
	}

	public CommentBean setComment_floor(int comment_floor) {
		this.comment_floor = comment_floor;
		return this;
	}

	public int getCommentReplyCount() {
		return commentReplyCount;
	}

	public CommentBean setCommentReplyCount(int commentReplyCount) {
		this.commentReplyCount = commentReplyCount;
		return this;
	}

	public String getCommentTime() {
		return commentTime;
	}

	public CommentBean setCommentTime(String commentTime) {
		this.commentTime = commentTime;
		return this;
	}

	public String getIs_delete() {
		return is_delete;
	}

	public CommentBean setIs_delete(String is_delete) {
		this.is_delete = is_delete;
		return this;
	}

	public List<ReplyBean> getReplyBeans() {
		return replyBeans;
	}

	public CommentBean setReplyBeans(List<ReplyBean> replyBeans) {
		this.replyBeans = replyBeans;
		return this;
	}

}