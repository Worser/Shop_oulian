package tst.project.bean.others;

public class AnnouncementBean {

	private int announcement_id;
	private String announcement_ids;
	private String announcement_title;
	private String announcement_content;
	private String is_delete;
	private String create_time;
	
	public String getAnnouncement_ids() {
		return announcement_ids;
	}
	public AnnouncementBean setAnnouncement_ids(String announcement_ids) {
		this.announcement_ids = announcement_ids;
		return this;
	}
	public int getAnnouncement_id() {
		return announcement_id;
	}
	public AnnouncementBean setAnnouncement_id(int announcement_id) {
		this.announcement_id = announcement_id;
		return this;
	}
	public String getAnnouncement_title() {
		return announcement_title;
	}
	public AnnouncementBean setAnnouncement_title(String announcement_title) {
		this.announcement_title = announcement_title;
		return this;
	}
	public String getAnnouncement_content() {
		return announcement_content;
	}
	public AnnouncementBean setAnnouncement_content(String announcement_content) {
		this.announcement_content = announcement_content;
		return this;
	}
	public String getIs_delete() {
		return is_delete;
	}
	public AnnouncementBean setIs_delete(String is_delete) {
		this.is_delete = is_delete;
		return this;
	}
	public String getCreate_time() {
		return create_time;
	}
	public AnnouncementBean setCreate_time(String create_time) {
		this.create_time = create_time;
		return this;
	}
	
}
