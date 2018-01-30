package tst.project.bean.bbs;

public class ThumbBean {
	private int thumb_id;
	private int member_id;
	private int relation_id;
	private int thumb_state;
	private int thumb_type;
	private int is_delete;
	private String creat_time;
	private String update_type;

	public int getThumb_id() {
		return thumb_id;
	}

	public ThumbBean setThumb_id(int thumb_id) {
		this.thumb_id = thumb_id;
		return this;
	}

	public int getMember_id() {
		return member_id;
	}

	public ThumbBean setMember_id(int member_id) {
		this.member_id = member_id;
		return this;
	}

	public int getRelation_id() {
		return relation_id;
	}

	public ThumbBean setRelation_id(int relation_id) {
		this.relation_id = relation_id;
		return this;
	}

	public int getThumb_state() {
		return thumb_state;
	}

	public ThumbBean setThumb_state(int thumb_state) {
		this.thumb_state = thumb_state;
		return this;
	}

	public int getThumb_type() {
		return thumb_type;
	}

	public ThumbBean setThumb_type(int thumb_type) {
		this.thumb_type = thumb_type;
		return this;
	}

	public int getIs_delete() {
		return is_delete;
	}

	public ThumbBean setIs_delete(int is_delete) {
		this.is_delete = is_delete;
		return this;
	}

	public String getCreat_time() {
		return creat_time;
	}

	public ThumbBean setCreat_time(String creat_time) {
		this.creat_time = creat_time;
		return this;
	}

	public String getUpdate_type() {
		return update_type;
	}

	public ThumbBean setUpdate_type(String update_type) {
		this.update_type = update_type;
		return this;
	}

}