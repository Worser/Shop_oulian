package tst.project.bean.member;

public class MainBrandBean {

	private Integer main_id; 
	private String member_id;
	private String main_brand; //主营品牌
	private String is_delete;
	private String create_time;
	
	public Integer getMain_id() {
		return main_id;
	}
	public MainBrandBean setMain_id(Integer main_id) {
		this.main_id = main_id;
		return this;
	}
	public String getMember_id() {
		return member_id;
	}
	public MainBrandBean setMember_id(String member_id) {
		this.member_id = member_id;
		return this;
	}
	public String getMain_brand() {
		return main_brand;
	}
	public MainBrandBean setMain_brand(String main_brand) {
		this.main_brand = main_brand;
		return this;
	}
	public String getIs_delete() {
		return is_delete;
	}
	public MainBrandBean setIs_delete(String is_delete) {
		this.is_delete = is_delete;
		return this;
	}
	public String getCreate_time() {
		return create_time;
	}
	public MainBrandBean setCreate_time(String create_time) {
		this.create_time = create_time;
		return this;
	}
	
}
