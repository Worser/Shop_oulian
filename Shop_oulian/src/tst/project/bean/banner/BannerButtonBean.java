package tst.project.bean.banner;

public class BannerButtonBean {
	private int button_banner_id;
	private String banner_id;
	private String button_name;
	private String button_url;
	private String button_state;
	private String is_delete;
	private String update_time;
	private String create_time;
	

	public String getBanner_id() {
		return banner_id;
	}
	public BannerButtonBean setBanner_id(String banner_id) {
		this.banner_id = banner_id;
		return this;
	}
	public int getButton_banner_id() {
		return button_banner_id;
	}
	public BannerButtonBean setButton_banner_id(int button_banner_id) {
		this.button_banner_id = button_banner_id;
		return this;
	}
	public String getButton_name() {
		return button_name;
	}
	public BannerButtonBean setButton_name(String button_name) {
		this.button_name = button_name;
		return this;
	}
	public String getButton_url() {
		return button_url;
	}
	public BannerButtonBean setButton_url(String button_url) {
		this.button_url = button_url;
		return this;
	}
	public String getButton_state() {
		return button_state;
	}
	public BannerButtonBean setButton_state(String button_state) {
		this.button_state = button_state;
		return this;
	}
	public String getIs_delete() {
		return is_delete;
	}
	public BannerButtonBean setIs_delete(String is_delete) {
		this.is_delete = is_delete;
		return this;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public BannerButtonBean setUpdate_time(String update_time) {
		this.update_time = update_time;
		return this;
	}
	public String getCreate_time() {
		return create_time;
	}
	public BannerButtonBean setCreate_time(String create_time) {
		this.create_time = create_time;
		return this;
	}	
}
