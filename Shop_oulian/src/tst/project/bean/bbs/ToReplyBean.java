package tst.project.bean.bbs;
/**
 * 回复111
 * @author xiesh
 *
 */
public class ToReplyBean {
	private int reply_id;
	private int to_reply_id;
	private int comment_id;
	private int id;
	private String replyContent;
	private String toReplyContent;
	private int member_id;
	private int to_member_id;
	private int to_reply_member_id;
	private String replyEmotion;
	private String replyReplyTime;
	private String replyTime;
	private String is_delete;
	private String member_name;
	private String to_member_name;

	public ToReplyBean() {
		this.replyEmotion = "face01.gif";
	}

	public int getReply_id() {
		return reply_id;
	}

	public ToReplyBean setReply_id(int reply_id) {
		this.reply_id = reply_id;
		return this;
	}

	public int getTo_reply_id() {
		return to_reply_id;
	}

	public ToReplyBean setTo_reply_id(int to_reply_id) {
		this.to_reply_id = to_reply_id;
		return this;
	}

	public int getComment_id() {
		return comment_id;
	}

	public ToReplyBean setComment_id(int comment_id) {
		this.comment_id = comment_id;
		return this;
	}

	public int getId() {
		return id;
	}

	public ToReplyBean setId(int id) {
		this.id = id;
		return this;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public ToReplyBean setReplyContent(String replyContent) {
		this.replyContent = replyContent;
		return this;
	}

	public String getToReplyContent() {
		return toReplyContent;
	}

	public ToReplyBean setToReplyContent(String toReplyContent) {
		this.toReplyContent = toReplyContent;
		return this;
	}

	public int getMember_id() {
		return member_id;
	}

	public ToReplyBean setMember_id(int member_id) {
		this.member_id = member_id;
		return this;
	}

	public int getTo_member_id() {
		return to_member_id;
	}

	public ToReplyBean setTo_member_id(int to_member_id) {
		this.to_member_id = to_member_id;
		return this;
	}

	public int getTo_reply_member_id() {
		return to_reply_member_id;
	}

	public ToReplyBean setTo_reply_member_id(int to_reply_member_id) {
		this.to_reply_member_id = to_reply_member_id;
		return this;
	}

	public String getReplyEmotion() {
		return replyEmotion;
	}

	public ToReplyBean setReplyEmotion(String replyEmotion) {
		this.replyEmotion = replyEmotion;
		return this;
	}

	public String getReplyReplyTime() {
		return replyReplyTime;
	}

	public ToReplyBean setReplyReplyTime(String replyReplyTime) {
		this.replyReplyTime = replyReplyTime;
		return this;
	}

	public String getReplyTime() {
		return replyTime;
	}

	public ToReplyBean setReplyTime(String replyTime) {
		this.replyTime = replyTime;
		return this;
	}

	public String getIs_delete() {
		return is_delete;
	}

	public ToReplyBean setIs_delete(String is_delete) {
		this.is_delete = is_delete;
		return this;
	}

	public String getMember_name() {
		return member_name;
	}

	public ToReplyBean setMember_name(String member_name) {
		this.member_name = member_name;
		return this;
	}

	public String getTo_member_name() {
		return to_member_name;
	}

	public ToReplyBean setTo_member_name(String to_member_name) {
		this.to_member_name = to_member_name;
		return this;
	}

}