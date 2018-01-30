package tst.project.bean.member;

public class EnterpriseAdvantageBean {

	private Integer advantage_id; 
	private String member_id;
	private String advantage_name; //企业优势
	private String is_delete;
	private String create_time;
	
	public Integer getAdvantage_id() {
		return advantage_id;
	}
	public EnterpriseAdvantageBean setAdvantage_id(Integer advantage_id) {
		this.advantage_id = advantage_id;
		return this;
	}
	public String getMember_id() {
		return member_id;
	}
	public EnterpriseAdvantageBean setMember_id(String member_id) {
		this.member_id = member_id;
		return this;
	}
	public String getAdvantage_name() {
		return advantage_name;
	}
	public EnterpriseAdvantageBean setAdvantage_name(String advantage_name) {
		this.advantage_name = advantage_name;
		return this;
	}
	public String getIs_delete() {
		return is_delete;
	}
	public EnterpriseAdvantageBean setIs_delete(String is_delete) {
		this.is_delete = is_delete;
		return this;
	}
	public String getCreate_time() {
		return create_time;
	}
	public EnterpriseAdvantageBean setCreate_time(String is_delete) {
		create_time = is_delete;
		return this;
	}
}
