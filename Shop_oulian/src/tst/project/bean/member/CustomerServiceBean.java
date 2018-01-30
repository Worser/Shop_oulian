package tst.project.bean.member;

public class CustomerServiceBean {

	private int customer_service_id;
	private String member_id;
	private String service_qq;
	private String is_delete;
	private String create_time;
	
	private String merchants_id;
	
	public String getMerchants_id() {
		return merchants_id;
	}
	public CustomerServiceBean setMerchants_id(String merchants_id) {
		this.merchants_id = merchants_id;
		return this;
	}
	public int getCustomer_service_id() {
		return customer_service_id;
	}
	public CustomerServiceBean setCustomer_service_id(int customer_service_id) {
		this.customer_service_id = customer_service_id;
		return this;
	}
	public String getMember_id() {
		return member_id;
	}
	public CustomerServiceBean setMember_id(String member_id) {
		this.member_id = member_id;
		return this;
	}
	public String getService_qq() {
		return service_qq;
	}
	public CustomerServiceBean setService_qq(String service_qq) {
		this.service_qq = service_qq;
		return this;
	}
	public String getIs_delete() {
		return is_delete;
	}
	public CustomerServiceBean setIs_delete(String is_delete) {
		this.is_delete = is_delete;
		return this;
	}
	public String getCreate_time() {
		return create_time;
	}
	public CustomerServiceBean setCreate_time(String create_time) {
		this.create_time = create_time;
		return this;
	}
	
}
