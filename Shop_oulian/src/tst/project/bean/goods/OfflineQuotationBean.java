package tst.project.bean.goods;

import java.util.List;

import tst.project.bean.member.CustomerServiceBean;

public class OfflineQuotationBean {

	private int quotation_id;
	private String member_id;
	private String find_id; //求购id
	private String quotation_price; //报价 价格
	private String is_delete;
	private String create_time;
	
	private String member_name;
	private List<CustomerServiceBean> customerServiceBeans; //供应商QQ数组
	
	public List<CustomerServiceBean> getCustomerServiceBeans() {
		return customerServiceBeans;
	}
	public OfflineQuotationBean setCustomerServiceBeans(
			List<CustomerServiceBean> customerServiceBeans) {
		this.customerServiceBeans = customerServiceBeans;
		return this;
	}
	public String getMember_name() {
		return member_name;
	}
	public OfflineQuotationBean setMember_name(String member_name) {
		this.member_name = member_name;
		return this;
	}
	public String getCreate_time() {
		return create_time;
	}
	public OfflineQuotationBean setCreate_time(String create_time) {
		this.create_time = create_time;
		return this;
	}
	public int getQuotation_id() {
		return quotation_id;
	}
	public OfflineQuotationBean setQuotation_id(int quotation_id) {
		this.quotation_id = quotation_id;
		return this;
	}
	public String getMember_id() {
		return member_id;
	}
	public OfflineQuotationBean setMember_id(String member_id) {
		this.member_id = member_id;
		return this;
	}
	public String getFind_id() {
		return find_id;
	}
	public OfflineQuotationBean setFind_id(String find_id) {
		this.find_id = find_id;
		return this;
	}
	public String getQuotation_price() {
		return quotation_price;
	}
	public OfflineQuotationBean setQuotation_price(String quotation_price) {
		this.quotation_price = quotation_price;
		return this;
	}
	public String getIs_delete() {
		return is_delete;
	}
	public OfflineQuotationBean setIs_delete(String is_delete) {
		this.is_delete = is_delete;
		return this;
	}
	
}
