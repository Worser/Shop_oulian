package tst.project.bean.member;

public class MessageBean {

	private int message_id;
	private String member_id;
	private String message_title;
	private String message_content;
	private String is_read;
	private String is_delete;
	private String create_time;
	private String message_type;
	private String message_type_show;
	private String member_name;
	private String merchants_name;
	private String message_ids;

	public String getMessage_type() {
		return this.message_type;
	}

	public MessageBean setMessage_type(String message_type) {
		this.message_type = message_type;
		this.message_type_show = ("0".equals(message_type) ? "销售" : "采购");
		return this;
	}

	public String getMessage_type_show() {
		return this.message_type_show;
	}

	public String getMember_name() {
		return this.member_name;
	}

	public MessageBean setMember_name(String member_name) {
		this.member_name = member_name;
		return this;
	}

	public String getMerchants_name() {
		return this.merchants_name;
	}

	public MessageBean setMerchants_name(String merchants_name) {
		this.merchants_name = merchants_name;
		return this;
	}

	public MessageBean setMessage_type_show(String message_type_show) {
		this.message_type_show = message_type_show;
		return this;
	}

	public String getMessage_ids() {
		return message_ids;
	}

	public MessageBean setMessage_ids(String message_ids) {
		this.message_ids = message_ids;
		return this;
	}

	public int getMessage_id() {
		return message_id;
	}

	public MessageBean setMessage_id(int message_id) {
		this.message_id = message_id;
		return this;
	}

	public String getMember_id() {
		return member_id;
	}

	public MessageBean setMember_id(String member_id) {
		this.member_id = member_id;
		return this;
	}

	public String getMessage_title() {
		return message_title;
	}

	public MessageBean setMessage_title(String message_title) {
		this.message_title = message_title;
		return this;
	}

	public String getMessage_content() {
		return message_content;
	}

	public MessageBean setMessage_content(String message_content) {
		this.message_content = message_content;
		return this;
	}

	public String getIs_read() {
		return is_read;
	}

	public MessageBean setIs_read(String is_read) {
		this.is_read = is_read;
		return this;
	}

	public String getIs_delete() {
		return is_delete;
	}

	public MessageBean setIs_delete(String is_delete) {
		this.is_delete = is_delete;
		return this;
	}

	public String getCreate_time() {
		return create_time;
	}

	public MessageBean setCreate_time(String create_time) {
		this.create_time = create_time;
		return this;
	}

}
